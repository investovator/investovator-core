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

import java.util.HashMap;

/**
 * @author rajith
 * @version $Revision$
 */
public class CompanyData {

    /**
     *
     * @return Company StockId and Name pairs
     */
    public HashMap<String,String> getCompanyIDsNames(){
        return null; //TODO
    }

    /**
     *
     * @return Company StockId and number of shares issued pairs
     */
    public HashMap<String, Integer> getCompanyIDsTotalShares(){
        return null; //TODO
    }

    /**
     *
     * @param infotype Information type required
     * @param stockId StockId of the company
     * @return Data
     */
    public Object getInfo(String infotype, String stockId){
        return null; //TODO
    }

}
