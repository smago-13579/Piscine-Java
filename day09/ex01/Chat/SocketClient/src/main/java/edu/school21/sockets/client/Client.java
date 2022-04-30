package edu.school21.sockets.client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private PrintWriter writer;
    private Scanner reader;

    public Client(String ip, int port) throws IOException {
        socket = new Socket(ip, port);
        reader = new Scanner(socket.getInputStream());
        writer = new PrintWriter(socket.getOutputStream(), true);
    }

    public void start() {
        ServerWriter serverWriter = new ServerWriter(writer);
        ServerReader serverReader = new ServerReader(reader, serverWriter);
        serverReader.start();
        serverWriter.start();

        try {
            serverReader.join();
            serverWriter.join();
            stop();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void stop() {
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        System.exit(0);
    }

    private class ServerReader extends Thread {
        private Scanner reader;
        ServerWriter serverWriter;

        public ServerReader(Scanner reader, ServerWriter serverWriter) {
            this.reader = reader;
            this.serverWriter = serverWriter;
        }

        @Override
        public void run() {
            try {
                receiveMessage();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

        private void receiveMessage() throws IOException {
            while (reader.hasNextLine()) {
                String message = reader.nextLine();
                System.out.println(message);

                if ("exit".equals(message)) {
                    reader.close();
                    serverWriter.active = false;
                    break;
                }
            }
        }
    }

    private class ServerWriter extends Thread {
        private PrintWriter writer;
        private Scanner scanner = new Scanner(System.in);
        private boolean active = true;

        public ServerWriter(PrintWriter writer) {
            this.writer = writer;
        }

        @Override
        public void run() {
            try {
                sendMessage();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        private void sendMessage() {
            while (active) {
                String message = scanner.nextLine();
                writer.println(message);

                if ("exit".equals(message)) {
                    System.exit(0);
                }
            }
            writer.close();
        }
    }
}
