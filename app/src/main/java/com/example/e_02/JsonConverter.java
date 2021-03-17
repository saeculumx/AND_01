package com.example.e_02;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;

public class JsonConverter {
    Gson gson = new Gson();
    //Users users
    public String toJson(Object o)
    {
        return gson.toJson(o);
    }
    public String fromJsonArrayList(String json)
    {
        return null;
       // return gson.fromJson(json,)
    }
}
