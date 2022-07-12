package com.example.mylocationservice;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.Map;
import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity  {
    Button startservice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startservice=(Button)findViewById(R.id.btnStartService);
        checkForPermissions();


    }

    private void checkForPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
                            String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION};
                            requestPermissionLauncher.launch(permissions);

        }
        else{
            Toast.makeText(MainActivity.this, "NNN", Toast.LENGTH_SHORT).show();
        }
    }

    public void StartMyForeground(View view) {
        Intent intent = new Intent(this,MyForegroundLocationService.class);
        ContextCompat.startForegroundService(this,intent);
    }

    private ActivityResultLauncher<String[]> requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(),
            new ActivityResultCallback<Map<String, Boolean>>() {
                @Override
                public void onActivityResult(Map<String, Boolean> result) {
                    if (result != null) {
                        boolean fine = result.get(Manifest.permission.ACCESS_FINE_LOCATION);
                        boolean coarse = result.get(Manifest.permission.ACCESS_COARSE_LOCATION);
                        if (fine && coarse) { //permission was approved
                            Toast.makeText(MainActivity.this, "Permission granted", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(MainActivity.this, "App cannot work without location approval", Toast.LENGTH_SHORT).show();
                            MainActivity.this.finish();
                        }
                    }
                    else {
                        Toast.makeText(MainActivity.this, "App cannot work without location approval", Toast.LENGTH_SHORT).show();
                        MainActivity.this.finish();
                    }
                }
            });


}

