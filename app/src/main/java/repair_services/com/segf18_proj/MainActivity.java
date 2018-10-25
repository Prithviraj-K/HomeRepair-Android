package repair_services.com.segf18_proj;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private DatabaseReference accountsDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        accountsDatabase = FirebaseDatabase.getInstance().getReference("accounts");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
