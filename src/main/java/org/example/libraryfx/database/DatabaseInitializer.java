package org.example.libraryfx.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {
    public static void initialize() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            // Таблиця користувачів
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS users (
                    id SERIAL PRIMARY KEY,
                    first_name VARCHAR(100),
                    last_name VARCHAR(100),
                    middle_name VARCHAR(100),
                    phone VARCHAR(20),
                    email VARCHAR(100),
                    login VARCHAR(50) UNIQUE,
                    password VARCHAR(255),
                    role VARCHAR(50),
                    faculty VARCHAR(100),
                    group_name VARCHAR(50),
                    course INTEGER,
                    teacher_code VARCHAR(50),
                    librarian_code VARCHAR(50)
                )
            """);

            // Таблиця книг
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS books (
                    id SERIAL PRIMARY KEY,
                    title VARCHAR(255),
                    author VARCHAR(255),
                    genre VARCHAR(100),
                    year INTEGER,
                    isbn VARCHAR(50),
                    available BOOLEAN DEFAULT TRUE,
                    quantity INTEGER DEFAULT 1,
                    cover_image_path VARCHAR(500)
                )
            """);

            // Таблиця бронювань
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS reservations (
                    id SERIAL PRIMARY KEY,
                    user_id INTEGER,
                    book_id INTEGER,
                    reservation_date DATE,
                    reserved_for_group BOOLEAN DEFAULT FALSE,
                    group_name VARCHAR(100),
                    reservation_status VARCHAR(20) DEFAULT 'PENDING',
                    pickup_start_date DATE,
                    pickup_end_date DATE,
                    FOREIGN KEY (user_id) REFERENCES users(id),
                    FOREIGN KEY (book_id) REFERENCES books(id)
                )
            """);

            // Таблиця виданих книг
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS borrowed_books (
                    id SERIAL PRIMARY KEY,
                    user_id INTEGER,
                    book_id INTEGER,
                    title VARCHAR(255),
                    author VARCHAR(255),
                    year INTEGER,
                    isbn VARCHAR(50),
                    issue_date DATE,
                    return_date DATE,
                    issue_condition VARCHAR(255),
                    return_condition VARCHAR(255),
                    quantity INTEGER,
                    FOREIGN KEY (user_id) REFERENCES users(id),
                    FOREIGN KEY (book_id) REFERENCES books(id)
                )
            """);

            // Таблиця відгуків
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS reviews (
                    id SERIAL PRIMARY KEY,
                    user_login VARCHAR(50),
                    book_title VARCHAR(255),
                    rating INTEGER,
                    comment TEXT,
                    review_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                )
            """);

            // Таблиця поданих (власних) книг
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS published_books (
                    id SERIAL PRIMARY KEY,
                    user_id INTEGER,
                    title VARCHAR(255),
                    author VARCHAR(255),
                    genre VARCHAR(100),
                    year INTEGER,
                    link TEXT,
                    status VARCHAR(20) DEFAULT 'PENDING',
                    submission_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    FOREIGN KEY (user_id) REFERENCES users(id)
                )
            """);

            System.out.println("✅ Усі таблиці перевірено або створено.");
        } catch (SQLException e) {
            System.err.println("❌ Помилка при створенні таблиць: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

