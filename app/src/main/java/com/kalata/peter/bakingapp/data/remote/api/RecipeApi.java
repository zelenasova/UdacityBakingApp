package com.kalata.peter.bakingapp.data.remote.api;


import com.kalata.peter.bakingapp.data.local.entity.RecipeEntity;

import java.util.List;

import io.reactivex.Observable;

public interface RecipeApi {

    Observable<List<RecipeEntity>> getRecipes();

}