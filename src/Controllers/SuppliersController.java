
package Controllers;

import Models.EmployeeDAO;
import Models.Supplier;
import Models.SupplierDAO;
import Views.SystemView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;

public class SuppliersController implements ActionListener, MouseListener, KeyListener{

    //Variables
    private Supplier supplier;
    private SupplierDAO supplier_dao;
    private SystemView views;
    
    String rol = EmployeeDAO.rol_user;

    //contructor
    public SuppliersController(Supplier supplier, SupplierDAO supplier_dao, SystemView views) {
        this.supplier = supplier;
        this.supplier_dao = supplier_dao;
        this.views = views;
        
        //register /escucha
        this.views.btn_register_supplier.addActionListener(this);
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == views.btn_register_supplier){
            if(views.txt_supplier_name.getText().equals("")
                    || views.txt_supplier_description.getText().equals("")
                    || views.txt_supplier_address.getText().equals("")
                    || views.txt_supplier_telephone.getText().equals("")
                    || views.txt_supplier_email.getText().equals("")
                    || views.cbx_supplier_city.getSelectedItem().toString().equals("")){
                JOptionPane.showMessageDialog(null, "Todos los campos estan vacios");
            }else{
                supplier.setName(views.txt_supplier_name.getText().trim());
                supplier.setDescription(views.txt_supplier_description.getText().trim());
                supplier.setAddress(views.txt_supplier_address.getText().trim());
                supplier.setTelephone(views.txt_supplier_telephone.getText().trim());
                supplier.setEmail(views.txt_supplier_email.getText().trim());
                supplier.setCity(views.cbx_supplier_city.getSelectedItem().toString());
                if(supplier_dao.registerSuppliersQuery(supplier)){
                    JOptionPane.showMessageDialog(null, "Se registro empleado");
                }else{
                    JOptionPane.showMessageDialog(null, "Error al registrar empleado");
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
