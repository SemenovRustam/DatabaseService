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

import static com.semenov.query.Constant.getPeriodQuery;
import static com.semenov.query.Constant.getSalesOnPeriodByCustomerIdQuery;

@Slf4j
public class StatisticQuery {

    public List<Integer> findCustomersIdInPeriod(String startDate, String endDate) {
        List<Integer> listCustomersId = new ArrayList<>();
        try (Connection connection = DatabaseHandler.getDbConnection();
             PreparedStatement statement = connection.prepareStatement(getPeriodQuery(startDate, endDate))) {

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                listCustomersId.add(resultSet.getInt("id"));
            }
        } catch (SQLException exception) {
            log.error(exception.getMessage());
        }
        log.info("CUSTOMERS ID FOR A PERIOD : {}", listCustomersId);
        return listCustomersId;
    }


    public Map<String, List<Purchase>> findSalesOnPeriodByCustomersId(Integer id, String startDate, String endDate) {
        log.info("START FIND SALES FOR PERIOD BY CUSTOMER ID : {}\n", id);
        Map<String, List<Purchase>> nameAndSalesMap = new HashMap<>();
        List<Purchase> purchaseList = new ArrayList<>();

        try (Connection connection = DatabaseHandler.getDbConnection();
             PreparedStatement statement = connection.prepareStatement(getSalesOnPeriodByCustomerIdQuery(id, startDate, endDate))) {

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Purchase purchase = getPurchase(resultSet);
                String fullCustomerName = getFullCustomerName(resultSet);
                purchaseList.add(purchase);
                nameAndSalesMap.put(fullCustomerName, purchaseList);
            }
        } catch (SQLException exception) {
            log.error(exception.getMessage());
        }
        return nameAndSalesMap;
    }

    private Purchase getPurchase(ResultSet resultSet) throws SQLException {
        return Purchase.builder()
                .name(resultSet.getString("name"))
                .expenses(resultSet.getInt("sum"))
                .build();
    }

    private String getFullCustomerName(ResultSet resultSet) throws SQLException {
        return resultSet.getString("lastname") + " " + resultSet.getString("firstname");
    }
}

