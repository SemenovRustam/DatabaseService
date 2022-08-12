package com.semenov.query;

import java.sql.PreparedStatement;

public class Constant {

    public static final String SELECT_BY_LAST_NAME = "SELECT customer.firstname, customer.lastname " +
            "FROM customer" +
            " WHERE lastName = ?";


    public static final String SELECT_PRODUCT_NAME_AND_FIRST_NAME_AND_LAST_NAME = "SELECT customer.firstname, customer.lastname\n" +
            "FROM customer\n" +
            "         JOIN sale ON customer.id = sale.customer_id\n" +
            "         JOIN product ON sale.product_id = product.id\n" +
            "WHERE product.name = ?\n" +
            "GROUP BY customer.firstname, customer.lastname, product.name\n" +
            "HAVING count(product.name) >=?";


    public static final String SELECT_FIRST_NAME_AND_LAST_NAME_BETWEEN = "SELECT customer.firstname, customer.lastname\n" +
            "FROM customer\n" +
            "         JOIN sale ON customer.id = sale.customer_id\n" +
            "         JOIN product ON sale.product_id = product.id\n" +
            "GROUP BY customer.firstname, customer.lastname\n" +
            "HAVING SUM(cost) BETWEEN ? AND ?";


    public static final String QUERY_FOR_BAD_CUSTOMERS = "SELECT customer.firstname, customer.lastname\n" +
            "FROM customer\n" +
            "         JOIN sale ON customer.id = sale.customer_id\n" +
            "         JOIN product ON sale.product_id = product.id\n" +
            "GROUP BY customer.firstname, customer.lastname\n" +
            "ORDER BY  count(product)\n" +
            "LIMIT ?;";

//    public static final String QUERY_FOR_BAD_CUSTOMERS = "select COUNT(product.product_id) as counter, customer.firstname, customer.lastname\n" +
//            "FROM product\n" +
//            "RIGHT JOIN sale ON product.product_id = sale.product_id\n" +
//            "RIGHT JOIN customer ON customer.customer_id = sale.customer_id\n" +
//            "GROUP BY customer.firstname, customer.lastname\n" +
//            "ORDER BY counter LIMIT ?";

    public static final String FIND_SALES_BY_CUSTOMER_ID = "SELECT product.name, SUM(product.price) AS expenses\n" +
            "FROM  product\n" +
            "JOIN sale ON sales.product_id = product.product_id\n" +
            "WHERE sale.customer_id = ?\n" +
            "AND sale.date BETWEEN ? AND ? AND extract('ISODOW' from date) < 6\n" +
            "GROUP BY product.name\n" +
            "ORDER BY expenses DESC;";
}
