package nodeflux.sample.faceliveness;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import nodeflux.sdk.liveness.Liveness;

public class MainActivity extends AppCompatActivity {
    private static final int CAMERA_PERMISSION_CODE = 100;
    private static final int STORAGE_PERMISSION_CODE = 101;
    Intent intent;
    Group intro;
    Group success;
    Group fail;
    Button startBtn;
    Boolean isFirst = true;

    TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intro = findViewById(R.id.intro);
        success = findViewById(R.id.success);
        fail = findViewById(R.id.fail);
        startBtn = findViewById(R.id.button);
        message = findViewById(R.id.message);

        try {
            Objects.requireNonNull(this.getSupportActionBar()).hide();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        setView(0, null);

        intent = new Intent(MainActivity.this, Liveness.class);
        intent.putExtra("ACCESS_KEY", "{ACCESS_KEY}");
        intent.putExtra("SECRET_KEY", "{SECRET_KEY}");
        intent.putExtra("THRESHOLD", 0.7);
        Liveness.setUpListener(new Liveness.LivenessCallback() {
            @Override
            public void onSuccess(boolean isLive, Bitmap bitmap, double score) {
                if (isLive) {
                    setView(1, null);
                } else {
                    setView(2, "Let's try again to check your liveness");
                }
                Toast.makeText(getApplicationContext(), String.valueOf(score), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(String message) {
                setView(2, message);
//                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void onLivenessPressed(View v) {
        isFirst = false;
        if (!isCameraPermissionGranted()) {
            requestCameraPermission();
        } else {
            startActivity(intent);
        }

    }

    public void setView(int state, String errMessage) {
        if (state == 1) {
            intro.setVisibility(View.GONE);
            success.setVisibility(View.VISIBLE);
            fail.setVisibility(View.GONE);
            if (!isFirst) {
                startBtn.setText("Start Over");
                message.setText(errMessage);
            }

        } else if (state == 2) {
            intro.setVisibility(View.GONE);
            success.setVisibility(View.GONE);
            fail.setVisibility(View.VISIBLE);
            startBtn.setText("Try Again");
            message.setText(errMessage);
        } else {
            intro.setVisibility(View.VISIBLE);
            success.setVisibility(View.GONE);
            fail.setVisibility(View.GONE);
            message.setText(errMessage);
        }
    }

    public boolean isCameraPermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    public void requestCameraPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_PERMISSION_CODE) {
            // Checking whether user granted the permission or not.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this,
                        "Camera Permission Granted",
                        Toast.LENGTH_LONG)
                        .show();
            } else {
                Toast.makeText(MainActivity.this,
                        "Camera Permission Denied",
                        Toast.LENGTH_LONG)
                        .show();
            }
        } else if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this,
                        "Storage Permission Granted",
                        Toast.LENGTH_LONG)
                        .show();
            } else {
                Toast.makeText(MainActivity.this,
                        "Storage Permission Denied",
                        Toast.LENGTH_LONG)
                        .show();
            }
        }
    }
}