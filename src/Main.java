import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args){

        String filePath = "E:\\Term 5\\Language & Machine Theory\\Normalized\\Grammar.txt";
        Greibach greibach = new Greibach(filePath);
        Chomsky chomsky = new Chomsky(filePath);
        LinkedList<char[]> newGrammar1 = greibach.cf_to_greibach();
        LinkedList<char[]> newGrammar2 = chomsky.cf_to_chomsky();
        System.out.println("new grammar is:");
        greibach.printGrebachGrammar();
        chomsky.printComskyGrammar();
        System.out.println("******");


    }

}

