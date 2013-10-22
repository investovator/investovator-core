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

package org.investovator.core.data.api.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

/**
 * @author rajith
 * @version $Revision$
 */
public class StockTradingDataImpl implements StockTradingData{

    private String stockId;
    private ArrayList<TradingDataAttribute> attributes;
    private HashMap<Date, HashMap<TradingDataAttribute, String>> marketData;

    public StockTradingDataImpl(String stockId, ArrayList<TradingDataAttribute> attributes,
                       HashMap<Date, HashMap<TradingDataAttribute, String>> marketData) {
        this.stockId = stockId;
        this.attributes = attributes;
        this.marketData = marketData;
    }

    @Override
    public ArrayList<TradingDataAttribute> getAttributes() {
        return attributes;
    }

    @Override
    public HashMap<Date, HashMap<TradingDataAttribute, String>> getTradingData() {
        return marketData;
    }

    @Override
    public HashMap<TradingDataAttribute, String> getTradingDataEntry(Date date){
        return marketData.get(date);
    }

    @Override
    public Set<Date> getDates(){
        return marketData.keySet();
    }

    @Override
    public String getTradingDataAttributeValue(Date date, TradingDataAttribute attribute){
        return marketData.get(date).get(attribute);
    }

    @Override
    public String getStockId() {
        return stockId;
    }
}
