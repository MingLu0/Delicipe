package com.luo.ming.delicipe.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class UserRecipeIngredient implements Parcelable {

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

    public UserRecipeIngredient(float amount, String unit, String name) {
        this.amount = amount;
        this.unit = unit;
        this.name = name;
    }

    protected UserRecipeIngredient(Parcel in) {
        amount = in.readFloat();
        unit = in.readString();
        name = in.readString();
    }

    public static final Creator<UserRecipeIngredient> CREATOR = new Creator<UserRecipeIngredient>() {
        @Override
        public UserRecipeIngredient createFromParcel(Parcel in) {
            return new UserRecipeIngredient(in);
        }

        @Override
        public UserRecipeIngredient[] newArray(int size) {
            return new UserRecipeIngredient[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeFloat(amount);
        dest.writeString(unit);
        dest.writeString(name);
    }
}
