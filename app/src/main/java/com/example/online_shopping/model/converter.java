package com.example.online_shopping.model;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class converter {
    @TypeConverter
    public static ArrayList<CategoryModel> fromString(String valuecategory) {
        Type listType = new TypeToken<ArrayList<CategoryModel>>() {}.getType();
        return new Gson().fromJson(valuecategory, listType);
    }

    @TypeConverter
    public static String fromArrayList(ArrayList<CategoryModel> list) {
        return new Gson().toJson(list);

    }

    @TypeConverter
    public static ArrayList<product> fromStringproduct(String valueproduct) {
        Type listType = new TypeToken<ArrayList<product>>() {}.getType();
        return new Gson().fromJson(valueproduct, listType);
    }

    @TypeConverter
    public static String fromArrayListprodcut(ArrayList<product> list) {
        return new Gson().toJson(list);

    }
}
