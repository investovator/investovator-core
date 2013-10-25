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

import org.investovator.core.data.api.utils.CompanyInfo;
import org.investovator.core.data.exeptions.DataAccessException;
import org.investovator.core.data.exeptions.DataNotFoundException;
import org.investovator.core.data.rssexplorer.RSSManager;
import org.investovator.core.data.rssexplorer.RSSManagerImpl;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author rajith
 * @version $Revision$
 */
public class CompanyDataImpl implements CompanyData{

    private RSSManager manager;

    public CompanyDataImpl() throws DataAccessException {
        manager = new RSSManagerImpl();
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public HashMap<String,String> getCompanyIDsNames() throws DataAccessException{
        return manager.getCompanyIDsNames();
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public void addCompanyData(String symbol, String companyName, int shares)
            throws DataAccessException {
        manager.addCompanyData(symbol, companyName, shares);
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public HashMap<String, Integer> getCompanyIDsTotalShares() throws DataAccessException{
        return manager.getCompanyIDsTotalShares();
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public String getCompanyName(String symbol) throws DataAccessException{
        return manager.getCompanyName(symbol);
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public int getCompanyNoOfShares(String symbol) throws DataAccessException{
        return manager.getCompanyNoOfShares(symbol);
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public String getInfo(CompanyInfo infoType, String symbol) throws DataAccessException, DataNotFoundException {
        return manager.getInfo(infoType, symbol);
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public void addInfo(CompanyInfo infoType, String symbol, String value) throws DataAccessException {
        manager.addInfo(infoType, symbol, value);
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public ArrayList<String> getAvailableStockIds() throws DataAccessException {
        return manager.getAvailableStockIds();
    }

}
