
package CasoUso21;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author star
 */
public class ActualizarProfesiones {

    public static void main(String[] args) {

        String usuario = "root";
        String password = "123456789";
        String url = "jdbc:mysql://localhost:3306/bd_timecrafters";
        Statement statement;
        ResultSet rs;
//        
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conexion = DriverManager.getConnection(url, usuario, password);
            Statement st = conexion.createStatement();
            
            String idProfesionString = JOptionPane.showInputDialog("Ingrese el ID de la profesion a actualizar:");
            int idProfesion = Integer.parseInt(idProfesionString);

            String nuevoNombreProfesion = JOptionPane.showInputDialog("Ingrese el nuevo nombre de la profesion:");



            conexion = DriverManager.getConnection(url, usuario, password);
            statement = conexion.createStatement();

            String query = "UPDATE profesiones SET profesiones_nombre = '" + nuevoNombreProfesion + "' WHERE profesiones_id = " + idProfesion;
            statement.executeUpdate(query);
            
            //Insertar directamente sin pedir por teclado 
//            st.executeUpdate("INSERT INTO ambientes(ambientes_numero_ambiente, ambientes_numero_bloque) VALUES (201, 1)");
            rs = st.executeQuery("SELECT * FROM ambientes");

            System.out.println("ID\tNombre Profesion");
                while (rs.next()) {
                    System.out.println(rs.getInt("profesiones_id") + "\t" + rs.getString("profesiones_nombre"));
                }

            conexion.close();

        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }
}

