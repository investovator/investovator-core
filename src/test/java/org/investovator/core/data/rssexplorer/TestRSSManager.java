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
import org.investovator.core.commons.configuration.ConfigLoader;
import org.investovator.core.data.exeptions.DataAccessException;
import org.investovator.core.data.rssexplorer.utils.MysqlConstants;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertFalse;
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

    @Test
    public void testAddDataToWatchListNRead() throws DataAccessException {
        RSSManager manager = new RSSManagerImpl();
        manager.addToWatchList("arun", "GOOG");
        manager.addToWatchList("arun", "IBM");
        manager.addToWatchList("arun", "YHO");

        ArrayList<String> ids = manager.getWatchList("arun");
        assertTrue(ids.contains("GOOG"));
        assertTrue(ids.contains("IBM"));
        assertTrue(ids.contains("YHO"));
    }

    @Test
    public void testAddDataToWatchListDeleteNRead() throws DataAccessException {
        RSSManager manager = new RSSManagerImpl();
        manager.addToWatchList("arun", "GOOG");
        manager.addToWatchList("arun", "IBM");
        manager.addToWatchList("arun", "YHO");

        manager.deleteFromWatchList("arun","IBM");

        ArrayList<String> ids = manager.getWatchList("arun");
        assertTrue(ids.contains("GOOG"));
        assertFalse(ids.contains("IBM"));
        assertTrue(ids.contains("YHO"));
    }

    @Test
    public void testUpdatePortfolioAndAssert() throws DataAccessException {
        RSSManager manager = new RSSManagerImpl();
        HashMap<String, HashMap<String, Double>> portfolio = new HashMap<String, HashMap<String, Double>>();
        HashMap<String, Double> values = new HashMap<String, Double>();

        values.put(MysqlConstants.QNTY, 1224.0);
        values.put(MysqlConstants.PRICE, 25.0);
        portfolio.put("GOOG", values);

        values = new HashMap<String, Double>();

        values.put(MysqlConstants.QNTY, 1243.0);
        values.put(MysqlConstants.PRICE, 23.0);
        portfolio.put("YHO", values);

        manager.updateUserPortfolio("Saman", portfolio);

        portfolio = manager.getUserPortfolio("Saman");

        assertTrue((portfolio.get("GOOG")).get(MysqlConstants.PRICE) == 25.0);
        assertTrue((portfolio.get("GOOG")).get(MysqlConstants.QNTY) == 1224.0);
        assertTrue((portfolio.get("YHO")).get(MysqlConstants.PRICE) == 23.0);
        assertTrue((portfolio.get("YHO")).get(MysqlConstants.QNTY) == 1243.0);
    }

    @Test
    public void testDeletePortfolioAndAssert() throws DataAccessException {
        RSSManager manager = new RSSManagerImpl();
        HashMap<String, HashMap<String, Double>> portfolio = new HashMap<String, HashMap<String, Double>>();
        HashMap<String, Double> values = new HashMap<String, Double>();

        values.put(MysqlConstants.QNTY, 1224.0);
        values.put(MysqlConstants.PRICE, 25.0);
        portfolio.put("GOOG", values);

        values = new HashMap<String, Double>();

        values.put(MysqlConstants.QNTY, 1243.0);
        values.put(MysqlConstants.PRICE, 23.0);
        portfolio.put("YHO", values);

        manager.updateUserPortfolio("Saman", portfolio);
        manager.deleteUserPortfolio("Saman");

        try {
            manager.getUserPortfolio("Saman");
        } catch (DataAccessException e){
            if(e.getMessage().contains("Table 'investovator_data.SAMAN_PORTFOLIO' doesn't exist")){
                assertTrue(true);
            } else
                throw new DataAccessException(e);
        }
    }

    @Test
    public void addPortfolioValuesAndAssert() throws DataAccessException {
        RSSManager manager = new RSSManagerImpl();
        manager.updatePortfolioValue("saman", 1342.3, 123.5);
        manager.updatePortfolioValue("arun", 12425.2, 234.6);

        assertTrue((manager.getPortfolioValue("saman")).get(MysqlConstants.VALUE)==1342.3);
        assertTrue((manager.getPortfolioValue("saman")).get(MysqlConstants.BLOCKED_VALUE)==123.5);

        HashMap<String, HashMap<String, Double>> portfolioValues = manager.getAllPortfolioValues();
        assertTrue((portfolioValues.get("saman")).get(MysqlConstants.VALUE)==1342.3);
        assertTrue((portfolioValues.get("arun")).get(MysqlConstants.VALUE)==12425.2);

        manager.deletePortfolioValue("arun");
        portfolioValues = manager.getAllPortfolioValues();
        assertFalse(portfolioValues.containsKey("arun"));
    }

    @Before
    public void setEnvironment() throws ConfigurationException {
        ConfigLoader.loadProperties(RESOURCE_DIR_PATH + "resource.properties");
    }
}
