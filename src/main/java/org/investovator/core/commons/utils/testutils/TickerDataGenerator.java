/*
 * investovator, Stock Market Gaming framework
 * Copyright (C) 2013  investovator
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.investovator.core.commons.utils.testutils;

import au.com.bytecode.opencsv.CSVWriter;
import org.investovator.core.data.api.CompanyStockTransactionsData;
import org.investovator.core.data.api.CompanyStockTransactionsDataImpl;
import org.investovator.core.data.api.utils.StockTradingData;
import org.investovator.core.data.api.utils.TradingDataAttribute;
import org.investovator.core.data.exeptions.DataAccessException;
import org.investovator.core.data.exeptions.DataNotFoundException;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * @author Amila Surendra
 * @version $Revision
 */
public class TickerDataGenerator {

    private String stockID;
    private double priceVariance;
    private String outputFilePath;
    private String dateFormat;

    //CSV Headings
    String[] columns = {
            TradingDataAttribute.getAttribName(TradingDataAttribute.TIME),
            TradingDataAttribute.getAttribName(TradingDataAttribute.PRICE),
            TradingDataAttribute.getAttribName(TradingDataAttribute.SHARES),
    };

    public TickerDataGenerator(String stockID) {
        this.stockID = stockID;

        //Creating Default Settings
        priceVariance = 1.5;
        outputFilePath = System.getProperty("user.home");
        dateFormat = "MM/dd/yyyy HH:mm:ss.SSS";
    }

    public String getStockID() {
        return stockID;
    }

    public void setStockID(String stockID) {
        this.stockID = stockID;
    }

    public double getPriceVariance() {
        return priceVariance;
    }

    public void setPriceVariance(double priceVariance) {
        this.priceVariance = priceVariance;
    }

    public String getOutputFilePath() {
        return outputFilePath;
    }

    public void setOutputFilePath(String outputFilePath) {
        this.outputFilePath = outputFilePath;
    }


    /**
     * Create CSV file for the given stock
     * @return output file path if success null if fails.
     */
    public String CreateCSV(){

        try {
            CSVWriter csvWriter = new CSVWriter(new FileWriter(outputFilePath));
            csvWriter.writeNext(columns);
            createDataRows(csvWriter);
            csvWriter.flush();
            csvWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return outputFilePath;

    }

    private void createDataRows(CSVWriter writer){

        try {

            CompanyStockTransactionsData historyData = new CompanyStockTransactionsDataImpl();

            Date[] dates = historyData.getDataDaysRange(CompanyStockTransactionsData.DataType.OHLC,stockID);

            ArrayList<TradingDataAttribute> dataAttribs = new ArrayList<>();
            dataAttribs.add(TradingDataAttribute.DAY);
            dataAttribs.add(TradingDataAttribute.CLOSING_PRICE);
            dataAttribs.add(TradingDataAttribute.TRADES);
            dataAttribs.add(TradingDataAttribute.SHARES);

            StockTradingData tradingData = historyData.getTradingData(CompanyStockTransactionsData.DataType.OHLC, stockID, dates[0], dates[1], Integer.MAX_VALUE,dataAttribs) ;

            HashMap<Date, HashMap<TradingDataAttribute, String>>  allTradingData  = tradingData.getTradingData();

            for(Date date : allTradingData.keySet()){

                HashMap<TradingDataAttribute,String> oneDayData = allTradingData.get(date);
                int trades = Integer.parseInt(oneDayData.get(TradingDataAttribute.TRADES));
                int shares = Integer.parseInt(oneDayData.get(TradingDataAttribute.SHARES));
                float closingPrice = Integer.parseInt( oneDayData.get(TradingDataAttribute.CLOSING_PRICE) );

                ArrayList<String[]> day = getSingleDay(trades ,shares,closingPrice );
                for(String[] line : day) {
                    writer.writeNext(line);
                }
            }


        } catch (DataAccessException e) {
            e.printStackTrace();
        } catch (DataNotFoundException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<String[]> getSingleDay(int tradesPerDay, int shares, double avgPrice){

        String
        int addedTrades;

        for (addedTrades = 0; addedTrades < tradesPerDay; addedTrades++) {



        }

    }


}
