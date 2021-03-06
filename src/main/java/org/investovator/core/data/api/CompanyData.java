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

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author rajith
 * @version $Revision$
 */
public interface CompanyData {

    /**
     *
     * @return Company StockId and Name pairs
     * @throws DataAccessException
     */
    public HashMap<String,String> getCompanyIDsNames() throws DataAccessException;

    /**
     *
     * @param symbol Company stockId
     * @param companyName Company name
     * @param shares number of shares issued
     * @throws DataAccessException
     */
    public void addCompanyData(String symbol, String companyName, int shares)
            throws DataAccessException;

    /**
     *
     * @return Company StockId and number of shares issued pairs
     * @throws DataAccessException
     */
    public HashMap<String, Integer> getCompanyIDsTotalShares() throws DataAccessException;

    /**
     *
     * @param symbol Company StockId
     * @return Company Name
     * @throws DataAccessException
     */
    public String getCompanyName(String symbol) throws DataAccessException;

    /**
     *
     * @param symbol Company StockId
     * @return Number of shares issued by the company
     * @throws DataAccessException
     */
    public int getCompanyNoOfShares(String symbol) throws DataAccessException;

    /**
     *
     * @param infoType Information type required
     * @param symbol StockId of the company
     * @return Data
     * @throws DataAccessException
     * @throws DataNotFoundException
     */
    public String getInfo(CompanyInfo infoType, String symbol) throws DataAccessException, DataNotFoundException;

    /**
     *
     * @param infoType Information type ex: annual dividend
     * @param symbol stockId of the company
     * @param value data
     * @throws DataAccessException
     */
    public void addInfo(CompanyInfo infoType, String symbol, String value) throws DataAccessException;

    /**
     *
     * @return all the stock symbols
     * @throws DataAccessException
     */
    public ArrayList<String> getAvailableStockIds() throws DataAccessException;
}
