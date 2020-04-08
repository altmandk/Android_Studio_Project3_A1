package com.example.project_3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private int CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button attractionsButton = (Button) findViewById(R.id.attractionsButton);
        Button restaurantsButton = (Button) findViewById(R.id.restaurantsButton);

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                "edu.uic.cs478.sp2020.project3") == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "You have the correct permission to run this app", Toast.LENGTH_LONG).show();
        } else {
            requestPermission();
        }

        attractionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Transferring to attractions page", Toast.LENGTH_SHORT).show();
                sendAttractionsBroadcast(v);
            }
        });

        restaurantsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Transferring to restaurants page", Toast.LENGTH_SHORT).show();
                sendRestaurantsBroadcast(v);
            }
        });


    }

    public void sendAttractionsBroadcast(View view) {
        Intent attractionsIntent = new Intent("edu.uic.cs478.sp2020.project3.ACTION");
        attractionsIntent.putExtra("edu.uic.cs478.sp2020.project3.TEXT", "Attraction Broadcast Received");
        sendBroadcast(attractionsIntent);
    }

    public void sendRestaurantsBroadcast(View view) {
        Intent restaurantsIntent = new Intent("edu.uic.cs478.sp2020.project3.ACTION");
        restaurantsIntent.putExtra("edu.uic.cs478.sp2020.project3.TEXT", "Restaurant Broadcast Received");
        sendBroadcast(restaurantsIntent);
    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, "edu.uic.cs478.sp2020.project3")) {
            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("A custom permission is needed to use this app")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{"edu.uic.cs478.sp2020.project3"}, CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{"edu.uic.cs478.sp2020.project3"}, CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
                //System.exit(0);
            }
        }
    }
}
