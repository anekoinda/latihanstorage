package id.anekoinda.latihanstorage;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnBaru;
    Button btnBuka;
    Button btnSimpan;
    EditText edtKonten;
    EditText edtJudul;

    File path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnBaru = (Button) findViewById(R.id.btnBaru);
        btnBuka = (Button) findViewById(R.id.btnBuka);
        btnSimpan = (Button) findViewById(R.id.btnSimpan);
        edtKonten = (EditText) findViewById(R.id.edtKonten);
        edtJudul = (EditText) findViewById(R.id.edtJudul);

        btnBaru.setOnClickListener(this);
        btnBuka.setOnClickListener(this);
        btnSimpan.setOnClickListener(this);
        path = getFilesDir();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btnBaru:
                newFile();
                break;
            case R.id.btnBuka:
                openFile();
                break;
            case R.id.btnSimpan:
                saveFile();
                break;
        }
    }

    public void newFile() {
        edtJudul.setText("");
        edtKonten.setText("");

        Toast.makeText(this, "Clearing file", Toast.LENGTH_SHORT).show();
    }

    private void loadData(String title) {
        String text = FileHelper.readFromFile(this, title);
        edtJudul.setText(title);
        edtKonten.setText(text);
        Toast.makeText(this, "Loading " + title + " data", Toast.LENGTH_SHORT).show();
    }

    public void openFile() {
        showList();
    }

    private void showList() {
        final ArrayList<String> arrayList = new ArrayList<String>();
        for (String file : path.list()) {
            arrayList.add(file);
        }
        final CharSequence[] items = arrayList.toArray(new CharSequence[arrayList.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilih file Anda yang diinginkan");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                loadData(items[item].toString());
            }
        });
        AlertDialog alert = builder.create();

        alert.show();
    }

    public void saveFile() {
        if (edtJudul.getText().toString().isEmpty()) {
            Toast.makeText(this, "Title harus diisi terlebih dahulu", Toast.LENGTH_SHORT).show();
        } else {
            String title = edtJudul.getText().toString();
            String text = edtKonten.getText().toString();
            FileHelper.writeToFile(title, text, this);
            Toast.makeText(this, "Saving " + edtJudul.getText().toString() + " file", Toast.LENGTH_SHORT).show();
        }
    }
}