package com.luo.ming.delicipe.Models;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class UserRecipeStep implements Parcelable {

    private byte[] imageBytes;
    private String stepText;

    private Uri imageUri;

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }



    public byte[] getImageBytes() {
        return imageBytes;
    }

    public void setImageBytes(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }

    public String getStepText() {
        return stepText;
    }

    public void setStepText(String stepText) {
        this.stepText = stepText;
    }

    public UserRecipeStep(byte[] imageBytes, String stepText) {
        this.imageBytes = imageBytes;
        this.stepText = stepText;
    }

    protected UserRecipeStep(Parcel in) {

        imageBytes = in.createByteArray();
        stepText = in.readString();
    }

    public UserRecipeStep() {
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

        dest.writeByteArray(imageBytes);
        dest.writeString(stepText);

    }
}
