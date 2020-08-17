package Model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProductManager {

    public static Iterator<Product> getProducts(){
        List<Product> products = new ArrayList<>();
        Connection connection = DatabaseConnector.getInstance();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery (
                    "SELECT " + "*" +
                            " FROM " + DatabaseConfig.TABLE_NAME
            );

            while (resultSet.next()){
               Product product = getProductFromResult(resultSet);
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products == null ? null : products.iterator();
    }

    public static Product getProduct(String productid){
        Statement statement = null;
        try {
            Connection connection = DatabaseConnector.getInstance();
            statement = connection.createStatement();
            String query = "SELECT * FROM " + DatabaseConfig.TABLE_NAME +
                    " WHERE productid = \"" + productid + "\";";
            ResultSet resultSet = statement.executeQuery(query);
            if(resultSet.next()){
                return getProductFromResult(resultSet);
            }
        } catch (SQLException unused) {}
        return null;
    }

    private static Product getProductFromResult(ResultSet resultSet){
        String productid = null;
        try {
            productid = resultSet.getString("productid");
            String name = resultSet.getString("name");
            String imagefile = resultSet.getString("imagefile");
            double price = resultSet.getDouble("price");
            Product product = new Product(productid, name, imagefile, price);
            return product;
        } catch (SQLException e) {
            return null;
        }
    }
}
