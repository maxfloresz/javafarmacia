
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
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class SuppliersController implements ActionListener, MouseListener, KeyListener{

    //Variables
    private Supplier supplier;
    private SupplierDAO supplier_dao;
    private SystemView views;
    
    String rol = EmployeeDAO.rol_user;
    DefaultTableModel model = new DefaultTableModel();

    //contructor
    public SuppliersController(Supplier supplier, SupplierDAO supplier_dao, SystemView views) {
        this.supplier = supplier;
        this.supplier_dao = supplier_dao;
        this.views = views;
        
        //register /escucha
        this.views.btn_register_supplier.addActionListener(this);
        //tabla
        this.views.jt_supplier_table.addMouseListener(this);
        //buscar /escucha
        this.views.txt_search_supplier.addKeyListener(this);
        //
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
                    cleanTable();
                    listAllSuppliers();
                    JOptionPane.showMessageDialog(null, "Se registro empleado");
                }else{
                    JOptionPane.showMessageDialog(null, "Error al registrar empleado");
                }
                
            }
        }
        
    }

    //listar Proveedores
    public void listAllSuppliers(){
        if(rol.equals("Administrador")){
            List<Supplier> list = supplier_dao.listSuppliersQuery(views.txt_search_supplier.getText());
            model = (DefaultTableModel) views.jt_supplier_table.getModel();
            Object[] row = new Object[7];
            for(int i=0; i < list.size(); i++){
                row[0] = list.get(i).getId();
                row[1] = list.get(i).getName();
                row[2] = list.get(i).getDescription();
                row[3] = list.get(i).getAddress();
                row[4] = list.get(i).getTelephone();
                row[5] = list.get(i).getEmail();
                row[6] = list.get(i).getCity();
                model.addRow(row);
            }
            views.jt_supplier_table.setModel(model);
        }
    }
    
    
    @Override
    public void mouseClicked(MouseEvent e) {

        if(e.getSource() == views.jt_supplier_table){
            int row = views.jt_supplier_table.rowAtPoint(e.getPoint());
            views.txt_supplier_id.setText(views.jt_supplier_table.getValueAt(row, 0).toString());
            views.txt_supplier_name.setText(views.jt_supplier_table.getValueAt(row, 1).toString());
            views.txt_supplier_description.setText(views.jt_supplier_table.getValueAt(row, 2).toString());
            views.txt_supplier_address.setText(views.jt_supplier_table.getValueAt(row, 3).toString());
            views.txt_supplier_telephone.setText(views.jt_supplier_table.getValueAt(row, 4).toString());
            views.txt_supplier_email.setText(views.jt_supplier_table.getValueAt(row, 5).toString());
            views.cbx_supplier_city.setSelectedItem(views.jt_supplier_table.getValueAt(row, 6).toString());
            
            views.btn_register_supplier.setEnabled(false);
            views.txt_supplier_id.setEditable(false);
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

        if(e.getSource() == views.txt_search_supplier){
            cleanTable();
            listAllSuppliers();
        }
    }
    
    //metodo limpiar
    public void cleanTable(){
        for(int i = 0 ; i < model.getRowCount(); i++){
            model.removeRow(i);
            i = i-1;
        }
    }
}
