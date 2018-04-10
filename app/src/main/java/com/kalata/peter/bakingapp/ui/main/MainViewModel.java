package com.kalata.peter.bakingapp.ui.main;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.kalata.peter.bakingapp.App;
import com.kalata.peter.bakingapp.common.SingleLiveEvent;
import com.kalata.peter.bakingapp.common.utils.AssetsUtils;
import com.kalata.peter.bakingapp.common.utils.SharedPrefsUtils;
import com.kalata.peter.bakingapp.data.local.entity.RecipeEntity;
import com.kalata.peter.bakingapp.data.repository.RecipeRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends ViewModel {

    @Inject
    RecipeRepository recipeRepository;

    private LiveData<List<RecipeEntity>> recipes = new MutableLiveData<>();

    public MainViewModel() {
        App.getAppComponent().inject(this);
        recipes = recipeRepository.getRecipes();
    }

    public LiveData<List<RecipeEntity>> getRecipes() {
        return recipes;
    }

    SingleLiveEvent<Throwable> getError() {
        return recipeRepository.getError();
    }

}