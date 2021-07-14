 package com.ss.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.lms.domains.BookGenre;

public class BookGenreDAO extends BaseDAO<BookGenre> {

	public BookGenreDAO(Connection connIn) {
		super(connIn);
	}
	public void addBookGenre(BookGenre bookGenre) throws SQLException, ClassNotFoundException {
		save("INSERT INTO tbl_book_genres VALUES (?, ?)", new Object[] {bookGenre.getBookID(), bookGenre.getGenreID()});
	}
	public void updateBookGenre(BookGenre bookGenre) throws SQLException, ClassNotFoundException {
		save("UPDATE tbl_book_genres SET genre_id = ? WHERE bookId = ?", new Object[] {bookGenre.getGenreID(), bookGenre.getBookID()});
	}
	public void deleteBookGenre(int id) throws ClassNotFoundException, SQLException {
		save("DELETE FROM tbl_book_genres WHERE bookId = ?", new Object[] {id});		
	}
	public List<BookGenre> readAllBookGenres() throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM tbl_book_genres", null);
		
	}
	
	public BookGenre readBookGenreByID(int id) throws ClassNotFoundException, SQLException{
		List<BookGenre> ret = read("SELECT * FROM tbl_book_genres WHERE bookId = ?", new Object[] {id});
		if (ret.size() == 0) { //couldn't find a match
			return new BookGenre(0, 0);
		}
		return ret.get(0);
	}
	
	//required for BaseDAO read method
	public List<BookGenre> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
		List<BookGenre> bookGenres = new ArrayList<>();
		while (rs.next()) {
			BookGenre bookGenre = new BookGenre();
			bookGenre.setBookID(rs.getInt("bookId"));
			bookGenre.setGenreID(rs.getInt("genre_id"));
			bookGenres.add(bookGenre);
			
		}
		return bookGenres;
	}

}
