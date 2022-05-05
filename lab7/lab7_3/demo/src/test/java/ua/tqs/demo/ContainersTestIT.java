package ua.tqs.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import ua.tqs.demo.models.Movie;
import ua.tqs.demo.repos.MovieRepository;


@Testcontainers
@SpringBootTest
public class ContainersTestIT {
    
    @Autowired
    private MovieRepository movieRepository;

    @Container
    public static PostgreSQLContainer container = new PostgreSQLContainer<>("postgres:latest")
        .withUsername("root")
        .withPassword("root")
        .withDatabaseName("test");


    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.username", container::getUsername);
    }

    @Test
    @Order(1)
    void insert() {
        Movie md = new Movie();
        md.setTitle("Mulholland Drive");
        md.setDirector("David Lynch");
        md.setYear(2001);

        movieRepository.save(md);

        assertEquals(md.getTitle(), movieRepository.findByTtile(md.getTitle()).getTitle());
    }

    @Test
    @Order(2)
    void fetchAll() {
        assertEquals(2, movieRepository.findAll().size());
    }
}
