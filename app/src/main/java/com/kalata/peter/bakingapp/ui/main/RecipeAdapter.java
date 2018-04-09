package com.kalata.peter.bakingapp.ui.main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kalata.peter.bakingapp.R;
import com.kalata.peter.bakingapp.data.local.entity.RecipeEntity;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter {

    List<RecipeEntity> recipes;
    private final ActionListener actionListener;

    public RecipeAdapter(ActionListener actionListener){
        this.actionListener = actionListener;
        setHasStableIds(true);
    }

    public void setRecipes(List<RecipeEntity> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recipe, viewGroup, false);
        return new RecipeViewHolder(itemView, actionListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int i) {
        ((RecipeViewHolder) holder).bindData(recipes.get(i));
    }

    @Override
    public int getItemCount() {
        return recipes == null ? 0 : recipes.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    interface ActionListener {

        void onRecipeClick(RecipeEntity recipe);

    }



}