package knyaseff.net.lab3_hashtable;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RandomAccessFile randomAccessFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkIsFileExist("Lab3.txt");
        File file = new File(super.getFilesDir(), "Lab3.txt");
        try {
            randomAccessFile = new RandomAccessFile(file, "rw");
        } catch (IOException e) {
            Log.d("BUNDLE ERROR: ", e.getMessage());
        }
        final Button saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                writeToFile("Lab3.txt");
            }
        });
        final Button readButton = (Button) findViewById(R.id.readButton);
        readButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });
    }

    public boolean checkIsFileExist(String filename) {
        boolean result;
        File file = new File (super.getFilesDir(), filename);
        if (result = file.exists()) {
            Toast.makeText(this.getApplicationContext(), "File " + filename + " already exist",
                    Toast.LENGTH_SHORT).show();
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
            createEmptyTemplate();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "File " + filename + " is NOT created",
                    Toast.LENGTH_SHORT).show();
        }
    }



    public int getCurrentPosition(int pos) {
        try {
            randomAccessFile.seek(pos);
            byte [] currentField = new byte[40];
            randomAccessFile.read(currentField);
            String currentRelativePosition = new String(currentField, "UTF-16");
            String moving = currentRelativePosition.substring(15, 20).replaceAll("⨪", "");
            if (moving.equals("") || Integer.parseInt(moving) == 0) {
                String valuesFromFile = currentRelativePosition.substring(5, 15).replaceAll("⨪", "");
                if (!valuesFromFile.equals("")) {
                    randomAccessFile.seek(pos + 15);
                    randomAccessFile.writeChars(Integer.valueOf((int)randomAccessFile.length())
                            .toString());
                    return (int) randomAccessFile.length() - 1;
                } else {
                    randomAccessFile.seek(pos + 15);
                    randomAccessFile.writeChars(Integer.valueOf(0).toString());
                    return pos;
                }
            } else {
                return getCurrentPosition(Integer.parseInt(moving));
            }

        } catch (IOException e) {
            Log.d("Error message", e.getMessage());
        }
        return 0;
    }

    public void createEmptyTemplate() {
        byte [] template = new byte[400];
        char star = '*';
        for (int i = 0; i < 400; i++) {
            template[i] = (byte)star;
        }
        try {
            randomAccessFile.write(template);
            Toast.makeText(getApplicationContext(), "RAF template creating success",
                    Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "RAF templace creating error",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void writeToFile(String filename) {
        EditText keyField = (EditText)findViewById(R.id.keyField1);
        EditText valueField = (EditText)findViewById(R.id.valueField1);
        String keyFieldValue = keyField.getText().toString();
        String valueFieldValue = valueField.getText().toString();
        HashTable hashEntry = new HashTable(keyFieldValue, valueFieldValue);

        try {
            Toast.makeText(getApplicationContext(), "RAF write success",
                    Toast.LENGTH_SHORT).show();
            // randomAccessFile.seek(hashEntry.getHashKey(hashEntry.keyString)*40);
            // randomAccessFile.writeInt(hashEntry.getKey());
            // randomAccessFile.writeBytes(hashEntry.getValue());
            // randomAccessFile.writeInt(hashEntry.getPositionOfRelative());

            byte [] btKey = new byte[10];
            byte [] btValue = new byte[20];
            byte [] btAddress = new byte[10];

            randomAccessFile.seek(getCurrentPosition(hashEntry.getKey()));
            byte [] newKey = keyFieldValue.getBytes();
            byte [] newValue = valueFieldValue.getBytes();
            String newAddress = (Integer.valueOf((int) randomAccessFile.getFilePointer()))
                    .toString();
            byte [] currentField = new byte[40];
            randomAccessFile.read(currentField);
            // String currentRelativePosition = new String(currentField, "UTF-16");
            // String keyFromFile = currentRelativePosition.substring(0, 5);
            // String valuesFromFile = currentRelativePosition.substring(5, 15).replaceAll("⨪", "");
            // String relativePosFromFile = currentRelativePosition.substring(15, 20).replaceAll("⨪", "");

            btKey = newKey;
            btValue = newValue;
            randomAccessFile.write(btKey);
            randomAccessFile.write(btValue);
            randomAccessFile.writeChars(newAddress);


        } catch(IOException e) {
            Toast.makeText(getApplicationContext(), "Random access file creation error",
                    Toast.LENGTH_SHORT).show();
        }

    }
}
