package com.semenov;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.semenov.utils.Utils;
import com.semenov.operation.Search;
import com.semenov.operation.Statistic;
import com.semenov.query.SearchQuery;
import com.semenov.query.StatisticQuery;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class Main {

    public static final String SEARCH = "search";
    public static final String STATISTIC = "stat";
    public static final String DEFAULT_PATH_NAME = "D:\\exception.json";

    public static void main(String[] args) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            String inputFile = args[1];
            String outputFile = args[2];

            if (args.length != 3) {
                Utils.writeExceptionInJsonFile("Arguments must be equals 3", outputFile);
                log.error("Arguments must be equals 3");
            }
            switch (args[0]) {
                case SEARCH:
                    SearchQuery searchQuery = new SearchQuery();
                    Search search = new Search(searchQuery, objectMapper);
                    search.search(inputFile, outputFile);
                    break;
                case STATISTIC:
                    StatisticQuery statisticQuery = new StatisticQuery();
                    Statistic statistic = new Statistic(statisticQuery, objectMapper);
                    statistic.getStatistic(inputFile, outputFile);
                    break;
                default:
                    log.info("Enter correct arguments!");
                    Utils.writeExceptionInJsonFile("Try again", outputFile);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            Utils.writeExceptionInJsonFile(e.getMessage(), DEFAULT_PATH_NAME);
        }
    }
}
