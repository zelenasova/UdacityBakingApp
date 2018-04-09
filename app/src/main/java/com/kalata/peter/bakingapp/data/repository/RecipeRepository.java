package com.kalata.peter.bakingapp.data.repository;


import android.arch.lifecycle.LiveData;
import com.kalata.peter.bakingapp.data.local.entity.RecipeEntity;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;


public interface RecipeRepository {

    Completable addRecipes(List<RecipeEntity> recipes);

    LiveData<List<RecipeEntity>> getRecipes();

}
