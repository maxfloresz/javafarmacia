
package Controllers;

import Models.CustomerDAO;
import Models.Customer;
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

public class CustomersController implements ActionListener, MouseListener, KeyListener{

    //Encapsular variables
    private Customer customer;
    private CustomerDAO customer_dao;
    private SystemView views;
    DefaultTableModel model = new DefaultTableModel(); //para listar

    //Contructor
    public CustomersController(Customer customer, CustomerDAO customer_dao, SystemView views) {
        this.customer = customer;
        this.customer_dao = customer_dao;
        this.views = views;
        
        //Boton Registrar Cliente "Escucha"
        this.views.btn_register_customer.addActionListener(this);
        //Tabla "escucha" //cuando demos click a algun dato
        this.views.jt_customer_table.addMouseListener(this);
        //buscador
        this.views.txt_search_customer.addKeyListener(this);
        //Modificar btn
        this.views.btn_update_customer.addActionListener(this);
        //Eliminar
        this.views.btn_delete_customer.addActionListener(this);
        //cancelar
        this.views.btn_cancel_customer.addActionListener(this);
        //Clientes jlabel
        this.views.jLabelCustomers.addMouseListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == views.btn_register_customer){
            if(views.txt_customer_id.getText().equals("")
                    || views.txt_customer_fullname.getText().equals("")
                    || views.txt_customer_address.getText().equals("")
                    || views.txt_customer_telephone.getText().equals("")
                    || views.txt_customer_email.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
            }else{
                customer.setId(Integer.parseInt(views.txt_customer_id.getText().trim()));
                customer.setFull_name(views.txt_customer_fullname.getText().trim());
                customer.setAddress(views.txt_customer_address.getText().trim());
                customer.setEmail(views.txt_customer_email.getText().trim());
                customer.setTelephone(views.txt_customer_telephone.getText().trim());
                if(customer_dao.registerCustomersQuery(customer)){
                    cleanTable();
                    listAllCustomers();
                    JOptionPane.showMessageDialog(null, "Cliente registrado con Exito!");
                }else{
                    
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error al registrar el cliente");
                }
            }
        }
        
        //actualizar
        if(e.getSource() == views.btn_update_customer){
            if(views.txt_customer_id.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Seleccione una fila para continuar");
            }else{
                if(views.txt_customer_id.getText().equals("")
                        || views.txt_customer_fullname.getText().equals("")
                        || views.txt_customer_address.getText().equals("")
                        || views.txt_customer_telephone.getText().equals("")
                        || views.txt_customer_email.getText().equals("")){
                    JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
                }else{
                    customer.setId(Integer.parseInt(views.txt_customer_id.getText().trim()));
                    customer.setFull_name(views.txt_customer_fullname.getText().trim());
                    customer.setAddress(views.txt_customer_address.getText().trim());
                    customer.setEmail(views.txt_customer_email.getText().trim());
                    customer.setTelephone(views.txt_customer_telephone.getText().trim());
                    if(customer_dao.updateCustomersQuery(customer)){
                        cleanTable();
                        cleanFields();
                        listAllCustomers();
                        views.btn_register_customer.setEnabled(true);
                        JOptionPane.showMessageDialog(null, "Datos del cliente modificador con exito");
                    }else{
                        JOptionPane.showMessageDialog(null, "Ha ocurrido un error al modificar cliente");
                    }
                }
            }
        }
        
        //Eliminar
        if(e.getSource() == views.btn_delete_customer){
            int row = views.jt_customer_table.getSelectedRow();
            if(row == -1){
                JOptionPane.showMessageDialog(null, "Debes seleccionar un cliente para eliminar");
            }else{
                int id = Integer.parseInt(views.jt_customer_table.getValueAt(row, 0).toString());
                int question = JOptionPane.showConfirmDialog(null, "Seguro que quiere eliminar?");
                if(question == 0 && customer_dao.deleteCustomersQuery(id) != false){
                    cleanFields();
                    cleanTable();
                    listAllCustomers();
                    views.btn_register_customer.setEnabled(true);
                    JOptionPane.showMessageDialog(null, "Cliente eliminado correctamente");
                }
            }
        }
        
        //Cancelar
        if(e.getSource() == views.btn_cancel_customer){
            views.btn_register_customer.setEnabled(true);
            cleanFields();
        }
    }
    
    
    //limpiar los campos de texto
    public void cleanFields(){
        views.txt_customer_id.setText("");
        views.txt_customer_id.setEditable(true);
        views.txt_customer_id.setEnabled(true);
        views.txt_customer_fullname.setText("");
        views.txt_customer_address.setText("");
        views.txt_customer_telephone.setText("");
        views.txt_customer_email.setText("");
    }
    
//    https://www.youtube.com/watch?v=xpYnYO7u8p0&list=PLffixYYr8M_uPiKk1VZOjhTHE8UGcQvKR&index=38
    //Listar Clientes
    public void listAllCustomers(){
        List<Customer> list = customer_dao.listCustomersQuery(views.txt_search_customer.getText());
        model = (DefaultTableModel) views.jt_customer_table.getModel();
        Object[] row = new Object[5];
        for(int i= 0; i<list.size(); i++){
            row[0] = list.get(i).getId();
            row[1] = list.get(i).getFull_name();
            row[2] = list.get(i).getAddress();
            row[3] = list.get(i).getTelephone();
            row[4] = list.get(i).getEmail();
            
            model.addRow(row);
        }
        views.jt_customer_table.setModel(model);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() == views.jt_customer_table){
            int row = views.jt_customer_table.rowAtPoint(e.getPoint()); // para optener las cordenadas donde el usuario esta dando click
            views.txt_customer_id.setText(views.jt_customer_table.getValueAt(row, 0).toString());
            views.txt_customer_fullname.setText(views.jt_customer_table.getValueAt(row, 1).toString());
            views.txt_customer_address.setText(views.jt_customer_table.getValueAt(row, 2).toString());
            views.txt_customer_telephone.setText(views.jt_customer_table.getValueAt(row, 3).toString());
            views.txt_customer_email.setText(views.jt_customer_table.getValueAt(row, 4).toString());
            
            views.btn_register_customer.setEnabled(false);
            views.txt_customer_id.setEditable(false);
            views.txt_customer_id.setEnabled(false);
        }
        
        //para navegacion de ventanas
        if(e.getSource() == views.jLabelCustomers){
            views.jTabbedPane1.setSelectedIndex(3);
            cleanFields();
            cleanTable();
            listAllCustomers();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseReleased(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseEntered(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseExited(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    //keylisteners all / 4
    @Override
    public void keyTyped(KeyEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void keyPressed(KeyEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
        if(e.getSource() == views.txt_search_customer){
            cleanTable();
            listAllCustomers();
        }
        
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
//    metodo limpiar tabla
    public void cleanTable(){
        for(int i = 0; i < model.getRowCount(); i++){
            model.removeRow(i);
            i = i-1;
        }
    }
    
}
