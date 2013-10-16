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

package org.investovator.core.data.cassandraexplorer.utils;

import me.prettyprint.cassandra.service.CassandraHostConfigurator;
import me.prettyprint.cassandra.service.ThriftCfDef;
import me.prettyprint.cassandra.service.ThriftKsDef;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.ddl.ColumnFamilyDefinition;
import me.prettyprint.hector.api.ddl.KeyspaceDefinition;
import me.prettyprint.hector.api.factory.HFactory;

import java.util.*;

/**
 * @author rajith
 * @version $Revision$
 */
public class CassandraConnector {

    public static final String CLUSTER = "investovator";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    /**
     * Creates a Cluster
     * @param username User name to be used to authenticate to Cassandra cluster
     * @param password Password to be used to authenticate to Cassandra cluster
     * @param url Url Connection url
     * @return <code>Cluster</code> instance
     */
    public static Cluster createCluster(String username, String password, String url) {
        Map<String, String> credentials = new HashMap<String, String>();
        credentials.put(USERNAME, username);
        credentials.put(PASSWORD, password);
        return HFactory.createCluster(CLUSTER, new CassandraHostConfigurator(url), credentials);
    }

    /**
     *
     * @param cluster cluster
     * @param keyspace keyspace
     */
    public static synchronized void createKeyspaceIfNotExists(Cluster cluster, String keyspace){
        if(!isKeyspaceAvailable(cluster, keyspace)){
            KeyspaceDefinition definition = new ThriftKsDef(keyspace);
            cluster.addKeyspace(definition, true);
        }
    }

    /**
     *
     * @param cluster cluster
     * @param keyspace keyspace
     * @param columnFamily columnFamily required
     */
    public static synchronized void createColumnFmlyIfNotExists(Cluster cluster,String keyspace,
                                                   String columnFamily){
        if(!isColumnFamilyAvailable(cluster, keyspace, columnFamily)){
            ColumnFamilyDefinition familyDefinition = new ThriftCfDef(keyspace, columnFamily);
            cluster.addColumnFamily(familyDefinition, true);
        }
    }

    /**
     * Checks availability of a key space
     * @param cluster Cluster
     * @param keyspace Keyspace name
     * @return True if the Keyspace is available
     */
    public static synchronized boolean isKeyspaceAvailable(Cluster cluster, String keyspace) {
        return cluster.describeKeyspace(keyspace)!= null;
    }

    /**
     * Checks availability of a column family
     * @param cluster Cluster
     * @param keyspace Keyspace name
     * @param columnFamily Column family name
     * @return True if the column family is available
     */
    public static synchronized boolean isColumnFamilyAvailable(Cluster cluster, String keyspace,
                                                  String columnFamily){
        if(isKeyspaceAvailable(cluster, keyspace)){
            Iterator<ColumnFamilyDefinition> columnFamilyItr;
            columnFamilyItr = cluster.describeKeyspace(keyspace).getCfDefs().iterator();
            ArrayList<String> cfNames = new ArrayList<String>();
            while (columnFamilyItr.hasNext()) {
                cfNames.add(columnFamilyItr.next().getName());
            }
            return cfNames.contains(columnFamily);
        }
        else return false;
    }

}
