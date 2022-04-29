package school21.spring.service.repositories;

import school21.spring.service.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcImpl implements UsersRepository {
    private DataSource dataSource;
    private final String idQuery = "SELECT * FROM models.user WHERE id = ";
    private final String emQuery = "SELECT * FROM models.user WHERE email = ?";
    private final String alQuery = "SELECT * FROM models.user";
    private final String upQuery = "UPDATE models.user SET email = ? WHERE id = ?";
    private final String dlQuery = "DELETE FROM models.user WHERE id = ";

    public UsersRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public User findById(Long id) {
        try (Connection con = dataSource.getConnection();
             Statement st = con.createStatement()) {
            ResultSet rs = st.executeQuery(idQuery + id);

            if (!rs.next()) {
                return null;
            }
            return new User(rs.getLong(1), rs.getString(2));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();

        try (Connection con = dataSource.getConnection();
             Statement st = con.createStatement()) {
            ResultSet rs = st.executeQuery(alQuery);

            while (rs.next()) {
                users.add(new User(rs.getLong(1), rs.getString(2)));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return users.isEmpty() ? null : users;
    }

    @Override
    public void save(User entity) {
        try (Connection con = dataSource.getConnection();
             Statement st = con.createStatement()) {
            ResultSet rs = st.executeQuery("INSERT INTO models.user (email) VALUES ('"
                    + entity.getEmail() + "') RETURNING id");

            if (!rs.next()) {
                throw new SQLException("INTERNAL ERROR: User wasn't save");
            }
            entity.setIdentifier(rs.getLong(1));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void update(User entity) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement st = con.prepareStatement(upQuery)) {
            st.setString(1, entity.getEmail());
            st.setLong(2, entity.getIdentifier());
            ResultSet rs = st.executeQuery();

            if (!rs.next()) {
                throw new SQLException("INTERNAL ERROR: User wasn't updated");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement st = con.prepareStatement(dlQuery + id)) {
            ResultSet rs = st.executeQuery();

            if (!rs.next()) {
                throw new SQLException("INTERNAL ERROR: User wasn't deleted");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement st = con.prepareStatement(emQuery)) {
            st.setString(1, email);
            ResultSet rs = st.executeQuery();

            if (!rs.next()) {
                return null;
            }
            return Optional.of(new User(rs.getLong(1), rs.getString(2)));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return Optional.empty();
    }
}
