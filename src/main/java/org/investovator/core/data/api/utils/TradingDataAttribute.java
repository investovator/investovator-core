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

/**
 * @author rajith
 * @version $Revision$
 */
public enum TradingDataAttribute {

    //for OHLC data
    DAY,
    HIGH_PRICE,
    LOW_PRICE,
    CLOSING_PRICE,
    SHARES,
    TURNOVER,
    TRADES,
    DATE_HIGH,
    DATE_LOW,
    DAYS_TRADED,
    LAST_TRADED,

    //for ticker data
    TIME,
    PRICE;

    /*This should be removed outside this class later*/
    public static String getAttribName(TradingDataAttribute attribute) {

        switch (attribute) {
            case CLOSING_PRICE:
                return "Closing (Rs.)";
            case LOW_PRICE:
                return "Low (Rs.)";
            case HIGH_PRICE:
                return "High (Rs.)";
            case SHARES:
                return "Shares(No.)";
            case TURNOVER:
                return "Turnover(Rs.)";
            case TRADES:
                return "Trades(No.)";
            case DAY:
                return "Day";
            case DATE_HIGH:
                return "Date High";
            case DATE_LOW:
                return "Date Low";
            case DAYS_TRADED:
                return "Days Traded";
            case LAST_TRADED:
                return "Last Traded";

            case PRICE:
                return "Price";
            case TIME:
                return "Time";
        }
        return null;
    }

    public static TradingDataAttribute fromString(String value) {
        if (value != null) {
            for (TradingDataAttribute attribute : TradingDataAttribute.values()) {
                if (value.equalsIgnoreCase(TradingDataAttribute.getAttribName(attribute))) {
                    return attribute;
                }
            }
        }
        return null;
    }
}
