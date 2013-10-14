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
public class PortfolioImpl implements Terms, Portfolio {

    private String username;
    private int cashBalance;
    private HashMap <String, HashMap <String, Float>> shares;

    public PortfolioImpl(String username, int cashBalance){
        this.username = username;
        this.cashBalance = cashBalance;
        this.shares = new HashMap<String, HashMap<String, Float>>();
    }

    public PortfolioImpl(String username, int cashBalance,
                     HashMap <String, HashMap <String, Float>> shares){
        this.username = username;
        this.cashBalance = cashBalance;
        this.shares = shares;
    }

    @Override
    public int compareTo(Portfolio comparePortfolio) {
        return this.cashBalance - comparePortfolio.getCashBalance();
    }

    @Override
    public int getCashBalance() {
        return cashBalance;
    }

    @Override
    public void setCashBalance(int cashBalance) {
        this.cashBalance = cashBalance;
    }

    @Override
    public HashMap<String, HashMap<String, Float>> getShares() {
        return shares;
    }

    @Override
    public void setShares(HashMap<String, HashMap<String, Float>> shares) {
        this.shares = shares;
    }

    @Override
    public void removeStock(String symbol){
        shares.remove(symbol);
    }

    @Override
    public String getUsername(){
        return username;
    }

    /**
     *
     * {@inheritDoc}
     */
    public void boughtShares(String symbol, float quantity, float price){
        if (shares.containsKey(symbol)){
            HashMap<String, Float> stockData = shares.get(symbol);
            stockData.put(QNTY, stockData.get(QNTY) + quantity);
            stockData.put(PRICE, price);
        } else {
            HashMap<String, Float> stockData = new HashMap<String, Float>();
            stockData.put(QNTY, quantity);
            stockData.put(PRICE, price);
            shares.put(symbol, stockData);
        }
    }

    /**
     *
     * {@inheritDoc}
     */
    public void soldShares(String symbol, float quantity){
        if (shares.get(symbol).get(QNTY) == quantity){
            removeStock(symbol);
        } else {
            HashMap<String, Float> stockData = shares.get(symbol);
            stockData.put(QNTY, stockData.get(QNTY) - quantity);
        }
    }
}
