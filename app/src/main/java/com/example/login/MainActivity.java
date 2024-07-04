package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.xml.transform.Result;

public class MainActivity extends AppCompatActivity {
    Connection connection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnconnect = (Button) findViewById(R.id.button2);
        btnconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView Name = (TextView) findViewById(R.id.textView3);

                ConSQL c = new ConSQL();
                connection = c.conclass();


                if (c != null) {
                    try {
                        String sqlstatement = "select * from LOGIN";
                        Statement smt = connection.createStatement();
                        ResultSet set = smt.executeQuery(sqlstatement);

                        while (set.next()) {
                            // Assuming "username" is the column name, replace it with the actual column name
                            Name.setText(set.getString("password"));
                        }

                        connection.close();
                    } catch (Exception e) {
                        Log.e("Error:", e.getMessage());
                    }
                }
            }

        });

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.spinner_items,
                android.R.layout.simple_spinner_item

        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Handle the selected item
                String selectedItem = parentView.getItemAtPosition(position).toString();
                // Do something with the selected item
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });

        }
        private void startNextActivity(String password) {
            // Example: Starting a new activity and passing data to it
            // Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            // intent.putExtra("PASSWORD_EXTRA", password);
            // startActivity(intent);
        }


}
