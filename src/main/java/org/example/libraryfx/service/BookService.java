package org.example.libraryfx.service;

import org.example.libraryfx.database.DatabaseConnection;
import org.example.libraryfx.model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookService {
    public static List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM books";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setGenre(rs.getString("genre"));
                book.setYear(rs.getInt("year"));
                book.setIsbn(rs.getString("isbn"));
                book.setAvailable(rs.getBoolean("available"));
                book.setQuantity(rs.getInt("quantity"));
                book.setCoverImagePath(rs.getString("cover_image_path"));
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public static List<Book> searchBooks(String keyword) {
        List<Book> books = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM books WHERE LOWER(title) LIKE ? OR LOWER(author) LIKE ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            String query = "%" + keyword.toLowerCase() + "%";
            ps.setString(1, query);
            ps.setString(2, query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setGenre(rs.getString("genre"));
                book.setYear(rs.getInt("year"));
                book.setIsbn(rs.getString("isbn"));
                book.setAvailable(rs.getBoolean("available"));
                book.setQuantity(rs.getInt("quantity"));
                book.setCoverImagePath(rs.getString("cover_image_path"));
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public static List<Book> getBooksByGenre(String genre) {
        List<Book> books = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM books WHERE genre = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, genre);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setGenre(rs.getString("genre"));
                book.setYear(rs.getInt("year"));
                book.setIsbn(rs.getString("isbn"));
                book.setAvailable(rs.getBoolean("available"));
                book.setQuantity(rs.getInt("quantity"));
                book.setCoverImagePath(rs.getString("cover_image_path"));
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public static boolean addBook(Book book) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO books (title, author, genre, year, isbn, available, quantity, cover_image_path) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getGenre());
            ps.setInt(4, book.getYear());
            ps.setString(5, book.getIsbn());
            ps.setBoolean(6, book.isAvailable());
            ps.setInt(7, book.getQuantity());
            ps.setString(8, book.getCoverImagePath());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

