package knyaseff.net.lab2_writefile;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.StringBuilderPrinter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkIsFileExist("Lab2.txt");

        final Button button = (Button) findViewById(R.id.mainActionButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                writeToFile("Lab2.txt");
            }
        });
    }

    public boolean checkIsFileExist(String filename) {
        boolean result;
        File file = new File (super.getFilesDir(), filename);
        if (result = file.exists()) {
             Toast.makeText(this.getApplicationContext(), "File " + filename + " already exist",
                     Toast.LENGTH_SHORT).show();
            // Log.d("Lab2", "File " + filename + " already exists");
        } else {
            showDialog(filename);
        }
        return result;
    }

    public void showDialog(final String filename) {
        final String fileName = filename;
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("File is now creating " + fileName).setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                         Toast.makeText(getApplicationContext(), "File " + filename
                                 + " will be created now",
                                 Toast.LENGTH_SHORT).show();
                        // Log.d("Lab2", "File " + filename + " will be created now");
                        createFile(filename);
                    }
                });
        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
    }

    public void createFile(String filename) {
        File file = new File(super.getFilesDir(), filename);
        try {
            file.createNewFile();
            Toast.makeText(getApplicationContext(), "File " + filename + " is created",
                    Toast.LENGTH_SHORT).show();
            // Log.d("Lab2", "File " + filename + " is created");
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "File " + filename + " is NOT created",
                Toast.LENGTH_SHORT).show();
            // Log.d("Lab2", "File " + filename + " is not created");
        }
    }

    public void writeToFile(String filename) {
        final EditText firstNameGet = (EditText) findViewById(R.id.firstNameField);
        final EditText lastNameGet = (EditText) findViewById(R.id.lastNameField);
        String fistName = firstNameGet.getText().toString();
        String lastName = lastNameGet.getText().toString();
        String fullName = fistName + "; " + lastName + "\r\n";
        Context context = getApplicationContext();
        try {
            OutputStreamWriter outputStreamWriter = new
                    OutputStreamWriter(context.openFileOutput(filename, context.MODE_APPEND));
            outputStreamWriter.write(fullName);
            outputStreamWriter.close();
            Toast.makeText(getApplicationContext(), "File write success",
                    Toast.LENGTH_SHORT).show();
            readFromFile(filename);
            // Log.d("Lab2", "File write succed");
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "File write fail",
                Toast.LENGTH_SHORT).show();
            // Log.d("Lab2", "File write fail");
        }
    }

    public void readFromFile(String filename) {
        final TextView txt = (TextView) findViewById(R.id.readFromFileLabel);
        try {
            Context context = getApplicationContext();
            FileInputStream fis = context.openFileInput(filename);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            Toast.makeText(getApplicationContext(), "File read success",
                    Toast.LENGTH_SHORT).show();
            txt.setText(sb.toString());
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "File read fail",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
