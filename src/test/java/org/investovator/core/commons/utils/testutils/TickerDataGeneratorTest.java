package org.investovator.core.commons.utils.testutils;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.thrift.transport.TTransportException;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.investovator.core.commons.configuration.ConfigLoader;
import org.investovator.core.data.api.CompanyStockTransactionsData;
import org.investovator.core.data.api.CompanyStockTransactionsDataImpl;
import org.investovator.core.data.api.utils.StockTradingData;
import org.investovator.core.data.api.utils.StockTradingDataImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author Amila Surendra
 * @version $Revision
 */
public class TickerDataGeneratorTest {

    private static String COLUMNFAMILY = "ohlc_data";
    private static String ROWKEY1 = "SAMP";

    private static String USERNAME = "admin";
    private static String PASSWORD = "admin";
    private static String URL = "localhost:9171";

    private static String RESOURCE_DIR_PATH = "src" + File.separator + "test"
            + File.separator + "resources" + File.separator;
    private static String FILENAME = "SAMP.csv";


    @Test
    public void testCreateCSV() throws Exception {

        CompanyStockTransactionsData stockData = new CompanyStockTransactionsDataImpl();

        File file = new File(RESOURCE_DIR_PATH + FILENAME);

        stockData.importCSV(CompanyStockTransactionsData.DataType.OHLC, "SAMP", "MM/dd/yyyy",file);

        TickerDataGenerator tickGenerator = new TickerDataGenerator("SAMP");
        tickGenerator.CreateCSV();

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
