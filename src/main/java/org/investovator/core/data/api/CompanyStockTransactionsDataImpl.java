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
import org.investovator.core.data.cassandraexplorer.CassandraManager;
import org.investovator.core.data.cassandraexplorer.CassandraManagerImpl;
import org.investovator.core.data.exeptions.DataAccessException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.HashMap;

/**
 * @author rajith
 * @version $Revision$
 */
public class CompanyStockTransactionsDataImpl implements CompanyStockTransactionsData{

    private CassandraManager manager;

    public CompanyStockTransactionsDataImpl(){
        manager = CassandraManagerImpl.getCassandraManager();
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public StockTradingData getTradingDataOHLC (String symbol,  Date startingDate,
                                                TradingDataAttribute[] attributes,
                                                   int numOfRows) throws DataAccessException{

        return null; //TODO
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public Date[] getOHLCDataDaysRange(String symbol) {
        return new Date[0];  //TODO
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public HashMap<Date, Float> getTradingData(String symbol, Date startingTime,
                                               int numOfRows) throws DataAccessException{
        return null; //TODO
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public Date[] getTradingDataDaysRange(String symbol) {
        return new Date[0];  //TODO
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public void importCSV(String stockId, File file) throws DataAccessException {
        try {
            manager.importCSV(stockId, new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new DataAccessException(e);
        }
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public void importXls(String stockId, File file) throws DataAccessException {
        //TODO
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public void clearTradingData(String stockId) throws DataAccessException {
        manager.truncateColumnFamily(stockId);
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public void clearAllTradingData() throws DataAccessException {
        //TODO
    }
}
