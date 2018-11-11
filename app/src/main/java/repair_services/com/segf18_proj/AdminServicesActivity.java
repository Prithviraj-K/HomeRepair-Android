package repair_services.com.segf18_proj;

import android.content.DialogInterface;
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

import java.util.ArrayList;

public class AdminServicesActivity extends AppCompatActivity {

    LinearLayout buttonContainer;
    FloatingActionButton fab;
    String serviceText = "";
    String servicePayText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_services);

        fab = findViewById(R.id.addService);
        buttonContainer = (LinearLayout) findViewById(R.id.buttonContainer);

        ArrayList<Button> service = new ArrayList<>();

        Button button = new Button(this);
        buttonContainer.addView(button);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialog();
            }
        });
    }

    private void createDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Services");

        View mView=getLayoutInflater().inflate(R.layout.service,null);

        final EditText serviceName = (EditText)mView.findViewById(R.id.ServiceType);
        final EditText servicePay = (EditText)mView.findViewById(R.id.Rate);
        Button createService=(Button)mView.findViewById(R.id.create);

        createService.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if(!serviceName.getText().toString().isEmpty()&&!servicePay.getText().toString().isEmpty()){
                    Toast.makeText(AdminServicesActivity.this,"create successful", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(AdminServicesActivity.this,"please fill in empty fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setView(serviceName);
        builder.setView(servicePay);

        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                serviceText = serviceName.getText().toString();
                servicePayText = servicePay.getText().toString();
                saveService();
                createButtonService();
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

    private void saveService(){

    }

    private void createButtonService(){

    }
}
