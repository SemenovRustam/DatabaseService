package com.semenov.parser;

import com.semenov.criterias.BadCustomerCriteria;
import com.semenov.criterias.NameCriteria;
import com.semenov.criterias.PriceCriteria;
import com.semenov.criterias.ProductNameCriteria;
import com.semenov.dto.Result;
import com.semenov.entity.Customer;
import com.semenov.query.Query;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.List;

public class SearchParser {

    private Query query;

    public void search(){
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
    }

}
