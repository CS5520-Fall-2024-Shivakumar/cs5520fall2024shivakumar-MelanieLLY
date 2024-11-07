package edu.northeastern.numad24fa_liuyiyang;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
//import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

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
        if (savedInstanceState != null) {
            contactsList = (List<Contact>) savedInstanceState.getSerializable("contactsList");
        }

        contactsRecyclerView = findViewById(R.id.contactsRecyclerView);
        contactsAdapter = new ContactsAdapter(this, contactsList);


        contactsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        contactsRecyclerView.setAdapter(contactsAdapter);

        FloatingActionButton addContactFab = findViewById(R.id.addContactFab);
        addContactFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddContactDialog();
            }
        });
    }

    private void showAddContactDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dialog_add_contact, null);

        final EditText contactNameEditText = dialogView.findViewById(R.id.contactNameEditText);
        final EditText contactPhoneEditText = dialogView.findViewById(R.id.contactPhoneEditText);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
//                    Toast.makeText(ContactsCollectorActivity.this, "Contact added", Toast.LENGTH_SHORT).show();

                    Snackbar.make(contactsRecyclerView, "Contact added", Snackbar.LENGTH_LONG)
                            .setAction("Undo", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    contactsList.remove(newContact);
                                    contactsAdapter.notifyDataSetChanged();
                                    Snackbar.make(contactsRecyclerView, "Contact creation undone", Snackbar.LENGTH_SHORT).show();
                                }
                            }).show();
                } else {
                    Toast.makeText(ContactsCollectorActivity.this, "Error: All details are needed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }
        public void showEditContactDialog(Contact contact, int position) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_edit_contact, null);

            if (dialogView == null) {
                Log.e("ContactsCollectorActivity", "layout problem");
                return;
            }

            builder.setView(dialogView);

            EditText editName = dialogView.findViewById(R.id.contactNameEditText);
            EditText editPhone = dialogView.findViewById(R.id.contactPhoneEditText);

            if (editName == null || editPhone == null) {
                Log.e("ContactsCollectorActivity", "EditText is null");
                return;
            }
            // 预填充现有数据
            editName.setText(contact.getName());
            editPhone.setText(contact.getPhoneNumber());

            builder.setTitle("Edit Contact");
            builder.setPositiveButton("Save", (dialog, which) -> {
                String newName = editName.getText().toString();
                String newPhone = editPhone.getText().toString();

                if (!newName.isEmpty() && !newPhone.isEmpty()) {
                    contact.setName(newName);
                    contact.setPhoneNumber(newPhone);
                    contactsAdapter.notifyItemChanged(position);
                    Snackbar.make(contactsRecyclerView, "Contact updated", Snackbar.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Error: All fields are required.", Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton("Cancel", null);
            builder.show();
        }

        public void deleteContact(int position) {
            if (position < 0 || position >= contactsList.size()) {
                Log.e("ContactsCollectorActivity", "Invalid position for deletion: " + position);
                return;
            }
            Contact deletedContact = contactsList.get(position);
            contactsList.remove(position);
            contactsAdapter.notifyItemRemoved(position);

            Snackbar.make(contactsRecyclerView, "Contact deleted", Snackbar.LENGTH_LONG)
                    .setAction("Undo", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            contactsList.add(position, deletedContact);
                            contactsAdapter.notifyItemInserted(position);
                        }
                    }).show();
        }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("contactsList", new ArrayList<>(contactsList));
    }
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });



}