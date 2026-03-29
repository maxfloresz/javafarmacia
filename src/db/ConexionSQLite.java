//https://www.youtube.com/watch?v=hxYpxp-kEno&list=PLffixYYr8M_uPiKk1VZOjhTHE8UGcQvKR&index=15
package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionSQLite {

    private static String database_name = "farmacia.db";

    private static Connection conn = null;

//    metodo par aobetner la conexion
    public static Connection getConnection() {
        if (conn == null) {
            try {
                //ruta del archivo .db
                String url = "jdbc:sqlite:D:\\PROGRAMAS\\sqliteBrowser\\proyectos_basesdedatos/" + database_name;

                conn = DriverManager.getConnection(url);
                System.out.println("Conexión establecida con SQLite.");
            } catch (SQLException e) {
                System.out.println("Error al conectar: " + e.getMessage());
            }

        }
        return conn;
    }

    //metodo para cerrar la conexion
    public static void closeConnection() {
        try {
            if (conn != null) {
                conn.close();
                conn = null;
                System.out.println("Conexion cerrada.");
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar conexion: " + e.getMessage());
        }
    }

}
