package com.kalata.peter.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.kalata.peter.bakingapp.common.utils.AssetsUtils;
import com.kalata.peter.bakingapp.data.local.entity.RecipeEntity;
import com.kalata.peter.bakingapp.ui.detail.RecipeDetailActivity;

import org.junit.Rule;
import org.junit.Test;
import org.parceler.Parcels;

import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class RecipeDetailTest {

    private static final String EXTRA_RECIPE = "args_recipe";

    private List<RecipeEntity> recipes;

    @Rule
    public ActivityTestRule<RecipeDetailActivity> activityTestRule =
            new ActivityTestRule<RecipeDetailActivity>(RecipeDetailActivity.class) {
                @Override
                protected Intent getActivityIntent() {
                    Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
                    recipes = AssetsUtils.getRecipes();
                    Intent result = new Intent(targetContext, RecipeDetailActivity.class);
                    result.putExtra(EXTRA_RECIPE, Parcels.wrap(recipes.get(0)));
                    return result;
                }
            };


    @Test
    public void clickOnRecyclerViewItem_OpensStepDetailActivity() {

        onView(withId(R.id.rv_steps))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.tv_title))
                .check(matches(isDisplayed()));
    }

}

