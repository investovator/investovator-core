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

import org.investovator.core.data.exeptions.DataAccessException;

import java.io.FileInputStream;

/**
 * @author rajith
 * @version $Revision$
 */
public interface CassandraManager {

    public static final String URL_KEY = "org.investovator.core.data.cassandra.url";
    public static final String USERNAME_KEY = "org.investovator.core.data.cassandra.username";
    public static final String PASSWORD_KEY = "org.investovator.core.data.cassandra.password";


    public void importCSV(String keyspace, String columnFamily,
                          FileInputStream fileInputStream) throws DataAccessException;

    public void importXls(String keyspace, String columnFamily,
                          FileInputStream fileInputStream) throws DataAccessException;

    public void truncateColumnFamily(String keyspace, String columnFamily) throws DataAccessException;

    @Deprecated
    public void dropColumnFamily(String keyspace, String columnFamily) throws DataAccessException;
}
