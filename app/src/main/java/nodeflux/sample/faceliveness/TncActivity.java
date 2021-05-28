package nodeflux.sample.faceliveness;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Objects;

import nodeflux.sdk.liveness.Liveness;

public class TncActivity extends AppCompatActivity {
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tnc);

        try {
            Objects.requireNonNull(this.getSupportActionBar()).hide();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        intent = new Intent(TncActivity.this, Liveness.class);
        intent.putExtra("ACCESS_KEY", "{ACCESS_KEY}");
        intent.putExtra("SECRET_KEY", "{SECRET_KEY}");
        intent.putExtra("THRESHOLD", 0.7);
        Liveness.setUpListener(new Liveness.LivenessCallback() {
            @Override
            public void onSuccess(boolean isLive, Bitmap bitmap, double score) {
                String rootPath = getExternalCacheDir().toString();
                String filePath = rootPath + "/face.png";
                File root = new File(rootPath);
                File file = new File(filePath);
                try {
                    if (!root.exists()) {
                        root.mkdirs();
                    }
                    FileOutputStream outStream = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 85, outStream);
                    outStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                callResultActivty(true, "success", score, filePath);
            }

            @Override
            public void onError(String message) {
                callResultActivty(false, message, 0.0, null);
            }
        });
    }

    public void onLivenessPressed(View v) {
        startActivity(intent);
    }

    public void callResultActivty(Boolean success, String message, Double score, String imagePath) {
        Intent myIntent = new Intent(TncActivity.this, ResultActivity.class);
        myIntent.putExtra("success", success);
        myIntent.putExtra("message", message);
        myIntent.putExtra("score", score);
        myIntent.putExtra("imagePath", imagePath);
        TncActivity.this.startActivity(myIntent);
    }


    public void onBackPressed(View v) {
        finish();
    }
}