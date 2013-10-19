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
import org.investovator.core.data.api.utils.Constants;
import org.investovator.core.data.api.utils.TradingDataAttribute;
import org.investovator.core.data.cassandraexplorer.utils.CassandraConnector;
import org.investovator.core.data.exeptions.DataAccessException;
import org.investovator.core.data.exeptions.DataNotFoundException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author rajith
 * @version $Revision$
 */
public class TestCassandraManager {

    private static String COLUMNFAMILY = "ohlc_data";
    private static String ROWKEY1 = "SAMP";

    private static String USERNAME = "admin";
    private static String PASSWORD = "admin";
    private static String URL = "localhost:9171";

    private static String RESOURCE_DIR_PATH = "src" + File.separator + "test"
            + File.separator + "resources" + File.separator;
    private static String FILENAME = "sampath_daily_test.csv";

    @Test
    public void testImportCSV() throws DataAccessException, FileNotFoundException {

        CassandraManager cassandraManager = CassandraManagerImpl.getCassandraManager();

        File file = new File(RESOURCE_DIR_PATH + FILENAME);
        cassandraManager
                .importCSV(COLUMNFAMILY, ROWKEY1, Constants.OHLC_DATE_FORMAT, new FileInputStream(file));

        Cluster cluster = CassandraConnector.createCluster(USERNAME, PASSWORD, URL);

        assertTrue(CassandraConnector.isKeyspaceAvailable(cluster, CassandraManager.KEYSPACE));
        assertTrue(CassandraConnector
                .isColumnFamilyAvailable(cluster, CassandraManager.KEYSPACE, COLUMNFAMILY));

        Keyspace keyspace =  HFactory.createKeyspace(CassandraManager.KEYSPACE, cluster);
        ColumnFamilyTemplate<String, String> template =
                new ThriftColumnFamilyTemplate<String, String>(keyspace,COLUMNFAMILY,
                        StringSerializer.get(),StringSerializer.get());

        ColumnFamilyResult<String, String> res = template.queryColumns(ROWKEY1);
        Collection<String> resCollection =res.getColumnNames();

        Assert.assertTrue(resCollection
                .contains(TradingDataAttribute.getAttribName(TradingDataAttribute.HIGH_PRICE)));
    }

    @Test
    public void testTruncateColumnFamily() throws DataAccessException, FileNotFoundException {

        File file = new File(RESOURCE_DIR_PATH + FILENAME);

        CassandraManager cassandraManager = CassandraManagerImpl.getCassandraManager();
        cassandraManager
                .importCSV(COLUMNFAMILY, ROWKEY1, Constants.OHLC_DATE_FORMAT, new FileInputStream(file));

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

        ColumnFamilyResult<String, String> res = template.queryColumns(ROWKEY1);
        assertFalse(res.hasResults());
    }

    @Test
    public void testGetData() throws FileNotFoundException, DataAccessException,
            DataNotFoundException, ParseException {

        File file = new File(RESOURCE_DIR_PATH + FILENAME);
        String ROWKEY2 = "AMPS";

        CassandraManager cassandraManager = CassandraManagerImpl.getCassandraManager();
        cassandraManager.importCSV(COLUMNFAMILY, ROWKEY2, Constants.OHLC_DATE_FORMAT,
                new FileInputStream(file));

        String staringDate = "1/5/2010";
        String endDate = "4/25/2012";

        ArrayList<TradingDataAttribute> attributes = new ArrayList<TradingDataAttribute>();
        attributes.add(TradingDataAttribute.CLOSING_PRICE);

        SimpleDateFormat format = new SimpleDateFormat(Constants.OHLC_DATE_FORMAT);


        HashMap<Date, HashMap<TradingDataAttribute, String>> dataValue =
                cassandraManager.getData(COLUMNFAMILY, ROWKEY2, format.parse(staringDate),
                        format.parse(endDate), 10, attributes);

        assertEquals("220.25", (dataValue.get(
                format.parse(staringDate))).get(TradingDataAttribute.CLOSING_PRICE));
    }

    @Before
    public void setEnvironment() throws InterruptedException, TTransportException,
            org.apache.cassandra.exceptions.ConfigurationException, IOException, ConfigurationException {

        ConfigLoader.loadProperties(RESOURCE_DIR_PATH + "resource.properties");
        EmbeddedCassandraServerHelper.startEmbeddedCassandra();
    }

    @After
    public void destroy(){
        EmbeddedCassandraServerHelper.cleanEmbeddedCassandra();
    }
}
