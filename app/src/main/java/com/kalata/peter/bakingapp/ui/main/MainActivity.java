package com.kalata.peter.bakingapp.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kalata.peter.bakingapp.R;
import com.kalata.peter.bakingapp.common.utils.SharedPrefsUtils;
import com.kalata.peter.bakingapp.data.local.entity.RecipeEntity;
import com.kalata.peter.bakingapp.ui.detail.RecipeDetailActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.ActionListener {

    @BindView(R.id.rv_recipes)
    RecyclerView rvRecipes;

    private MainViewModel mainViewModel;
    private RecipeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupRecyclerView();
        setupViewModel();
    }

    private void setupViewModel() {
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getRecipes().observe(this, recipes -> {
            adapter.setRecipes(recipes);
        });
    }

    private void setupRecyclerView() {
        if (getResources().getBoolean(R.bool.is_tablet)) {
            rvRecipes.setLayoutManager(new GridLayoutManager(this, 2));
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        } else {
            rvRecipes.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        }
        adapter = new RecipeAdapter(this);
        rvRecipes.setAdapter(adapter);
    }

    public MainViewModel getMainViewModel() {
        return mainViewModel;
    }

    @Override
    public void onRecipeClick(RecipeEntity recipe) {
        RecipeDetailActivity.startDetailActivity(this, recipe);
    }
}
