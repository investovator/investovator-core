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

package org.investovator.core.data.rssexplorer;

import org.apache.commons.configuration.ConfigurationException;
import org.investovator.core.configuration.ConfigLoader;
import org.investovator.core.data.exeptions.DataAccessException;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertTrue;

/**
 * @author rajith
 * @version $Revision$
 */
public class TestRSSManager {

    private static String RESOURCE_DIR_PATH = "src" + File.separator + "test"
            + File.separator + "resources" + File.separator;

    @Test
    public void testAddDataNReadNames() throws DataAccessException {
        RSSManager manager = new RSSManagerImpl();
        manager.addCompanyData("GOOG", "Google", 169000);
        manager.addCompanyData("IBM", "IBM Company", 123456);
        manager.addCompanyData("YHO", "YAHOO", 142566);

        HashMap<String, String> idNames = manager.getCompanyIDsNames();
        assertTrue(idNames.containsKey("GOOG"));
        assertTrue(idNames.containsKey("IBM"));
        assertTrue((idNames.get("YHO")).equals("YAHOO"));
    }

    @Test
    public void testAddDataNReadShares() throws DataAccessException {
        RSSManager manager = new RSSManagerImpl();
        manager.addCompanyData("GOOG", "Google", 2300);
        manager.addCompanyData("IBM", "IBM Company", 1244);
        manager.addCompanyData("YHO", "YAHOO", 1265);

        HashMap<String, Integer> idNames = manager.getCompanyIDsTotalShares();
        assertTrue((idNames.get("YHO")).equals(1265));
        assertTrue((idNames.get("GOOG")).equals(2300));
    }

    @Test
    public void testAddDataNReadComName() throws DataAccessException {
        RSSManager manager = new RSSManagerImpl();
        manager.addCompanyData("GOOG", "Google Inc", 2300221);

        assertTrue(manager.getCompanyName("GOOG").equals("Google Inc"));
    }

    @Test
    public void testAddDataNReadComShares() throws DataAccessException {
        RSSManager manager = new RSSManagerImpl();
        manager.addCompanyData("YHO", "Yahoo Inc", 2210221);

        assertTrue(manager.getCompanyNoOfShares("YHO")==(2210221));
    }

    @Test
    public void testAddDataNReadAllIds() throws DataAccessException {
        RSSManager manager = new RSSManagerImpl();
        manager.addCompanyData("GOOG", "Google Inc", 169000);
        manager.addCompanyData("IBM", "IBM Inc", 123456);
        manager.addCompanyData("YHO", "Yahoo Inc", 142566);

        ArrayList<String> ids = manager.getAvailableStockIds();
        assertTrue(ids.contains("GOOG"));
        assertTrue(ids.contains("IBM"));
        assertTrue(ids.contains("YHO"));
    }

    @Before
    public void setEnvironment() throws ConfigurationException {
        ConfigLoader.loadProperties(RESOURCE_DIR_PATH + "resource.properties");
    }
}
