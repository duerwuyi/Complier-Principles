import java.util.*;


public class Java_LLParserAnalysis
{
    public static String[] alphabets = {
            "{",                "}",                "if",               "(",                ")",//0-4
            "then",             "else",             "while",            "ID",               "=",//5-9
            ">",                "<",                ">=",               "<=",               "==",//10-14
            "+",                "-",                "*",                "/",                "NUM",//15-19
            "E",                "$",                ";",                "compoundstmt",     "stmt",//20-24  0-1
            "ifstmt",           "whilestmt",        "assgstmt",         "stmts",            "boolexpr",//25-29   2-6
            "arithexpr",        "boolop",           "multexpr",         "arithexprprime",   "simpleexpr",//30-34   7-11
            "multexprprime",    "program"                                                                   //35-36  12-13
    };

    public static class Expression {
        public Expression(int leftV, int[] rightV){
            left = leftV;
            right = rightV;
        }
        public int left;
        public int[] right;
    }

    public static ArrayList<Expression> expressions =new ArrayList<>();

    public static void exprInit(){
        expressions.add(new Expression(36, new int[]{23}));
        expressions.add(new Expression(24, new int[]{25}));
        expressions.add(new Expression(24, new int[]{26}));
        expressions.add(new Expression(24, new int[]{27}));
        expressions.add(new Expression(24, new int[]{23}));

        expressions.add(new Expression(23, new int[]{0,28,1}));
        expressions.add(new Expression(28, new int[]{24,28}));
        expressions.add(new Expression(28, new int[]{20}));
        expressions.add(new Expression(25, new int[]{2,3,29,4,5,24,6,24}));
        expressions.add(new Expression(26, new int[]{7,3,29,4,24}));

        expressions.add(new Expression(27, new int[]{8,9,30,22}));
        expressions.add(new Expression(29, new int[]{30,31,30}));
        expressions.add(new Expression(31, new int[]{10}));
        expressions.add(new Expression(31, new int[]{11}));
        expressions.add(new Expression(31, new int[]{12}));

        expressions.add(new Expression(31, new int[]{13}));
        expressions.add(new Expression(31, new int[]{14}));
        expressions.add(new Expression(30, new int[]{32,33}));
        expressions.add(new Expression(33, new int[]{15,32,33}));
        expressions.add(new Expression(33, new int[]{16,32,33}));

        expressions.add(new Expression(33, new int[]{20}));
        expressions.add(new Expression(32, new int[]{34,35}));
        expressions.add(new Expression(35, new int[]{17,34,35}));
        expressions.add(new Expression(35, new int[]{18,34,35}));
        expressions.add(new Expression(35, new int[]{20}));

        expressions.add(new Expression(34, new int[]{8}));
        expressions.add(new Expression(34, new int[]{19}));
        expressions.add(new Expression(34, new int[]{3,30,4}));
    }

    public static int[][] table = new int[14][23];

    public static void tableInit(){
        for(int i =0; i<14;i++){
            for(int j=0;j<23;j++){
                table[i][j] = -1;
            }
        }
        table[13][22] = -2; //accept
        table[13][0] = 0;table[0][0] = 5; table[1][0] = 4; table[1][2] = 1; table[1][7] = 2;
        table[1][8] = 3;table[2][2] = 8 ;table[3][7] = 9 ;table[4][8] = 10 ; table[5][0] = 6;
        table[5][2] = 6;table[5][7] = 6;table[5][8] = 6;table[5][1] = 7;table[6][8] = 11;
        table[6][19] = 11;table[6][3] = 11;table[7][8] = 17 ;table[7][19] =17 ;table[7][3] =17 ;
        table[8][11] = 12;table[8][10] = 13;table[8][13] = 14;table[8][12] = 15;table[8][14] =16;
        table[9][8] = 21;table[9][19] = 21;table[9][3] = 21;table[10][14] = 20;table[10][10] = 20;
        table[10][11] = 20;table[10][12] = 20;table[10][13] = 20;table[10][15] = 18;table[10][16] = 19;
        table[10][22] = 20;table[10][4] = 20;table[11][8] = 25;table[11][19] = 26;table[11][3] = 27;
        table[12][10] = 24;table[12][11] = 24;table[12][12] = 24;table[12][13] = 24;table[12][14] = 24;
        table[12][15] = 24;table[12][16] = 24;table[12][17] = 22;table[12][18] = 23;
        table[12][22] = 24;table[12][4] = 24;

    }

    public static Boolean isTerminal(int num){
        return num <= 22;
    }

    private static String input;
    private static final ArrayList<Integer> symbols = new ArrayList<>();

    /**
     *  this method is to read the standard input
     */
    private static void readProg()
    {
        StringBuilder prog = new StringBuilder();
        Scanner sc = new Scanner(System.in);
        while(sc.hasNextLine())
        {
            String s = sc.nextLine();
            prog.append(s);
            if(!s.isEmpty()){
                prog.append(" \n ");
            }
        }
        prog.append(" $");
        input = prog.toString();
    }

    public static int symbolParse(){
        int row = 1;
        String[] trials = input.split(" ");
        for(String trial: trials){
            if(trial.equals("")) continue;
            if(trial.equals("\n")){
                enterPos.put(row,symbols.size());
                row++;
                continue;
            }
            int i = 0;
            for(; i < alphabets.length ; i++){
                if(alphabets[i].equals(trial)){
                    symbols.add(i);
                    break;
                }
            }
            if(i == alphabets.length){
                return -1; //error
            }
        }
        return 1;
    }

    public static Stack<Integer> sequence = new Stack<>();
    public static Stack<Integer> depths = new Stack<>();
    public static StringBuilder parserTree = new StringBuilder();
    public static StringBuilder errors = new StringBuilder();
    public static HashMap<Integer,Integer> enterPos = new HashMap<>();

    public static void sequenceInit(){
        sequence.add(36);// sequence: program
        depths.add(0);
    }

    /**
     *  you should add some code in this method to achieve this lab
     */
    private static void analysis()
    {
        exprInit();//input expr
        tableInit();//input parsing table
        readProg();
        int code = symbolParse();//parse the input
        if(code == -1){
            System.out.print("error");
            return;
        }
        int i = 0; // points to array "symbols"
        int row = 1 ;//row numbers
        sequenceInit();
        int x = sequence.peek();//the leftest symbol in stack
        while(!sequence.empty()){
            int nextInput = symbols.get(i);
            if (x == nextInput){// the leftest symbol is a terminal-symbol and equals the input
                sequence.pop();
                int depth = depths.pop();
                for(int d =0;d<depth;d++){
                    parserTree.append("\t");
                }
                parserTree.append(alphabets[x]);
                parserTree.append('\n');
                i++;
                if(enterPos.get(row) <= i){
                    row++;
                }
            } else if (isTerminal(x)) {// the leftest symbol is a terminal-symbol but not emerges,or it's E
                int e = sequence.pop();
                int depth = depths.pop();
                if(e != 20) {
                    errors.append("语法错误,第").append(row-1).append("行,缺少\"").append(alphabets[x]).append("\"\n");
                    for(int d =0;d<depth;d++){
                        parserTree.append("\t");
                    }
                    parserTree.append(alphabets[x]);
                    parserTree.append('\n');
                }else {
                    for(int d =0;d<depth;d++){
                        parserTree.append("\t");
                    }
                    parserTree.append(alphabets[x]);
                    parserTree.append('\n');
                }
            } else if (table[x-23][nextInput] == -1) {

                if(isEmptySymbol(x)){// X -> E is a default choice
                    sequence.pop();
                    int depth = depths.pop();
                    for(int d =0;d<depth;d++){
                        parserTree.append("\t");
                    }
                    parserTree.append(alphabets[x]);
                    parserTree.append('\n');

                    for(int d =0;d<depth+1;d++){
                        parserTree.append("\t");
                    }
                    parserTree.append("E");
                    parserTree.append('\n');
                }else{
                    System.out.print(alphabets[x]+" shouldn't meet "+ alphabets[nextInput]+"\n");
                    break;
                }
            } else{// the leftest symbol is a non-terminal-symbol and triggers some actions.
                sequence.pop();
                int depth = depths.pop();
                for(int d =0;d<depth;d++){
                    parserTree.append("\t");
                }
                parserTree.append(alphabets[x]);
                parserTree.append('\n');

                int generation = table[x-23][nextInput];
                depth++;
                Expression expression = expressions.get(generation);
                for(int k = expression.right.length -1;k >= 0; k--){
                    sequence.add(expression.right[k]);
                    depths.add(depth);
                }
            }
            try{
                x = sequence.peek();
            }catch (Exception e){break;}//at the end

        }
        if(symbols.get(i) != 21){
            System.out.print("left:  ");
            for(int k = i;k<symbols.size();k++){
                System.out.print(alphabets[symbols.get(k)]  + " ");
            }
            System.out.print("\n");
        }

        System.out.print(errors);
        System.out.print(parserTree);
    }

    private static boolean isEmptySymbol(int x) {
        for(Expression expression :expressions){
            if(expression.left == x){
                for(int i : expression.right){
                    if(i == 20) return true;
                }
            }
        }
        return false;
    }

    /**
     * this is the main method
     * @param args
     */
    public static void main(String[] args) {
        analysis();
    }
}
