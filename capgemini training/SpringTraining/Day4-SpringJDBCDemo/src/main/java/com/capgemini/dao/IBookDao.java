package com.capgemini.dao;

import java.util.List;

import com.capgemini.pojo.Book;

public interface IBookDao {
	
	public void createBookTable();
	
	public boolean insertBook(Book book);
	
	public boolean bookDelete(Book book);

	public boolean updateBook(Book book);

	public Book findByBookId(int bookId);

	public List<Book> getAllBooks();

	public int countBookByPrice(double price);

	public String findBookNameById(int bookId);

	public void callBookDetailsProcedure(int bookId);
	
}
