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

import junit.framework.Assert;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.service.template.ColumnFamilyResult;
import me.prettyprint.cassandra.service.template.ColumnFamilyTemplate;
import me.prettyprint.cassandra.service.template.ThriftColumnFamilyTemplate;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.factory.HFactory;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.thrift.transport.TTransportException;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.investovator.core.configuration.ConfigLoader;
import org.investovator.core.data.api.utils.TradingDataAttribute;
import org.investovator.core.data.cassandraexplorer.utils.CassandraConnector;
import org.investovator.core.data.exeptions.DataAccessException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * @author rajith
 * @version $Revision$
 */
public class TestCassandraManager {

    private static String COLUMNFAMILY = "SAMP";
    private static String USERNAME = "admin";
    private static String PASSWORD = "admin";
    private static String URL = "localhost:9171";

    @Test
    public void testImportCSV() throws DataAccessException, FileNotFoundException {

        CassandraManager cassandraManager = CassandraManagerImpl.getCassandraManager();

        File file = new File("src" + File.separator + "test" + File.separator + "resources"
                + File.separator + "sampath_daily_test.csv");
        cassandraManager.importCSV(COLUMNFAMILY, new FileInputStream(file));

        Cluster cluster = CassandraConnector.createCluster(USERNAME, PASSWORD, URL);

        assertTrue(CassandraConnector.isKeyspaceAvailable(cluster, CassandraManager.KEYSPACE));
        assertTrue(CassandraConnector.isColumnFamilyAvailable(cluster, CassandraManager.KEYSPACE, COLUMNFAMILY));

        Keyspace keyspace =  HFactory.createKeyspace(CassandraManager.KEYSPACE, cluster);
        ColumnFamilyTemplate<String, String> template =
                new ThriftColumnFamilyTemplate<String, String> (keyspace,COLUMNFAMILY,
                        StringSerializer.get(),StringSerializer.get());

        ColumnFamilyResult<String, String> res = template.queryColumns("1/6/2010");
        Collection<String> resCollection =res.getColumnNames();

        Assert.assertTrue(resCollection
                .contains(TradingDataAttribute.getAttribName(TradingDataAttribute.HIGH_PRICE)));
    }

    @Test
    public void testDropColumnFamily() throws DataAccessException, FileNotFoundException {

        File file = new File("src" + File.separator + "test" + File.separator + "resources"
                + File.separator + "sampath_daily_test.csv");

        CassandraManager cassandraManager = CassandraManagerImpl.getCassandraManager();
        cassandraManager.importCSV(COLUMNFAMILY, new FileInputStream(file));

        Cluster cluster = CassandraConnector.createCluster(USERNAME, PASSWORD, URL);

        assertTrue(CassandraConnector.isKeyspaceAvailable(cluster, CassandraManager.KEYSPACE));
        assertTrue(CassandraConnector
                .isColumnFamilyAvailable(cluster, CassandraManager.KEYSPACE, COLUMNFAMILY));

        cassandraManager.truncateColumnFamily(COLUMNFAMILY);
        assertTrue(CassandraConnector
                .isColumnFamilyAvailable(cluster, CassandraManager.KEYSPACE, COLUMNFAMILY));

        Keyspace keyspace =  HFactory.createKeyspace(CassandraManager.KEYSPACE, cluster);
        ColumnFamilyTemplate<String, String> template =
                new ThriftColumnFamilyTemplate<String, String> (keyspace,COLUMNFAMILY,
                        StringSerializer.get(),StringSerializer.get());

        ColumnFamilyResult<String, String> res = template.queryColumns("1/6/2010");
        assertFalse(res.hasResults());
    }

    @Before
    public void start() throws InterruptedException, TTransportException,
            org.apache.cassandra.exceptions.ConfigurationException, IOException, ConfigurationException {

        ConfigLoader.loadProperties("src" + File.separator + "test" + File.separator
                + "resources" + File.separator + "resource.properties");
        EmbeddedCassandraServerHelper.startEmbeddedCassandra();
    }

    @After
    public void stop(){
        EmbeddedCassandraServerHelper.cleanEmbeddedCassandra();
    }
}
