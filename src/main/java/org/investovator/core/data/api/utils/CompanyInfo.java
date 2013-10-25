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
public enum CompanyInfo {

    DIVIDEND,
    DEBT;


    /*This should be removed outside this class later*/
    public static String getAttribName(CompanyInfo companyInfo) {

        switch (companyInfo) {
            case DIVIDEND:
                return "Dividend";
            case DEBT:
                return "Debt";
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
