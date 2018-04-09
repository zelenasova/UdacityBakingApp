package com.kalata.peter.bakingapp.data.local.entity;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

import static com.kalata.peter.bakingapp.data.local.entity.RecipeEntity.TABLE_NAME;


@Parcel
@Entity(tableName = TABLE_NAME)
public class RecipeEntity {
    public static final String TABLE_NAME = "recipes";

    List<IngredientEntity> ingredients;

    @PrimaryKey
    @NonNull
    @SerializedName("id")
    String id;

    String servings;

    String name;

    String image;

    List<StepEntity> steps;

    public RecipeEntity() {}

    public RecipeEntity(List<IngredientEntity> ingredients, @NonNull String id, String servings, String name, String image, List<StepEntity> steps) {
        this.ingredients = ingredients;
        this.id = id;
        this.servings = servings;
        this.name = name;
        this.image = image;
        this.steps = steps;
    }

    public List<IngredientEntity> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientEntity> ingredients) {
        this.ingredients = ingredients;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServings() {
        return servings;
    }

    public void setServings(String servings) {
        this.servings = servings;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<StepEntity> getSteps() {
        return steps;
    }

    public void setSteps(List<StepEntity> steps) {
        this.steps = steps;
    }


}
