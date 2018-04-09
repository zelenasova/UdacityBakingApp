package com.kalata.peter.bakingapp.data.repository;


import android.arch.lifecycle.LiveData;

import com.kalata.peter.bakingapp.data.local.dao.RecipeDao;
import com.kalata.peter.bakingapp.data.local.entity.RecipeEntity;

import java.util.List;

import io.reactivex.Completable;


public class RecipeRepositoryImpl implements RecipeRepository {

    private final RecipeDao recipeDao;

    public RecipeRepositoryImpl(RecipeDao recipeDao) {
        this.recipeDao = recipeDao;
    }

    @Override
    public Completable addRecipes(List<RecipeEntity> recipes) {
        return Completable.fromAction(() -> recipeDao.addRecipes(recipes));
    }

    @Override
    public LiveData<List<RecipeEntity>> getRecipes() {
        return recipeDao.getRecipes();
    }

}
