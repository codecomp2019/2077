import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class getJSON {

    private String getJSONFromURLConnection(String urlString) {

        BufferedReader reader = null;
        HttpURLConnection urlConnection;

        try {
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();

            String apikey = "AIzaSyBftSSjlA0jAt3hz8fDg3-Qu5NT4F3FpME";
            urlConnection.setRequestMethod("GET");
            urlConnection.addRequestProperty("Authorization", "Bearer " + apikey);
            urlConnection.setRequestProperty("Accept", "application/json");

            if (urlConnection.getResponseCode() == 200) {
                InputStream stream = urlConnection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();

                String line;

                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                return buffer.toString();
            } else {
                Log.e("Error", "Error response code: " +
                        urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }
        return null;
    }

    public void getJson() {
        String json = getJSONFromURLConnection("https://api.clashofclans.com/v1/clans?name=illuminati");
        if (json != null) {
            Log.i("JSON", json.toString());
        }
    }

}