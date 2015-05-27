package com.kevin889.as_rs.visual;

import com.kevin889.as_rs.core.Customer;
import com.kevin889.as_rs.core.Order;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by kevin889 on 16-04-15.
 */
public class OrderSpecsDialog extends JDialog implements ActionListener{

    private Order order;
    private DefaultTableModel dtm;
    private JTable jtProducts;
    private JButton jbClose;

    /**
     * Maakt het dialoog "Order gegevens" aan, en plaatst de interface objecten.
     *
     * @param owner
     * @param order
     * @param dtm
     */
    public OrderSpecsDialog(TourScreen owner, Order order, DefaultTableModel dtm) {
        super(owner, true);
        this.order = order;
        this.dtm = dtm;
        setTitle("Order gegevens");
        setSize(360, 230);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        JLabel jlDate = new JLabel("Order datum: " + order.getDate());
        jlDate.setBounds(10, 10, 200, 20);
        add(jlDate);

        JLabel jlOrderNr = new JLabel("Ordernummer: " + order.getOrderNumber());
        jlOrderNr.setBounds(10, 30, 150, 20);
        add(jlOrderNr);

        JLabel jlName = new JLabel("Voornaam: " + Customer.getName());
        jlName.setBounds(10, 60, 150, 20);
        add(jlName);

        JLabel jlSurname = new JLabel("Achternaam: " + Customer.getSurname());
        jlSurname.setBounds(10, 80, 150, 20);
        add(jlSurname);

        JLabel jlAddress = new JLabel("Adres: " + Customer.getAddress());
        jlAddress.setBounds(10, 100, 150, 20);
        add(jlAddress);

        JLabel jlZipcode = new JLabel("Postcode: " + Customer.getZipcode());
        jlZipcode.setBounds(10, 120, 150, 20);
        add(jlZipcode);

        JLabel jlCity = new JLabel("Stad: " + Customer.getCity());
        jlCity.setBounds(10, 140, 150, 20);
        add(jlCity);

        jtProducts = new JTable(dtm){
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        jtProducts.getColumnModel().getColumn(0).setPreferredWidth(10);
        JScrollPane jsSP = new JScrollPane(jtProducts);
        jsSP.setBounds(200, 10, 150, 190);
        add(jsSP);


        jbClose = new Button("Sluiten", Button.ButtonType.CLOSE, new Rectangle(10, 170, 100, 30), owner);

        add(jbClose);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        setVisible(false);
    }
}
