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
public interface DirectoryDAO {

    public enum UserRole{ADMIN,
        REGISTERED;

        protected static String toLdapKey(UserRole role) {
            return role==ADMIN ? LdapUtils.DN_ADMIN_ROLE_KEY : LdapUtils.DN_ROLES_KEY;
        }
    }

    public enum UserDataType{NAME}

    /**
     * Check authentication of a user
     * @param credentials credentials
     * @return authenticated = true, else false
     * @throws AuthenticationException
     */
    public boolean authenticate(SimpleCredentials credentials) throws AuthenticationException;

    /**
     * Authentication and getting user attributes
     * @param credentials credentials
     * @return user attributes
     * @throws AuthenticationException
     */
    public HashMap<Object, Object> bindUser(SimpleCredentials credentials) throws AuthenticationException;

    /**
     * Search all the users registered for the game
     * @param role role
     * @return List of users
     * @throws AuthenticationException
     * @throws AuthorizationException
     */
    public ArrayList<String> getAllUsers(UserRole role) throws AuthenticationException, AuthorizationException;

    /**
     *
     * Adding users to roles
     * @param credentials user credentials of the authorized user to perform this action
     * @param uid user id of the user to be added
     * @param role role which should be added
     * @throws AuthenticationException
     * @throws AuthorizationException
     */
    public boolean addUserToRole(SimpleCredentials credentials, String uid, UserRole role)
            throws AuthenticationException, AuthorizationException;

    /**
     *
     * @param credentials user credentials of the authorized user to perform this action
     * @param uid user id of the user to be removed
     * @param role role which should be removed
     * @throws AuthenticationException
     * @throws AuthorizationException
     */
    public boolean removeUserFromRole(SimpleCredentials credentials, String uid, UserRole role)
            throws AuthenticationException, AuthorizationException;
}
