package com.example.login;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConSQL {
    Connection Con;

    @SuppressLint("NewApi")

    public Connection conclass() {
        String ip = "192.168.2.46", port = "1433", db = "TEEMAGE_COM", username = "sa", password = "TEST";
        StrictMode.ThreadPolicy a = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(a);
        String connectURL = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connectURL = "jdbc:jtds:sqlserver://" +ip+ ":" +port+ ";databasename=" +db+ ";user=" + username + ";"+"password=" +password+ ";";
            Con = DriverManager.getConnection(connectURL);
        } catch (Exception e) {
            Log.e("Error :", e.getMessage());
        }
        return Con;
    }
}
