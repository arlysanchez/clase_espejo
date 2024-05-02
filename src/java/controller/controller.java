/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import model.product;
import model.productDao;
import model.sales;
import model.salesDao;
import util.GenerateSerie;

/**
 *
 * @author tpp
 */
public class controller extends HttpServlet {

    //delcaración de variables globales
    product p = new product();
    productDao pDao = new productDao();

    //
    sales v = new sales();
    List<sales> lista = new ArrayList<>();

    int idproduct;
    int idcustomer;
    int stock;
    String description_product;
    String serialNumber;
    double price;
    int amout;
    double subTotal;
    double TotalPay;

    String SerialNumber;
    salesDao vDao = new salesDao();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String op = request.getParameter("op");
        String accion = request.getParameter("accion");

        int idProduct;
        String name_product = request.getParameter("txt_name_product");
        String stock = request.getParameter("txt_stock");
        if (op.equals("Sales")) {
            v = new sales();
            switch (accion) {
                case "Producto":
                    idProduct = Integer.parseInt(request.getParameter("txt_cod_product"));
                    product pr = pDao.searchProduct(idProduct);
                    request.setAttribute("producto", pr);
                    request.setAttribute("lista", lista);
                    request.setAttribute("totalPay", TotalPay);
                    request.setAttribute("serial", serialNumber);
                    request.getRequestDispatcher("sales.jsp").forward(request, response);
                    break;
                case "Agregar":
                    TotalPay = 0.0;
                    idproduct = Integer.parseInt(request.getParameter("txt_cod_product"));
                    idcustomer = Integer.parseInt(request.getParameter("txt_cod_cliente"));
                    description_product = request.getParameter("txt_name_product");
                    price = Double.parseDouble(request.getParameter("txt_precio"));
                    amout = Integer.parseInt(request.getParameter("txt_cantidad"));
                    subTotal = price * amout;

                    v.setIdproduct(idproduct);
                    v.setDescription_product(description_product);
                    v.setPrice(price);
                    v.setAmount(amout);
                    v.setSubTotal(subTotal);
                    lista.add(v);
                    for (int i = 0; i < lista.size(); i++) {
                        TotalPay = TotalPay + lista.get(i).getSubTotal();
                    }
                    request.setAttribute("totalPay", TotalPay);
                    request.setAttribute("lista", lista);
                    request.setAttribute("serial", serialNumber);
                    request.getRequestDispatcher("sales.jsp").forward(request, response);
                    break;
                case "Generate_Venta":

                    for (int i = 0; i < lista.size(); i++) {
                        product p = new product();
                        int amout = lista.get(i).getAmount();
                        int idproduct = lista.get(i).getIdproduct();
                        productDao pDaoStock = new productDao();
                        p = pDaoStock.searchProduct(idproduct);

                        int stockAc = Integer.parseInt(p.getStock()) - amout;
                        pDaoStock.updateStock(idproduct, stockAc);
                    }
                    //save venta
                    v.setIdcustomer(idcustomer);
                    v.setSerialNumber(serialNumber);
                    v.setSale_date("23-01-01");
                    v.setStatus("1");
                    v.setPrice(TotalPay);
                    vDao.addSale(v);
                    //save detail venta
                    int idSalerecuperado = Integer.parseInt(vDao.MaxIdSales());
                    for (int i = 0; i < lista.size(); i++) {
                        v = new sales();
                        v.setIdsales(idSalerecuperado);
                        v.setIdproduct(lista.get(i).getIdproduct());
                        v.setAmount(lista.get(i).getAmount());
                        v.setPrice(lista.get(i).getPrice());
                        vDao.addSaleDetail(v);
                        lista.clear();

                    }
                    request.getRequestDispatcher("sales.jsp").forward(request, response);
                    break;
                case "Delete":
                    int idProductoEliminar = Integer.parseInt(request.getParameter("id"));
                    for (int i = 0; i < lista.size(); i++) {
                        if (lista.get(i).getIdproduct() == idProductoEliminar) {
                            lista.remove(i);
                            break;
                        }
                    }
                    request.getRequestDispatcher("sales.jsp").forward(request, response);
                    break;
                default:
                    serialNumber = vDao.GenerateSerieNumber();
                    if (serialNumber == null) {
                        serialNumber = "00000001";
                        request.setAttribute("serial", serialNumber);
                    } else {
                        int increment = Integer.parseInt(serialNumber);
                        GenerateSerie g = new GenerateSerie();
                        serialNumber = g.SerialNumber(increment);
                        request.setAttribute("serial", serialNumber);
                        request.getRequestDispatcher("sales.jsp").forward(request, response);
                    }
                    request.getRequestDispatcher("sales.jsp").forward(request, response);
            }

        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
