package net.knyaseff.hellobuttons;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText firstText = (EditText)findViewById(R.id.textView);
                EditText secondText = (EditText)findViewById(R.id.textView3);
                String message = getResources().getString(R.string.toastMain) + " " +
                                     firstText.getText() + " " + secondText.getText();
                Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);
                toast.show();
            }
        });

        final Button saveOnSDBtn = (Button)findViewById(R.id.saveToSDBtn);

        saveOnSDBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                File sdCard = Environment.getExternalStorageDirectory();
                File dir = new File(sdCard.getAbsolutePath() + "/Projects/HashTable");
                dir.mkdirs();
                EditText fileName = (EditText) findViewById(R.id.fileNameField);
                File file = new File(dir, fileName.getText().toString());

                try {
                    FileWriter f = new FileWriter(file);
                    EditText firstText = (EditText)findViewById(R.id.textView);
                    EditText secondText = (EditText)findViewById(R.id.textView3);
                    f.write(firstText.getText().toString() + '\n'
                            + secondText.getText().toString());
                    f.close();

                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}