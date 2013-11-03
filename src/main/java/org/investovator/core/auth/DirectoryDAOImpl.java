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
import org.apache.directory.api.ldap.model.entry.DefaultAttribute;
import org.apache.directory.api.ldap.model.entry.StringValue;
import org.apache.directory.api.ldap.model.message.*;
import org.apache.directory.api.ldap.model.name.Dn;
import org.apache.directory.ldap.client.api.LdapConnection;
import org.investovator.core.auth.exceptions.AuthenticationException;
import org.investovator.core.auth.exceptions.AuthorizationException;
import org.investovator.core.auth.utils.LdapUtils;

import javax.jcr.SimpleCredentials;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author rajith
 * @version ${Revision}
 */
public class DirectoryDAOImpl implements DirectoryDAO {

    @Override
    public boolean authenticate(SimpleCredentials credentials) throws AuthenticationException {

        LdapConnection connection = LdapUtils.getLdapConnection();

        try {
            Dn baseDN = new Dn(System.getProperty(LdapUtils.DN_PEOPLE_KEY));
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

    @Override
    public HashMap<String, String> getUserAttributes(SimpleCredentials credentials)
            throws AuthenticationException, AuthorizationException {
        return null;  //TODO
    }

    @Override
    public ArrayList<String> getAllUsers() throws AuthenticationException, AuthorizationException {

        LdapConnection connection = LdapUtils.getLdapConnection();

        try {
            Dn baseDN = new Dn(System.getProperty(LdapUtils.DN_ROLES_KEY));
            String filter = "("+ LdapUtils.CN_STRING + System.getProperty(LdapUtils.DN_USER_ROLE_KEY) + ")";

            EntryCursor cursor = connection.search(baseDN, filter, SearchScope.ONELEVEL, LdapUtils.MEMBER_ATTRIB);
            ArrayList<String> users = new ArrayList<>();
            if (cursor.next()){
                DefaultAttribute userIds = (DefaultAttribute) (cursor.get()).get(LdapUtils.MEMBER_ATTRIB);
                for (Object userId : userIds) {
                    String username = ((StringValue) userId).getString();
                    users.add((username.split(",")[0]).split(LdapUtils.UID_STRING)[1]);
                }
                return users;
            } else {
                throw new AuthorizationException("Searching not allowed for given user");
            }
        } catch (AuthorizationException exception) {
            throw exception;
        } catch (Exception e) {
            throw new AuthenticationException(e);
        } finally {
            LdapUtils.releaseConnectionQuietly(connection);
        }
    }
}
