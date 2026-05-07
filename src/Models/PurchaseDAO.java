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

public class PurchaseDAO {

//    inicializar conexiones
    PreparedStatement pst;
    ResultSet rs;

    //Regostrar Compra
    public boolean registerPurchaseQuery(int suplier_id, int employee_id, double total) {
        String query = "INSERT INTO purchases (supplier_id, employee_id, total, created)"
                + "VALUES (?,?,?,?)";

        Timestamp dateTime = new Timestamp(new Date().getTime());

        try {
            Connection conexion = ConexionSQLite.getConnection();
            pst = conexion.prepareStatement(query);
            pst.setInt(1, suplier_id);
            pst.setInt(2, employee_id);
            pst.setDouble(3, total);
            pst.setString(4, dateTime.toString());

            int rowAffected = pst.executeUpdate();
            return rowAffected > 0;

        } catch (SQLException e) {
            System.out.println("Error al ingresar la compra " + e.getMessage());
            return false;
        } finally {
            ConexionSQLite.closeConnection();
        }

    }

    //Registrar Detalle de compra
    public boolean registerPurchaseDetailQuery(int purchase_id, double purchase_price, int purchase_amount, double purchase_subtotal, int product_id) {
        String query = "INSERT INTO purchase_details (purchase_id, purchase_price, purchase_amount, "
                + "purchase_subtotal, product_id) VALUES (?,?,?,?,?)";
//        Timestamp dateTime = new Timestamp(new Date().getTime());
        try {
            Connection conexion = ConexionSQLite.getConnection();
            pst = conexion.prepareStatement(query);
            pst.setInt(1, purchase_id);
            pst.setDouble(2, purchase_price);
            pst.setInt(3, purchase_amount);
            pst.setDouble(4, purchase_subtotal);
            pst.setInt(5, product_id);

            int rowAffected = pst.executeUpdate();
            return rowAffected > 0;

        } catch (SQLException e) {
            System.out.println("Error al registrar detalle de la compra " + e.getMessage());
            return false;
        } finally {
            ConexionSQLite.closeConnection();
        }

    }

    //Obtener ID de la compra
    public int purchaseId() {
        int id = 0;
        String query = "SELECT MAX(id) AS id FROM purchases";
        try {
            Connection conexion = ConexionSQLite.getConnection();
            pst = conexion.prepareStatement(query);
            rs = pst.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println("Error id de compra " + e.getMessage());
        } finally {
            ConexionSQLite.closeConnection();
        }

        return id;
    }

//    https://www.youtube.com/watch?v=LC4DcCMuSvU&list=PLffixYYr8M_uPiKk1VZOjhTHE8UGcQvKR&index=28
    //Listar todas las compras
    public List listAllPurchasesQuery() {
        List<Purchase> list_purchases = new ArrayList<>();
        String query = "SELECT pu.*, su.name as supplier_name FROM purchases pu INNER JOIN suppliers su "
                + "ON pu.supplier_id = su.id ORDER BY pu.id ASC";

        try {
            Connection conexion = ConexionSQLite.getConnection();
            pst = conexion.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {
                Purchase purchase = new Purchase();
                purchase.setId(rs.getInt("id"));
                purchase.setSupplier_name_product(rs.getString("supplier_name"));
                purchase.setTotal(rs.getDouble("total"));
                purchase.setCreated(rs.getString("created"));
                list_purchases.add(purchase);
            }
        } catch (SQLException e) {
            System.out.println("Error al obetener lista de todas las compras " + e.getMessage());
        } finally {
            ConexionSQLite.closeConnection();
        }
        return list_purchases;
    }

    //Listar compras para imprimir factura
    public List listPurchaseDetailQuery(int id) {
        List<Purchase> list_purchases = new ArrayList<>();
        String query = "SELECT pu.created, pude.purchase_price, pude.purchase_amount,\n"
                + "pude.purchase_subtotal, su.name as supplier_name,\n"
                + "pro.name as product_name, em.full_name\n"
                + "FROM purchases pu INNER JOIN purchase_details pude\n"
                + "ON pu.id = pude.purchase_id INNER JOIN products pro\n"
                + "ON pude.product_id = pro.id INNER JOIN suppliers su\n"
                + "ON pu.supplier_id = su.id INNER JOIN employees em \n"
                + "ON pu.employee_id= em.id WHERE pu.id = ?";

        try {
            Connection conexion = ConexionSQLite.getConnection();
            pst = conexion.prepareStatement(query);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            
            while(rs.next()){
                Purchase purchase = new Purchase();
                purchase.setProduct_name(rs.getString("product_name"));
                purchase.setPurchase_amount(rs.getInt("purchase_amount"));
                purchase.setPurchase_price(rs.getDouble("purchase_price"));
                purchase.setPurchase_subtotal(rs.getDouble("purchase_subtotal"));
                purchase.setSupplier_name_product(rs.getString("supplier_name"));
                purchase.setCreated(rs.getString("created"));
                purchase.setPurchase(rs.getString("full_name"));
                
                list_purchases.add(purchase);
            }
            
        } catch (SQLException e) {
            System.out.println("Error al consutar las compras para imprimir factura "+e.getMessage());
        } finally {
            ConexionSQLite.closeConnection();
        }
        
        return list_purchases;
        
    }

}
