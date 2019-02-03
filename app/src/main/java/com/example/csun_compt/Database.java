package com.example.csun_compt;

public class Database {
    int id;
    Object memedir;
    String discription;

    Database(int id, String url, String discription)
    {
        this.id = id;
        this.memedir = url;
        this.discription = discription;
    }

    Database(int id, Integer hardcode, String discription)
    {
        this.id = id;
        this.memedir = hardcode;
        this.discription = discription;
    }


}
