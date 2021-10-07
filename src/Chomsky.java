import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;

public class Chomsky {
    private String filePath;

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public int lineNumber(String filePath){
        int linesNumber = 0;
        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader reader = new BufferedReader(fileReader);
            while (reader.readLine() != null)
                linesNumber ++;
            fileReader.close();
            reader.close();

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return linesNumber;
    }
    public LinkedList<String> readFile(String filePath){
        int linesNumber = lineNumber(filePath);
        LinkedList<String> lines = new LinkedList<>();
        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader reader = new BufferedReader(fileReader);
            for (int i = 0 ; i < linesNumber ; i++)
                lines.add(reader.readLine());
            fileReader.close();
            reader.close();

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return lines;
    }
    public LinkedList<char[]> cf_to_chomsky()
    {
        LinkedList<String> cfGrammar = readFile(filePath);
        LinkedList<char[]> chomsky = new LinkedList<>();
        LinkedList<Character> var = new LinkedList<>();
        LinkedList<char[]> terminal = new LinkedList<>();
        for(int i = 65 ; i < 91 ; i++)
            var.add(((char) i));
        for (int i = 0 ; i < cfGrammar.size() ; i++) {// devide to small laws
            char[] law = cfGrammar.get(i).toCharArray();
            char[] s = new char[law.length];
            s[0] = law[0];
            var.remove(law[0]-65);
            int k = 1;
            for (int j = 1 ; j < law.length ; j++){
                if(law[j] != '-' && law[j] != '>' && law[j] != ' ' && law[j] != '|'){
                    s[k] = law[j];
                    k++;
                }
                if((j != law.length-1 && law[j+1] == '|') || j == law.length-1){
                    chomsky.add(s);
                    k=1;
                    s = new char[law.length];
                    s[0] = law[0];
                }
            }
        }
        for(int i = 0 ; i < chomsky.size() ; i++)
        {
            if((chomsky.get(i)[1] > 96 && chomsky.get(i)[1] < 123 && chomsky.get(i).length == 1 ) ||
                    (chomsky.get(i)[1] > 64 && chomsky.get(i)[1] < 91 && chomsky.get(i)[2] > 64 && chomsky.get(i)[2] < 91 && chomsky.get(i).length == 2))
                continue;
            else
            {
                for(int j = 1 ; j < chomsky.get(i).length ; j++)
                {
                    if(chomsky.get(i)[j] > 96 &&  chomsky.get(i)[j] < 123) // lower case
                    {
                        boolean isterm = false;
                        for(int k = 0 ; k < terminal.size() ; k++){
                            if(terminal.get(k)[1] == chomsky.get(i)[j]) {
                                chomsky.get(i)[j] = terminal.get(k)[0];
                                isterm = true;
                            }
                        }
                        if(isterm == false){
                            char[] newS = new char[2];
                            newS[1] = chomsky.get(i)[j];
                            newS[0] = var.get(0);
                            var.remove(0);
                            terminal.add(newS);
                            chomsky.get(i)[j] = newS[0];
                        }
                    }
                }
                int l = chomsky.get(i).length;
                int m = chomsky.get(i).length;
                for(int j = 1 ; j < l-1 ; j++)
                {
                    char[] news = new char[3];
                    news[1]  = chomsky.get(i)[1];
                    news[2]  = chomsky.get(i)[2];
                    news[0] = var.get(0);
                    var.remove(0);
                    terminal.add(news);
                    chomsky.get(i)[1]=news[0];
                    for(int k = 3 ; k < m ; k++)
                    {
                        chomsky.get(i)[k-1]=chomsky.get(i)[k];
                    }
                    m--;
                }
            }
        }
        for (int i = 0 ; i < terminal.size() ; i++)
            chomsky.add(terminal.get(i));

        return chomsky;
    }

    public void printComskyGrammar(){
        LinkedList<char[]> chomsky = cf_to_chomsky();
        for (int i =0 ; i < chomsky.size() ; i++)
            System.out.println(chomsky.get(i));
    }

    public Chomsky(String filePath) {
        setFilePath(filePath);
    }
}
