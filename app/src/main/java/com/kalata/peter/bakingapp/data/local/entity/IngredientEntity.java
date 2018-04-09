package com.kalata.peter.bakingapp.data.local.entity;

import org.parceler.Parcel;

@Parcel
public class IngredientEntity
{
    String measure;

    String ingredient;

    String quantity;

    public IngredientEntity() {}

    public IngredientEntity(String measure, String ingredient, String quantity) {
        this.measure = measure;
        this.ingredient = ingredient;
        this.quantity = quantity;
    }

    public String getMeasure ()
    {
        return measure;
    }

    public void setMeasure (String measure)
    {
        this.measure = measure;
    }

    public String getIngredient ()
    {
        return ingredient;
    }

    public void setIngredient (String ingredient)
    {
        this.ingredient = ingredient;
    }

    public String getQuantity ()
    {
        return quantity;
    }

    public void setQuantity (String quantity)
    {
        this.quantity = quantity;
    }
}
