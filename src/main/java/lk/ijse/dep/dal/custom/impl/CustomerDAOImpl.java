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

import lk.ijse.dep.dal.CrudUtil;
import lk.ijse.dep.dal.custom.CustomerDAO;
import lk.ijse.dep.entity.Customer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {

    private static final String TABLE_NAME = "customer";
    private Connection connection;

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean save(Customer customer) throws SQLException {
        Integer generatedKey = CrudUtil.executeAndReturnGeneratedKeys(this.connection,
                "INSERT INTO " + TABLE_NAME + "(name, contact_no) VALUES (?,?)",
                customer.getName(), customer.getContactNo());
        System.out.println("Generated Key: " + generatedKey);
        return (generatedKey > 0);
    }

    @Override
    public boolean update(Customer customer) throws SQLException {
        return CrudUtil.execute(this.connection,
                "UPDATE " + TABLE_NAME + " SET name=?, contact_no=? WHERE id=?",
                customer.getName(), customer.getContactNo(), customer.getId());
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
        return CrudUtil.execute(this.connection,
                "DELETE FROM " + TABLE_NAME + " WHERE id=?",
                id);
    }

    @Override
    public Customer get(Integer id) throws SQLException {
        ResultSet rst = CrudUtil.execute(this.connection,
                "SELECT * FROM " + TABLE_NAME + " WHERE id=?",
                id);

        if (rst.next()) {
            return new Customer(rst.getInt("id"),
                    rst.getString("name"),
                    rst.getString("contact_no"));
        } else {
            return null;
        }
    }

    @Override
    public List<Customer> getAll() throws SQLException {
        ResultSet rst = CrudUtil.execute(this.connection,
                "SELECT * FROM " + TABLE_NAME);

        List<Customer> customerArrayList = new ArrayList<Customer>();

        while (rst.next()) {
            Customer customer = new Customer(rst.getInt("id"),
                    rst.getString("name"),
                    rst.getString("contact_no"));
            customerArrayList.add(customer);
            return customerArrayList;
        }

        return customerArrayList;
    }
}
