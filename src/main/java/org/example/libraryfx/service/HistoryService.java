package org.example.libraryfx.service;

import org.example.libraryfx.database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistoryService {
    public static List<Map<String, Object>> getUserHistory(int userId) {
        List<Map<String, Object>> history = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = """
                SELECT bb.id, bb.title, bb.author, bb.year, bb.isbn, bb.issue_date, bb.return_date, 
                       bb.issue_condition, bb.return_condition, bb.quantity, b.genre, b.cover_image_path
                FROM borrowed_books bb
                JOIN books b ON bb.book_id = b.id
                WHERE bb.user_id = ?
                ORDER BY bb.issue_date DESC
            """;
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Map<String, Object> record = new HashMap<>();
                record.put("id", rs.getInt("id"));
                record.put("title", rs.getString("title"));
                record.put("author", rs.getString("author"));
                record.put("genre", rs.getString("genre"));
                record.put("year", rs.getInt("year"));
                record.put("isbn", rs.getString("isbn"));
                record.put("issueDate", rs.getDate("issue_date"));
                record.put("returnDate", rs.getDate("return_date"));
                record.put("issueCondition", rs.getString("issue_condition"));
                record.put("returnCondition", rs.getString("return_condition"));
                record.put("quantity", rs.getInt("quantity"));
                record.put("coverImagePath", rs.getString("cover_image_path"));
                history.add(record);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return history;
    }
}

