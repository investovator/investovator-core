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


package org.investovator.core.commons.utils;

import java.util.HashMap;

/**
 * @author rajith
 * @version $Revision$
 */
public interface Portfolio extends Comparable <Portfolio> {

    public double getCashBalance();

    public void setCashBalance(double cashBalance);

    public void setBlockedCash(double amount);

    public double getBlockedCash();

    public HashMap<String, HashMap<String, Double>> getShares();

    public void setShares(HashMap<String, HashMap<String, Double>> shares);

    public void removeStock(String symbol);

    public String getUsername();

    /**
     *
     * @param symbol symbol of the stock
     * @param quantity quantity bought
     */
    public void boughtShares(String symbol, double quantity, double price);

    /**
     *
     * @param symbol symbol of the stock
     * @param quantity quantity sold
     */
    public void soldShares(String symbol, double quantity, double price);
}
