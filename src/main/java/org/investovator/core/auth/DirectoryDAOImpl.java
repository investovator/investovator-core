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

import org.apache.directory.api.ldap.model.cursor.CursorException;
import org.apache.directory.api.ldap.model.cursor.EntryCursor;
import org.apache.directory.api.ldap.model.entry.DefaultAttribute;
import org.apache.directory.api.ldap.model.entry.Entry;
import org.apache.directory.api.ldap.model.entry.StringValue;
import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.directory.api.ldap.model.message.*;
import org.apache.directory.api.ldap.model.name.Dn;
import org.apache.directory.ldap.client.api.LdapConnection;
import org.investovator.core.auth.exceptions.AuthenticationException;
import org.investovator.core.auth.exceptions.AuthorizationException;
import org.investovator.core.auth.exceptions.InvalidArgumentException;
import org.investovator.core.auth.utils.LdapUtils;

import javax.jcr.SimpleCredentials;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author rajith
 * @version ${Revision}
 */
public class DirectoryDAOImpl implements DirectoryDAO {

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public boolean authenticate(SimpleCredentials credentials) throws AuthenticationException {

        LdapConnection connection = LdapUtils.getLdapConnection();
        try {
            Dn baseDN = new Dn(System.getProperty(LdapUtils.DN_PEOPLE_KEY));
            String username = LdapUtils.UID_STRING + credentials.getUserID();
            connection.bind();
            EntryCursor cursor = connection.search(baseDN, "(" + username + ")", SearchScope.SUBTREE, "*");
            if(cursor.next()){
                BindRequest bindRequest = new BindRequestImpl();
                bindRequest.setName(username +","+ baseDN.toString());
                bindRequest.setCredentials(new String(credentials.getPassword()).getBytes("UTF-8"));

                BindResponse response = connection.bind(bindRequest);
                return (response.getLdapResult().getResultCode() == ResultCodeEnum.SUCCESS);
            } else {
                throw new AuthenticationException(LdapUtils.ERROR_INVALID_USER);
            }
        } catch (Exception e) {
            throw new AuthenticationException(e);
        } finally {
            LdapUtils.releaseConnectionQuietly(connection);
        }
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public HashMap<Object, Object> bindUser(SimpleCredentials credentials) throws AuthenticationException {

        LdapConnection anonymousConnection = LdapUtils.getLdapConnection();
        try {
            Dn baseDN = new Dn(System.getProperty(LdapUtils.DN_PEOPLE_KEY));
            String username = LdapUtils.UID_STRING + credentials.getUserID();
            anonymousConnection.bind();

            /*Search anonymously or with applications credentials*/
            EntryCursor cursor = anonymousConnection
                    .search(baseDN, "(" + username + ")", SearchScope.SUBTREE, LdapUtils.ALL_ATTRIB);
            if(cursor.next()){

                String fullyQualifiedUsername = username +","+ baseDN.toString();
                BindRequest bindRequest = new BindRequestImpl();
                bindRequest.setName(fullyQualifiedUsername);
                bindRequest.setCredentials(new String(credentials.getPassword()).getBytes("UTF-8"));

                /*User authentication connection*/
                LdapConnection connection = LdapUtils.getUserLdapConnection(fullyQualifiedUsername,
                        new String(credentials.getPassword()));
                if(connection.bind(bindRequest).getLdapResult().getResultCode() == ResultCodeEnum.SUCCESS){
                    HashMap<Object, Object> userData = getUserDataFilled(credentials, baseDN, username, connection);
                    connection.close();
                    return userData;

                } else {
                    connection.close();
                    throw new AuthenticationException(LdapUtils.ERROR_INVALID_PASSWORD);
                }
            } else {
                throw new AuthenticationException(LdapUtils.ERROR_INVALID_USER);
            }
        } catch (AuthenticationException exception) {
            throw exception;
        } catch (Exception e) {
            throw new AuthenticationException(e);
        } finally {
            LdapUtils.releaseConnectionQuietly(anonymousConnection);
        }
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public ArrayList<String> getAllUsers(UserRole type) throws AuthenticationException, AuthorizationException {

        LdapConnection connection = LdapUtils.getLdapConnection();
        try {
            return retrieveUserListOnRoles(connection, type);
        } catch (AuthorizationException exception) {
            throw exception;
        } catch (Exception e) {
            throw new AuthenticationException(e);
        } finally {
            LdapUtils.releaseConnectionQuietly(connection);
        }
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public void addUserToRole(SimpleCredentials credentials, String uid, UserRole role)
            throws AuthenticationException, AuthorizationException {
        //ToDo
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public void removeUserFromRole(SimpleCredentials credentials, String uid, UserRole role)
            throws AuthenticationException, AuthorizationException {
        //ToDo
    }

    private HashMap<Object, Object> getUserDataFilled(SimpleCredentials credentials,
                                                      Dn baseDN, String fullyQualifiedUsername,
                                                      LdapConnection connection)
            throws LdapException, CursorException, AuthorizationException, IOException, AuthenticationException,
            InvalidArgumentException {
        EntryCursor entryCursor = connection
                .search(baseDN, "(" + fullyQualifiedUsername + ")", SearchScope.ONELEVEL, LdapUtils.ALL_ATTRIB);
        if (entryCursor.next()){
            HashMap<Object, Object> userData = getValues(entryCursor);

            boolean userInRegisteredRole = (retrieveUserListOnRoles(connection, UserRole.REGISTERED))
                    .contains(credentials.getUserID());
            boolean userInAdminRole = (retrieveUserListOnRoles(connection, UserRole.ADMIN))
                    .contains(credentials.getUserID());

            userData.put(UserRole.REGISTERED, userInRegisteredRole);
            userData.put(UserRole.ADMIN, userInAdminRole);
            return userData;
        } else {
            throw new AuthenticationException(LdapUtils.ERROR_INSUFFICIENT_ACCESS);
        }
    }

    private ArrayList<String> retrieveUserListOnRoles(LdapConnection connection, UserRole role)
            throws LdapException, CursorException, AuthorizationException, InvalidArgumentException {

        String roleFilter;
        if(role == UserRole.ADMIN){
            roleFilter = "("+ LdapUtils.CN_STRING + System.getProperty(LdapUtils.DN_ADMIN_ROLE_KEY) + ")";
        } else if (role == UserRole.REGISTERED) {
            roleFilter = "("+ LdapUtils.CN_STRING + System.getProperty(LdapUtils.DN_USER_ROLE_KEY) + ")";
        } else {
            throw new InvalidArgumentException("Specified role not defined");
        }

        Dn baseDN = new Dn(System.getProperty(LdapUtils.DN_ROLES_KEY));
        connection.bind();
        EntryCursor cursor = connection.search(baseDN, roleFilter, SearchScope.ONELEVEL, LdapUtils.MEMBER_ATTRIB);
        ArrayList<String> users = new ArrayList<>();
        if (cursor.next()){
            DefaultAttribute userIds = (DefaultAttribute) (cursor.get()).get(LdapUtils.MEMBER_ATTRIB);
            for (Object userId : userIds) {
                String username = ((StringValue) userId).getString();
                users.add((username.split(",")[0]).split(LdapUtils.UID_STRING)[1]);
            }
            return users;
        } else {
            throw new AuthorizationException(LdapUtils.ERROR_INSUFFICIENT_ACCESS);
        }
    }

    /**
     *
     * @param entryCursor cursor
     * @return only full name
     * @throws CursorException
     */
    private HashMap<Object, Object> getValues(EntryCursor entryCursor) throws CursorException {
        Entry values = entryCursor.get();
        HashMap<Object, Object> userData = new HashMap<>();
        StringBuilder name = new StringBuilder((((values.get(LdapUtils.COMMON_NAME)
                .toString()).split(":"))[1]).trim());
        String lastName = (((values.get(LdapUtils.SURNAME).toString()).split(":"))[1]).trim();
        userData.put(UserDataType.NAME, ((name.append(" ").append(lastName)).toString()));
        return userData;
    }

}
