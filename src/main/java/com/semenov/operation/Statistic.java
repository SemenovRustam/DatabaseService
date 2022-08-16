package com.semenov.operation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.semenov.utils.Utils;
import com.semenov.dto.CompleteStatistics;
import com.semenov.dto.CustomerStatistic;
import com.semenov.dto.Purchase;
import com.semenov.dto.StatisticDate;
import com.semenov.exception.ApplicationException;
import com.semenov.query.StatisticQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
public class Statistic {

    private final StatisticQuery statisticQuery;
    private final ObjectMapper objectMapper;


    public void getStatistic(String inputFile, String outputFile) {
        JSONObject inputJson = getJsonObject(inputFile, outputFile);
        StatisticDate statisticDate = getStatisticDate(inputJson, outputFile);

        String startDate = statisticDate.getStartDate();
        String endDate = statisticDate.getEndDate();

        validateDate(outputFile, startDate, endDate);

        LocalDate startDateLocal = LocalDate.parse(startDate);
        LocalDate endDateLocal = LocalDate.parse(endDate);

        Integer numberDay = getWorkDay(startDateLocal, endDateLocal);
        List<Integer> customersIdInPeriod = statisticQuery.findCustomersIdInPeriod(startDate, endDate);

        List<CustomerStatistic> customerStatisticList = new ArrayList<>();
        customersIdInPeriod.forEach(id -> {
            Map<String, List<Purchase>> salesOnPeriodByCustomersId = statisticQuery.findSalesOnPeriodByCustomersId(id, startDate, endDate);

            salesOnPeriodByCustomersId.forEach((name, purchases) -> {
                int totalPurchaseSum = purchases.stream()
                        .mapToInt(Purchase::getExpenses)
                        .sum();

                CustomerStatistic customerStatistic = CustomerStatistic.builder()
                        .name(name)
                        .purchases(purchases)
                        .totalExpenses(totalPurchaseSum)
                        .build();
                customerStatisticList.add(customerStatistic);
            });
        });

        int totalExpensiveAllPurchaseOfPeriod = getTotalExpensiveAllPurchaseForPeriod(customerStatisticList);
        BigDecimal avgExpenses = getAvgExpenses(customersIdInPeriod, totalExpensiveAllPurchaseOfPeriod);

        log.info("START CREATE A COMPLETE STATISTIC FOR PERIOD\n");
        CompleteStatistics finalStatistics = CompleteStatistics.builder()
                .totalDays(numberDay)
                .customers(customerStatisticList)
                .type(Type.STAT.getType())
                .totalExpenses(totalExpensiveAllPurchaseOfPeriod)
                .avgExpenses(avgExpenses)
                .build();
        log.info("COMPLETE STATISTIC FOR PERIOD {}\n", finalStatistics);
        writeResultStatisticInFile(finalStatistics, outputFile);
    }

    private void validateDate(String outputFile, String startDate, String endDate) {
        String correctDate = "\\d{4}-\\d{2}-\\d{2}";

        if (!startDate.matches(correctDate) || !endDate.matches(correctDate)) {
            log.warn("incorrect date {}, {}", startDate, endDate);
            Utils.writeExceptionInJsonFile("Incorrect date from Json file!", outputFile);
            throw new ApplicationException("Incorrect date from Json file!");
        }
        log.info("START DATE : {}, END DATE : {}", startDate, endDate);
    }


    private void writeResultStatisticInFile(CompleteStatistics finalStatistics, String outputFile) {
        try {
            objectMapper.writeValue(new File(outputFile), finalStatistics);
        } catch (IOException e) {
            Utils.writeExceptionInJsonFile(e.getMessage(), outputFile);
            e.printStackTrace();
        }
    }

    private BigDecimal getAvgExpenses(List<Integer> customersIdInPeriod, int totalExpensiveAllPurchaseOfPeriod) {
        int numberOfCustomersOfPeriod = customersIdInPeriod.size();
        BigDecimal totalExpensive = BigDecimal.valueOf(totalExpensiveAllPurchaseOfPeriod);

        BigDecimal avgExpenses = totalExpensive.divide(BigDecimal.valueOf(numberOfCustomersOfPeriod), 2, RoundingMode.HALF_EVEN);
        log.info("TOTAL AVERAGE EXPENSIVE : {}\n", avgExpenses);
        return avgExpenses;
    }

    private int getTotalExpensiveAllPurchaseForPeriod(List<CustomerStatistic> customerStatisticList) {
        int totalExpensiveAllPurchaseOfPeriod = customerStatisticList.stream()
                .mapToInt(CustomerStatistic::getTotalExpenses)
                .sum();

        log.info("TOTAL EXPENSIVE FOR ALL PURCHASE : {}", totalExpensiveAllPurchaseOfPeriod);
        return totalExpensiveAllPurchaseOfPeriod;
    }

    private StatisticDate getStatisticDate(JSONObject inputJson, String output) {
        StatisticDate statisticDate;
        try {
            statisticDate = objectMapper.readValue(inputJson.toString(), StatisticDate.class);
        } catch (IOException e) {
            Utils.writeExceptionInJsonFile(e.getMessage(), output);
            throw new ApplicationException("Exception  while mapping file");
        }
        return statisticDate;
    }

    private JSONObject getJsonObject(String inputFile, String output) {
        JSONParser jsonParser = new JSONParser();
        JSONObject inputJson;
        try {
            inputJson = (JSONObject) jsonParser.parse(new FileReader(inputFile));
            log.info("INPUT JSON : {}\n", inputJson);
        } catch (IOException | ParseException e) {
            log.error(e.getMessage());
            Utils.writeExceptionInJsonFile(e.getMessage(), output);
            throw new ApplicationException("Exception in parse file.");
        }
        return inputJson;
    }

    private Integer getWorkDay(LocalDate startDateLocal, LocalDate endDateLocal) {
        DayOfWeek startW = startDateLocal.getDayOfWeek();
        DayOfWeek endW = endDateLocal.getDayOfWeek();

        long days = ChronoUnit.DAYS.between(startDateLocal, endDateLocal);
        long daysWithoutWeekends = days - ((days + startW.getValue()) / 7);

        Long numberDay = daysWithoutWeekends + (startW == DayOfWeek.SUNDAY ? 1 : 0) + (endW == DayOfWeek.SUNDAY ? 1 : 0);
        log.info("Work day quantity : {}\n", numberDay);
        return numberDay.intValue();
    }
}
