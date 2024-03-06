    package CasoUso21 ;


    import java.sql.Connection ;
    import java.sql.DriverManager ;
    import java.sql.SQLException ;
    import java.sql.Statement ;
    import java.util.logging.Level ;
    import java.util.logging.Logger ;
    import java.sql.ResultSet ;
    import javax.swing.JOptionPane ;
/**
 *
 * @author star
 */
public class EliminarProfesiones {

        public static void main(String[] args) {

            String usuario = "root";
            String password = "123456789";
            String url = "jdbc:mysql://localhost:3306/bd_timecrafters";
//        Connection  conexion;
            ResultSet rs;
//        
          try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String idProfesionString = JOptionPane.showInputDialog(null, "Ingrese el ID de la profesion a eliminar:");
            int idProfesion = Integer.parseInt(idProfesionString);

            Connection conexion = DriverManager.getConnection(url, usuario, password);
            Statement st = conexion.createStatement();


            String sql = "DELETE FROM profesiones WHERE profesiones_id = " + idProfesion;
            int filasAfectadas = st.executeUpdate(sql);
            

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "La profesion ha sido eliminada correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró ningúna profesion con el ID proporcionado.");
            }
            rs = st.executeQuery("SELECT * FROM profesiones");

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
