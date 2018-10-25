package repair_services.com.segf18_proj;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class CreateAdmain extends AppCompatActivity {

    private EditText name;
    private EditText password;
    private EditText Email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_admain);
        name = (EditText) findViewById(R.id.admainName);
        password =(EditText) findViewById(R.id.admainPassword);
        Email = (EditText) findViewById(R.id.admainEmail);
    }
}
