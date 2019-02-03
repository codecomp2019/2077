package com.example.csun_compt;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.*;
import java.io.File;
import java.io.FileInputStream;
import 	java.io.BufferedReader;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class model_database {

    ArrayList<database> data = new ArrayList<database>() {{
        add(new database(0,R.drawable.meme1,""));
        add(new database(1,R.drawable.meme2,""));
        add(new database(2,R.drawable.meme3,""));
        add(new database(3,R.drawable.meme4,""));
        add(new database(4,R.drawable.meme5,""));
    }};


    public void WriteBtn(Context context, String s) {
        // add-write text into file
        try {
            FileOutputStream fileout=context.openFileOutput("database.txt", MODE_PRIVATE);
            OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
            outputWriter.write(s);
            outputWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ReadBtn(Context context) {
        //reading text from file
        try {
            FileInputStream fileIn=context.openFileInput("database.txt");
            InputStreamReader InputRead= new InputStreamReader(fileIn);

            char[] inputBuffer= new char[100];
            String s="";
            int charRead;

            while ((charRead=InputRead.read(inputBuffer))>0) {
                // char to string conversion
                String readstring=String.copyValueOf(inputBuffer,0,charRead);
                s +=readstring;
            }
            InputRead.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getimg (int id)
    {
        database getel = data.get(id);
        int imgnum = getel.memedir;
        return imgnum;
    }

    public Bitmap getimg (String url)
    {
        Bitmap bmp = null;

        try {
            URL geturl = new URL(url);
            try {
                bmp = BitmapFactory.decodeStream(geturl.openConnection().getInputStream());

            } catch (IOException e)
            {
                e.printStackTrace();
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return bmp;

    }

    public String getdis (int id)
    {
        database getel = data.get(id);
        String disc = getel.discription;
        return disc;
    }

    public void setdisc (int id, String disc)
    {
        database getel = data.get(id);
        if (getel.discription == "")
        {
            getel.discription = disc;
        } else
        {
            getel.discription = getel.discription + "," + disc;
        }
    }

//    public static void main (String[] args)
//    {
//        model_database a = new model_database();
//
//    }


}
