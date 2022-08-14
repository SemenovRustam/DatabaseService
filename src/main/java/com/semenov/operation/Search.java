package com.semenov.operation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.semenov.criterias.Criteria;
import com.semenov.utils.Utils;
import com.semenov.criterias.BadCustomerCriteria;
import com.semenov.criterias.NameCriteria;
import com.semenov.criterias.PriceCriteria;
import com.semenov.criterias.ProductNameCriteria;
import com.semenov.dto.Result;
import com.semenov.dto.SearchResult;
import com.semenov.entity.Customer;
import com.semenov.query.SearchQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@RequiredArgsConstructor
public class Search {

    private final SearchQuery searchQuery;
    private final ObjectMapper objectMapper;


    public void search(String inputFile, String outputFile) {
        List<Result> resultList = new ArrayList<>();
        SearchResult searchResult = SearchResult.builder()
                .type(Type.SEARCH.getType())
                .results(resultList)
                .build();

        JSONParser jsonParser = new JSONParser();
        JSONObject inputJson = null;
        try {
            inputJson = (JSONObject) jsonParser.parse(new FileReader(inputFile));
            log.info("INPUT JSON : {}\n", inputJson);
        } catch (IOException | ParseException e) {
            Utils.writeExceptionInJsonFile(e.getMessage(), outputFile);
        }
        JSONArray jsonArray = (JSONArray) inputJson.get("criterias");

        jsonArray.forEach(objectFromJsonArray -> {
            JSONObject jsonString = (JSONObject) objectFromJsonArray;
            log.info("OBJECT FROM JSON ARRAY {}", jsonString);

            parseLastNameFromJson(resultList, jsonString);
            parseProductNameAndMinTimes(resultList, jsonString);
            parseMinAndMaxExpense(resultList, jsonString);
            parseBadCustomers(resultList, jsonString);
        });
        writeFinalResultSearch(searchResult, outputFile);
    }

    private void writeFinalResultSearch(SearchResult searchResult, String outputFile) {
        try {
            objectMapper.writeValue(new File(outputFile), searchResult);
        } catch (IOException e) {
            Utils.writeExceptionInJsonFile(e.getMessage(), outputFile);
            e.printStackTrace();
        }
    }

    private void parseBadCustomers(List<Result> resultList, JSONObject jsonString) {
        if (jsonString.containsKey("badCustomers")) {
            int badCustomers = Integer.parseInt(jsonString.get("badCustomers").toString());
            log.info("BAD CUSTOMERS FROM JSON : {}", badCustomers);

            List<Customer> listBadCustomers = searchQuery.findCustomersByBadCustomers(badCustomers);

            BadCustomerCriteria badCustomerCriteria = new BadCustomerCriteria(badCustomers);

            Result result = getResult(listBadCustomers, badCustomerCriteria);

            resultList.add(result);
            log.info("ADD IN RESULT LIST RESULT FROM QUERY {}\n", result);
        }
    }

    private void parseMinAndMaxExpense(List<Result> resultList, JSONObject jsonString) {
        if (jsonString.containsKey("minExpenses") && jsonString.containsKey("maxExpenses")) {
            int minExpenses = Integer.parseInt(jsonString.get("minExpenses").toString());
            int maxExpenses = Integer.parseInt(jsonString.get("maxExpenses").toString());
            log.info("MIN EXPENSES : {},   MAX EXPENSES : {}", minExpenses, maxExpenses);

            List<Customer> listCustomers = searchQuery.findCustomersByBetweenSum(minExpenses, maxExpenses);

            PriceCriteria priceCriteria = new PriceCriteria(minExpenses, maxExpenses);
            Result result = getResult(listCustomers, priceCriteria);
            resultList.add(result);
            log.info("ADD IN RESULT LIST RESULT FROM QUERY {}\n", result);
        }
    }


    private void parseProductNameAndMinTimes(List<Result> resultList, JSONObject jsonString) {
        if (jsonString.containsKey("productName") && jsonString.containsKey("minTimes")) {
            String productName = jsonString.get("productName").toString();
            log.info("PRODUCT NAME FROM JSON : {}", productName);

            int minTimes = Integer.parseInt(jsonString.get("minTimes").toString());
            log.info("MIN TIMES  FROM JSON : {}", minTimes);

            List<Customer> listCustomers = searchQuery.findCustomersByProductNameAndMinTimes(productName, minTimes);
            ProductNameCriteria productNameCriteria = new ProductNameCriteria(productName, minTimes);

            Result result = getResult(listCustomers, productNameCriteria);

            resultList.add(result);
            log.info("ADD IN RESULT LIST RESULT FROM QUERY {}\n", result);
        }
    }

    private void parseLastNameFromJson(List<Result> resultList, JSONObject jsonString) {
        if (jsonString.containsKey("lastName")) {
            String lastName = jsonString.get("lastName").toString();
            List<Customer> customerList = searchQuery.findCustomersByLastName(lastName);
            log.info("LAST NAME FROM JSON STRING : {}", lastName);

            NameCriteria nameCriteria = new NameCriteria(lastName);

            Result result = getResult(customerList, nameCriteria);
            resultList.add(result);
            log.info("ADD IN RESULT LIST RESULT FROM QUERY {}\n", result);
        }
    }

    private Result getResult(List<Customer> listCustomers, Criteria criteria) {
        return Result.builder()
                .criteria(criteria)
                .results(listCustomers)
                .build();
    }
}
