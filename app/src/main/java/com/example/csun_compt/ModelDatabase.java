package com.example.csun_compt;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.*;

import java.net.URL;
import java.util.ArrayList;

public class ModelDatabase {

    ArrayList<Database> data = new ArrayList<Database>() {{
        //add(new Database(0,"https://spectatorau.imgix.net/content/uploads/2017/08/Snip20170801_15.png?auto=compress,enhance,format&crop=faces,entropy,edges&fit=crop&w=820&h=550","Say Something"));
        add(new Database(0, R.drawable.meme1,"Man surprised with text that says when you realize twitter videos are 2:20 because that's 140 seconds" ));
        add(new Database(1, R.drawable.meme3,"Pikachu surprised"));
        add(new Database(2, R.drawable.meme2,""));
        add(new Database(3,"https://typeset-beta.imgix.net/lovelace/uploads/448/56fbeee0-67e5-0134-5ee6-06caa2286297.jpg",""));
        add(new Database(4,"https://www.google.com/url?sa=i&rct=j&q=&esrc=s&source=images&cd=&cad=rja&uact=8&ved=2ahUKEwiL7O7qp6DgAhWhsFQKHcfiC5IQjRx6BAgBEAU&url=https%3A%2F%2Fsalsaritas.com%2F2016%2F07%2F14%2F10-memes-real-youre-dating-taco-lover%2F&psig=AOvVaw0NWoy5Nj9zUE4PRKoYK6XL&ust=1549308966532825",""));
    }};

    public Object[] getall ()
    {
        Object[] location = new Object[5];
        for (int i = 0; i < 5 ; i++)
        {
            location[i] = getimg(i);
        }
        return location;
    }

    Object getimg (int id)
    {
        Database getel = data.get(id);
        Object imgnum = getel.memedir;
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
        getel.discription = disc;

    }

}
