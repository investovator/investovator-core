/*
 * investovator, Stock Market Gaming framework
 * Copyright (C) 2013  investovator
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.investovator.core.data.api;


import org.investovator.core.data.api.utils.StockTradingData;
import org.investovator.core.data.api.utils.TradingDataAttribute;
import org.investovator.core.data.exeptions.DataAccessException;
import org.investovator.core.data.exeptions.DataNotFoundException;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author rajith
 * @version $Revision$
 */
public interface CompanyStockTransactionsData {

    /**
     * Currently Data module supports only two trading
     * data types
     */
    public enum DataType {
        OHLC,
        TICKER;

        public static String getString(DataType dataType){
            switch (dataType) {
                case OHLC:
                    return "ohlc_data";

                case TICKER:
                    return "ticker_data";
            }
            return null;
        }

    }

    /**
     * @param type either OHLC or Ticker
     * @param symbol StockId
     * @param startingDate Required data starting date
     * @param endDate upper limit of date
     * @param attributes Required attributes;
     *                   for ticker data valid trading attributes are TIME & PRICE only
     * @param numOfRows Required num of rows
     * @return {@link StockTradingData}
     * @throws DataAccessException,
     * @throws DataNotFoundException in case of only the requested data are not found.
     */
    public StockTradingData getTradingData (DataType type, String symbol,  Date startingDate, Date endDate,
                                            int numOfRows, ArrayList<TradingDataAttribute> attributes)
            throws DataNotFoundException ,DataAccessException;

    /**
     * returns available OHLC data's days range for symbol
     * @param type either OHLC or Ticker
     * @param symbol stock id
     * @return two element array
     * startDay = date[0], endDay = date[1]
     * @throws DataAccessException
     */
    public Date[] getDataDaysRange(DataType type, String symbol) throws DataAccessException;


    /*Configuration related*/

    /**
     * @param type either OHLC or Ticker
     * @param stockId Stock id
     * @param dateFormat dateFormat in csv file
     *                   ex: for OHLC data: "MM/dd/yyyy"
     *                   ex: for Ticker data: "MM/dd/yyyy HH:MM:SS"
     * @param file csv file
     * @throws DataAccessException
     */
    public void importCSV(DataType type, String stockId, String dateFormat, File file)
            throws DataAccessException;

    /**
     * @param type either OHLC or Ticker
     * @param stockId Stock id
     * @param file csv file
     * @throws DataAccessException
     */
    public void importXls(DataType type, String stockId, File file) throws DataAccessException;

    /**
     * @param type either OHLC or Ticker
     * @param stockId Stock Id
     * @throws DataAccessException
     */
    public void clearTradingData(DataType type, String stockId) throws DataAccessException;

    /**
     * Clear everything
     * @param type either OHLC or Ticker
     * @throws DataAccessException
     */
    public void clearAllTradingData(DataType type) throws DataAccessException;

    /**
     *
     * Check availability of data
     * @param type either OHLC or Ticker
     * @param stockId Stock Id
     * @return data availability
     * @throws DataAccessException
     */
    public boolean isDataAvailable(DataType type, String stockId) throws DataAccessException;

}
