/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarysystem;

import enitities.Book;
import enitities.Borrower;
import enitities.borrowerBooks;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Optional;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author alaaKholi
 */
public class Layout {

    Statement statement;
    HashMap<Integer, Book> booksHM = new HashMap();
    HashMap<Integer, Borrower> borrowersHM = new HashMap();
    TableView<Borrower> borrowerLV;
    TableView<Book> bookLV;

    private void selectBook(TableView bookLV, TextField idTF, TextField nameTF, TextArea desTA) {
        Book b = (Book) bookLV.getSelectionModel().getSelectedItem();
        if (b != null) {
            idTF.setText(b.getId() + "");
            nameTF.setText(b.getName());
            desTA.setText(b.getDescription());
        }
    }

    private void selectBorrower(TableView borrowerLV, TextField id2TF, TextField fnameTF, TextField lnameTF, TextField emailTF,
            TextField mobileTF, TextField addressTF, TextField genderTF) {
        Borrower b = (Borrower) borrowerLV.getSelectionModel().getSelectedItem();
        if (b != null) {
            id2TF.setText(b.getId() + "");
            fnameTF.setText(b.getFirstName());
            lnameTF.setText(b.getLastName());
            emailTF.setText(b.getEmail());
            mobileTF.setText(b.getMobile() + "");
            addressTF.setText(b.getAddress());
            genderTF.setText(b.getGender() + "");
        }
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

    public void getBorrowers() {
        try {
            ResultSet rs = this.statement.executeQuery(" Select * From Borrowers ");
            while (rs.next()) {
                Borrower borrower = new Borrower();
                Integer userId = rs.getInt("id");
                borrower.setId(userId);
                borrower.setFirstName(rs.getString("first_name"));
                borrower.setLastName(rs.getString("last_name"));
                borrower.setMobile(rs.getInt("mobile"));
                borrower.setEmail(rs.getString("email"));
                borrower.setAddress(rs.getString("address"));
                borrower.setGender(rs.getString("gender").charAt(0));
                borrowersHM.put(userId, borrower);
            }

        } catch (SQLException ex) {
            System.out.println("Error in Getting borrowers");
        }
    }

    public void getBooks() {
        try {
            ResultSet rs = this.statement.executeQuery("Select * From Books ");
            while (rs.next()) {
                Book book = new Book();
                Integer userId = rs.getInt("id");
                book.setId(userId);
                book.setName(rs.getString("name"));
                book.setDescription(rs.getString("description"));
                booksHM.put(userId, book);
            }

        } catch (SQLException ex) {
            System.out.println("Error in Getting books");
        }
    }

    public void addUpdateBookLayout(TextField idTF, TextField nameTF, TextArea desTA, Button addOperation, Button resetOperation,
            Label head, TableColumn idCol, TableColumn nameCol, TableColumn descCol, VBox body) {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(25);
        gridPane.setVgap(30);

        gridPane.add(new Label("ID:"), 0, 0);
        gridPane.add(new Label("Name:"), 0, 1);
        gridPane.add(new Label("Description:"), 0, 2);
        gridPane.add(idTF, 1, 0);
        gridPane.add(nameTF, 1, 1);
        gridPane.add(desTA, 1, 2);
        gridPane.add(new HBox(10, addOperation, resetOperation), 1, 3);

        head.setText("Adding Book");
        bookLV = new TableView();
        bookLV.getColumns().addAll(idCol, nameCol, descCol);

        booksHM.keySet().stream().sorted().forEach((b)
                -> bookLV.getItems().add(booksHM.get(b)));
        if (addOperation.getText().equals("Update")) {
            bookLV.getSelectionModel().selectedItemProperty().addListener(
                    e -> selectBook(bookLV, idTF, nameTF, desTA));
        }
        body.getChildren().setAll(new HBox(50, new VBox(head, gridPane),
                new VBox(40, new Label("Available Books"),
                        bookLV)));
    }

    public void addUpdateBorrowerLayout(TextField id2TF, TextField fnameTF, TextField lnameTF, TextField emailTF,
            TextField mobileTF, TextField addressTF, TextField genderTF, Button addOperation,
            Button resetOperation, Label head, TableColumn id2Col, TableColumn fnameCol, TableColumn lnameCol,
            TableColumn emailCol, TableColumn mobileCol, TableColumn addressCol, TableColumn genderCol, VBox body) {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(20);
        gridPane.setVgap(20);

        gridPane.add(new Label("ID:"), 0, 0);
        gridPane.add(new Label("FirstName:"), 0, 1);
        gridPane.add(new Label("LastName:"), 0, 2);
        gridPane.add(new Label("Email:"), 0, 3);
        gridPane.add(new Label("Mobile:"), 2, 0);
        gridPane.add(new Label("Address:"), 2, 1);
        gridPane.add(new Label("Gender:"), 2, 2);

        gridPane.add(id2TF, 1, 0);
        gridPane.add(fnameTF, 1, 1);
        gridPane.add(lnameTF, 1, 2);
        gridPane.add(emailTF, 1, 3);
        gridPane.add(mobileTF, 3, 0);
        gridPane.add(addressTF, 3, 1);
        gridPane.add(genderTF, 3, 2);
        gridPane.add(new HBox(10, addOperation, resetOperation), 2, 3);

        head.setText("Adding Borrower");

        borrowerLV = new TableView();
        borrowerLV.getColumns().addAll(id2Col, fnameCol, lnameCol, emailCol, mobileCol, addressCol, genderCol);
        borrowersHM.keySet().stream().sorted().forEach((Integer b) -> {
            borrowerLV.getItems().add(borrowersHM.get(b));
        });
        if (addOperation.getText().equals("Update")) {
            borrowerLV.getSelectionModel().selectedItemProperty().addListener(
                    e -> selectBorrower(borrowerLV, id2TF, fnameTF, lnameTF, emailTF, mobileTF, addressTF, genderTF));
        }
        body.getChildren().setAll(new VBox(10, new VBox(head, gridPane),
                new VBox(15, new Label("Avilable Borrowers :-"),
                        borrowerLV)));
    }

    public void BookOperations(String op, PrintWriter pw, TextField idTF, TextField nameTF, TextArea desTA) {
        try {
            Integer id = Integer.parseInt(idTF.getText());
            String name = nameTF.getText();
            String desc = desTA.getText();

            String sql = "";
            String toStore = "";
            try {
                if (op.equals("add")) {
                    sql = "Insert Into Books values(" + id + ",'" + name + "','"
                            + desc + "')";
                    toStore = "Adding Book";
                } else if (op.equals("update")) {
                    sql = "";
                    toStore = "";
                    if (booksHM.containsKey(id)) {
                        sql = "Update Books Set name='" + name + "', description='"
                                + desc + "' Where id=" + id;
                        toStore = "Updating Book From " + booksHM.get(id) + " To ";
                    }
                }
                statement.executeUpdate(sql);
                Book newBook = new Book(id, name, desc);
                booksHM.put(id, newBook);
                pw.println(toStore + newBook);
                pw.println("---------------------");
                pw.flush();
                bookLV.getItems().setAll();
                booksHM.keySet().stream().sorted().forEach(b -> bookLV.getItems().add(booksHM.get(b)));
                new Alert(Alert.AlertType.INFORMATION, " Done Operation :) ").show();
            } catch (SQLIntegrityConstraintViolationException e) {
                new Alert(Alert.AlertType.ERROR, "Book ID [ " + id + " ] is already exists ").show();
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "ERROR SQL ! Book ID [ " + id + " ] is not exists to update").show();
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, " Empty Fields OR miss match input data type . Try again ").show();
        }
    }

    public void BorrowerOperations(String op, PrintWriter pw, TextField id2TF, TextField fnameTF, TextField lnameTF, TextField emailTF,
            TextField mobileTF, TextField addressTF, TextField genderTF) {
        try {
            Integer id = Integer.parseInt(id2TF.getText());
            String fname = fnameTF.getText();
            String lname = lnameTF.getText();
            String email = emailTF.getText();
            Integer mobile = Integer.parseInt(mobileTF.getText());
            String address = addressTF.getText();
            char gender = genderTF.getText().charAt(0);
            String sql = "";
            String toStore = "";
            try {
                if (op.equals("add")) {
                    sql = "Insert Into Borrowers values(" + id + ",'" + fname + "','" + lname + "','" + mobile
                            + "','" + gender + "','" + email + "','" + address + "')";
                    toStore = "Adding Borrower";
                } else if (op.equals("update")) {
                    sql = "";
                    toStore = "";
                    if (borrowersHM.containsKey(id)) {
                        sql = "Update Borrowers Set first_name='" + fname + "', last_name='" + lname + "',mobile='" + mobile
                                + "',gender='" + gender + "',email='" + email
                                + "',address='" + address + "' Where id=" + id;
                        toStore = "Updating Borrower From " + borrowersHM.get(id) + " To ";
                    }
                }
                statement.executeUpdate(sql);
                Borrower newBorrower = new Borrower(id, fname, lname, mobile, email, address, gender);
                borrowersHM.put(id, newBorrower);
                pw.println(toStore + newBorrower);
                pw.println("---------------------");
                borrowerLV.getItems().setAll();
                borrowersHM.keySet().stream().sorted().forEach(b -> borrowerLV.getItems().add(borrowersHM.get(b)));
                new Alert(Alert.AlertType.INFORMATION, " Done Operation :) ").show();
            } catch (SQLIntegrityConstraintViolationException e) {
                new Alert(Alert.AlertType.ERROR, "Borrower ID [ " + id + " ] is already exists ").show();
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "ERROR SQL ! Borrower ID [ " + id + " ] is not exists to update").show();
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, " Empty Fields OR miss match input data type . Try again ").show();
        }

    }

    public void deleteSearchLayout(String op, String type, PrintWriter pw, TableColumn idCol, TableColumn nameCol, TableColumn descCol, TableColumn id2Col, TableColumn fnameCol, TableColumn lnameCol,
            TableColumn emailCol, TableColumn mobileCol, TableColumn addressCol, TableColumn genderCol, VBox body) {

        bookLV = new TableView();
        borrowerLV = new TableView();
        
        //search operation for Books and Borrowers
        if (op.equals("search")) {
            TextField searchTF = new TextField();
            searchTF.setPrefWidth(650);
            Button searchBt = new Button("Search");
            VBox vb = new VBox(20, new Label("Search : "), new HBox(20, searchTF, searchBt));
            vb.setAlignment(Pos.CENTER_LEFT);

            searchBt.setOnAction(event -> {
                String querySearch = searchTF.getText();
                if (type.equals("Book")) {
                    searchBook(querySearch, pw);
                } else if (type.equals("Borrower")) {
                    searchBorrower(querySearch, pw);
                }
            });
            if (type.equals("Book")) {
                bookLV.getColumns().addAll(idCol, nameCol, descCol);
                body.getChildren().setAll(new VBox(30, vb, bookLV));

            } else if (type.equals("Borrower")) {
                borrowerLV.getColumns().addAll(id2Col, fnameCol, lnameCol, mobileCol, emailCol, addressCol, genderCol);
                body.getChildren().setAll(new VBox(30, vb, borrowerLV));
            }

        } //End search
        //Delete Operation For both Books and Borrowers
        else if (op.equals("delete")) {
            Button deleteBt = new Button("Delete");
            VBox v = new VBox(30, new Label("Available In Database :"));
            v.setAlignment(Pos.CENTER_LEFT);
            if (type.equals("Book")) {
                v.getChildren().addAll(bookLV, deleteBt);
                bookLV.getColumns().addAll(idCol, nameCol, descCol);
            } else if (type.equals("Borrower")) {
                v.getChildren().addAll(borrowerLV, deleteBt);
                borrowerLV.getColumns().addAll(id2Col, fnameCol, lnameCol, mobileCol, emailCol, addressCol, genderCol);
            }
            body.getChildren().setAll(v);
            //for Book
            if (type.equals("Book")) {
                booksHM.keySet().stream().sorted().forEach(b -> bookLV.getItems().add(booksHM.get(b)));

                deleteBt.setOnAction(event -> {
                    Integer id = bookLV.getSelectionModel().getSelectedItem().getId();
                    Optional<ButtonType> isOk = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure about delete operation").showAndWait();
                    if (ButtonType.OK == isOk.get()) {
                        try {
                            String sql = "delete from books where id = " + id;
                            statement.executeUpdate(sql);

                            Book k = booksHM.remove(id);
                            pw.println("Delete Book : " + k);
                            pw.println("---------------------");
                            pw.flush();
                            new Alert(Alert.AlertType.INFORMATION, " Done Operation :) ").show();
                            bookLV.getItems().setAll();
                            booksHM.keySet().stream().sorted().forEach(b -> bookLV.getItems().add(booksHM.get(b)));

                        } catch (SQLException ex) {
                            System.out.println("Erorr in deleting book");
                        }
                    }
                }
                );
                //for Borrower
            } else if (type.equals("Borrower")) {
                borrowersHM.keySet().stream().sorted().forEach(b -> borrowerLV.getItems().add(borrowersHM.get(b)));

                deleteBt.setOnAction(event -> {
                    Optional<ButtonType> isOk = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure about delete operation").showAndWait();
                    if (ButtonType.OK == isOk.get()) {

                        Integer id = borrowerLV.getSelectionModel().getSelectedItem().getId();
                        try {
                            String sql = "delete from borrowers where id = " + id;
                            statement.executeUpdate(sql);
                            Borrower br = borrowersHM.remove(id);
                            pw.println("Delete Borrower : " + br);
                            pw.println("---------------------");
                            pw.flush();

                            new Alert(Alert.AlertType.INFORMATION, " Done Operation :) ").show();
                            borrowerLV.getItems().setAll();
                            borrowersHM.keySet().stream().sorted().forEach(b -> borrowerLV.getItems().add(borrowersHM.get(b)));

                        } catch (SQLException ex) {
                            System.out.println("Erorr in deleting borrower");
                        }
                    }
                }
                );

            }
        }
    }

    private void searchBook(String querySearch, PrintWriter pw) {
        bookLV.getItems().setAll();

        booksHM.keySet().stream().forEach((Integer k) -> {
            Book b = booksHM.get(k);
            if (b.getId().toString().contains(querySearch) || b.getName().contains(querySearch)
                    || b.getDescription().contains(querySearch)) {
                System.out.println("found********** " + k);
                bookLV.getItems().add(b);
                pw.println("Search Operation {"
                        + querySearch + "}: Match result with Book [ " + b + " ]");

            }
        }
        );
    }

    private void searchBorrower(String querySearch, PrintWriter pw) {
        borrowerLV.getItems().setAll();

        borrowersHM.keySet().stream().forEach((Integer k) -> {
            Borrower b = borrowersHM.get(k);
            if (b.getId().toString().equals(querySearch) //|| b.getFirstName().equals(querySearch) //   || b.getDescription().contains(querySearch) 
                    ) {
                System.out.println("found********** " + k);
                borrowerLV.getItems().add(b);
                pw.println("Search Operation {" + querySearch + "}: Match result with Borrower [ " + b + " ]");
            }
        }
        );
    }
    HashMap<Integer, Borrower> mngborrowHM = (HashMap) borrowersHM.clone();
    HashMap<Integer, Book> mngBookHM = (HashMap) booksHM.clone();
    TableView<borrowerBooks> tableView;

    public void borrowBook(PrintWriter pw, TableColumn Book_id, TableColumn Borrower_id, TableColumn borrowers_date, TableColumn Return_date,
            TableColumn idCol, TableColumn nameCol, TableColumn id2Col, TableColumn fnameCol, TableColumn lnameCol, VBox body) {

        TableView<Book> avBook = new TableView();
        avBook.setPrefWidth(250);
        avBook.getColumns().setAll(idCol, nameCol);
        TableView<Borrower> avBorrower = new TableView();
        avBorrower.getColumns().setAll(id2Col, fnameCol, lnameCol);

        borrowersHM.keySet().stream().sorted().forEach(b -> avBorrower.getItems().add(borrowersHM.get(b)));

        booksHM.keySet().stream().sorted().forEach(b -> avBook.getItems().add(booksHM.get(b)));
        avBook.getItems().removeAll(getBB().keySet());

        VBox vv = new VBox(20, new Label("Available Books:"), avBook, new Label("Available Borrowers:"), avBorrower);
        TableView<borrowerBooks> borrowing = new TableView();
        borrowing.getItems().addAll(getBB().values());
        borrowing.setPrefWidth(400);
        borrowing.getColumns().setAll(Book_id, Borrower_id, borrowers_date, Return_date);
        TextField bookId = new TextField();
        TextField borrowerId = new TextField();
        TextField bdate = new TextField();
        TextField rdate = new TextField();
        Button borrow = new Button("Borrow Now");
        Button returned = new Button("Return Now");

        GridPane gp = new GridPane();
        gp.setHgap(20);
        gp.setVgap(10);
        gp.add(new Label("Book ID :"), 0, 0);
        gp.add(bookId, 1, 0);
        gp.add(new Label("Borrower ID :"), 0, 1);
        gp.add(borrowerId, 1, 1);
        gp.add(new Label("Borrow Date :"), 0, 2);
        gp.add(bdate, 1, 2);
        gp.add(new Label("Return Date :"), 0, 3);
        gp.add(rdate, 1, 3);

        gp.add(borrow, 2, 3);
        Label h1 = new Label("Borrow Book:");
        h1.setStyle("-fx-font-size:26px;-fx-font-weight:900;-fx-text-fill:#ff1654;");
        Label h2 = new Label("Return Book :");
        h2.setStyle("-fx-font-size:26px;-fx-font-weight:900;-fx-text-fill:#ff1654;");
        VBox a = new VBox(20, h1, gp, h2, borrowing, returned);
        HBox v = new HBox(40, a, vv);
        v.setAlignment(Pos.CENTER);

        body.getChildren().setAll(v);

        avBook.getSelectionModel().selectedItemProperty().addListener(e -> {
            bookId.setText(avBook.getSelectionModel().getSelectedItem().getId() + "");
        });
        avBorrower.getSelectionModel().selectedItemProperty().addListener(e -> {
            borrowerId.setText(avBorrower.getSelectionModel().getSelectedItem().getId() + "");
        });

        borrow.setOnAction(value -> {
            try {
                int d1 = Integer.parseInt(bookId.getText());
                int d2 = Integer.parseInt(borrowerId.getText());
                Date bd = Date.valueOf(bdate.getText());
                Date rd = Date.valueOf(rdate.getText());
                this.statement.executeUpdate("insert into borrower_books values(" + d1 + "," + d2 + ",'" + bd + "','" + rd + "')");
                borrowerBooks bb = new borrowerBooks(d1, d2, bd, rd);
                borrowing.getItems().add(bb);
                avBook.getItems().remove(booksHM.get(Integer.parseInt(bookId.getText())));
                pw.println("The Borrower " + d2 + " borrow A Book ID " + d1 + " from " + bd + " To " + rd);
                pw.println("-----------------"); pw.flush();
                // avBorrower.getItems().remove(borrowersHM.get(bwid));
            } catch (SQLException ex) {
                System.out.println("Error in inserting borrower_book ");
            }
        });
        
        returned.setOnAction(value -> {
            borrowerBooks x = borrowing.getSelectionModel().getSelectedItem();
            try {
                Integer id = x.getBook_id();
                this.statement.executeUpdate("delete from borrower_books where Book_id =" + id);
                avBook.getItems().add(booksHM.get(x.getBook_id()));
                borrowing.getItems().remove(x);
                pw.println("The  Book ID " + id + " is Returned and Now available to borrow agaiin");
                pw.println("------------------");
                pw.flush();
            } catch (SQLException ex) {
                System.out.println("Error in delete from borrower_book ");
            }
        });
    }

    HashMap<Book, borrowerBooks> getBB() {
        HashMap<Book, borrowerBooks> hm = new HashMap<>();
        try {
            ResultSet rs = statement.executeQuery("select * from borrower_Books");
            while (rs.next()) {
                hm.put(booksHM.get(rs.getInt("Book_id")),
                        new borrowerBooks(rs.getInt("Book_id"), rs.getInt("Borrower_id"),
                                rs.getDate("borrowers_date"), rs.getDate("Return_date")));
            }
        } catch (SQLException ex) {
            System.out.println("Error in grtting borrowerBooks");
        }
        return hm;
    }

}
