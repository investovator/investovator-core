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

import org.investovator.core.data.exeptions.DataAccessException;

import java.util.HashMap;

/**
 * @author rajith
 * @version $Revision$
 */
public class CompanyDataimpl implements CompanyData{

    /**
     *
     * {@inheritDoc}
     */
    public HashMap<String,String> getCompanyIDsNames() throws DataAccessException{
        return null; //TODO
    }

    /**
     *
     * {@inheritDoc}
     */
    public HashMap<String, Integer> getCompanyIDsTotalShares() throws DataAccessException{
        return null; //TODO
    }

    /**
     *
     * {@inheritDoc}
     */
    public String getCompanyName(String symbol) throws DataAccessException{
        return null; //TODO
    }

    /**
     *
     * {@inheritDoc}
     */
    public int getCompanyNoOfShares(String symbol) throws DataAccessException{
        return 0; //TODO
    }

    /**
     *
     * {@inheritDoc}
     */
    public Object getInfo(String infotype, String symbol) throws DataAccessException{
        return null; //TODO
    }


    //TODO data adding api
}
