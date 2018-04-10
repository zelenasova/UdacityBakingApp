package com.kalata.peter.bakingapp.data.repository;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.Transformations;
import android.support.annotation.Nullable;
import android.util.Log;

import com.kalata.peter.bakingapp.common.SingleLiveEvent;
import com.kalata.peter.bakingapp.data.local.dao.RecipeDao;
import com.kalata.peter.bakingapp.data.local.entity.RecipeEntity;
import com.kalata.peter.bakingapp.data.remote.api.RecipeApi;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RecipeRepositoryImpl implements RecipeRepository {

    private final RecipeApi recipeApi;
    private final RecipeDao recipeDao;
    private final MediatorLiveData<List<RecipeEntity>> recipes = new MediatorLiveData<>();
    private final SingleLiveEvent<Throwable> error = new SingleLiveEvent<>();

    public RecipeRepositoryImpl(RecipeApi recipeApi, RecipeDao recipeDao) {
        this.recipeDao = recipeDao;
        this.recipeApi = recipeApi;
    }

    private Completable addRecipes(List<RecipeEntity> recipes) {
        return Completable.fromAction(() -> recipeDao.addRecipes(recipes));
    }

    @Override
    public LiveData<List<RecipeEntity>> getRecipes() {
        LiveData<List<RecipeEntity>> dbSource = recipeDao.getRecipes();
        recipes.addSource(dbSource, data -> {
            recipes.removeSource(dbSource);
            if (data.size() == 0) {
                getRecipesFromApi();
            } else {
                recipes.setValue(dbSource.getValue());
            }
        });
        return recipes;
    }

    private void getRecipesFromApi() {
        recipeApi.getRecipes()
                .subscribeOn(Schedulers.io())
                .doOnNext(recipes -> addRecipes(recipes).subscribe())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        recipes::setValue,
                        error::setValue
                );
    }

    @Override
    public SingleLiveEvent<Throwable> getError() {
        return error;
    }

}
