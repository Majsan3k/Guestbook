import java.sql.Timestamp;
import java.util.Date;

public class Guest {
    private int id;
    private String name;
    private String email;
    private String homepage;
    private String comment;
    private Timestamp dateCreated;

    public Guest(int id, String name, String email, String homepage, String comment, Timestamp date){
        this.id = id;
        this.name = name;
        this.email = email;
        this.homepage = homepage;
        this.comment = comment;
        this.dateCreated = date;
    }

    public String getComment() {
        return comment;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getHomepage() {
        return homepage;
    }

    public String toString(){
        return name;
    }

    public int getId() {
        return id;
    }

    public Date getDateCreated() {
        return dateCreated;
    }
}
