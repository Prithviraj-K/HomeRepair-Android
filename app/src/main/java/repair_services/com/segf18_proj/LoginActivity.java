package repair_services.com.segf18_proj;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {

    private TextView welcome, textRole;
    private Button logOutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        welcome = (TextView) findViewById(R.id.textView);
        textRole = (TextView) findViewById(R.id.textrole);

        logOutButton = (Button) findViewById(R.id.logOutButton);

        welcome.setText("Welcome "+ getIntent().getStringExtra("ADMIN") + ".");
        textRole.setText("Logged as: "+getIntent().getStringExtra("ROLE"));

        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent back = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(back);
            }
        });
    }
}
