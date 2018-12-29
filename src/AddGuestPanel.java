import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddGuestPanel extends JPanel {

    private GuestBook parentFrame;
    private JTextField nameField;
    private JTextField emailField;
    private JTextField homepageField;
    private JTextField commentField;
    private static String HTML_TAGS = "<.*>";
    private static Pattern htmlPattern = Pattern.compile(HTML_TAGS);

    public AddGuestPanel(GuestBook frame) {

        parentFrame = frame;
        setLayout(new GridLayout(5,2));

        add(new JLabel("Name: "));
        add(nameField = new JTextField());
        add(new JLabel("Email: "));
        add(emailField = new JTextField());
        add(new JLabel("Homepage: "));
        add(homepageField = new JTextField());
        add(new JLabel("Comment: "));
        add(commentField = new JTextField());
        add(new JLabel("Add: "));
        JButton addBtn = new JButton("Press me!");

        addBtn.addActionListener(e -> {
            String name = clearFromHTML(nameField.getText());
            String email = clearFromHTML(emailField.getText());
            String homepage = clearFromHTML(homepageField.getText());
            String comment = clearFromHTML(commentField.getText());

            if (!checkValidValues(name, email, homepage, comment)) {
                parentFrame.insertToGuestBook(name, email, homepage, comment);
                clearAllTextFields();
                parentFrame.displayGuestBook();
            }
        });
        add(addBtn);
    }

    private void clearAllTextFields(){
        nameField.setText("");
        emailField.setText("");
        homepageField.setText("");
        commentField.setText("");
    }

    private String clearFromHTML(String attribute){
        Matcher match = htmlPattern.matcher(attribute);
        return match.replaceAll("censur");
    }

    private String checkValidLength(String type, String value, int length, String message){
        if(value.length() > length){
            return message + type + " can't be longer than " + length + " characters!\n";
        }
        return message;
    }

    private boolean checkValidValues(String name, String email, String homepage, String comment){
        String message = "";
        if(name.isEmpty() || email.isEmpty() || name.length() > 30 || email.length() > 30 || homepage.length() > 200 || comment.length() > 200) {
            if (name.isEmpty())
                message = "Name can't be empty! \n";
            if (email.isEmpty())
                message += "Email can't be empty! \n";
            message = checkValidLength("Name", name, 30, message);
            message = checkValidLength("Email", email, 30, message);
            message = checkValidLength("Homepage", homepage, 200, message);
            message = checkValidLength("Comment", comment, 200, message);

            showMessageDialog(parentFrame, message);
            return true;
            }
        return false;
    }
}

