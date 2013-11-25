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

import org.investovator.core.data.api.utils.CompanyInfo;
import org.investovator.core.data.exeptions.DataAccessException;
import org.investovator.core.data.exeptions.DataNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author rajith
 * @version $Revision$
 */
public interface RSSManager {

    public HashMap<String,String> getCompanyIDsNames() throws DataAccessException;

    public void addCompanyData(String symbol, String companyName, int shares) throws DataAccessException;

    public HashMap<String, Integer> getCompanyIDsTotalShares() throws DataAccessException;

    public String getCompanyName(String symbol) throws DataAccessException;

    public int getCompanyNoOfShares(String symbol) throws DataAccessException;

    public String getInfo(CompanyInfo infoType, String symbol) throws DataAccessException, DataNotFoundException;

    public void addInfo(CompanyInfo infoType, String symbol, String value) throws DataAccessException;

    public ArrayList<String> getAvailableStockIds() throws DataAccessException;

    public HashMap<String, HashMap<String, Double>> getUserPortfolio(String gameInstanceName, String username)
            throws DataAccessException;

    public void updateUserPortfolio(String gameInstanceName, String username, HashMap<String,
            HashMap<String, Double>> portfolio) throws DataAccessException;

    public void deleteUserPortfolio(String gameInstanceName, String username) throws DataAccessException;

    public void updatePortfolioValue(String gameInstanceName, String username, double value, double blockedValue)
            throws DataAccessException;

    public HashMap<String, Double> getPortfolioValue(String gameInstanceName, String username)
            throws DataAccessException;

    public HashMap<String, HashMap<String, Double>> getAllPortfolioValues(String gameInstanceName)
            throws DataAccessException;

    public void deletePortfolioValue(String gameInstanceName, String username) throws DataAccessException;

    public ArrayList<String> getWatchList(String gameInstanceName, String username) throws DataAccessException;

    public void addToWatchList(String gameInstanceName, String username, String symbol) throws DataAccessException;

    public void deleteFromWatchList(String gameInstanceName, String username, String symbol)
            throws DataAccessException;

    public void dropGameInstanceTables(String gameInstanceName) throws DataAccessException;

    public void resetDatabase() throws DataAccessException;
}
