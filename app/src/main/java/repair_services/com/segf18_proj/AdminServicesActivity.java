package repair_services.com.segf18_proj;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class AdminServicesActivity extends AppCompatActivity {

    FirebaseListAdapter<Service> adapter;

    ListView serviceList;
    Button addService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_services);

        addService = findViewById(R.id.addService);
        serviceList = findViewById(R.id.serviceList);

        serviceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final DatabaseReference itemRef = adapter.getRef(i);

                final int listBTNPosition = i;

                final AlertDialog updateDialog = new AlertDialog.Builder(AdminServicesActivity.this).create();
                updateDialog.setTitle("Edit Service");

                LinearLayout layoutUpdate = new LinearLayout(AdminServicesActivity.this);
                layoutUpdate.setOrientation(LinearLayout.VERTICAL);

                final EditText inputName = new EditText(AdminServicesActivity.this);
                inputName.setHint(""+adapter.getItem(i).getServName());
                inputName.setInputType(InputType.TYPE_CLASS_TEXT);
                layoutUpdate.addView(inputName);

                final EditText inputRate = new EditText(AdminServicesActivity.this);
                inputRate.setHint(""+adapter.getItem(i).getServRate());
                inputRate.setInputType(InputType.TYPE_CLASS_NUMBER);
                layoutUpdate.addView(inputRate);

                updateDialog.setView(layoutUpdate);

                updateDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String updatedName = inputName.getText().toString();
                        String updatedRate = inputRate.getText().toString();

                        if(inputName.getText().length()!=0 && inputRate.getText().length()!=0){
                            itemRef.child("servName").setValue(updatedName);
                            itemRef.child("servRate").setValue(updatedRate);
                            Toast.makeText(AdminServicesActivity.this,"Updated",Toast.LENGTH_SHORT).show();
                        }
                        else if (inputName.getText().length()!=0){
                            itemRef.child("servName").setValue(updatedName);
                            Toast.makeText(AdminServicesActivity.this,"Updated",Toast.LENGTH_SHORT).show();
                        }
                        else if (inputRate.getText().length()!=0){
                            itemRef.child("servRate").setValue(updatedRate);
                            Toast.makeText(AdminServicesActivity.this,"Updated",Toast.LENGTH_SHORT).show();
                        }
                        updateDialog.dismiss();
                    }
                });
                updateDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Delete",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                itemRef.removeValue();
                                updateDialog.dismiss();
                            }
                        });
                updateDialog.show();
        }});

        addService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent createActivity = new Intent(AdminServicesActivity.this, CreateActivity.class);
                startActivity(createActivity);
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
                itemRate.setText("$ " + model.getServRate());
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
}
