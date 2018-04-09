package com.kalata.peter.bakingapp.ui.main;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.kalata.peter.bakingapp.App;
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

    private final CompositeDisposable composite = new CompositeDisposable();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private LiveData<List<RecipeEntity>> recipes = new MutableLiveData<>();

    public MainViewModel() {
        App.getAppComponent().inject(this);
        initDataFromJson();
        recipes = recipeRepository.getRecipes();

    }

    public void initDataFromJson() {
        if (SharedPrefsUtils.isFirstRun()) {
            SharedPrefsUtils.setFirstRun(false);
            composite.add(recipeRepository.addRecipes(AssetsUtils.getRecipes())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe()
            );
        }
    }

    public LiveData<List<RecipeEntity>> getRecipes() {
        return recipes;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        composite.clear();
    }
}