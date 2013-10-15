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
