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

import org.investovator.core.data.api.utils.StockTradingData;
import org.investovator.core.data.api.utils.TradingDataAttribute;
import org.investovator.core.data.exeptions.DataAccessException;

import java.util.Date;

/**
 * @author rajith
 * @version $Revision$
 */
public class CompanyStockTransactionsDataImpl implements CompanyStockTransactionsData{

    /**
     *
     * {@inheritDoc}
     */
    public StockTradingData getTradingDataOHLC (String symbol,  Date startingDate,
                                                TradingDataAttribute[] attributes,
                                                   int numOfRows) throws DataAccessException{

        return null; //TODO
    }

    /**
     *
     * {@inheritDoc}
     */
    public StockTradingData getTradingData (String symbol, Date startingTime,
                                            int numOfRows) throws DataAccessException{
        return null; //TODO
    }
}
