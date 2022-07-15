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
package lk.ijse.dep.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.dbcp2.BasicDataSource;

import java.io.IOException;
import java.util.Properties;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DatabaseHelper {

    private static DatabaseHelper databaseHelper = null;

    private static Properties properties = null;

    private static BasicDataSource dataSource = null;

    private static final String DB_DRIVER_CLASS = "javax.persistence.jdbc.driver";
    //    private static final String DB_DRIVER_CLASS = "driver.class.name";
    private static final String DB_URL = "javax.persistence.jdbc.url";
    //    private static final String DB_URL = "db.url";
    private static final String DB_USERNAME = "javax.persistence.jdbc.user";
    //    private static final String DB_USERNAME = "db.username";
    private static final String DB_PASSWORD = "javax.persistence.jdbc.password";
//    private static final String DB_PASSWORD = "db.password";

    private static final Integer INITIAL_NO_CONNECTIONS = 5;
    private static final Integer MAX_NO_CONNECTIONS = 10;

    private static String driverClassName;
    private static String url;
    private static String username;
    private static String password;

    static {
        getMyProperties();

        driverClassName = properties.getProperty(DB_DRIVER_CLASS);
        url = properties.getProperty(DB_URL);
        username = properties.getProperty(DB_USERNAME);
        password = properties.getProperty(DB_PASSWORD);

        if (dataSource == null) {
            BasicDataSource basicDataSource = new BasicDataSource();
            basicDataSource.setDriverClassName(driverClassName);
            basicDataSource.setUrl(url);
            basicDataSource.setUsername(username);
            basicDataSource.setPassword(password);
            basicDataSource.setInitialSize(INITIAL_NO_CONNECTIONS);
            basicDataSource.setMaxTotal(MAX_NO_CONNECTIONS);
            dataSource = basicDataSource;
        }
    }

//    public static DatabaseHelper getInstance() {
//        return (databaseHelper == null) ? databaseHelper = new DatabaseHelper() : databaseHelper;
//    }

    public static Properties getMyProperties() {
        if (properties == null) {
            properties = new Properties();
            try {
                properties.load(DatabaseHelper.class.getResourceAsStream("/application.properties"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return properties;
    }

    public static BasicDataSource getDataSource() {
        return dataSource;
    }

}
