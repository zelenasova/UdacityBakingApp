package com.kalata.peter.bakingapp.common.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.kalata.peter.bakingapp.App;
import com.kalata.peter.bakingapp.data.local.entity.RecipeEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AssetsUtils {

    private static final String RECIPE_PATH = "recipes.json";

    private static BufferedReader getJsonString(final String assetPath) {
        try {
            InputStream is = App.getAppContext().getAssets().open(assetPath);
            int size = is.available();
            return new BufferedReader(new InputStreamReader(is), size);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<RecipeEntity> getRecipes() {
        Gson gson = new GsonBuilder().create();
        Type listType = new TypeToken<ArrayList<RecipeEntity>>() {}.getType();
        BufferedReader reader = getJsonString(RECIPE_PATH);
        List<RecipeEntity> recipes = null;
        if (reader != null) {
            recipes = gson.fromJson(reader, listType);
        }
        return recipes;
    }

}
