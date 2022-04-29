package school21.spring.service.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import school21.spring.service.models.User;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcTemplateImpl implements UsersRepository {
    private DataSource dataSource;
    private NamedParameterJdbcTemplate jdbcTemplate;
    private final String idQuery = "SELECT * FROM models.user WHERE id = :id";
    private final String emQuery = "SELECT * FROM models.user WHERE email = :email";
    private final String alQuery = "SELECT * FROM models.user";
    private final String upQuery = "UPDATE models.user SET email = :email WHERE id = :id";
    private final String dlQuery = "DELETE FROM models.user WHERE id = :id";
    private final String inQuery = "INSERT INTO models.user (email) VALUES (:email) RETURNING id";

    private class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int i) throws SQLException {
            User user = new User();

            user.setIdentifier(rs.getLong("id"));
            user.setEmail(rs.getString("email"));
            return user;
        }
    }

    public UsersRepositoryJdbcTemplateImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public NamedParameterJdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    @Override
    public User findById(Long id) {
        User user = jdbcTemplate.queryForObject(idQuery,
                new MapSqlParameterSource().addValue("id", id),
                new UserRowMapper());

        return user;
    }

    @Override
    public List<User> findAll() {
        List<User> users = jdbcTemplate.query(alQuery, new UserRowMapper());
        return users;
    }

    @Override
    public void save(User entity) {
        Integer id = jdbcTemplate.update(inQuery, new MapSqlParameterSource()
                .addValue("email", entity.getEmail()));
        entity.setIdentifier(id.longValue());
    }

    @Override
    public void update(User entity) {
        int i = jdbcTemplate.update(upQuery, new MapSqlParameterSource()
                .addValue("id", entity.getIdentifier())
                .addValue("email", entity.getEmail()));

        if (i == 0) {
            System.err.println("INTERNAL ERROR: User wasn't updated");
        }
    }

    @Override
    public void delete(Long id) {
        int i = jdbcTemplate.update(dlQuery, new MapSqlParameterSource()
                .addValue("id", id));

        if (i == 0) {
            System.err.println("INTERNAL ERROR: User wasn't deleted");
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        User user = jdbcTemplate.queryForObject(emQuery,
                new MapSqlParameterSource().addValue("email", email),
                new UserRowMapper());
        return Optional.ofNullable(user);
    }
}
