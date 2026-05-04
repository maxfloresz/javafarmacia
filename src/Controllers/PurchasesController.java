
package Controllers;

//https://www.youtube.com/watch?v=l1kDjXDqz8k&list=PLffixYYr8M_uPiKk1VZOjhTHE8UGcQvKR&index=53

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
import javax.swing.JOptionPane;

public class PurchasesController implements ActionListener, MouseListener, KeyListener{
    
    private Purchase purchase;
    private PurchaseDAO purchase_dao;
    private SystemView views;
    Product products = new Product();
    ProductDAO products_dao = new ProductDAO();
    String rol = EmployeeDAO.rol_user;

    public PurchasesController(Purchase purchase, PurchaseDAO purchase_dao, SystemView views) {
        this.purchase = purchase;
        this.purchase_dao = purchase_dao;
        this.views = views;
        
        this.views.txt_purchanse_product_code.addKeyListener(this);
        this.views.txt_purchanse_price.addKeyListener(this);
        
    }
    
    

    @Override
    public void actionPerformed(ActionEvent e) {

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
