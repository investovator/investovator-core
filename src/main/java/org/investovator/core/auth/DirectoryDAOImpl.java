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

package org.investovator.core.auth;

import org.apache.directory.api.ldap.model.cursor.EntryCursor;
import org.apache.directory.api.ldap.model.message.*;
import org.apache.directory.api.ldap.model.name.Dn;
import org.apache.directory.ldap.client.api.LdapConnection;
import org.investovator.core.auth.exceptions.AuthenticationException;
import org.investovator.core.auth.utils.LdapUtils;

import javax.jcr.SimpleCredentials;

/**
 * @author rajith
 * @version ${Revision}
 */
public class DirectoryDAOImpl implements DirectoryDAO {

    @Override
    public boolean authenticate(SimpleCredentials credentials) throws AuthenticationException {

        LdapConnection connection;
        try {
            connection = LdapUtils.getLdapConnection();
        } catch (Exception e) {
            throw new AuthenticationException(e);
        }

        try {

            Dn baseDN = new Dn(System.getProperty(LdapUtils.DN_KEY));
            String username = LdapUtils.UID_STRING + credentials.getUserID();

            EntryCursor cursor = connection.search(baseDN, "(" + username + ")", SearchScope.SUBTREE, "*");
            if(cursor.next()){
                BindRequest bindRequest = new BindRequestImpl();
                bindRequest.setName(username +","+ baseDN.toString());
                bindRequest.setCredentials(new String(credentials.getPassword()).getBytes("UTF-8"));

                BindResponse response = connection.bind(bindRequest);
                return (response.getLdapResult().getResultCode() == ResultCodeEnum.SUCCESS);
            } else {
                throw new AuthenticationException("User not available");
            }
        } catch (Exception e) {
            throw new AuthenticationException(e);
        } finally {
            LdapUtils.releaseConnectionQuietly(connection);
        }
    }
}
