package sg.edu.np.team2.smarttrash;


public class Reward {

    private String Name;

    private int Point;

    private int Thumbnail;

    public Reward() {
    }

    public Reward(String name, int point, int thumbnail) {
        Name = name;
        Point = point;
        Thumbnail = thumbnail;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getPoint() {
        return Point;
    }

    public void setPoint(int point) {
        Point = point;
    }

    public int getThumbnail() {
        return Thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        Thumbnail = thumbnail;
    }
}
