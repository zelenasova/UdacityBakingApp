package com.kalata.peter.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import com.kalata.peter.bakingapp.R;
import com.kalata.peter.bakingapp.common.utils.ParsingUtils;
import com.kalata.peter.bakingapp.data.local.entity.IngredientEntity;
import com.kalata.peter.bakingapp.data.local.entity.RecipeEntity;
import com.kalata.peter.bakingapp.ui.detail.RecipeDetailActivity;
import com.kalata.peter.bakingapp.ui.main.MainActivity;

import org.parceler.Parcels;

import java.util.List;

import static com.kalata.peter.bakingapp.ui.detail.RecipeDetailActivity.EXTRA_RECIPE;

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidgetProvider extends AppWidgetProvider {

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                       int appWidgetId, RecipeEntity recipe) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);

        views.setTextViewText(R.id.tv_title, recipe.getName());
        views.setTextViewText(R.id.tv_ingredients, ParsingUtils.getIngredientString(recipe.getIngredients()));


        Intent intent = new Intent(context, RecipeDetailActivity.class);
        intent.setPackage(context.getPackageName());
        intent.putExtra(EXTRA_RECIPE, Parcels.wrap(recipe));
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.ll_widget, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

}

