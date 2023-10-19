package com.example.inventario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ConexionBD {

    //Establecer conexion con la base de datos, aquí agregar usuario, contraseña y el localhost
    public static Connection getConexion() throws SQLException {
        String contrasena = "hola";
        String usuario = "newuser";
        String db = "jdbc:mysql://localhost:3306/inventario";
        return DriverManager.getConnection(db, usuario, contrasena);
    }


    //Verificar el usuario y contrasena del usuario para iniciar sesion en la BD
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

    //Obtener toda la lista de productos
    public static ObservableList<Producto> obtenerProductos() {
        ObservableList<Producto> datos = FXCollections.observableArrayList();

        try (Connection connection = getConexion()) {
            String query = "select p.codigo, p.nombre, p.descripcion, p.precio, p.cantidadstock as cantidad, u.nombre as ubicacion from producto as p\n" +
                    "inner join ubicacion as u on u.ubicacionId = p.ubicacionId";

            PreparedStatement busqueda = connection.prepareStatement(query);

            ResultSet resultados = busqueda.executeQuery();

            while (resultados.next()) {
                Producto modelo = new Producto();
                modelo.setId(resultados.getInt("codigo"));
                modelo.setNombre(resultados.getString("nombre"));
                modelo.setDescripcion(resultados.getString("descripcion"));
                modelo.setPrecio(resultados.getDouble("precio"));
                modelo.setCantidad(resultados.getInt("cantidad"));
                modelo.setUbicacion(resultados.getString("ubicacion"));

                datos.add(modelo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return datos;
    }

    //Agregar un producto nuevo
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


    //Obtener la ubicacion con base al nombre de la ubicacion
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

    //Obtener informacion del producto basandose en el código de este
    public static Producto getInfoProducto(String id) {
        try (Connection connection = getConexion()) {

            String query = "select * from producto where codigo = ?";

            PreparedStatement busqueda = connection.prepareStatement(query);
            busqueda.setInt(1, Integer.parseInt(id));

            ResultSet resultados = busqueda.executeQuery();
            Producto modelo = new Producto();

            while (resultados.next()) {

                modelo.setId(resultados.getInt("codigo"));
                modelo.setNombre(resultados.getString("nombre"));
                modelo.setDescripcion(resultados.getString("descripcion"));
                modelo.setPrecio(resultados.getDouble("precio"));
                modelo.setCantidad(resultados.getInt("cantidadstock"));
                modelo.setUbicacion(resultados.getString("ubicacionId"));

            }

            return modelo;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    //Obtener la lsiat de ubicaciones disponibles
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

    //Actualizar productos en la bd
    public static boolean actualizarProducto(int id, String nombre, String descripcion, double precio, int cantidadstock, String ubicacionS) {
        try (Connection connection = getConexion()) {


            int ubicacion = getUbi(ubicacionS);

            System.out.println(ubicacion);

            String query = "UPDATE producto\n" +
                    "SET nombre = ? ,\n" +
                    "    descripcion = ? ,\n" +
                    "    precio = ? ,\n" +
                    "    cantidadstock = ? ,\n" +
                    "    ubicacionId = ? \n" +
                    "WHERE codigo = ? ;";

            PreparedStatement busqueda = connection.prepareStatement(query);
            busqueda.setString(1, nombre);
            busqueda.setString(2, descripcion);
            busqueda.setDouble(3, precio);
            busqueda.setInt(4, cantidadstock);
            busqueda.setInt(5, ubicacion);
            busqueda.setInt(6, id);


            int rowsAffected = busqueda.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //Agregar productos al inventario mediante el id
    public static boolean sumarCantidadProducto(int id, int cantidad) {
        try (Connection connection = getConexion()) {

            System.out.println("Hola");


            String query = "update producto\n" +
                    "set cantidadstock = cantidadstock + ? \n" +
                    "where codigo = ? ;";

            PreparedStatement busqueda = connection.prepareStatement(query);
            busqueda.setInt(1, cantidad);
            busqueda.setInt(2, id);

            System.out.println("Hola2");


            int rowsAffected = busqueda.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    //Eliminar productos del inventario mediante el id
    public static boolean restarCantidadProducto(int id, int cantidad) {
        try (Connection connection = getConexion()) {

            String query = "update producto\n" +
                    "set cantidadstock = cantidadstock - ? \n" +
                    "where codigo = ? ;";

            PreparedStatement busqueda = connection.prepareStatement(query);
            busqueda.setInt(1, cantidad);
            busqueda.setInt(2, id);

            int rowsAffected = busqueda.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //Eliminar productos completos del inventario
    public static boolean eliminarProducto(int id) {
        try (Connection connection = getConexion()) {

            String query = "delete from producto\n" +
                    "where codigo = ? ;";

            PreparedStatement busqueda = connection.prepareStatement(query);
            busqueda.setInt(1, id);

            int rowsAffected = busqueda.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    //Obtener el reporte de movimientos
    public static ObservableList<Movimientos> obtenerMovimeintos() {
        ObservableList<Movimientos> datos = FXCollections.observableArrayList();

        try (Connection connection = getConexion()) {
            String query = "select m.idMovimiento as id, m.nombreProducto as producto, m.fecha, m.tipo, m.cantidad, u.nombre as ubicacion from movimientos as m inner join ubicacion as u on u.ubicacionId = m.ubicacionId;\n";

            PreparedStatement busqueda = connection.prepareStatement(query);

            ResultSet resultados = busqueda.executeQuery();

            while (resultados.next()) {
                Movimientos modelo = new Movimientos();
                modelo.setId(resultados.getInt("id"));
                modelo.setNombre(resultados.getString("producto"));
                modelo.setFecha(resultados.getDate("fecha"));
                modelo.setTipo(resultados.getString("tipo"));
                modelo.setCantidad(resultados.getInt("cantidad"));
                modelo.setUbicacion(resultados.getString("ubicacion"));

                datos.add(modelo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return datos;
    }


    //Obtener el reporte de ventas
    public static ObservableList<Ventas> obtenerVentasTotales() {
        ObservableList<Ventas> datos = FXCollections.observableArrayList();

        try (Connection connection = getConexion()) {
            String query = "SELECT \n" +
                    "DATE_FORMAT(fecha, '%Y-%m') AS Mes,\n" +
                    "(SELECT SUM(cantidad) from Movimientos where tipo = 'Entrada') AS EntradasTotales,\n" +
                    "(SELECT SUM(cantidad) from Movimientos where tipo = 'Salida') AS SalidasTotales\n" +
                    "FROM movimientos\n" +
                    "GROUP BY Mes ORDER BY Mes;";

            PreparedStatement busqueda = connection.prepareStatement(query);

            ResultSet resultados = busqueda.executeQuery();

            while (resultados.next()) {
                Ventas modelo = new Ventas();
                modelo.setMes(resultados.getString("mes"));
                modelo.setEntradasTotales(resultados.getInt("EntradasTotales"));
                modelo.setSalidasTotales(resultados.getInt("SalidasTotales"));

                datos.add(modelo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return datos;
    }

}
