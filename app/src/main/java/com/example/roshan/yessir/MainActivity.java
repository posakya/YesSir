package com.example.roshan.yessir;

import android.content.res.Configuration;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import com.example.roshan.yessir.Fragments.Abesentees;
import com.example.roshan.yessir.Fragments.AboutUs;
import com.example.roshan.yessir.Fragments.Attendance;
import com.example.roshan.yessir.Fragments.Home;
import com.example.roshan.yessir.Fragments.Setting;
import com.example.roshan.yessir.Fragments.Share;

import static android.R.id.toggle;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home");
//        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();
//
//
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                if (drawer.isDrawerOpen(GravityCompat.START)) {
//                   getSupportActionBar().setTitle("Rosahn");
//                } else {
//                    drawer.openDrawer(GravityCompat.START);
//                }
//            }
//        });
        FragmentManager fragmentManager = getSupportFragmentManager();
        {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , new Home())
                    .commit();}

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("YesSir");
            }
        };
        drawer.setDrawerListener(toggle);
        drawer.setScrimColor(getResources().getColor(android.R.color.transparent));
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (id == R.id.nav_home) {
            getSupportActionBar().setTitle("Home");
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , new Home())
                    .commit();
            // Handle the attendance action
        } else if (id == R.id.nav_attendance) {
            getSupportActionBar().setTitle("Student List");
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , new Attendance())
                    .commit();

        } else if (id == R.id.nav_absentees) {
            getSupportActionBar().setTitle("Absentees");
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , new Abesentees())
                    .commit();
        } else if (id == R.id.nav_setting) {
            getSupportActionBar().setTitle("Setting");
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , new Setting())
                    .commit();

        }  else if (id == R.id.nav_work) {
            getSupportActionBar().setTitle("Days");
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , new Share())
                    .commit();

        } else if (id == R.id.nav_about_us) {
            getSupportActionBar().setTitle("About Us");
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , new AboutUs())
                    .commit();


        }

       DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
       drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}