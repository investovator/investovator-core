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

package org.investovator.core.data.api;

import org.investovator.core.data.cassandraexplorer.CassandraManager;
import org.investovator.core.data.cassandraexplorer.CassandraManagerImpl;
import org.investovator.core.data.exeptions.DataAccessException;
import org.investovator.core.data.rssexplorer.RSSManager;
import org.investovator.core.data.rssexplorer.RSSManagerImpl;

/**
 * @author rajith
 * @version ${Revision}
 */
public class DataStorageImpl implements DataStorage {

    private RSSManager rssManager;
    private CassandraManager casManager;

    public DataStorageImpl() throws DataAccessException {
        rssManager = new RSSManagerImpl();
        casManager = CassandraManagerImpl.getCassandraManager();
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public void resetDataStorage() throws DataAccessException {

        /*reset rss data storage*/
        rssManager.resetDatabase();

        /*reset cassandra data storage*/
        for(CompanyStockTransactionsData.DataType dataType : CompanyStockTransactionsData.DataType.values()){
            casManager.truncateColumnFamily(CompanyStockTransactionsData.DataType.getString(dataType));
        }
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public void resetRSS() throws DataAccessException {
        rssManager.resetDatabase();
    }
}
