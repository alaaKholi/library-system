/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarysystem;

import enitities.Book;
import enitities.Borrower;
import enitities.borrowerBooks;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.Optional;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author alaaKholi
 */
public class LibrarySystem extends Application {

    Button bookUserMng, settingUserBtn, settingbookBtn;
    private TableColumn<Borrower, String> fnameCol;
    private TableColumn<Borrower, String> lnameCol;
    private TableColumn<Borrower, String> emailCol;
    private TableColumn<Borrower, String> addressCol;
    private TableColumn<Borrower, String> genderCol;
    private TableColumn<Borrower, Integer> id2Col;
    private TableColumn<Borrower, Integer> mobileCol;
    private TableColumn<Book, Integer> idCol;
    private TableColumn<Book, String> nameCol;
    private TableColumn<Book, String> descCol;
    private TableColumn<borrowerBooks, Integer> bbBook;
    private TableColumn<borrowerBooks, Integer> bbBorrower;
    private TableColumn<borrowerBooks, Date> bDate;
    private TableColumn<borrowerBooks, Date> rDate;

    TextField idTF, nameTF, lnameTF, fnameTF, id2TF, emailTF, mobileTF, addressTF, genderTF;
    TextArea desTA;
    Button addOperation, resetOperation;
    Operations operations;
    Layout layouts;
    Label head;
    VBox body = new VBox();
    PrintWriter pw;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        operations = new Operations();
        layouts = new Layout();

        //append new transactuons
        FileWriter fw = new FileWriter(new File("src/LibrarySystem/transation.txt"), true);
        pw = new PrintWriter(fw);

        fnameCol = new TableColumn<>("FName");
        fnameCol.setPrefWidth(100);
        lnameCol = new TableColumn<>("LName");
        lnameCol.setPrefWidth(100);

        emailCol = new TableColumn<>("Email");
        emailCol.setPrefWidth(120);
        addressCol = new TableColumn<>("Address");
        addressCol.setPrefWidth(120);
        genderCol = new TableColumn<>("Gender");
        genderCol.setPrefWidth(100);
        id2Col = new TableColumn<>("ID");
        id2Col.setPrefWidth(100);
        mobileCol = new TableColumn<>("Mobile");
        mobileCol.setPrefWidth(100);
        idCol = new TableColumn<>("ID");
        idCol.setPrefWidth(100);
        nameCol = new TableColumn<>("Name");
        nameCol.setPrefWidth(100);
        descCol = new TableColumn<>("Description");
        descCol.setPrefWidth(100);
        bbBook = new TableColumn<>("Book ID");
        bbBook.setPrefWidth(100);
        bbBorrower = new TableColumn<>("Borrower ID");
        bbBorrower.setPrefWidth(120);
        bDate = new TableColumn<>("Borrower Date");
        bDate.setPrefWidth(120);
        rDate = new TableColumn<>("Return Date");
        rDate.setPrefWidth(120);

        idCol.setCellValueFactory(new PropertyValueFactory("id"));
        id2Col.setCellValueFactory(new PropertyValueFactory("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory("name"));
        descCol.setCellValueFactory(new PropertyValueFactory("description"));
        fnameCol.setCellValueFactory(new PropertyValueFactory("firstName"));
        lnameCol.setCellValueFactory(new PropertyValueFactory("lastName"));
        emailCol.setCellValueFactory(new PropertyValueFactory("email"));
        mobileCol.setCellValueFactory(new PropertyValueFactory("mobile"));
        addressCol.setCellValueFactory(new PropertyValueFactory("address"));
        genderCol.setCellValueFactory(new PropertyValueFactory("gender"));
        bbBook.setCellValueFactory(new PropertyValueFactory("Book_id"));
        bbBorrower.setCellValueFactory(new PropertyValueFactory("Borrower_id"));
        bDate.setCellValueFactory(new PropertyValueFactory("borrowers_date"));
        rDate.setCellValueFactory(new PropertyValueFactory("Return_date"));

        body.setId("body");
        addOperation = new Button("Add");
        resetOperation = new Button("Reset");
        idTF = new TextField();
        nameTF = new TextField();
        desTA = new TextArea();
        desTA.setPrefRowCount(3);
        desTA.setPrefColumnCount(15);

        id2TF = new TextField();
        fnameTF = new TextField();
        lnameTF = new TextField();
        emailTF = new TextField();
        mobileTF = new TextField();
        addressTF = new TextField();
        genderTF = new TextField();

        head = new Label();
        head.setId("head");
        layouts.conectDBs();
        layouts.getBooks();
        layouts.getBorrowers();

        Button addBtn = new Button();
        addBtn.setOnAction(event -> {
            addOperation.setText("Add");
            try {
                if (addBtn.getId().equals("addBook")) {
                    layouts.addUpdateBookLayout(idTF, nameTF, desTA, addOperation, resetOperation, head, idCol, nameCol, descCol, body);
                    addOperation.setOnAction(addEvent -> {
                        Optional<ButtonType> isOk = new Alert(Alert.AlertType.CONFIRMATION, "Confirm OK to Add operation").showAndWait();
                        if (ButtonType.OK == isOk.get()) {
                            layouts.BookOperations("add", pw, idTF, nameTF, desTA);
                            operations.resetBookController(idTF, nameTF, desTA);
                            operations.resetBorrowerController(id2TF, fnameTF, lnameTF, emailTF, mobileTF, addressTF, genderTF);
                        }
                    });
                    resetOperation.setOnAction(e -> {
                        operations.resetBookController(idTF, nameTF, desTA);
                    });
                } else if (addBtn.getId().equals("addBorrower")) {

                    layouts.addUpdateBorrowerLayout(id2TF, fnameTF, lnameTF, emailTF, mobileTF, addressTF, genderTF, addOperation, resetOperation, head, id2Col, fnameCol, lnameCol, emailCol, mobileCol, addressCol, genderCol, body);
                    addOperation.setOnAction(e -> {
                        Optional<ButtonType> isOk = new Alert(Alert.AlertType.CONFIRMATION, "Confirm OK to Add operation").showAndWait();
                        if (ButtonType.OK == isOk.get()) {
                            layouts.BorrowerOperations("add", pw, id2TF, fnameTF, lnameTF, emailTF, mobileTF, addressTF, genderTF);
                            operations.resetBookController(idTF, nameTF, desTA);
                            operations.resetBorrowerController(id2TF, fnameTF, lnameTF, emailTF, mobileTF, addressTF, genderTF);
                        }
                    });
                    resetOperation.setOnAction(e -> {
                        operations.resetBorrowerController(id2TF, fnameTF, lnameTF, emailTF, mobileTF, addressTF, genderTF);
                    });
                }
            } catch (NullPointerException E) {
                new Alert(Alert.AlertType.WARNING, " Please Choose the category from above First ").show();
            }
        });

        Button updateBtn = new Button();
        updateBtn.setOnAction(event -> {
            addOperation.setText("Update");
            try {
                if (updateBtn.getId().equals("updateBook")) {
                    //Runnable r = () -> addOperation.setText("Update"); r.run();
                    layouts.addUpdateBookLayout(idTF, nameTF, desTA, addOperation, resetOperation, head, idCol, nameCol, descCol,
                            body);
                    head.setText("Updating Book");
                    addOperation.setOnAction(e -> {
                        Optional<ButtonType> isOk = new Alert(Alert.AlertType.CONFIRMATION, "Confirm OK to Update operation").showAndWait();
                        if (ButtonType.OK == isOk.get()) {
                            layouts.BookOperations("update", pw, idTF, nameTF, desTA);
                            operations.resetBookController(idTF, nameTF, desTA);
                            operations.resetBorrowerController(id2TF, fnameTF, lnameTF, emailTF, mobileTF, addressTF, genderTF);
                        }
                    });
                    resetOperation.setOnAction(e -> {
                        operations.resetBookController(idTF, nameTF, desTA);
                    });
                } else if (updateBtn.getId().equals("updateBorrower")) {

                    layouts.addUpdateBorrowerLayout(id2TF, fnameTF, lnameTF, emailTF, mobileTF, addressTF, genderTF, addOperation, resetOperation, head, id2Col, fnameCol, lnameCol, emailCol, mobileCol, addressCol, genderCol, body);
                    head.setText("Updating Borrower");
                    addOperation.setOnAction(e -> {
                        Optional<ButtonType> isOk = new Alert(Alert.AlertType.CONFIRMATION, "Confirm OK to Update operation").showAndWait();
                        if (ButtonType.OK == isOk.get()) {
                            layouts.BorrowerOperations("update", pw, id2TF, fnameTF, lnameTF, emailTF, mobileTF, addressTF, genderTF);
                            operations.resetBookController(idTF, nameTF, desTA);
                            operations.resetBorrowerController(id2TF, fnameTF, lnameTF, emailTF, mobileTF, addressTF, genderTF);
                        }
                    });
                    resetOperation.setOnAction(e -> {
                        operations.resetBorrowerController(id2TF, fnameTF, lnameTF, emailTF, mobileTF, addressTF, genderTF);
                    });
                }
            } catch (NullPointerException E) {
                new Alert(Alert.AlertType.WARNING, " Please Choose the category from above First ").show();
            }
        });

        Button searchBtn = new Button();
        searchBtn.setOnAction(event -> {
            try {
                if (searchBtn.getId().equals("searchBook")) {
                    layouts.deleteSearchLayout("search", "Book", pw, idCol, nameCol, descCol, id2Col, fnameCol, lnameCol, emailCol, mobileCol, addressCol, genderCol, body);
                } else if (searchBtn.getId().equals("searchBorrower")) {
                    layouts.deleteSearchLayout("search", "Borrower", pw, idCol, nameCol, descCol, id2Col, fnameCol, lnameCol, emailCol, mobileCol, addressCol, genderCol, body);
                }
            } catch (NullPointerException E) {
                new Alert(Alert.AlertType.WARNING, " Please Choose the category from above First ").show();
            }
        });

        Button deleteBtn = new Button();
        deleteBtn.setOnAction(event -> {
            try {
                if (deleteBtn.getId().equals("deleteBook")) {
                    layouts.deleteSearchLayout("delete", "Book", pw, idCol, nameCol, descCol, id2Col, fnameCol, lnameCol, emailCol, mobileCol, addressCol, genderCol, body);
                } else if (deleteBtn.getId().equals("deleteBorrower")) {
                    layouts.deleteSearchLayout("delete", "Borrower", pw, idCol, nameCol, descCol, id2Col, fnameCol, lnameCol, emailCol, mobileCol, addressCol, genderCol, body);
                }
            } catch (NullPointerException E) {
                new Alert(Alert.AlertType.WARNING, "Please Choose the category from above First").show();
            }
        });

        Button themeBtn = new Button();
        themeBtn.setId("themeBtn");

        VBox vbox = new VBox(addBtn, updateBtn, searchBtn, deleteBtn);

        Label s = new Label("Welcome To");
        s.setStyle("-fx-font-size:70px;");
        Label ss = new Label("Library Manegement System ");
        ss.setStyle("-fx-font-size:20px;");
        VBox vv = new VBox(s, ss);
        vv.setAlignment(Pos.CENTER);
        VBox v = new VBox(300, vv, new Label("Coded By : Alaa Kholi"));
        v.setAlignment(Pos.CENTER);
        Button settings = new Button();
        settings.setId("settings");
        settings.setOnAction(event -> {
            operations.reset(settingUserBtn, settingbookBtn, bookUserMng, body);
            addBtn.setId("");
            updateBtn.setId("");
            searchBtn.setId("");
            deleteBtn.setId("");
            body.getChildren().setAll(v);
        });

        settingUserBtn = new Button("Borrowes Settings");
        settingUserBtn.setId("settingUserBtn");
        settingUserBtn.getStyleClass().add("headerBtn");
        settingUserBtn.setOnAction(event -> {
            operations.reset(settingUserBtn, settingbookBtn, bookUserMng, body);

            settingUserBtn.getStyleClass().add("selected");
            addBtn.setId("addBorrower");
            updateBtn.setId("updateBorrower");
            searchBtn.setId("searchBorrower");
            deleteBtn.setId("deleteBorrower");

            body.getChildren().setAll();
        });

        settingbookBtn = new Button("Books Settings");
        settingbookBtn.setId("settingbookBtn");
        settingUserBtn.getStyleClass().add("headerBtn");
        settingbookBtn.setOnAction(event -> {
            operations.reset(settingUserBtn, settingbookBtn, bookUserMng, body);

            settingbookBtn.getStyleClass().add("selected");
            addBtn.setId("addBook");
            updateBtn.setId("updateBook");
            searchBtn.setId("searchBook");
            deleteBtn.setId("deleteBook");
        });

        bookUserMng = new Button("Borrowers Manengement");
        bookUserMng.setId("bookUserMng");
        settingUserBtn.getStyleClass().add("headerBtn");
        bookUserMng.setOnAction(event -> {
            operations.reset(settingUserBtn, settingbookBtn, bookUserMng, body);
            bookUserMng.getStyleClass().add("selected");
            addBtn.setId("");
            updateBtn.setId("");
            searchBtn.setId("");
            deleteBtn.setId("");
            layouts.borrowBook(pw, bbBook, bbBorrower, bDate, rDate, idCol, nameCol, id2Col, fnameCol, lnameCol, body);

        });

        HBox header = new HBox(30, settings, settingUserBtn, settingbookBtn, bookUserMng);

        BorderPane root = new BorderPane();
        root.setLeft(vbox);
        root.setTop(header);
        root.setBottom(themeBtn);
        root.setCenter(body);

        Scene scene = new Scene(root);
        scene.getStylesheets().add("light.css");

        themeBtn.setOnAction(event -> {
            String toogled = operations.toogleTheme();
            scene.getStylesheets().setAll(toogled);
        }
        );

        primaryStage.setOnCloseRequest(e -> {

            try {
                fw.close();
            } catch (IOException ex) {
                System.out.println("Error on FileWriter");
            }
            pw.flush();
            pw.close();
        });
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setMinWidth(900);
        primaryStage.setMinHeight(700);
        primaryStage.show();

    }
}
