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

package org.investovator.core.data.api;

import org.investovator.core.commons.configuration.ConfigLoader;
import org.investovator.core.commons.utils.Portfolio;
import org.investovator.core.commons.utils.PortfolioImpl;
import org.investovator.core.data.exeptions.DataAccessException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

/**
 * @author rajith
 * @version ${Revision}
 */
public class TestUserDataImpl {

    private static String RESOURCE_DIR_PATH = "src" + File.separator + "test"
            + File.separator + "resources" + File.separator;

    @Test
    public void testUserDataPersistence() throws DataAccessException {
        Portfolio newPortfolio = new PortfolioImpl("testUser1",1000000,0);
        UserData userData = new UserDataImpl();
        userData.updateUserPortfolio("testUser1", newPortfolio);

        Portfolio portfolio =  userData.getUserPortfolio("testUser1");
        portfolio.boughtShares("GOOG", 1, 1000);
        userData.updateUserPortfolio("testUser1", portfolio);
        portfolio =  userData.getUserPortfolio("testUser1");
        portfolio.soldShares("GOOG", 1, 1000);
        assert(portfolio.getShares().size()==0);

        userData.updateUserPortfolio("testUser1", portfolio);
        portfolio =  userData.getUserPortfolio("testUser1");
        assert(portfolio.getShares().size()==0);
    }

    @Before
    public void setUp() throws Exception {
      ConfigLoader.loadProperties(RESOURCE_DIR_PATH + "resource.properties");
    }

    @After
    public void tearDown() throws Exception {
        new DataStorageImpl().resetRSS();
    }
}
