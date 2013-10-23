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

package org.investovator.core.data.rssexplorer.utils;

import java.io.IOException;
import java.sql.*;

/**
 * @author rajith
 * @version $Revision$
 */
public class MysqlConnector {

    public static final String MYSQL_URL_KEY = "org.investovator.core.data.mysql.url";
    public static final String MYSQL_USERNAME_KEY = "org.investovator.core.data.mysql.username";
    public static final String MYSQL_PASSWORD_KEY = "org.investovator.core.data.mysql.password";
    public static final String MYSQL_DB_KEY = "org.investovator.core.data.mysql.database";
    public static final String DRIVER_CLASS_NAME_KEY = "org.investovator.core.data.mysql.driverclassname";
    public static final String DDL_SCRIPT_PATH_KEY = "org.investovator.core.data.mysql.ddlscriptpath";

    private static volatile MysqlConnector mysqlConnector;

    private Connection connection;

    public static MysqlConnector getConnector() throws ClassNotFoundException,
            InstantiationException, IllegalAccessException, SQLException, IOException {

        if(mysqlConnector == null){
            synchronized(MysqlConnector.class){
                if(mysqlConnector == null)
                    mysqlConnector = new MysqlConnector();
            }
        }
        return mysqlConnector;
    }

    public Connection getConnection(){
        return this.connection;
    }

    public static void insertToCompanyInfo(Connection con, String symbol, String companyName,
                                           int noOfShares) throws SQLException {

        String query =  "INSERT INTO " + MysqlConstants.COMPANY_INFO +
                        " VALUES (?,?,?) ON DUPLICATE KEY UPDATE NAME = (?), SHARES = (?)";

        PreparedStatement preparedStatement = con.prepareStatement(query);
        preparedStatement.setString(1, symbol);
        preparedStatement.setString(2, companyName);
        preparedStatement.setInt(3, noOfShares);

        preparedStatement.setString(4, companyName);
        preparedStatement.setInt(5, noOfShares);

        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public static ResultSet getAllNameId(Connection con) throws SQLException {

/*        StringBuilder query = new StringBuilder("SELECT ");
        query.append("?");
        for (int i = 0; i < attributes.size() - 1;i++){
            query.append(", ?");
        }
        query.append(" FROM ").append(tableName);

        PreparedStatement preparedStatement = con.prepareStatement(query.toString());
        for (int i = 1; i <= attributes.size(); i++){
            preparedStatement.setString(i, attributes.get(i - 1));
        }*/

        String query = "SELECT " + MysqlConstants.SYMBOL + ", " + MysqlConstants.NAME
                + " FROM " + MysqlConstants.COMPANY_INFO;

        Statement statement = con.createStatement();
        return statement.executeQuery(query);
    }

    public static ResultSet getAllIdShares(Connection con) throws SQLException {
        String query = "SELECT " + MysqlConstants.SYMBOL + ", " + MysqlConstants.SHARES
                + " FROM " + MysqlConstants.COMPANY_INFO;

        Statement statement = con.createStatement();
        return statement.executeQuery(query);
    }

    public static ResultSet getCompanyName(Connection con, String symbol) throws SQLException {
        String query = "SELECT " + MysqlConstants.NAME + " FROM " + MysqlConstants.COMPANY_INFO
                + " WHERE " + MysqlConstants.SYMBOL + " = (?)";

        PreparedStatement preparedStatement = con.prepareStatement(query);
        preparedStatement.setString(1, symbol);
        return preparedStatement.executeQuery();
    }

    public static ResultSet getCompanyShares(Connection con, String symbol) throws SQLException {
        String query = "SELECT " + MysqlConstants.SHARES + " FROM " + MysqlConstants.COMPANY_INFO
                + " WHERE " + MysqlConstants.SYMBOL + " = (?)";

        PreparedStatement preparedStatement = con.prepareStatement(query);
        preparedStatement.setString(1, symbol);
        return preparedStatement.executeQuery();
    }

    public static ResultSet getAllStockIds(Connection con) throws SQLException {
        String query = "SELECT " + MysqlConstants.SYMBOL + " FROM " + MysqlConstants.COMPANY_INFO;

        Statement statement = con.createStatement();
        return statement.executeQuery(query);
    }

    private MysqlConnector() throws ClassNotFoundException, IllegalAccessException,
            InstantiationException, SQLException, IOException {

        String username = System.getProperty(MYSQL_USERNAME_KEY);
        String password = System.getProperty(MYSQL_PASSWORD_KEY);
        String databaseName = System.getProperty(MYSQL_DB_KEY);

        String url = "jdbc:mysql://" + System.getProperty(MYSQL_URL_KEY);

        Class.forName(System.getProperty(DRIVER_CLASS_NAME_KEY)).newInstance();
        String dbUrl = url + "/" + databaseName;

        createDbIfNotExists(username, password, url, dbUrl);
        connection = DriverManager.getConnection(dbUrl, username, password);
    }

    private void createDbIfNotExists(String username, String password, String url, String dbUrl)
            throws SQLException, IOException {
        try {
            connection = DriverManager.getConnection(dbUrl, username, password);
        } catch (Exception error){
            if (error.getMessage().contains("Unknown database")){
                ConnectorUtils.executeSQLFile(username, password, url, System.getProperty(DDL_SCRIPT_PATH_KEY));
            } else {
                throw new SQLException(error);
            }
        }
    }
}
