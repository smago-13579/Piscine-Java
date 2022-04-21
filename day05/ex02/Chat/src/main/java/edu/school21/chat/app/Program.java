package edu.school21.chat.app;

import edu.school21.chat.exceptions.NotSavedSubEntityException;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;
import edu.school21.chat.repositories.JdbcDataSource;
import edu.school21.chat.repositories.MessagesRepository;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;

import java.io.InputStream;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Program {
    public static void main(String[] args)  {
        JdbcDataSource dataSource = new JdbcDataSource();
        updateData("schema.sql", dataSource);
        updateData("data.sql", dataSource);
        MessagesRepository repository = new MessagesRepositoryJdbcImpl(dataSource.getDataSource());
        createNewMessage(repository);
    }

    private static void createNewMessage(MessagesRepository repository) {
        User creator = new User(1L, "Smago", "smago", new ArrayList<>(), new ArrayList<>());
        User author = creator;
        Chatroom room = new Chatroom(1L, "Chat1", creator, new ArrayList<>());
        Message message = new Message(null, author, room, "Hello from Smago!", LocalDateTime.now());

        System.out.println("---NEW MESSAGE---");
        try {
            repository.save(message);
            System.out.println("New message id = " + message.getId());
        } catch (NotSavedSubEntityException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("---AUTHOR IS EMPTY---");
        try {
            creator = new User(2L, "Tom", "tom", null, null);
            author = null;
            room = new Chatroom(2L, "Chat2", creator, null);
            message = new Message(null, author, room, "Something must happen", LocalDateTime.now());
            repository.save(message);
        } catch (NotSavedSubEntityException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("---CHATROOM DOESN'T EXIST---");
        try {
            creator = new User(2L, "Tom", "tom", null, null);
            author = creator;
            room = new Chatroom(21L, "Chat21", creator, null);
            message = new Message(null, author, room, "No Chatroom, hmm...", LocalDateTime.now());
            repository.save(message);
        } catch (NotSavedSubEntityException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("---AUTHOR DOESN'T EXIST---");
        try {
            creator = new User(22L, "Tom22", "tom22", null, null);
            author = creator;
            room = new Chatroom(2L, "Chat2", creator, null);
            message = new Message(null, author, room, "Unknown author, hmm...", LocalDateTime.now());
            repository.save(message);
        } catch (NotSavedSubEntityException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("---ANOTHER MESSAGE FROM ARTUR---");
        try {
            creator = new User(6L, "Artur", "artur", null, null);
            author = creator;
            room = new Chatroom(6L, "Chat6", creator, null);
            message = new Message(null, author, room, "Hello from Artur!", LocalDateTime.now());
            repository.save(message);
            System.out.println("New message id = " + message.getId());
        } catch (NotSavedSubEntityException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void updateData(String file, JdbcDataSource dataSource) {
        try (Connection con = dataSource.getConnection();
             Statement st = con.createStatement()) {
            InputStream input = Program.class.getClassLoader().getResourceAsStream(file);
            Scanner scanner = new Scanner(input).useDelimiter(";");

            while (scanner.hasNext()) {
                st.executeUpdate(scanner.next().trim());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}