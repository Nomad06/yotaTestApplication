package projects.bubalex.ru.webpagecodeviewer;

import android.os.AsyncTask;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class CodeGetter extends AsyncTask<String, Void, String>
{
    int lineCount;
    TextView webPageCodeViewer;

    CodeGetter(TextView textView)
    {
        webPageCodeViewer = textView;
    }

    @Override
    protected String doInBackground(String... strings)
    {
        String pageAddress = strings[0];
        lineCount = 0;
        StringBuilder sb = new StringBuilder();
        HttpURLConnection connection = null;

        try {

            connection = (HttpURLConnection) new URL(pageAddress).openConnection();

            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                lineCount = codeReading(lineCount, reader, sb);
                //codeView(lineCount, sb);
            }
            else
            {
                System.out.println("Failed with error: " + connection.getResponseCode() + " " + connection.getResponseMessage());
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            connection.disconnect();
        }
        return sb.toString();
    }

    @Override
    protected void onPostExecute(String s)
    {

        if(lineCount == 1)
        {
            String[] stringList = s.split("<");
            for (String curString:stringList)
            {
                webPageCodeViewer.append(curString);
                webPageCodeViewer.append("\n");
            }
        }
        else if(lineCount > 1)
        {
            webPageCodeViewer.setText(s);
        }



    }

    private static int codeReading(int lineCount, BufferedReader reader, StringBuilder sb) throws IOException
    {
        String line;
        while ((line = reader.readLine()) != null)
        {
            lineCount++;
            sb.append(line);
            sb.append("\n");
        }
        return lineCount;
    }



}
