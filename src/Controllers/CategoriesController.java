
package Controllers;

import Models.CategoryDAO;
import Models.Category;
import Models.EmployeeDAO;
import Views.SystemView;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;

//https://www.youtube.com/watch?v=mRwjONCNEKc&list=PLffixYYr8M_uPiKk1VZOjhTHE8UGcQvKR&index=45
public class CategoriesController implements ActionListener, KeyListener, MouseListener{
    
    private Category category;
    private CategoryDAO category_dao;
    private SystemView views;
    String rol = EmployeeDAO.rol_user;

    public CategoriesController(Category category, CategoryDAO category_dao, SystemView views) {
        this.category = category;
        this.category_dao = category_dao;
        this.views = views;
        
        this.views.btn_register_category.addActionListener(this);
    }

    
    
    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == this.views.btn_register_category){
            if(views.txt_category_name.getText().equals("")){
                JOptionPane.showMessageDialog(null, "El campo de nombre es obligatorio");
            }else{
                category.setName(views.txt_category_name.getText().trim());
                if(category_dao.registerCategoryQuery(category)){
                    JOptionPane.showMessageDialog(null, "Categoria registrado correctamente.");
                }else{
                    JOptionPane.showMessageDialog(null, "Error al registrar la categoria");
                }
            }
        }
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
    
}
