package com.semenov.query;

public class ConstantQuery {

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

    public static String getPeriodQuery(String startDate, String endDate) {
        return "SELECT customer.id\n" +
                "FROM sale\n" +
                "         JOIN customer ON sale.customer_id = customer.id\n" +
                "         JOIN product ON sale.product_id = product.id\n" +
                "WHERE sale.sales_date BETWEEN '" + startDate + "' and '" + endDate + "'\n" +
                "  AND EXTRACT(isodow FROM sale.sales_date) NOT IN (6, 7)\n" +
                "GROUP BY customer.id\n" +
                "ORDER BY sum(product.cost) DESC";
    }

    public static String getSalesOnPeriodByCustomerIdQuery(Integer id, String startDate, String endDate) {
        return "SELECT customer.firstname, customer.lastname, product.name, sum(product.cost) as sum\n" +
                "FROM customer\n" +
                "         JOIN sale ON customer.id = sale.customer_id\n" +
                "         JOIN product ON sale.product_id = product.id\n" +
                "WHERE sale.sales_date BETWEEN '" + startDate + "' and '" + endDate + "'\n" +
                "  AND EXTRACT(isodow FROM sale.sales_date) NOT IN (6, 7)\n" +
                "  AND customer_id = " + id + "\n" +
                "GROUP BY customer.firstname, customer.lastname, product.name, product.cost\n" +
                "ORDER BY sum DESC;";
    }
}
