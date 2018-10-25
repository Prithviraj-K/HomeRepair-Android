package repair_services.com.segf18_proj;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private DatabaseReference accountsDatabase;
    private EditText Name;
    private EditText Password;
    private Button Login;
    private Button CreateNewAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        accountsDatabase = FirebaseDatabase.getInstance().getReference("accounts");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Name = (EditText) findViewById(R.id.name);
        Password = (EditText) findViewById(R.id.password);
        Login = (Button) findViewById(R.id.LoginButton);
        CreateNewAccount=(Button) findViewById(R.id.createAccount);

        //login
        Login.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view){
                checkPassword(Name.getText().toString(),Password.getText().toString());
            }
        });

        //create new account
        CreateNewAccount.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this, CreateNewAccount.class);
                startActivity(intent);
            }
        });

    }
    //called when log in is pressed
    //checks username and password
    private void checkPassword(String name, String password){
        if((name=="Admain")&&(password=="Admain")){
            Intent intent = new Intent(MainActivity.this, AdmainActivity.class);
            startActivity(intent);
        }
    }
}
