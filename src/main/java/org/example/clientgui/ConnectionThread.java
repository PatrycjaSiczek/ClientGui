package org.example.clientgui;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.Socket;


//komunikuje sie z serwerem
public class ConnectionThread extends Thread {

//to socket umozliwia polaczenie z serwerem
    Socket socket;

//a to wysyla dane do serwera
    PrintWriter writer;

//tworzy polaczenie zserwerem
    public ConnectionThread(String address, int port) throws IOException {
        socket = new Socket(address, port);
    }

//cala funkcja ktora nasluchuje wiadomosci z serwera i wywoluje metody w ClientReceiver
    public void run() {
        try {
            InputStream input = socket.getInputStream(); //odbiera dane
            OutputStream output = socket.getOutputStream(); //wysyla dane

            BufferedReader reader = new BufferedReader( //odczytuje dane
                    new InputStreamReader(input)
            );
            writer = new PrintWriter(output,true); //wysyla wiadomosci

            String rawMessage;  //ona bedzie odczytywac wiadomosci
            // "{"type": "", "content": ""}"

//program odczytuje linie tekstu (JSON) z serwera za pomoca reader,readLine()
            while((rawMessage = reader.readLine()) != null) {
                Message message = new ObjectMapper() //parsowanie wiadomosci JSON rawmessage do Message za pomoca ObjectMapper
 //odczytana wartosc rawMessage jest w formacie JSON
                        .readValue(rawMessage, Message.class);

//zalezy jaki jest typ wiadomosci
                switch (message.type) {
                    case Broadcast -> ClientReceiver.receiveBroadcast(message.content);
                    case Whisper -> ClientReceiver.receiveWhisper(message.content);
                    case Login -> ClientReceiver.handleLoginBroadcast(message.content);
                    case Logout -> ClientReceiver.handleLogoutBroadcast(message.content);
                    case Online -> ClientReceiver.updateOnlineUsers(message.content.split(","));
                    case File -> ClientReceiver.receiveFile(message.content);
                }
            }
        } catch (IOException e) {throw new RuntimeException(e);}

    }

//obiekt message jest brany do formatu JSON za pomoca ObjectMapper.wite...
//konwertuje obiekt Message do formatu JSON i wysyla do serwera
    public void send(Message message) throws JsonProcessingException {
        String rawMessage = new ObjectMapper()
                .writeValueAsString(message);

//rawmessage wysylana jest do serwera za pomoca tego:
        writer.println(rawMessage);
    }

//wysyla do serwera
    public void login(String login) throws JsonProcessingException {
        Message message = new Message(MessageType.Login, login);
        send(message);
    }
}