package com.capgemini.service;

import java.util.List;

import com.capgemini.pojo.Book;

public interface IBookService {

	public void createBookTable();

	public boolean insertBook(Book book);

	public boolean bookDelete(Book book);

	boolean updateBook(Book book);

	public Book findBookById(int bookId);

	public List<Book> getAllBooks();

	public int countBookByPrice(double price);

	public String findBookNameById(int bookId);

	public void callBookDetailsProcedure(int bookId);
	
}
