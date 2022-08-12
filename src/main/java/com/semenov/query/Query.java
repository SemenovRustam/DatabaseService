package com.semenov.query;

import com.semenov.config.DatabaseHandler;
import com.semenov.entity.Customer;
import com.semenov.entity.Sale;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;

public class Query {

    public List<Customer> findByLastName(String lastName) {
        List<Customer> customers = new ArrayList<>();

        try (Connection connection = DatabaseHandler.getDbConnection();
             PreparedStatement statement = connection.prepareStatement(Constant.SELECT_BY_LAST_NAME)) {
            statement.setString(1, lastName);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Customer customer = new Customer(
                        resultSet.getString("firstname"), resultSet.getString("lastname"));

                customers.add(customer);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customers;
    }


    public List<Customer> findByProductNameAndMinTimes(String productName, int minTimes) {
        List<Customer> customers = new ArrayList<>();
        try (Connection connection = DatabaseHandler.getDbConnection();
             PreparedStatement statement = connection.prepareStatement(Constant.SELECT_PRODUCT_NAME_AND_FIRST_NAME_AND_LAST_NAME)) {

            statement.setString(1, productName);
            statement.setInt(2, minTimes);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Customer customer = new Customer(resultSet.getString("firstname"), resultSet.getString("lastname"));
                customers.add(customer);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customers;
    }

    public List<Customer> findByBetweenSum(int minExpenses, int maxExpenses) {
        List<Customer> customers = new ArrayList<>();
        try (Connection connection = DatabaseHandler.getDbConnection();
             PreparedStatement statement = connection.prepareStatement(Constant.SELECT_FIRST_NAME_AND_LAST_NAME_BETWEEN)) {

            statement.setInt(1, minExpenses);
            statement.setInt(2, maxExpenses);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Customer customer = new Customer(resultSet.getString("firstname"), resultSet.getString("lastname"));
                customers.add(customer);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customers;
    }

    public List<Customer> findByBadCustomers(int badCustomers) throws SQLException {
        List<Customer> customers = new ArrayList<>();
        try (Connection connection = DatabaseHandler.getDbConnection();
             PreparedStatement statement = connection.prepareStatement(Constant.QUERY_FOR_BAD_CUSTOMERS)) {

            statement.setInt(1, badCustomers);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Customer customer = new Customer(resultSet.getString("firstname"), resultSet.getString("lastname"));
                customers.add(customer);
            }
        }
        return customers;
    }

//    public List<Sale> findSalesByCustomerId(int customerId, Date startDate, Date endDate) throws SQLException {
//        List<Sale> saleList = new ArrayList<>();
//
//        try (Connection connection = DatabaseHandler.getDbConnection();
//             PreparedStatement statement = connection.prepareStatement(Constant.FIND_SALES_BY_CUSTOMER_ID)) {
//
//            statement.setInt(1, customerId);
//            statement.setDate(2, startDate);
//            statement.setDate(3, endDate);
//
//            ResultSet resultSet = statement.executeQuery();
//            while (resultSet.next()) {
//                Sale sale = new Sale(resultSet.getString("name"), resultSet.getInt("expenses"));
//                saleList.add(sale);
//            }
//        }
//        return saleList;
//    }


}
