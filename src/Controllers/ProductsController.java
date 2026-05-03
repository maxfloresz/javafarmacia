
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
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ProductsController implements ActionListener, MouseListener, KeyListener{

    private Product product;
    private ProductDAO product_dao;
    private SystemView views;
    String rol = EmployeeDAO.rol_user;
    
    DefaultTableModel model = new DefaultTableModel();

    public ProductsController(Product product, ProductDAO product_dao, SystemView views) {
        this.product = product;
        this.product_dao = product_dao;
        this.views = views;
        //btn Register escucha
        this.views.btn_registrar_product.addActionListener(this);
        //tabla
        this.views.jt_product_table.addMouseListener(this);
        //txt buscar escucha
        this.views.txt_search_product.addKeyListener(this);
        //btn modificar escucha
        this.views.btn_update_product.addActionListener(this);
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
                    cleanTable();
                    cleanFields();
                    listAllProducts();
                    JOptionPane.showMessageDialog(null, "Producto Registrado con exito");
                }else{
                    JOptionPane.showMessageDialog(null, "Error al registrar producto");
                }
            }
        }
        
        //Actualizar
        if(e.getSource() == views.btn_update_product){
            if(views.txt_product_code.getText().equals("")
                    || views.txt_product_name.getText().equals("")
                    || views.txt_product_description.getText().equals("")
                    || views.txt_product_unit_price.getText().equals("")
                    || views.cbx_product_category.getSelectedItem().toString().equals("")){
                JOptionPane.showMessageDialog(null, "Todos los camops son obligatorio");
            }else{
                product.setCode(Integer.parseInt(views.txt_product_code.getText()));
                product.setName(views.txt_product_name.getText().trim());
                product.setDescription(views.txt_product_description.getText().trim());
                product.setUnit_price(Double.parseDouble(views.txt_product_unit_price.getText()));
                DynamicComboBox category_id = (DynamicComboBox) views.cbx_product_category.getSelectedItem();
                product.setCategory_id(category_id.getId());
                product.setId(Integer.parseInt(views.txt_product_id.getText()));
                if(product_dao.updateProductQuery(product)){
                    cleanTable();
                    cleanFields();
                    listAllProducts();
                    JOptionPane.showMessageDialog(null, "Producto modificado con exito");
                }else{
                    JOptionPane.showMessageDialog(null, "Error al modifica el producto");
                }
            }
        }
    }
    public void cleanFields(){
        views.txt_product_id.setText("");
        views.txt_product_code.setText("");
        views.txt_product_name.setText("");
        views.txt_product_description.setText("");
        views.txt_product_unit_price.setText("");
    }
    
//    https://www.youtube.com/watch?v=H4d3SNxVka0&list=PLffixYYr8M_uPiKk1VZOjhTHE8UGcQvKR&index=49
    public void listAllProducts(){
        if(rol.equals("Administrador") || rol.equals("Auxiliar")){
            List<Product> list = product_dao.listProductQuery(views.txt_search_product.getText());
            model = (DefaultTableModel) views.jt_product_table.getModel();
            Object[] row = new Object[7];
            for (int i = 0; i < list.size(); i++) {
                row[0] = list.get(i).getId();
                row[1] = list.get(i).getCode();
                row[2] = list.get(i).getName();
                row[3] = list.get(i).getDescription();
                row[4] = list.get(i).getUnit_price();
                row[5] = list.get(i).getProduct_quantity();
                row[6] = list.get(i).getCategory_name();
                model.addRow(row);
                
            }
            views.jt_product_table.setModel(model);
            if(rol.equals("Auxiliar")){
                views.btn_registrar_product.setEnabled(false);
                views.btn_update_product.setEnabled(false);
                views.btn_delete_product.setEnabled(false);
                views.btn_cancel_product.setEnabled(false);
                views.txt_product_code.setEditable(false);
                views.txt_product_description.setEditable(false);
                views.txt_product_id.setEditable(false);
                views.txt_product_name.setEditable(false);
                views.txt_product_unit_price.setEditable(false);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if(e.getSource() == views.jt_product_table){
            int row = views.jt_product_table.rowAtPoint(e.getPoint());
            views.txt_product_id.setText(views.jt_product_table.getValueAt(row, 0).toString());
            product = product_dao.searchProduct(Integer.parseInt(views.txt_product_id.getText()));
            views.txt_product_code.setText(""+ product.getCode());
            views.txt_product_name.setText(product.getName());
            views.txt_product_description.setText(product.getDescription());
            views.txt_product_unit_price.setText(""+ product.getUnit_price());
            views.cbx_product_category.setSelectedItem(new DynamicComboBox(product.getCategory_id(), product.getCategory_name()));
            views.btn_registrar_product.setEnabled(false);
            
            
        }
        
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

        if(e.getSource() == views.txt_search_product){
            cleanTable();
            listAllProducts();
        }
        
    }
    
    public void cleanTable(){
        for (int i = 0; i < model.getRowCount(); i++) {
                model.removeRow(i);
                i = i -1;
            }
    }
    
}
