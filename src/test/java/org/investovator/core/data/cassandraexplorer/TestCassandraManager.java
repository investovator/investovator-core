package org.investovator.core.data.cassandraexplorer;

import junit.framework.TestCase;
import org.apache.commons.configuration.ConfigurationException;
import org.investovator.core.configuration.ConfigLoader;
import org.investovator.core.data.exeptions.DataAccessException;

import java.io.File;

/**
 * @author rajith
 * @version $Revision$
 */
public class TestCassandraManager extends TestCase {

    public void testImportCSV() throws DataAccessException {

        try {
            ConfigLoader.loadProperties("src" + File.separator + "test" + File.separator
                    + "resources" + File.separator + "resource.properties");
        } catch (ConfigurationException e) {
            assertFalse(e.getMessage(), true);
        }

        CassandraManager cassandraManager = new CassandraManagerImpl();

        File file = new File("src" + File.separator + "test" + File.separator
                + "resources" + File.separator + "sampath_daily_test.csv");

        //cassandraManager.importCSV("SAMP", file);



    }
}
