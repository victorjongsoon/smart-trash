package sg.edu.np.team2.smarttrash;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {

    private EditText nameTV, emailTV, passwordTV;
    //private Button buttonUploadBin;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference, databaseReference1;
    private String email, password, name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }

        nameTV = findViewById(R.id.editTextName);
        //buttonUploadBin = findViewById(R.id.buttonUploadBin);
        emailTV = findViewById(R.id.editTextEmail);
        passwordTV = findViewById(R.id.editTextPassword);
        progressBar = findViewById(R.id.progressBar);

        progressBar.setVisibility(View.GONE);


        //Get firbase authentication instance
        auth = FirebaseAuth.getInstance();

        databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Bin");

//UID,lat,lng,title,sensordata
//        buttonUploadBin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                createFirebaseBinData("1", "1,1.3321,103.771,Recycling Bin,20");
//                createFirebaseBinData("2", "2,1.3322,103.772,Recycling Bin,40");
//                createFirebaseBinData("3", "3,1.3323,103.773,Recycling Bin,60");
//                createFirebaseBinData("4", "4,1.3324,103.774,Recycling Bin,80");
//                createFirebaseBinData("5", "5,1.3325,103.775,Recycling Bin,-1");
//            }
//        });
    }


    //Method to create new user
    private void registerNewUser() {
        name = nameTV.getText().toString();
        email = emailTV.getText().toString();
        password = passwordTV.getText().toString();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(getApplicationContext(), "Please enter name...", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please enter email...", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_LONG).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(name, "User");
                            databaseReference = FirebaseDatabase.getInstance().getReference().child("User").child(auth.getUid());
                            databaseReference.setValue(user);

                            Toast.makeText(getApplicationContext(), "Registration successful!", Toast.LENGTH_LONG).show();

                            progressBar.setVisibility(View.GONE);

                            Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Registration failed! Please try again later", Toast.LENGTH_LONG).show();

                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }

    //temp method to upload bin details (Unused)
    private void createFirebaseBinData(String binUID, String binData) {
        Bin bin = new Bin(binData);
        databaseReference1.child(binUID).setValue(bin);
    }

    public void onRegisterButtonClick(View View) {
        registerNewUser();
    }

    public void onLoginClick(View view) {
        startActivity(new Intent(this, LoginActivity.class));
        overridePendingTransition(R.anim.slide_in_right, android.R.anim.slide_out_right);
    }
}