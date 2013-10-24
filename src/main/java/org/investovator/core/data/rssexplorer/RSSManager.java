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

import org.investovator.core.data.exeptions.DataAccessException;

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

    public Object getInfo(String infoType, String symbol) throws DataAccessException;

    public void addInfo(String infoType, String symbol, Object object) throws DataAccessException;

    public ArrayList<String> getAvailableStockIds() throws DataAccessException;

    public HashMap<String, HashMap<String, Double>> getUserPortfolio(String username) throws DataAccessException;

    public void updateUserPortfolio(String username, HashMap<String, HashMap<String, Double>> portfolio)
            throws DataAccessException;

    public void deleteUserPortfolio(String username) throws DataAccessException;

    public void updatePortfolioValue(String username, double value, double blockedValue) throws DataAccessException;

    public HashMap<String, Double> getPortfolioValue(String username) throws DataAccessException;

    public HashMap<String, HashMap<String, Double>> getAllPortfolioValues() throws DataAccessException;

    public void deletePortfolioValue(String username) throws DataAccessException;

    public ArrayList<String> getWatchList(String username) throws DataAccessException;

    public void addToWatchList(String username, String symbol) throws DataAccessException;

    public void deleteFromWatchList(String username, String symbol) throws DataAccessException;
}
