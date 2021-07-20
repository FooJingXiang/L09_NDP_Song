package sg.edu.rp.c346.id20011262.ndpsong;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class NDP_ShowListView extends AppCompatActivity {

    Button btn;
    ListView lv;
    ArrayList<Song> al;
    ArrayAdapter<Song> aa;
    Song data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_n_d_p__show_list_view);

        btn = findViewById(R.id.button);
        lv = findViewById(R.id.listview);
        al = new ArrayList<>();
        aa = new ArrayAdapter<>(NDP_ShowListView.this, android.R.layout.simple_list_item_1, al);
        lv.setAdapter(aa);

        aa.notifyDataSetChanged();

        Intent i = getIntent();
        data = (Song) i.getSerializableExtra("data");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(NDP_ShowListView.this);
                //data = dbh.getAllSong();
                //songs = dbh.getAllSongsByStars(5);
                al.clear();
                String filterList = dbh.getAllSongsByStars(5).toString();
                if(filterList.length() == 0 ) {
                    al.addAll(dbh.getAllSong());
                } else {
                    al.addAll(dbh.getAllSongsByStars(5));
                }
            }
        });
    }

}