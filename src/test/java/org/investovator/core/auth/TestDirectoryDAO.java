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

import junit.framework.TestCase;
import org.apache.commons.configuration.ConfigurationException;
import org.investovator.core.auth.exceptions.AuthenticationException;
import org.investovator.core.commons.configuration.ConfigLoader;

import javax.jcr.SimpleCredentials;
import java.io.File;

/**
 * @author rajith
 * @version ${Revision}
 */
public class TestDirectoryDAO extends TestCase{

    private static String RESOURCE_DIR_PATH = "src" + File.separator + "test"
            + File.separator + "resources" + File.separator;

    public void testAuthentication() throws ConfigurationException, AuthenticationException {

        DirectoryDAO dao = new DirectoryDAOImpl();

        ConfigLoader.loadProperties(RESOURCE_DIR_PATH + "resource.properties");

        SimpleCredentials simpleCredentials = new SimpleCredentials("dexter",("dexter").toCharArray());
        assertTrue(dao.authenticate(simpleCredentials));
    }
}
