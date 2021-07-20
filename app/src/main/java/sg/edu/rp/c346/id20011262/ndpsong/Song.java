package sg.edu.rp.c346.id20011262.ndpsong;

public class Song {

    private int id;
    private String Title;
    private String Singer;
    private int year;
    private int star;

    public Song(String Title, String singer, int year, int star) {
        this.Title = Title;
        this.Singer = Singer;
        this.year = year;
        this.star = star;
    }

    public int get_id() {
        return id;
    }

    public String getTitle() {
        return Title;
    }
    public void setTitle(String title) {
        Title = title;
    }

    public String getSinger() {
        return Singer;
    }

    public void setSinger(String singer) {
        Singer = singer;
    }

    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }

    public int getStar() {
        return star;
    }
    public void setStar(int star) {
        this.star = star;
    }
}
