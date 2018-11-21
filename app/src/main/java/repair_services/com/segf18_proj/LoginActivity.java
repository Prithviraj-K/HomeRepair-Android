package repair_services.com.segf18_proj;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mDatabase;

    private TextView welcome, textRole, profileText;
    private Button logOutButton, serviceButton, enterBtn;
    private EditText serviceAddress, servicePhone, serviceCompany, serviceDescription;
    private Switch licensed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        welcome = (TextView) findViewById(R.id.textView);
        textRole = (TextView) findViewById(R.id.textrole);

        serviceCompany = (EditText) findViewById(R.id.serviceCompany);
        serviceAddress = (EditText) findViewById(R.id.serviceAddress);
        serviceDescription = (EditText) findViewById(R.id.serviceDescription);
        servicePhone = (EditText) findViewById(R.id.servicePhone);
        profileText = (TextView) findViewById(R.id.profileText);


        licensed = (Switch) findViewById(R.id.licensed);

        enterBtn = (Button) findViewById(R.id.enterBtn);
        logOutButton = (Button) findViewById(R.id.logOutButton);
        serviceButton = (Button) findViewById(R.id.serviceButton);

        mDatabase.child(mUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                String username = user.getUsername();
                String userRole = user.getUserrole();

                if (userRole.equals("Admin")){
                    serviceButton.setVisibility(View.VISIBLE);
                    serviceCompany.setVisibility(View.INVISIBLE);
                    serviceAddress.setVisibility(View.INVISIBLE);
                    servicePhone.setVisibility(View.INVISIBLE);
                    serviceDescription.setVisibility(View.INVISIBLE);
                    licensed.setVisibility(View.INVISIBLE);
                    profileText.setVisibility(View.INVISIBLE);
                    enterBtn.setVisibility(View.INVISIBLE);
                }
                else if (userRole.equals("Service Provider")){
                    serviceButton.setVisibility(View.INVISIBLE);
                    serviceCompany.setVisibility(View.VISIBLE);
                    serviceAddress.setVisibility(View.VISIBLE);
                    servicePhone.setVisibility(View.VISIBLE);
                    serviceDescription.setVisibility(View.VISIBLE);
                    licensed.setVisibility(View.VISIBLE);
                    profileText.setVisibility(View.VISIBLE);
                    enterBtn.setVisibility(View.VISIBLE);
                }

                welcome.setText ("Welcome " + username);
                textRole.setText("Logged in as: "+ userRole);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //enter button for service providers
        enterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((serviceAddress.getText().length()!=0) && (servicePhone.getText().length()!=0) && (serviceCompany.getText().length()!=0)){
                    
                }
                else{
                    serviceAddress.setError("Required");
                    servicePhone.setError("Required");
                    serviceCompany.setError("Required");
                }
            }
        });
        //log out button
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                finish();
                Intent back = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(back);
            }
        });
        //services button for admin
        serviceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent adminServices = new Intent(LoginActivity.this, AdminServicesActivity.class);
                startActivity(adminServices);
            }
        });
    }
    //checks if the user is already logged in, and sends to logged in page if true
    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() == null){
            finish();
            Intent main = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(main);

        }
    }
}
