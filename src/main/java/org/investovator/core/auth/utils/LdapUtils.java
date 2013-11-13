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
import org.investovator.core.auth.exceptions.AuthenticationException;

/**
 * @author rajith
 * @version ${Revision}
 */
public class LdapUtils {

    public static final String URL_HOST_KEY = "org.investovator.core.auth.ldap.url.host";
    public static final String URL_PORT_KEY = "org.investovator.core.auth.ldap.url.port";
    public static final String DN_PEOPLE_KEY = "org.investovator.core.auth.ldap.dn.people";
    public static final String DN_ROLES_KEY = "org.investovator.core.auth.ldap.dn.roles";
    public static final String DN_ADMIN_ROLE_KEY = "org.investovator.core.auth.ldap.dn.adminrole";
    public static final String DN_USER_ROLE_KEY = "org.investovator.core.auth.ldap.dn.gameusers";

    /*String related to LDAP*/
    public static final String UID_STRING = "uid=";
    public static final String CN_STRING = "cn=";
    public static final String MEMBER_ATTRIB = "member";
    public static final String ALL_ATTRIB = "*";
    public static final String COMMON_NAME = "cn";
    public static final String SURNAME = "sn";

    /*custom error messages*/
    public static final String ERROR_INSUFFICIENT_ACCESS = "Insufficient access permissions";
    public static final String ERROR_INVALID_PASSWORD = "Invalid password";
    public static final String ERROR_INVALID_USER = "User not available in the directory";

    private static volatile LdapConnectionPool connectionPool;


    public static LdapConnection getLdapConnection() throws AuthenticationException {
        if(connectionPool == null){
            synchronized(LdapUtils.class){
                if(connectionPool == null)
                    createNewConnectionPool();
            }
        }
        try {
            return connectionPool.getConnection();
        } catch (Exception e) {
            throw new AuthenticationException(e);
        }
    }

    public static void releaseConnection(LdapConnection connection) throws AuthenticationException {
        try {
            connectionPool.releaseConnection(connection);
        } catch (Exception e) {
            throw new AuthenticationException(e);
        }
    }

    public static void releaseConnectionQuietly(LdapConnection connection) {
        try {
            connectionPool.releaseConnection(connection);
        } catch (Exception e) {
            //exception ignored
        }
    }

    public static LdapConnection getUserLdapConnection(String username, String password){
        LdapConnectionConfig config = new LdapConnectionConfig();
        config.setLdapHost(System.getProperty(URL_HOST_KEY));

        if(!System.getProperty(URL_PORT_KEY).equals("")){
            config.setLdapPort(Integer.valueOf(System.getProperty(URL_PORT_KEY)));
        }
        config.setName(username);
        config.setCredentials(password);

        return new LdapNetworkConnection(config);
    }

    private static void createNewConnectionPool(){
        LdapConnectionConfig config = new LdapConnectionConfig();
        config.setLdapHost(System.getProperty(URL_HOST_KEY));

        if(!System.getProperty(URL_PORT_KEY).equals("")){
            config.setLdapPort(Integer.valueOf(System.getProperty(URL_PORT_KEY)));
        }
        PoolableLdapConnectionFactory factory = new PoolableLdapConnectionFactory( config );
        connectionPool = new LdapConnectionPool(factory);
        connectionPool.setTestOnBorrow(true);
    }
}
