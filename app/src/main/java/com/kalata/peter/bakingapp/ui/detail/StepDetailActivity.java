package com.kalata.peter.bakingapp.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.kalata.peter.bakingapp.R;
import com.kalata.peter.bakingapp.data.local.entity.RecipeEntity;
import com.kalata.peter.bakingapp.data.local.entity.StepEntity;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

public class StepDetailActivity extends AppCompatActivity {

    public static final String EXTRA_STEPS = "args_steps";
    public static final String EXTRA_POSITION = "args_position";
    public static final String STEP_POSITION = "step_position";

    public static void startStepDetailActivity(Context context, List<StepEntity> steps, int selectedPosition) {
        Intent intent = new Intent(context, StepDetailActivity.class);
        intent.putExtra(EXTRA_STEPS, Parcels.wrap(steps));
        intent.putExtra(EXTRA_POSITION, selectedPosition);
        context.startActivity(intent);
    }

    @Nullable
    @BindView(R.id.fab_left)
    FloatingActionButton fabLeft;

    @Nullable
    @BindView(R.id.fab_right)
    FloatingActionButton fabRight;

    private List<StepEntity> steps;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);
        ButterKnife.bind(this);

        steps = getSteps();
        position = getPosition();
        setupActionBar();
        if (savedInstanceState == null) {
            changeFragment();
        } else {
            position = savedInstanceState.getInt(STEP_POSITION);
            checkButtonVisibility(position);
        }
    }

    private void changeFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fl_step_detail_container, StepDetailFragment.newInstance(steps.get(position)));
        ft.commit();
        updateTitle();
        checkButtonVisibility(position);
    }

    private List<StepEntity> getSteps() {
        return Parcels.unwrap(getIntent().getParcelableExtra(EXTRA_STEPS));
    }

    private int getPosition() {
        return getIntent().getIntExtra(EXTRA_POSITION, 0);
    }

    private void setupActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void updateTitle() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(steps.get(position).getShortDescription());
        }
    }

    private void checkButtonVisibility(int currentPosition) {
        if (fabLeft == null || fabRight == null) return;
        fabLeft.setVisibility(currentPosition == 0 ? View.INVISIBLE : View.VISIBLE);
        fabRight.setVisibility(currentPosition == steps.size() - 1 ? View.INVISIBLE : View.VISIBLE);
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STEP_POSITION, position);
    }

    @Optional
    @OnClick(R.id.fab_left)
    void onPreviousClick() {
        position--;
        changeFragment();
    }

    @Optional
    @OnClick(R.id.fab_right)
    void onNextClick() {
        position++;
        changeFragment();

    }

}
