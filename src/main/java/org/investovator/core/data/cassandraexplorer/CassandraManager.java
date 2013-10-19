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

package org.investovator.core.data.cassandraexplorer;

import org.investovator.core.data.api.utils.TradingDataAttribute;
import org.investovator.core.data.exeptions.DataAccessException;
import org.investovator.core.data.exeptions.DataNotFoundException;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * @author rajith
 * @version $Revision$
 */
public interface CassandraManager {

    public static final String URL_KEY = "org.investovator.core.data.cassandra.url";
    public static final String USERNAME_KEY = "org.investovator.core.data.cassandra.username";
    public static final String PASSWORD_KEY = "org.investovator.core.data.cassandra.password";

    public static final String KEYSPACE = "investovator_data";


    public void importCSV(String columnFamilyName, String rowKey, String dateFormat,
                          FileInputStream fileInputStream) throws DataAccessException;

    public void importXls(String columnFamilyName, String rowKey, String dateFormat,
                          FileInputStream fileInputStream) throws DataAccessException;

    public void truncateColumnFamily(String columnFamily) throws DataAccessException;

    @Deprecated
    public void dropColumnFamily(String columnFamily) throws DataAccessException;

    public HashMap<Date, HashMap<TradingDataAttribute, String>> getData(String dataType, String symbol,
                                                                        Date startingDate, Date endDate, int numOfRows,
                                                                        ArrayList<TradingDataAttribute> attributes)

            throws DataAccessException, DataNotFoundException;

    //TODO truncate stock data
}
