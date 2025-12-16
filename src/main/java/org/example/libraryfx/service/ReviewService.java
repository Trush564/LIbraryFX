package org.example.libraryfx.service;

import org.example.libraryfx.database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReviewService {
    public static boolean addReview(String userLogin, String bookTitle, int rating, String comment) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String insert = "INSERT INTO reviews (user_login, book_title, rating, comment) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(insert);
            ps.setString(1, userLogin);
            ps.setString(2, bookTitle);
            ps.setInt(3, rating);
            ps.setString(4, comment);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<Map<String, Object>> getReviews(String bookTitle) {
        List<Map<String, Object>> reviews = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM reviews WHERE book_title = ? ORDER BY review_date DESC";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, bookTitle);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, Object> review = new HashMap<>();
                review.put("userLogin", rs.getString("user_login"));
                review.put("bookTitle", rs.getString("book_title"));
                review.put("rating", rs.getInt("rating"));
                review.put("comment", rs.getString("comment"));
                review.put("reviewDate", rs.getTimestamp("review_date"));
                reviews.add(review);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    public static List<Map<String, Object>> getAllReviews() {
        List<Map<String, Object>> reviews = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM reviews ORDER BY review_date DESC";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Map<String, Object> review = new HashMap<>();
                review.put("userLogin", rs.getString("user_login"));
                review.put("bookTitle", rs.getString("book_title"));
                review.put("rating", rs.getInt("rating"));
                review.put("comment", rs.getString("comment"));
                review.put("reviewDate", rs.getTimestamp("review_date"));
                reviews.add(review);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }
}

