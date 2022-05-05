package ua.tqs.demo.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="movies")
public class Movie {

    @Id
    @GeneratedValue 
    private Long id;

    @Column(length = 200)
    private String title;

    @Column(length = 200)
    private String director;

    @Column
    private int year;

    public Movie() {}

    public Movie(Long id, String title, String director, int year, String genre, float rating) {
        this.id = id;
        this.title = title;
        this.director = director;
        this.year = year;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return this.director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getYear() {
        return this.year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String toString() {
        return String.format("<MOVIE>[title=%s director=%s, year=%d]", this.title, this.director, this.year);
    }
}
