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

package org.investovator.core.data.api;

import org.investovator.core.data.exeptions.DataAccessException;
import org.investovator.core.utils.Portfolio;

import java.util.HashMap;

/**
 * @author rajith
 * @version $Revision$
 */
public class UserDataImpl implements UserData{

    public void addUser(String username, String firstname,
                        String lastname, String emailaddress, String password)
            throws DataAccessException {
    //TODO
    }

    public String getUserPassword(String username)
            throws DataAccessException {
        return null; //TODO
    }

    public HashMap<String, String> getUserDetails(String username)
            throws DataAccessException {
        return null; //TODO
    }

    public Portfolio getUserPortfolio(String username)
            throws DataAccessException {
        return null; //TODO
    }

}
