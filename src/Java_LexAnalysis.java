import java.util.Scanner;

public class Java_LexAnalysis
{
    private static StringBuffer prog = new StringBuffer();

    /**
     *  this method is to read the standard input
     */
    private static void read_prog()
    {
        Scanner sc = new Scanner(System.in);
        while(sc.hasNextLine())
        {
            prog.append(sc.nextLine());
        }
    }


    // add your method here!!



    /**
     *  you should add some code in this method to achieve this lab
     */
    private static void analysis()
    {
        read_prog();
        System.out.print(prog);
    }

    /**
     * this is the main method
     * @param args
     */
    public static void main(String[] args) {
        analysis();
    }
}
