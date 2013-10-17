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

import au.com.bytecode.opencsv.CSVReader;
import me.prettyprint.cassandra.serializers.DateSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.beans.HSuperColumn;
import me.prettyprint.hector.api.ddl.ColumnFamilyDefinition;
import me.prettyprint.hector.api.ddl.ColumnType;
import me.prettyprint.hector.api.ddl.ComparatorType;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.mutation.Mutator;
import org.investovator.core.data.cassandraexplorer.utils.CassandraConnector;
import org.investovator.core.data.exeptions.DataAccessException;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author rajith
 * @version $Revision$
 */
public class CassandraManagerImpl implements CassandraManager{

    private static final String CHAR_ENCODING = "UTF8";
    private static volatile CassandraManagerImpl cassandraManager;

    public static CassandraManagerImpl getCassandraManager() {
        if(cassandraManager == null){
            synchronized(CassandraManagerImpl.class){
                if(cassandraManager == null)
                    cassandraManager = new CassandraManagerImpl();
            }
        }
        return cassandraManager;
    }

    private CassandraManagerImpl(){}

    @Override
    public void importCSV(String columnFamilyName, String rowKey, String dateFormat,
                          FileInputStream fileInputStream) throws DataAccessException {

        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        try {

            //Since Mutator is not thread safe
            synchronized (this){
                Cluster cluster = getClusterInitialized();
                CassandraConnector.createKeyspaceIfNotExists(cluster, KEYSPACE);
                CassandraConnector.createColumnFmlyIfNotExists(cluster, KEYSPACE, columnFamilyName);

                Keyspace keyspace =  HFactory.createKeyspace(KEYSPACE, cluster);
                ColumnFamilyDefinition columnFamilyDef = HFactory.createColumnFamilyDefinition(KEYSPACE,
                        columnFamilyName, ComparatorType.UTF8TYPE);
                columnFamilyDef.setColumnType(ColumnType.SUPER);

                CSVReader reader = new CSVReader(new InputStreamReader(fileInputStream, CHAR_ENCODING));
                String[] labelsColumn = reader.readNext();

                Mutator<String> mutator = HFactory.createMutator(keyspace, StringSerializer.get());
                String [] data;

                while ((data = reader.readNext()) != null) {
                    ArrayList <HColumn<String, String>> columns = new ArrayList<HColumn<String, String>>();
                    for (int num = 1; num < labelsColumn.length; num ++){
                        HColumn<String, String> column = HFactory.
                                createStringColumn(labelsColumn[num], data[num]);
                        columns.add(column);
                    }

                    HSuperColumn<Date, String, String> superColumn = HFactory
                            .createSuperColumn(format.parse(data[0]), columns,
                                    DateSerializer.get(), StringSerializer.get(), StringSerializer.get());

                    mutator.addInsertion(rowKey, columnFamilyDef.getName(), superColumn);
                }
                mutator.execute();
            }
        } catch (Exception e) {
            throw new DataAccessException(e);
        }


    }

    @Override
    public void importXls(String columnFamilyName, String rowKey, String dateFormat,
                          FileInputStream fileInputStream) throws DataAccessException {
        //TODO
    }

    @Override
    public void truncateColumnFamily(String columnFamilyName)  throws DataAccessException{
        Cluster cluster = getClusterInitialized();
        try {
            CassandraConnector.truncateColumnFamily(cluster, KEYSPACE, columnFamilyName);
        } catch (Exception e){
            throw new DataAccessException(e);
        }
    }

    @Override
    public void dropColumnFamily(String columnFamilyName) throws DataAccessException {
        Cluster cluster = getClusterInitialized();
        try {
            CassandraConnector.dropColumnFamily(cluster, KEYSPACE, columnFamilyName);
        } catch (Exception e){
            throw new DataAccessException(e);
        }
    }

    private Cluster getClusterInitialized() {
        String username = System.getProperty(USERNAME_KEY);
        String password = System.getProperty(PASSWORD_KEY);
        String url = System.getProperty(URL_KEY);

        return CassandraConnector.createCluster(username, password, url);
    }

}
