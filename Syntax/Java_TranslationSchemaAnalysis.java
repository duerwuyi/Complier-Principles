import java.util.*;

import static java.lang.Character.*;

public class Java_TranslationSchemaAnalysis
{
    public static class CompilerException  extends Exception{
        String message;
        int line;
        CompilerException(String message ,int line){
            this.line = line;
            this.message = message;
        }

        public String toString(){
            return "error message:line "+ line+ "," + message + "\n";
        }
    }
    public static String input = new String();

    public static class Alphabet{
        public int id;
        public boolean isTerminal;
        public String name;
        Alphabet(int id,boolean isTerminal,String name){
            this.id = id;
            this.isTerminal = isTerminal;
            this.name = name;
        }
    }
    public static Alphabet[] alphabets = new Alphabet[]{
            new Alphabet(0,true,"{"),
            new Alphabet(1,true,"}"),
            new Alphabet(2,true,"if"),
            new Alphabet(3,true,"("),
            new Alphabet(4,true,")"),
            new Alphabet(5,true,"then"),
            new Alphabet(6,true,"else"),
            new Alphabet(7,true,"int"),
            new Alphabet(8,true,"real"),
            new Alphabet(9,true,"="),
            new Alphabet(10,true,";"),
            new Alphabet(11,true,"<"),
            new Alphabet(12,true,">"),
            new Alphabet(13,true,"<="),
            new Alphabet(14,true,">="),
            new Alphabet(15,true,"=="),
            new Alphabet(16,true,"+"),
            new Alphabet(17,true,"-"),
            new Alphabet(18,true,"*"),
            new Alphabet(19,true,"/"),
            new Alphabet(20,true,"ID"),
            new Alphabet(21,true,"INTNUM"),
            new Alphabet(22,true,"REALNUM"),
            new Alphabet(23,true,"E"),
            new Alphabet(24,true,"\n"),
            new Alphabet(25,true,"$"),
            new Alphabet(26,false,"program"),
            new Alphabet(27,false,"decls"),
            new Alphabet(28,false,"compoundstmt"),
            new Alphabet(29,false,"decl"),
            new Alphabet(30,false,"stmt"),
            new Alphabet(31,false,"ifstmt"),
            new Alphabet(32,false,"assgstmt"),
            new Alphabet(33,false,"stmts"),
            new Alphabet(34,false,"boolexpr"),
            new Alphabet(35,false,"arithexpr"),
            new Alphabet(36,false,"boolop"),
            new Alphabet(37,false,"multexpr"),
            new Alphabet(38,false,"arithexprprime"),
            new Alphabet(39,false,"simpleexpr"),
            new Alphabet(40,false,"multexprprime"),
            new Alphabet(41,false,"Action"),
    };


    /**
     *  this method is to read the standard input
     */
    private static void read_prog()
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

    public static class Symbol {
        public int id;//identical with the alphabets
        public String name;
        public int lineNumber;
        Symbol(int id, String name, int lineNumber){
            this.id = id;
            this.name = name;
            this.lineNumber = lineNumber;
        }
    }

    public static ArrayList<Symbol> inputSymbols =new ArrayList<>();

    public static void lexicalAnalysis() throws CompilerException {
        int line = 1;
        for(String trial : input.split(" ")){
            if(trial.equals("")){
                continue;
            }
            if(trial.equals("\n")){
                line++;
                continue;
            }
            // reserved word
            boolean flag = false;
            for(Alphabet a : alphabets){
                if(a.isTerminal && a.name.equals(trial)){
                    inputSymbols.add(new Symbol(a.id, a.name, line));
                    flag = true;
                    break;
                }
            }
            if(flag){
                continue;
            }
            if(isLowerCase(trial.charAt(0)) || isUpperCase(trial.charAt(0))){
                inputSymbols.add(new Symbol(20, trial, line));
                continue;
            } else if (isDigit(trial.charAt(0))) {
                boolean isReal = false;
                for(int i = 0;i<trial.length();i++){
                    if(!isDigit(trial.charAt(i)) && trial.charAt(i) != '.'){
                        throw new CompilerException("unexpected symbol:"+trial,line);
                    }
                    else if(trial.charAt(i) == '.' && i != trial.length()-1){
                        isReal = true;
                    }
                    else if(trial.charAt(i) == '.' && i == trial.length()-1){
                        throw new CompilerException("unexpected symbol:"+trial,line);
                    }
                }
                if(isReal){
                    inputSymbols.add(new Symbol(22, trial, line));
                }
                else{
                    inputSymbols.add(new Symbol(21, trial, line));
                }
                continue;
            }
            throw new CompilerException("unexpected symbol:"+trial,line);
        }
    }

    public static class Variable{
        boolean isInteger;
        double realValue;
        int intValue;
        Variable(int intValue){
            this.realValue = 0;
            this.isInteger = true;
            this.intValue = intValue;
        }

        Variable(double realValue){
            this.realValue = realValue;
            this.isInteger = false;
            this.intValue = 0;
        }
    }

    public static boolean expectSymbol(int id){
        // check and update next symbol
        if(id == inputSymbols.get(inputPos).id){
            inputPos++;
            return true;
        }
        return false;
    }

    public static boolean retrieveSymbol(int id){
        return id == inputSymbols.get(inputPos).id;
    }

    public static HashMap<String, Variable> variableMap = new HashMap<>();

    public static void variableDecline() throws CompilerException {
        while(inputSymbols.get(inputPos).id != 0){
            boolean isInteger;
            String name;
            // int or real
            if(expectSymbol(7)){
                isInteger = true;
            }else if(expectSymbol(8)){
                isInteger =false;
            }else{
                throw new CompilerException("unexpected symbol:" + inputSymbols.get(inputPos).name
                        ,inputSymbols.get(inputPos).lineNumber);
            }
            // ID
            if(retrieveSymbol(20)){
                name = inputSymbols.get(inputPos).name;
                inputPos++;
            }else{
                throw new CompilerException("unexpected symbol:" + inputSymbols.get(inputPos).name
                        ,inputSymbols.get(inputPos).lineNumber);
            }
            // =
            if(!expectSymbol(9)){
                throw new CompilerException("unexpected symbol:" + inputSymbols.get(inputPos).name
                        ,inputSymbols.get(inputPos).lineNumber);
            }
            // NUM
            if(isInteger && retrieveSymbol(21)){
                int value = Integer.parseInt(inputSymbols.get(inputPos).name);
                inputPos++;
                variableMap.put(name,new Variable(value));
            }
            else if(!isInteger && retrieveSymbol(22)){
                double value = Double.parseDouble(inputSymbols.get(inputPos).name);
                inputPos++;
                variableMap.put(name,new Variable(value));
            }else if(!isInteger && retrieveSymbol(21)){
                throw new CompilerException("intnum can not be translated into real type"
                        ,inputSymbols.get(inputPos).lineNumber);
            }else if(isInteger && retrieveSymbol(22)){
                throw new CompilerException("realnum can not be translated into int type"
                        ,inputSymbols.get(inputPos).lineNumber);
            }else{
                throw new CompilerException("unexpected symbol:" + inputSymbols.get(inputPos).name
                        ,inputSymbols.get(inputPos).lineNumber);
            }
            // ;
            if(!expectSymbol(10)){
                throw new CompilerException("\";\" is needed"
                        ,inputSymbols.get(inputPos).lineNumber);
            }
        }
    }

    public static boolean computeBool(int head,int tail) throws CompilerException {
        int boolOp = 0;
        for(int i = head;i<tail;i++){
            if(inputSymbols.get(i).id <=15 && inputSymbols.get(i).id >=11){
                boolOp = i;
            }
        }
        int left= computeint(head, boolOp);
        int right = computeint(boolOp+1 , tail-1);
        switch (inputSymbols.get(boolOp).id){
            case 11:
                return left<right;
            case 12:
                return left>right;
            case 13:
                return left<=right;
            case 14:
                return left>=right;
            case 15:
                return left==right;
        }
        throw new CompilerException("miss bool operation",inputSymbols.get(head).lineNumber);
    }

    public static boolean notSuperiorTo(char a,char b){
        if ((a =='*' || a =='/') && (b =='+' || b =='-'))
            return false;
        else
            return true;
    }

    private static class computeUnit{
        public int intValue;
        public double realValue;
        public boolean isNum;
        public char symbol;// 0 +,1 -,2 *,3 /,4 (,5 )

        computeUnit(int intValue){
            this.intValue=intValue;
            isNum = true;
        }

        computeUnit(double realValue){
            this.realValue=realValue;
            isNum = true;
        }

        computeUnit(char symbol){
            this.symbol = symbol;
            isNum = false;
        }
    }

    public static int computeint(int head,int tail) throws CompilerException {
        int i = head;
        List<computeUnit> computeList = new ArrayList<>();
        Stack<Character> symbolStack = new Stack<>();
        while(i != tail){
            switch (inputSymbols.get(i).id){
                case 20:
                    computeList.add(new computeUnit(variableMap.get(inputSymbols.get(i).name).intValue));
                    break;
                case 21:
                    computeList.add(new computeUnit(Integer.parseInt(inputSymbols.get(i).name)));
                    break;
                case 3:
                    symbolStack.add('(');
                    break;
                case 4:
                    while (symbolStack.peek()!='('){
                        computeList.add(new computeUnit(symbolStack.pop()));
                    }
                    symbolStack.pop();
                    break;
                default:
                    while(!symbolStack.empty()
                            && notSuperiorTo(inputSymbols.get(i).name.charAt(0),symbolStack.peek())){
                        computeList.add(new computeUnit(symbolStack.pop()));
                    }
                    symbolStack.add(inputSymbols.get(i).name.charAt(0));
            }
            i++;
        }
        while (!symbolStack.empty()){
            computeList.add(new computeUnit(symbolStack.pop()));
        }
        //compute poland expressions
        Stack<Integer> answerStack = new Stack<>();
        for(computeUnit token: computeList){
            int ans = 0;
            if(!token.isNum){
                switch (token.symbol) {
                    case '+' :
                        ans = answerStack.pop() + answerStack.pop();
                        break;
                    case '-' :
                        ans = -answerStack.pop() + answerStack.pop();
                        break;
                    case '*' :
                        ans = answerStack.pop() * answerStack.pop();
                        break;
                    case '/' :
                        int divisor = answerStack.pop();
                        int dividend = answerStack.pop();
                        if (dividend == 0) {
                            throw new CompilerException("error message:line 5,division by zero",
                                    inputSymbols.get(head).lineNumber);
                        }
                        ans = dividend / divisor;
                        break;
                }
                answerStack.push(ans);
            }else{
                answerStack.push(token.intValue);
            }
        }
        return answerStack.pop();
    }

    public static double computedouble(int head,int tail) throws CompilerException {
        int i = head;
        List<computeUnit> computeList = new ArrayList<>();
        Stack<Character> symbolStack = new Stack<>();
        while(i != tail){
            switch (inputSymbols.get(i).id){
                case 20:
                    if(variableMap.get(inputSymbols.get(i).name).isInteger){
                        computeList.add(new computeUnit((double) variableMap.get(inputSymbols.get(i).name).intValue));
                    }else{
                        computeList.add(new computeUnit(variableMap.get(inputSymbols.get(i).name).realValue));
                    }

                    break;
                case 21:
                case 22:
                    computeList.add(new computeUnit(Double.parseDouble(inputSymbols.get(i).name)));
                    break;
                case 3:
                    symbolStack.add('(');
                    break;
                case 4:
                    while (symbolStack.peek()!='('){
                        computeList.add(new computeUnit(symbolStack.pop()));
                    }
                    symbolStack.pop();
                    break;
                default:
                    while(!symbolStack.empty()
                            && notSuperiorTo(inputSymbols.get(i).name.charAt(0),symbolStack.peek())){
                        computeList.add(new computeUnit(symbolStack.pop()));
                    }
                    symbolStack.add(inputSymbols.get(i).name.charAt(0));
            }
            i++;
        }
        while (!symbolStack.empty()){
            computeList.add(new computeUnit(symbolStack.pop()));
        }
        //compute poland expressions
        Stack<Double> answerStack = new Stack<>();
        for(computeUnit token: computeList){
            double ans = 0;
            if(!token.isNum){
                switch (token.symbol) {
                    case '+':
                        ans = answerStack.pop() + answerStack.pop();
                        break;
                    case '-':
                        ans = -answerStack.pop() + answerStack.pop();
                        break;
                    case '*' :
                        ans = answerStack.pop() * answerStack.pop();
                        break;
                    case '/' :
                        double divisor = answerStack.pop();
                        double dividend = answerStack.pop();
                        if (dividend == 0) {
                            throw new CompilerException("error message:line 5,division by zero",
                                    inputSymbols.get(head).lineNumber);
                        }
                        ans = dividend / divisor;
                        break;
                }
                answerStack.push(ans);
            }else{
                answerStack.push(token.realValue);
            }
        }
        return answerStack.pop();
    }

    public static void excute(int head, int tail) throws CompilerException {
        int pos = head;
        Variable target = variableMap.get(inputSymbols.get(pos).name);
        if(target == null) throw new CompilerException(
                "unexpected variable "+inputSymbols.get(pos).name,
                inputSymbols.get(pos).lineNumber);
        pos ++;
        if(inputSymbols.get(pos).id != 9){
            throw new CompilerException(
                    "missed symbol \"=\"",
                    inputSymbols.get(pos).lineNumber);
        }
        pos ++;
        if(target.isInteger){
            target.intValue = computeint(pos, tail);
        }else{
            target.realValue = computedouble(pos, tail);
        }
    }

    public static void LL1Analysis() throws CompilerException{
        int compoundDepth = 0;
        while(!retrieveSymbol(25)){
            switch (inputSymbols.get(inputPos).id){
                //'{' ->compoundstmt
                case 0:
                    compoundDepth ++;
                    inputPos++;
                    break;
                case 1:
                    compoundDepth --;
                    inputPos++;
                    break;
                case 2:
                    int thenPos = inputPos+1;
                    while (inputSymbols.get(thenPos).id!=5){
                        thenPos++;
                    }
                    int elsePos = thenPos+1;
                    while (inputSymbols.get(elsePos).id!=6){
                        elsePos++;
                    }
                    int nextPos=elsePos+1;
                    if(retrieveSymbol(0)){
                        while (inputSymbols.get(nextPos).id!=1){
                            nextPos++;
                        }
                    }else{
                        while (inputSymbols.get(nextPos).id!=10){
                            nextPos++;
                        }
                    }
                    inputPos++;
                    try {
                        if(computeBool(inputPos,thenPos)){
                            excute(thenPos+1,elsePos-1);
                        }else{
                            excute(elsePos+1,nextPos);
                        }
                    }catch (CompilerException e){
                        System.out.print(e);
                    }
                    inputPos = nextPos + 1;
                    break;
                case 20:
                    int next = inputPos+1;
                    while (inputSymbols.get(next).id!=10){
                        next++;
                    }
                    excute(inputPos,next);
                    inputPos = next + 1;
                    break;
            }
        }


    }

    public static int inputPos = 0;

    private static void analysis()
    {
        read_prog();
        //lexicalAnalysis
        try{
            lexicalAnalysis();
        }
        catch (CompilerException e) {
            System.out.print(e);
        }
        //type check and variable decline
        try{
            variableDecline();
        }
        catch (CompilerException e) {
            System.out.print(e);
            System.out.print("error message:line 5,division by zero\n");
            return;
        }
        // LL1Analysis
        try {
            LL1Analysis();
            for(String key : variableMap.keySet()){
                if(variableMap.get(key).isInteger){
                    System.out.print(key+": "+variableMap.get(key).intValue+"\n");
                }else{
                    System.out.print(key+": "+variableMap.get(key).realValue+"\n");
                }
            }
        }catch (CompilerException e) {
            System.out.print(e);
        }

    }

    /**
     * this is the main method
     * @param args
     */
    public static void main(String[] args) {
        analysis();
    }
}
