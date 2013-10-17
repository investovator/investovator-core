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
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.ddl.ColumnFamilyDefinition;
import me.prettyprint.hector.api.ddl.ComparatorType;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.mutation.Mutator;
import org.investovator.core.data.cassandraexplorer.utils.CassandraConnector;
import org.investovator.core.data.exeptions.DataAccessException;

import java.io.FileInputStream;
import java.io.InputStreamReader;

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
    public void importCSV(String keyspaceName, String columnFamilyName,
                          FileInputStream fileInputStream) throws DataAccessException {

        try {
            //Since Mutator is not thread safe
            synchronized (this){
                Cluster cluster = getClusterInitialized();
                CassandraConnector.createKeyspaceIfNotExists(cluster, keyspaceName);
                CassandraConnector.createColumnFmlyIfNotExists(cluster, keyspaceName, columnFamilyName);

                Keyspace keyspace =  HFactory.createKeyspace(keyspaceName, cluster);
                ColumnFamilyDefinition columnFamilyDef = HFactory
                        .createColumnFamilyDefinition(keyspaceName, columnFamilyName, ComparatorType.UTF8TYPE);


                CSVReader reader = new CSVReader(new InputStreamReader(fileInputStream, CHAR_ENCODING));

                String[] labelsColumn = reader.readNext();


                Mutator<String> mutator = HFactory.createMutator(keyspace, StringSerializer.get());

                String [] data;
                while ((data = reader.readNext()) != null) {
                    for (int num = 1; num < labelsColumn.length; num ++){
                        mutator.addInsertion(data[0], columnFamilyDef.getName(),
                                HFactory.createStringColumn(labelsColumn[num], data[num]));
                    }
                }

                mutator.execute();
            }
        } catch (Exception e) {
            throw new DataAccessException(e);
        }


    }

    @Override
    public void importXls(String keyspaceName, String columnFamilyName, FileInputStream fileInputStream)
            throws DataAccessException {
        //TODO
    }

    @Override
    public void truncateColumnFamily(String keyspaceName, String columnFamily)  throws DataAccessException{
        Cluster cluster = getClusterInitialized();
        try {
            CassandraConnector.truncateColumnFamily(cluster, keyspaceName, columnFamily);
        } catch (Exception e){
            throw new DataAccessException(e);
        }
    }

    @Override
    public void dropColumnFamily(String keyspaceName, String columnFamilyName) throws DataAccessException {
        Cluster cluster = getClusterInitialized();
        try {
            CassandraConnector.dropColumnFamily(cluster, keyspaceName, columnFamilyName);
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
