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

package org.investovator.core.data.rssexplorer.utils;

import org.investovator.core.commons.utils.fileutils.FileManager;
import org.investovator.core.commons.utils.fileutils.StringConverter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author rajith
 * @version $Revision$
 */
public class ConnectorUtils {

    private static final String COMMENT_PATTERN = "--";
    private static final String DELIMITER = ";";

    private static final String TABLE_CONNECTOR_STRING = "_";

    public static void executeSQLFile(String username, String password, String url, String relativeFilePath)
            throws IOException, SQLException {
        String sqlStatement = FileManager.readFileOmitComments(relativeFilePath, COMMENT_PATTERN);
        Connection conn = DriverManager.getConnection(url, username, password);

        Statement statement = conn.createStatement();
        String[] formattedSQLStrings = sqlStatement.split(DELIMITER);

        for(String formattedSQLString: formattedSQLStrings){
            if(!(formattedSQLString.trim().equals(""))){
                statement.executeUpdate(formattedSQLString + DELIMITER);
            }
        }
        statement.close();
        conn.close();
    }

    public static String getFullyQualifiedTableName(String prefix, String suffix){
        return StringConverter.keepOnlyAlphaNumeric(prefix.toUpperCase()) + TABLE_CONNECTOR_STRING +
                StringConverter.keepOnlyAlphaNumeric(suffix.toUpperCase());
    }

    public static String getFullyQualifiedTableName(String prefix,String midString, String suffix){
        return getFullyQualifiedTableName(prefix, midString) + TABLE_CONNECTOR_STRING +
                StringConverter.keepOnlyAlphaNumeric(suffix.toUpperCase());
    }
}
