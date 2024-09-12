package org.example.clientgui;

import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;

public class HelloController {

    @FXML
    private TextField messageField; //do wpisywania wiadomosci

    @FXML
    private TextArea chatArea;  // wyswietla historiee czatu

    @FXML
    private ListView<String> userList;  //lista aktywnych uczestnikow

    //steruje interfejsem uzytkownika, dzieki temu mozna aktualizowac dane
    public HelloController() {
        ClientReceiver.controller = this;
    }

    //wysyla wiadomosci po kliknieciu przycisku lub enter
    @FXML
    public void sendMessage() throws JsonProcessingException {
        String message = messageField.getText();  //pobiera tekst z pola wiadomosci

        if (!message.isEmpty()) {
            chatArea.appendText("Me: " + message + "\n");
            messageField.clear();  //czysci to pole tekstowe po wyslaniu wiadomosci

            Message messageToSend = new Message(MessageType.Broadcast, message); //tworxy obiekt do wyslania na serwer
            ClientReceiver.thread.send(messageToSend); //wysyla wiadomosc do serwera
    }
}

//aktualizuje textArea po otrzymaniu wiadomosci od serwera, dodaje wiadomosc do pola czatu
public void onMessage(String message){
        chatArea.appendText(message + "\n");
}


    public void updateUserList(String[] users) {
        userList.getItems().clear();  //czysci liste uzytkownikow
        userList.getItems().addAll(users);  //dodaje nowych do listy
    }



//    @FXML
//    private Label welcomeText;
//
//    @FXML
//    protected void onHelloButtonClick() {
//        welcomeText.setText("Welcome to JavaFX Application!");
//    }

}