package sg.edu.rp.c346.id20011262.ndpsong;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText etTitle, etSinger, etYear;
    Button btnInsert, btnShowList;
    RadioGroup rgStar;
    ArrayList<Song> songs;
    ArrayAdapter<Song> aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTitle = findViewById(R.id.etTitle);
        etSinger = findViewById(R.id.etSinger);
        etYear = findViewById(R.id.etYear);
        btnInsert = findViewById(R.id.btnInsert);
        btnShowList = findViewById(R.id.btnShowList);
        rgStar = findViewById(R.id.RadioGroup);

        songs = new ArrayList<>();
        aa = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, songs);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(MainActivity.this);

                String title = etTitle.getText().toString();
                String singer = etSinger.getText().toString();
                int year = Integer.parseInt(etYear.getText().toString());
                long inserted_id = dbh.insertSong(title, singer, year, getStars());

                if (inserted_id != -1){
                    songs.clear();
                    songs.addAll(dbh.getAllSong());
                    aa.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, "Insert successful", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, NDP_ShowListView.class);
                startActivity(i);
            }
        });
    }

    private int getStars() {
        int stars = 1;
        if(rgStar.getCheckedRadioButtonId() == R.id.radioButton1) {
            stars = 1;
        }
        else if(rgStar.getCheckedRadioButtonId() == R.id.radioButton2) {
            stars = 2;
        }
        else if(rgStar.getCheckedRadioButtonId() == R.id.radioButton3) {
            stars = 3;
        }
        else if(rgStar.getCheckedRadioButtonId() == R.id.radioButton4) {
            stars = 4;
        }
        else if(rgStar.getCheckedRadioButtonId() == R.id.radioButton5) {
            stars = 5;
        }
        return stars;
    }

    @Override
    protected void onResume() {
        DBHelper dbh = new DBHelper(MainActivity.this);
        songs.clear();
        songs.addAll(dbh.getAllSong());
        aa.notifyDataSetChanged();
        dbh.close();
        super.onResume();
    }

}