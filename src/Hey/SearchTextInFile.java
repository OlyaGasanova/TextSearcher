package Hey;

import java.io.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Scanner;
import java.util.concurrent.Exchanger;

import Hey.Settings;

public class SearchTextInFile implements Runnable
{
    public String currentpath;
    public String currentquery;
    public Exchanger<String> exchanger;
    String message;

    public static void Search() throws IOException
    {
        // First write a file, with large number of entries
        writeFile(Settings.directory);

        long scannerSearchMillis = 0;
        long brSearchMillis = 0;

        int iterations = 5;

        // Now search random strings five times, and see the time taken
        for (int i = 0; i < iterations; i++)
        {
            String msisdn = String.valueOf(923000000000l + ((long) (Math.random() * 40000000)));

            System.out.println("ITERATION " + i);
            System.out.print("Search " + msisdn + " using scanner");
            Date d1 = new Date();
            searchUsingScanner("C:/New.txt", msisdn);
            Date d2 = new Date();

            long millis = (d2.getTime() - d1.getTime());
            scannerSearchMillis += millis;
            System.out.println(" | " + (millis / 1000) + " Seconds");
            System.out.println("==================================================================");
            System.out.print("Search " + msisdn + " using buffered reader");
            d1 = new Date();
            searchUsingBufferedReader("C:/New.txt", msisdn);
            d2 = new Date();
            millis = d2.getTime() - d1.getTime();
            brSearchMillis += millis;
            System.out.println(" | " + (millis / 1000) + " Seconds");
            System.out.println("==================================================================");
            System.out.println("==================================================================");
            System.out.println("==================================================================");
            System.out.println("==================================================================");
        }

        System.out.println("Average Search time using Scanner " + (scannerSearchMillis / (iterations * 1000.0)) + " Seconds");
        System.out.println("Average Search time using BufferedReader " + (brSearchMillis / (iterations * 1000.0)) + " Seconds");
    }

    public static void writeFile(String path)
    {
        BufferedWriter csvWriter = null;
        HashSet<Integer> additions = new HashSet<Integer>();
        try
        {
            csvWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path)));

            for (int i = 0; i < 40000000; i++)
            {
                int addition = (int) (Math.random() * 40000000);
                additions.add(addition);

                if (i % 20000 == 0)
                {
                    System.out.println("Entries written : " + i + " ------ Unique Entries: " + additions.size());
                    csvWriter.flush();
                }

                long msisdn = 923000000000l + addition;
                csvWriter.write(String.valueOf(msisdn) + "|" + String.valueOf((int) (Math.random() * 131)) + "\r\n");
            }

            csvWriter.flush();

            System.out.println("Unique Entries written : " + additions.size());
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally
        {
            if (csvWriter != null)
            {
                try
                {
                    csvWriter.close();
                }
                catch (IOException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    public static String searchUsingScanner(String filePath, String searchQuery) throws FileNotFoundException
    {
        searchQuery = searchQuery.trim();
        Scanner scanner = null;
        try
        {
            scanner = new Scanner(new File(filePath));
            while (scanner.hasNextLine())
            {
                String line = scanner.nextLine();
                if (line.contains(searchQuery))
                {
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
                if (scanner != null)
                    scanner.close();
            }
            catch (Exception e)
            {
                System.err.println("Exception while closing scanner " + e.toString());
            }
        }

        return null;
    }

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
            System.out.println("PutThread получил: " + message);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}