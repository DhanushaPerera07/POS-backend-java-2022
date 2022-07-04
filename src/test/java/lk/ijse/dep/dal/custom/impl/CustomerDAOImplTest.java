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
package lk.ijse.dep.dal.custom.impl;

import lk.ijse.dep.dal.DAOFactory;
import lk.ijse.dep.dal.DAOTypes;
import lk.ijse.dep.dal.custom.CustomerDAO;
import lk.ijse.dep.entity.Customer;
import lk.ijse.dep.util.DatabaseHelper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CustomerDAOImplTest {

    private static Connection connection;

    private static CustomerDAO customerDAO;

//    @Test
//    void setConnection() throws SQLException {
//        BasicDataSource dataSource = DatabaseHelper.getDataSource();
//        assertNotNull(dataSource.getConnection());
//    }

    @BeforeAll
    static void connectWithDatabase() throws SQLException {
        connection = DatabaseHelper.getDataSource().getConnection();

        customerDAO = (CustomerDAO) DAOFactory.getInstance().getDAO(DAOTypes.CUSTOMER);
        customerDAO.setConnection(connection);
    }

    @AfterAll
    static void disconnectWithDatabase() throws SQLException {
        DatabaseHelper.getDataSource().close();
    }

    @Test
    void save() throws SQLException {
        Customer customer = new Customer();
        customer.setName("John Doe");
        customer.setContactNo("0112123456");

        assertTrue(customerDAO.save(customer));
    }

    @Test
    void getAll() throws SQLException {
//        CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getInstance().getDAO(DAOTypes.CUSTOMER);
//        customerDAO.setConnection(connection);

        List<Customer> customerList = customerDAO.getAll();
        assertEquals(0, customerList.size()); // since database has no customer records.
        System.out.println(customerDAO.getAll());
    }
}