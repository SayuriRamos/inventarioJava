package com.example.inventario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ConexionBD {


    public static Connection getConexion() throws SQLException {
        String contrasena = "hola";
        String usuario = "newuser";
        String db = "jdbc:mysql://localhost:3306/inventario";
        return DriverManager.getConnection(db, usuario, contrasena);
    }


    public static boolean autentificarUsuario(String usuario, String contrasena) {
        try (Connection connection = getConexion()) {
            String query = "SELECT * FROM usuario WHERE usuario = ? AND contrasena = ?";

            PreparedStatement busqueda = connection.prepareStatement(query);
            busqueda.setString(1, usuario);
            busqueda.setString(2, contrasena);

            ResultSet resultado = busqueda.executeQuery();
            return resultado.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static ObservableList<Producto> obtenerProductos() {
        ObservableList<Producto> datos = FXCollections.observableArrayList();

        try (Connection connection = getConexion()) {
            String query = "select p.codigo, p.nombre, p.descripcion, p.precio, p.cantidadstock as cantidad, u.nombre as ubicacion from producto as p\n" +
                    "inner join ubicacion as u on u.ubicacionId = p.ubicacionId";

            PreparedStatement busqueda = connection.prepareStatement(query);

            ResultSet resultados = busqueda.executeQuery();

            while (resultados.next()) {
                Producto dataModel = new Producto();
                dataModel.setId(resultados.getInt("codigo"));
                dataModel.setNombre(resultados.getString("nombre"));
                dataModel.setDescripcion(resultados.getString("descripcion"));
                dataModel.setPrecio(resultados.getDouble("precio"));
                dataModel.setCantidad(resultados.getInt("cantidad"));
                dataModel.setUbicacion(resultados.getString("ubicacion"));

                datos.add(dataModel);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return datos;
    }

    public static boolean agregarProducto(String nombre, String descripcion, double precio, int cantidadstock, String ubicacionS) {
        try (Connection connection = getConexion()) {


            int ubicacion = getUbi(ubicacionS);

            String query = "insert into producto (nombre, descripcion, precio, cantidadstock, ubicacionId) values( ? , ? , ? , ? , ? );";

            PreparedStatement busqueda = connection.prepareStatement(query);
            busqueda.setString(1, nombre);
            busqueda.setString(2, descripcion);
            busqueda.setDouble(3, precio);
            busqueda.setInt(4, cantidadstock);
            busqueda.setInt(5, ubicacion);


            int rowsAffected = busqueda.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static int getUbi(String ubicacion) {
        try (Connection connection = getConexion()) {

            System.out.println(ubicacion);
            String query = "select ubicacionId from ubicacion where nombre = ?";

            PreparedStatement busqueda = connection.prepareStatement(query);
            busqueda.setString(1, ubicacion);


            ResultSet resultado = busqueda.executeQuery();

            if (resultado.next()) {
                return resultado.getInt("ubicacionId");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public static List<String> obtenerUbicaciones() {
        List<String> datos = new ArrayList<>();
        try (Connection connection = getConexion()) {
            String query = "select nombre from ubicacion";

            PreparedStatement busqueda = connection.prepareStatement(query);
            ResultSet resultado = busqueda.executeQuery();

            while (resultado.next()) {
                String nombre = resultado.getString("nombre");
                datos.add(nombre);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return datos;
    }
}