package com.luo.ming.delicipe.Models;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class RecipeStep implements Parcelable {

    private String imageUri;
    private String stepText;

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getStepText() {
        return stepText;
    }

    public void setStepText(String stepText) {
        this.stepText = stepText;
    }

    public RecipeStep(String imageUri, String stepText) {
        this.imageUri = imageUri;
        this.stepText = stepText;
    }

    protected RecipeStep(Parcel in) {

        imageUri = in.readString();
        stepText = in.readString();
    }

    public static final Creator<RecipeStep> CREATOR = new Creator<RecipeStep>() {
        @Override
        public RecipeStep createFromParcel(Parcel in) {
            return new RecipeStep(in);
        }

        @Override
        public RecipeStep[] newArray(int size) {
            return new RecipeStep[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(imageUri);
        dest.writeString(stepText);

    }
}
