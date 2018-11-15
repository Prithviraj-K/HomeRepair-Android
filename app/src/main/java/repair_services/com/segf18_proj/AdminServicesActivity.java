package repair_services.com.segf18_proj;

import android.content.ContentProvider;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminServicesActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    FirebaseListAdapter<Service> adapter;


    ArrayList<Button> aList;
    ListView serviceList;
    Button addService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_services);

        mDatabase = FirebaseDatabase.getInstance().getReference("Services");

        addService = (Button) findViewById(R.id.addService);
        serviceList = (ListView) findViewById(R.id.serviceList);

        addService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialog();
            }
        });

        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("Services");

        FirebaseListOptions<Service> options = new FirebaseListOptions.Builder<Service>()
                .setLayout(R.layout.service_item_layout)
                .setQuery(query, Service.class)
                .build();

        adapter = new FirebaseListAdapter<Service>(options) {
            @Override
            protected void populateView(View v, final Service model, int position) {
                // Bind the service to the view
                TextView itemName = v.findViewById(R.id.servName);
                TextView itemRate = v.findViewById(R.id.servRate);

                itemName.setText(model.getServName());
                itemRate.setText(model.getServRate());
            }
        };
        serviceList.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    private void createDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add a Service");

        LinearLayout layoutText = new LinearLayout(this);
        final EditText serviceName = new EditText(this);
        final EditText servicePay = new EditText(this);
        serviceName.setHint("Service Name");
        servicePay.setHint("Hourly Rate");

        layoutText.addView(serviceName);
        layoutText.addView(servicePay);
        builder.setView(layoutText);

        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (serviceName.getText().toString().length()!=0 || servicePay.getText().toString().length() !=0 || TextUtils.isDigitsOnly(servicePay.getText()))  {
                    String serviceText = serviceName.getText().toString();
                    String servicePayText = servicePay.getText().toString();
                    Service service = new Service (serviceText,servicePayText);
                    mDatabase.push().setValue(service);
                }
                else{
                    Toast.makeText(AdminServicesActivity.this, "Invalid entry.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.show();
    }

    //save service to firebase
    /*private void writeService(String serviceName, String serviceRate){
        Service service = new Service (serviceName,serviceRate);
        mDatabase.child("Services").setValue(serviceName);
        mDatabase.child("Services").child(serviceName).setValue(serviceRate);
    }*/

    private void createEditDialog(final Service service){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit "+ service.getServName() + " Service");
        LinearLayout layoutText = new LinearLayout(this);
        final EditText serviceName = new EditText(this);
        final EditText servicePay = new EditText(this);
        serviceName.setHint("Change Service Name");
        servicePay.setHint("Change Rate ($)");
        serviceName.setInputType(InputType.TYPE_CLASS_TEXT);
        servicePay.setInputType(InputType.TYPE_CLASS_TEXT);

        layoutText.addView(serviceName);
        layoutText.addView(servicePay);
        layoutText.addView(servicePay);
        builder.setView(layoutText);

        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String serviceText = serviceName.getText().toString();
                String servicePayText = servicePay.getText().toString();
                if (serviceText.length()!=0 && servicePayText.length() !=0) {
                    service.setServName(serviceText);
                    service.setRate(servicePayText);
                }
                else{
                    serviceName.setError("Name required");
                    servicePay.setError("Rate required");
                }
            }
        });
        builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mDatabase.child(service.getServName()).removeValue();
            }
        });
        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.show();
    }
}
