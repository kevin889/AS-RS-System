package com.kevin889.as_rs.visual;

import com.kevin889.as_rs.technical.*;
import com.kevin889.as_rs.algoritme.GA_TSP;
import com.kevin889.as_rs.core.Customer;
import com.kevin889.as_rs.core.Order;
import com.kevin889.as_rs.core.Product;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by kevin889 on 16-04-15.
 */
public class TourScreen extends JFrame{

    private GA_TSP gaTSP;

    private JButton jbXmlSelect;
    private JButton jbOrderSpecs;
    private JButton jbPrintOrder;
    private JButton jbPickOrder;
    private JButton jbStopPick;
    private JTable jtProducts;
    private JLabel jlStatus;

    private ProductsTableModel dtm;
    private TourPanel tpView;

    private JFileChooser fc;
    private File selectedXML;
    private XMLFile xmlFile;
    private SQLHandler sqlh;

    private Order order;
    private OrderSpecsDialog orderSpecsDialog;

    public TourScreen(SQLHandler sqlh) {
        setTitle("Magazijn robot Controle Systeem");
        setSize(800, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        this.sqlh = sqlh;
        fc = new JFileChooser();
        fc.addChoosableFileFilter(new XMLFilter());
        fc.setAcceptAllFileFilterUsed(false);

        jbXmlSelect = new Button("Selecteer XML", Button.ButtonType.SELECT_XML, new Rectangle(10, 10, 150, 50), this);

        add(jbXmlSelect);

        jbOrderSpecs = new Button("Order gegevens", Button.ButtonType.ORDER_SPECS, new Rectangle(10, 65, 150, 50), this);
        jbOrderSpecs.setEnabled(false);
        add(jbOrderSpecs);

        jbPrintOrder = new Button("Print pakbon", Button.ButtonType.PRINT_ORDER, new Rectangle(10, 125, 150, 50));
        jbPrintOrder.setEnabled(false);
        add(jbPrintOrder);

        dtm = new ProductsTableModel();

        jtProducts = new JTable(dtm);

        jtProducts.getColumnModel().getColumn(0).setPreferredWidth(10);
        JScrollPane jsSP = new JScrollPane(jtProducts);
        jsSP.setBounds(10, 180, 150, 200);
        add(jsSP);

        tpView = new TourPanel();
        tpView.setBounds(170, 10, 620, 500);
        add(tpView);

        jbPickOrder = new Button("Start", Button.ButtonType.START_GA, new Rectangle(170, 520, 250, 50));
        jbPickOrder.setEnabled(false);
        add(jbPickOrder);

        jbStopPick = new Button("Stop", Button.ButtonType.STOP_GA, new Rectangle(540, 520, 250, 50));
        jbStopPick.setEnabled(false);
        add(jbStopPick);

        setVisible(true);

    }

    public Order getOrder(){
        return order;
    }

    public ProductsTableModel getProductsTableModel(){
        return dtm;
    }

    public void setFile(File f){
        selectedXML = f;
        try {
            xmlFile = new XMLFile(f);
            System.out.println(xmlFile.getOrderNr());

            order = new Order(xmlFile.getOrderNr(), xmlFile.getDate(), xmlFile.getCustomer());

            jbOrderSpecs.setEnabled(true);
            jbPrintOrder.setEnabled(true);
            jbPickOrder.setEnabled(true);
            jbStopPick.setEnabled(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File getSelectedXML(){
        return selectedXML;
    }

    public JFileChooser getFileChooser(){
        return fc;
    }

    public boolean hasFileSelected(){
        return selectedXML != null;
    }


}
