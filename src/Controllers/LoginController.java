//https://www.youtube.com/watch?v=mrI0CVKCo3Q&list=PLffixYYr8M_uPiKk1VZOjhTHE8UGcQvKR&index=31
package Controllers;

import Models.Employee;
import Models.EmployeeDAO;
import Views.LoginView;
import Views.SystemView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class LoginController implements ActionListener{
    
    //Encapsular variables
    private Employee employee;
    private EmployeeDAO employee_dao;
    private LoginView login_view;

    public LoginController(Employee employees, EmployeeDAO employee_dao, LoginView login_view) {
        this.employee = employees;
        this.employee_dao = employee_dao;
        this.login_view = login_view;
        // escuchamos el boton de la vita login
        this.login_view.btn_enter.addActionListener(this);
    }

    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        //Obteniendo los datos datos de la vista
        String user = login_view.txt_username.getText().trim();
        String pass = String.valueOf(login_view.txt_password.getPassword());
        if(e.getSource() == login_view.btn_enter){
            if(!user.equals("") || !pass.equals("")){
//                System.out.println(user);
//                System.out.println(pass);
                employee = employee_dao.loginQuery(user, pass);
//                System.out.println(employee.getUsername());
                if(employee.getUsername() != null){
                    System.out.println(employee.getUsername());
                    if(employee.getRol().equals("Administrador")){
                        SystemView admin = new SystemView();
                        admin.setVisible(true);
                    }else{
                        SystemView aux = new SystemView();
                        aux.setVisible(true);
                    }
                    this.login_view.dispose();
                }else{
                    JOptionPane.showMessageDialog(null, "Usuariio o Password Incorrecto");
                }
            }else{
                JOptionPane.showMessageDialog(null, "Los campos o uno de los campos esta vacio");
            }
        }
    }
    
}
