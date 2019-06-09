package com.luo.ming.delicipe.Views;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.luo.ming.delicipe.Models.RecipeCover;
import com.luo.ming.delicipe.Models.RecipeIngredient;
import com.luo.ming.delicipe.Models.RecipeStep;
import com.luo.ming.delicipe.R;

import java.util.ArrayList;

import static com.luo.ming.delicipe.Views.AddCoverFragment.COVER_INFO_BUNDLE_TAG;
import static com.luo.ming.delicipe.Views.AddIngredientFragment.INGREDIENT_BUNDLE_TAG;

public class AddRecipeActivity extends AppCompatActivity implements AddCoverFragment.OnAddCoverFragmentInteractionListener, AddIngredientFragment.OnAddIngredientFragmentInteractionListener{

    private PagerAdapter mSectionsPagerAdapter;
    private AddCoverFragment addCoverFragment;
    private AddIngredientFragment addIngredientFragment;
    private AddCookingStepFragment addCookingStepFragment;

    private RecipeCover recipeCover;
    private RecipeIngredient ingredient;
    private RecipeStep step;

    private ArrayList<RecipeIngredient> ingredientList;


    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()){
            case R.id.save_recipe:
                addCoverFragment.saveCoverPageInfo();
                addIngredientFragment.saveIngredientListInfo();


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

        recipeCover = bundle.getParcelable(COVER_INFO_BUNDLE_TAG);
        Log.d("AddRecipeActivity",recipeCover.getComment());
        Log.d("AddRecipeActivity",recipeCover.getImageUri());
        Log.d("AddRecipeActivity",recipeCover.getCoverName());
        Log.d("AddRecipeActivity",String.valueOf(recipeCover.getCookingTime()));
        Log.d("AddRecipeActivity",String.valueOf(recipeCover.getComment()));


    }

    @Override
    public void onAddIngredientFragmentInteraction(Bundle bundle) {

        ingredientList = bundle.getParcelableArrayList(INGREDIENT_BUNDLE_TAG);

        for(int i=0;i<ingredientList.size();i++){
            ingredient = ingredientList.get(i);
            Log.d("AddRecipeActivity",String.valueOf(ingredient.getAmount()));
            Log.d("AddRecipeActivity",ingredient.getUnit());
            Log.d("AddRecipeActivity",ingredient.getName());
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
