package com.kalata.peter.bakingapp.data.remote.api;

import com.kalata.peter.bakingapp.data.local.entity.RecipeEntity;
import com.kalata.peter.bakingapp.data.remote.services.RecipeService;

import java.util.List;

import io.reactivex.Observable;


public class RecipeApiImpl implements RecipeApi {

    private final RecipeService service;

    public RecipeApiImpl(RecipeService service) {
        this.service = service;
    }

    @Override
    public Observable<List<RecipeEntity>> getRecipes() {
        return service.getRecipes();
    }


}
