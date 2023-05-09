package dk.abandonship.dataaccess;

import dk.abandonship.dataaccess.interfaces.ICustomerDAO;
import dk.abandonship.entities.Customer;
import dk.abandonship.entities.CustomerDTO;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CustomerDatabaseDAO implements ICustomerDAO {

    @Override
    public List<Customer> getAllCustomers() throws SQLException {
        List<Customer> customers = new ArrayList<>();

        try(var connection = DBConnector.getInstance().getConnection()) {
            String sql = "SELECT [Id], [Name], [Email], [Phone], [Address], [PostalCode] FROM [Customer];";

            var statement = connection.prepareStatement(sql);

            var resultSet = statement.executeQuery();

            while(resultSet.next()) {
                customers.add(new Customer(
                        resultSet.getInt("Id"),
                        resultSet.getString("Name"),
                        resultSet.getString("Email"),
                        resultSet.getString("Phone"),
                        resultSet.getString("Address"),
                        resultSet.getString("PostalCode")
                ));
            }
        }

        return customers;
    }

    @Override
    public Customer addCustomer(CustomerDTO customerDTO) throws SQLException {
        try(var connection = DBConnector.getInstance().getConnection()) {
            String sql = "INSERT INTO [Customer] ([Name], [Phone], [Email], [Address], [PostalCode]) VALUES (?,?,?,?,?);";

            var statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, customerDTO.getName());
            statement.setString(2, customerDTO.getPhone());
            statement.setString(3, customerDTO.getEmail());
            statement.setString(4, customerDTO.getAddress());
            statement.setString(5, customerDTO.getPostalCode());

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

    @Override
    public boolean editCustomer(Customer customer, CustomerDTO newData) throws SQLException {

        try(var connection = DBConnector.getInstance().getConnection()) {
            String sql = "UPDATE [Customer] SET [Name]=?,[Phone]=?,[Email]=?,[Address]=?,[PostalCode]=? WHERE [Id]=?";

            var statement = connection.prepareStatement(sql);
            statement.setString(1, newData.getName());
            statement.setString(2, newData.getPhone());
            statement.setString(3, newData.getEmail());
            statement.setString(4, newData.getAddress());
            statement.setString(5, newData.getPostalCode());
            statement.setInt(6, customer.getId());

            int affectedRows = statement.executeUpdate();

            return affectedRows != 0;
        }
    }
}
