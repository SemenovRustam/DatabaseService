package com.semenov.query;

import com.semenov.config.DatabaseHandler;
import com.semenov.dto.Purchase;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class StatisticQuery {

    public List<Integer> findCustomersIdInPeriod(String startDate, String endDate) {
        List<Integer> listCustomersId = new ArrayList<>();

        try (Connection connection = DatabaseHandler.getDbConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT customer.id\n" +
                     "FROM sale\n" +
                     "         JOIN customer ON sale.customer_id = customer.id\n" +
                     "         JOIN product ON sale.product_id = product.id\n" +
                     "WHERE sale.sales_date BETWEEN '" + startDate + "' and '" + endDate + "'\n" +
                     "  AND EXTRACT(isodow FROM sale.sales_date) NOT IN (6, 7)\n" +
                     "GROUP BY customer.id\n" +
                     "ORDER BY sum(product.cost) DESC")) {

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                listCustomersId.add(resultSet.getInt("id"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        log.info("CUSTOMERS ID FOR A PERIOD : {}", listCustomersId);
        return listCustomersId;
    }


    public Map<String, List<Purchase>> findSalesOnPeriodByCustomersId(Integer id, String startDate, String endDate) {
        log.info("START FIND SALES FOR PERIOD BY CUSTOMER ID : {}\n", id);
        Map<String, List<Purchase>> nameAndSalesMap = new HashMap<>();
        List<Purchase> purchaseList = new ArrayList<>();

        try (Connection connection = DatabaseHandler.getDbConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT customer.firstname, customer.lastname, product.name, sum(product.cost) as sum\n" +
                     "FROM customer\n" +
                     "         JOIN sale ON customer.id = sale.customer_id\n" +
                     "         JOIN product ON sale.product_id = product.id\n" +
                     "WHERE sale.sales_date BETWEEN '" + startDate + "' and '" + endDate + "'\n" +
                     "  AND EXTRACT(isodow FROM sale.sales_date) NOT IN (6, 7)\n" +
                     "  AND customer_id = " + id + "\n" +
                     "GROUP BY customer.firstname, customer.lastname, product.name, product.cost\n" +
                     "ORDER BY sum DESC;")) {

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {

                Purchase purchase = Purchase.builder()
                        .name(resultSet.getString("name"))
                        .expenses(resultSet.getInt("sum"))
                        .build();

                String fullCustomerName = resultSet.getString("lastname") + " " + resultSet.getString("firstname");
                purchaseList.add(purchase);
                nameAndSalesMap.put(fullCustomerName, purchaseList);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return nameAndSalesMap;
    }
}

