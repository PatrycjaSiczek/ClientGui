package org.example.clientgui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;


//trona startova javafx uruchamia GUI i tworzy polaczenie z serwerem
public class HelloApplication extends Application {
    @Override

//start uruchamia caly interfejs graficzny
    public void start(Stage stage) throws IOException {

//wczytuje plik FXML definuje on wyglad okna GUI
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
//tworzenie nowego okna bazujac o plik FXML o tym rozmiarze
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
//tworzenie nowego watku do polaczenia z sererem
        ConnectionThread thread = new ConnectionThread("localhost", 5001);
//nasluchuje wiadomosci z serera
        thread.start();
//stworzonyv watek bedzie przesylany do Clientreciver aby komunikowac sie
        ClientReceiver.thread = thread;

//wyswietla okno dialogowe, aby moc wpisac login
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Logowanie");
        dialog.setContentText("Wpisz login");

        Optional<String> login = dialog.showAndWait(); //oczekuje na login od uzytkownika

        if (login.isPresent()) {
            thread.login(login.get());  //przesylanie loginu do serwera

            stage.setTitle("Hello!");  //tytul okna
            stage.setScene(scene); //ustawienie widoku z pliku FXML
            stage.show();  //wystwielanie okna GUI
        }
    }
}