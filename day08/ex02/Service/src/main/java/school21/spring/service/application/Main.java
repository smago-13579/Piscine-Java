package school21.spring.service.application;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import school21.spring.service.config.ApplicationConfig;
import school21.spring.service.models.User;
import school21.spring.service.repositories.UsersRepository;
import school21.spring.service.repositories.UsersRepositoryJdbcTemplateImpl;
import school21.spring.service.services.UsersService;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext("school21.spring.service");
        UsersService usersService = context.getBean("usersService", UsersService.class);
        UsersRepository usersJdbcTemplate = context.getBean("jdbcTemplateRepository", UsersRepository.class);
        UsersRepository usersJdbc = context.getBean("jdbcRepository", UsersRepository.class);
        createTable(context);

        System.out.println("\n---INSERT NEW USERS---");
        System.out.println(usersService.signUp("smago@school21.ru"));
        System.out.println(usersService.signUp("bbelen@school21.ru"));
        System.out.println(usersService.signUp("ngonzo@school21.ru"));
        System.out.println(usersService.signUp("01234@school21.ru"));
        System.out.println(usersService.signUp("56789@school21.ru"));

        System.out.println("\n---FIND ALL, BY_ID, BY_EMAIL---");
        System.out.println(usersJdbcTemplate.findAll());
        System.out.println(usersJdbc.findAll());
        System.out.println(usersJdbcTemplate.findById(1L));
        System.out.println(usersJdbc.findById(1L));
        System.out.println(usersJdbcTemplate.findByEmail("01234@school21.ru"));
        System.out.println(usersJdbc.findByEmail("01234@school21.ru"));
    }

    private static void createTable(ApplicationContext context) {
        DataSource dataSource = context.getBean("hikariDataSource", HikariDataSource.class);
        try (Connection con = dataSource.getConnection();
             Statement st = con.createStatement()) {
            st.executeUpdate("drop schema if exists models cascade;");
            st.executeUpdate("create schema if not exists models;");
            st.executeUpdate("create table if not exists models.user "
                    + "(id integer, email varchar(50) not null, password varchar(50));");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
