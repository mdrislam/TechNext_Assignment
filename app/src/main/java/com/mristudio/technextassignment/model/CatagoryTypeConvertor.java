package com.mristudio.technextassignment.model;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class CatagoryTypeConvertor {

    @TypeConverter
    public String fromCatagoriList(List<String> catagori) {
        if (catagori == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<String>>() {}.getType();
        String json = gson.toJson(catagori, type);
        return json;
    }

    @TypeConverter
    public List<String> toCatagoriList(String catagori) {
        if (catagori == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<String>>() {}.getType();
        List<String> catagoriList = gson.fromJson(catagori, type);
        return catagoriList;
    }
}
