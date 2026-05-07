
package Controllers;

//https://www.youtube.com/watch?v=l1kDjXDqz8k&list=PLffixYYr8M_uPiKk1VZOjhTHE8UGcQvKR&index=53

import Models.DynamicComboBox;
import Models.EmployeeDAO;
import Models.Product;
import Models.ProductDAO;
import Models.Purchase;
import Models.PurchaseDAO;
import Views.SystemView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class PurchasesController implements ActionListener, MouseListener, KeyListener{
    
    private Purchase purchase;
    private PurchaseDAO purchase_dao;
    private SystemView views;
    Product products = new Product();
    ProductDAO products_dao = new ProductDAO();
    String rol = EmployeeDAO.rol_user;
    private int getIdSupplier = 0;
    private int item = 0;
    DefaultTableModel model = new DefaultTableModel();
    DefaultTableModel temp;

    public PurchasesController(Purchase purchase, PurchaseDAO purchase_dao, SystemView views) {
        this.purchase = purchase;
        this.purchase_dao = purchase_dao;
        this.views = views;
        
        this.views.txt_purchanse_product_code.addKeyListener(this);
        this.views.txt_purchanse_price.addKeyListener(this);
        
        //Agregar btn escucha
        this.views.btn_add_product_to_buy.addActionListener(this);
        //comprar escucha btn
        this.views.btn_confirm_purchase.addActionListener(this);
        //eliminar
        this.views.btn_remove_purchase.addActionListener(this);
        
    }
    
    

    @Override
    public void actionPerformed(ActionEvent e) {

        //agregar
        if(e.getSource() == views.btn_add_product_to_buy){
            DynamicComboBox suplier_cbx = (DynamicComboBox) views.cbx_purchase_supplier.getSelectedItem();
            int supplier_id = suplier_cbx.getId();
            if(getIdSupplier == 0){
                getIdSupplier = supplier_id;
            }else{
                if(getIdSupplier != supplier_id){
                    JOptionPane.showMessageDialog(null, "No puede realizar una misma compra con varios proveedores");
                }else{
                    int amount = Integer.parseInt(views.txt_purchanse_amount.getText());
                    String product_name = views.txt_purchanse_product_name.getText();
                    double price = Double.parseDouble(views.txt_purchanse_price.getText());
                    int puerchase_id = Integer.parseInt(views.txt_purchanse_id.getText());
                    String supplier_name = views.cbx_purchase_supplier.getSelectedItem().toString();
                    
                    if(amount > 0){
                        temp = (DefaultTableModel) views.jt_purchases_table.getModel();
                        for (int i = 0; i < views.jt_purchases_table.getRowCount(); i++) {
                            if(views.jt_purchases_table.getValueAt(i, 1).equals(views.txt_purchanse_product_name.getText())){
                                JOptionPane.showMessageDialog(null, "El producto ya esta registrado en la tabla de compras");
                                return;
                            }
                        }
                        ArrayList list = new ArrayList();
                        item = 1;
                        list.add(item);
                        list.add(puerchase_id);
                        list.add(product_name);
                        list.add(amount);
                        list.add(price);
                        list.add(amount * price);
                        list.add(supplier_name);
                        
                        Object[] obj = new Object[6];
                        obj[0] = list.get(1);
                        obj[1] = list.get(2);
                        obj[2] = list.get(3);
                        obj[3] = list.get(4);
                        obj[4] = list.get(5);
                        obj[5] = list.get(6);

                        temp.addRow(obj);
                        views.jt_purchases_table.setModel(temp);
                        cleanFieldsPurchases();
                        views.cbx_purchase_supplier.setEditable(false);
                        views.txt_purchanse_product_code.requestFocus();
                        calculatePurchase();
                    }
                }
            }
        }
        
        //comprar
        if(e.getSource() == views.btn_confirm_purchase){
            insertPurchase();
        }
        
        //eliminar
        if(e.getSource() == views.btn_remove_purchase){
            model = (DefaultTableModel) views.jt_purchases_table.getModel();
            model.removeRow(views.jt_purchases_table.getSelectedRow());
            calculatePurchase();
            views.txt_purchanse_product_code.requestFocus();
        }
    }
    
    public void cleanFieldsPurchases(){
        views.txt_purchanse_product_name.setText("");
        views.txt_purchanse_price.setText("");
        views.txt_purchanse_amount.setText("");
        views.txt_purchanse_product_code.setText("");
        views.txt_purchanse_subtotal.setText("");
        views.txt_purchanse_id.setText("");
        views.txt_purchanse_total_to_pay.setText("");
    }
    
    public void calculatePurchase(){
        double total = 0;
        int num_row = views.jt_purchases_table.getRowCount();
        for (int i = 0; i < num_row; i++) {
            total = total + Double.parseDouble(String.valueOf(views.jt_purchases_table.getValueAt(i, 4)));
            
        }
        views.txt_purchanse_total_to_pay.setText(""+total);
    }
    
//    https://www.youtube.com/watch?v=msKrDV1if6U&list=PLffixYYr8M_uPiKk1VZOjhTHE8UGcQvKR&index=59
    public void insertPurchase(){
        double total = Double.parseDouble(views.txt_purchanse_total_to_pay.getText());
        int employee_id = EmployeeDAO.id_user;
        if(purchase_dao.registerPurchaseQuery(getIdSupplier, employee_id, total)){
            int purchase_id = purchase_dao.purchaseId();
            for (int i = 0; i < views.jt_purchases_table.getRowCount(); i++) {
                int porduct_id = Integer.parseInt(views.jt_purchases_table.getValueAt(i, 0).toString());
                int purchase_amount = Integer.parseInt(views.jt_purchases_table.getValueAt(i, 2).toString());
                double purchase_price = Double.parseDouble(views.jt_purchases_table.getValueAt(i, 3).toString());
                double purchase_subtotal = purchase_price * purchase_amount;
                
                purchase_dao.registerPurchaseDetailQuery(purchase_id, purchase_price, purchase_amount, purchase_subtotal, porduct_id);
                
                
            }
            cleanTableTemp();
            JOptionPane.showMessageDialog(null, "Compra generada con exito!");
            cleanFieldsPurchases();
        }
    }
    
    public void cleanTableTemp(){
        for (int i = 0; i < temp.getRowCount(); i++) {
            temp.removeRow(i);
            i = i - 1;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        if(e.getSource() == views.txt_purchanse_product_code){
            if(e.getKeyCode() == KeyEvent.VK_ENTER){
                if(views.txt_purchanse_product_code.getText().equals("")){
                    JOptionPane.showMessageDialog(null, "Ingresa el codigo del producto");
                }else{
                    int id = Integer.parseInt(views.txt_purchanse_product_code.getText());
                    products = products_dao.searchCode(id);
                    views.txt_purchanse_product_name.setText(products.getName());
                    views.txt_purchanse_id.setText("" + products.getId());
                    views.txt_purchanse_amount.requestFocus();
                
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        if(e.getSource() == views.txt_purchanse_price){
            int quatity;
            double price = 0.0;
            if(views.txt_purchanse_amount.getText().equals("")){
                quatity = 1;
                views.txt_purchanse_price.setText(""+price);
                
            }else{
                quatity = Integer.parseInt(views.txt_purchanse_amount.getText());
                price = Double.parseDouble(views.txt_purchanse_price.getText());
                views.txt_purchanse_subtotal.setText(""+quatity * price);
            }
        }
        
    }
    
    
}
