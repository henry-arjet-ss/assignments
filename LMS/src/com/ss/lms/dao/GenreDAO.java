 package com.ss.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.lms.domains.Genre;

public class GenreDAO extends BaseDAO<Genre> {

	public GenreDAO(Connection connIn) {
		super(connIn);
	}
	public void addGenre(Genre genre) throws SQLException, ClassNotFoundException {
		save("INSERT INTO tbl_genre VALUES (0, ?)", new Object[] {genre.getName()});
	}
	public void updateGenre(Genre genre) throws SQLException, ClassNotFoundException {
		save("UPDATE tbl_genre SET genre_name = ? WHERE genre_id = ?", new Object[] {genre.getName(), genre.getId()});
	}
	public void deleteGenre(int id) throws ClassNotFoundException, SQLException {
		save("DELETE FROM tbl_genre WHERE genre_id = ?", new Object[] {id});		
	}
	public List<Genre> readAllGenres() throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM tbl_genre", null);
		
	}
	
	public Genre readGenreByID(int id) throws ClassNotFoundException, SQLException{
		List<Genre> ret = read("SELECT * FROM tbl_genre WHERE genre_id = ?", new Object[] {id});
		if (ret.size() == 0) { //couldn't find a match
			return new Genre(0, "NOT FOUND");
		}
		return ret.get(0);
	}
	public Genre readGenreByName(String name) throws ClassNotFoundException, SQLException{
		List<Genre> ret = read("SELECT * FROM tbl_genre WHERE genre_name = ?", new Object[] {name});
		if (ret.size() == 0) { //couldn't find a match
			return new Genre(0, "NOT FOUND");
		}
		return ret.get(0);
	}
	
	//required for BaseDAO read method
	public List<Genre> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
		List<Genre> genres = new ArrayList<>();
		while (rs.next()) {
			Genre genre = new Genre();
			genre.setId(rs.getInt("genre_id"));
			genre.setName(rs.getString("genre_name"));
			genres.add(genre);
			
		}
		return genres;
	}

}
