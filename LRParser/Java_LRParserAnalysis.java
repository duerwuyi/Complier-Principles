import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;


public class Java_LRParserAnalysis
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
        expressions.add(new Expression(28, new int[]{}));
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

        expressions.add(new Expression(33, new int[]{}));
        expressions.add(new Expression(32, new int[]{34,35}));
        expressions.add(new Expression(35, new int[]{17,34,35}));
        expressions.add(new Expression(35, new int[]{18,34,35}));
        expressions.add(new Expression(35, new int[]{}));

        expressions.add(new Expression(34, new int[]{8}));
        expressions.add(new Expression(34, new int[]{19}));
        expressions.add(new Expression(34, new int[]{3,30,4}));
    }

    public static class LRState{
        LRState(){
            transitions = new ArrayList<>();
        }

        public void add(int mode, int actionMode, int trigger, int destination){
            Transition transition = new Transition();
            transition.mode = mode;
            transition.actionMode = actionMode;
            transition.trigger = trigger;
            transition.destination = destination;
            transitions.add(transition);
        }

        public static class Transition{
            public int mode;//0-ACTION,1-GOTO
            public int actionMode;//0-shift 1-reduce 2-acc
            public int trigger;
            public int destination;
        }
        public ArrayList<Transition> transitions;
    }

    public static LRState.Transition findTransition(int stateIndex,int trigger){
        for(LRState.Transition transition : LRStates.get(stateIndex).transitions){
            if(transition.trigger == trigger){
                return transition;
            }
        }
        return null;
    }

    public static ArrayList<LRState> LRStates = new ArrayList<>();

    //use python to write this function
    public static void stateInit(){
        LRState L = new LRState();
//state0
        L.add(0,0,0,2);
        L.add(1,0,23,1);
        LRStates.add(L);
        L = new LRState();
//state1
        L.add(0,2,21,0);
        LRStates.add(L);
        L = new LRState();
//state2
        L.add(0,0,0,2);
        L.add(0,0,2,10);
        L.add(0,0,7,11);
        L.add(0,1,1,7);
        L.add(0,0,8,12);
        L.add(1,0,24,5);
        L.add(1,0,25,6);
        L.add(1,0,26,7);
        L.add(1,0,27,8);
        L.add(1,0,23,9);
        L.add(1,0,28,3);
        LRStates.add(L);
        L = new LRState();
//state3
        L.add(0,0,1,4);
        LRStates.add(L);
        L = new LRState();
//state4
        L.add(0,1,0,5);
        L.add(0,1,2,5);
        L.add(0,1,6,5);
        L.add(0,1,7,5);
        L.add(0,1,1,5);
        L.add(0,1,8,5);
        L.add(0,1,21,5);
        LRStates.add(L);
        L = new LRState();
//state5
        L.add(0,0,0,2);
        L.add(0,0,2,10);
        L.add(0,0,7,11);
        L.add(0,1,1,7);
        L.add(0,0,8,12);
        L.add(1,0,24,5);
        L.add(1,0,28,13);
        LRStates.add(L);
        L = new LRState();
//state6
        L.add(0,1,0,1);
        L.add(0,1,2,1);
        L.add(0,1,6,1);
        L.add(0,1,7,1);
        L.add(0,1,1,1);
        L.add(0,1,8,1);
        L.add(0,1,21,1);
        LRStates.add(L);
        L = new LRState();
//state7
        L.add(0,1,0,2);
        L.add(0,1,2,2);
        L.add(0,1,6,2);
        L.add(0,1,7,2);
        L.add(0,1,1,2);
        L.add(0,1,8,2);
        L.add(0,1,21,2);
        LRStates.add(L);
        L = new LRState();
//state8
        L.add(0,1,0,3);
        L.add(0,1,2,3);
        L.add(0,1,6,3);
        L.add(0,1,7,3);
        L.add(0,1,1,3);
        L.add(0,1,8,3);
        L.add(0,1,21,3);
        LRStates.add(L);
        L = new LRState();
//state9
        L.add(0,1,0,4);
        L.add(0,1,2,4);
        L.add(0,1,6,4);
        L.add(0,1,7,4);
        L.add(0,1,1,4);
        L.add(0,1,8,4);
        L.add(0,1,21,4);
        LRStates.add(L);
        L = new LRState();
//state10
        L.add(0,0,3,14);
        LRStates.add(L);
        L = new LRState();
//state11
        L.add(0,0,3,15);
        LRStates.add(L);
        L = new LRState();
//state12
        L.add(0,0,9,16);
        LRStates.add(L);
        L = new LRState();
//state13
        L.add(0,1,1,6);
        LRStates.add(L);
        L = new LRState();
//state14
        L.add(0,0,3,39);
        L.add(0,0,8,37);
        L.add(0,0,19,38);
        L.add(1,0,29,17);
        L.add(1,0,30,23);
        L.add(1,0,32,35);
        L.add(1,0,34,36);
        LRStates.add(L);
        L = new LRState();
//state15
        L.add(0,0,3,39);
        L.add(0,0,8,37);
        L.add(0,0,19,38);
        L.add(1,0,29,30);
        L.add(1,0,30,23);
        L.add(1,0,32,35);
        L.add(1,0,34,36);
        LRStates.add(L);
        L = new LRState();
//state16
        L.add(0,0,3,39);
        L.add(0,0,8,37);
        L.add(0,0,19,38);
        L.add(1,0,30,33);
        L.add(1,0,32,35);
        L.add(1,0,34,36);
        LRStates.add(L);
        L = new LRState();
//state17
        L.add(0,0,4,18);
        LRStates.add(L);
        L = new LRState();
//state18
        L.add(0,0,5,19);
        LRStates.add(L);
        L = new LRState();
//state19
        L.add(0,0,0,2);
        L.add(0,0,2,10);
        L.add(0,0,7,11);
        L.add(0,0,8,12);
        L.add(1,0,24,20);
        L.add(1,0,25,6);
        L.add(1,0,26,7);
        L.add(1,0,27,8);
        L.add(1,0,23,9);
        LRStates.add(L);
        L = new LRState();
//state20
        L.add(0,0,6,21);
        LRStates.add(L);
        L = new LRState();
//state21
        L.add(0,0,0,2);
        L.add(0,0,2,10);
        L.add(0,0,7,11);
        L.add(0,0,8,12);
        L.add(1,0,24,22);
        L.add(1,0,25,6);
        L.add(1,0,26,7);
        L.add(1,0,27,8);
        L.add(1,0,23,9);
        LRStates.add(L);
        L = new LRState();
//state22
        L.add(0,1,0,8);
        L.add(0,1,2,8);
        L.add(0,1,6,8);
        L.add(0,1,7,8);
        L.add(0,1,1,8);
        L.add(0,1,8,8);
        L.add(0,1,21,8);
        LRStates.add(L);
        L = new LRState();
//state23
        L.add(0,0,11,25);
        L.add(0,0,10,26);
        L.add(0,0,13,27);
        L.add(0,0,12,28);
        L.add(0,0,14,29);
        L.add(1,0,31,24);
        LRStates.add(L);
        L = new LRState();
//state24
        L.add(0,0,3,39);
        L.add(0,0,8,37);
        L.add(0,0,19,38);
        L.add(1,0,30,56);
        L.add(1,0,32,35);
        L.add(1,0,34,36);
        LRStates.add(L);
        L = new LRState();
//state25
        L.add(0,1,3,12);
        L.add(0,1,8,12);
        L.add(0,1,19,12);
        LRStates.add(L);
        L = new LRState();
//state26
        L.add(0,1,3,13);
        L.add(0,1,8,13);
        L.add(0,1,19,13);
        LRStates.add(L);
        L = new LRState();
//state27
        L.add(0,1,3,14);
        L.add(0,1,8,14);
        L.add(0,1,19,14);
        LRStates.add(L);
        L = new LRState();
//state28
        L.add(0,1,3,15);
        L.add(0,1,8,15);
        L.add(0,1,19,15);
        LRStates.add(L);
        L = new LRState();
//state29
        L.add(0,1,3,16);
        L.add(0,1,8,16);
        L.add(0,1,19,16);
        LRStates.add(L);
        L = new LRState();
//state30
        L.add(0,0,4,31);
        LRStates.add(L);
        L = new LRState();
//state31
        L.add(0,0,0,2);
        L.add(0,0,2,10);
        L.add(0,0,7,11);
        L.add(0,0,8,12);
        L.add(0,1,21,9);
        L.add(1,0,24,32);
        L.add(1,0,25,6);
        L.add(1,0,26,7);
        L.add(1,0,27,8);
        L.add(1,0,23,9);
        LRStates.add(L);
        L = new LRState();
//state32
        L.add(0,1,0,9);
        L.add(0,1,2,9);
        L.add(0,1,6,9);
        L.add(0,1,7,9);
        L.add(0,1,1,9);
        L.add(0,1,8,9);
        LRStates.add(L);
        L = new LRState();
//state33
        L.add(0,0,22,34);
        LRStates.add(L);
        L = new LRState();
//state34
        L.add(0,1,0,10);
        L.add(0,1,2,10);
        L.add(0,1,6,10);
        L.add(0,1,7,10);
        L.add(0,1,1,10);
        L.add(0,1,8,10);
        L.add(0,1,21,10);
        LRStates.add(L);
        L = new LRState();
//state35
        L.add(0,1,4,20);
        L.add(0,1,11,20);
        L.add(0,1,10,20);
        L.add(0,1,13,20);
        L.add(0,1,12,20);
        L.add(0,1,14,20);
        L.add(0,0,15,41);
        L.add(0,0,16,42);
        L.add(0,1,22,20);
        L.add(1,0,33,40);
        LRStates.add(L);
        L = new LRState();
//state36
        L.add(0,1,4,24);
        L.add(0,1,11,24);
        L.add(0,1,10,24);
        L.add(0,1,13,24);
        L.add(0,1,12,24);
        L.add(0,1,14,24);
        L.add(0,1,15,24);
        L.add(0,1,16,24);
        L.add(0,0,17,48);
        L.add(0,0,18,49);
        L.add(0,1,22,24);
        L.add(1,0,35,47);
        LRStates.add(L);
        L = new LRState();
//state37
        L.add(0,1,4,25);
        L.add(0,1,11,25);
        L.add(0,1,10,25);
        L.add(0,1,13,25);
        L.add(0,1,12,25);
        L.add(0,1,14,25);
        L.add(0,1,15,25);
        L.add(0,1,16,25);
        L.add(0,1,17,25);
        L.add(0,1,18,25);
        L.add(0,1,22,25);
        LRStates.add(L);
        L = new LRState();
//state38
        L.add(0,1,4,26);
        L.add(0,1,11,26);
        L.add(0,1,10,26);
        L.add(0,1,13,26);
        L.add(0,1,12,26);
        L.add(0,1,14,26);
        L.add(0,1,15,26);
        L.add(0,1,16,26);
        L.add(0,1,17,26);
        L.add(0,1,18,26);
        L.add(0,1,22,26);
        LRStates.add(L);
        L = new LRState();
//state39
        L.add(0,0,8,37);
        L.add(0,0,19,38);
        L.add(1,0,30,52);
        L.add(1,0,32,35);
        L.add(1,0,34,36);
        LRStates.add(L);
        L = new LRState();
//state40
        L.add(0,1,4,17);
        L.add(0,1,11,17);
        L.add(0,1,10,17);
        L.add(0,1,13,17);
        L.add(0,1,12,17);
        L.add(0,1,14,17);
        L.add(0,1,22,17);
        LRStates.add(L);
        L = new LRState();
//state41
        L.add(0,0,3,39);
        L.add(0,0,8,37);
        L.add(0,0,19,38);
        L.add(1,0,32,43);
        L.add(1,0,34,36);
        LRStates.add(L);
        L = new LRState();
//state42
        L.add(0,0,3,39);
        L.add(0,0,8,37);
        L.add(0,0,19,38);
        L.add(1,0,32,44);
        L.add(1,0,34,36);
        LRStates.add(L);
        L = new LRState();
//state43
        L.add(0,1,4,20);
        L.add(0,1,11,20);
        L.add(0,1,10,20);
        L.add(0,1,13,20);
        L.add(0,1,12,20);
        L.add(0,1,14,20);
        L.add(0,0,15,41);
        L.add(0,0,16,42);
        L.add(0,1,22,20);
        L.add(1,0,33,45);
        LRStates.add(L);
        L = new LRState();
//state44
        L.add(0,1,4,20);
        L.add(0,1,11,20);
        L.add(0,1,10,20);
        L.add(0,1,13,20);
        L.add(0,1,12,20);
        L.add(0,1,14,20);
        L.add(0,0,15,41);
        L.add(0,0,16,42);
        L.add(0,1,22,20);
        L.add(1,0,33,46);
        LRStates.add(L);
        L = new LRState();
//state45
        L.add(0,1,4,18);
        L.add(0,1,11,18);
        L.add(0,1,10,18);
        L.add(0,1,13,18);
        L.add(0,1,12,18);
        L.add(0,1,14,18);
        L.add(0,1,22,18);
        LRStates.add(L);
        L = new LRState();
//state46
        L.add(0,1,4,19);
        L.add(0,1,11,19);
        L.add(0,1,10,19);
        L.add(0,1,13,19);
        L.add(0,1,12,19);
        L.add(0,1,14,19);
        L.add(0,1,22,19);
        LRStates.add(L);
        L = new LRState();
//state47
        L.add(0,1,4,21);
        L.add(0,1,11,21);
        L.add(0,1,10,21);
        L.add(0,1,13,21);
        L.add(0,1,12,21);
        L.add(0,1,14,21);
        L.add(0,1,15,21);
        L.add(0,1,16,21);
        L.add(0,1,22,21);
        LRStates.add(L);
        L = new LRState();
//state48
        L.add(0,0,3,39);
        L.add(0,0,8,37);
        L.add(0,0,19,38);
        L.add(1,0,34,50);
        LRStates.add(L);
        L = new LRState();
//state49
        L.add(0,0,3,39);
        L.add(0,0,8,37);
        L.add(0,0,19,38);
        L.add(1,0,34,51);
        LRStates.add(L);
        L = new LRState();
//state50
        L.add(0,1,4,24);
        L.add(0,1,11,24);
        L.add(0,1,10,24);
        L.add(0,1,13,24);
        L.add(0,1,12,24);
        L.add(0,1,14,24);
        L.add(0,1,15,24);
        L.add(0,1,16,24);
        L.add(0,0,17,48);
        L.add(0,0,18,49);
        L.add(0,1,22,24);
        L.add(1,0,35,54);
        LRStates.add(L);
        L = new LRState();
//state51
        L.add(0,1,4,24);
        L.add(0,1,11,24);
        L.add(0,1,10,24);
        L.add(0,1,13,24);
        L.add(0,1,12,24);
        L.add(0,1,14,24);
        L.add(0,1,15,24);
        L.add(0,1,16,24);
        L.add(0,0,17,48);
        L.add(0,0,18,49);
        L.add(0,1,22,24);
        L.add(1,0,35,55);
        LRStates.add(L);
        L = new LRState();
//state52
        L.add(0,0,4,53);
        LRStates.add(L);
        L = new LRState();
//state53
        L.add(0,1,4,27);
        L.add(0,1,11,27);
        L.add(0,1,10,27);
        L.add(0,1,13,27);
        L.add(0,1,12,27);
        L.add(0,1,14,27);
        L.add(0,1,15,27);
        L.add(0,1,16,27);
        L.add(0,1,17,27);
        L.add(0,1,18,27);
        L.add(0,1,22,27);
        LRStates.add(L);
        L = new LRState();
//state54
        L.add(0,1,4,22);
        L.add(0,1,11,22);
        L.add(0,1,10,22);
        L.add(0,1,13,22);
        L.add(0,1,12,22);
        L.add(0,1,14,22);
        L.add(0,1,15,22);
        L.add(0,1,16,22);
        L.add(0,1,22,22);
        LRStates.add(L);
        L = new LRState();
//state55
        L.add(0,1,4,23);
        L.add(0,1,11,23);
        L.add(0,1,10,23);
        L.add(0,1,13,23);
        L.add(0,1,12,23);
        L.add(0,1,14,23);
        L.add(0,1,15,23);
        L.add(0,1,16,23);
        L.add(0,1,22,23);
        LRStates.add(L);
        L = new LRState();
//state56
        L.add(0,1,4,11);
        LRStates.add(L);

    }

    public static Boolean isTerminal(int num){
        return num <= 22;
    }

    private static String input;


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
                enterPos.put(row, inputSymbols.size());
                row++;
                continue;
            }
            int i = 0;
            for(; i < alphabets.length ; i++){
                if(alphabets[i].equals(trial)){
                    inputSymbols.add(i);
                    break;
                }
            }
            if(i == alphabets.length){
                return -1; //error
            }
        }
        return 1;
    }

    public static HashMap<Integer,Integer> enterPos = new HashMap<>();
    private static final ArrayList<Integer> inputSymbols = new ArrayList<>();
    public static Stack<Integer> stateStack = new Stack<>();
    private static final ArrayList<Integer> parserSymbols = new ArrayList<>();
    public static ArrayList<String> answer = new ArrayList<>();

    public static void addToAnswer(int index){
        StringBuilder s = new StringBuilder();

        for(int i : parserSymbols){
            s.append(alphabets[i]);
            s.append(' ');
        }
        for(int i = index;i < inputSymbols.size() - 1;i++){
            s.append(alphabets[inputSymbols.get(i)]);
            s.append(' ');
        }
        if(index != 0){
            s.append("=> ");
        }
        s.append("\n");
        answer.add(s.toString());
    }

    public static void printAnswer(){
        for(int i = answer.size()-1;i>=0;i--){
            System.out.print(answer.get(i));
        }
    }


    /**
     *  you should add some code in this method to achieve this lab
     */
    private static void analysis()
    {
        exprInit();
        stateInit();
        readProg();
        if(symbolParse() != 1){
            System.out.print("error input");
            return;
        }
        stateStack.push(0);
        int rowNum = 1;
        int offset = 0;
        int inputIndex = 0;
        int next = inputSymbols.get(inputIndex);
        addToAnswer(inputIndex);
        while(true){
            int state = stateStack.peek();
            LRState.Transition t = findTransition(state,next);
            if(t != null && t.mode == 0 && t.actionMode == 0){
                stateStack.add(t.destination);
                parserSymbols.add(next);
                inputIndex++;
                if(inputIndex - offset >= enterPos.get(rowNum)){
                    rowNum++;
                }
                next = inputSymbols.get(inputIndex);
            }else if(t != null && t.mode == 0 && t.actionMode == 1){
                int expressionIndex = t.destination; // reduce action's destination is the index of a generating expression
                int length = expressions.get(expressionIndex).right.length;
                int left = expressions.get(expressionIndex).left;
                for(int i =0;i<length;i++){
                    stateStack.pop();
                    parserSymbols.remove(parserSymbols.size()-1);
                }
                int tempState = stateStack.peek();
                LRState.Transition tempTransition = findTransition(tempState,left);
                if(tempTransition != null && tempTransition.mode == 1){//to confirm it is a GOTO transition
                    stateStack.add(tempTransition.destination);
                    parserSymbols.add(left);
                }else{
                    //it is impossible to have a null GOTO[] unless the transition was computed wrong
                    //thus it should be impossible to get here.
                    System.out.print("error\n");
                }
                //print parserSymbols into stack
                addToAnswer(inputIndex);
            }else if(t != null && t.mode == 0 && t.actionMode == 2){
                break;//accept
            }else{
                LRState.Transition tempTransition;

                for(int i = 22; i >= 0 ; i--){
                    tempTransition = findTransition(state,i);
                    if(tempTransition == null) continue;
                    // error handler
                    System.out.print("语法错误，第"+(rowNum-1)+"行，缺少\""+alphabets[i] +"\"\n");
                    inputSymbols.add(inputIndex,i);
                    stateStack.clear();
                    stateStack.add(0);
                    parserSymbols.clear();
                    answer.clear();
                    rowNum = 1;
                    offset += 1;
                    inputIndex = 0;
                    next = inputSymbols.get(inputIndex);
                    addToAnswer(inputIndex);
                    break;
                }
            }

        }
        answer.add("program => \n");
        printAnswer();
    }

    /**
     * this is the main method
     * @param args
     */
    public static void main(String[] args) {
        analysis();
    }
}
