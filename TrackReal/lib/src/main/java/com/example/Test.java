package com.example;

import org.json.JSONArray;
import org.json.JSONObject;

public class Test {
    public static void main(String[] args) {
        JSONArray categoriesArray = new JSONArray();
        JSONObject jsonObject= new JSONObject();

        JSONObject insideJsonArrayObject = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();

        jsonObject1.put("name", "fashon");
        jsonObject1.put("id", "328");
        jsonObject1.put("name1", "fashon");
        jsonObject1.put("id1", "328");

        for (int i = 0; i < 5; i++) {
            insideJsonArrayObject.put("category"+i, jsonObject1);
        }

        categoriesArray.put(insideJsonArrayObject);

        jsonObject.put("categories",categoriesArray);

        System.out.println(jsonObject);
    }
}
