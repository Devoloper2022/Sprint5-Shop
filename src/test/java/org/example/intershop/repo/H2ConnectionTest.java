//package org.example.intershop.repo;
//
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//
//import java.sql.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//
//public class H2ConnectionTest {
//
//
//    static Connection connection;
//
//    @BeforeAll
//    static void setupConnection() throws SQLException {
//        connection = DriverManager.getConnection("jdbc:h2:mem:testdb");
//
//        try (Statement st = connection.createStatement()) {
//            st.execute("create table users(id bigint primary key, name varchar(256) not null)");
//            st.execute("insert into users(id, name) values (1, 'Ivan')");
//        }
//    }
//
//    @AfterAll
//    static void closeConnection() throws SQLException {
//        if (connection != null) {
//            connection.close();
//        }
//    }
//
//    @Test
//    void testUserExists() throws SQLException {
//        try (Statement st = connection.createStatement()) {
//            ResultSet rs = st.executeQuery("select name from users where id = 1");
//
//            assertTrue(rs.next());
//            assertEquals("Ivan", rs.getString("name"));
//
//            assertFalse(rs.next()); // Только одна запись
//        }
//    }
//}
