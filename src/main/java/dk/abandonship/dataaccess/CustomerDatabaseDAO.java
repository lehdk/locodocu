package dk.abandonship.dataaccess;

import dk.abandonship.dataaccess.interfaces.ICustomerDAO;
import dk.abandonship.entities.Customer;
import dk.abandonship.entities.CustomerDTO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CustomerDatabaseDAO implements ICustomerDAO {

    @Override
    public List<Customer> getAllCustomers() throws SQLException {
        List<Customer> customers = new ArrayList<>();

        try(var connection = DBConnector.getInstance().getConnection()) {
            String sql = "SELECT [Id], [Name], [Email], [Phone], [Address] FROM [Customer];";

            var statement = connection.prepareStatement(sql);

            var resultSet = statement.executeQuery();

            while(resultSet.next()) {
                customers.add(new Customer(
                        resultSet.getInt("Id"),
                        resultSet.getString("Name"),
                        resultSet.getString("Email"),
                        resultSet.getString("Phone"),
                        resultSet.getString("Address")
                ));
            }
        }

        return customers;
    }

    @Override
    public Customer addCustomer(CustomerDTO customerDTO) throws SQLException {
        try(var connection = DBConnector.getInstance().getConnection()) {
            String sql = "INSERT INTO [Customer] ([Name], [Phone], [Email], [Address]) VALUES (?,?,?,?);";

            var statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, customerDTO.getName());
            statement.setString(2, customerDTO.getPhone());
            statement.setString(3, customerDTO.getEmail());
            statement.setString(4, customerDTO.getAddress());

            statement.executeUpdate();

            try(var resultSet = statement.getGeneratedKeys()) {
                if(resultSet.next()) {
                    return customerDTO.convertToCustomer(resultSet.getInt(1));
                }
            }
        }

        return null;
    }

    @Override
    public boolean deleteCustomer(Customer customer) throws SQLException {

        try(var connection = DBConnector.getInstance().getConnection()) {
            String sql = "DELETE FROM [Customer] WHERE [Id]=?";

            var statement = connection.prepareStatement(sql);
            statement.setInt(1, customer.getId());

            int affectedRows = statement.executeUpdate();

            return affectedRows != 0;
        }
    }
}
