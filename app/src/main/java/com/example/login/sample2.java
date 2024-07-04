package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class sample2 extends AppCompatActivity {
    Connection connection;
    EditText editTextEcno;
    TextView textViewName;
    Spinner spinnerbranch;

    AutoCompleteTextView autoCompleteSite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample2);

        editTextEcno = findViewById(R.id.editText1);
        textViewName = findViewById(R.id.editText2);
        spinnerbranch = findViewById(R.id.spinner1);
        autoCompleteSite = findViewById(R.id.autoCompleteTextView2);



// Set the background color directly
        spinnerbranch.setBackgroundColor(getResources().getColor(android.R.color.white));

        autoCompleteSite.setBackgroundColor(getResources().getColor(android.R.color.white));




        ArrayAdapter<CharSequence> branchAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.branch_array,
                android.R.layout.simple_spinner_item
        );
        branchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerbranch.setAdapter(branchAdapter);

        // Fetch and set the data for the site spinner
        List<String> sites = fetchSitesFromDatabase();
        ArrayAdapter<String> siteAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                sites
        );
        autoCompleteSite.setAdapter(siteAdapter);

        // Button for Update
        Button buttonUpdate = findViewById(R.id.button);
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ecno = editTextEcno.getText().toString().trim();
                String name = textViewName.getText().toString().trim();
                String selectedSite = autoCompleteSite.getText().toString().trim();
                String selectedBranch = spinnerbranch.getSelectedItem().toString();

                if (!ecno.isEmpty() && !name.isEmpty()) {
                    performUpdate(ecno, name, selectedSite, selectedBranch);
                } else {
                    // Handle empty ECNO or name
                    textViewName.setText("Please enter ECNO and Name");
                }
            }
        });


        editTextEcno.addTextChangedListener(new TextWatcher() {

            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // This method is called to notify you that somewhere within charSequence, the text has been changed
                String ecnoToSearch = charSequence.toString().trim();
                if (!ecnoToSearch.isEmpty()) {
                    String selectedCity =  spinnerbranch.getSelectedItem().toString();
                    String selectedState = autoCompleteSite.getText().toString();
                    retrieveNameForEcno(ecnoToSearch);
                } else {
                    textViewName.setText("Please enter ECNO");
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
                // Not needed for your case, but must be implemented due to the interface
            }

        });
    }

    private void performUpdate(String ecno, String name, String selectedSite, String selectedBranch) {
        try {
            ConSQL c = new ConSQL();
            connection = c.conclass();

            if (c != null) {
                // Update query with conditions
                String updateQuery;

                if (!selectedSite.isEmpty()) {
                    // Update with site condition
                    updateQuery = "UPDATE EMPMAS SET EMPSITE = (SELECT PROJECTID FROM PROJECTMASTER WHERE PROJECTNAME = '" + selectedSite + "') WHERE EMPCODE = '" + ecno + "'";
                } else {
                    // Update without site condition
                    updateQuery = "UPDATE EMPMAS SET WHERE EMPCODE = '" + ecno + "'";
                }

                Statement smt = connection.createStatement();
                int rowsAffected = smt.executeUpdate(updateQuery);

                if (rowsAffected > 0) {
                    // Handle success or notify the user about the update
                    textViewName.setText("Update successful for ECNO: " + ecno);
                } else {
                    textViewName.setText("Invalid ECNO: " + ecno);
                }

                connection.close();
            }
        } catch (Exception e) {
            Log.e("Error:", e.getMessage());
            textViewName.setText("Error: " + e.getMessage());
        }
    }

    private boolean isEcnoValid(String ecno) {
        return !ecno.isEmpty() && ecno.length() == 5;
    }

    private List<String> fetchSitesFromDatabase() {
        List<String> sites = new ArrayList<>();

        try {
            ConSQL c = new ConSQL();
            connection = c.conclass();

            if (c != null) {
                String sqlStatement = "SELECT DISTINCT PROJECTNAME FROM PROJECTMASTER";
                Statement smt = connection.createStatement();
                ResultSet set = smt.executeQuery(sqlStatement);

                while (set.next()) {
                    String site = set.getString("PROJECTNAME");
                    sites.add(site);
                }

                connection.close();
            }
        } catch (Exception e) {
            Log.e("Error:", e.getMessage());
        }

        return sites;
    }

    private void retrieveNameForEcno(String ecnoToSearch) {
        try {
            ConSQL c = new ConSQL();
            connection = c.conclass();

            if (c != null) {
                String sqlStatement = "SELECT EMPNAME FROM EMPMAS WHERE EMPCODE = '" + ecnoToSearch + "'";
                Statement smt = connection.createStatement();
                ResultSet set = smt.executeQuery(sqlStatement);

                if (set.next()) {
                    String name = set.getString("EMPNAME");
                    textViewName.setText("Name: " + name);
                } else {
                    // Handle case when no matching ECNO is found
                    textViewName.setText("No matching ECNO found");
                }

                connection.close();
            }
        } catch (Exception e) {
            Log.e("Error:", e.getMessage());
            textViewName.setText("Error: " + e.getMessage());
        }
    }
    }








