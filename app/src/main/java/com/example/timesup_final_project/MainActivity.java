package com.example.timesup_final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_main_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView = findViewById(R.id.nav_view);

        View headerView = navigationView.getHeaderView(0);
        TextView usernameTitle = headerView.findViewById(R.id.userNameTitle);
        TextView emailAdd = headerView.findViewById(R.id.emailAddTitle);
        TextView contactno = headerView.findViewById(R.id.contactNoTitle);
        usernameTitle.setText(CurrentUser.getFirstname() + " " + CurrentUser.getLastname());
        emailAdd.setText(CurrentUser.getEmail());
        contactno.setText(CurrentUser.getContactNo());

        drawerLayout = findViewById(R.id.drawer_main);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle
                (this, drawerLayout, toolbar,
                        R.string.navigation_drawer_open,
                        R.string.navigation_drawer_close
                );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        initialize();
    }

    public void setActionBarTitle(String title){
        getSupportActionBar().setTitle(title);
    }

    private void initialize(){
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.help_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.help:{
                Toast.makeText(getApplicationContext(), "Nice", Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else if(Navigation.findNavController(this, R.id.nav_host_fragment)
                .getCurrentDestination().getId()!= R.id.homeFragment){
            NavOptions navOptions = new NavOptions.Builder()
                    .setPopUpTo(R.id.main_nav, true).build();
            Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.homeFragment,
                    null, navOptions);
        }
        else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.home:{
                NavOptions navOptions = new NavOptions.Builder()
                        .setPopUpTo(R.id.main_nav, true).build();
                Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.homeFragment,
                        null, navOptions);
                break;
            }
            case R.id.account_info:{
                if (isValidDestination(R.id.accountFragment)) {
                    Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.accountFragment);
                }
                break;
            }
            case R.id.acount_settings:{
                if(isValidDestination(R.id.accountSettingsFragment)){
                    Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.accountSettingsFragment);
                }
                break;
            }
            case R.id.about:{
                if(isValidDestination(R.id.aboutFragment)){
                    Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.aboutFragment);
                }
            }
        }
        item.setChecked(true);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private boolean isValidDestination(int destination){
        return destination != Navigation.findNavController(this, R.id.nav_host_fragment)
                .getCurrentDestination().getId();
    }
}
