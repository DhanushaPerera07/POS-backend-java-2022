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
package lk.ijse.dep.business.custom.impl;

import lk.ijse.dep.business.custom.CustomerBO;
import lk.ijse.dep.dal.DAOFactory;
import lk.ijse.dep.dal.DAOTypes;
import lk.ijse.dep.dal.custom.CustomerDAO;
import lk.ijse.dep.dto.CustomerDTO;
import lk.ijse.dep.entity.Customer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerBOImpl implements CustomerBO {

    private CustomerDAO customerDAO;
    private Connection connection;

    public CustomerBOImpl() {
        customerDAO = DAOFactory.getInstance().getDAO(DAOTypes.CUSTOMER);
    }

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
        customerDAO.setConnection(connection);
    }

    @Override
    public boolean saveCustomer(CustomerDTO customerDTO) throws SQLException {
        return customerDAO.save(new Customer(customerDTO.getId(), customerDTO.getName(), customerDTO.getContactNo()));
    }

    @Override
    public boolean updateCustomer(CustomerDTO customerDTO) throws SQLException {
        return customerDAO.update(new Customer(customerDTO.getId(), customerDTO.getName(), customerDTO.getContactNo()));
    }

    @Override
    public boolean deleteCustomer(Integer customerId) throws SQLException {
        return customerDAO.delete(customerId);
    }

    @Override
    public List<CustomerDTO> findAllCustomers() throws SQLException {
        return customerDAO.getAll()
                .stream()
                .map((c) -> new CustomerDTO(c.getId(), c.getName(), c.getContactNo()))
                .collect(Collectors.toList());
    }
}