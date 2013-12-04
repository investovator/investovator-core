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

package org.investovator.core.commons.simulationengine;

import org.investovator.core.commons.utils.ExecutionResult;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author rajith
 * @version $Revision$
 */
public interface SimulationFacade {

    /*Simulation related*/

    public boolean startSimulation();

    public boolean pauseSimulation();

    public boolean terminateSimulation();

    public boolean resumeSimulation();

    /**
     * @param username username
     * @param initFunds initial account balance
     */
    public void AddUserAgent(String username, double initFunds);

    /*User related*/

    /**
     *
     * @param username corresponding username
     * @param stockId security id
     * @param quantity stock quantity
     * @param isBuy buy = true, sell=false;
     * @param price single stock price
     * @return adding order successful
     */
    public ExecutionResult putLimitOrder(String username, String stockId, int quantity,
                                 double price, boolean isBuy);

    /**
     *
     * @param username corresponding username
     * @param stockId security id
     * @param quantity stock quantity
     * @param isBuy buy = true, sell=false;
     * @return adding order successful
     */
    public ExecutionResult putMarketOrder(String username, String stockId, int quantity,
                                  boolean isBuy);

    //TODO throwing an event when the orders get matched

    /**
     *
     * @param username username
     * @return HashMap containing stockId and number od stocks holding
     */
    public HashMap<String, Integer> getUserAgentAssets(String username);

    /**
     *
     * @param username username
     * @return human agent's funds in the simulation
     */
    public double getUserAgentFunds(String username);

    /**
     * Get unmatched orders of an username
     * @param username Username
     * @return Unmatched orders
     */
    public HashMap<String, ArrayList<MarketOrder>> getUserUnmatchedOrders(String username);
}
