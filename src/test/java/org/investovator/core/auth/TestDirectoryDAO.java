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

import org.apache.commons.configuration.ConfigurationException;
import org.investovator.core.auth.exceptions.AuthenticationException;
import org.investovator.core.auth.exceptions.AuthorizationException;
import org.investovator.core.commons.configuration.ConfigLoader;
import org.junit.Before;
import org.junit.Test;

import javax.jcr.SimpleCredentials;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author rajith
 * @version ${Revision}
 */
public class TestDirectoryDAO{

    private static String RESOURCE_DIR_PATH = "src" + File.separator + "test"
            + File.separator + "resources" + File.separator;

    @Test
    public void testAuthentication() throws ConfigurationException, AuthenticationException {

        DirectoryDAO dao = new DirectoryDAOImpl();
        SimpleCredentials simpleCredentials = new SimpleCredentials("dexter",("dexter").toCharArray());
        assertTrue(dao.authenticate(simpleCredentials));
    }

    @Test
    public void testBindUser() throws ConfigurationException, AuthenticationException {

        DirectoryDAO dao = new DirectoryDAOImpl();
        SimpleCredentials simpleCredentials = new SimpleCredentials("dexter",("dexter").toCharArray());
        HashMap<Object, Object> userData = dao.bindUser(simpleCredentials);

        assertTrue(userData.get(DirectoryDAO.UserDataType.NAME).equals("Dexter Morgan"));
        assertFalse((boolean) userData.get(DirectoryDAO.UserRole.ADMIN));
        assertTrue((boolean)userData.get(DirectoryDAO.UserRole.REGISTERED));
    }

    @Test
    public void testGetAllUsers() throws AuthenticationException, AuthorizationException {
        DirectoryDAO dao = new DirectoryDAOImpl();
        ArrayList<String> users = dao.getAllUsers(DirectoryDAO.UserRole.REGISTERED);

        assertTrue(users.contains("alex"));
        assertTrue(users.contains("dexter"));
    }

    @Before
    public void setEnvironment() throws ConfigurationException {
        ConfigLoader.loadProperties(RESOURCE_DIR_PATH + "resource.properties");
    }

}
