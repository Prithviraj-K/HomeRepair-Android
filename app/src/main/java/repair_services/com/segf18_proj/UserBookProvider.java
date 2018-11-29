package repair_services.com.segf18_proj;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class UserBookProvider extends AppCompatActivity {

    Button cancelBtn;
    String serviceRef;
    ListView providerList;
    FirebaseListAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_book_provider);

        cancelBtn = findViewById(R.id.cancelBtn);
        providerList = findViewById(R.id.providerList);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null){
            serviceRef = (String) bundle.get("serviceClicked");
        }

        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("Services")
                .child(serviceRef).child("Providers");

        FirebaseListOptions<String> options = new FirebaseListOptions.Builder<String>()
                .setLayout(R.layout.service_item_layout)
                .setQuery(query, String.class)
                .build();

        adapter = new FirebaseListAdapter<String>(options) {
            @Override
            protected void populateView(View v, final String model, int position) {
                // Bind the provider to the view
                TextView itemName = v.findViewById(R.id.servName);
                TextView itemRate = v.findViewById(R.id.servRate);

                itemName.setText(model);
            }
        };
        providerList.setAdapter(adapter);
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
