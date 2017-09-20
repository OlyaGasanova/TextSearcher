package Hey;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 20.09.2017.
 */
public class FileNavigator {


    public static ArrayList<String> myText=new ArrayList<String>();

    public static String searchUsingBufferedReader(String filePath, String searchQuery) throws IOException
    {
        searchQuery = searchQuery.trim();
        BufferedReader br = null;

        System.out.print(filePath);

        try
        {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
            String line;
            int count=0;
            boolean flag=false;
            while ((line = br.readLine()) != null)
            {
                myText.add(line);
                if (line.contains(searchQuery))
                {
                    System.out.print(line+"\n");
                   flag=true;

                }
                else
                {
                }
                if (myText.size()>11) myText.remove(0);
                if (flag==true) count++;
                if (count==5) {
                    System.out.println(myText.toString());
                    return line;
                }
            }
        }
        finally
        {
            try
            {
                if (br != null)
                    br.close();
            }
            catch (Exception e)
            {
                System.err.println("Exception while closing bufferedreader " + e.toString());
            }
        }

        return null;
    }
}
