
package Models;

import db.ConexionSQLite;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductDAO {
    
    //inicializacion conexion
    PreparedStatement pst;
    ResultSet rs;
    
    // Registrar productos
    public boolean registerProductQuery(Product product){
        String query = "INSERT INTO products (code, name,description, unit_price, created, updated, category_id)"
                + "VALUES (?,?,?,?,?,?,?)";
        
        Timestamp dateTime = new Timestamp(new Date().getTime());
        
        try {
            Connection conexion = ConexionSQLite.getConnection();
            pst = conexion.prepareStatement(query);
            
            pst.setInt(1, product.getCode());
            pst.setString(2, product.getName());
            pst.setString(3, product.getDescription());
            pst.setDouble(4, product.getUnit_price());
            pst.setString(5, dateTime.toString());
            pst.setString(6, dateTime.toString());
            pst.setInt(7, product.getCategory_id());
            
            int rowAffected = pst.executeUpdate();
            return rowAffected > 0;
            
            
        } catch (SQLException e) {
            System.out.println("Error al guardar el producto "+e.getMessage());
            return false;
        } finally {
            ConexionSQLite.closeConnection();
        }
    }
    
    
    //Listar productos
    public List listProductQuery(String val){
        List<Product> list_products = new ArrayList<>();
        String query = "SELECT pro.*, ca.name AS category_name FROM products pro, categories ca WHERE pro.category_id = ca.id";
        String query_search_products = "SELECT pro.*, ca.name AS category_name FROM products pro INNER JOIN categories ca"
                + "ON pro.category_id = ca.id WHERE pro.name LIKE '%"+val+"%'";
        
        try {
            Connection conexion = ConexionSQLite.getConnection();
            if(val.equalsIgnoreCase("")){
                pst = conexion.prepareStatement(query);
                rs = pst.executeQuery();
            }else{
                pst = conexion.prepareStatement(query_search_products);
                rs = pst.executeQuery();
            }
            
            while (rs.next()) {                
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setCode(rs.getInt("code"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setUnit_price(rs.getDouble("unit_price"));
                product.setProduct_quantity(rs.getInt("product_quantity"));
                product.setCategory_name(rs.getString("category_name"));
                
                list_products.add(product);
                
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener los datos "+e.getMessage());
            
        } finally {
            ConexionSQLite.closeConnection();
        }
        
        return list_products;
    }
    
    
//    https://www.youtube.com/watch?v=FNZD5L0YNBE&list=PLffixYYr8M_uPiKk1VZOjhTHE8UGcQvKR&index=25
    // Modificar productos
    public boolean updateProductQuery(Product product){
        String query ="UPDATE products SET code = ?, name = ?, description = ?, unit_price = ?, updated = ?, category_id = ? "
                + "WHERE id = ?";
        Timestamp dateTime = new Timestamp(new Date().getTime());
        try {
            
            Connection conexion = ConexionSQLite.getConnection();
            pst = conexion.prepareStatement(query);
            
            pst.setInt(1, product.getCode());
            pst.setString(2, product.getName());
            pst.setString(3, product.getDescription());
            pst.setDouble(4, product.getUnit_price());
            pst.setString(5, dateTime.toString());
            pst.setInt(6, product.getCategory_id());
            
            int rowAffected = pst.executeUpdate();
            return rowAffected > 0;
            
        } catch (SQLException e) {
            System.out.println("Error al actualizar el producto "+e.getMessage());
            return false;
        } finally {
            ConexionSQLite.closeConnection();
        }
    }
    
    //Eliminar producto
    public boolean deleteProductQuery(int id){
        String query = "DELETE FROM products WHERE id = "+id;
        
        try {
            Connection conexion = ConexionSQLite.getConnection();
            pst = conexion.prepareStatement(query);
            int rowAffected = pst.executeUpdate();
            return rowAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar el producto");
            return false;
        } finally {
            ConexionSQLite.closeConnection();
        }
    }
//    tiempo del video 18.20
    
    //Buscar Producto //cuando doy click el cualquier producto me va aprecer en los labeltext
    public Product searchProduct(int id){
        String query = "SELECT pro.*, ca.name as category_name FROM products pro"
                + "INNER JOIN categories ca ON pro.category_id = ca.id"
                + "WHERE pro.id = ?";
        Product product = new Product();
        
        try {
            Connection conexion = ConexionSQLite.getConnection();
            pst = conexion.prepareStatement(query);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            if(rs.next()){
                product.setId(rs.getInt("id"));
                product.setCode(rs.getInt("code"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setUnit_price(rs.getDouble("unit_price"));
                product.setProduct_quantity(rs.getInt("product_quantity"));
                product.setCategory_id(rs.getInt("category_id"));
                product.setCategory_name(rs.getString("category_name"));
                
            }
        } catch (SQLException e) {
            System.out.println("Error en la consulta del producto "+e.getMessage());
            
        } finally {
            ConexionSQLite.closeConnection();
        }
        return product;
        
    }
    
    //Buscar productos por codigo
    public Product searchCode(int code){
        String query = "SELECT pro.id, pro.name FROM products pro"
                + "WHERE pro.code = ?";
        Product product = new Product();
        try {
            
            Connection conexion = ConexionSQLite.getConnection();
            pst = conexion.prepareStatement(query);
            pst.setInt(1, code);
            rs = pst.executeQuery();
            if (rs.next()) {
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println("Error el busqueda por producto "+e.getMessage());
        } finally {
            ConexionSQLite.closeConnection();
        }
        
        return product;
    }
    
    //Traer la cantidad de productos
    public Product searchId(int id){
        String query = "SELECT pro.product_cuantity FROM products pro"
                + "WHERE pro.id = ?";
        Product product = new Product();
        
        try {
            Connection conexion = ConexionSQLite.getConnection();
            pst = conexion.prepareStatement(query);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            if (rs.next()) {
                product.setProduct_quantity(rs.getInt("product_cuantity"));
                
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar la cantidad de productos "+e.getMessage());
        }finally{
            ConexionSQLite.closeConnection();
        }
        return product;
    }
    
    //Actualizar Stock
    public boolean updateStockQuery(int amount, int product_id){
        String query = "UPDATE products SET product_cuantity = ?"
                + "WHERE id = ?";
        try {
            Connection conexion = ConexionSQLite.getConnection();
            pst = conexion.prepareStatement(query);
            pst.setInt(1, amount);
            pst.setInt(2, product_id);
            int rowAffected =  pst.executeUpdate();
            return rowAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar la cantidad de product "+e.getMessage());
            return false;
        } finally {
            ConexionSQLite.closeConnection();
        }
    }
}

