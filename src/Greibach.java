import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;

public class Greibach {
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

    public LinkedList<char[]> cf_to_greibach()
    {
        LinkedList<String> cfGrammar = readFile(filePath);
        LinkedList<char[]> greibach = new LinkedList<>();
        LinkedList<Character> v = new LinkedList<>();
        LinkedList<char[]> terminal = new LinkedList<>();
        for(int i = 65 ; i < 91 ; i++)
            v.add(((char) i));
        for (int i = 0 ; i < cfGrammar.size() ; i++) {// devide to small laws
            char[] law = cfGrammar.get(i).toCharArray();
            char[] s = new char[law.length];
            s[0] = law[0];
            v.remove(law[0]-65);
            int k = 1;
            for (int j = 1 ; j < law.length ; j++){
                if(law[j] != '-' && law[j] != '>' && law[j] != ' ' && law[j] != '|'){
                    s[k] = law[j];
                    k++;
                }
                if((j != law.length-1 && law[j+1] == '|') || j == law.length-1){
                    greibach.add(s);
                    k=1;
                    s = new char[law.length];
                    s[0] = law[0];
                }
            }
        }

        for(int i = 0 ; i < greibach.size() ; i++){
            if(greibach.get(i)[1] > 96 &&  greibach.get(i)[1] < 123) {//lower case
                for(int j = 2 ; j < greibach.get(i).length ; j++){
                    if(greibach.get(i)[j] > 64 && greibach.get(i)[j] < 91)//upper case
                        continue;
                    else if(greibach.get(i)[j] > 96 &&  greibach.get(i)[j] < 123){
                        boolean find = false;
                        for(int k = 0 ; k < terminal.size() ; k++){
                            if(terminal.get(k)[1] == greibach.get(i)[j]) {
                                greibach.get(i)[j] = terminal.get(k)[0];
                                find = true;
                            }
                        }
                        if(find == false){
                            char[] newS = new char[2];
                            newS[1] = greibach.get(i)[j];
                            newS[0] = v.get(0);
                            v.remove(0);
                            terminal.add(newS);
                            greibach.get(i)[j] = newS[0];
                        }

                    }
                }
            }
            else if(greibach.get(i)[1] > 64 && greibach.get(i)[1] < 91){//upper case
                for (int j = 0 ; j < greibach.size() ; j++){
                    char[] news = new char[greibach.get(i).length + greibach.get(j).length];
                    news[0]  = greibach.get(i)[0];
                    int count = 1;
                    if(greibach.get(j)[0] == greibach.get(i)[1]){
                        for (int k = 1 ; k < greibach.get(j).length ; k++){
                            if((greibach.get(j)[k] > 64 && greibach.get(j)[k] < 91) || (greibach.get(j)[k] > 96 &&  greibach.get(j)[k] < 123) || greibach.get(j)[k] == 'λ'){
                                news[count] = greibach.get(j)[k];
                                count++;
                            }
                            else break;
                        }
                        for (int k = 2 ; k < greibach.get(i).length ; k++){
                            if((greibach.get(i)[k] > 64 && greibach.get(i)[k] < 91) || (greibach.get(i)[k] > 96 &&  greibach.get(i)[k] < 123)) {
                                if(news[count-1] != 'λ') {
                                    news[count] = greibach.get(i)[k];
                                    count++;
                                }
                                else{
                                    count--;
                                    news[count] = greibach.get(i)[k];
                                    count++;
                                }
                            }
                        }
                        greibach.add(news);
                    }
                }
                greibach.remove(i);
                i--;
            }
        }
        for (int i = 0 ; i < terminal.size() ; i++)
            greibach.add(terminal.get(i));

        return greibach;
    }

    public void printGrebachGrammar(){
        LinkedList<char[]> greibach = cf_to_greibach();
        for (int i =0 ; i < greibach.size() ; i++)
            System.out.println(greibach.get(i));
    }

    public Greibach(String filePath) {
        setFilePath(filePath);
    }
}
/*
A-> cAf|λ|bbb
S-> aSb|aSbb|Aa
N-> aha|F
F-> λ
*/