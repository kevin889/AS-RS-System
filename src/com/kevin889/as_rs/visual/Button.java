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
    private TourScreen frame = null;

    /**
     * Genereert een button met ingebouwde ActionListener
     * @param title
     * @param type
     * @param bounds
     */
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

    /**
     * Activeer de juiste actie bij een klik op een button
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() instanceof JButton){
            switch(type){
                case SELECT_XML:
                    if(frame == null) return;

                    int returnVal = frame.getFileChooser().showOpenDialog(frame);
                    if(returnVal == JFileChooser.APPROVE_OPTION){
                        System.out.println(frame.getFileChooser().getSelectedFile().getAbsolutePath());
                        frame.setFile(frame.getFileChooser().getSelectedFile());
                    }
                    break;
                case ORDER_SPECS:
                    if(frame == null) return;
                    System.out.println("Order specs");
                    frame.getOrderSpecsDialog().setVisible(true);
                    break;
                case PRINT_ORDER:
                    frame.printOrder();
                    break;
                case START_GA:
                    if(frame == null) return;
                    frame.getTourPanel().start();
                    break;
                case STOP_GA:
                    if(frame == null) return;
                    frame.getTourPanel().stop();
                    break;
                case CLOSE:
                    frame.getOrderSpecsDialog().setVisible(false);
                    break;

            }
        }
    }

    public enum ButtonType{ SELECT_XML, ORDER_SPECS, PRINT_ORDER, START_GA, STOP_GA, CLOSE; }

}
