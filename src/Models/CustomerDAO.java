
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

public class CustomerDAO {
    
    //inicializamos conexion
    //aqui inicia el connection con = new Connection() //si no esbubiera como static
    PreparedStatement pst; // el qye ejecuta las consultas
    ResultSet rs; // el que revice los datos de la bd

    //Registrar Cliente
    public boolean registerCustomersQuery(Customer customer){
        String query = "INSERT INTO customers (full_name, address, telephone, email,created,updated) "
                + "VALUES (?,?,?,?,?,?)";
        
        Timestamp dateTime = new Timestamp(new Date().getTime());
        
        try {
            Connection conexion = ConexionSQLite.getConnection();
            pst = conexion.prepareStatement(query);
            pst.setString(1, customer.getFull_name());
            pst.setString(2, customer.getAddress());
            pst.setString(3, customer.getTelephone());
            pst.setString(4, customer.getEmail());
            pst.setTimestamp(5, dateTime);
            pst.setTimestamp(6, dateTime);
            
            pst.execute();
            return true;
            
        } catch (SQLException e) {
            System.out.println("Error al registrar el Cliente "+e.getMessage());
            return false;
        } finally {
            ConexionSQLite.closeConnection();
        }
        
    }
    
    
    //Listar Clientes
    public List listCustomersQuery(String value){
        List<Customer> list_customer = new ArrayList<>();
        String query = "SELECT * FROM customers";
        String query_search_customer = "SELECT * FROM customers WHERE id LIKE '%"+value+"%'";
        
        try {
            Connection con = ConexionSQLite.getConnection();
            
            if(value.equalsIgnoreCase("")){
                pst = con.prepareStatement(query);
                rs = pst.executeQuery();
            }else{
                pst = con.prepareStatement(query_search_customer);
                rs = pst.executeQuery();
            }
            
            while(rs.next()){
                Customer customer = new Customer();
                customer.setId(rs.getInt("id"));
                customer.setFull_name(rs.getString("full_name"));
                customer.setAddress(rs.getString("address"));
                customer.setTelephone(rs.getString("telephone"));
                customer.setEmail(rs.getString("email"));
                
                list_customer.add(customer);
            }
            
        } catch (SQLException e) {
            System.out.println("No se pudo acceder a los datos "+ e.getMessage());
        } finally {
            ConexionSQLite.closeConnection();
        }
        
        return list_customer;
    }
    
    
    //Modificar Cliente
    public boolean updateCustomersQuery(Customer customer){
        String query = "UPDATE customers SET full_name = ? , address = ? , telephone = ?, email= ? , updated = ?"
                + "WHERE id = ?";
        
        Timestamp dateTime = new Timestamp(new Date().getTime());
        
        try {
            Connection con = ConexionSQLite.getConnection();
            pst = con.prepareStatement(query);
            
            pst.setString(1, customer.getFull_name());
            pst.setString(2, customer.getAddress());
            pst.setString(3, customer.getTelephone());
            pst.setString(4, customer.getEmail());
            pst.setTimestamp(5, dateTime);
            pst.setInt(6, customer.getId());
            
            int rowsAffected = pst.executeUpdate(); //executeupdated devuelve cuantas filas fueron afectadas
            return rowsAffected > 0; //si es mayor a 0 las filas afectadas deuvel true
        } catch (Exception e) {
            System.out.println("Erro al actualizar los datos del cliente "+e.getMessage());
            return false;
        } finally {
            ConexionSQLite.closeConnection();
        }
    }
    
    
    //Eliminar cliente
    public boolean deleteCustomersQuery(int id){
        String query = "DELETE FROM customers WHERE id= ?";
        try {
            Connection con = ConexionSQLite.getConnection();
            pst = con.prepareStatement(query);
            
            int rowsAffected = pst.executeUpdate(); // devuelve cuantas filas afecto
            return rowsAffected > 0; // si afecto a 1 o mas filas en la eliminacion esta bien
            
        } catch (SQLException e) {
            System.out.println("NO se puede eliminar un cliente "+e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            ConexionSQLite.closeConnection();
        }
    }
}
