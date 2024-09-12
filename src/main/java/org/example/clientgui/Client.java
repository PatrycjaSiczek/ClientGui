package org.example.clientgui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//laczy z serwerem, tworzy connectionthread
public class Client {

//start laczy z serwerem tworzac nowy watek
    public void start(String address, int port) {
        try {
            ConnectionThread thread = new ConnectionThread(address, port);
            thread.start();

//odczutuje tekst wpisany przez uzytkownika, czyta dane z konsoli
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(System.in)
            );

//umozliwia wpisanie loginu i wysyla wiadomosci do serwera
            String login = reader.readLine();
            thread.login(login);

//uzytkownik wpisuje dane i leca one do serwera
            while(true) {
                String rawMessage = reader.readLine(); //odczytuje wiadomosc uzytkownika z konsoli
                Message message = new Message(  //tworzy nowy obiekt
                        MessageType.Broadcast, rawMessage);
                thread.send(message); //wysyla wiadomosc do serwera
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}