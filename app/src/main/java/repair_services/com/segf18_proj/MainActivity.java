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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.ArrayList;
import java.lang.String;


public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText UserName, Password;
    private Button Login, CreateNewUserAcc, CreateNewServiceProviderAcc, CreateAdminAcc;
    private String userrole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //firebase authorization
        mAuth = FirebaseAuth.getInstance();

        //Input text fields
        UserName = (EditText) findViewById(R.id.name);
        Password = (EditText) findViewById(R.id.password);

        //Buttons
        Login = (Button) findViewById(R.id.LoginButton);
        CreateNewUserAcc=(Button) findViewById(R.id.createUserAccount);
        CreateNewServiceProviderAcc= (Button) findViewById(R.id.createServiceProviderAccount);
        CreateAdminAcc = (Button) findViewById(R.id.createAdminAccount);

        //ON CLICK LISTENERS
        //login button **CHECK IF VALID**
        Login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                //checkLogin(UserName.getText().toString(), Password.getText().toString());
            }
        });

        //create user account button **ADD TO DATABASE NEXT AND CHECK IF VALID**
        CreateNewUserAcc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                checkUser(UserName.getText().toString(), Password.getText().toString());
            }
        });

        //create service provider acc button **ADD TO DATABASE NEXT AND CHECK IF VALID**
        CreateNewServiceProviderAcc.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                checkServiceProvider(UserName.getText().toString(), Password.getText().toString());
            }
        });

        //create admin acc button **ADD TO DATABASE NEXT**
        CreateAdminAcc.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                checkAdmin(UserName.getText().toString(), Password.getText().toString());
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null){
            //handle already logged in user
        }
    }

    //registers user to firebase database
    private void registerUser(final String username, final String password, final String userrole){
        //Toast.makeText(MainActivity.this, "registeruser", Toast.LENGTH_SHORT).show();
        mAuth.createUserWithEmailAndPassword(username, password)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "Success.", Toast.LENGTH_SHORT).show();
                            User user = new User (username, password, userrole);
                            FirebaseDatabase.getInstance().getReference()
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {

                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) {
                                        Toast.makeText(MainActivity.this, "Success.", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(MainActivity.this,"Unsuccessful.",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(MainActivity.this, "Unsuccessful.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /*
    //called when login button is pressed
    //checks username and password ** still to implement
    private void checkLogin(String name, String password){

        Intent loginScreen = new Intent(MainActivity.this, LoginActivity.class);
        loginScreen.putExtra("ADMIN", name);
        loginScreen.putExtra("ROLE", userrole); //***implement: get the role of user from database
        startActivity(loginScreen);
    }*/

    //called when create user account button is pressed
    //checks username and password ** still to implement
    private void checkUser(String username, String password){
        if(!username.isEmpty() && !password.isEmpty()) {
            Intent loginScreen = new Intent(MainActivity.this, LoginActivity.class);
            userrole = "User";
            registerUser(username,password,userrole);
            loginScreen.putExtra("ADMIN", username);
            loginScreen.putExtra("ROLE", userrole);
            startActivity(loginScreen);
        }else{
            UserName.setError("Name Required");
            Password.setError("Password required");

        }
    }

    //called when create service provider account button is pressed
    //checks username and password ** still to implement
    private void checkServiceProvider(String username, String password) {
        if(!username.isEmpty() && !password.isEmpty()) {
            Intent loginScreen = new Intent(MainActivity.this, LoginActivity.class);
            userrole = "Service Provider";
            registerUser(username,password,userrole);
            loginScreen.putExtra("ADMIN", username);
            loginScreen.putExtra("ROLE", userrole);
            startActivity(loginScreen);
        }else{
            UserName.setError("Name Required");
            Password.setError("Password required");

        }
    }
    //called when create admin account button is pressed
    //checks username and password
    private void checkAdmin(String name, String password) {
        //if name and pass = admin or Admin.. and email has @
        if (name.equalsIgnoreCase("admin")&& password.equalsIgnoreCase("admin")){
            Intent loginScreen = new Intent(MainActivity.this, LoginActivity.class);
            userrole = "Administrator";
            loginScreen.putExtra("ADMIN", name);
            loginScreen.putExtra("ROLE", userrole);
            startActivity(loginScreen);
        }
        else {
            Toast.makeText(getApplicationContext(), "Invalid username and password.", Toast.LENGTH_SHORT).show();
        }
    }
}