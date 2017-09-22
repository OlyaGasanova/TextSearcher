package Hey;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import static  Hey.MyControl.fireFirstTextAdded;
import static  Hey.MyControl.fireEndofFile;
import Hey.Settings;

/**
 * Created by user on 20.09.2017.
 */
public class FileNavigator implements Runnable{

    BufferedReader br = null;
    String Hello="";
    String searchQuery="";

    boolean nextpage=false;
    boolean previouspage=false;
    private boolean firsttime=true;
    public boolean next =true;

    int currentposition=0;
    int currentpenetration=0;
    int currentpage = 0;
    int newpage=0;
    ArrayList positionmatches = new ArrayList();
    ArrayList pages =new ArrayList();


    public String currentpath;
    public static ArrayList<String> myText=new ArrayList<String>();

    public String NextPage()throws IOException{

        String searchQuery = Settings.request;
        br=null;
        Hello="";
        myText=new ArrayList<String>();

        char[] cbuf=new char[1];
        boolean somethibgFind=false;

        String line="";

        System.out.println("Следующая страница");

        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(currentpath)));
            br.skip(currentpage);
            while ((br.read(cbuf,0,1))!=-1)
            {
                somethibgFind=true;
                newpage++;
                line=line+cbuf[0];
                currentposition++;


                if (line.contains(searchQuery))
                {
                    positionmatches.add(currentposition);
                    myText.add(line);
                    line="";
                }

                if (cbuf[0]=='\n') {

                    line+="<br>";
                    myText.add(line);
                    line="";

                    if (newpage>=200)
                    {
                        newpage=0;
                        pages.add(currentposition);
                        return null;
                    }
                }


            }

        }
        finally {


            if (!somethibgFind)
            {
                fireEndofFile();

            }
            else {
                if (br.read(cbuf,0,1)==-1) {
                    pages.add(currentposition);
                    newpage=0;
                }
                System.out.println(pages.toString());

                line += "<br>";
                myText.add(line);
                for (String part : myText
                        ) {
                    Hello += part;

                }
                currentpage = currentposition;
                fireFirstTextAdded(Hello);
            }

            try
            {
                br.close();
            }
            catch (Exception e)
            {
                System.err.println("Exception while closing bufferedreader " + e.toString());
            }
        }
        return null;
    }


    public String PreviousPage()throws IOException{

        if (currentpage==(int)pages.get(1)) {
            fireEndofFile();
            return null;
        }

        currentpage=(int)pages.get(pages.size()-3);
        currentposition=currentpage;
        pages.remove(pages.size()-1);

        String searchQuery = Settings.request;
        br=null;
        Hello="";
        myText=new ArrayList<String>();

        char[] cbuf=new char[1];

        String line="";
        System.out.println(pages.toString()+" "+currentpage);
        System.out.println("Предыдущая страница");

        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(currentpath)));
            br.skip(currentpage);
            while ((br.read(cbuf,0,1))!=-1)
            {
                newpage++;
                line=line+cbuf[0];
                currentposition++;


                if (cbuf[0]=='\n') {

                    line+="<br>";
                    myText.add(line);
                    line="";

                    if (newpage>=200)
                    {
                        newpage=0;
                        return null;
                    }
                }


            }

        }
        finally {

            line+="<br>";
            myText.add(line);
            for (String part:myText
                    ) { Hello+=part;

            }
            currentpage=currentposition;
            fireFirstTextAdded(Hello);



            try
            {
                br.close();
            }
            catch (Exception e)
            {
                System.err.println("Exception while closing bufferedreader " + e.toString());
            }
        }


        return null;


    }



    public String NextPenetration() throws IOException {

        boolean flag=false;
        int temppenetr = currentpenetration;

        if (currentpenetration<(int)positionmatches.get(positionmatches.size()-1)) {
            flag=false;
            int countpen = 0;
            int countpag = pages.size() - 1;
            //найти мин пенетрейшн>кьюррент
            while (currentpenetration >= (int) positionmatches.get(countpen))
                countpen++;
            //найти макс пэйдж< мин петерейшн
            while ((int) positionmatches.get(countpen) < (int) pages.get(countpag)) {
                //pages.remove(countpag);
                countpag--;
            }
            currentpage = (int) pages.get(countpag);
            currentposition=currentpage;
        }

        else
        {
            flag=true;
            currentpage=(int)pages.get(pages.size()-1);
            currentposition=currentpage;
        }
            //считать её, выделив вхождение на этой позиции

            String searchQuery = Settings.request;
            br=null;
            Hello="";
            myText=new ArrayList<String>();
            currentposition=currentpage;
            char[] cbuf=new char[1];
            boolean somethingfind=false;

            String line="";
            try {
                System.out.println("Чтение");
                br = new BufferedReader(new InputStreamReader(new FileInputStream(currentpath)));
                br.skip(currentpage);
                newpage=0;
                while ((br.read(cbuf,0,1))!=-1)
                {
                    line=line+cbuf[0];
                    currentposition++;
                    newpage++;
                    if (line.contains(searchQuery))
                    {
                        if (!positionmatches.contains(currentposition)) positionmatches.add(currentposition);
                        if (currentposition==(int)positionmatches.get(positionmatches.indexOf(temppenetr)+1)) {
                            currentpenetration=currentposition;
                            line = line.replaceAll(searchQuery, "<b>"+searchQuery+"</b>");
                            somethingfind=true;
                        }
                        myText.add(line);
                        line="";
                    }

                    if (cbuf[0]=='\n') {

                        line+="<br>";
                        myText.add(line);
                        line="";

                        if (newpage>=200)
                        {
                            newpage=0;
                            if (!pages.contains(currentposition)) pages.add(currentposition);
                            return null;
                        }
                    }
                }
            }
            catch (IndexOutOfBoundsException ex){
                fireEndofFile();
            }
            finally {

                if (somethingfind){
                    line += "<br>";
                    myText.add(line);
                    for (String part : myText
                            ) {
                        Hello += part;
                    }
                    System.out.println(pages.toString() + positionmatches.toString());
                    currentpage = currentposition;
                    fireFirstTextAdded(Hello);
                }
                try
                {
                    br.close();
                }
                catch (Exception e)
                {
                    System.err.println("Exception while closing bufferedreader " + e.toString());
                }
            }
        return null;

    }



    public String PreviousPenetration() throws IOException{

        boolean somethingFind=false;
        try {
            System.out.println(currentpenetration);
            currentpenetration = (int) positionmatches.get(positionmatches.indexOf(currentpenetration) - 2);
            System.out.println(currentpenetration);
            NextPenetration();
        }
        finally {

        }

        return null;

    }


    public String searchUsingBufferedReader(String filePath, String Query) throws IOException
    {

        searchQuery = Query.trim();
        br=null;
        Hello="";
        myText=new ArrayList<String>();

        char[] cbuf=new char[1];

        String line="";
        currentpenetration=0;
        pages.add(0);
        positionmatches.clear();
        currentpath=filePath;
        System.out.println("Первый раз");

        try {
            FileInputStream i =new FileInputStream(filePath); //Нужно ли оно??
            br = new BufferedReader(new InputStreamReader(i));

            while ((br.read(cbuf,0,1))!=-1)
            {
                newpage++;
                currentposition++;
                line=line+cbuf[0];

                if (line.contains(searchQuery))
                {
                    //line = line.replaceAll(searchQuery, "<b>"+searchQuery+"</b>");
                    positionmatches.add(currentposition);
                    myText.add(line);
                    line="";
                }

                if (cbuf[0]=='\n') {

                    line+="<br>";
                    myText.add(line);
                    line="";

                    if (newpage>=200)
                    {
                        pages.add(currentposition);
                        newpage=0;
                        return null;
                    }
                }


            }

        }
        finally {

            currentpage=currentposition;
            line+="<br>";
            myText.add(line);
            for (String part:myText
                    ) { Hello+=part;

            }
            fireFirstTextAdded(Hello);


            try
            {
                br.close();
            }
            catch (Exception e)
            {
                System.err.println("Exception while closing bufferedreader " + e.toString());
            }
        }



        return null;
    }

    @Override
    public void run() {
        System.out.println("Новый поток навигатора");
        try {
        if (previouspage) {PreviousPage(); previouspage=false;System.out.println("previous page");}
        else
        if (nextpage){ NextPage();nextpage=false;System.out.println("nextpage");}
             else{
                if (firsttime) {
                    searchUsingBufferedReader(this.currentpath, Settings.request);
                    firsttime = false;
                } else {
                    if (next) {
                        NextPenetration();
                    } else PreviousPenetration();
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
