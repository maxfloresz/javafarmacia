
package Controllers;

import Models.CustomerDAO;
import Models.Customer;
import Views.SystemView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class CustomersController implements ActionListener{

    //Encapsular variables
    private Customer customer;
    private CustomerDAO customer_dao;
    private SystemView views;

    //Contructor
    public CustomersController(Customer customer, CustomerDAO customer_dao, SystemView views) {
        this.customer = customer;
        this.customer_dao = customer_dao;
        this.views = views;
        
        //Boton Registrar Cliente "Escucha"
        this.views.btn_register_customer.addActionListener(this);
        
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
                    JOptionPane.showMessageDialog(null, "Cliente registrado con Exito!");
                }else{
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error al registrar el cliente");
                }
            }
        }
    }
    
}
