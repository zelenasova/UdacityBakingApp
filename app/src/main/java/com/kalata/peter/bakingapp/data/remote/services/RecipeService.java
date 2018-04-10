package com.kalata.peter.bakingapp.data.remote.services;


import com.kalata.peter.bakingapp.data.local.entity.RecipeEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface RecipeService {

    @GET("baking.json")
    Observable<List<RecipeEntity>> getRecipes();

}
