package com.example.csun_compt;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.*;

import java.net.URL;
import java.util.ArrayList;

public class ModelDatabase {

    ArrayList<Database> data = new ArrayList<Database>() {{
        add(new Database(0,R.drawable.meme1,""));
        add(new Database(1,R.drawable.meme2,""));
        add(new Database(2,R.drawable.meme3,""));
        add(new Database(3,R.drawable.meme4,""));
        add(new Database(4,R.drawable.meme5,""));
    }};

    public Integer[] getall ()
    {
        Integer[] location = new Integer[4];
        for (int i = 0; i < 4 ; i++)
        {
            location[i] = getimg(i);
        }
        return location;
    }

    public int getimg (int id)
    {
        Database getel = data.get(id);
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
        Database getel = data.get(id);
        String disc = getel.discription;
        return disc;
    }

    public void setdisc (int id, String disc)
    {
        Database getel = data.get(id);
        if (getel.discription == "")
        {
            getel.discription = disc;
        } else
        {
            getel.discription = getel.discription + "," + disc;
        }
    }

}
