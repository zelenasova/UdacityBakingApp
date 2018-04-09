package com.kalata.peter.bakingapp.data.local.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.kalata.peter.bakingapp.data.local.dao.RecipeDao;
import com.kalata.peter.bakingapp.data.local.entity.RecipeEntity;

@Database(entities = {RecipeEntity.class}, version = 1)
@TypeConverters({IngredientTypeConverter.class, StepTypeConverter.class})
public abstract class RecipeDatabase extends RoomDatabase {

    public abstract RecipeDao recipeDao();

}