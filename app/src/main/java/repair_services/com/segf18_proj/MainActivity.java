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
    private EditText Name, Password, Email;
    private Button Login, CreateNewUserAcc, CreateNewServiceProviderAcc, CreateAdminAcc;
    String userrole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // create instance of database
        accountsDatabase = FirebaseDatabase.getInstance().getReference("accounts");

        //Input text fields
        Name = (EditText) findViewById(R.id.name);
        Password = (EditText) findViewById(R.id.password);
        Email = (EditText) findViewById(R.id.email);

        //Buttons
        Login = (Button) findViewById(R.id.LoginButton);
        CreateNewUserAcc=(Button) findViewById(R.id.createUserAccount);
        CreateNewServiceProviderAcc= (Button) findViewById(R.id.createServiceProviderAccount);
        CreateAdminAcc = (Button) findViewById(R.id.createAdminAccount);

        //ON CLICK LISTENERS
        //login button
        Login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                checkLogin(Name.getText().toString(),Email.getText().toString(), Password.getText().toString());
            }
        });

        //create user account button
        CreateNewUserAcc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                checkLogin(Name.getText().toString(),Email.getText().toString(), Password.getText().toString());
            }
        });

        //create service provider acc button
        CreateNewServiceProviderAcc.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                checkLogin(Name.getText().toString(),Email.getText().toString(), Password.getText().toString());
            }
        });

        //create admin acc button
        CreateAdminAcc.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                String name = Name.getText().toString();
                String password = Password.getText().toString();
                //name and pass = admin or Admin
                if (name.equalsIgnoreCase("admin")&& password.equalsIgnoreCase("admin")){
                    Intent loginScreen = new Intent(MainActivity.this, LoginActivity.class);
                    loginScreen.putExtra("ADMIN", name);
                    loginScreen.putExtra("ROLE", userrole);
                    startActivity(loginScreen);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Invalid admin", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    //called when login button is pressed
    //checks username and password
    private void checkLogin(String name, String Email, String password){

    }
}
