package com.kalata.peter.bakingapp.data.local.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import com.kalata.peter.bakingapp.data.local.entity.RecipeEntity;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface RecipeDao {

    @Query("SELECT * FROM " + RecipeEntity.TABLE_NAME + " ORDER BY id")
    LiveData<List<RecipeEntity>> getRecipes();

    @Insert(onConflict = REPLACE)
    void addRecipes(List<RecipeEntity> recipes);

}