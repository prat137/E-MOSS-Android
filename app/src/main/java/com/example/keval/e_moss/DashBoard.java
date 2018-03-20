package com.example.keval.e_moss;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class DashBoard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private boolean exit = false;
    Toolbar toolbar;
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        displaySelectedScreen(R.id.menuDashBoardNavBarDashBoard);
    }

    private void displaySelectedScreen(int id) {

        Fragment fragment = null;
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (id == R.id.menuDashBoardNavBarDashBoard) {
            fragment = new FragmentDashboard();
            getSupportActionBar().setTitle("DashBoard");

        } else if (id == R.id.menuDashBoardNavBarGovYojanas) {
            fragment = new FragmentDashboard();
            getSupportActionBar().setTitle("Yojanas");

        } else if (id == R.id.menuDashBoardNavBarBankServices) {
            fragment = new FragmentDashboard();
            getSupportActionBar().setTitle("Bank Services");

        } else if (id == R.id.menuDashBoardNavBarDigitalBonofied) {
            fragment = new FragmentDashboard();
            getSupportActionBar().setTitle("Digital Bonofied");

        } else if (id == R.id.menuDashBoardNavBarSubscribeService) {
            fragment = new FragmentDashboard();
            getSupportActionBar().setTitle("Subscribe Service");

        } else if (id == R.id.menuDashBoardNavBarPaymentsCircular) {
            fragment = new FragmentDashboard();
            getSupportActionBar().setTitle("Payment Circular");

        } else if (id == R.id.menuDashBoardNavBarKioskCenter) {

            Handler handler = new Handler();
            drawer.closeDrawer(GravityCompat.START);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(DashBoard.this, KioskCenter.class));
                }
            }, 500);
        } else if (id == R.id.menuDashBoardNavBarContactUs) {
            fragment = new FragmentContactUs();
            getSupportActionBar().setTitle("Contact Us");
        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dash_board, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menuDashBoardLogout) {
            startActivity(new Intent(this, SignIn.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        displaySelectedScreen(item.getItemId());

        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (exit) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Press Back again to Exit.", Toast.LENGTH_SHORT).show();
                exit = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        exit = false;
                    }
                }, 5000);
            }
        }
    }
}
