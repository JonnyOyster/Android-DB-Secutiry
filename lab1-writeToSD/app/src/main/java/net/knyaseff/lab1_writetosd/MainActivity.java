package net.knyaseff.lab1_writetosd;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    // permissions
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    private void checkPermissions() {
        int hasWritePermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWritePermission != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE },
                    REQUEST_CODE_ASK_PERMISSIONS);
            return;
        }
        buttonsActions();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    buttonsActions();
                } else {
                    // Permission Denied
                    Toast.makeText(MainActivity.this, "WRITE_EXTERNAL_STORAGE Denied", Toast.LENGTH_LONG)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    // permissions end

    public void buttonsActions() {
        final Button getFilesDirBtn = (Button) findViewById(R.id.getFilesDirBtn);
        final Button getCacheDirBtn = (Button) findViewById(R.id.getCacheDirBtn);
        final Button getExternalFilexBtn = (Button) findViewById(R.id.getExternalFilesDirBtn);
        final Button getExternalCacheDirBtn = (Button) findViewById(R.id.getExternalCacheDirBtn);
        final Button getExternalStorageDirBtn = (Button) findViewById(R.id.getExternalStorageDirBtn);
        final Button getExternalPublicStorageDirBtn = (Button) findViewById(R.id.getExternalPublicStorageDirBtn);

        getFilesDirBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                File file;
                file = getFilesDir();
                printToActivity(file);
            }
        });
        getCacheDirBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                File file;
                file = getCacheDir();
                printToActivity(file);
            }
        });
        getExternalFilexBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                File file;
                file = getExternalFilesDir(null);
                printToActivity(file);
            }
        });
        getExternalCacheDirBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                File file;
                file = getExternalCacheDir();
                printToActivity(file);
            }
        });
        getExternalStorageDirBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                File file;
                file = Environment.getExternalStorageDirectory();
                printToActivity(file);
            }
        });
        getExternalPublicStorageDirBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                File file;
                file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                printToActivity(file);

            }
        });
    }

    public void printToActivity(File file) {
        final TextView absoluteField = (TextView) findViewById(R.id.absoluteField);
        final TextView nameField = (TextView) findViewById(R.id.nameField);
        final TextView pathField = (TextView) findViewById(R.id.pathField);
        final TextView readWriteField = (TextView) findViewById(R.id.readWriteField);
        final TextView externalField = (TextView) findViewById(R.id.externalField);
        absoluteField.setText("Absolute path: " + file.getAbsolutePath());
        nameField.setText("Name: " + file.getName());
        pathField.setText("Path:" + file.getPath());
        readWriteField.setText("Read / Write: " + file.canRead() + " / " + file.canWrite());
        externalField.setText("External state: " + Environment.getExternalStorageState());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermissions();
    }
}
