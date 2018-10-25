package repair_services.com.segf18_proj;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {

    private TextView welcome, textRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        welcome = (TextView) findViewById(R.id.textView);
        textRole = (TextView) findViewById(R.id.textrole);

        welcome.setText("Welcome "+ getIntent().getStringExtra("ADMIN") + ".");
        textRole.setText("Registered as: "+getIntent().getStringExtra("ROLE"));
    }
}
