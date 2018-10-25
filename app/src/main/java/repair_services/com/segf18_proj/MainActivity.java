package repair_services.com.segf18_proj;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.ArrayList;
import java.lang.String;


public class MainActivity extends AppCompatActivity {
    private DatabaseReference accountsDatabase;
    private EditText Name, Password;
    private Button Login, CreateNewUserAcc, CreateNewServiceProviderAcc, CreateAdminAcc;
    String userrole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // create instance of database
        accountsDatabase = FirebaseDatabase.getInstance().getReference();

        //Input text fields
        Name = (EditText) findViewById(R.id.name);
        Password = (EditText) findViewById(R.id.password);

        //Buttons
        Login = (Button) findViewById(R.id.LoginButton);
        CreateNewUserAcc=(Button) findViewById(R.id.createUserAccount);
        CreateNewServiceProviderAcc= (Button) findViewById(R.id.createServiceProviderAccount);
        CreateAdminAcc = (Button) findViewById(R.id.createAdminAccount);

        //ON CLICK LISTENERS
        //login button
        Login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                checkLogin(Name.getText().toString(), Password.getText().toString());
            }
        });

        //create user account button
        CreateNewUserAcc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                checkUser(Name.getText().toString(), Password.getText().toString());
            }
        });

        //create service provider acc button
        CreateNewServiceProviderAcc.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                checkServiceProvider(Name.getText().toString(), Password.getText().toString());
            }
        });

        //create admin acc button **ADD TO DATABASE NEXT **
        CreateAdminAcc.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                checkAdmin(Name.getText().toString(), Password.getText().toString());
            }
        });

    }

    //called when login button is pressed
    //checks username and password ** still to implement
    private void checkLogin(String name, String password){
        Intent loginScreen = new Intent(MainActivity.this, LoginActivity.class);
        loginScreen.putExtra("ADMIN", name);
        loginScreen.putExtra("ROLE", userrole); //***implement: get the role of user from database
        startActivity(loginScreen);
    }
    //called when create user account button is pressed
    //checks username and password ** still to implement
    private void checkUser(String name, String password){
        Intent loginScreen = new Intent(MainActivity.this, LoginActivity.class);
        userrole = "User";
        loginScreen.putExtra("ADMIN", name);
        loginScreen.putExtra("ROLE", userrole);
        startActivity(loginScreen);
    }
    //called when create service provider account button is pressed
    //checks username and password ** still to implement
    private void checkServiceProvider(String name, String password) {
        Intent loginScreen = new Intent(MainActivity.this, LoginActivity.class);
        userrole = "Service Provider";
        loginScreen.putExtra("ADMIN", name);
        loginScreen.putExtra("ROLE", userrole);
        startActivity(loginScreen);
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