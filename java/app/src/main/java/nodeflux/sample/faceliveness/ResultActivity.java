package nodeflux.sample.faceliveness;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        try {
            Objects.requireNonNull(this.getSupportActionBar()).hide();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        CircleImageView circleImageView = findViewById(R.id.profile_image);
        TextView tvTitle = findViewById(R.id.result_title);
        TextView tvMessage = findViewById(R.id.message);
        TextView tvScore = findViewById(R.id.score);
        TextView btnBack = findViewById(R.id.back_btn);

        Intent intent = getIntent();
        boolean success = intent.getBooleanExtra("success", false);
        String message = intent.getStringExtra("message");
        double score = intent.getDoubleExtra("score", 0);
        String imagePath = intent.getStringExtra("imagePath");


        if (success) {
            File image = new File(imagePath);
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath(), bmOptions);

            tvTitle.setText("Verification Success");
            tvMessage.setText("Your face has been verified, and the Liveness score is:");
            tvScore.setText(String.format("%.2f", 100 * score) + "%");
            circleImageView.setImageBitmap(bitmap);
        } else {
            tvTitle.setText("Verification Failed");
            tvMessage.setText(message);
            tvScore.setVisibility(View.INVISIBLE);
        }

        btnBack.setOnClickListener(v -> {
            Intent gotoScreenVar = new Intent(ResultActivity.this, MainActivity.class);
            gotoScreenVar.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(gotoScreenVar);
        });
    }

    @Override
    public void onBackPressed() {
        Intent gotoScreenVar = new Intent(ResultActivity.this, MainActivity.class);
        gotoScreenVar.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(gotoScreenVar);
    }
}