package org.example.clientgui;

//odbiera wiadomosci z serwera i przekazuje do gui
public class ClientReceiver {


    public static HelloController controller;
    public static ConnectionThread thread;

//obsluguje wiadomosci typu broadcast
    public static void receiveBroadcast(String message) {
        controller.onMessage(message);
    }

    public static void receiveWhisper(String message) {
        controller.onMessage("Whisper: " + message);
    }

    public static void handleLoginBroadcast(String user) {
        controller.onMessage(user + " has joined the chat.");
    }

    public static void handleLogoutBroadcast(String user) {
        controller.onMessage(user + " has left the chat.");
    }

    public static void updateOnlineUsers(String[] users) {
        controller.updateUserList(users);
    }

    public static void receiveFile(String fileName) {
        controller.onMessage("File received: " + fileName);
    }
}
