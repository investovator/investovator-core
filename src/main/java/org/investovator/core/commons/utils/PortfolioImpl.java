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
    private float cashBalance;
    private float blockedCashAmount;
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
        return (int) (this.cashBalance - comparePortfolio.getCashBalance());
    }

    @Override
    public float getCashBalance() {
        return cashBalance;
    }

    @Override
    public void setCashBalance(float cashBalance) {
        this.cashBalance = cashBalance;
    }

    @Override
    public void setBlockedCash(float amount) {
       this.blockedCashAmount = amount;
    }

    @Override
    public float getBlockedCash() {
        return blockedCashAmount;
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
        blockedCashAmount -= price*quantity;
    }

    /**
     *
     * {@inheritDoc}
     */
    public void soldShares(String symbol, float quantity, float price){
        if (shares.get(symbol).get(QNTY) == quantity){
            removeStock(symbol);
        } else {
            HashMap<String, Float> stockData = shares.get(symbol);
            stockData.put(QNTY, stockData.get(QNTY) - quantity);
        }
    }
}
