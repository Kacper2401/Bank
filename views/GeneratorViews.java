package sample.views;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.transfer.TransferException;
import sample.transfer.TransferManagement;
import sample.users.AccountException;
import java.awt.print.Book;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GeneratorViews extends TransferManagement {
    private final Stage windows;
    private String userName;
    private int userId;
    private float account_balance;

    public GeneratorViews(Stage primaryStage) {
        super();
        windows = primaryStage;
    }

    public Scene getLogInView() {
        Group view = new Group();
        Button buttonLogin = new Button("Log in");
        Button buttonRegister = new Button("Register");
        TextField fieldLogin = new TextField();
        PasswordField fieldPassword = new PasswordField();
        Text messageLogin = new Text();
        Text messagePassword = new Text();
        Text messageWelcome = new Text();

        messageWelcome.setText("International Bank System");
        messageWelcome.setLayoutX(60);
        messageWelcome.setLayoutY(70);
        messageWelcome.setStyle("-fx-font: 26 arial");
        messageWelcome.setFill(Color.WHITE);

        messageLogin.setText("Login");
        messageLogin.setLayoutX(70);
        messageLogin.setLayoutY(155);
        messageLogin.setStyle("-fx-font: 26 arial");
        messageLogin.setFill(Color.WHITE);

        fieldLogin.setLayoutX(200);
        fieldLogin.setLayoutY(135);

        messagePassword.setText("Password");
        messagePassword.setLayoutX(70);
        messagePassword.setLayoutY(205);
        messagePassword.setStyle("-fx-font: 26 arial");
        messagePassword.setFill(Color.WHITE);

        fieldPassword.setLayoutX(200);
        fieldPassword.setLayoutY(185);

        buttonLogin.setLayoutX(100);
        buttonLogin.setLayoutY(250);
        buttonLogin.setPrefWidth(75);

        buttonRegister.setLayoutX(200);
        buttonRegister.setLayoutY(250);
        buttonRegister.setPrefWidth(75);

        addLogInEventToButton(view, buttonLogin, fieldLogin, fieldPassword);
        addRegisterEventToButton(view, buttonRegister, fieldLogin, fieldPassword);

        view.getChildren().addAll(buttonLogin, fieldLogin,
                                    fieldPassword, messageLogin,
                                    messagePassword, messageWelcome,
                                    buttonRegister);

        return new Scene(view, 410, 320, Color.rgb(100, 0, 190));
    }

    private Scene getAccountCockpitView() {
        Group view = new Group();
        Button buttonTransferMoney = new Button("Transfer money");
        Button buttonTransferHistory = new Button("Transfer history");
        Button buttonLogOut = new Button("Log out");
        Button buttonDeleteAccount = new Button("Delete account");
        Text messageUserName = new Text();
        Text messageAccountBalance = new Text();

        messageUserName.setText("User " + this.userName);
        messageUserName.setLayoutX(200);
        messageUserName.setLayoutY(50);
        messageUserName.setStyle("-fx-font: 26 arial");
        messageUserName.setFill(Color.WHITE);

        messageAccountBalance.setText("Account balance " + this.account_balance);
        messageAccountBalance.setLayoutX(120);
        messageAccountBalance.setLayoutY(100);
        messageAccountBalance.setStyle("-fx-font: 26 arial");
        messageAccountBalance.setFill(Color.WHITE);

        buttonTransferMoney.setMinWidth(100);
        buttonTransferMoney.setMinHeight(40);
        buttonTransferMoney.setLayoutX(40);
        buttonTransferMoney.setLayoutY(200);

        buttonTransferHistory.setMinWidth(100);
        buttonTransferHistory.setMinHeight(40);
        buttonTransferHistory.setLayoutX(160);
        buttonTransferHistory.setLayoutY(200);

        buttonLogOut.setMinWidth(100);
        buttonLogOut.setMinHeight(40);
        buttonLogOut.setLayoutX(280);
        buttonLogOut.setLayoutY(200);

        buttonDeleteAccount.setMinWidth(100);
        buttonDeleteAccount.setMinHeight(40);
        buttonDeleteAccount.setLayoutX(400);
        buttonDeleteAccount.setLayoutY(200);

        view.getChildren().addAll(buttonTransferMoney, buttonTransferHistory,
                                    buttonLogOut, messageUserName,
                                    messageAccountBalance, buttonDeleteAccount);

        addExitEventToButton(buttonLogOut);
        addGetTransferMoneyEventToButton(buttonTransferMoney);
        addGetHistoryTransferEventToButton(buttonTransferHistory);
        addDeleteEventToButton(buttonDeleteAccount);

        return new Scene(view, 530, 320, Color.rgb(100, 0, 190));
    }

    private Scene getTransferMoneyView() {
        Group view = new Group();
        Button buttonReturn = new Button("Return");
        Button buttonMakeTransfer = new Button("Transfer");
        TextField fieldToAccountName = new TextField();
        TextField fieldAmount = new TextField();
        TextField fieldTitle = new TextField();
        TextArea textAreaDescription = new TextArea();
        Text messageAccountName = new Text();
        Text messageAmount = new Text();
        Text messageTitle = new Text();
        Text messageDescription = new Text();

        messageAccountName.setText("Recipient");
        messageAccountName.setLayoutX(150);
        messageAccountName.setLayoutY(100);
        messageAccountName.setStyle("-fx-font: 26 arial");
        messageAccountName.setFill(Color.WHITE);

        fieldToAccountName.setLayoutX(130);
        fieldToAccountName.setLayoutY(120);

        messageAmount.setText("Amount");
        messageAmount.setLayoutX(160);
        messageAmount.setLayoutY(200);
        messageAmount.setStyle("-fx-font: 26 arial");
        messageAmount.setFill(Color.WHITE);

        fieldAmount.setLayoutX(130);
        fieldAmount.setLayoutY(220);

        messageTitle.setText("Title");
        messageTitle.setLayoutX(180);
        messageTitle.setLayoutY(300);
        messageTitle.setStyle("-fx-font: 26 arial");
        messageTitle.setFill(Color.WHITE);

        fieldTitle.setLayoutX(60);
        fieldTitle.setLayoutY(320);
        fieldTitle.setMinWidth(300);

        messageDescription.setText("Description");
        messageDescription.setLayoutX(140);
        messageDescription.setLayoutY(400);
        messageDescription.setStyle("-fx-font: 26 arial");
        messageDescription.setFill(Color.WHITE);

        textAreaDescription.setLayoutX(0);
        textAreaDescription.setLayoutY(410);
        textAreaDescription.setMaxHeight(150);

        buttonReturn.setLayoutY(570);
        buttonReturn.setLayoutX(140);

        buttonMakeTransfer.setLayoutY(570);
        buttonMakeTransfer.setLayoutX(220);

        eventGetMenuView(buttonReturn);
        addMakeTransferEventToButton(view, buttonMakeTransfer, fieldToAccountName, fieldAmount, fieldTitle, textAreaDescription);

        view.getChildren().addAll(buttonReturn, buttonMakeTransfer, fieldToAccountName,
                                    fieldAmount, fieldTitle, textAreaDescription,
                                    messageAccountName, messageAmount, messageTitle,
                                    messageDescription);

        return new Scene(view, 410, 600, Color.rgb(100, 0, 190));
    }

    private Scene getHistoryTransferView() {
        Group view = new Group();
        Button buttonReturn = new Button("Return");
        TableView<Book> table;

        TableColumn<Book, String> columnFrom = new TableColumn<>("From");
        columnFrom.setMinWidth(200);
        columnFrom.setCellValueFactory(new PropertyValueFactory<>("fromAccountId"));

        TableColumn<Book, String> columnTo = new TableColumn<>("To");
        columnTo.setMinWidth(200);
        columnTo.setCellValueFactory(new PropertyValueFactory<>("toAccountId"));

        TableColumn<Book, String> columnAmount = new TableColumn<>("Amount");
        columnAmount.setMinWidth(100);
        columnAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));

        TableColumn<Book, String> columnTitle = new TableColumn<>("Title");
        columnTitle.setMinWidth(200);
        columnTitle.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Book, String> columnDescription = new TableColumn<>("Description");
        columnDescription.setMinWidth(500);
        columnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<Book, String> columnData = new TableColumn<>("Transfer Date");
        columnData.setMinWidth(200);
        columnData.setCellValueFactory(new PropertyValueFactory<>("transferDate"));

        table = new TableView<>();
        table.setItems(getUserTransferList(getUserTransfers(this.userId)));
        table.getColumns().addAll(columnFrom, columnTo, columnTitle, columnDescription, columnAmount, columnData);

        buttonReturn.setLayoutY(412.5);
        buttonReturn.setLayoutX(650);
        buttonReturn.minWidth(100);

        eventGetMenuView(buttonReturn);

        view.getChildren().addAll(table, buttonReturn);

        return new Scene(view, 1400, 450, Color.rgb(100, 0, 190));
    }

    private ObservableList<Book> getUserTransferList(ResultSet transferList) {
        ObservableList<Book> transfers = FXCollections.observableArrayList();
        try {
            while(transferList.next()) {
                float amount =  transferList.getFloat(3);

                if(transferList.getInt(1) == userId) {
                    amount *= -1;
                }

                transfers.add(new UserTransfers(transferList.getInt(1),
                                                transferList.getInt(2),
                                                amount,
                                                transferList.getTimestamp(4).toString().substring(0, 19),
                                                transferList.getString(5),
                                                transferList.getString(6)));
            }
        } catch (SQLException throwable) {
            System.out.println("Problem downloading data to table: " + throwable.getMessage());
        }

        return transfers;
    }

    private void addLogInEventToButton(Group view, Button button, TextField login, TextField password) {
        EventHandler<ActionEvent> logIn = event -> {
            Text wrongData = new Text();

            view.getChildren().remove(view.lookup("#wrongData"));

            wrongData.setLayoutX(60);
            wrongData.setLayoutY(310);
            wrongData.setStyle("-fx-font: 24 arial");
            wrongData.setFill(Color.WHITE);
            wrongData.setId("wrongData");

            if(!login.getText().isEmpty() && !password.getText().isEmpty()) {
                try {
                    ResultSet userData = logIn(login.getText(), password.getText());

                    if (userData.getFloat(1) != -1) {
                        this.userName = login.getText();
                        this.userId = userData.getInt(2);
                        this.account_balance = userData.getFloat(1);

                        windows.setScene(getAccountCockpitView());
                    } else {

                        wrongData.setText("Wrong username or password");
                    }
                } catch (SQLException throwable) {
                    System.out.println("Error with log in: " + throwable.getMessage());
                } catch (AccountException.AccountNotFound accountNotFound) {
                    view.getChildren().remove(view.lookup("#wrongData"));

                    wrongData.setText(accountNotFound.getMessage());
                    wrongData.setLayoutX(80);
                }
            }else {
                wrongData.setText("Empty field");
                wrongData.setLayoutX(140);
            }

            view.getChildren().add(wrongData);
        };

        button.setOnAction(logIn);
    }

    private void addMakeTransferEventToButton(Group view, Button button, TextField toAccountName, TextField amount, TextField title, TextArea description) {
        EventHandler<ActionEvent> makeTransfer = event -> {
            Text wrongData = new Text();

            view.getChildren().remove(view.lookup("#wrongData"));

            wrongData.setLayoutX(150);
            wrongData.setLayoutY(50);
            wrongData.setStyle("-fx-font: 24 arial");
            wrongData.setFill(Color.WHITE);
            wrongData.setId("wrongData");

            if(!toAccountName.getText().isEmpty() && !amount.getText().isEmpty() && !title.getText().isEmpty() && !description.getText().isEmpty()) {
                try {
                    moneyTransfer(this.userId, checkAccountId(toAccountName.getText()), Float.parseFloat(amount.getText()), title.getText(), description.getText());

                    this.account_balance -= Float.parseFloat(amount.getText());

                    wrongData.setText("Transfer has been completed");
                    wrongData.setLayoutX(70);
                } catch (NumberFormatException ignored) {
                    wrongData.setText("Wrong data");
                } catch (TransferException.NotEnoughMoney notEnoughMoney) {
                    wrongData.setLayoutX(100);
                    wrongData.setText(notEnoughMoney.getMessage());
                } catch (TransferException.UserDoseNotExist userDoseNotExist) {
                    wrongData.setLayoutX(120);
                    wrongData.setText(userDoseNotExist.getMessage());
                }
            }else {
                wrongData.setText("Empty field");
            }

            view.getChildren().add(wrongData);
        };

        button.setOnAction(makeTransfer);
    }

    private void addRegisterEventToButton(Group view, Button button, TextField login, TextField password) {
        EventHandler<ActionEvent> register = event -> {
            Text wrongData = new Text();

            view.getChildren().remove(view.lookup("#wrongData"));

            wrongData.setLayoutY(310);
            wrongData.setStyle("-fx-font: 24 arial");
            wrongData.setFill(Color.WHITE);
            wrongData.setId("wrongData");

            if(!login.getText().isEmpty() && !password.getText().isEmpty()) {
                view.getChildren().remove(view.lookup("#wrongData"));
                try {
                    addAccount(login.getText(), password.getText());

                    wrongData.setText("Account has been created");
                } catch (AccountException.AccountExist accountExist) {
                    wrongData.setText(accountExist.getMessage());
                    wrongData.setLayoutX(100);
                }
            }else {
                wrongData.setText("Empty field");
                wrongData.setLayoutX(140);
            }

            view.getChildren().add(wrongData);
        };

        button.setOnAction(register);
    }

    private void addDeleteEventToButton(Button button) {
        EventHandler<ActionEvent> deleteAccount = event -> {

            deleteAccount(this.userId);
            Platform.exit();
        };

        button.setOnAction(deleteAccount);
    }

    private void addGetTransferMoneyEventToButton(Button button) {
        EventHandler<ActionEvent> newView = event -> windows.setScene(getTransferMoneyView());

        button.setOnAction(newView);
    }

    private void addGetHistoryTransferEventToButton(Button button) {
        EventHandler<ActionEvent> newView = event -> windows.setScene(getHistoryTransferView());

        button.setOnAction(newView);
    }

    private void eventGetMenuView(Button button) {
        EventHandler<ActionEvent> newView = event -> windows.setScene(getAccountCockpitView());

        button.setOnAction(newView);
    }

    private void addExitEventToButton(Button button) {
        EventHandler<ActionEvent> exit = event -> Platform.exit();

        button.setOnAction(exit);
    }
}
