package sg.edu.rp.c346.id20011262.ndpsong;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ndpsong.db";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_SONG = "Song";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_SONG_SINGER = "singers";
    private static final String COLUMN_SONG_TITLE = "title";
    private static final String COLUMN_YEAR = "year";
    private static final String COLUMN_STAR = "stars";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createSongTableSql = "CREATE TABLE " + TABLE_SONG + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_SONG_TITLE + " TEXT, "
                + COLUMN_SONG_SINGER + " TEXT, "
                + COLUMN_YEAR + " INTEGER, "
                + COLUMN_STAR + " INTEGER ) ";
        db.execSQL(createSongTableSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("ALTER TABLE " + TABLE_SONG + " ADD COLUMN  module_name TEXT ");
    }

    public long insertSong(String title, String singers, int year, int stars) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SONG_SINGER, singers);
        values.put(COLUMN_SONG_TITLE, title);
        values.put(COLUMN_YEAR, year);
        values.put(COLUMN_STAR, stars);
        long result = db.insert(TABLE_SONG, null, values);
        db.close();
        return result;
    }

    public ArrayList<Song> getAllSong() {
        ArrayList<Song> notes = new ArrayList<>();

        String sql = "SELECT " + COLUMN_ID + ","
                + COLUMN_SONG_TITLE + " , "
                + COLUMN_SONG_SINGER + " , "
                + COLUMN_YEAR + " , "
                + COLUMN_STAR
                + " FROM " + TABLE_SONG;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String singer = cursor.getString(1);
                String title = cursor.getString(2);
                int year = cursor.getInt(3);
                int stars = cursor.getInt(4);
                Song songs = new Song(singer, title, year, stars);
                notes.add(songs);
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return notes;
    }

    public ArrayList<Song> getAllSongsByStars(int stars) {
        ArrayList<Song> songs = new ArrayList<Song>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_SONG_TITLE, COLUMN_SONG_SINGER, COLUMN_YEAR, COLUMN_STAR};
        String condition = COLUMN_STAR + " Like ?";
        String[] args = { "%" +  stars + "%"};
        Cursor cursor = db.query(TABLE_SONG, columns, condition, args,
                null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String singer = cursor.getString(1);
                String title = cursor.getString(2);
                int year = cursor.getInt(3);
                int star = cursor.getInt(4);
                Song song = new Song(singer, title, year, star);
                songs.add(song);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return songs;
    }

    public int updateSong(Song data) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SONG_TITLE, data.getTitle());
        values.put(COLUMN_SONG_SINGER, data.getSinger());
        values.put(COLUMN_YEAR, data.getYear());
        values.put(COLUMN_STAR, data.getStar());
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(data.get_id()) };
        int result = db.update(TABLE_SONG, values, condition, args);
        db.close();
        return result;
    }

    public int deleteSong(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_SONG, condition, args);
        db.close();
        return result;
    }

}
