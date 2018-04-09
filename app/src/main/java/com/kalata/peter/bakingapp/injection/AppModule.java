package com.kalata.peter.bakingapp.injection;

import android.arch.persistence.room.Room;
import android.content.Context;
import com.kalata.peter.bakingapp.data.local.dao.RecipeDao;
import com.kalata.peter.bakingapp.data.local.db.RecipeDatabase;
import com.kalata.peter.bakingapp.data.repository.RecipeRepository;
import com.kalata.peter.bakingapp.data.repository.RecipeRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private static final String DB_NAME = "recipe_db";

    Context mAppContext;

    public AppModule(Context appContext) {
        this.mAppContext = appContext;
    }

    @Provides
    @Singleton
    Context provideAppContext() {
        return mAppContext;
    }

    @Provides
    @Singleton
    RecipeDao providesRecipeDao(Context context) {
        return Room.databaseBuilder(context, RecipeDatabase.class, DB_NAME).build().recipeDao();
    }

    @Provides
    @Singleton
    RecipeRepository providesRecipeRepository(RecipeDao recipeDao) {
        return new RecipeRepositoryImpl(recipeDao);
    }

}
