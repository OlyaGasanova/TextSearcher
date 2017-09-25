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
    boolean firstmathing = true;
    private boolean firsttime=true;
    public boolean next =false;

    int currentposition=0;
    int currentpenetration=0;
    int currentpage = 0;
    int newpage=0;
    ArrayList positionmatches = new ArrayList();
    ArrayList pages =new ArrayList();


    public String currentpath;
    public static ArrayList<String> myText=new ArrayList<String>();

   public String Read(int position, String mode) throws IOException{

       br=null;
       Hello="";
       myText=new ArrayList<String>();
       char[] cbuf=new char[1];


       boolean findmatch = false;
       String line="";

       newpage=0;




       try {
           br = new BufferedReader(new InputStreamReader(new FileInputStream(currentpath)));
           br.skip(position);

           while ((br.read(cbuf,0,1))!=-1)
           {
               newpage++;
               currentposition++;
               line=line+cbuf[0];

               if (line.contains(searchQuery)&&(!findmatch))
               {
                   if (!positionmatches.contains(currentposition)){
                       positionmatches.add(currentposition);
                   }
                   if((mode=="find")&&(currentposition>currentpenetration)&&(!findmatch)) {
                       line = line.replaceAll(searchQuery, "<b>"+searchQuery+"</b>");
                       currentpenetration=currentposition;
                       findmatch=true;
                   }
                   if (firsttime) findmatch=true;
                   myText.add(line);
                   line="";

               }

               if (cbuf[0]=='\n') {

                   line+="<br>";
                   myText.add(line);
                   line="";

                   if (newpage>=200)
                   {
                       if (!pages.contains(currentposition))pages.add(currentposition);
                       newpage=0;
                       currentpage=currentposition;
                       line+="<br>";
                       //for (String part:myText
                       //        ) { Hello+=part;

                       //}
                       if (((mode=="find")&&findmatch)||((mode=="first")&&(firsttime)&&(findmatch))||(mode=="another")) break;
                       else myText.clear();
                   }
               }


           }

       }
       finally {

           currentpage=currentposition;
           if (!pages.contains(currentposition))pages.add(currentposition);
           if (line!="") myText.add(line);
           if(!myText.isEmpty()) {
               //line+="<br>";
               myText.add(line);
               for (String part : myText
                       ) {
                   Hello += part;

               }
               return Hello;
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

    public String NextPage()throws IOException{
        currentposition=currentpage;
        String result = Read(currentpage, "another");
        if (result!=null) fireFirstTextAdded(result);
        else fireEndofFile();
        return null;
    }


    public String PreviousPage()throws IOException{

        if (pages.indexOf(currentpage)>1)
        {

            currentpage=(int)pages.get(pages.indexOf(currentpage)-2);
            currentposition=currentpage;
            String result = Read(currentpage, "another");
            fireFirstTextAdded(result);
        }
        else fireEndofFile();
        return null;


    }



    public String NextPenetration() throws IOException {

        int countpage = pages.size() - 1;
        int tempmatch = currentpenetration;
        int sparepage = currentpage;
        String result = "";


        if (currentpenetration == 0) {
            while (currentpenetration < (int) pages.get(countpage))
                countpage--;
            currentpage = (int) pages.get(countpage);
            currentposition = currentpage;
            result = Read(currentpage, "find");
        } else{
            while (currentpenetration < (int) pages.get(countpage))
                countpage--;
            currentpage = (int) pages.get(countpage);
            currentposition = currentpage;
            result = Read(currentpage, "find");
            while (currentpenetration == tempmatch) {
                result = Read(currentpage, "find");
                if (result == null) {
                    fireEndofFile();
                    currentpage = sparepage;
                    currentposition = sparepage;
                    return null;
                }
            }
        }
        fireFirstTextAdded(result);
        return null;

    }



    public String PreviousPenetration() throws IOException{

       if ((currentpenetration==0)||(positionmatches.indexOf(currentpenetration)==0)) {
           fireEndofFile();
           return null;
       }
       if (positionmatches.indexOf(currentpenetration)==1) currentpenetration=0;
       else currentpenetration=(int)positionmatches.get(positionmatches.indexOf(currentpenetration)-2);

        NextPenetration();

        return null;

    }


    public String searchUsingBufferedReader(String filePath, String Query) throws IOException
    {
        pages.add(0);
        currentpath=filePath;
        searchQuery = Query.trim();
        String result="";
        firsttime = true;
        while (positionmatches.isEmpty()) {
            if (firsttime) {
                Read(currentposition, "first");
                firsttime=false;
            }
        }
        currentposition=0;
        currentpage=0;
        result = Read(currentposition, "another");
        fireFirstTextAdded(result);
        currentpenetration=0;
        return null;
    }

    @Override
    public void run() {
        try {
        if (previouspage) {PreviousPage(); previouspage=false;}
        else
        if (nextpage){ NextPage();nextpage=false;}
             else{
                if (firsttime) {
                    searchUsingBufferedReader(this.currentpath, Settings.request);
                    firsttime = false;
                } else {
                    if (next) {
                        NextPenetration();
                        next=false;
                    } else PreviousPenetration();
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
