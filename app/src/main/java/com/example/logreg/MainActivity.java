package com.example.logreg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText useroremail, password;
    Button login, registry;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        registry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
                finish();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameOrEmail = useroremail.getText().toString();
                String password1 = password.getText().toString();

                if (dbHelper.checkUserByUsername(usernameOrEmail, password1)) {
                    Toast.makeText(MainActivity.this, "Sikeres bejelentkezés!", Toast.LENGTH_SHORT).show();
                    login(usernameOrEmail, password1);
                } else if (dbHelper.checkUserByEmail(usernameOrEmail, password1)) {
                    Toast.makeText(MainActivity.this, "Sikeres bejelentkezés!", Toast.LENGTH_SHORT).show();
                    login(usernameOrEmail, password1);
                } else {
                    Toast.makeText(MainActivity.this, "Sikertelen bejelentkezés!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void login(String usernameOrEmail, String password) {
        Cursor cursor = dbHelper.getTableElementByUsername(usernameOrEmail, password);
        cursor.moveToFirst();

        SharedPreferences sharedPreferences = getSharedPreferences("Data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("id", cursor.getInt(0));
        editor.apply();

        startActivity(new Intent(MainActivity.this, LoggedInActivity.class));
        finish();
    }

    private void init() {

        useroremail = findViewById(R.id.txt_useroremail);
        password = findViewById(R.id.txt_password);
        login = findViewById(R.id.btn_login);
        registry = findViewById(R.id.btn_registry);
        dbHelper = new DBHelper(this);
    }

}