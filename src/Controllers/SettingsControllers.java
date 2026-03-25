
package Controllers;

import Views.SystemView;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SettingsControllers implements MouseListener{
    private SystemView views;
    
    public SettingsControllers (SystemView views){
        this.views = views;
        
        this.views.jLabelProducts.addMouseListener(this);
        this.views.jLabelPurchases.addMouseListener(this);
        this.views.jLabelCustomers.addMouseListener(this);
        this.views.jLabelEmployees.addMouseListener(this);
        this.views.jLabelSuppliers.addMouseListener(this);
        this.views.jLabelCategories.addMouseListener(this);
        this.views.jLabelReports.addMouseListener(this);
        this.views.jLabelSettings.addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
        
        
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
        
        if(e.getSource() == views.jLabelProducts){
            views.jPanelProducts.setBackground(new Color(152, 202, 63));
        }
        else if(e.getSource() == views.jLabelPurchases){
            views.jPanelPutchanses.setBackground(new Color(152,202,63));
        }
        else if(e.getSource() == views.jLabelCustomers){
            views.jPanelCustomers.setBackground(new Color(152,202,63));
        }
        else if(e.getSource() == views.jLabelEmployees){
            views.jPanelEmployes.setBackground(new Color(152,202,63));
        }
        else if(e.getSource() == views.jLabelSuppliers){
            views.jPanelSupliers.setBackground(new Color(152,202,63));
        }
        else if(e.getSource() == views.jLabelCategories){
            views.jPanelCategories.setBackground(new Color(152,202,63));
        }
        else if(e.getSource() == views.jLabelReports){
            views.jPanelReports.setBackground(new Color(152,205,63));
        }
        else if(e.getSource() == views.jLabelSettings){
            views.jPanelSetting.setBackground(new Color(152,202,63));
        }
                
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
        if(e.getSource() == views.jLabelProducts){
            views.jPanelProducts.setBackground(new Color(18,45,61));
        }
        else if(e.getSource() == views.jLabelPurchases){
            views.jPanelPutchanses.setBackground(new Color(18,45,61));
        }
        else if(e.getSource() == views.jLabelCustomers){
            views.jPanelCustomers.setBackground(new Color(18,45,61));
        }
        else if(e.getSource() == views.jLabelEmployees){
            views.jPanelEmployes.setBackground(new Color(18,45,61));
        }
        else if(e.getSource() == views.jLabelSuppliers){
            views.jPanelSupliers.setBackground(new Color(18,45,61));
        }
        else if(e.getSource() == views.jLabelCategories){
            views.jPanelCategories.setBackground(new Color(18,45,61));
        }
        else if(e.getSource() == views.jLabelReports){
            views.jPanelReports.setBackground(new Color(18,45,61));
        }
        else if(e.getSource() == views.jLabelSettings){
            views.jPanelSetting.setBackground(new Color(18,45,61));
        }
        
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
    
}
