package com.luo.ming.delicipe.Models;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.luo.ming.delicipe.Data.DatabaseHandler;

import java.util.ArrayList;

public class UserRecipeCover implements Parcelable {

    private String imageUri;
    private String coverName;
    private String comment;
    private String coverID;
    private int cookingTime;
    private int servingSize;

    public String getCoverID() {
        return coverID;
    }

    public void setCoverID(String coverID) {
        this.coverID = coverID;
    }

    private DatabaseHandler db;

    //default constructor
    public UserRecipeCover() {
    }

    public UserRecipeCover(String imageUri, String coverName, int cookingTime, int servingSize, String comment) {
        this.imageUri = imageUri;
        this.coverName = coverName;
        this.cookingTime = cookingTime;
        this.servingSize = servingSize;
        this.comment = comment;
    }

    public ArrayList<UserRecipeCover> getAllRecipeCoversFromDB(Context context){
        ArrayList<UserRecipeCover> userRecipeCovers ;

        db = DatabaseHandler.getDataBase(context);

        userRecipeCovers= db.getAllUserRecipeCovers();

        return userRecipeCovers;
    }


    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public void setCoverName(String coverName) {
        this.coverName = coverName;
    }

    public void setCookingTime(int cookingTime) {
        this.cookingTime = cookingTime;
    }

    public void setServingSize(int servingSize) {
        this.servingSize = servingSize;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getImageUri() {
        return imageUri;
    }

    public String getCoverName() {
        return coverName;
    }

    public int getCookingTime() {
        return cookingTime;
    }

    public int getServingSize() {
        return servingSize;
    }

    public String getComment() {
        return comment;
    }

    protected UserRecipeCover(Parcel in) {

        imageUri = in.readString();
        coverName = in.readString();
        cookingTime = in.readInt();
        servingSize = in.readInt();
        comment = in.readString();

    }

    public static final Creator<UserRecipeCover> CREATOR = new Creator<UserRecipeCover>() {
        @Override
        public UserRecipeCover createFromParcel(Parcel in) {
            return new UserRecipeCover(in);
        }

        @Override
        public UserRecipeCover[] newArray(int size) {
            return new UserRecipeCover[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(imageUri);
        dest.writeInt(cookingTime);
        dest.writeInt(servingSize);
        dest.writeString(coverName);
        dest.writeString(comment);
    }
}
