package com.example.csun_compt;


/**
 * Created by kyle on 2/2/19.
 */

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Locale;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

public class RestClient {

    private static final String BASE_URL = "https://vision.googleapis.com/v1/images:annotate?key=AIzaSyBftSSjlA0jAt3hz8fDg3-Qu5NT4F3FpME";

    private static AsyncHttpClient client = new AsyncHttpClient();
    private int MAX_DESCRIPTION = 3;
    private TextToSpeech tts;
    private MainActivity main;

    public RestClient(MainActivity m) {
        super();
        main = m;
    }

    private void _post(Context context, String url, StringEntity entity, AsyncHttpResponseHandler responseHandler) {
        client.post(context, getAbsoluteUrl(url), entity, "application/json", responseHandler);
    }

    public void post(String uri, final AppCompatActivity thing){
//        String uri = "https://spectatorau.imgix.net/content/uploads/2017/08/Snip20170801_15.png?auto=compress,enhance,format&crop=faces,entropy,edges&fit=crop&w=820&h=550";
        String ftrs = "\'features\': [{ \'type\': \'TEXT_DETECTION\', \'maxResults\': 3 }, { \'type\': \'LABEL_DETECTION\', \'maxResults\': 3 }]";
        String jsonStr = "{\'requests\':[{\'image\': {\'source\': {\'imageUri\':\'"+uri+"\'} }, "+ftrs+" } ] }";

        try{
            StringEntity entity = new StringEntity( jsonStr );

            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

            this._post(null, "", entity, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    // If the response is JSONObject instead of expected JSONArray
                    String results = "";
                    try {
                        JSONObject a = response.getJSONArray("responses").getJSONObject(0);
                        JSONArray labels = a.getJSONArray("labelAnnotations");
                        JSONArray texts = a.getJSONArray("textAnnotations");
                        int index = 0;
                        for (int i = 0; i < MAX_DESCRIPTION; i++){
                            if(index == 0){
                                results = results + "Context of the picture contains ";
                            }
                            results = results + labels.getJSONObject(i).get("description").toString() + ", ";
                            i++;
                            index++;
                        }
                        String text_description = texts.getJSONObject(0).get("description").toString();
                        if(text_description.toCharArray().length > 0){
                            results = results + "Images Says ";
                        }
                        results = results + texts.getJSONObject(0).get("description").toString();
                    }catch (Exception e){
                        System.out.println();
                    }
                    main.speak.out(main, results);
                    main.descriptionTextView.setText(results);
                    main.db.setdisc(main.currImage, results);
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                    // Pull out the first event on the public timeline
                    JSONObject firstEvent = new JSONObject();
                    String tweetText = "";
                    try {
                        firstEvent = (JSONObject) timeline.get(0);
                    }catch (Exception e) {
                        System.out.println();
                    }
                    try {
                        tweetText = firstEvent.toString();
                    }catch (Exception e) {
                        System.out.println();
                    }

                    // Do something with the response
//                    ObjectMapper mapper = new ObjectMapper();
//                    Map<String,Object> map = mapper.readValue(json, Map.class);
                    System.out.println(tweetText);
                }
            });
        }
        catch (Exception e) {

        }
    }

    public void postRawBitMap(String bitmp, final AppCompatActivity thing){
//        String uri = "https://spectatorau.imgix.net/content/uploads/2017/08/Snip20170801_15.png?auto=compress,enhance,format&crop=faces,entropy,edges&fit=crop&w=820&h=550";
        String ftrs = "\'features\': [{ \'type\': \'TEXT_DETECTION\', \'maxResults\': 3 }, { \'type\': \'LABEL_DETECTION\', \'maxResults\': 3 }]";
        String jsonStr = "{\'requests\':[{\'image\': {\'content\': \'"+bitmp+"\'}, "+ftrs+" } ] }";

        try{
            StringEntity entity = new StringEntity( jsonStr );

            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

            this._post(null, "", entity, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    // If the response is JSONObject instead of expected JSONArray
                    String results = "";
                    JSONArray texts = new JSONArray();
                    Log.d("", response.toString());
                    try {
                        JSONObject a = response.getJSONArray("responses").getJSONObject(0);
                        JSONArray labels = a.getJSONArray("labelAnnotations");
                        try {
                            texts = a.getJSONArray("textAnnotations");
                        }catch (Exception e){
                            Log.d("Fail", "Fail in onSucess.TextAnnotations");
                        }
                        int index = 0;
                        for (int i = 0; i < MAX_DESCRIPTION; i++){
                            if(index == 0){
                                results = results + "Context of the picture contains ";
                            }
                            results = results + labels.getJSONObject(i).get("description").toString() + ", ";
                            i++;
                            index++;
                        }
                        String text_description = "";
                        try {
                            text_description = texts.getJSONObject(0).get("description").toString();
                        }catch (Exception e){
                            Log.d("Fail", "Fail in onSucess.TextAnnotations.descriptions");
                        }
                        if(text_description.toCharArray().length > 0){
                            results = results + "Images Says ";
                        }
                        results = results + texts.getJSONObject(0).get("description").toString();
                    }catch (Exception e){
                        Log.d("FAIL", "****** FAIL *********");
                    }
                    main.speak.out(main, results);
                    main.descriptionTextView.setText(results);
                    main.db.setdisc(main.currImage, results);
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                    // Pull out the first event on the public timeline
                    JSONObject firstEvent = new JSONObject();
                    String tweetText = "";
                    try {
                        firstEvent = (JSONObject) timeline.get(0);
                    }catch (Exception e) {
                        System.out.println();
                    }
                    try {
                        tweetText = firstEvent.toString();
                    }catch (Exception e) {
                        System.out.println();
                    }

                    // Do something with the response
//                    ObjectMapper mapper = new ObjectMapper();
//                    Map<String,Object> map = mapper.readValue(json, Map.class);
                    System.out.println(tweetText);
                }
            });
        }
        catch (Exception e) {

        }
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}
