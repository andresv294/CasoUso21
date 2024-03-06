
package CasoUso21;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;

public class InsertarProfesiones {

    public static void main(String[] args) {

        String usuario = "root";
        String password = "123456789";
        String url = "jdbc:mysql://localhost:3306/bd_timecrafters";

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");


            Connection conexion = DriverManager.getConnection(url, usuario, password);
            
            String nombreProfesion = JOptionPane.showInputDialog("Ingrese el nombre de la profesión:");

                        String sqlInsert = "INSERT INTO profesiones(profesiones_nombre) VALUES (?)";
            try (PreparedStatement statement = conexion.prepareStatement(sqlInsert)) {
                statement.setString(1, nombreProfesion);
                statement.executeUpdate();
                JOptionPane.showMessageDialog(null, "Datos insertados correctamente.");
            }

            // Consultar los datos después de la inserción
            try (Statement st = conexion.createStatement();
                 ResultSet rs = st.executeQuery("SELECT * FROM profesiones")) {

                // Mostrar los datos
                System.out.println("ID\tNombre profesión");
                while (rs.next()) {
                    System.out.println(rs.getInt("profesiones_id") + "\t" + rs.getString("profesiones_nombre"));
                }
            }

        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        } 
    }
}
