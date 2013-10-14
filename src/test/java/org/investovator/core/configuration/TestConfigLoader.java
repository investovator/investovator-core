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

package org.investovator.core.configuration;

import junit.framework.TestCase;
import org.apache.commons.configuration.ConfigurationException;

import java.io.File;

/**
 * @author rajith
 * @version $Revision$
 */
public class TestConfigLoader extends TestCase {

    public void testConfigLoader(){
        try {
            ConfigLoader.loadProperties("src"+ File.separator + "test" + File.separator
                    + "resources" + File.separator + "resource.properties");
        } catch (ConfigurationException e) {
            assertFalse(e.getMessage(), true);
        }

        assertEquals("localhost:9160", System.getProperty("org.investovator.core.data.cassandra.url"));
        assertEquals("admin", System.getProperty("org.investovator.core.data.cassandra.username"));
        assertEquals("admin", System.getProperty("org.investovator.core.data.cassandra.password"));
    }
}
