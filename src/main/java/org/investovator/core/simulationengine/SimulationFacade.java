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

package org.investovator.core.simulationengine;

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
     * @param initFunds initial account balance
     * @return spawned AgentId
     */
    public String AddUserAgent(double initFunds);

    /*User related*/

    /**
     *
     * @param humanAgentId corresponding agentId
     * @param stockId security id
     * @param quantity stock quantity
     * @param isBuy buy = true, sell=false;
     * @param price single stock price
     * @return adding order successful
     */
    public boolean putLimitOrder(String humanAgentId, String stockId, int quantity,
                                 double price, boolean isBuy);

    /**
     *
     * @param humanAgentId corresponding agentId
     * @param stockId security id
     * @param quantity stock quantity
     * @param isBuy buy = true, sell=false;
     * @return adding order successful
     */
    public boolean putMarketOrder(String humanAgentId, String stockId, int quantity,
                                  boolean isBuy);

    //TODO throwing an event when the orders get matched

    /**
     *
     * @param humanAgentId human agent's id
     * @return HashMap containing stockId and number od stocks holding
     */
    public HashMap<String, Integer> getUserAgentAssets(String humanAgentId);

    /**
     *
     * @param humanAgentId human agent's id
     * @return human agent's funds in the simulation
     */
    public double getUserAgentFunds(String humanAgentId);
}
