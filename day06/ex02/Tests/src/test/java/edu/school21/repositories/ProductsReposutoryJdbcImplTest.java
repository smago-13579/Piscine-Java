package edu.school21.repositories;

import edu.school21.models.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import static org.junit.jupiter.api.Assertions.*;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ProductsReposutoryJdbcImplTest {
    private ProductsRepository repository;
    private DataSource dataSource;

    final List<Product> FIND_ALL = Arrays.asList(
        new Product(1l, "Banana", 99l),
        new Product(2l, "Chia", 250l),
        new Product(3l, "Tea", 80l),
        new Product(4l, "Coffee", 500l),
        new Product(5l, "Yoghurt", 140l),
        new Product(6l, "Dates", 390l));

    final Product FIND_BY_ID = new Product(4l, "Coffee", 500l);
    final Product UPDATED_PRODUCT = new Product(5l, "Cheesecake", 299l);
    final Product SAVE_PRODUCT = new Product(7l, "Orange", 69l);

    final List<Product> PRODUCTS_AFTER_DELETE = Arrays.asList(
            new Product(1l, "Banana", 99l),
            new Product(2l, "Chia", 250l),
            new Product(4l, "Coffee", 500l),
            new Product(5l, "Yoghurt", 140l),
            new Product(6l, "Dates", 390l));

    @BeforeEach
    private void init() {
        dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("schema.sql")
                .addScript("data.sql")
                .build();
        repository = new ProductsRepositoryJdbcImpl(dataSource);
    }

    @Test
    public void findAllTest() {
        List<Product> actual = repository.findAll();
        assertEquals(FIND_ALL, actual);
    }

    @Test
    public void findByIdTest() {
        assertEquals(FIND_BY_ID, repository.findById(4l).get());
        assertEquals(Optional.empty(), repository.findById(10l));
        assertEquals(Optional.empty(), repository.findById(null));
    }

    @Test
    public void updateTest() {
        repository.update(new Product(5l, "Cheesecake", 299l));
        assertEquals(UPDATED_PRODUCT, repository.findById(5l).get());
    }

    @Test
    public void saveTest() {
        repository.save(new Product(7l, "Orange", 69l));
        assertEquals(SAVE_PRODUCT, repository.findById(7l).get());
    }

    @Test
    public void deleteTest() {
        repository.delete(3l);
        assertEquals(PRODUCTS_AFTER_DELETE, repository.findAll());
        assertFalse(repository.findById(3l).isPresent());
    }
}
