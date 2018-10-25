package repair_services.com.segf18_proj;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdmainActivity extends AppCompatActivity {

    private Button addNewAdmain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admain);

        addNewAdmain=(Button) findViewById(R.id.addNewAdmain);

        addNewAdmain.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view){
                Intent intent = new Intent(AdmainActivity.this, CreateAdmain.class);
                startActivity(intent);
            }
        });
    }
}
