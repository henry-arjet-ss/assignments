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
	public int create(BookGenre bookGenre) throws SQLException, ClassNotFoundException {
		return save("INSERT INTO tbl_book_genres VALUES (?, ?)", new Object[] {bookGenre.getBookID(), bookGenre.getGenreID()});
	}
	public void update(BookGenre bookGenre) throws SQLException, ClassNotFoundException {
		save("UPDATE tbl_book_genres SET genre_id = ? WHERE bookId = ?", new Object[] {bookGenre.getGenreID(), bookGenre.getBookID()});
	}
	public void delete(BookGenre input) throws ClassNotFoundException, SQLException {
		save("DELETE FROM tbl_book_genres WHERE bookId = ?", new Object[] {input.getGenreID()});		
	}
	public List<BookGenre> read() throws ClassNotFoundException, SQLException {
		return pull("SELECT * FROM tbl_book_genres", null);
		
	}
	
	public List<BookGenre> readBookGenreByID(int id) throws ClassNotFoundException, SQLException{
		List<BookGenre> ret = pull("SELECT * FROM tbl_book_genres WHERE bookId = ?", new Object[] {id});
		if (ret.size() == 0) { //couldn't find a match
			return null;
		}
		return ret;
	}
	
	public void deleteByBook(int id) throws ClassNotFoundException, SQLException{
		save("DELETE FROM tbl_book_genres WHERE bookId = ?", new Object[] {id});
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
