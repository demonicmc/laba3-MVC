package com.laba3.bigchat.client;


import com.laba3.bigchat.Connection;
import com.laba3.bigchat.ConsoleHelper;
import com.laba3.bigchat.Message;
import com.laba3.bigchat.MessageType;

import java.io.IOException;
import java.net.Socket;


public class Client {
    public Connection connection;
    private volatile boolean clientConnected;

    public class SocketThread extends Thread {

        public void processIncomingMessage(String message) {
            ConsoleHelper.writeMessage(message);
        }


        public void notifyConnectionStatusChanged(boolean clientConnected) {
            Client.this.clientConnected = clientConnected;
            synchronized (Client.this) {
                Client.this.notify();
            }
        }

        public void clientHandshake() throws IOException, ClassNotFoundException {
            Message message;
            while (true) {
                message = connection.receive();
                if (message.getType().equals(MessageType.NAME_REQUEST)) {
                    connection.send(new Message(MessageType.USER_NAME, getUserName()));
                } else if (message.getType().equals(MessageType.NAME_ACCEPTED)) {
                    notifyConnectionStatusChanged(true);
                    return;
                } else throw new IOException("Unexpected MessageType");
            }
        }

        public void clientMainLoop() throws IOException, ClassNotFoundException {
            Message message;
            while (true) {
                message = connection.receive();
                processIncomingMessage(message.getData());
            }
        }

        public void run() {
            try {
                Socket socket = new Socket("localhost", 8080);
                connection = new Connection(socket);
                clientHandshake();
                clientMainLoop();
            } catch (IOException e) {
                notifyConnectionStatusChanged(false);
            } catch (ClassNotFoundException e) {
                notifyConnectionStatusChanged(false);
            }
        }
    }

    public void run() {
        SocketThread socketThread = getSocketThread();
        socketThread.setDaemon(true);
        socketThread.start();
        synchronized (this) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
        }
        if (clientConnected) {
            ConsoleHelper.writeMessage("Соединение установлено.");
        } else {
            ConsoleHelper.writeMessage("Произошла ошибка.");
        }
        while (clientConnected) {
            sendTextMessage(ConsoleHelper.readString());
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.run();
    }

    public String getUserName() {
        ConsoleHelper.writeMessage("Введите имя");
        return ConsoleHelper.readString();
    }

    public SocketThread getSocketThread() {
        return new SocketThread();
    }

    public void sendTextMessage(String text) {
        try {
            connection.send(new Message(MessageType.TEXT, text));
        } catch (IOException e) {
            ConsoleHelper.writeMessage("Connection error");
            clientConnected = false;
        }
    }
}
