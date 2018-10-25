package repair_services.com.segf18_proj;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CreateNewAccount extends AppCompatActivity {

    private Button createUser;
    //private Button createAdmain;
    private Button createServiceProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_account);
        createUser = (Button) findViewById(R.id.createUser);
        //createAdmain = (Button) findViewById(R.id.createAdmain);
        createServiceProvider = (Button) findViewById(R.id.createServiceProvider);

        createUser.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view){
                Intent intent = new Intent(CreateNewAccount.this, CreateUser.class);
                startActivity(intent);
            }
        });

        //only admain may create admain
        //createAdmain.setOnClickListener(new View.OnClickListener() {

        //    public void onClick(View view){
        //        Intent intent = new Intent(CreateNewAccount.this, CreateAdmain.class);
        //        startActivity(intent);
        //    }
        //});

        createServiceProvider.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view){
                Intent intent = new Intent(CreateNewAccount.this, CreateServiceProvider.class);
                startActivity(intent);
            }
        });
    }
}
