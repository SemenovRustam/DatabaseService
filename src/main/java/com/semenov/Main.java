package com.semenov;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.semenov.criterias.BadCustomerCriteria;
import com.semenov.criterias.NameCriteria;
import com.semenov.criterias.PriceCriteria;
import com.semenov.criterias.ProductNameCriteria;
import com.semenov.dto.FinalResult;
import com.semenov.dto.Result;
import com.semenov.entity.Customer;
import com.semenov.query.Query;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Main {


    public static void main(String[] args) throws SQLException, IOException, ParseException {
        FinalResult finalResult = new FinalResult();
        Query query = new Query();
        List<Result> resultList = new ArrayList<>();
        finalResult.setResults(resultList);
        finalResult.setType("search");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader("test.txt"));
        JSONArray jsonArray = (JSONArray) jsonObject.get("criterias");

        for (Object obj : jsonArray) {
            JSONObject jsonString = (JSONObject) obj;

            if (jsonString.containsKey("lastName")) {
                String lastName = jsonString.get("lastName").toString();
                List<Customer> customerList = query.findByLastName(lastName);
                System.out.println(customerList);


                NameCriteria nameCriteria = new NameCriteria(lastName);
                Result result = new Result();
                result.setCriteria(nameCriteria);
                resultList.add(result);
                result.setResults(customerList);
            }

            if (jsonString.containsKey("productName") & jsonString.containsKey("minTimes")) {
                String productName = jsonString.get("productName").toString();
                Integer minTimes = Integer.parseInt(jsonString.get("minTimes").toString());

                List<Customer> listCustomers = query.findByProductNameAndMinTimes(productName, minTimes);

                ProductNameCriteria productNameCriteria = new ProductNameCriteria(productName, minTimes);
                Result result = new Result();
                result.setCriteria(productNameCriteria);
                resultList.add(result);
                result.setResults(listCustomers);
            }

            if (jsonString.containsKey("minExpenses") && jsonString.containsKey("maxExpenses")) {
                Integer minExpenses = Integer.parseInt(jsonString.get("minExpenses").toString());
                Integer maxExpenses = Integer.parseInt(jsonString.get("maxExpenses").toString());

                List<Customer> listCustomers = query.findByBetweenSum(minExpenses, maxExpenses);

                PriceCriteria priceCriteria = new PriceCriteria(minExpenses, maxExpenses);
                Result result = Result.builder()
                        .criteria(priceCriteria)
                        .results(listCustomers)
                        .build();

                resultList.add(result);
            }

            if (jsonString.containsKey("badCustomers")) {
                Integer badCustomers = Integer.parseInt(jsonString.get("badCustomers").toString());

                List<Customer> listBadCustomers = query.findByBadCustomers(badCustomers);

                BadCustomerCriteria badCustomerCriteria = new BadCustomerCriteria(badCustomers);

                Result result = Result.builder()
                        .criteria(badCustomerCriteria)
                        .results(listBadCustomers)
                        .build();

                resultList.add(result);
            }

        }
        objectMapper.writeValue(new File("resultFile.txt"), finalResult);
    }
}
