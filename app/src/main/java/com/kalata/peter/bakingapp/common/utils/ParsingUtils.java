package com.kalata.peter.bakingapp.common.utils;

import android.content.Context;

import com.kalata.peter.bakingapp.App;
import com.kalata.peter.bakingapp.R;
import com.kalata.peter.bakingapp.data.local.entity.IngredientEntity;

import java.util.List;
import java.util.Locale;

public class ParsingUtils {

    public static String getIngredientString(List<IngredientEntity> ingredients) {

        StringBuilder result = new StringBuilder();

        for (IngredientEntity ingredient : ingredients) {

            String name = ingredient.getIngredient();
            String quantity = ingredient.getQuantity();
            String measure = ingredient.getMeasure();

            String format = App.getAppContext().getString(R.string.ingredient_line);
            result.append(String.format(format, name, quantity, measure));
            result.append("\n");
        }
        return result.toString();
    }


}
