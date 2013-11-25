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

import org.investovator.core.commons.utils.Portfolio;
import org.investovator.core.commons.utils.PortfolioImpl;
import org.investovator.core.commons.utils.Terms;
import org.investovator.core.data.exeptions.DataAccessException;
import org.investovator.core.data.rssexplorer.RSSManager;
import org.investovator.core.data.rssexplorer.RSSManagerImpl;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author rajith
 * @version $Revision$
 */
public class UserDataImpl implements UserData{

    private RSSManager manager;

    public UserDataImpl() throws DataAccessException {
        manager = new RSSManagerImpl();
    }

    @Override
    public Portfolio getUserPortfolio(String gameInstanceName, String username) throws DataAccessException {
        HashMap <String, HashMap<String, Double>> shares = manager.getUserPortfolio(gameInstanceName, username);
        HashMap <String, Double> values = manager.getPortfolioValue(gameInstanceName, username);
        return new PortfolioImpl(username, values.get(Terms.VALUE), values.get(Terms.BLOCKED_VALUE), shares);
    }

    @Override
    public void updateUserPortfolio(String gameInstanceName, String username, Portfolio portfolio)
            throws DataAccessException {
        manager.updatePortfolioValue(gameInstanceName, username, portfolio.getCashBalance(), portfolio.getBlockedCash());
        manager.updateUserPortfolio(gameInstanceName, username, portfolio.getShares());
    }

    @Override
    public ArrayList<String> getWatchList(String gameInstanceName, String username) throws DataAccessException {
        return manager.getWatchList(gameInstanceName, username);
    }

    @Override
    public void addToWatchList(String gameInstanceName, String username, String symbol) throws DataAccessException {
        manager.addToWatchList(gameInstanceName, username, symbol);
    }

    @Override
    public void deleteFromWatchList(String gameInstanceName, String username, String symbol) throws DataAccessException {
        manager.deleteFromWatchList(gameInstanceName, username, symbol);
    }

    @Override
    public void updateWatchList(String gameInstanceName, String username, ArrayList<String> watchList)
            throws DataAccessException {
        ArrayList<String> previousList = manager.getWatchList(gameInstanceName, username);

        for (String id : watchList){
            if (!previousList.contains(id)){
                 manager.addToWatchList(gameInstanceName, username, id);
            }
        }

        for (String id: previousList){
            if (!watchList.contains(id)){
                manager.deleteFromWatchList(gameInstanceName, username, id);
            }
        }
    }

    @Override
    public void clearUserDataOnGameInstance(String gameInstanceName) throws DataAccessException {
        manager.dropGameInstanceTables(gameInstanceName);
    }

}
