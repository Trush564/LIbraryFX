package org.example.libraryfx.database;

import java.sql.Connection;
import java.sql.ResultSet;
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

            // Початкові книги, щоб пошук і каталог мали дані
            // Якщо немає книги з назвою "Книга1" — додаємо 10 тестових книг Книга1..Книга10.
            try (ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM books WHERE title = 'Книга1'")) {
                if (rs.next() && rs.getInt(1) == 0) {
                    stmt.executeUpdate("""
                        INSERT INTO books (title, author, genre, year, isbn, available, quantity, cover_image_path) VALUES
                        ('Книга1', 'Автор1', 'Рекомендовані', 2020, 'ISBN-0001', TRUE, 5, '/org/example/libraryfx/images/book.jpg'),
                        ('Книга2', 'Автор2', 'Рекомендовані', 2021, 'ISBN-0002', TRUE, 3, '/org/example/libraryfx/images/book.jpg'),
                        ('Книга3', 'Автор3', 'Рекомендовані', 2019, 'ISBN-0003', TRUE, 4, '/org/example/libraryfx/images/book.jpg'),
                        ('Книга4', 'Автор4', 'Наукова література', 2018, 'ISBN-0004', TRUE, 2, '/org/example/libraryfx/images/book.jpg'),
                        ('Книга5', 'Автор5', 'Наукова література', 2017, 'ISBN-0005', TRUE, 6, '/org/example/libraryfx/images/book.jpg'),
                        ('Книга6', 'Автор6', 'Фантастика', 2016, 'ISBN-0006', TRUE, 7, '/org/example/libraryfx/images/book.jpg'),
                        ('Книга7', 'Автор7', 'Фантастика', 2015, 'ISBN-0007', TRUE, 1, '/org/example/libraryfx/images/book.jpg'),
                        ('Книга8', 'Автор8', 'ТОП-100', 2014, 'ISBN-0008', TRUE, 9, '/org/example/libraryfx/images/book.jpg'),
                        ('Книга9', 'Автор9', 'ТОП-100', 2013, 'ISBN-0009', TRUE, 2, '/org/example/libraryfx/images/book.jpg'),
                        ('Книга10', 'Автор10', 'ТОП-100', 2012, 'ISBN-0010', TRUE, 8, '/org/example/libraryfx/images/book.jpg')
                    """);
                    System.out.println("✅ Додано тестові книги (Книга1..Книга10) у таблицю books.");
                }
            }

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

