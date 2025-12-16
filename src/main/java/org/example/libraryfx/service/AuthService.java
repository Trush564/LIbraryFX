package org.example.libraryfx.service;

import org.example.libraryfx.database.DatabaseConnection;
import org.example.libraryfx.model.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class AuthService {
    private static final String ADMIN_LOGIN = "admin";
    private static final String ADMIN_PASSWORD = "admin123";

    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Помилка хешування пароля", e);
        }
    }

    public static User login(String login, String password) {
        if (login.equals(ADMIN_LOGIN) && password.equals(ADMIN_PASSWORD)) {
            // Адміністратор
            User admin = new User();
            admin.setLogin(ADMIN_LOGIN);
            admin.setRole("admin");
            return admin;
        }

        String hashedPassword = hashPassword(password);
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM users WHERE login = ? AND password = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, login);
            pstmt.setString(2, hashedPassword);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setMiddleName(rs.getString("middle_name"));
                user.setPhone(rs.getString("phone"));
                user.setEmail(rs.getString("email"));
                user.setLogin(rs.getString("login"));
                user.setRole(rs.getString("role"));
                user.setCourse(rs.getObject("course") != null ? rs.getInt("course") : null);
                user.setFaculty(rs.getString("faculty"));
                user.setGroupName(rs.getString("group_name"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean register(User user, String password) {
        if (checkLoginExists(user.getLogin())) {
            return false;
        }
        String hashedPassword = hashPassword(password);
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO users (first_name, last_name, middle_name, phone, email, login, password, role, faculty, group_name, course, teacher_code, librarian_code) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, user.getFirstName());
            pstmt.setString(2, user.getLastName());
            pstmt.setString(3, user.getMiddleName());
            pstmt.setString(4, user.getPhone());
            pstmt.setString(5, user.getEmail());
            pstmt.setString(6, user.getLogin());
            pstmt.setString(7, hashedPassword);
            pstmt.setString(8, user.getRole());
            pstmt.setString(9, user.getFaculty());
            pstmt.setString(10, user.getGroupName());
            if (user.getCourse() != null) {
                pstmt.setInt(11, user.getCourse());
            } else {
                pstmt.setNull(11, Types.INTEGER);
            }
            pstmt.setString(12, user.getTeacherCode());
            pstmt.setString(13, user.getLibrarianCode());
            
            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean register(User user, String role, String code) {
        if (checkLoginExists(user.getLogin())) {
            return false;
        }
        user.setRole(role);
        if ("teacher".equals(role)) {
            user.setTeacherCode(code);
        } else if ("librarian".equals(role)) {
            user.setLibrarianCode(code);
        }
        return register(user, user.getPassword());
    }

    public static boolean checkLoginExists(String login) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT COUNT(*) FROM users WHERE login = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, login);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
