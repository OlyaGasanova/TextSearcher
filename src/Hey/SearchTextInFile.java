package Hey;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Exchanger;

public class SearchTextInFile implements Runnable
{
    public String currentpath;
    public String currentquery;
    public Exchanger<String> exchanger;
    String message;

    public static String searchUsingBufferedReader(String filePath, String searchQuery) throws IOException
    {
        searchQuery = searchQuery.trim();
        BufferedReader br = null;

        System.out.print(filePath);

        try
        {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
            String line;
            while ((line = br.readLine()) != null)
            {
                if (line.contains(searchQuery))
                {
                    System.out.print(line+"\n");
                    return line;
                }
                else
                {
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

    @Override
    public void run() {
        try {

            message=searchUsingBufferedReader(currentpath,currentquery);
            message=exchanger.exchange(message);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}