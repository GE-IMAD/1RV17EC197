package com.example.mad_assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.CookieHandler;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Upload extends AppCompatActivity {
    int i;
    long j;

    public static ArrayList<String> dates = new ArrayList<String>();
    String[] country = { "General", "Psychiatrist", "Surgery", "Dermatology", "Other"};

    String value,name,days,date;
    CheckBox ch, ch1, ch2;
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        Spinner spin = (Spinner) findViewById(R.id.spinner);
        ch  = (CheckBox) findViewById(R.id.cbmorning);
        ch1 = (CheckBox) findViewById(R.id.cbnoon);
        ch2 = (CheckBox) findViewById(R.id.cbnight);
        Button upload = (Button) findViewById(R.id.uploadbtn);
        database = FirebaseDatabase.getInstance();
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                value=country[i];
                Toast.makeText(getApplicationContext(),country[i] , Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, country);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                Toast.makeText(Upload.this, date, Toast.LENGTH_SHORT).show();
                EditText ed = (EditText) findViewById(R.id.edname);
                EditText ed1 = findViewById(R.id.eddays);
                name = ed.getText().toString();
                days = ed1.getText().toString();
                DatabaseReference myRef3 = database.getReference(value);
                myRef3.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child("i").getValue()==null)
                            j=0;
                        else
                        j = dataSnapshot.child("i").getValue(long.class);
                        Toast.makeText(Upload.this,""+dataSnapshot.child("i").getValue(),Toast.LENGTH_LONG).show();
                   //DatabaseReference myref = database.getReference();
                        DatabaseReference myRef1 = database.getReference(value);
                        Map<String, Object> userUpdates1 = new HashMap<>();
                        j++;
                        userUpdates1.put("i",j);
                        myRef1.updateChildren(userUpdates1);
                        DatabaseReference myRef = database.getReference(value+"/Prescription"+j);
                        Map<String, Object> userUpdates = new HashMap<>();
                        userUpdates.put("Tablet name1", name);
                        userUpdates.put("Date",date);
                        userUpdates.put("No of days",days);
                        if (ch.isChecked()) {
                            userUpdates.put("Morning", "Yes");
                        } else {
                            userUpdates.put("Morning", "No");
                        }
                        if (ch1.isChecked()) {
                            userUpdates.put("Afternoon", "Yes");
                        } else {
                            userUpdates.put("Afternoon", "No");
                        }
                        if (ch2.isChecked()) {
                            userUpdates.put("Night", "Yes");
                        } else {
                            userUpdates.put("Night", "No");
                        }
                        myRef.setValue(userUpdates);
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w("tag", "Failed to read value.", error.toException());
                    }
                });

//                DatabaseReference myRef1 = database.getReference(value);
//                Map<String, Object> userUpdates1 = new HashMap<>();
//                i++;
//                userUpdates1.put("i",i);
//                myRef1.updateChildren(userUpdates1);
//                DatabaseReference myRef = database.getReference(value+"/Prescription"+j);
//                Map<String, Object> userUpdates = new HashMap<>();
//                userUpdates.put("Tablet name1", name);
//                userUpdates.put("Date",date);
//                userUpdates.put("No of days",days);
//                if (ch.isChecked()) {
//                    userUpdates.put("Morning", "Yes");
//                } else {
//                    userUpdates.put("Morning", "No");
//                }
//                if (ch1.isChecked()) {
//                    userUpdates.put("Afternoon", "Yes");
//                } else {
//                    userUpdates.put("Afternoon", "No");
//                }
//                if (ch2.isChecked()) {
//                    userUpdates.put("Night", "Yes");
//                } else {
//                    userUpdates.put("Night", "No");
//                }
//                myRef.setValue(userUpdates);
            }
        });



    }






}
