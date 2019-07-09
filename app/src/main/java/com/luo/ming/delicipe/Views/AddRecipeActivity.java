package com.luo.ming.delicipe.Views;

import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.luo.ming.delicipe.Models.UserRecipe;
import com.luo.ming.delicipe.Models.UserRecipeCover;
import com.luo.ming.delicipe.Models.UserRecipeIngredient;
import com.luo.ming.delicipe.Models.UserRecipeStep;
import com.luo.ming.delicipe.R;

import java.util.ArrayList;

import static com.luo.ming.delicipe.Views.AddCookingStepFragment.STEP_INFO_BUNDLE;
import static com.luo.ming.delicipe.Views.AddCoverFragment.COVER_INFO_BUNDLE_TAG;
import static com.luo.ming.delicipe.Views.AddIngredientFragment.INGREDIENT_BUNDLE_TAG;

public class AddRecipeActivity extends AppCompatActivity implements AddCoverFragment.OnAddCoverFragmentInteractionListener, AddIngredientFragment.OnAddIngredientFragmentInteractionListener,AddCookingStepFragment.OnAddCookingStepFragmentInteractionListener{

    private PagerAdapter mSectionsPagerAdapter;
    private AddCoverFragment addCoverFragment;
    private AddIngredientFragment addIngredientFragment;
    private AddCookingStepFragment addCookingStepFragment;

    private UserRecipeCover userRecipeCover;
    private UserRecipeIngredient ingredient;
    private UserRecipeStep step;

    private ArrayList<UserRecipeIngredient> ingredientList;
    private ArrayList<UserRecipeStep> userRecipeStepList;

    //todo convert to MVP
    private UserRecipe userRecipe;


    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.save_recipe:
                addCoverFragment.sendDataBackToActivity();
                addIngredientFragment.sendDataBackToActivity();

                if(addCookingStepFragment!=null){
                    addCookingStepFragment.sendDataBackToActivity();
                }

                if(userRecipeCover != null){
                     userRecipe = new UserRecipe(userRecipeCover,ingredientList,userRecipeStepList);
                     userRecipe.saveUserRecipeToDatabase(this);
                     Intent intent = new Intent(this, TabbedActivity.class);
                     startActivity(intent);
                    Toast.makeText(this,"Recipe has been saved", Toast.LENGTH_SHORT).show();
                }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);

        if(fragment instanceof AddCoverFragment){
            addCoverFragment = (AddCoverFragment)fragment;
        } else if (fragment instanceof AddIngredientFragment){
            addIngredientFragment = (AddIngredientFragment)fragment;
        } else if (fragment instanceof AddCookingStepFragment){
            addCookingStepFragment = (AddCookingStepFragment)fragment;
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add_recipe,menu);

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        Toolbar toolbar =  findViewById(R.id.toolbar_recipe);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
       // actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.pager);


        TabLayout tabLayout = findViewById(R.id.tab_layout_recipe);
        // Set the text for each tab.
        tabLayout.addTab(tabLayout.newTab().setText("COVER"));
        tabLayout.addTab(tabLayout.newTab().setText("INGREDIENTS"));
        tabLayout.addTab(tabLayout.newTab().setText("STEPS"));
        // Set the tabs to fill the entire layout.
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.

        mSectionsPagerAdapter = new PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        mViewPager.setAdapter(mSectionsPagerAdapter);
//
        //detect if a tab is clicked
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        // set viewpager to the appropriate screen
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
    }

    @Override
    public void onAddCoverFragmentInteraction(Bundle bundle) {

        userRecipeCover = null;
        userRecipeCover = bundle.getParcelable(COVER_INFO_BUNDLE_TAG);
//        Log.d("AddRecipeActivity", userRecipeCover.getComment());
//        Log.d("AddRecipeActivity", userRecipeCover.getImageUri());
//        Log.d("AddRecipeActivity", userRecipeCover.getCoverName());
//        Log.d("AddRecipeActivity",String.valueOf(userRecipeCover.getCookingTime()));
//        Log.d("AddRecipeActivity",String.valueOf(userRecipeCover.getServingSize()));

    }

    @Override
    public void onAddIngredientFragmentInteraction(Bundle bundle) {

        ingredientList = bundle.getParcelableArrayList(INGREDIENT_BUNDLE_TAG);

        for(int i=0;i<ingredientList.size();i++){
            ingredient = ingredientList.get(i);
//            Log.d("AddRecipeActivity",String.valueOf(ingredient.getAmount()));
//            Log.d("AddRecipeActivity",ingredient.getUnit());
//            Log.d("AddRecipeActivity",ingredient.getName());
        }

    }

    @Override
    public void onAddCookingStepFragmentInteraction(Bundle bundle) {

        userRecipeStepList = bundle.getParcelableArrayList(STEP_INFO_BUNDLE);

        for(int i = 0; i< userRecipeStepList.size(); i++){

            step = userRecipeStepList.get(i);
//            Log.d("AddRecipeActivity",step.getImageUri());
//            Log.d("AddRecipeActivity",step.getStepText());

        }


    }


    public class PagerAdapter extends FragmentStatePagerAdapter {

        int mNumOfTabs;

        public PagerAdapter(FragmentManager fm, int mNumOfTabs) {
            super(fm);
            this.mNumOfTabs = mNumOfTabs;
        }

        @Override
        public Fragment getItem(int position) {

            switch(position){
                case 0: return new AddCoverFragment();
                case 1: return new AddIngredientFragment();
                case 2: return new AddCookingStepFragment();
                default: return null;
            }

        }

        @Override
        public int getCount() {
            return mNumOfTabs;
        }


    }
}
