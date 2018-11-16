package repair_services.com.segf18_proj;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.lang.String;


public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private FirebaseUser mUser;

    private EditText UserName, Password;
    private Button Login, CreateNewUserAcc, CreateNewServiceProviderAcc;
    private String userrole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //firebase vars
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mUser = mAuth.getCurrentUser();

        //Input text fields
        UserName = (EditText) findViewById(R.id.name);
        Password = (EditText) findViewById(R.id.password);

        //Buttons
        Login = (Button) findViewById(R.id.LoginButton);
        CreateNewUserAcc=(Button) findViewById(R.id.createUserAccount);
        CreateNewServiceProviderAcc= (Button) findViewById(R.id.createServiceProviderAccount);

        //ON CLICK LISTENERS

        //login button
        Login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                checkLogin(UserName.getText().toString(), Password.getText().toString());
            }
        });

        //create user account button
        CreateNewUserAcc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                checkUser(UserName.getText().toString(), Password.getText().toString());
            }
        });

        //create service provider acc button
        CreateNewServiceProviderAcc.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                checkServiceProvider(UserName.getText().toString(), Password.getText().toString());
            }
        });
    }

    //checks if the user is already logged in, and sends to logged in page if true
    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null){
            Intent loginScreen = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(loginScreen);
        }
    }

    //called when login button is pressed
    private void checkLogin(String username, String password){
        if(!username.isEmpty() && !password.isEmpty()) {
            final Intent loginScreen = new Intent(MainActivity.this, LoginActivity.class);
            mAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        startActivity(loginScreen);
                    } else {
                        Toast.makeText(MainActivity.this,
                                "Cannot sign in. Please check your email and password and try again.", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
        else{
            UserName.setError("Required");
            Password.setError("Required");
        }
    }

    //called when create user account button is pressed
    private void checkUser(String username, String password){
        if(!username.isEmpty() && !password.isEmpty()) {
            userrole = "User";
            registerUser(username,password,userrole);
        }else{
            UserName.setError("Name Required");
            Password.setError("Password required");

        }
    }

    //called when create service provider account button is pressed
    //checks username and password ** still to implement
    private void checkServiceProvider(String username, String password) {
        if(!username.isEmpty() && !password.isEmpty()) {
            userrole = "Service Provider";
            registerUser(username,password,userrole);
        }else{
            UserName.setError("Name Required");
            Password.setError("Password required");

        }
    }

    //registers user to firebase database
    private void registerUser(final String username, final String password, final String userrole){
        final User mUser = new User (username, password, userrole);
        mAuth.createUserWithEmailAndPassword(username, password)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            mDatabase.getReference().child(mAuth.getCurrentUser().getUid()).setValue(mUser)
                                    .addOnCompleteListener(MainActivity.this, new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                finish();
                                                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                                            }
                                            else{
                                                Toast.makeText(MainActivity.this, "Firebase Database Error", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                            });
                        }
                        else if (!task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "Firebase Authentication Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}