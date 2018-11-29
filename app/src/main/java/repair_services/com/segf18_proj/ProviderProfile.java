package repair_services.com.segf18_proj;

import android.content.DialogInterface;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
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

import java.security.Provider;

public class ProviderProfile extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mDatabase;
    FirebaseListAdapter<ProviderAvailablity> listAdapter;

    TextView textService, logged, address, phone, company, description, currentServicesText;
    Button addService, logoutBtn;
    ListView profileServicesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_profile);

        addService = findViewById(R.id.addService);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        textService = findViewById(R.id.textService);
        currentServicesText = findViewById(R.id.currentServicesText);
        logged = findViewById(R.id.logged);
        address = findViewById(R.id.address);
        phone = findViewById(R.id.phone);
        company = findViewById(R.id.Company);
        description = findViewById(R.id.description);
        profileServicesList = findViewById(R.id.profileServiceList);
        logoutBtn = findViewById(R.id.logoutBtn);

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                finish();
                Intent back = new Intent(ProviderProfile.this, MainActivity.class);
                startActivity(back);
            }
        });

        addService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProviderProfile.this, AddToProviderProfile.class);
                startActivity(intent);
            }
        });

        mDatabase.child(mUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                String username = user.getUsername();
                logged.setText("Logged in as: "+ username);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mDatabase.child(mUser.getUid()).child("Info").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProviderInfo info = dataSnapshot.getValue(UserProviderInfo.class);
                String naddress = info.getAddress();
                String nphone = info.getPhoneNum();
                String ncompany = info.getCompany();
                String ndescription = info.getServDescription();

                address.setText("Address: "+naddress);
                phone.setText("Phone: "+nphone);
                company.setText("Company: "+ ncompany);
                description.setText(ndescription);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        profileServicesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final DatabaseReference itemRef = listAdapter.getRef(i);
                final String serviceKey = itemRef.getKey();

                final AlertDialog.Builder updateDialog = new AlertDialog.Builder(ProviderProfile.this);
                View mView = getLayoutInflater().inflate(R.layout.spinner_dialogue,null);
                updateDialog.setTitle("Edit Day and Time");
                //cast the views
                final Spinner spinnerDay = (Spinner) mView.findViewById(R.id.spinnerDay);
                final Spinner spinnerTime = (Spinner) mView.findViewById(R.id.spinnerTime);

                ArrayAdapter<String> adapterDay = new ArrayAdapter<String>(ProviderProfile.this,android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.DayList));
                adapterDay.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDay.setAdapter(adapterDay);

                ArrayAdapter<String> adapterTime = new ArrayAdapter<String>(ProviderProfile.this,android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.TimeList));
                adapterTime.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerTime.setAdapter(adapterTime);

                updateDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String day = spinnerDay.getSelectedItem().toString();
                        String time = spinnerTime.getSelectedItem().toString();
                        itemRef.child("dayOfWeek").setValue(day);
                        itemRef.child("serviceTime").setValue(time);
                        dialogInterface.dismiss();
                    }
                });

                updateDialog.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DatabaseReference providerRef = mDatabase.child(serviceKey).child("Providers");
                        providerRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                                    String userID = (String) postSnapshot.getValue();
                                    if(userID.equals(mUser.getUid())){
                                        postSnapshot.getRef().removeValue();
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        itemRef.removeValue();
                        dialogInterface.dismiss();
                    }
                });
                updateDialog.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                updateDialog.setView(mView);
                AlertDialog dialog = updateDialog.create();
                dialog.show();
            }
        });

        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child(mUser.getUid())
                .child("Services");

        FirebaseListOptions<ProviderAvailablity> options = new FirebaseListOptions.Builder<ProviderAvailablity>()
                .setLayout(R.layout.service_item_layout)
                .setQuery(query, ProviderAvailablity.class)
                .build();
        listAdapter = new FirebaseListAdapter<ProviderAvailablity>(options) {
            @Override
            protected void populateView(View v, final ProviderAvailablity model, int position) {
                // Bind the service to the view
                TextView itemName = v.findViewById(R.id.servName);
                TextView itemRate = v.findViewById(R.id.servRate);

                itemName.setText(""+model.getServiceName());
                itemRate.setText(""+model.getDayOfWeek()+" - "+model.getserviceTime());
            }
        };
        profileServicesList.setAdapter(listAdapter);
    }
    @Override
    protected void onStart() {
        super.onStart();
        listAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        listAdapter.stopListening();
    }
}
