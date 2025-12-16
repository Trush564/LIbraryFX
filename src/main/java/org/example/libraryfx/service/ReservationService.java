package org.example.libraryfx.service;

import org.example.libraryfx.database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReservationService {
    public static boolean createReservation(int userId, int bookId, String reservationDate, boolean forGroup, String groupName) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String insert = "INSERT INTO reservations (user_id, book_id, reserved_for_group, group_name, reservation_date) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(insert);
            stmt.setInt(1, userId);
            stmt.setInt(2, bookId);
            stmt.setBoolean(3, forGroup);
            stmt.setString(4, groupName);
            stmt.setDate(5, Date.valueOf(reservationDate));
            stmt.executeUpdate();

            // Зменшити кількість
            String updateQty = "UPDATE books SET quantity = quantity - 1 WHERE id = ?";
            PreparedStatement qtyStmt = conn.prepareStatement(updateQty);
            qtyStmt.setInt(1, bookId);
            qtyStmt.executeUpdate();

            // Перевірити чи quantity = 0
            String checkQty = "SELECT quantity FROM books WHERE id = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkQty);
            checkStmt.setInt(1, bookId);
            ResultSet checkRs = checkStmt.executeQuery();
            if (checkRs.next() && checkRs.getInt("quantity") <= 0) {
                String makeUnavailable = "UPDATE books SET available = FALSE WHERE id = ?";
                PreparedStatement unavailableStmt = conn.prepareStatement(makeUnavailable);
                unavailableStmt.setInt(1, bookId);
                unavailableStmt.executeUpdate();
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<Map<String, Object>> getUserReservations(int userId) {
        List<Map<String, Object>> reservations = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = """
                SELECT r.id, r.reservation_status, b.title, b.genre, b.author, r.reservation_date, 
                       r.pickup_start_date, r.pickup_end_date, r.reserved_for_group, r.group_name
                FROM reservations r
                JOIN books b ON r.book_id = b.id
                WHERE r.user_id = ?
            """;
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Map<String, Object> reservation = new HashMap<>();
                reservation.put("id", rs.getInt("id"));
                reservation.put("title", rs.getString("title"));
                reservation.put("genre", rs.getString("genre"));
                reservation.put("author", rs.getString("author"));
                reservation.put("status", rs.getString("reservation_status"));
                reservation.put("reservationDate", rs.getDate("reservation_date"));
                reservation.put("pickupStartDate", rs.getDate("pickup_start_date"));
                reservation.put("pickupEndDate", rs.getDate("pickup_end_date"));
                reservation.put("forGroup", rs.getBoolean("reserved_for_group"));
                reservation.put("groupName", rs.getString("group_name"));
                reservations.add(reservation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }
}

