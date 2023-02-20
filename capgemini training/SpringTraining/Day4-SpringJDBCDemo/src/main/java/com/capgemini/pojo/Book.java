package com.capgemini.pojo;

public class Book {
	
	private int bookId;
	private String bookName;
	private String author;
	private String publisher;
	private double price;
	private int quantity;
	
	public Book() {}

	public Book(int bookId, String bookName, String author, String publisher, double price, int quantity) {
		this.bookId = bookId;
		this.bookName = bookName;
		this.author = author;
		this.publisher = publisher;
		this.price = price;
		this.quantity = quantity;
	}

	public Book(String bookName, String author, String publisher, double price, int quantity) {
		this.bookName = bookName;
		this.author = author;
		this.publisher = publisher;
		this.price = price;
		this.quantity = quantity;
	}
	
	@Override
	public String toString() {
		return "Book [bookId=" + bookId + ", bookName=" + bookName + ", author=" + author + ", publisher=" + publisher
				+ ", price=" + price + ", quantity=" + quantity + "]";
	}

	public int getBookId() {
		return bookId;
	}
	
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	
	public String getBookName() {
		return bookName;
	}
	
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public String getPublisher() {
		return publisher;
	}
	
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
	
}
