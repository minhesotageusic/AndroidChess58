package com.example.chess58;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class SavePrompt extends AppCompatActivity {

    public EditText inputText;
    public TextView dateText;
    public Recording recording;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.save_game);

        Bundle bundle1 = getIntent().getExtras();
        recording = bundle1.getParcelable("recording");

        inputText = findViewById(R.id.gameTitleInputText);
        dateText = findViewById(R.id.dateText);
        Button cancelBtn = findViewById(R.id.cancel_save_btn);
        Button saveBtn = findViewById(R.id.save_btn);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        dateText.setText(dtf.format(now));

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                CancelSave(v);
            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Save(v);
            }
        });
    }

    /**
     * Cancel save
     * @param view
     */
    public void CancelSave(View view){
        //goto main activity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void Save(View view){
        String title = inputText.getText().toString();
        recording.SetTitle(title);
        recording.SetDate(dateText.getText().toString());
        AppData.ReadData();
        AppData.recordingsData.add(recording);
        AppData.WriteData();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
