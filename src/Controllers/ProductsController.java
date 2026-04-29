
package Controllers;

//https://www.youtube.com/watch?v=H4d3SNxVka0&list=PLffixYYr8M_uPiKk1VZOjhTHE8UGcQvKR&index=49

import Models.DynamicComboBox;
import Models.EmployeeDAO;
import Models.ProductDAO;
import Models.Product;
import Views.SystemView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;

public class ProductsController implements ActionListener, MouseListener, KeyListener{

    private Product product;
    private ProductDAO product_dao;
    private SystemView views;
    String rol = EmployeeDAO.rol_user;

    public ProductsController(Product product, ProductDAO product_dao, SystemView views) {
        this.product = product;
        this.product_dao = product_dao;
        this.views = views;
        //btn Register escucha
        this.views.btn_registrar_product.addActionListener(this);
    }
    
    
    
    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == views.btn_registrar_product){
            if(views.txt_product_code.getText().equals("")
                    || views.txt_product_name.getText().equals("")
                    || views.txt_product_description.getText().equals("")
                    || views.txt_product_unit_price.getText().equals("")
                    || views.cbx_product_category.getSelectedItem().toString().equals("")){
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
            }else{
                product.setCode(Integer.parseInt(views.txt_product_code.getText()));
                product.setName(views.txt_product_name.getText().trim());
                product.setDescription(views.txt_product_description.getText().trim());
                product.setUnit_price(Double.parseDouble(views.txt_product_unit_price.getText()));
                DynamicComboBox category_id = (DynamicComboBox) views.cbx_product_category.getSelectedItem();
                product.setCategory_id(category_id.getId());
                if(product_dao.registerProductQuery(product)){
                    JOptionPane.showMessageDialog(null, "Producto Registrado con exito");
                }else{
                    JOptionPane.showMessageDialog(null, "Error al registrar producto");
                }
            }
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

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
    
}
