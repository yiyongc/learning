package com.capgemini.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.capgemini.pojo.Book;
import com.capgemini.util.BookRowMapper;

@Repository("bookDao")
public class BookDaoImpl implements IBookDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private SimpleJdbcCall jdbcCall;
	
	@Override
	public void createBookTable() {
		String query = "CREATE TABLE books("
						+ "bookid int PRIMARY KEY AUTO_INCREMENT,"
						+ "bookname varchar(25) NOT NULL,"
						+ "author varchar(25),"
						+ "publisher varchar(25),"
						+ "price numeric(8,2),"
						+ "quantity int)";
		
		jdbcTemplate.execute(query);
		System.out.println("Table created.");
	}

	@Override
	public boolean insertBook(Book book) {
		
		boolean result = false;
		
		String query = "INSERT into books(bookname, author, publisher, price, quantity) "
						+ "VALUES(?,?,?,?,?)";
		
		int count = jdbcTemplate.update(query, book.getBookName(), book.getAuthor(), book.getPublisher(), book.getPrice(), book.getQuantity());
		
		if (count > 0)
			result = true;
		
		return result;
	}

	@Override
	public boolean bookDelete(Book book) {
		boolean result = false;
		
		String query = "DELETE FROM books WHERE bookid = ?";
		
		int count = jdbcTemplate.update(query, book.getBookId());
		
		if (count > 0)
			result = true;
		
		return result;
	}

	@Override
	public boolean updateBook(Book book) {
		boolean result = false;
		
		String query = "UPDATE books SET bookname = ?, author = ?, publisher = ?, price = ?, quantity = ? "
				     	+ "WHERE bookid = ?";
		
		int count = jdbcTemplate.update(query, book.getBookName(), book.getAuthor(), book.getPublisher(), book.getPrice(), book.getQuantity(), book.getBookId());
		
		if (count > 0)
			result = true;
		
		return result;
	}
	
	@Override
	public Book findByBookId(int bookId) {
		
		String query = "SELECT * FROM books WHERE bookid = ?";
		
		return jdbcTemplate.queryForObject(query, new BookRowMapper(), bookId);
		
	}
	
	@Override
	public List<Book> getAllBooks() {
		
		String query = "SELECT * FROM books";
		
		return jdbcTemplate.query(query, new BookRowMapper());

	}
	
	
	@Override
	public int countBookByPrice(double price) {
		
		String query = "SELECT COUNT(*) FROM books WHERE price >= ?";
		
		return jdbcTemplate.queryForObject(query, Integer.class, price);
		
	}
	
	@Override
	public String findBookNameById(int bookId) {
		
		String query = "SELECT bookname FROM books WHERE bookid = ?";
		
		return jdbcTemplate.queryForObject(query, String.class, bookId);
	}
	
	@Override
	public void callBookDetailsProcedure(int bookId) {
		//Calling storedProcedure by using jdbcCall
		jdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName("findBookDetails");
		
		//Prepare input parameters
		MapSqlParameterSource maps = new MapSqlParameterSource();
		
		maps.addValue("bkId", bookId);
		
		//Execute stored procedure
		Map<String, Object> output = jdbcCall.execute(maps);

		String bookName = (String) output.get("bkname");
		Double bookPrice = Double.parseDouble(output.get("bkprice").toString());

		System.out.println("Name: " + bookName + " Price: S$" + bookPrice);
	}

}
