package com.webbions.writecsv;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestStoragePermission();
        txt=findViewById(R.id.txt);

        try {
            exportEmailInCSV();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
        }
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2020);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == 2020) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    public Cursor getCursorFromList() {
        MatrixCursor cursor = new MatrixCursor(
                new String[] {"_id", "name", "surname", "phone"}
        );

        cursor.newRow()
                .add("_id", "1")
                .add("name", "Amit")
                .add("surname", "Kava")
                .add("phone", "123");

        cursor.newRow()
                .add("_id", "2")
                .add("name", "Mayur")
                .add("surname", "Patel")
                .add("phone", "456");
        cursor.newRow()
                .add("_id", "3")
                .add("name", "Bhaumik")
                .add("surname", "Patel")
                .add("phone", "789");
        cursor.newRow()
                .add("_id", "4")
                .add("name", "Ripal")
                .add("surname", "Khalas")
                .add("phone", "741");
        cursor.newRow()
                .add("_id", "5")
                .add("name", "Renuka")
                .add("surname", "Patel")
                .add("phone", "852");
        cursor.newRow()
                .add("_id", "6")
                .add("name", "Ankit")
                .add("surname", "Patel")
                .add("phone", "963");
        cursor.newRow()
                .add("_id", "1")
                .add("name", "Amit")
                .add("surname", "Kava")
                .add("phone", "123");

        cursor.newRow()
                .add("_id", "2")
                .add("name", "Mayur")
                .add("surname", "Patel")
                .add("phone", "456");
        cursor.newRow()
                .add("_id", "3")
                .add("name", "Bhaumik")
                .add("surname", "Patel")
                .add("phone", "789");
        cursor.newRow()
                .add("_id", "4")
                .add("name", "Ripal")
                .add("surname", "Khalas")
                .add("phone", "741");
        cursor.newRow()
                .add("_id", "5")
                .add("name", "Renuka")
                .add("surname", "Patel")
                .add("phone", "852");
        cursor.newRow()
                .add("_id", "6")
                .add("name", "Ankit")
                .add("surname", "Patel")
                .add("phone", "963");
        return cursor;
    }

    public void exportEmailInCSV() throws IOException {
        {

            File folder = new File(Environment.getExternalStorageDirectory()
                    + "/WriteCsv");

            boolean var = false;
            if (!folder.exists())
                var = folder.mkdir();

            System.out.println("" + var);


            final String filename = folder.toString() + "/" + "Test.csv";

            // show waiting screen
            CharSequence contentTitle = getString(R.string.app_name);
            final ProgressDialog progDailog = ProgressDialog.show(
                    MainActivity.this, contentTitle, "even geduld aub...",
                    true);//please wait
            final Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                }
            };

            new Thread() {
                public void run() {
                    try {

                        FileWriter fw = new FileWriter(filename);

                        Cursor cursor=getCursorFromList();

                        fw.append("Id");
                        fw.append(',');

                        fw.append("Name");
                        fw.append(',');

                        fw.append("Sname");
                        fw.append(',');

                        fw.append("Phone");
                        fw.append(',');

                        fw.append('\n');

                        if (cursor.moveToFirst()) {
                            do {
                                fw.append(cursor.getString(0));
                                fw.append(',');

                                fw.append(cursor.getString(1));
                                fw.append(',');

                                fw.append(cursor.getString(2));
                                fw.append(',');

                                fw.append(cursor.getString(3));
                                fw.append(',');

                                fw.append('\n');

                            } while (cursor.moveToNext());
                        }
                        if (cursor != null && !cursor.isClosed()) {
                            cursor.close();
                        }

                        // fw.flush();
                        fw.close();

                    } catch (Exception e) {
                    }
                    handler.sendEmptyMessage(0);
                    progDailog.dismiss();
                    txt.setText("Task Complate");
                }
            }.start();

        }

    }
}
