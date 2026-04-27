
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
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

//https://www.youtube.com/watch?v=mRwjONCNEKc&list=PLffixYYr8M_uPiKk1VZOjhTHE8UGcQvKR&index=45
public class CategoriesController implements ActionListener, KeyListener, MouseListener{
    
    private Category category;
    private CategoryDAO category_dao;
    private SystemView views;
    String rol = EmployeeDAO.rol_user;
    DefaultTableModel model =new DefaultTableModel();

    public CategoriesController(Category category, CategoryDAO category_dao, SystemView views) {
        this.category = category;
        this.category_dao = category_dao;
        this.views = views;
        //boton register
        this.views.btn_register_category.addActionListener(this);
        //txt buscar
        this.views.txt_search_category.addKeyListener(this);
        //btn modificar
        this.views.btn_update_category.addActionListener(this);
        //escucha la tabla
        this.views.jt_categories_table.addMouseListener(this);
        //escucha btn eliminar
        this.views.btn_delete_category.addActionListener(this);
        //escucha btn cencel
        this.views.btn_cancel_category.addActionListener(this);
        
        //menu category
        this.views.jLabelCategories.addMouseListener(this);
    }

    
    
    @Override
    public void actionPerformed(ActionEvent e) {

        //registrar
        if(e.getSource() == this.views.btn_register_category){
            if(views.txt_category_name.getText().equals("")){
                JOptionPane.showMessageDialog(null, "El campo de nombre es obligatorio");
            }else{
                category.setName(views.txt_category_name.getText().trim());
                if(category_dao.registerCategoryQuery(category)){
                    cleanTable();
                    cleanFields();
                    listAllCategories();
                    JOptionPane.showMessageDialog(null, "Categoria registrado correctamente.");
                }else{
                    JOptionPane.showMessageDialog(null, "Error al registrar la categoria");
                }
            }
        }
        
        //modificar
        if(e.getSource() == views.btn_update_category){
            if(views.txt_category_id.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Selecciona una fila para continuar");
            }else{
                if(views.txt_category_id.getText().equals("") 
                        || views.txt_category_name.getText().equals("")){
                    JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
                }else{
                    category.setId(Integer.parseInt(views.txt_category_id.getText()));
                    category.setName(views.txt_category_name.getText().trim());
                    if(category_dao.updateCategoryQuery(category)){
                        cleanTable();
                        cleanFields();
                        listAllCategories();
                        views.btn_register_category.setEnabled(true);
                    }
                }
            }
        }
        
        //Eliminar
        if(e.getSource() == views.btn_delete_category){
            int row = views.jt_categories_table.getSelectedRow();
            if(row == -1){
                JOptionPane.showMessageDialog(null, "Selecciona la categoria a eliminar");
            }else{
                int id = Integer.parseInt(views.jt_categories_table.getValueAt(row, 0).toString());
                int question = JOptionPane.showConfirmDialog(null, "En realidad quieres eliminar esta categoria?");
                if(question == 0 && category_dao.deleteCategoryQuery(id) != false){
                    cleanFields();
                    cleanTable();
                    listAllCategories();
                    views.btn_register_category.setEnabled(true);
                    JOptionPane.showMessageDialog(null, "Categoria eliminada con exito");
                }
            }
        }
        
        //btn cancelar
        if(e.getSource() == views.btn_cancel_category){
            cleanFields();
            views.btn_register_category.setEnabled(true);
        }
    }
    
    public void cleanFields(){
        views.txt_category_id.setText("");
        views.txt_category_name.setText("");
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

        if(e.getSource() == views.txt_search_category){
            cleanTable();
            listAllCategories();
        }
    }
    
    public void cleanTable(){
        for (int i = 0; i < model.getRowCount(); i++) {
            model.removeRow(i);
            i = i-1;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if(e.getSource() == views.jt_categories_table){
            int row = views.jt_categories_table.rowAtPoint(e.getPoint());
            views.txt_category_id.setText(views.jt_categories_table.getValueAt(row, 0).toString());
            views.txt_category_name.setText(views.jt_categories_table.getValueAt(row, 1).toString());
            views.btn_register_category.setEnabled(false);
        }
        
        //menu category
        if(e.getSource() == views.jLabelCategories){
            if(rol.equals("Administrador")){
                views.jTabbedPane1.setSelectedIndex(6);
                cleanFields();
                cleanTable();
                listAllCategories();
            }else{
                views.jTabbedPane1.setEnabledAt(6, false);
                views.jLabelCategories.setEnabled(false);
                JOptionPane.showMessageDialog(null, "No tienes privilegios de administrador");
            }
                
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
    
    public void listAllCategories(){
        if(rol.equals("Administrador")){
            List<Category> list = category_dao.listCategoryQuery(views.txt_search_category.getText());
            model = (DefaultTableModel) views.jt_categories_table.getModel();
            Object[] row = new Object[2];
            for (int i = 0; i < list.size(); i++) {
                row[0] = list.get(i).getId();
                row[1] = list.get(i).getName();
                model.addRow(row);
            }
            views.jt_categories_table.setModel(model);
        }
    }
    
}
