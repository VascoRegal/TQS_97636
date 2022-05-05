package ua.tqs.demo.repos;

import org.springframework.stereotype.Repository;

import ua.tqs.demo.models.Movie;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    Movie findByTtile(String title);
}
