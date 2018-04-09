package com.kalata.peter.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import com.kalata.peter.bakingapp.common.utils.AssetsUtils;
import com.kalata.peter.bakingapp.data.local.entity.RecipeEntity;
import com.kalata.peter.bakingapp.ui.detail.RecipeDetailActivity;
import com.kalata.peter.bakingapp.ui.detail.StepListFragment;
import com.kalata.peter.bakingapp.ui.main.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.parceler.Parcels;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;


@RunWith(AndroidJUnit4.class)
public class RecipeDetailIntentTest {

    private static final String EXTRA_POSITION = "args_position";
    private static final int EXTRA_POSITION_VALUE = 2;
    private static final String EXTRA_RECIPE = "args_recipe";

    @Rule
    public IntentsTestRule<RecipeDetailActivity> intentsTestRule =
            new IntentsTestRule<>(RecipeDetailActivity.class, true, false);

    @Test
    public void clickOnRecyclerViewItem_runsRecipeDetailsActivityIntent() {

        Context targetContext = InstrumentationRegistry.getInstrumentation()
                .getTargetContext();

        RecipeEntity recipe = AssetsUtils.getRecipes().get(0);
        Intent intent = new Intent(targetContext, RecipeDetailActivity.class);
        intent.putExtra(EXTRA_RECIPE, Parcels.wrap(recipe));
        intentsTestRule.launchActivity(intent);

        onView(withId(R.id.rv_steps))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));

        intended(
                hasExtra(EXTRA_POSITION, EXTRA_POSITION_VALUE)
        );
    }

}
