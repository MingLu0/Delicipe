package com.luo.ming.delicipe.Helpers;

public class Constants {

    public static final int DB_VERSION = 9;
    public static final String DB_NAME = "delicipeDB";

    //Table names
    public static final String TABLE_FAVORITE_RECIPE = "favoriteRecipeTB";
    public static final String TABLE_SHOPPING_LIST_NAME = "shoppingListTB";
    public static final String TABLE_INGREDIENT = "ingredientTB";
    public static final String TABLE_USER_RECIPE_COVER = "userRecipeCoverTB";
    public static final String TABLE_USER_INGREDIENT = "userIngredientTB";
    public static final String TABLE_USER_STEP ="userStepTB";

    //Ingredient Shopping List Table columns
    public static final String KEY_INGREDIENT_ITEM_ID = "id";
    public static final String KEY_COUNT = "count";
    public static final String KEY_UNIT = "unit";
    public static final String KEY_ITEM_NAME = "item_name";
    public static final String KEY_DATE_Time = "date_added";

    //User Recipe Cover Table columns
    public static final String KEY_COVER_ID = "coverId";
    public static final String KEY_COVER_NAME = "coverName";
    public static final String KEY_COVER_COOKING_TIME = "coverCookingTime";
    public static final String KEY_COVER_SERVING_SIZE = "coverServingSize";
    public static final String KEY_COVER_COMMENT = "coverComment";
    public static final String KEY_COVER_IMAGE_BYTES = "coverImageBytes";


    //User Ingredient Table columns
    public static final String KEY_USER_INGREDIENT_ID = "userIngredientId";
    public static final String KEY_USER_INGREDIENT_COVER_ID = "userIngredientCoverId";
    public static final String KEY_USER_INGREDIENT_AMOUNT = "userIngredientAmount";
    public static final String KEY_USER_INGREDIENT_UNIT = "userIngredientUnit";
    public static final String KEY_USER_INGREDIENT_NAME = "userIngreidentName";

    //User Cooking Step Table columns
    public static final String KEY_USER_STEP_ID = "userStepId";
    public static final String KEY_USER_STEP_COVER_ID = "userStepCoverId";
    public static final String KEY_USER_STEP_IMAGE_URI = "userStepImageUri";
    public static final String KEY_USER_STEP_TEXT = "userStepText";







}
