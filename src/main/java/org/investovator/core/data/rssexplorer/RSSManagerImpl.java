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

package org.investovator.core.data.rssexplorer;

import org.investovator.core.data.api.utils.CompanyInfo;
import org.investovator.core.data.exeptions.DataAccessException;
import org.investovator.core.data.exeptions.DataNotFoundException;
import org.investovator.core.data.rssexplorer.utils.ConnectorUtils;
import org.investovator.core.data.rssexplorer.utils.MysqlConnector;
import org.investovator.core.data.rssexplorer.utils.MysqlConstants;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author rajith
 * @version $Revision$
 */
public class RSSManagerImpl implements RSSManager {

    private MysqlConnector mysqlConnector;

    public RSSManagerImpl() throws DataAccessException {
        try {
            mysqlConnector = MysqlConnector.getConnector();
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public HashMap<String, String> getCompanyIDsNames() throws DataAccessException {
        try {
            Connection connection = mysqlConnector.getConnection();
            ResultSet resultSet = MysqlConnector.getAllNameId(connection);

            HashMap<String, String> data = new HashMap<String, String>();
            while (resultSet.next()){
                data.put(resultSet.getString(MysqlConstants.SYMBOL), resultSet.getString(MysqlConstants.NAME));
            }
            resultSet.close();
            return data;
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public void addCompanyData(String symbol, String companyName, int shares) throws DataAccessException {
        try {
            Connection connection = mysqlConnector.getConnection();
            MysqlConnector.insertToCompanyInfo(connection, symbol, companyName, shares);
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public HashMap<String, Integer> getCompanyIDsTotalShares() throws DataAccessException {
        try {
            Connection connection = mysqlConnector.getConnection();
            ResultSet resultSet = MysqlConnector.getAllIdShares(connection);

            HashMap<String, Integer> data = new HashMap<String, Integer>();
            while (resultSet.next()){
                data.put(resultSet.getString(MysqlConstants.SYMBOL), resultSet.getInt(MysqlConstants.SHARES));
            }
            resultSet.close();
            return data;
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public String getCompanyName(String symbol) throws DataAccessException {
        try {
            Connection connection = mysqlConnector.getConnection();
            ResultSet resultSet = MysqlConnector.getCompanyName(connection, symbol);
            resultSet.next();
            String name = resultSet.getString(MysqlConstants.NAME);
            resultSet.close();
            return name;
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public int getCompanyNoOfShares(String symbol) throws DataAccessException {
        try {
            Connection connection = mysqlConnector.getConnection();
            ResultSet resultSet = MysqlConnector.getCompanyShares(connection, symbol);
            resultSet.next();
            int shares = resultSet.getInt(MysqlConstants.SHARES);
            resultSet.close();
            return shares;
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public String getInfo(CompanyInfo infoType, String symbol) throws DataAccessException, DataNotFoundException {
        try {
            Connection connection = mysqlConnector.getConnection();
            ResultSet resultSet = MysqlConnector.getCompanyInfo(connection,
                    CompanyInfo.getAttribName(infoType), symbol);
            resultSet.next();
            String value = resultSet.getString(MysqlConstants.VALUE);
            resultSet.close();
            return value;
        } catch (Exception e) {
            if(e.getMessage().contains("Illegal operation on empty result set.")){
               throw new DataNotFoundException(CompanyInfo.getAttribName(infoType)
                       + "information not available for " + symbol);
            } else
                throw new DataAccessException(e);
        }
    }

    @Override
    public void addInfo(CompanyInfo infoType, String symbol, String value) throws DataAccessException {
        try {
            Connection connection = mysqlConnector.getConnection();
            MysqlConnector.addToCompanyInfo(connection, CompanyInfo.getAttribName(infoType), symbol, value);
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public ArrayList<String> getAvailableStockIds() throws DataAccessException {
        try {
            Connection connection = mysqlConnector.getConnection();
            ResultSet resultSet = MysqlConnector.getAllStockIds(connection);

            ArrayList<String> data = new ArrayList<String>();
            while (resultSet.next()){
                data.add(resultSet.getString(MysqlConstants.SYMBOL));
            }
            resultSet.close();
            return data;
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public HashMap<String, HashMap<String, Double>> getUserPortfolio(String gameInstanceName, String username)
            throws DataAccessException {
        try {
            Connection connection = mysqlConnector.getConnection();
            String portfolioName = ConnectorUtils.getFullyQualifiedTableName(gameInstanceName, username,
                    MysqlConstants.PORTFOLIO);
            ResultSet resultSet = MysqlConnector.getUserPortfolio(connection, portfolioName);

            HashMap<String, HashMap<String, Double>> data = new HashMap<String, HashMap<String, Double>>();
            while (resultSet.next()){
                HashMap<String, Double> values = new HashMap<String, Double>();
                values.put(MysqlConstants.QNTY, resultSet.getDouble(MysqlConstants.QNTY));
                values.put(MysqlConstants.PRICE, resultSet.getDouble(MysqlConstants.PRICE));
                data.put(resultSet.getString(MysqlConstants.SYMBOL), values);
            }
            resultSet.close();
            return data;
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public void updateUserPortfolio(String gameInstanceName, String username,
                                    HashMap<String, HashMap<String, Double>> portfolio)
            throws DataAccessException {
        try {
            Connection connection = mysqlConnector.getConnection();
            String portfolioName = ConnectorUtils.getFullyQualifiedTableName(gameInstanceName, username,
                    MysqlConstants.PORTFOLIO);
            MysqlConnector.dropTableIfExists(connection, portfolioName);
            MysqlConnector.bulkInsertToUserPortfolio(connection, portfolioName, portfolio);
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public void deleteUserPortfolio(String gameInstanceName, String username) throws DataAccessException{
        try {
            Connection connection = mysqlConnector.getConnection();
            String portfolioName = ConnectorUtils.getFullyQualifiedTableName(gameInstanceName, username,
                    MysqlConstants.PORTFOLIO);
            MysqlConnector.dropTableIfExists(connection, portfolioName);
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public void updatePortfolioValue(String gameInstanceName, String username, double value, double blockedValue)
            throws DataAccessException{
        try {
            Connection connection = mysqlConnector.getConnection();
            String portfolioValueTableName = ConnectorUtils.getFullyQualifiedTableName(gameInstanceName,
                    MysqlConstants.PORTFOLIO_VALUES);
            MysqlConnector.addToPortfolioValues(connection,portfolioValueTableName, username, value, blockedValue);
        } catch (Exception e){
             throw new DataAccessException(e);
        }
    }

    @Override
    public HashMap<String, Double> getPortfolioValue(String gameInstanceName, String username)
            throws DataAccessException{
        try {
            Connection connection = mysqlConnector.getConnection();
            String portfolioValueTableName = ConnectorUtils.getFullyQualifiedTableName(gameInstanceName,
                    MysqlConstants.PORTFOLIO_VALUES);
            ResultSet resultSet = MysqlConnector
                    .getValueFromPortfolioValues(connection, portfolioValueTableName, username);

            HashMap<String, Double> data = new HashMap<String, Double>();
            while (resultSet.next()){
                data.put(MysqlConstants.VALUE, resultSet.getDouble(MysqlConstants.VALUE));
                data.put(MysqlConstants.BLOCKED_VALUE, resultSet.getDouble(MysqlConstants.BLOCKED_VALUE));
            }
            resultSet.close();
            return data;
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public HashMap<String, HashMap<String, Double>> getAllPortfolioValues(String gameInstanceName)
            throws DataAccessException{
        try {
            Connection connection = mysqlConnector.getConnection();
            String portfolioValueTableName = ConnectorUtils.getFullyQualifiedTableName(gameInstanceName,
                    MysqlConstants.PORTFOLIO_VALUES);
            ResultSet resultSet = MysqlConnector.getAllValuesFromPortfolioValues(connection, portfolioValueTableName);

            HashMap<String, HashMap<String, Double>> data = new HashMap<String, HashMap<String, Double>>();
            while (resultSet.next()){
                HashMap<String, Double> values = new HashMap<String, Double>();
                values.put(MysqlConstants.VALUE, resultSet.getDouble(MysqlConstants.VALUE));
                values.put(MysqlConstants.BLOCKED_VALUE, resultSet.getDouble(MysqlConstants.BLOCKED_VALUE));
                data.put(resultSet.getString(MysqlConstants.USERNAME), values);
            }
            resultSet.close();
            return data;
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public void deletePortfolioValue(String gameInstanceName, String username) throws DataAccessException{
        try {
            Connection connection = mysqlConnector.getConnection();
            String portfolioValueTableName = ConnectorUtils.getFullyQualifiedTableName(gameInstanceName,
                    MysqlConstants.PORTFOLIO_VALUES);
            MysqlConnector.deleteValueFrmPortfolioValues(connection, portfolioValueTableName, username);
        } catch (Exception e){
            throw new DataAccessException(e);
        }
    }

    @Override
    public ArrayList<String> getWatchList(String gameInstanceName, String username) throws DataAccessException{
        try {
            Connection connection = mysqlConnector.getConnection();
            String watchlistTableName = ConnectorUtils.getFullyQualifiedTableName(gameInstanceName,
                    MysqlConstants.WATCHLIST);
            ResultSet resultSet = MysqlConnector.getWatchList(connection, watchlistTableName, username);

            ArrayList<String> data = new ArrayList<String>();
            while (resultSet.next()){
                data.add(resultSet.getString(MysqlConstants.SYMBOL));
            }
            resultSet.close();
            return data;
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public void addToWatchList(String gameInstanceName, String username, String symbol)
            throws DataAccessException {
        try {
            Connection connection = mysqlConnector.getConnection();
            String watchlistTableName = ConnectorUtils.getFullyQualifiedTableName(gameInstanceName,
                    MysqlConstants.WATCHLIST);
            MysqlConnector.insertToWatchList(connection, watchlistTableName, username, symbol);
        } catch (Exception e){
            throw new DataAccessException(e);
        }
    }

    @Override
    public void deleteFromWatchList(String gameInstanceName, String username, String symbol)
            throws DataAccessException {
        try {
            Connection connection = mysqlConnector.getConnection();
            String watchlistTableName = ConnectorUtils.getFullyQualifiedTableName(gameInstanceName,
                    MysqlConstants.WATCHLIST);
            MysqlConnector.deleteFromWatchList(connection, watchlistTableName, username, symbol);
        } catch (Exception e){
            throw new DataAccessException(e);
        }
    }

    @Override
    public void addUserToGameInstancesTable(String gameInstanceName, String username) throws DataAccessException {
        try {
            Connection connection = mysqlConnector.getConnection();
            MysqlConnector.addUserToGameInstanceUsersTable(connection, gameInstanceName, username);
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public ArrayList<String> getUserJoinedGameInstances(String username) throws DataAccessException {
        try {
            Connection connection = mysqlConnector.getConnection();
            ResultSet resultSet = MysqlConnector.getUserJoinedGames(connection, username);

            ArrayList<String> data = new ArrayList<>();
            while (resultSet.next()){
                data.add(resultSet.getString(MysqlConstants.GAME_INSTANCE));
            }
            resultSet.close();
            return data;
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public ArrayList<String> getGameInstanceUsers(String gameInstanceName) throws DataAccessException {
        try {
            Connection connection = mysqlConnector.getConnection();
            ResultSet resultSet = MysqlConnector.getGameInstanceUsers(connection, gameInstanceName);

            ArrayList<String> data = new ArrayList<>();
            while (resultSet.next()){
                data.add(resultSet.getString(MysqlConstants.USERNAME));
            }
            resultSet.close();
            return data;
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public void dropGameInstanceTables(String gameInstanceName) throws DataAccessException {
        try {
            Connection connection = mysqlConnector.getConnection();
            ResultSet resultSet = MysqlConnector.getAllGameInstanceTables(connection, gameInstanceName);
            ArrayList<String> tablesToDelete = new ArrayList<>();
            while (resultSet.next()){
                tablesToDelete.add(resultSet.getString(1));

            }
            resultSet.close();

            for(String tableName : tablesToDelete){
                MysqlConnector.dropTableIfExists(connection, tableName);
            }

            MysqlConnector.dropGameInstanceFrmGameInstancesTable(connection, gameInstanceName);

        } catch (Exception e){
            throw new DataAccessException(e);
        }
    }

    @Override
    public synchronized void resetDatabase() throws DataAccessException {
        try {
            Connection connection = mysqlConnector.getConnection();
            MysqlConnector.dropDatabase(connection);
            mysqlConnector = MysqlConnector.getConnector();
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }
}
