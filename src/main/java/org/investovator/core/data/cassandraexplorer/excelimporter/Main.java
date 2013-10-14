package org.investovator.core.data.cassandraexplorer.excelimporter;

import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {

        ConfigParser parser = new ConfigParser("/home/hasala/Excel-Database/src/main/resources/sampleConfig.xml");

        ReadExcel test = new ReadExcel();
        test.setInputFile("/home/hasala/Desktop/Sampath Data/sampath_daily.xls",parser);
        test.read();

        ArrayList<HistoryData> historyData = test.getSharePriceData();

        DatabaseManager manager = new DatabaseManager();
        manager.saveHistoryData(historyData.get(0));
        manager.saveHistoryData(historyData.get(1));
        manager.saveHistoryData(historyData.get(2));
        manager.saveHistoryData(historyData.get(3));

    }
}