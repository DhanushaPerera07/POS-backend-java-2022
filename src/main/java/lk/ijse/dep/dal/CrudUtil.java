/*
 * MIT License
 *
 * Copyright (c) 2022 Dhanusha Perera
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package lk.ijse.dep.dal;

import java.sql.*;

public class CrudUtil {

    public static <T> T execute(Connection connection, String sqlStatement, Object... params) throws SQLException {

        PreparedStatement pstm = connection.prepareStatement(sqlStatement);
        for (int i = 0; i < params.length; i++) {
            pstm.setObject(i + 1, params[i]);
        }

        return (sqlStatement.trim().matches("(?i)(SELECT).+")) ? (T) pstm.executeQuery() :
                (T) (Boolean) (pstm.executeUpdate() > 0);
    }

    /**
     * Execute query and return the generated ID. For example: INSERT SQL queries.
     */
    public static Integer executeAndReturnGeneratedKeys(Connection connection, String sqlStatement, Object... params)
            throws SQLException {

        PreparedStatement pstm = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);

        /* Setting Parameters. */
        for (int i = 0; i < params.length; i++) {
            pstm.setObject(i + 1, params[i]);
        }

        /* Execute the SQL statement. */
        int affectedRows = pstm.executeUpdate();
        ResultSet rs = pstm.getGeneratedKeys();

        if (affectedRows > 0 && rs.next()) {
            return rs.getInt(1); // First column index = 1
        } else {
            return null;
        }

    }
}
