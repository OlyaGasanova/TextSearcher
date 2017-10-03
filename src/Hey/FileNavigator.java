package Hey;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static Hey.MyControl.fireEndofFile;
import static Hey.MyControl.fireFirstTextAdded;

/**
 * Created by user on 20.09.2017.
 */
public class FileNavigator implements Runnable{

    BufferedReader br = null;
    String resultText="";
    String searchQuery="";

    boolean nextPage=false;
    boolean previousPage=false;
    boolean firstmathing = true;
    private boolean firsttime=true;
    public boolean next =false;

    int currentPosition=0;
    int currentOccurrence=0;
    int currentPage = 0;
    int newPage=0;
    ArrayList PositionMatches = new ArrayList();
    ArrayList Pages =new ArrayList();


    public String currentpath;
    public static ArrayList<String> listText=new ArrayList<String>();

   public String Read(int Position, String mode) throws IOException{

       br=null;
       resultText="";
       listText=new ArrayList<String>();
       char[] cbuf=new char[1];
       boolean findmatch = false;
       String line="";

       newPage=0;

       try {
           br = new BufferedReader(new InputStreamReader(new FileInputStream(currentpath)));
           br.skip(Position);

           while ((br.read(cbuf,0,1))!=-1)
           {
               newPage++;
               currentPosition++;
               line=line+cbuf[0];

               if (line.contains(searchQuery)&&(!findmatch))
               {
                   if (!PositionMatches.contains(currentPosition)){
                       PositionMatches.add(currentPosition);
                   }
                   if((mode=="find")&&(currentPosition>currentOccurrence)&&(!findmatch)) {
                       line = line.replaceAll(searchQuery, "<b>"+searchQuery+"</b>");
                       currentOccurrence=currentPosition;
                       findmatch=true;
                   }
                   if (firsttime) findmatch=true;
                   listText.add(line);
                   line="";

               }

               if (cbuf[0]=='\n') {

                   line+="<br>";
                   listText.add(line);
                   line="";

                   if (newPage>=200)
                   {
                       if (!Pages.contains(currentPosition))Pages.add(currentPosition);
                       newPage=0;
                       currentPage=currentPosition;
                       line+="<br>";
                       //for (String part:listText
                       //        ) { resultText+=part;

                       //}
                       if (((mode=="find")&&findmatch)||((mode=="first")&&(firsttime)&&(findmatch))||(mode=="resultBox")) break;
                       else listText.clear();
                   }
               }


           }

       }
       finally {

           currentPage=currentPosition;
           if (!Pages.contains(currentPosition))Pages.add(currentPosition);
           if (line!="") listText.add(line);
           if(!listText.isEmpty()) {
               listText.add(line);
               for (String part : listText
                       ) {
                   resultText += part;

               }
               return resultText;
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
        currentPosition=currentPage;
        String result = Read(currentPage, "resultBox");
        if (result!=null) fireFirstTextAdded(result);
        else fireEndofFile();
        return null;
    }


    public String PreviousPage()throws IOException{

        if (Pages.indexOf(currentPage)>1)
        {

            currentPage=(int)Pages.get(Pages.indexOf(currentPage)-2);
            currentPosition=currentPage;
            String result = Read(currentPage, "resultBox");
            fireFirstTextAdded(result);
        }
        else fireEndofFile();
        return null;


    }



    public String NextOccurrence() throws IOException {

        int countPage = Pages.size() - 1;
        int tempmatch = currentOccurrence;
        int sparePage = currentPage;
        String result = "";


        if (currentOccurrence == 0) {
            while (currentOccurrence < (int) Pages.get(countPage))
                countPage--;
            currentPage = (int) Pages.get(countPage);
            currentPosition = currentPage;
            result = Read(currentPage, "find");
        } else{
            while (currentOccurrence < (int) Pages.get(countPage))
                countPage--;
            currentPage = (int) Pages.get(countPage);
            currentPosition = currentPage;
            result = Read(currentPage, "find");
            while (currentOccurrence == tempmatch) {
                result = Read(currentPage, "find");
                if (result == null) {
                    fireEndofFile();
                    currentPage = sparePage;
                    currentPosition = sparePage;
                    return null;
                }
            }
        }
        fireFirstTextAdded(result);
        return null;

    }



    public String PreviousOccurrence() throws IOException{

       if ((currentOccurrence==0)||(PositionMatches.indexOf(currentOccurrence)==0)) {
           fireEndofFile();
           return null;
       }
       if (PositionMatches.indexOf(currentOccurrence)==1) currentOccurrence=0;
       else currentOccurrence=(int)PositionMatches.get(PositionMatches.indexOf(currentOccurrence)-2);

        NextOccurrence();

        return null;

    }


    public String searchUsingBufferedReader(String filePath, String Query) throws IOException
    {
        Pages.add(0);
        currentpath=filePath;
        searchQuery = Query.trim();
        String result="";
        firsttime = true;
        while (PositionMatches.isEmpty()) {
            if (firsttime) {
                Read(currentPosition, "first");
                firsttime=false;
            }
        }
        currentPosition=0;
        currentPage=0;
        result = Read(currentPosition, "resultBox");
        fireFirstTextAdded(result);
        currentOccurrence=0;
        return null;
    }

    @Override
    public void run() {
        try {
        if (previousPage) {PreviousPage(); previousPage=false;}
        else
        if (nextPage){ NextPage();nextPage=false;}
             else{
                if (firsttime) {
                    searchUsingBufferedReader(this.currentpath, Settings.request);
                    firsttime = false;
                } else {
                    if (next) {
                        NextOccurrence();
                        next=false;
                    } else PreviousOccurrence();
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
