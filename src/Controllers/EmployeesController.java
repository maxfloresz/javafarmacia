//https://www.youtube.com/watch?v=Qk-rz06xUIQ&list=PLffixYYr8M_uPiKk1VZOjhTHE8UGcQvKR&index=32
package Controllers;

import Models.EmployeeDAO;
import Models.Employee;
import static Models.EmployeeDAO.rol_user;
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

public class EmployeesController implements ActionListener, MouseListener, KeyListener{
    
    private Employee employee;
    private EmployeeDAO employee_dao;
    private SystemView views;

    String rol = rol_user;
    
    DefaultTableModel model = new DefaultTableModel(); //para trabjar con la tabla
    
    public EmployeesController(Employee employee, EmployeeDAO employee_dao, SystemView views) {
        this.employee = employee;
        this.employee_dao = employee_dao;
        this.views = views;
        
        //boton registrar empleado
        this.views.btn_register_employee.addActionListener(this);
        
        //Poner a la escucha de la tabla
        this.views.jt_employees_table.addMouseListener(this);
        //txt buscar a la escucha
        this.views.txt_search_employee.addKeyListener(this);
    }
    
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == views.btn_register_employee){
            if(views.txt_employee_id.getText().equals("") 
                    || views.txt_employee_fullname.getText().equals("") 
                    || views.txt_employee_username.getText().equals("") 
                    || views.txt_employee_address.getText().equals("") 
                    || views.txt_employee_telephone.getText().equals("") 
                    || views.txt_employee_email.getText().equals("") 
                    || views.cmb_rol.getSelectedItem().toString().equals("") 
                    || String.valueOf(views.txt_employee_password.getPassword()).equals("")){
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
            }else{
//                employee.setId(Integer.parseInt(views.txt_employee_id.getText().trim()));
                employee.setFull_name(views.txt_employee_fullname.getText().trim());
                employee.setUsername(views.txt_employee_username.getText().trim());
                employee.setAddress(views.txt_employee_address.getText().trim());
                employee.setTelephone(views.txt_employee_telephone.getText().trim());
                employee.setEmail(views.txt_employee_email.getText().trim());
                employee.setPassword(String.valueOf(views.txt_employee_password.getPassword()));
                employee.setRol(views.cmb_rol.getSelectedItem().toString());
                
                if(employee_dao.registerEmployeeQuery(employee)){
                    cleanTable();
                    JOptionPane.showMessageDialog(null, "Empleado registrado con exito");
                }else{
                    JOptionPane.showMessageDialog(null, "Ocurrio un error al registrar el empleado");
                }
            }
                            
        }
    }

    
    //Listar todos los empleados
    public void listAllEmployees(){
        if(rol.equals("Administrador")){
            List<Employee> list = employee_dao.listEmployeesQuery(views.txt_search_employee.getText());
            model = (DefaultTableModel) views.jt_employees_table.getModel();
            Object[] row = new Object[7];
            for(int i=0; i<list.size();i++){
                row[0] = list.get(i).getId();
                row[1] = list.get(i).getFull_name();
                row[2] = list.get(i).getUsername();
                row[3] = list.get(i).getAddress();
                row[4] = list.get(i).getTelephone();
                row[5] = list.get(i).getEmail(); 
                row[6] = list.get(i).getRol();
                model.addRow(row);
            }
        }
    }
    
    
    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() == views.jt_employees_table){
            int row = views.jt_employees_table.rowAtPoint(e.getPoint());
            views.txt_employee_id.setText(views.jt_employees_table.getValueAt(row, 0).toString());
            views.txt_employee_fullname.setText(views.jt_employees_table.getValueAt(row, 1).toString());
            views.txt_employee_username.setText(views.jt_employees_table.getValueAt(row, 2).toString());
            views.txt_employee_address.setText(views.jt_employees_table.getValueAt(row, 3).toString());
            views.txt_employee_telephone.setText(views.jt_employees_table.getValueAt(row, 4).toString());
            views.txt_employee_email.setText(views.jt_employees_table.getValueAt(row, 5).toString());
            views.cmb_rol.setSelectedItem(views.jt_employees_table.getValueAt(row, 6).toString());
            
            views.txt_employee_id.setEditable(false);
            views.txt_employee_password.setEnabled(false);
            views.btn_register_employee.setEnabled(false);
            
        }
        
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
        if(e.getSource() == views.txt_search_employee){
            cleanTable();
            listAllEmployees();
        }
        
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
    //metodo para limpliar la tabla
    public void cleanTable(){
        for(int i=0; i<model.getRowCount(); i++){
            model.removeRow(i);
            i = i-1;
            
        }
    }
    
}
