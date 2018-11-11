package repair_services.com.segf18_proj;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminServicesActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    LinearLayout buttonContainer;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_services);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        fab = findViewById(R.id.addService);
        buttonContainer = (LinearLayout) findViewById(R.id.buttonContainer);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialog();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateButtons();
    }

    private void createDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Services");

        LinearLayout layoutText = new LinearLayout(this);
        final EditText serviceName = new EditText(this);
        final EditText servicePay = new EditText(this);
        serviceName.setHint("Set Service Name");
        servicePay.setHint("Set Hourly Rate (Exclude $)");
        serviceName.setInputType(InputType.TYPE_CLASS_TEXT);
        servicePay.setInputType(InputType.TYPE_CLASS_TEXT);

        layoutText.addView(serviceName);
        layoutText.addView(servicePay);
        builder.setView(layoutText);

        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String serviceText = serviceName.getText().toString();
                String servicePayText = servicePay.getText().toString();
                writeService(serviceText,servicePayText);
                updateButtons();
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
    private void writeService(String serviceText, String servicePayText){
        Service service = new Service (serviceText,servicePayText);
        mDatabase.child("Services").setValue(service);
    }

    private void updateButtons(){
        final ArrayList<Button> servicesList = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("Services")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                            Service service = dataSnapshot.getValue(Service.class);
                            final String name = service.getServName();
                            final String pay = service.getHourlypay();
                            Button button = new Button(getApplicationContext());
                            button.setText(name + ": $ " + pay);
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    createEditDialog(name, pay);
                                }
                            });
                            servicesList.add(button);
                            buttonContainer.addView(button);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(AdminServicesActivity.this, "Firebase Database Error",Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void createEditDialog(final String name, final String pay){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Service");

        LinearLayout layoutText = new LinearLayout(this);
        final EditText serviceName = new EditText(this);
        final EditText servicePay = new EditText(this);
        serviceName.setHint("Change Service Name");
        servicePay.setHint("Change Hourly Rate (Exclude $)");
        serviceName.setInputType(InputType.TYPE_CLASS_TEXT);
        servicePay.setInputType(InputType.TYPE_CLASS_TEXT);

        layoutText.addView(serviceName);
        layoutText.addView(servicePay);
        builder.setView(layoutText);

        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String serviceText = serviceName.getText().toString();
                String servicePayText = servicePay.getText().toString();
                writeService(serviceText,servicePayText);
                updateButtons();
            }
        });
        builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mDatabase.child("Services").child(name).removeValue();
                mDatabase.child("Services").child(pay).removeValue();
                updateButtons();
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
