/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import util.ConexionSingleton;

/**
 *
 * @author tpp
 */
public class productDao {
    private Connection cn;

 //listar productds
    public List<product> listarProducts() {
        List<product> lista = null;
        product pr;
        PreparedStatement st;
        ResultSet rs;
        String query = null;
        try {
            query = "SELECT * FROM product WHERE status = 1; ";
            lista = new ArrayList<>();
            cn = ConexionSingleton.getConnection();
            st = cn.prepareStatement(query);
            rs = st.executeQuery();
            //este while recorre todos los registro que trae el Objeto resultset
            while (rs.next()) {
                pr = new product();
                pr.setIdproduct(rs.getInt("idproduct"));
                pr.setName_product(rs.getString("name_product"));
                pr.setPrice(rs.getBigDecimal("price"));
                pr.setStock(rs.getString("stocks"));
                pr.setStatus(rs.getString("status"));
                lista.add(pr);
            }

        } catch (Exception e) {
            System.out.println("Error al Listar Productos: " + e.getMessage());
            try {
                cn.rollback();
            } catch (Exception ex) {
            }
            System.out.println("Erro. No se pudo listar al productos");
        } finally {
            if (cn != null) {
                try {
                } catch (Exception ex) {
                }
            }
        }
        return lista;
    
    }
    
  
    public product searchProduct(int id) {
        product pr = null;
        PreparedStatement st;
        ResultSet rs;
        String query = null;
        try {
            query = "SELECT * FROM product WHERE idproduct = ?";
            cn = ConexionSingleton.getConnection();
            st = cn.prepareStatement(query);
            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                pr = new product();
                pr.setIdproduct(rs.getInt("idproduct"));
                pr.setName_product(rs.getString("name_product"));
                pr.setPrice(rs.getBigDecimal("price"));
                pr.setStock(rs.getString("stocks"));
                pr.setStatus(rs.getString("status"));
            }
        } catch (Exception e) {
            System.out.println("Error buscar al producto ID: " + e.getMessage());
            try {
                cn.rollback();
            } catch (Exception ex) {
            }
            System.out.println("Erro. No se pudo buscar produto por ID");
        } finally {
            if (cn != null) {
                try {
                } catch (Exception ex) {
                }
            }
        }
        return pr;
    }
    

      
      public boolean updateStock(int id, int stock){
       boolean flag = false;
        PreparedStatement st;
        String query = null;

        try {
            query = "UPDATE product SET stocks = ? WHERE idproduct = ?";
            cn = ConexionSingleton.getConnection();
            st = cn.prepareStatement(query);
            st.setInt(1, stock);
            st.setInt(2, id);
            st.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error Actualizar stock del Product: " + e.getMessage());
            try {
                cn.rollback();
            } catch (Exception ex) {
            }
            flag = false;
            System.out.println("Erro. No se pudo Actualizar");
        } finally {
            if (cn != null) {
                try {
                } catch (Exception ex) {
                }
            }
        }
        return flag;   
      }
      
}
