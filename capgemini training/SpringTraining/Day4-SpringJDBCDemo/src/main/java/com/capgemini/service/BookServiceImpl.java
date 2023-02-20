package com.capgemini.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.dao.BookDaoImpl;
import com.capgemini.pojo.Book;

@Service("bookService")
public class BookServiceImpl implements IBookService {

	@Autowired
	private BookDaoImpl bookDao;
	
	@Override
	public void createBookTable() {
		bookDao.createBookTable();
	}

	@Override
	public boolean insertBook(Book book) {
		return bookDao.insertBook(book);
	}
	
	@Override
	public boolean bookDelete(Book book) {
		return bookDao.bookDelete(book);
	}
	
	@Override
	public boolean updateBook(Book book) {
		return bookDao.updateBook(book);
	}
	
	@Override
	public Book findBookById(int bookId) {
		return bookDao.findByBookId(bookId);
	}
	
	@Override
	public List<Book> getAllBooks() {
		return bookDao.getAllBooks();
	}
	
	@Override
	public int countBookByPrice(double price) {
		return bookDao.countBookByPrice(price);
	}
	
	@Override
	public String findBookNameById(int bookId) {
		return bookDao.findBookNameById(bookId);
	}
	
	@Override
	public void callBookDetailsProcedure(int bookId) {
		bookDao.callBookDetailsProcedure(bookId);
	}
}

