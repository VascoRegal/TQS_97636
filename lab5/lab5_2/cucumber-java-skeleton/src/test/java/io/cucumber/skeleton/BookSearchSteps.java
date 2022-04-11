package io.cucumber.skeleton;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class BookSearchSteps {
    Library library = new Library();
	List<Book> result = new ArrayList<>();
 

	@ParameterType("{int}/{int}/{int}")
	public Date iso8601Date(final String year, final String month, final String day){
        DateFormat sourceDateFormat = new SimpleDateFormat("yyyy/mm/dd");
		try {
            return sourceDateFormat.parse(year + "/" + month + "/" + day);
        } catch (ParseException e) {
            return null;
        }
	}

	@Given("a book with the title '{string}', written by '{string}', published in {iso8601Date} with category '{string}'")
	public void addNewBook(final String title, final String author, final Date published, String category) {
		Book book = new Book(title, author, published, category);
		library.addBook(book);
	}
 
	@When("The customer searches for books published between {iso8601Date} and {iso8601Date}")
	public void setSearchParameters(final Date from,  final Date to) {
		result = library.findBooks(from, to);
	}
 
	@Then("{int} books should have been found")
	public void verifyAmountOfBooksFound(final int booksFound) {
        assertTrue(result.size() == booksFound);
	}
 
	@Then("Book {int} should have the title '{string}'")
	public void verifyBookAtPosition(final int position, final String title) {
        assertTrue(result.get(position - 1).getTitle() == title);
	}
}
