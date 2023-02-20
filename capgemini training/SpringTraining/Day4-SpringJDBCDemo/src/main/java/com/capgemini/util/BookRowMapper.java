package com.capgemini.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.capgemini.pojo.Book;

public class BookRowMapper implements RowMapper<Book> {

	@Override
	public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
		Book book = new Book();
		
		book.setBookId(rs.getInt("bookid"));
		book.setBookName(rs.getString("bookname"));
		book.setAuthor(rs.getString("author"));
		book.setPublisher(rs.getString("publisher"));
		book.setPrice(rs.getDouble("price"));
		book.setQuantity(rs.getInt("quantity"));
		
		return book;
	}

}
