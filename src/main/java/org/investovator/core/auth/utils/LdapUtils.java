/*
 * investovator, Stock Market Gaming framework
 * Copyright (C) 2013  investovator
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.investovator.core.auth.utils;

import org.apache.directory.ldap.client.api.*;

/**
 * @author rajith
 * @version ${Revision}
 */
public class LdapUtils {

    public static final String URL_HOST_KEY = "org.investovator.core.auth.ldap.url.host";
    public static final String URL_PORT_KEY = "org.investovator.core.auth.ldap.url.port";
    public static final String DN_KEY = "org.investovator.core.auth.ldap.dn";

    /*String related to LDAP*/
    public static String UID_STRING = "uid=";

    private static volatile LdapConnectionPool connectionPool;


    public static LdapConnection getLdapConnection() throws Exception {
        if(connectionPool == null){
            synchronized(LdapUtils.class){
                if(connectionPool == null)
                    createNewConnectionPool();
            }
        }
        return connectionPool.getConnection();
    }

    public static void releaseConnection(LdapConnection connection) throws Exception {
        connectionPool.releaseConnection(connection);
    }

    public static void releaseConnectionQuietly(LdapConnection connection) {
        try {
            connectionPool.releaseConnection(connection);
        } catch (Exception e) {
            //exception ignored
        }
    }

    private static void createNewConnectionPool(){
        LdapConnectionConfig config = new LdapConnectionConfig();
        config.setLdapHost(System.getProperty(URL_HOST_KEY));

        if(!System.getProperty(URL_PORT_KEY).equals("")){
            config.setLdapPort(Integer.valueOf(System.getProperty(URL_PORT_KEY)));
        }
        PoolableLdapConnectionFactory factory = new PoolableLdapConnectionFactory( config );
        connectionPool = new LdapConnectionPool( factory );
        connectionPool.setTestOnBorrow(true);
    }
}
