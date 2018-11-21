package repair_services.com.segf18_proj;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;

    EditText serviceName, serviceRate;
    Button cancel,create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        mDatabase = FirebaseDatabase.getInstance().getReference("Services");

        serviceName = findViewById(R.id.servName);
        serviceRate = findViewById(R.id.servRate);
        cancel = findViewById(R.id.cancelBtn);
        create = findViewById(R.id.createBtn);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent serviceAdmin = new Intent(CreateActivity.this, AdminServicesActivity.class);
                startActivity(serviceAdmin);
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String serviceText = serviceName.getText().toString();
                String serviceRateText = serviceRate.getText().toString();
                if (serviceText.isEmpty()){
                    serviceName.setError("Required");
                }
                else if(serviceRateText.isEmpty()){
                    serviceRate.setError("Required");
                }
                else{
                    createService(serviceText, serviceRateText);
                    Toast.makeText(CreateActivity.this, "Created", Toast.LENGTH_SHORT).show();
                    finish();
                    Intent serviceAdmin = new Intent(CreateActivity.this, AdminServicesActivity.class);
                    startActivity(serviceAdmin);
                }
            }
        });

    }

    private void createService(String serviceName, String serviceRate){
        Service service = new Service (serviceName,serviceRate);
        mDatabase.push().setValue(service);
    }
}
