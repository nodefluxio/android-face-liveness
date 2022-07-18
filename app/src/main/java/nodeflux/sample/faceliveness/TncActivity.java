package nodeflux.sample.faceliveness;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Objects;

import nodeflux.sdk.liveness.Liveness;
import nodeflux.sdk.liveness.NodefluxLivenessSDKOptions;

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

        NodefluxLivenessSDKOptions sdkOptions = new NodefluxLivenessSDKOptions();

        sdkOptions.setAccessKey(getResources().getString(R.string.access_key));
        sdkOptions.setSecretKey(getResources().getString(R.string.secret_key));
        sdkOptions.setThreshold(0.7);

        Liveness.setUpListener(sdkOptions, new Liveness.LivenessCallback() {
            @Override
            public void onSuccess(boolean b, String imageBase64, double score, String s1, JSONObject jsonObject) {
                String rootPath = getExternalCacheDir().toString();
                String filePath = rootPath + "/face.png";
                File root = new File(rootPath);
                try {
                    if (!root.exists()) {
                        root.mkdirs();
                    }
                    byte[] imageByte = Base64.decode(imageBase64, 0);

                    try (OutputStream stream = new FileOutputStream(filePath)) {
                        stream.write(imageByte);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                callResultActivty(true, "success", score, filePath);
            }

            @Override
            public void onSuccessWithSubmissionToken(String s, String s1) {

            }

            @Override
            public void onError(String message, JSONObject jsonObject) {
                callResultActivty(false, message, 0.0, null);
            }
        });
    }

    public void onLivenessPressed(View v) {
        startActivity(new Intent(TncActivity.this, Liveness.class));
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