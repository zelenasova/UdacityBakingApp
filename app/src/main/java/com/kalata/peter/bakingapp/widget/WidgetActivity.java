package com.kalata.peter.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatRadioButton;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.kalata.peter.bakingapp.App;
import com.kalata.peter.bakingapp.R;
import com.kalata.peter.bakingapp.data.local.entity.RecipeEntity;
import com.kalata.peter.bakingapp.ui.main.MainViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;

public class WidgetActivity extends AppCompatActivity {

    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;

    private int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    private WidgetViewModel widgetViewModel;
    private List<RecipeEntity> recipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget);
        ButterKnife.bind(this);
        setupViewModel();
        if (getIntent().getExtras() != null) {
            appWidgetId = getIntent().getExtras().getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);

            if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
                finish();
            }
        }
    }

    private void setupViewModel() {
        widgetViewModel = ViewModelProviders.of(this).get(WidgetViewModel.class);
        widgetViewModel.getRecipes().observe(this, recipes -> {
            this.recipes = recipes;
            populateRadioButtons();
        });
    }

    private void populateRadioButtons() {
        if (recipes.size() == 0) {
            Toast.makeText(this, getString(R.string.error_data), Toast.LENGTH_LONG).show();
            finish();
        }

        radioGroup.removeAllViews();

        for (RecipeEntity recipe: recipes) {
            AppCompatRadioButton button = new AppCompatRadioButton(this);
            button.setText(recipe.getName());
            radioGroup.addView(button);
        }

        if (radioGroup.getChildCount() > 0) {
            ((AppCompatRadioButton) radioGroup.getChildAt(0)).setChecked(true);
        }
    }

    @OnClick(R.id.btn_ok)
    public void onOkButtonClick() {

        int radioButtonID = radioGroup.getCheckedRadioButtonId();
        View radioButton = radioGroup.findViewById(radioButtonID);
        int index = radioGroup.indexOfChild(radioButton);
        RecipeEntity selectedRecipe = recipes.get(index);

        Context context = App.getAppContext();
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        BakingAppWidgetProvider.updateAppWidget(context, appWidgetManager, appWidgetId, selectedRecipe);

        Intent intent = new Intent();
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        setResult(RESULT_OK, intent);
        finish();
    }


}
