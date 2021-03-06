package repair_services.com.segf18_proj;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;

public class UserBook extends AppCompatActivity implements Serializable {
    FirebaseListAdapter<Service> adapter;

    ListView serviceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_book);

        serviceList = findViewById(R.id.serviceList);

        serviceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final String serviceRef = adapter.getRef(i).getKey();
                Intent userBookProvider = new Intent(UserBook.this, UserBookProvider.class);
                userBookProvider.putExtra("serviceClicked", serviceRef);
                startActivity(userBookProvider);
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
