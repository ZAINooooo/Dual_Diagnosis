package com.example.dual_diagnosis;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.rahman.dialog.Activity.SmartDialog;
import com.rahman.dialog.ListenerCallBack.SmartDialogClickListener;
import com.rahman.dialog.Utilities.SmartDialogBuilder;
import com.squareup.picasso.Picasso;

public class Dual_Diagnosis extends AppCompatActivity {


    private NavigationView navigationView;
    private DrawerLayout drawer;
    TextView m1;
    ActionBarDrawerToggle actionBarDrawerToggle;
    public static int navItemIndex = 0;
    private Handler mHandler;
    Typeface face, face2;

    private static final String TAG_HOME = "DASHBOARD";
    private static final String TAG_PRICES = "Add Space";
    private static final String TAB_ABOUT_US = "About us";
    public static String CURRENT_TAG = TAG_HOME;
    private Toolbar toolbar;
    private String[] activityTitles;
    private TextView txtName, txtWebsite;
    private View navHeader;
    private ImageView imgNavHeaderBg, imgProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dual__diagnosis);
        toolbar = findViewById(R.id.toolbar);

        face = Typeface.createFromAsset(getAssets(), "ptsanswebbold.ttf");
        face2 = Typeface.createFromAsset(getAssets(), "ptsanswebregular.ttf");

        mHandler = new Handler();

        navigationView = findViewById(R.id.nav_view);
        drawer = findViewById(R.id.drawer);
        m1 = findViewById(R.id.m1);

        navHeader = navigationView.getHeaderView(0);
        txtName = navHeader.findViewById(R.id.name);
        txtWebsite = navHeader.findViewById(R.id.website);
        imgNavHeaderBg = navHeader.findViewById(R.id.img_header_bg);
        imgProfile = navHeader.findViewById(R.id.img_profile);
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);


        loadNavHeader();

        setUpNavigationView();
        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);


    }

    private void loadNavHeader() {
        // name, website

        txtName.setText("Dual Diagnosis");
        txtWebsite.setText("itsec.co.uk");

        Picasso.get().load("https://www.pngkey.com/png/full/349-3499617_person-placeholder-person-placeholder.png").noFade().into(imgProfile);
//        navigationView.getMenu().getItem(2).setActionView(R.layout.menu_dot);
    }


    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected( MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_home:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;
                        break;

                    case R.id.nav_prices:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_PRICES;
                        break;

                    case R.id.nav_about_us:
                        startActivity(new Intent(Dual_Diagnosis.this, About_Us_Activity.class));
                        drawer.closeDrawers();
                        return true;

                    case R.id.logout_user:


                        new SmartDialogBuilder2(Dual_Diagnosis.this)
                                .setTitle("Logout")
                                .setSubTitle("Do You Want To Logout")
                                .setCancalable(true)
                                .setTitleFont(face)
                                .setSubTitleFont(face2).setPositiveButton("OK", new SmartDialogClickListener2() {
                            @Override
                            public void onClick(SmartDialog2 smartDialog) {

                                //                                SharedPreferences.Editor editor=sharedPreferences.edit();
//                                editor.remove("email").apply();
//                                editor.remove("name").apply();
//                                editor.remove("phone").apply();
//                                editor.remove("token").apply();
//                                editor.remove("user_id").apply();
//                                editor.remove("role").apply();

                                smartDialog.dismiss();
                                startActivity(new Intent(Dual_Diagnosis.this , Login_Activity.class));
//                        finish();
                            }


                        }).setNegativeButton("NO" , new SmartDialogClickListener2()
                        {
                            @Override
                            public void onClick(SmartDialog2 smartDialog) {

                                smartDialog.dismiss();
                            }
                        }).build().show();

                        drawer.closeDrawers();
                        return true;


                    default:
                        navItemIndex = 0;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });


        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {
            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    private void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();
        // set toolbar title
        setToolbarTitle();

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();
            return;
        }

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.fl_MainContainer, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }


        drawer.closeDrawers();
        // refresh toolbar menu
//        invalidateOptionsMenu();
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                // home
                return new Dashboard_Fragment();

            case 1:

                return  new  Add_Space_Fragment();
                // photos
//                return new Blogs_Fragment();

            case 2:
                // movies fragment
//                return new Prices_Fragment();

            default:
//                return new Home_Fragment();
        }

        return new Dashboard_Fragment();
    }



    private void setToolbarTitle() {
        m1.setText(activityTitles[navItemIndex]);
        Log.d("text" ,activityTitles[navItemIndex] );
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    @Override
    public void onBackPressed() {

    }
}
