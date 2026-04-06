//https://www.youtube.com/watch?v=Qk-rz06xUIQ&list=PLffixYYr8M_uPiKk1VZOjhTHE8UGcQvKR&index=32
package Controllers;

import Models.EmployeeDAO;
import Models.Employee;
import static Models.EmployeeDAO.rol_user;
import Views.SystemView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class EmployeesController implements ActionListener{
    
    private Employee employee;
    private EmployeeDAO employee_dao;
    private SystemView views;

    String rol = rol_user;
    
    public EmployeesController(Employee employee, EmployeeDAO employee_dao, SystemView views) {
        this.employee = employee;
        this.employee_dao = employee_dao;
        this.views = views;
        
        //boton registrar empleado
        this.views.btn_register_employee.addActionListener(this);
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
                    JOptionPane.showMessageDialog(null, "Empleado registrado con exito");
                }else{
                    JOptionPane.showMessageDialog(null, "Ocurrio un error al registrar el empleado");
                }
            }
                            
        }
    }
    
    
}
