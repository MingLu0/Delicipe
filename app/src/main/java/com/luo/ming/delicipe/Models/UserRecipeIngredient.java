package com.luo.ming.delicipe.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class UserRecipeIngredient implements Parcelable {


    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserRecipeIngredient(String name) {
        this.name = name;
    }

    public UserRecipeIngredient() {
    }

    protected UserRecipeIngredient(Parcel in) {
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
        dest.writeString(name);
    }
}
