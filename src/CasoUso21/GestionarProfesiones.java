package CasoUso21;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;

public class GestionarProfesiones {

    public static void main(String[] args) {
        String usuario = "root";
        String password = "123456789";
        String url = "jdbc:mysql://localhost:3306/bd_timecrafters";
        Connection conexion = null;
        Statement statement = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            boolean salir = false;

            while (!salir) {
                String opcion = JOptionPane.showInputDialog("Seleccione una opción:\n"
                        + "1. Insertar profesión\n"
                        + "2. Actualizar profesión\n"
                        + "3. Eliminar profesión\n"
                        + "4. Consultar profesión por ID\n"
                        + "5. Consultar todas las profesiones\n"
                        + "6. Salir");

                if (opcion != null) {
                    switch (opcion) {
                        case "1":
                            String nombreProfesion = JOptionPane.showInputDialog("Ingrese el nombre de la profesión:");
                            if (nombreProfesion != null) {
                                conexion = DriverManager.getConnection(url, usuario, password);
                                String sqlInsert = "INSERT INTO profesiones(profesiones_nombre) VALUES (?)";
                                try (PreparedStatement preparedStatement = conexion.prepareStatement(sqlInsert)) {
                                    preparedStatement.setString(1, nombreProfesion);
                                    preparedStatement.executeUpdate();
                                    JOptionPane.showMessageDialog(null, "La profesión se ha insertado correctamente.");
                                }
                                conexion.close();
                            }
                            break;

                        case "2":
                            String idProfesionString = JOptionPane.showInputDialog("Ingrese el ID de la profesión a actualizar:");
                            if (idProfesionString != null) {
                                int idProfesion = Integer.parseInt(idProfesionString);
                                String nuevoNombreProfesion = JOptionPane.showInputDialog("Ingrese el nuevo nombre de la profesión:");
                                if (nuevoNombreProfesion != null) {
                                    conexion = DriverManager.getConnection(url, usuario, password);
                                    statement = conexion.createStatement();
                                    String query = "UPDATE profesiones SET profesiones_nombre = '" + nuevoNombreProfesion + "' WHERE profesiones_id = " + idProfesion;
                                    statement.executeUpdate(query);
                                    JOptionPane.showMessageDialog(null, "La profesión se ha actualizado correctamente.");
                                    conexion.close();
                                }
                            }
                            break;

                        case "3":
                            String idEliminarString = JOptionPane.showInputDialog("Ingrese el ID de la profesión a eliminar:");
                            if (idEliminarString != null) {
                                int idEliminar = Integer.parseInt(idEliminarString);
                                conexion = DriverManager.getConnection(url, usuario, password);
                                statement = conexion.createStatement();
                                String sqlEliminar = "DELETE FROM profesiones WHERE profesiones_id = " + idEliminar;
                                int filasAfectadas = statement.executeUpdate(sqlEliminar);
                                if (filasAfectadas > 0) {
                                    JOptionPane.showMessageDialog(null, "La profesión ha sido eliminada correctamente.");
                                } else {
                                    JOptionPane.showMessageDialog(null, "No se encontró ninguna profesión con el ID proporcionado.");
                                }
                                conexion.close();
                            }
                            break;

                        case "4":
                            String idConsultarString = JOptionPane.showInputDialog("Ingrese el ID de la profesión a consultar:");
                            if (idConsultarString != null) {
                                int idConsultar = Integer.parseInt(idConsultarString);
                                conexion = DriverManager.getConnection(url, usuario, password);
                                statement = conexion.createStatement();
                                rs = statement.executeQuery("SELECT * FROM profesiones WHERE profesiones_id = " + idConsultar);
                                StringBuilder sb = new StringBuilder();
                                boolean encontrado = false; // Variable para controlar si se encontró la profesión
                                while (rs.next()) {
                                    sb.append("ID: ").append(idConsultar).append("\n");
                                    sb.append("Nombre: ").append(rs.getString("profesiones_nombre")).append("\n");
                                    encontrado = true;
                                }
                                if (!encontrado) {
                                    JOptionPane.showMessageDialog(null, "No se encontró ninguna profesión con el ID proporcionado.");
                                } else {
                                    JOptionPane.showMessageDialog(null, sb.toString());
                                }
                                conexion.close();
                            }
                            break;

                        case "5":
                            // Consultar todas las profesiones
                            conexion = DriverManager.getConnection(url, usuario, password);
                            statement = conexion.createStatement();
                            rs = statement.executeQuery("SELECT * FROM profesiones");
                            StringBuilder sb = new StringBuilder();
                            while (rs.next()) {
                                sb.append("ID: ").append(rs.getInt("profesiones_id")).append(" \tNombre: ").append(rs.getString("profesiones_nombre")).append("\n");
                            }
                            JOptionPane.showMessageDialog(null, sb.toString());
                            conexion.close();
                            break;

                        case "6":
                            salir = true;
                            break;

                        default:
                            JOptionPane.showMessageDialog(null, "Opción no válida.");
                            break;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Operación cancelada por el usuario.");
                    salir = true;
                }
            }

        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (conexion != null) {
                    conexion.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
