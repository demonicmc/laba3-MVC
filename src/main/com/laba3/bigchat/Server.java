package com.laba3.bigchat;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    private static Map<String, Connection> connectionMap = new ConcurrentHashMap<>();


    private static class Handler extends Thread {
        @Override
        public void run() {
            String userName;
            try (Connection connection = new Connection(socket)) {
                ConsoleHelper.writeMessage("установлено новое соединение");
                userName = serverHandshake(connection);
                serverMainLoop(connection, userName);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        private Socket socket;

        public Handler(Socket socket) {
            this.socket = socket;
        }

        private String serverHandshake(Connection connection) throws IOException, ClassNotFoundException {
            while (true) {
                Message message_request = new Message(MessageType.NAME_REQUEST);
                connection.send(message_request);
                Message message_response = connection.receive();
                if (message_response.getType() == MessageType.USER_NAME) {
                    String name = message_response.getData();
                    if (name != null && !name.isEmpty()) {
                        if (!connectionMap.containsKey(name)) {
                            connectionMap.put(name, connection);
                            Message message = new Message(MessageType.NAME_ACCEPTED);
                            connection.send(message);
                            return name;
                        }
                    }
                }
            }
        }

        private void serverMainLoop(Connection connection, String userName) throws IOException, ClassNotFoundException {
            while (true) {
                Message message = connection.receive();
                sendBroadcastMessage(new Message(MessageType.TEXT,
                        String.format("%s: %s", userName, message.getData())));
            }
        }
    }

    public static void sendBroadcastMessage(Message message) {
        for (Map.Entry<String, Connection> pair : connectionMap.entrySet()) {
            try {
                pair.getValue().send(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("Server run");
            while (true) {
                Handler handler = new Handler(serverSocket.accept());
                handler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
