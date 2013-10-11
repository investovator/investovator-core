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

import java.util.Date;
import java.util.HashMap;
import java.util.Set;

/**
 * @author rajith
 * @version $Revision$
 */
public class StockTradingData {

    private TradingDataAttribute[] attributes;
    private HashMap<Date, HashMap<TradingDataAttribute, Float>> marketData;

    public StockTradingData(TradingDataAttribute[] attributes,
                       HashMap<Date, HashMap<TradingDataAttribute, Float>> marketData) {
        this.attributes = attributes;
        this.marketData = marketData;
    }

    public TradingDataAttribute[] getAttributes() {
        return attributes;
    }

    public HashMap<Date, HashMap<TradingDataAttribute, Float>> getMarketData() {
        return marketData;
    }

    public HashMap<TradingDataAttribute, Float> getDayTransactionEntry(Date date){
        return marketData.get(date);
    }

    public Set<Date> getDates(){
        return marketData.keySet();
    }

    public Float getAttributeValue(Date date, TradingDataAttribute attribute){
        return marketData.get(date).get(attribute);
    }
}
