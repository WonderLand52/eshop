package com.rudenkoInc.eshop.jdbcDemo;


import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcTest {

    public static void main(String[] args) {
        try {
            Driver driver = DriverManager.getDriver("jdbc:mysql://127.0.0.1:3306/eshop?user=admin&password=******");
            System.out.println("Driver: " + driver);
        }catch (SQLException e){
            System.out.println("Exception: " + e);
        }
    }
}
