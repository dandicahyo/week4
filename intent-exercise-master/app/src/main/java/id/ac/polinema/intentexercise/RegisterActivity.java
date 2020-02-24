package id.ac.polinema.intentexercise;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import javax.xml.transform.Result;

import de.hdodenhof.circleimageview.CircleImageView;

import static id.ac.polinema.intentexercise.ProfileActivity.USER_KEY;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText etFullname;
    private TextInputEditText etEmail;
    private TextInputEditText etPassword;
    private TextInputEditText etConfirmPw;
    private TextInputEditText etHomepage;
    private TextInputEditText etAbout;
    private Bitmap imageProfile;
    private CircleImageView profile;

    private static final int REQUEST_CODE_IMAGE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etFullname = findViewById(R.id.text_fullname);
        etEmail = findViewById(R.id.text_email);
        etPassword = findViewById(R.id.text_password);
        etConfirmPw = findViewById(R.id.text_confirm_password);
        etHomepage = findViewById(R.id.text_homepage);
        etAbout = findViewById(R.id.text_about);
        profile = findViewById(R.id.image_profile);

        //get default image profile
        imageProfile = BitmapFactory.decodeResource(getResources(), R.drawable.profile_picture);

        etHomepage.addTextChangedListener(new TextWatcher() {
            int count;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                this.count = count;
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().startsWith("http://") && count != 0) {
                    s.insert(0, "http://");
                }
            }
        });
    }

    public void handleRegister(View view) {
        if (TextUtils.isEmpty(etFullname.getText())) {
            Toast.makeText(this, "Username Kosong", Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(etEmail.getText()) ){
            Toast.makeText(this, "email Kosong", Toast.LENGTH_SHORT).show();

        } else if(TextUtils.isEmpty(etPassword.getText())){
            Toast.makeText(this, "password Kosong", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(etConfirmPw.getText())){
            Toast.makeText(this, "confirm Kosong", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(etHomepage.getText())){

            Toast.makeText(this, "Homepage Kosong", Toast.LENGTH_SHORT).show();
        }else if( TextUtils.isEmpty(etAbout.getText())){
            Toast.makeText(this, "about Kosong", Toast.LENGTH_SHORT).show();
        }
        else {
            if (!isValidEmail(etEmail.getText().toString())) {
                etEmail.setError("Format Email salah");
                Toast.makeText(this, "Format email salah", Toast.LENGTH_SHORT).show();

            } else {
                if(!validasi(etHomepage.getText().toString())){
                    Toast.makeText(this, "homepage salah (aa.com)", Toast.LENGTH_SHORT).show();
                }else{
                    if (TextUtils.equals(etPassword.getText().toString(), etConfirmPw.getText().toString())) {
                        UserModel userModel = new UserModel(etFullname.getText().toString(),
                                etEmail.getText().toString(),
                                etAbout.getText().toString(),
                                etHomepage.getText().toString(), Bitmap.createScaledBitmap(imageProfile, 150, 150, true));
                        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                        intent.putExtra(USER_KEY, userModel);
                        startActivity(intent);
                    } else {
                        Toast.makeText(this, "Please enter same password", Toast.LENGTH_SHORT).show();
                    }
                }

            }

        }
    }


    public void handleUploadImage(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) return;

        if (requestCode == REQUEST_CODE_IMAGE) {
            if (data != null) {
                try {
                    Uri imageUri = data.getData();
                     if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        imageProfile = ImageDecoder.decodeBitmap(ImageDecoder.createSource(getContentResolver(), imageUri));
                    } else {
                        imageProfile = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                    }
                    profile.setImageBitmap(imageProfile);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static boolean isValidEmail(String email) {
        boolean validate;
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String emailPattern2 = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+\\.+[a-z]+";

        if (email.matches(emailPattern)) {
            validate = true;
        } else if (email.matches(emailPattern2)) {
            validate = true;
        } else {
            validate = false;
        }
        return validate;
    }
    public static boolean validasi(String url) {
        boolean validate;
        String url1 = "[http://]+[a-zA-Z0-9._-]+.[com]+";

        if (url.matches(url1)) {
            validate = true;
        } else {
            validate = false;
        }
        return validate;
    }

}
