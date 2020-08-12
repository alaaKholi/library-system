package librarysystem;

import enitities.User;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author alaaKholi
 */
public class Login extends Application {

    Statement statement;
    User user;
    Stage s;

    public String convertToHash(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        return DatatypeConverter.printHexBinary(digest);
    }

    public void conectDBs() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection
                    = DriverManager.
                            getConnection("jdbc:mysql://127.0.0.1:3306/library_management?serverTimezone=UTC",
                                    "root", "");
            this.statement = connection.createStatement();
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Error in Connecting to DBs");
        }
    }

    public void getUsers() {
        try {
            ResultSet rs = this.statement.executeQuery("Select * From Users");
            while (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
            }
        } catch (SQLException ex) {
            System.out.println("Error in Getting users");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws NoSuchAlgorithmException {

        conectDBs();
        //Adding HBox and text
        Text text = new Text("Login");
        text.setId("text");
        HBox hb = new HBox(text);
        hb.setAlignment(Pos.CENTER);
        hb.setPadding(new Insets(20));

        Label username = new Label("Username :");
        TextField usernameField = new TextField();

        Label password = new Label("Password :");
        PasswordField passwordField = new PasswordField();

        Label message = new Label();
        Button loginBtn = new Button("Login");
        loginBtn.setId("loginBtn");
        HBox hbut = new HBox(loginBtn);
        hbut.setAlignment(Pos.CENTER);
        hbut.setPadding(new Insets(10, 0, 0, 0));

        //Action for loginBtn
        loginBtn.setOnAction((ActionEvent event) -> {
            String checkUser = usernameField.getText();
            String checkpassword = passwordField.getText();

            getUsers();

            try {
                if (checkUser.equals(user.getName()) && (convertToHash(checkpassword)).equals(user.getPassword())) {
                    new LibrarySystem().start(new Stage());
                    s.close();
                } else {
                    message.setText("Incorrect user or password.");
                    message.setTextFill(Color.RED);
                }
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
            usernameField.setText("");
            passwordField.setText("");
        });

        //Adding GridPane
        VBox components = new VBox(15, username, usernameField, password, passwordField, hbut, message);

        VBox vbox = new VBox(hb, components);
        vbox.setId("login");

        //Add HBox and GridPane layout to VBox root Layout
        VBox root = new VBox(vbox);
        root.setPadding(new Insets(150, 500, 50, 75));
        root.setId("root");
        components.setId("components");

        //Adding VBox to the scene and loading CSS
        Scene scene = new Scene(root);
        scene.getStylesheets().add("ss.css");

        primaryStage.setScene(scene);
        primaryStage.setTitle("Library System");
        primaryStage.setResizable(false);
        primaryStage.setMinWidth(900);
        primaryStage.setMinHeight(700);
        primaryStage.show();
        s = primaryStage;
    }
}
