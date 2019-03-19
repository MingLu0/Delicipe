package com.luo.ming.delicipe.Models;

import java.util.ArrayList;

public class Recipe {

    private String ID;
    private String title;
    private String imageLink;
    private String publisher;
    private String sourceURL;
    //private ArrayList<Ingredient> ingredients;

    public Recipe(){}

    // Getters
    public String getTitle() {
        return title;
    }
    public String getImageLink() {
        return imageLink;
    }
    public String getPublisher() {
        return publisher;
    }
    public String getSourceURL() {
        return sourceURL;
    }
//    public ArrayList<Ingredient> getIngredients() {
//        return(ingredients);
//    }

    public String getID() {
        return ID;
    }

    // Setters
    public void setTitle(String title){
        this.title = title;
    }
    public void setImageLink(String imageLink){
        // Swapping http with https so image is found and displayed
        this.imageLink = imageLink.replace("http","https");
        if(this.imageLink.contains("httpss")){
            this.imageLink = this.imageLink.replace("httpss","https");
        }
    }
    public void setPublisher(String publisher){
        this.publisher = publisher;
    }
    public void setSourceURL(String sourceURL) {
        // Swapping http with https so web page is found and displayed
        this.sourceURL = sourceURL.replace("http","https");
    }

//    public void setIngredients(ArrayList<Ingredient> ingredients) {
//        this.ingredients = new ArrayList<>(ingredients);
//    }

    public void setID(String id){
        this.ID = id;
    }


}
