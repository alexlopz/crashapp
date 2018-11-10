package com.alex.crashdemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SharedPreferences pref;
    private SharedPreferences.Editor borr;
    //@BindView(R.id.button_action) Button buttonAction;
    @BindView(R.id.fullnameEtext) EditText fullName;
    @BindView(R.id.ciudadEtext) EditText ciudad;
    @BindView(R.id.telefonoEtext) EditText telefono;
    @BindView(R.id.profesionEtext) EditText profesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //TOOLBAR
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //OBTENER USUARIO
        pref = getApplicationContext().getSharedPreferences("Options", MODE_PRIVATE);
        borr = pref.edit();
        String user = pref.getString("USER", "");

        //ENVIAR USUARIO A CRASHLITYCS
        Crashlytics.setUserIdentifier(user);

        //FLOATIN BUTTON
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String fullnameData = fullName.getText().toString().trim();
                String ciudadData = ciudad.getText().toString().trim();
                String telefonoData = telefono.getText().toString().trim();
                String profesionData = profesion.getText().toString().trim();

                Crashlytics.log(Log.DEBUG, "datos_usario",
                        fullnameData+" / "+
                        ciudadData+" / "+
                        telefonoData+" / "+
                        profesionData);


                int telInt = 502 + Integer.parseInt(telefonoData);

                //Crashlytics.getInstance().crash();

                Snackbar.make(view, "Datos guardados", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                clearData();
            }
        });

        //DRAWER
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = ((NavigationView)findViewById(R.id.nav_view)).getHeaderView(0);

        //SETEAR USUARIO AL MENU LATERAL
        TextView userName = (TextView)header.findViewById(R.id.username);
        userName.setText(user);




//        buttonAction.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Crashlytics.getInstance().crash();
//            }
//        });


    }//onCreate

    private void clearData() {
        fullName.getText().clear();
        ciudad.getText().clear();
        telefono.getText().clear();
        profesion.getText().clear();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //SE BORRA EL USUARIO
            borr.remove("USER");
            borr.apply();
            goLoginScreen();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void goLoginScreen() {
        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
