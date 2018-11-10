package com.alex.crashdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.BatteryManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.crashlytics.android.Crashlytics;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    //SE REFERENCIAN LAS VISTAS
    @BindView(R.id.user_etext) EditText userEtext;
    @BindView(R.id.password_etext) EditText passwordEtext;
    @BindView(R.id.submit_login_btn) Button submitBtn;

    private String user;
    private SharedPreferences sharePref;
    SharedPreferences.Editor saveEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //SE OBTIENE EL USER A TRAVES DE SharedPreferences
        sharePref = getApplicationContext().getSharedPreferences("Options", MODE_PRIVATE);
        saveEditor = sharePref.edit();
        user = sharePref.getString("USER", "N0_USER");


        if (!user.equals("N0_USER")) {
            goToMainScreen();
        } else {
            //SE DIBUJA EL LAYOUT
            setContentView(R.layout.activity_login);
            ButterKnife.bind(this);

            //BOTON QUE CAPTURA EL USUARIO Y CONTRASENA
            submitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    user = userEtext.getText().toString().trim();
                    String passwordUser = passwordEtext.getText().toString().trim();
                    Log.d("user", String.valueOf(user + "-" + passwordUser));
                    //ENVIAR USUARIO DE CRASHLITYIS

                    saveEditor.putString("USER", user);
                    saveEditor.apply();
                    goToMainScreen();
                }
            });
        }

        //NIVEL DE BATERIA EN PORCENTAJE
        BroadcastReceiver bateriaReciever = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                context.unregisterReceiver(this);
                int currentLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL,-1);
                int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE,-1);
                int level = -1;
                if (currentLevel >=0 && scale > 0){
                    level= (currentLevel * 100)/scale;
                }

                Crashlytics.setString("BATERIA", String.valueOf(level+"%"));

            }
        };
        IntentFilter batteryFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(bateriaReciever,batteryFilter);

    }//onCreate

    private void goToMainScreen() {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}
