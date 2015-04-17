package com.kevin889.as_rs.visual;

import javafx.geometry.Bounds;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by kevin889 on 17-04-15.
 */
public class Button extends JButton implements ActionListener {

    private ButtonType type;
    private TourScreen frame;

    public Button(String title, ButtonType type, Rectangle bounds){
        setText(title);
        this.type = type;
        setBounds(bounds);
        addActionListener(this);
    }

    public Button(String title, ButtonType type, Rectangle bounds, TourScreen frame){
        this(title, type, bounds);
        this.frame = frame;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() instanceof JButton){
            switch(type){
                case SELECT_XML:
                    if(frame == null) return;

                    int returnVal = frame.getFileChooser().showOpenDialog(frame);
                    if(returnVal == JFileChooser.APPROVE_OPTION){
                        frame.setFile(frame.getFileChooser().getSelectedFile());
                    }
                    break;
                case ORDER_SPECS:
                    if(frame == null) return;
                    System.out.println("Order specs");
                    OrderSpecsDialog orderSpecsDialog = new OrderSpecsDialog(frame, frame.getOrder(), frame.getProductsTableModel());
                    orderSpecsDialog.setVisible(true);
                    break;
                case PRINT_ORDER:
                    break;
                case START_GA:
                    System.out.println("START GA");
                    break;
                case STOP_GA:
                    System.out.println("STOP GA");
                    break;
                case CLOSE:
                    break;

            }
        }
    }

    public enum ButtonType{ SELECT_XML, ORDER_SPECS, PRINT_ORDER, START_GA, STOP_GA, CLOSE; }

}
