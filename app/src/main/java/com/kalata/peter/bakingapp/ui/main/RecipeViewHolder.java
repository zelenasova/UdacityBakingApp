package com.kalata.peter.bakingapp.ui.main;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.kalata.peter.bakingapp.R;
import com.kalata.peter.bakingapp.data.local.entity.RecipeEntity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecipeViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_recipe_name)
    TextView tvRecipeName;

    private RecipeAdapter.ActionListener actionListener;
    private RecipeEntity recipe;

    RecipeViewHolder(View itemView, final RecipeAdapter.ActionListener actionListener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.actionListener = actionListener;
    }

    public void bindData(RecipeEntity recipe) {
        this.recipe = recipe;
        tvRecipeName.setText(recipe.getName());
    }

    @OnClick(R.id.cv_root)
    void onRecipeClick() {
        actionListener.onRecipeClick(recipe);
    }

}
