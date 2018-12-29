//I uppgiften står det att de färdiga class-filerna ska packas i en jar-fil. Däremot har ni sagt att det
//är källkoden ni vill ha in. Jag skickar därför både med källkoden och jar-filen i denna uppgift.

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.Color;
import java.sql.*;
import java.util.ArrayList;

public class GuestBook extends JFrame {

    private Connection dbConnection = null;
    private static final String GET_GUEST_BOOK_QUERY = "SELECT * FROM guest_book ORDER BY id";
    private static final String INSERT_QUERY = "INSERT INTO guest_book (name, email, homepage, comment) VALUES (?, ?, ?, ?)";
    private JTextArea guestBookViewer;

    public GuestBook(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        AddGuestPanel newGuestPanel = new AddGuestPanel(this);
        add(newGuestPanel, BorderLayout.NORTH);

        guestBookViewer = new JTextArea();
        guestBookViewer.setBackground(Color.WHITE);
        guestBookViewer.setEditable(false);

        JScrollPane guestBookScroll = new JScrollPane(guestBookViewer);
        add(guestBookScroll);

        setSize(640, 480);
        setVisible(true);

        displayGuestBook();
    }

    private void connectToDatabase(){
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        String url = "jdbc:mysql://" + "atlas.dsv.su.se" + "/" + "db_17921331";
        String userName = "usr_17921331";
        String password = "921331";
        try {
            dbConnection = DriverManager.getConnection(url, userName, password);
        } catch (SQLException e) {
            System.out.println("SQL exception connect: " + e.getMessage());
        }
    }

    public void insertToGuestBook(String name, String email, String website, String comment) {
        connectToDatabase();
        PreparedStatement stmt = null;

        try {
            stmt = dbConnection.prepareStatement(INSERT_QUERY);
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, website);
            stmt.setString(4, comment);
            stmt.execute();
        } catch (SQLException e) {
            System.out.println("SQL exception insert: " + e.getMessage());
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    void displayGuestBook(){
        connectToDatabase();
        Statement stmt = null;
        ArrayList<Guest> allGuests = new ArrayList<>();

        try{
            stmt = dbConnection.createStatement();
            ResultSet rs = stmt.executeQuery(GET_GUEST_BOOK_QUERY);
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String homepage = rs.getString("homepage");
                String comment = rs.getString("comment");
                Timestamp date = rs.getTimestamp("date");
                allGuests.add(new Guest(id, name, email, homepage, comment, date));
            }
        }catch (SQLException e){
            System.out.println("SQL exception: " + e.getMessage());
        }finally {
            if(stmt != null){
                try {
                    stmt.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        updateGuestBookViewer(allGuests);
    }

    public void updateGuestBookViewer(ArrayList<Guest> guests){
        guestBookViewer.setText("");
        for(Guest guest : guests) {
            guestBookViewer.append("NO: " + guest.getId() + " TIME: " + guest.getDateCreated() + "\n");
            guestBookViewer.append("NAME: " + guest.getName() + "\n");
            guestBookViewer.append("EMAIL: " + guest.getEmail() + "\n");
            guestBookViewer.append("HOMEPAGE: " + guest.getHomepage() + "\n");
            guestBookViewer.append("COMMENT: " + guest.getComment() + "\n \n");
        }
    }

    public static void main(String[] args){
        new GuestBook();
    }
}