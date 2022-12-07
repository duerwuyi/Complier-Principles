import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Character.*;


public class Java_LexAnalysis
{
    private static String input = new String("");
    private static ArrayList<String> answers = new ArrayList<>();
    private static final String[] keys = {"auto","break","case","char","const","continue","default","do","double",
            "else","enum","extern","float","for","goto","if","int","long", "register","return","short","signed",
            "sizeof","static","struct","switch","typedef","union","unsigned","void","volatile","while"};
    private static final String[] punctuations ={"-","--","-=","->","!","!=","%","%=","&","&&","&=","(",")","*","*=",",",
            ".","/","/=",":",";","?","[","]","^","^=","{","|","||","|=","}","~","+","++","+=","<","<<","<<=","<=","=",
            "==",">",">=",">>",">>="};

    /**
     *  this method is to read the standard input
     */
    private static void read_prog()
    {
        StringBuilder prog = new StringBuilder();
        Scanner sc = new Scanner(System.in);
        while(sc.hasNextLine())
        {
            prog.append('\n');
            prog.append(sc.nextLine());
        }
        // append two spaces and an Enter at the end
        prog.append("\n  ");
        input = prog.toString();
    }


    // add your method here!!



    /**
     *  you should add some code in this method to achieve this lab
     */
    private static void analysis()
    {
        read_prog();
        int maxLength = input.length();
        int pointer = 0;
        //scan the input
        while(pointer < maxLength){
            //trim
            try{
                //maybe there are spaces in the end and pointer grows out of bound
                while(input.charAt(pointer) == ' ' || input.charAt(pointer) == '\n'|| input.charAt(pointer) == '\t'){
                    pointer++;
                }
            }catch (Exception StringIndexOutOfBoundsException){
                break;
            }
            //letter
            if(isLowerCase(input.charAt(pointer)) || isUpperCase(input.charAt(pointer))){
                int first = pointer;
                //circulate till the end
                while(isLowerCase(input.charAt(pointer))){
                    pointer++;
                }
                // find out if it is a key
                String target = input.substring(first,pointer);
                int num = 1;
                boolean isKey = false;
                for(String key : keys){
                    if(target.equals(key)){
                        // eg.:"while()",here while is a key , "while123",here while is a variable name
                        if(!isDigit(input.charAt(pointer))){
                            isKey = true;
                            break;
                        }
                    }
                    num += 1;
                }
                // "while1234while123" is  a variable name
                while(isLowerCase(input.charAt(pointer))|| isUpperCase(input.charAt(pointer))
                        || isDigit(input.charAt(pointer))){
                    pointer++;
                }
                String ans;
                if(isKey){
                    ans = "<" + target +","+ num + ">";
                }else{
                    target = input.substring(first,pointer);
                    ans = "<" + target + ",81>";
                }
                answers.add(ans);
                continue;
            }
            // number
            else if(isDigit(input.charAt(pointer))){
                int first = pointer;
                //come to the end of digit
                while(isDigit(input.charAt(pointer))){
                    pointer++;
                }
                // decimal number
                if(input.charAt(pointer) == '.'){
                    pointer++;
                    while(isDigit(input.charAt(pointer))){
                        pointer++;
                    }
                }
                //append
                String ans = "<" + input.substring(first,pointer) + ",80>";
                answers.add(ans);
                continue;
            }
            //comments
            else if(input.charAt(pointer) == '/' && input.charAt(pointer + 1) =='/'){
                int first = pointer;
                while (input.charAt(pointer)!='\n'){
                    pointer++;
                }
                String ans = "<" + input.substring(first,pointer) + ",79>";
                answers.add(ans);
                continue;
            }
            else if(input.charAt(pointer) == '/' && input.charAt(pointer + 1) =='*'){
                int first = pointer;
                StringBuilder buffer = new StringBuilder();
                try{
                    while (input.charAt(pointer)!='*' || input.charAt(pointer + 1) !='/'){
                        if(input.charAt(pointer)!='\n'){
                            buffer.append(input.charAt(pointer));
                        }
                        pointer++;
                    }
                    pointer += 2;
                }catch(Exception StringIndexOutOfBoundsException){
                    System.out.print("WTF?\n");
                }
                String ans = "<" + buffer + "*/,79>";
                answers.add(ans);
                continue;
            }
            // ""
            else if (input.charAt(pointer) == '\"') {
                int first = pointer;
                pointer++;
                try{
                    while (input.charAt(pointer)!='\"'){
                        pointer++;
                    }
                }catch(Exception StringIndexOutOfBoundsException){
                    System.out.print("WTF?\n");
                }
                String ans = "<\",78>";
                answers.add(ans);
                ans = "<" + input.substring(first + 1,pointer) + ",81>";
                answers.add(ans);
                ans = "<\",78>";
                answers.add(ans);
                pointer++;
                continue;
            }

            //punctuation
            int num = 33;
            int targetNum = 33;
            String targetString = "";
            for(String punctuation : punctuations){
                //try to catch the target
                if(pointer+punctuation.length() >= maxLength) continue;
                String target = input.substring(pointer, pointer+punctuation.length());
                if(target.equals(punctuation)){
                    targetNum = num;
                    targetString = target;
                }
                num++;
            }
            pointer += targetString.length();
            if(!targetString.equals("")){
                String ans = "<" + targetString +","+ targetNum + ">";
                answers.add(ans);
                continue;
            }
            //

            //error
            System.out.print("WTF?\n");
        }
        int num = 1;
        for(String answer:answers){
            System.out.print(num+": "+answer+'\n');
            num++;
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
