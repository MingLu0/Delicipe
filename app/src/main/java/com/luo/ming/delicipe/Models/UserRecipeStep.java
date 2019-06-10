package com.luo.ming.delicipe.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class UserRecipeStep implements Parcelable {

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

    public UserRecipeStep(String imageUri, String stepText) {
        this.imageUri = imageUri;
        this.stepText = stepText;
    }

    protected UserRecipeStep(Parcel in) {

        imageUri = in.readString();
        stepText = in.readString();
    }

    public static final Creator<UserRecipeStep> CREATOR = new Creator<UserRecipeStep>() {
        @Override
        public UserRecipeStep createFromParcel(Parcel in) {
            return new UserRecipeStep(in);
        }

        @Override
        public UserRecipeStep[] newArray(int size) {
            return new UserRecipeStep[size];
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
