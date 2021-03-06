package sg.edu.np.team2.smarttrash;

//Bin Class to upload data into firebase RTDatabase

public class Bin {

    private String binData;


    public Bin(String binData) {
        this.binData = binData;
    }


    public String getBinData() {
        return binData;
    }

    public void setBinData(String binUID) {
        this.binData = binData;
    }

}
