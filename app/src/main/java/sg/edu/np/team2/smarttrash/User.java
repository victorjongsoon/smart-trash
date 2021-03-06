package sg.edu.np.team2.smarttrash;

//User class to upload user data along side firebase authentication to firebase RTDatabase

public class User {

    private String name;
    private String accountType;


    public User(String name, String accountType) {
        this.name = name;
        this.accountType = accountType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

}
