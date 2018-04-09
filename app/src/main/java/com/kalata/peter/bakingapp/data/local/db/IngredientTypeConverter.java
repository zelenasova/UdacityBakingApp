package com.kalata.peter.bakingapp.data.local.db;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kalata.peter.bakingapp.data.local.entity.IngredientEntity;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class IngredientTypeConverter {

    @TypeConverter
    public static List<IngredientEntity> stringOrderList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<IngredientEntity>>() {}.getType();

        return new Gson().fromJson(data, listType);
    }

    @TypeConverter
    public static String OrderListToString(List<IngredientEntity> someObjects) {
        return new Gson().toJson(someObjects);
    }
}
