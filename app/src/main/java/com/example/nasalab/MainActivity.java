package com.example.nasalab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;


import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout dl;
    NavigationView nv;
    Toolbar tb;
    Fragment f,f1,f2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dl=findViewById(R.id.drawer);
        nv=findViewById(R.id.nav_view);
        tb=findViewById(R.id.toolbar);
        f1=new apod();
        f2=new lib();

        setSupportActionBar(tb);

        nv.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,dl,tb,R.string.nav_drawer_open,R.string.nav_drawer_close);
        dl.addDrawerListener(toggle);
        toggle.syncState();

        f=f2;

        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view_tag,f).commit();
            nv.setCheckedItem(R.id.lib);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.apod:
                f=f1;
                nv.setCheckedItem(R.id.apod);
                break;
            case R.id.lib:
                f=f2;
                nv.setCheckedItem(R.id.lib);
                break;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view_tag,f).commit();

        dl.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {

        if(Global.getFrag()==1){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view_tag,f2).commit();
            nv.setCheckedItem(R.id.lib);
        }
        else{
            super.onBackPressed();
        }

    }
}