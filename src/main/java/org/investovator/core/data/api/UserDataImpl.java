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
import org.investovator.core.data.rssexplorer.RSSManager;
import org.investovator.core.data.rssexplorer.RSSManagerImpl;

import java.util.ArrayList;

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
    public Portfolio getUserPortfolio(String username) throws DataAccessException {
        return null; //TODO
    }

    @Override
    public void updateUserPortfolio(String username, Portfolio portfolio) throws DataAccessException {
        //TODO
    }

    @Override
    public ArrayList<String> getWatchList(String username) throws DataAccessException {
        return manager.getWatchList(username);
    }

    @Override
    public void addToWatchList(String username, String symbol) throws DataAccessException {
        manager.addToWatchList(username, symbol);
    }

    @Override
    public void deleteFromWatchList(String username, String symbol) throws DataAccessException {
        manager.deleteFromWatchList(username, symbol);
    }

    @Override
    public void updateWatchList(String username, ArrayList<String> watchList) throws DataAccessException {
        ArrayList<String> previousList = manager.getWatchList(username);

        for (String id : watchList){
            if (!previousList.contains(id)){
                 manager.addToWatchList(username, id);
            }
        }

        for (String id: previousList){
            if (!watchList.contains(id)){
                manager.deleteFromWatchList(username, id);
            }
        }
    }

}
