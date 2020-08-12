/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarysystem;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 *
 * @author alaaKholi
 */
public class Operations {

    String theme = "light.css";

    public String toogleTheme() {
        theme = (theme.equals("dark.css")) ? "light.css" : "dark.css";
        System.out.println("Toogled To " + theme);
        return theme;
    }

    public void reset(Button User, Button book, Button mng, VBox body) {
        User.getStyleClass().setAll("headerBtn");
        book.getStyleClass().setAll("headerBtn");
        mng.getStyleClass().setAll("headerBtn");
        User.setId("settingUserBtn");
        book.setId("settingbookBtn");
        mng.setId("bookUserMng");
        body.getChildren().setAll();
    }

    //reset controller feilds with form parameter 
    public void resetBookController(TextField idTF, TextField nameTF, TextArea desTA) {
        idTF.setText("");
        nameTF.setText("");
        desTA.setText("");
    }

    public void resetBorrowerController(TextField id2TF, TextField fnameTF, TextField lnameTF, TextField emailTF,
            TextField mobileTF, TextField addressTF, TextField genderTF) {
        lnameTF.setText("");
        fnameTF.setText("");
        id2TF.setText("");
        emailTF.setText("");
        mobileTF.setText("");
        addressTF.setText("");
        genderTF.setText("");
    }
}
