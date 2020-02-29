package com.example.timesup_final_project;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.DialogInterface;
import android.content.Intent;
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
    private NavOptions navOptions;

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
        navOptions = new NavOptions.Builder()
                .setPopUpTo(R.id.main_nav, true).build();
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
            Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.homeFragment,
                    null, navOptions);
        }
        else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.home:{
                if (isValidDestination(R.id.homeFragment)) {
                    Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.homeFragment);
                }
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
                break;
            }
            case R.id.logout:{
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.DialogTheme)
                        .setIcon(R.drawable.logout_icon)
                        .setTitle(getString(R.string.logout))
                        .setMessage(getString(R.string.logout_confirmation))
                        .setNeutralButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(getApplicationContext(), "Log-out cancelled.", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(getApplicationContext(), getString(R.string.goodbye) + " " + CurrentUser.getFirstname() + "...",
                                        Toast.LENGTH_SHORT).show();
                                Intent logout = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(logout);
                                finish();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
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
