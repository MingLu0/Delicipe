package com.luo.ming.delicipe.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class RecipeIngredient implements Parcelable {

    private float amount;
    private String unit;
    private String name;

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RecipeIngredient(float amount, String unit, String name) {
        this.amount = amount;
        this.unit = unit;
        this.name = name;
    }

    protected RecipeIngredient(Parcel in) {
    }

    public static final Creator<RecipeIngredient> CREATOR = new Creator<RecipeIngredient>() {
        @Override
        public RecipeIngredient createFromParcel(Parcel in) {
            return new RecipeIngredient(in);
        }

        @Override
        public RecipeIngredient[] newArray(int size) {
            return new RecipeIngredient[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
