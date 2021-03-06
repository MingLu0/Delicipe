package com.luo.ming.delicipe.Views;


import android.content.Intent;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.luo.ming.delicipe.Models.User;
import com.luo.ming.delicipe.Presenters.TabbedActivityPresenter;
import com.luo.ming.delicipe.R;
import com.squareup.picasso.Picasso;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        TabbedActivityPresenter.View {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    @BindView(R.id.fragment_container) ViewPager mViewPager;
    @BindView(R.id.drawer_layout) DrawerLayout drawerLayout;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.nav_view) NavigationView navigationView;
    @BindView(R.id.tabs)TabLayout tabLayout;

    private ImageView userImage;
    private TextView userName;
    private TextView userEmail;

    private TabbedActivityPresenter presenter;

    public static final String MESSAGE_FROM_TABBEDACTIVITY_TAG ="package com.luo.ming.delicipe.Views";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        presenter = new TabbedActivityPresenter(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawerLayout, toolbar,R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView =  navigationView.getHeaderView(0);

       //ButterKnife.bind(this,headerView);
        userName = headerView.findViewById(R.id.nav_header_user_name);
        userImage = headerView.findViewById(R.id.nav_header_user_image);
        userEmail = headerView.findViewById(R.id.nav_header_email);

        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.TABBED_ACTIVITY_TAB1)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.TABBED_ACTIVITY_TAB2)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.TABBED_ACTIVITY_TAB3)));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        mViewPager.setAdapter(mSectionsPagerAdapter);

        //detect if a tab is clicked
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        // set viewpager to the appropriate screen
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle!=null){
            if(bundle.containsKey(SignInActivity.MAIN_ACTIVITY_MESSAGE)){
                User user = (User)bundle.getSerializable(SignInActivity.MAIN_ACTIVITY_MESSAGE);
                presenter.updateUiFromIntent(user);
            }
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu,menu);
        androidx.appcompat.widget.SearchView searchView =
                (androidx.appcompat.widget.SearchView) menu.findItem(R.id.search).getActionView();

        searchView.setQueryHint(getResources().getString(R.string.SEARCH_ONLINE_RECIPE_HINT));
        searchView.setIconified(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query) {

                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                intent.putExtra(MESSAGE_FROM_TABBEDACTIVITY_TAG,query);
                startActivity(intent);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
     }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id = menuItem.getItemId();

        switch (id){
            case R.id.nav_shopping:
                Intent intent = new Intent(this,ShoppingListActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_create_recipe:
                Intent intent_recipe = new Intent(this,AddRecipeActivity.class);
                startActivity(intent_recipe);
                break;

            case R.id.nav_online_recipe:
                Intent intent_search = new Intent(this, SearchActivity.class);
                startActivity(intent_search);
                break;

            case R.id.nav_log_out:
                presenter.logOut();
                drawerLayout.closeDrawer(GravityCompat.START,false);
                break;

            case R.id.nav_log_in:
                Intent intent_log_in = new Intent(this, SignInActivity.class);
                startActivity(intent_log_in);
                break;

        }

        return true;
    }

    @Override
    public void setNavHeaderImg(String url) {

        Picasso.with(this)
                .load(url)
                .error(R.mipmap.ic_launcher)
                .into(userImage);
    }

    @Override
    public void setNavHeaderUserName(String name) {

        if(name!=null){
            userName.setText(name);
        } else {
            userName.setVisibility(View.INVISIBLE);
        }


    }

    @Override
    public void setNavHeaderEmail(String email) {

        if(email!=null){
            userEmail.setText(email);
        } else {
            userEmail.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public void displayToast(String text) {

        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }


    /**
     * A {@link FragmentStatePagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        int mNumOfTabs;

        public SectionsPagerAdapter(FragmentManager fm,int mNumOfTabs) {
            super(fm);
            this.mNumOfTabs = mNumOfTabs;
        }

        @Override
        public Fragment getItem(int position) {

            switch(position){
                case 0: return new RecommendationFragment();
                case 1: return new UserRecipeFragment();
                case 2: return new FavouritesFragment();
                default: return null;
            }

        }

        @Override
        public int getCount() {
            return mNumOfTabs;
        }

    }

}
