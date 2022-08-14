package com.semenov.query;

import com.semenov.config.DatabaseHandler;
import com.semenov.entity.Customer;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SearchQuery {

    public List<Customer> findCustomersByLastName(String lastName) {
        List<Customer> customerList = new ArrayList<>();
        try (Connection connection = DatabaseHandler.getDbConnection();
             PreparedStatement statement = connection.prepareStatement(Constant.SELECT_BY_LAST_NAME)) {
            statement.setString(1, lastName);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Customer customer = getCustomer(resultSet);
                customerList.add(customer);
            }
        } catch (SQLException exception) {
            log.error(exception.getMessage());
        }
        return customerList;
    }


    public List<Customer> findCustomersByProductNameAndMinTimes(String productName, int minTimes) {
        List<Customer> customerList = new ArrayList<>();
        try (Connection connection = DatabaseHandler.getDbConnection();
             PreparedStatement statement = connection.prepareStatement(Constant.SELECT_PRODUCT_NAME_AND_FIRST_NAME_AND_LAST_NAME)) {
            statement.setString(1, productName);
            statement.setInt(2, minTimes);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Customer customer = getCustomer(resultSet);
                customerList.add(customer);
            }
        } catch (SQLException exception) {
            log.error(exception.getMessage());
        }
        return customerList;
    }

    public List<Customer> findCustomersByBetweenSum(int minExpenses, int maxExpenses) {
        List<Customer> customerList = new ArrayList<>();
        try (Connection connection = DatabaseHandler.getDbConnection();
             PreparedStatement statement = connection.prepareStatement(Constant.SELECT_FIRST_NAME_AND_LAST_NAME_BETWEEN)) {
            statement.setInt(1, minExpenses);
            statement.setInt(2, maxExpenses);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Customer customer = getCustomer(resultSet);
                customerList.add(customer);
            }
        } catch (SQLException exception) {
            log.error(exception.getMessage());
        }
        return customerList;
    }

    public List<Customer> findCustomersByBadCustomers(int badCustomers) {
        List<Customer> customerList = new ArrayList<>();
        try (Connection connection = DatabaseHandler.getDbConnection();
             PreparedStatement statement = connection.prepareStatement(Constant.QUERY_FOR_BAD_CUSTOMERS)) {
            statement.setInt(1, badCustomers);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Customer customer = getCustomer(resultSet);
                customerList.add(customer);
            }
        } catch (SQLException exception) {
            log.error(exception.getMessage());
        }
        return customerList;
    }

    private Customer getCustomer(ResultSet resultSet) throws SQLException {
        return Customer.builder()
                .firstName(resultSet.getString("firstname"))
                .lastName(resultSet.getString("lastname"))
                .build();
    }
}
