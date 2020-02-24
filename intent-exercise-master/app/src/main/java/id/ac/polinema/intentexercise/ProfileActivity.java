package id.ac.polinema.intentexercise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    public static final String USER_KEY = "user";
    private CircleImageView profil;
    private TextView tvAbout;
    private TextView tvFullname;
    private TextView tvEmail;
    private TextView tvHomepage;
    private UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profil      = findViewById(R.id.image_profile);
        tvAbout     = findViewById(R.id.label_about);
        tvFullname  = findViewById(R.id.label_fullname);
        tvEmail     = findViewById(R.id.label_email);
        tvHomepage  = findViewById(R.id.label_homepage);
//
        if (getIntent().getParcelableExtra(USER_KEY) != null){
            userModel = getIntent().getParcelableExtra(USER_KEY);

            profil.setImageBitmap(userModel.getProfileImage());
            tvAbout.setText(userModel.getAboutYou());
            tvFullname.setText(userModel.getFullname());
            tvEmail.setText(userModel.getEmail());
            tvHomepage.setText(userModel.getHomePage());
        }
    }

    public void handleVisit(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(userModel.getHomePage()));
        startActivity(intent);
    }
}
