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
import org.investovator.core.data.exeptions.DataAccessException;

import java.util.ArrayList;

/**
 * @author rajith
 * @version $Revision$
 */
public interface UserData {

    public Portfolio getUserPortfolio(String gameInstanceName, String username) throws DataAccessException;

    public void updateUserPortfolio(String gameInstanceName, String username, Portfolio portfolio)
            throws DataAccessException;

    public ArrayList<String> getWatchList(String gameInstanceName, String username) throws DataAccessException;

    public void addToWatchList(String gameInstanceName, String username, String symbol) throws DataAccessException;

    public void deleteFromWatchList(String gameInstanceName, String username, String symbol) throws DataAccessException;

    public void updateWatchList(String gameInstanceName, String username, ArrayList<String> watchList)
            throws DataAccessException;

    /**
     * Add user to a particular game instance.
     *     IMPORTANT : when an user joins a game, add the user to a game instance;
     *     then update user portfolio even without buying any stocks.
     * @param gameInstanceName game instance name
     * @param username username
     * @throws DataAccessException
     */
    public void addUserToGameInstance(String gameInstanceName, String username) throws DataAccessException;

    public ArrayList<String> getUserJoinedGameInstances(String username) throws DataAccessException;

    public ArrayList<String> getGameInstanceUsers(String gameInstanceName) throws DataAccessException;

    public void clearUserDataOnGameInstance(String gameInstanceName) throws DataAccessException;
}
