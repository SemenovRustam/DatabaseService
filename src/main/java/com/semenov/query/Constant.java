package com.semenov.query;

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
}
