package sg.edu.rp.c346.id20011262.ndpsong;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class NDP_modify extends AppCompatActivity {

    Button btnUpdate, btnDelete, btnCancel;
    EditText etTitle, etSingers, etYear, etID;
    RadioGroup rgstars;
    RadioButton rb1,rb2,rb3,rb4,rb5;
    ArrayList<Song> songs;
    ArrayAdapter<Song> aa;
    Song song;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_n_d_p_modify);

        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnCancel = findViewById(R.id.btnCancel);
        etTitle = findViewById(R.id.etTitle);
        etSingers = findViewById(R.id.etSinger);
        etYear = findViewById(R.id.etYear);
        etID = findViewById(R.id.etSongID);
        rgstars = findViewById(R.id.rg);
        rb1 = findViewById(R.id.radioButton);
        rb2 = findViewById(R.id.radioButton6);
        rb3 = findViewById(R.id.radioButton7);
        rb4 = findViewById(R.id.radioButton8);
        rb5 = findViewById(R.id.radioButton9);

        Intent i = getIntent();
        song = (Song) i.getSerializableExtra("song");
        etID.setText(String.valueOf(song.get_id()));
        etTitle.setText(String.valueOf(song.getTitle()));
        etSingers.setText(song.getSinger());
        etYear.setText(song.getYear());
        int stars = song.getStar();
        if (stars == 1) {
            rb1.setChecked(true);
        }
        else if (stars == 2) {
            rb2.setChecked(true);
        }
        else if (stars == 3) {
            rb3.setChecked(true);
        }
        else if (stars == 4) {
            rb4.setChecked(true);
        }
        else if (stars == 5) {
            rb5.setChecked(true);
        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                song.setTitle(etTitle.getText().toString());
                song.setSinger(etSingers.getText().toString());
                song.setYear(Integer.parseInt(etYear.getText().toString()));
                song.setStar(stars);
                DBHelper dbh = new DBHelper(NDP_modify.this);
                dbh.updateSong(song);
                setResult(RESULT_OK);
                finish();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(NDP_modify.this);
                dbh.deleteSong(song.get_id());
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

    }


    @Override
    protected void onResume() {
        DBHelper dbh = new DBHelper(NDP_modify.this);
        songs.clear();
        songs.addAll(dbh.getAllSong());
        aa.notifyDataSetChanged();
        dbh.close();
        super.onResume();
    }

}