package com.example.ar1;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.Manifest;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int cameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
        }
        super.onCreate(savedInstanceState);
        setTitle("A");
        setContentView(R.layout.activity_main);

        Button btn_to_B = (Button) findViewById(R.id.btn_to_B);

        btn_to_B.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, B.class);
                startActivity(intent);
            }
        });

    }

    // Disable back button
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

}