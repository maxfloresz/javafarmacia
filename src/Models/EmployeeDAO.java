//https://www.youtube.com/watch?v=0xBf5fGtwQU&list=PLffixYYr8M_uPiKk1VZOjhTHE8UGcQvKR&index=16

package Models;

import db.ConexionSQLite;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;


public class EmployeeDAO {
    //Conectamos a bd // siempre y cuando esta en state sno es con un new y una linea mas
//    Connection conexion = ConexionSQLite.getConnection();
    PreparedStatement pst;// realizar las conulstas
    ResultSet rs;// ver las consultas obteneidas de la base de datos
    //Instanciar conexion
    
    
    
    // variables para enviar datos entre interfaces
    public static int id_user=0;
    public static String full_name = "";
    public static String username_user = "";
    public static String address_user = "";
    public static String telephone_user = "";
    public static String email_user ="";
    public static String rol_user ="";
    
    //Metodo login
    public Employee loginQuery(String user, String password){
        String query = "SELECT * FROM employees WHERE username = ? and password = ?";
        Employee employee = new Employee();
        
       
        
        try {
            
            Connection conexion = ConexionSQLite.getConnection();
            pst = conexion.prepareStatement(query);
            
            //enviarmos parametros
            pst.setString(1, user);
            pst.setString(2, password);
            
            rs = pst.executeQuery();
            
            if(rs.next()){
                System.out.println("-> ¡Registro encontrado en la DB!");
                employee.setId(rs.getInt("id"));
                id_user = employee.getId();
                
                employee.setFull_name(rs.getString("full_name"));
                full_name = employee.getFull_name();
                
                employee.setUsername(rs.getString("username"));
                username_user = employee.getUsername();
                
                employee.setAddress(rs.getString("address"));
                address_user = employee.getAddress();
                
                employee.setTelephone(rs.getString("telephone"));
                telephone_user = employee.getTelephone();
                
                employee.setEmail(rs.getString("email"));
                email_user = employee.getEmail();
                
                employee.setRol(rs.getString("rol"));
                rol_user  = employee.getRol();
                
            }else {
                System.out.println("-> No hay coincidencias en la DB para el usuario: " + user);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener al empleado" +e);
        }finally{
            ConexionSQLite.closeConnection();
        }
        return employee;
    }
    
    
//    https://www.youtube.com/watch?v=4zzaaGKt-2k&list=PLffixYYr8M_uPiKk1VZOjhTHE8UGcQvKR&index=17
    //Registrar empleado
    public boolean  registerEmployeeQuery(Employee employee){
        String query = "INSERT INTO employees (full_name, username, address, telephone, email, password, rol, created, updated)"
                + "VALUES (?,?,?,?,?,?,?,?,?)";
        Timestamp datetime = new Timestamp(new Date().getTime());
        System.out.println(datetime);
        try {
            Connection conexion = ConexionSQLite.getConnection();
            pst = conexion.prepareStatement(query);
            
//            pst.setInt(1, employee.getId()); //como mi base de datos puse autoincrementable es opcional
            pst.setString(1, employee.getFull_name());
            pst.setString(2, employee.getUsername());
            pst.setString(3, employee.getAddress());
            pst.setString(4, employee.getTelephone());
            pst.setString(5, employee.getEmail());
            pst.setString(6, employee.getPassword());
            pst.setString(7, employee.getRol());
            
            pst.setString(8, datetime.toString());
            pst.setString(9, datetime.toString());
            
            pst.execute();
            
            return true;
            
        } catch (SQLException e) {
            System.out.println("Error en el registro de empleado "+e.getMessage());
            return false;
        }
    }
    
    
    // Listar empleado
    public List listEmployeesQuery (String value){
        List<Employee> list_employees = new ArrayList<>();
        String query = "SELECT * FROM employees ORDER BY rol ASC";
        
        String query_search_employee = "SELECT * FROM employees WHERE id LIKE '%" + value + "%'";
        
        try {
            Connection conexion = ConexionSQLite.getConnection();
            if(value.equalsIgnoreCase("")){
                pst = conexion.prepareStatement(query);
                rs = pst.executeQuery();
            }else{
                pst = conexion.prepareStatement(query_search_employee);
                rs = pst.executeQuery();
                
                
            }
            while (rs.next()) {                
                Employee employee = new Employee();
                
                employee.setId(rs.getInt("id"));
                employee.setFull_name(rs.getString("full_name"));
                employee.setUsername(rs.getString("username"));
                employee.setAddress(rs.getString("address"));
                employee.setTelephone(rs.getString("telephone"));
                employee.setEmail(rs.getString("email"));
                employee.setRol(rs.getString("rol"));
                
                // ahora enviamos a la lista
                list_employees.add(employee);
            }
            
        } catch (SQLException e) {
            System.out.println("Error al consultar empleados "+e.getMessage());
        }finally{
            ConexionSQLite.closeConnection();
        }
        
        return list_employees;
    }
    
    
//    https://www.youtube.com/watch?v=_rR3NRbJxQo&list=PLffixYYr8M_uPiKk1VZOjhTHE8UGcQvKR&index=18
    // Modificar empleado
    public boolean updateEmployeeQuery( Employee employee){
        String query = "UPDATE employees SET full_name = ?, username=?, address=?,"
                + "telephone=?, email=?, rol=?, updated=?"
                + "WHERE id = ?";
        
        Timestamp dateTime = new Timestamp(new Date().getTime());
        
        try {
            Connection conexion = ConexionSQLite.getConnection();
            
            pst = conexion.prepareStatement(query);
            pst.setString(1, employee.getFull_name());
            pst.setString(2, employee.getUsername());
            pst.setString(3, employee.getAddress());
            pst.setString(4, employee.getTelephone());
            pst.setString(5, employee.getEmail());
            pst.setString(6, employee.getRol());
            pst.setString(7, dateTime.toString());
            pst.setInt(8, employee.getId());
            
            pst.execute();
            
            return true;
            
        } catch (SQLException e) {
            System.out.println("No se pudo actualizar los datos del empleado "+e.getMessage());
            return false;
        } finally {
            ConexionSQLite.closeConnection();
        }
    }
    
    //Eliminar empleado
    public boolean deleteEmployeeQuery(int id){
        String query = "DELETE FROM employees WHERE id ="+id;
        try {
            Connection conexion = ConexionSQLite.getConnection();
            
            pst = conexion.prepareStatement(query);
            pst.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("No se puede eliminar un empleado que tena realcion con otra tabla "+e.getMessage());
            return false;
        } finally {
            ConexionSQLite.closeConnection();
        }
    }
    
    //Cambiar Password
    public boolean deleteEmployeePassword(Employee employee){
        String quey = "UPDATE employees SET password = ? WHERE username = "+username_user+"";
        
        try {
            Connection conexion = ConexionSQLite.getConnection();
            pst= conexion.prepareStatement(quey);
            pst.setString(1, employee.getPassword());
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al actualizar la contraseña "+e.getMessage());
            return false;
        } finally {
            ConexionSQLite.closeConnection();
        }
    }
}
