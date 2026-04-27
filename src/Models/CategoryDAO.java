
package Models;

import db.ConexionSQLite;
import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CategoryDAO {
//    Inicializar conexion
    PreparedStatement pst;
    ResultSet rs;
    
    //Registrar Categoria
    public boolean registerCategoryQuery(Category category){
        String query = "INSERT INTO categories (name, created, updated) VALUES (?,?,?)";
        Timestamp dateTime = new Timestamp(new Date().getTime());
        
        try {
            Connection conn = ConexionSQLite.getConnection();
            pst = conn.prepareStatement(query);
            pst.setString(1, category.getName() );
            pst.setString(2, dateTime.toString());
            pst.setString(3, dateTime.toString());
            
            int rowAffected = pst.executeUpdate();
            return rowAffected >0;
        } catch (SQLException e) {
            System.out.println("Error al registrar la categoria "+e.getMessage());
            return false;
        } finally {
            ConexionSQLite.closeConnection();
        }
    
    }
    
//    https://www.youtube.com/watch?v=NFFCgjcCE3Y&list=PLffixYYr8M_uPiKk1VZOjhTHE8UGcQvKR&index=46
    //Listar categorias
    public List listCategoryQuery(String val){
        List<Category> list_categories  =new ArrayList<>();
        String query = "SELECT * FROM categories";
        String query_search_category = "SELECT * FROM categories WHERE name LIKE '%"+val+"%'";
        
        try {
            Connection conexion = ConexionSQLite.getConnection();
            if(val.equalsIgnoreCase("")){
                pst = conexion.prepareStatement(query);
                rs = pst.executeQuery();
            }else{
                pst = conexion.prepareStatement(query_search_category);
                rs = pst.executeQuery();
            }
            
            while (rs.next()) {                
                Category category = new Category();
                category.setId(rs.getInt("id"));
                category.setName(rs.getString("name"));
                list_categories.add(category);
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar las cateogries "+e.getMessage());
            
        } finally {
            ConexionSQLite.closeConnection();
        }
        return list_categories;
    }
    
    //Modificar categoria
    public boolean updateCategoryQuery(Category category){
        String query = "UPDATE categories SET name = ?, updated = ? WHERE id = ?";
        Timestamp dateTime = new Timestamp(new Date().getTime());
        
        try {
            Connection conexion = ConexionSQLite.getConnection();
            pst = conexion.prepareStatement(query);
            pst.setString(1, category.getName());
            pst.setString(2, dateTime.toString());
            pst.setInt(3, category.getId());
            
            int rowAffected = pst.executeUpdate();
            return rowAffected > 0;
            
        } catch (SQLException e) {
            System.out.println("Error al modificar la categoria "+e.getMessage());
            return false;
        } finally {
            ConexionSQLite.closeConnection();
        }
    }
    
    //Eliminar categoria
    public boolean deleteCategoryQuery(int id){
        String query = "DELETE FROM categories WHERE id ="+id;
        try {
            Connection conexion = ConexionSQLite.getConnection();
            pst = conexion.prepareStatement(query);
            int rowAffected = pst.executeUpdate();
            return rowAffected >0;
            
        } catch (SQLException e) {
            System.out.println("Error al eliminar la categoria "+e.getMessage());
            return false;
            
        } finally {
            ConexionSQLite.closeConnection();
        }
    }
}
