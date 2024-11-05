package edu.northeastern.numad24fa_liuyiyang;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class ContactsCollectorActivity extends AppCompatActivity {
    private RecyclerView contactsRecyclerView;
    private ContactsAdapter contactsAdapter;
    private List<Contact> contactsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_contacts_collector);
        contactsList = new ArrayList<>();
        contactsRecyclerView = findViewById(R.id.contactsRecyclerView);
        contactsAdapter = new ContactsAdapter(contactsList);

        contactsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        contactsRecyclerView.setAdapter(contactsAdapter);

        FloatingActionButton addContactFab = findViewById(R.id.addContactFab);
        addContactFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(ContactsCollectorActivity.this);
                View dialogView = inflater.inflate(R.layout.dialog_add_contact, null);

                final EditText contactNameEditText = dialogView.findViewById(R.id.contactNameEditText);
                final EditText contactPhoneEditText = dialogView.findViewById(R.id.contactPhoneEditText);

                AlertDialog.Builder builder = new AlertDialog.Builder(ContactsCollectorActivity.this);
                builder.setTitle("Add New Contact");
                builder.setView(dialogView);

                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = contactNameEditText.getText().toString();
                        String phone = contactPhoneEditText.getText().toString();
                        if (!name.isEmpty() && !phone.isEmpty()) {
                            Contact newContact = new Contact(name, phone);
                            contactsList.add(newContact);
                            contactsAdapter.notifyDataSetChanged();
                            Toast.makeText(ContactsCollectorActivity.this, "Contact added", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(ContactsCollectorActivity.this, "Error: All details are needed. ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.create().show();










            }
        });

//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
    }
}