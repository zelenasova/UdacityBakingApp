package com.kalata.peter.bakingapp.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.kalata.peter.bakingapp.R;
import com.kalata.peter.bakingapp.data.local.entity.RecipeEntity;
import com.kalata.peter.bakingapp.data.local.entity.StepEntity;

import org.parceler.Parcels;

import butterknife.ButterKnife;

public class RecipeDetailActivity extends AppCompatActivity implements StepAdapter.ActionListener {

    public static final String EXTRA_RECIPE = "args_recipe";

    public static void startDetailActivity(Context context, RecipeEntity recipe) {
        Intent intent = new Intent(context, RecipeDetailActivity.class);
        intent.putExtra(EXTRA_RECIPE, Parcels.wrap(recipe));
        context.startActivity(intent);
    }

    private boolean isTwoPane;
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        ButterKnife.bind(this);
        isTwoPane = getResources().getBoolean(R.bool.is_tablet);
        fm = getSupportFragmentManager();

        setupActionBar();
        if (savedInstanceState == null) {
            fm.beginTransaction()
                    .replace(R.id.fl_step_list_container, StepListFragment.newInstance(getRecipe()))
                    .commit();
        }

    }

    private RecipeEntity getRecipe() {
        return Parcels.unwrap(getIntent().getParcelableExtra(EXTRA_RECIPE));
    }

    private void setupActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getRecipe().getName());
        }
    }

    @Override
    public void onStepClick(StepEntity step, int position) {
        if (isTwoPane) {
            StepDetailFragment stepDetailFragment = StepDetailFragment.newInstance(step);
            fm.beginTransaction().replace(R.id.fl_step_detail_container, stepDetailFragment).commit();
        } else {
            StepDetailActivity.startStepDetailActivity(this, getRecipe().getSteps(), position);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
