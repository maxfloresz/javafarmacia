
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

public class SupplierDAO {
    
    PreparedStatement pst;
    ResultSet rs;
    
    //Registrar Proveedor
    public boolean registerSuppliersQuery(Supplier supplier){
        String query= "INSERT INTO suppliers(name,description,address,telephone,email,city,created,updated)"
                + "VALUES (?,?,?,?,?,?,?,?)";
        Timestamp dateTime = new Timestamp(new Date().getTime());
        
        try {
            Connection con = ConexionSQLite.getConnection();
            pst = con.prepareStatement(query);
            pst.setString(1, supplier.getName());
            pst.setString(2, supplier.getDescription());
            pst.setString(3, supplier.getAddress());
            pst.setString(4, supplier.getTelephone());
            pst.setString(5, supplier.getEmail());
            pst.setString(6, supplier.getCity());
            pst.setString(7, dateTime.toString());
            pst.setString(8, dateTime.toString());
            
            int rowEffected = pst.executeUpdate();
            
            return rowEffected > 0;
            
        } catch (SQLException e) {
            System.out.println("Error al registrar proveedor "+e.getMessage());
            return false;
        } finally {
            ConexionSQLite.closeConnection();
        }
    }
    
    
    //Listar proveedores
    public List listSuppliersQuery(String val){
        List<Supplier> list_supplier = new ArrayList<>();
        String query = "SELECT * FROM suppliers";
        String query_search_suppliers = "SELECT * FROM suppliers WHERE name LIKE '%"+val+"%'";
        
        try {
            Connection conn = ConexionSQLite.getConnection();
            if(val.equalsIgnoreCase("")){
                pst = conn.prepareStatement(query);
                rs = pst.executeQuery();
            }else{
                pst = conn.prepareStatement(query_search_suppliers);
                rs = pst.executeQuery();
            }
            
            while(rs.next()){
                Supplier suppliers = new Supplier();
                suppliers.setId(rs.getInt("id"));
                suppliers.setName(rs.getString("name"));
                suppliers.setDescription(rs.getString("description"));
                suppliers.setAddress(rs.getString("address"));
                suppliers.setTelephone(rs.getString("telephone"));
                suppliers.setEmail(rs.getString("email"));
                suppliers.setCity(rs.getString("city"));
                
                list_supplier.add(suppliers);
            }
        } catch (SQLException e) {
            System.out.println("NO se puedo establercer conexcion con base de datos "+e.getMessage());
        } finally {
            ConexionSQLite.closeConnection();
        }
        
        return list_supplier;
    }
    
    
    // Modificar proveedor
    public boolean updateSuppliersQuery(Supplier supplier){
        String query = "UPDATE suppliers SET name= ?, description=?, address=?, telephone=?, email=?, city=?,updated=?"
                + "WHERE id = ?";
        Timestamp dateTime = new Timestamp(new Date().getTime());
        
        try {
            Connection conn = ConexionSQLite.getConnection();
            pst = conn.prepareStatement(query);
            pst.setString(1, supplier.getName());
            pst.setString(2, supplier.getDescription());
            pst.setString(3, supplier.getAddress());
            pst.setString(4, supplier.getTelephone());
            pst.setString(5, supplier.getEmail());
            pst.setString(6, supplier.getCity());
            pst.setTimestamp(7, dateTime);
            pst.setInt(8, supplier.getId());
            
            int rowAffected = pst.executeUpdate();
            return rowAffected > 0;
            
        } catch (SQLException e) {
            System.out.println("No se actualizo el proveedor "+e.getMessage());
            return false;
            
        } finally {
            ConexionSQLite.closeConnection();
        }
        
    }
    
    // Eliminar proveedor
    public boolean deleteSuppliersQuery(int id){
        String query = "DELETE FROM suppliers WHERE id ="+id;
        
        try {
            Connection conn = ConexionSQLite.getConnection();
            pst = conn.prepareStatement(query);
            int rowAffected = pst.executeUpdate();
            return rowAffected > 0;
            
            
        } catch (SQLException e) {
            System.out.println("No se pudo eliminar el proveedor " + e.getMessage());
            return false;
        } finally {
            ConexionSQLite.closeConnection();
        }
    }
}
