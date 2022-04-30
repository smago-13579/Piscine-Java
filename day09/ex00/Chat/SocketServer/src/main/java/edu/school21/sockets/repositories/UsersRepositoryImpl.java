package edu.school21.sockets.repositories;

import edu.school21.sockets.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

@Component
public class UsersRepositoryImpl implements UsersRepository {
    private JdbcTemplate jdbcTemplate;
    private final String idQuery = "SELECT * FROM server.user WHERE id = ?";
    private final String usQuery = "SELECT * FROM server.user WHERE username = ?";
    private final String alQuery = "SELECT * FROM server.user";
    private final String upQuery = "UPDATE server.user SET username = ?, password = ? WHERE id = ?";
    private final String dlQuery = "DELETE FROM server.user WHERE id = ?";
    private final String inQuery = "INSERT INTO server.user (username, password) VALUES (?, ?)";

    @Autowired
    public UsersRepositoryImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        init();
    }

    private void init() {
        jdbcTemplate.execute("CREATE SCHEMA IF NOT EXISTS server;");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS server.user (\n" +
                "id serial primary key,\n" +
                "username varchar(40) not null unique,\n" +
                "password varchar(100) not null);");
    }

    private class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int i) throws SQLException {
            User user = new User();

            user.setId(rs.getLong("id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            return user;
        }
    }

    @Override
    public User findById(Long id) {
        User user = jdbcTemplate.query(idQuery,
                new Object[]{id},
                new int[]{Types.INTEGER},
                new UserRowMapper()).stream().findAny().orElse(null);

        return user;
    }

    @Override
    public List<User> findAll() {
        List<User> users = jdbcTemplate.query(alQuery, new UserRowMapper());
        return users;
    }

    @Override
    public void save(User entity) {
        int i = jdbcTemplate.update(inQuery, entity.getUsername(), entity.getPassword());

        if (i == 0) {
            System.err.println("User wasn't saved: " + entity);
        }
    }

    @Override
    public void update(User entity) {
        int i = jdbcTemplate.update(upQuery, entity.getUsername(),
                entity.getPassword(), entity.getId());

        if (i == 0) {
            System.err.println("User wasn't updated: " + entity);
        }
    }

    @Override
    public void delete(Long id) {
        int i = jdbcTemplate.update(dlQuery, id);

        if (i == 0) {
            System.err.println("User not found with id: " + id);
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        User user = jdbcTemplate.query(usQuery,
                new Object[]{username},
                new int[]{Types.VARCHAR},
                new UserRowMapper()).stream().findAny().orElse(null);
        return Optional.ofNullable(user);
    }
}
