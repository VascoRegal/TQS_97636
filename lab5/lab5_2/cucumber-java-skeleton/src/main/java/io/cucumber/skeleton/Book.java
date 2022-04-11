package io.cucumber.skeleton;

import java.util.Date;
 
public class Book {
	private String title;
	private String author;
	private Date published;
	private String category;
 
	// constructors, getter, setter ommitted
	public Book(String title, String author, Date pub, String category) {
		this.title = title;
		this.author = author;
		this.published = pub;
		this.category = category;
	}

	public void setTitle(String t) {
		this.title = t;
	}

	public String getTitle() {
		return this.title;
	}

	public void setAuthor(String a) {
		this.author = a;
	}	

	public String getAuthor() {
		return this.author;
	}

	public void setPublished(Date d) {
		this.published = d;
	}

	public Date getPublished() {
		return this.published;
	}


	public void setCategory(String c) {
		this.category = c;
	}

	public String getCategory() {
		return this.category;
	}
    
}