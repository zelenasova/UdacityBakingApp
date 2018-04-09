package com.kalata.peter.bakingapp.ui.detail;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.kalata.peter.bakingapp.R;
import com.kalata.peter.bakingapp.common.utils.ParsingUtils;
import com.kalata.peter.bakingapp.data.local.entity.RecipeEntity;
import com.kalata.peter.bakingapp.data.local.entity.StepEntity;

import org.parceler.Parcels;
import butterknife.BindView;
import butterknife.ButterKnife;


public class StepListFragment extends Fragment implements StepAdapter.ActionListener {

    private static final String ARGS_RECIPE = "args_recipe";

    @BindView(R.id.rv_steps)
    RecyclerView rvSteps;
    @BindView(R.id.tv_ingrediences)
    TextView tvIngrediences;

    private RecipeEntity recipe;
    private StepAdapter adapter;
    private StepAdapter.ActionListener listener;

    public StepListFragment() {}

    public static StepListFragment newInstance(RecipeEntity recipe) {
        StepListFragment fragment = new StepListFragment();
        Bundle arguments = new Bundle();
        arguments.putParcelable(ARGS_RECIPE, Parcels.wrap(recipe));
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            recipe = getRecipe();
        }
    }

    private RecipeEntity getRecipe() {
        return Parcels.unwrap(getArguments().getParcelable(ARGS_RECIPE));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_list, container, false);
        ButterKnife.bind(this, view);
        setupRecyclerView();
        if (recipe != null) {
            tvIngrediences.setText(ParsingUtils.getIngredientString(recipe.getIngredients()));
        }
        return view;
    }

    private void setupRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvSteps.setLayoutManager(layoutManager);
        adapter = new StepAdapter(this, recipe.getSteps(), getResources().getBoolean(R.bool.is_tablet));
        rvSteps.setAdapter(adapter);
        final DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),
                layoutManager.getOrientation());
        rvSteps.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof StepAdapter.ActionListener) {
            listener = (StepAdapter.ActionListener) context;
        }
    }

    @Override
    public void onStepClick(StepEntity step, int position) {
        listener.onStepClick(step, position);
    }
}
