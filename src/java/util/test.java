/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package util;

import java.sql.Connection;
import java.sql.SQLException;
import model.sales;
import model.salesDao;

/**
 *
 * @author tpp
 */
public class test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        test t = new test();
        //t.testConexion();
        t.addVenta();
    }

    public void testConexion() {
        ConexionSingleton c = new ConexionSingleton();
        try {
            Connection connection = c.getConnection();
            if (connection != null && !connection.isClosed()) {
                System.out.println("Conexión establecida correctamente.");
                // Puedes realizar otras operaciones con la conexión aquí si lo deseas
            } else {
                System.out.println("No se pudo establecer la conexión.");
            }
        } catch (SQLException e) {
            System.out.println("Error al intentar establecer la conexión: " + e.getMessage());
            e.printStackTrace();
        }

    }

    public void addVenta() {
        sales v = new sales();
        salesDao vDao = new salesDao();

        v.setIdcustomer(1);
        v.setSerialNumber("V001");
        v.setSale_date("2024-05-02");
        v.setPrice(15.5);
        v.setStatus("1");

        // Llama al método addSale() una vez y guarda el resultado
        boolean registroCorrecto = vDao.addSale(v);

        // Verifica el resultado y muestra el mensaje correspondiente
        if (registroCorrecto) {
            System.out.println("Registro correcto");
        } else {
            System.out.println("Registro incorrecto");
        }
    }

}
