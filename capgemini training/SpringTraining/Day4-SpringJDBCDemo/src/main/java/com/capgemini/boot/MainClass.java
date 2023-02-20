package com.capgemini.boot;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.capgemini.config.JavaConfig;
import com.capgemini.pojo.Book;
import com.capgemini.service.IBookService;

public class MainClass {

	
	public static void main(String[] args) {
		
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(JavaConfig.class);
		
		IBookService bookService = (IBookService) context.getBean("bookService");
		
//		Book book = new Book("Lord of The Rings", "Gandalf", "Mordor", 30.50, 1);
//		Book book2 = new Book("The Town", "The best", "publisher 101", 20, 3);
//		Book book3 = new Book("Harry Potter", "JK Rowling", "JK Rowling", 50, 1);
//		Book book4 = new Book("Java", "Kathy Sierra", "Kathy Sierra", 35, 1);
//		Book bookToDelete = new Book();
//		bookToDelete.setBookId(1);
//		Book bookToUpdate = new Book();
//		bookToUpdate.setBookId(2);
//		bookToUpdate.setBookName("My new Book");
//		bookToUpdate.setPublisher("My new publisher");
//		bookToUpdate.setAuthor("My new Author");
//		bookToUpdate.setPrice(50);
//		bookToUpdate.setQuantity(5);
		
//		System.out.println(bookService.insertBook(book));
//		System.out.println(bookService.insertBook(book2));
//		System.out.println(bookService.insertBook(book3));
//		System.out.println(bookService.inzsertBook(book4));
//		System.out.println(bookService.bookDelete(bookToDelete));
//		System.out.println(bookService.updateBook(bookToUpdate));
//		Book bookFound = bookService.findBookById(4);
//		
//		System.out.println(bookFound);
		
//		System.out.println(bookService.countBookByPrice(25));
//		System.out.println(bookService.countBookByPrice(50));
//		
		for (Book book : bookService.getAllBooks()) {
			System.out.println(book);
		}
//		
//		System.out.println(bookService.findBookNameById(3));
//		
		
		bookService.callBookDetailsProcedure(2);
		
		context.close();
	}
}
