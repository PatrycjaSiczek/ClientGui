package org.example.clientgui;


//reprezentuje wiadomosci przesylane miedzy klientem a serwerem
public class Message {
    public MessageType type;
    public String content;  //tresc


    //domyslny kontrukotr np do deserializacji JSON
    public Message() {}

    //konstruktor do tworzeniss obiektu message z ypen i trescia
    public Message(MessageType type, String content) {
        this.type = type;
        this.content = content;
    }
}