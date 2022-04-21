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
import java.util.Optional;
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
        long id = 1L;
        User Smago = new User(1L, "Smago", "smago", new ArrayList<>(), new ArrayList<>());
        User Tom = new User(2L, "Tom", "tom", new ArrayList<>(), new ArrayList<>());
        User Nick = new User(3L, "Nick", "nick", new ArrayList<>(), new ArrayList<>());
        Chatroom room = new Chatroom(2L, "Chat2", Tom, new ArrayList<>());
        Message message;

        System.out.println("---UPDATE MESSAGES---");
        try {
            Optional<Message> optMessage = repository.findById(id);

            if (optMessage.isPresent()) {
                message = optMessage.get();
                System.out.println("MESSAGE BEFORE UPDATE:");
                System.out.println(message);
                message.setAuthor(Tom);
                message.setRoom(room);
                message.setText("New message from Tom!");
                message.setLocalDateTime(null);
                System.out.println("MESSAGE AFTER UPDATE:");
                repository.update(message);
                System.out.println(repository.findById(message.getId()).get());
            }
        } catch (NotSavedSubEntityException e) {
            System.out.println(e.getMessage());
        }

        try {
            id = 2L;
            Optional<Message> optMessage = repository.findById(id);
            room = new Chatroom(3L, "Chat3", Nick, new ArrayList<>());

            if (optMessage.isPresent()) {
                message = optMessage.get();
                System.out.println("MESSAGE BEFORE UPDATE:");
                System.out.println(message);
                message.setAuthor(Nick);
                message.setRoom(room);
                message.setText("Nick is a developer))");
                message.setLocalDateTime(LocalDateTime.now().minusDays(10));
                System.out.println("MESSAGE AFTER UPDATE:");
                repository.update(message);
                System.out.println(repository.findById(message.getId()).get());
            }
        } catch (NotSavedSubEntityException e) {
            System.out.println(e.getMessage());
        }

        try {
            id = 4L;
            Optional<Message> optMessage = repository.findById(id);

            if (optMessage.isPresent()) {
                message = optMessage.get();
                System.out.println("MESSAGE BEFORE UPDATE:");
                System.out.println(message);
                message.setAuthor(Smago);
                message.setRoom(room);
                message.setText("Hello World!!! What's Up))");
                message.setLocalDateTime(LocalDateTime.now().minusDays(100));
                System.out.println("MESSAGE AFTER UPDATE:");
                repository.update(message);
                System.out.println(repository.findById(message.getId()).get());
            }
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