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
    LineNumberReader lr =null;
    String Hello="";
    int newpage=0;
    boolean nextpage=false;
    boolean previouspage=false;
    private int countnext=0;
    private int countprev=0;
    int currentposition=0;
    ArrayList<String> matches;
    private ArrayList positionmatches = new ArrayList();
    public String currentpath;
    private boolean firsttime=true;
    public boolean next =true;
    private BufferedReader savedreader=null;
    public static ArrayList<String> myText=new ArrayList<String>();
    private void SaveIt(){
        System.out.println("shit");
    }
    private int currentpage = 0;
    ArrayList pages =new ArrayList();

    public String NextPage()throws IOException{

        boolean somethingFind = true;
        pages.add(0);
        Hello = "";
        System.out.println("Next Page");
        boolean flag = false;
        char[] cbuf= new char[1];
        try {

            //currentposition=(int)positionmatches.get(positionmatches.size()-1);
          if (currentpage<=currentposition)
            currentpage=currentposition;
            //for (int countsecond=0; countsecond<positionmatches.size()-1;countsecond++) {
            //    System.out.println();
             //   if (currentpage>(int)positionmatches.get(countsecond)&&currentpage<=(int)positionmatches.get(countsecond+1))
            //        currentpage=(int)positionmatches.get(countsecond);
           // }
            FileInputStream i = new FileInputStream(currentpath);

            br = new BufferedReader(new InputStreamReader(i));
            br.skip(currentpage);
            String line="";
            int count = 0;
            myText = new ArrayList<String>();


            while ((br.read(cbuf,0,1)) !=-1) {


                currentpage++;

                line=line+cbuf[0];
                System.out.println(cbuf[0]);
                if (cbuf[0]=='\n') {
                    line+="<br>";
                    myText.add(line);
                    line="";
                }
                if (myText.size() > 21) {
                    return null;

                    }

                   // return line;
                }


        } finally {

            if (br.read(cbuf,0,1) ==-1) fireEndofFile();
            Hello = "";
            for (String part : myText
                    ) {
                Hello += part;
                pages.add(currentpage);

            }
            fireFirstTextAdded(Hello);
            //System.out.println(Hello);
            br.close();

        }

        return null;


    }


    public String PreviousPage()throws IOException{

        boolean somethingFind = true;
        Hello = "";
        System.out.println("Previous Page");
        boolean flag = false;
        char[] cbuf= new char[1];
        try {

            currentposition=(int)positionmatches.get(positionmatches.size()-1);
           // if (currentpage>currentposition)
                currentpage=(int)pages.get(pages.indexOf(currentpage)-2);
                pages.remove(pages.size()-1);
             //   else
            //if (currentpage<=currentposition)
             //   currentpage=currentposition;
            //for (int countsecond=0; countsecond<positionmatches.size()-1;countsecond++) {
            //    System.out.println();
            //   if (currentpage>(int)positionmatches.get(countsecond)&&currentpage<=(int)positionmatches.get(countsecond+1))
            //        currentpage=(int)positionmatches.get(countsecond);
            // }
            FileInputStream i = new FileInputStream(currentpath);

            br = new BufferedReader(new InputStreamReader(i));
            br.skip(currentpage);
            String line="";
            int count = 0;
            myText = new ArrayList<String>();


            while ((br.read(cbuf,0,1)) !=-1) {


                currentpage++;

                line=line+cbuf[0];
                System.out.println(cbuf[0]);
                if (cbuf[0]=='\n') {
                    line+="<br>";
                    myText.add(line);
                    line="";
                }
                if (myText.size() > 21) {
                    return null;

                }

                // return line;
            }


        } finally {
            if (br.read(cbuf,0,1) ==-1) fireEndofFile();
            Hello = "";
            for (String part : myText
                    ) {
                Hello += part;

            }
            fireFirstTextAdded(Hello);
            //System.out.println(Hello);
            br.close();

        }

        return null;


    }


    public String NextPenetration() throws IOException {

       // if (countprev==0){
        //br.mark(1000);
        boolean somethingFind = false;
        Hello = "";

        System.out.println("Следущиииииииий");
        boolean flag = false;
        try {

            //lr = new LineNumberReader(new InputStreamReader(new FileInputStream(currentpath)));

            FileInputStream i = new FileInputStream(currentpath);

           // i.getChannel().position((long)positionmatches.get(positionmatches.size()-1));
           // i.getChannel().position(20);
            currentposition=(int)positionmatches.get(positionmatches.size()-1);
            //lr.skip(currentposition);
            //currentposition=(int)positionmatches.get(positionmatches.size()-1);
            br = new BufferedReader(new InputStreamReader(i));
            br.skip(currentposition);
            //br = new BufferedReader(new InputStreamReader(i));
            //positionmatches.add(i.getChannel().position());
            System.out.println(positionmatches.toString()+"    это что-то новое, сейчас начинаем читать с "+ currentposition);
            String line="";
            String searchQuery = Settings.request;
            searchQuery = searchQuery.trim();
            int count = 0;

            //BufferedReader clone = new BufferedReader(br);
            //readersList.add(clone);
            myText = new ArrayList<String>();
            //lr.setLineNumber(currentposition);
            char[] cbuf= new char[1];

            while ((br.read(cbuf,0,1)) !=-1) {


                currentposition++;

                //System.out.println(cbuf[0]);
                line=line+cbuf[0];
                if (line.contains(searchQuery))
                {
                    somethingFind = true;
                    //positionmatches.add(currentposition);
                    somethingFind=true;
                    if(count==0&&flag==false){ flag=true;
                        line="<b>"+line+"</b>";
                        positionmatches.add(currentposition);
                    }

                }
                if (cbuf[0]=='\n') {
                    line+="<br>";
                    myText.add(line);
                    line="";
                    if (flag == true) count++;
                }
                if (myText.size() > 21) myText.remove(0);

                if (count == 10) {
                        Hello = "";
                        for (String part : myText
                                ) {
                            Hello += part;

                        }
                        fireFirstTextAdded(Hello);
                        flag = false;
                        return line;
                }
                //if ((br.read(cbuf,0,1)) == 0) return null;
            }

            if (flag == true) {
                Hello = "";
                for (String part : myText
                        ) {
                    Hello += part;
                }
                fireFirstTextAdded(Hello);
                flag = false;
            }
        } finally {
            currentpage=currentposition;
            pages.add(currentposition);
            if (!somethingFind) fireEndofFile();
            System.out.println(somethingFind);
            br.close();

        }

        return null;

    }



    public String PreviousPenetration() throws IOException{

        boolean somethingFind=false;
        try {
            //br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
            currentposition = (int) positionmatches.get(positionmatches.size()-3);
            br = new BufferedReader(new InputStreamReader(new FileInputStream(currentpath)));
            positionmatches.remove(positionmatches.size()-1);

            positionmatches.trimToSize();
            String line="";
            System.out.println("Предыдущий");
            String searchQuery = Settings.request;
            searchQuery = searchQuery.trim();
            int count = 0;
            boolean flag = false;
            char[] cbuf= new char[1];
            br.skip(currentposition);
            myText = new ArrayList<String>();
            System.out.println(positionmatches.toString()+"    это что-то новое, сейчас начинаем читать с "+ currentposition);
            while ((br.read(cbuf,0,1)) != -1) {
                currentposition++;

                //System.out.println(cbuf[0]);
                line=line+cbuf[0];
                if (line.contains(searchQuery))
                {
                    somethingFind=true;

                    if(count==0&&flag==false){ flag=true;
                        line="<b>"+line+"</b>";
                        //positionmatches.add(currentposition);
                    }

                }
                if (cbuf[0]=='\n') {
                    line+="<br>";
                    myText.add(line);
                    line="";
                    if (flag == true) count++;
                }
                if (myText.size() > 21) myText.remove(0);

                if (count == 10) {
                   // positionmatches.add(currentposition);
                    Hello = "";
                    for (String part : myText
                            ) {
                        Hello += part;

                    }
                    fireFirstTextAdded(Hello);
                    flag = false;

                    return line;
                }
            }
            if (flag == true) {
                Hello = "";
                for (String part : myText
                        ) {
                    Hello += part;
                }
                fireFirstTextAdded(Hello);
                flag = false;
            }
        } finally {
            pages.remove(pages.size()-1);

            if (!somethingFind) fireEndofFile();
            br.close();


        }
        // }

        // else {
       // fireFirstTextAdded(matches.get(matches.size()-countprev));
       // countprev--;
        //   }
        return null;

    }


    public String searchUsingBufferedReader(String filePath, String searchQuery) throws IOException
    {
        searchQuery = searchQuery.trim();
        br = null;
        Hello = "";
        boolean flag=false;
        String line="";
        pages.add(0);
        try
        {

           FileInputStream i=new FileInputStream(filePath);
           // positionmatches.add(i.getChannel().position());
            positionmatches.clear();
            positionmatches.add(0);
            br = new BufferedReader(new InputStreamReader(i));
            currentpath=filePath;
            int count=0;
            //lr.lines().skip(4);
            //br.lines().skip(4);
            System.out.println("Первый раз");
            myText=new ArrayList<String>();
            char[] cbuf=new char[1];

            while ((br.read(cbuf,0,1)) !=-1)
            {
                newpage++;
                currentposition++;

                //System.out.println(cbuf[0]);
                line=line+cbuf[0];
                if (line.contains(searchQuery))
                {
                    //positionmatches.add(currentposition);


                    line = line.replaceAll(searchQuery, "<b>"+searchQuery+"</b>");
                   if(count==0&& flag==false){
                       flag=true;
                   positionmatches.add(currentposition);
                   System.out.print(line+"\n прочитано "+currentposition);
                   //myText.add(line);
                   //line="";
                   }

                }

                if (cbuf[0]=='\n') {
                    if (newpage>=200){pages.add(currentposition); newpage=0;}
                    line+="<br>";
                    myText.add(line);
                    line="";
                    if (flag == true) count++;
                }
                if (myText.size()>21) myText.remove(0);
                if (count==10||currentposition==i.getChannel().position()) {
                    Hello="";
                    for (String part:myText
                         ) { Hello+=part;

                    }
                    fireFirstTextAdded(Hello);
                    flag=false;
                    return line;
                }
                if (currentposition==i.getChannel().position()) return null;
            }
            if (flag == true) {
                Hello = "";
                for (String part : myText
                        ) {
                    Hello += part;
                }
                fireFirstTextAdded(Hello);
                flag = false;
            }
        }
        finally
        {


            line+="<br>";
            currentpage=currentposition;
            pages.add(currentposition);
            myText.add(line);
            line="";
            Hello="";
            System.out.println("this situation");
            for (String part:myText
                    ) { Hello+=part;

            }
            fireFirstTextAdded(Hello);
            flag=false;

           // System.out.println(matches.toString());
            try
            {
                br.close();
                System.out.println("this situation");
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
