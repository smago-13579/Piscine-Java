package school21.spring.service.application;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import school21.spring.service.models.User;
import school21.spring.service.repositories.UsersRepository;
import school21.spring.service.repositories.UsersRepositoryJdbcTemplateImpl;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        createTable();
        UsersRepository usersRepositoryJdbc = context.getBean("usersRepositoryJdbc", UsersRepository.class);
        UsersRepository usersRepositoryJdbcTemplate = context.getBean("usersRepositoryJdbcTemplate", UsersRepository.class);
        System.out.println("---FIND ALL USERS---");
        System.out.println(usersRepositoryJdbc.findAll());
        System.out.println(usersRepositoryJdbcTemplate.findAll());
    }

    private static void createTable() {
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        DataSource dataSource = context.getBean("hikariDataSource", HikariDataSource.class);
        UsersRepository usersRepository = context.getBean("usersRepositoryJdbc", UsersRepository.class);
        try (Connection con = dataSource.getConnection();
             Statement st = con.createStatement()) {
            st.executeUpdate("drop schema if exists models cascade;");
            st.executeUpdate("create schema if not exists models;");
            st.executeUpdate("create table if not exists models.user "
                    + "(id serial primary key, email varchar(50) not null);");
            System.out.println("---CREATE NEW USERS---");
            User user = new User("smago@school21.ru");
            usersRepository.save(user);
            System.out.println(user);
            user = new User("bbelen@school21.ru");
            usersRepository.save(user);
            System.out.println(user);
            user = new User("ngonzo@school21.ru");
            usersRepository.save(user);
            System.out.println(user);
            user = new User("1234@school21.ru");
            usersRepository.save(user);
            System.out.println(user);
            user = new User("56789school21.ru");
            usersRepository.save(user);
            System.out.println(user);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
