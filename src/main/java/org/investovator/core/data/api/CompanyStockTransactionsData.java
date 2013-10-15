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

import java.io.File;
import java.util.Date;
import java.util.HashMap;

/**
 * @author rajith
 * @version $Revision$
 */
public interface CompanyStockTransactionsData {

    /**
     *
     * @param symbol StockId
     * @param startingDate Required data starting date
     * @param attributes Required attributes
     * @param numOfRows Required num of rows
     * @return {@link StockTradingData}
     * @throws DataAccessException
     */
    public StockTradingData getTradingDataOHLC (String symbol,  Date startingDate,
                                                TradingDataAttribute[] attributes,
                                                int numOfRows) throws DataAccessException;


    /**
     *
     * @param symbol StockId
     * @param startingTime Ticker data starting time
     * @param numOfRows Required num of rows
     * @return HashMap containing time and transaction price
     * @throws DataAccessException
     */
    public HashMap<Date, Float> getTradingData (String symbol, Date startingTime,
                                            int numOfRows) throws DataAccessException;


    /*Configuration related*/

    /**
     * @param stockId Stock id
     * @param file csv file
     * @throws DataAccessException
     */
    public void importCSV(String stockId, File file) throws DataAccessException;

    /**
     * @param stockId Stock id
     * @param file csv file
     * @throws DataAccessException
     */
    public void importXls(String stockId, File file) throws DataAccessException;

    /**
     *
     * @param stockId Stock Id
     * @throws DataAccessException
     */
    public void clearTradingData(String stockId) throws DataAccessException;

    /**
     * Clear Keyspace
     * @throws DataAccessException
     */
    public void clearAllTradingData() throws DataAccessException;

}
