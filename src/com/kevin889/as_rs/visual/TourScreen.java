package com.kevin889.as_rs.visual;

import com.kevin889.as_rs.technical.SQLHandler;
import com.kevin889.as_rs.technical.XMLFilter;
import com.kevin889.as_rs.technical.XMLHandler;
import com.kevin889.as_rs.algoritme.GA_TSP;
import com.kevin889.as_rs.core.Customer;
import com.kevin889.as_rs.core.Order;
import com.kevin889.as_rs.core.Product;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by kevin889 on 16-04-15.
 */
public class TourScreen extends JFrame implements ActionListener{

    private GA_TSP gaTSP;

    private JButton jbXmlSelect;
    private JButton jbOrderSpecs;
    private JButton jbPrintOrder;
    private JButton jbPickOrder;
    private JButton jbStopPick;
    private JTable jtProducts;
    private DefaultTableModel dtm;
    private JLabel jlStatus;
    private TourPanel tpView;
    private final JFileChooser fc = new JFileChooser();
    private File selectedXML;
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

        jbXmlSelect = new JButton("Selecteer XML");
        jbXmlSelect.setBounds(10, 10, 150, 50);
        jbXmlSelect.addActionListener(this);
        add(jbXmlSelect);

        jbOrderSpecs = new JButton("Order gegevens");
        jbOrderSpecs.setBounds(10, 65, 150, 50);
        jbOrderSpecs.addActionListener(this);
        jbOrderSpecs.setEnabled(false);
        add(jbOrderSpecs);

        jbPrintOrder = new JButton("Print pakbon");
        jbPrintOrder.setBounds(10, 125, 150, 50);
        jbPrintOrder.addActionListener(this);
        jbPrintOrder.setEnabled(false);
        add(jbPrintOrder);

        dtm = new DefaultTableModel();
        dtm.addColumn("Id");
        dtm.addColumn("Naam");

        jtProducts = new JTable(dtm){
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        jtProducts.getColumnModel().getColumn(0).setPreferredWidth(10);
        JScrollPane jsSP = new JScrollPane(jtProducts);
        jsSP.setBounds(10, 180, 150, 200);
        add(jsSP);

        tpView = new TourPanel();
        tpView.setBounds(170, 10, 620, 500);
        add(tpView);

        jbPickOrder = new JButton("Start");
        jbPickOrder.setBounds(170, 520, 250, 50);
        jbPickOrder.addActionListener(this);
        jbPickOrder.setEnabled(false);
        add(jbPickOrder);

        jbStopPick = new JButton("Stop");
        jbStopPick.setBounds(540, 520, 250, 50);
        jbStopPick.addActionListener(this);
        jbStopPick.setEnabled(false);
        add(jbStopPick);

        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == jbXmlSelect){
            fc.addChoosableFileFilter(new XMLFilter());
            fc.setAcceptAllFileFilterUsed(false);
            int returnVal = fc.showOpenDialog(this);

            if(returnVal == JFileChooser.APPROVE_OPTION){
                selectedXML = fc.getSelectedFile();
                System.out.println("Opening: " + selectedXML.getName());
                jbOrderSpecs.setEnabled(true);
                jbPrintOrder.setEnabled(true);
                jbPickOrder.setEnabled(true);
                jbStopPick.setEnabled(true);

                try {
                    XMLHandler xFile = new XMLHandler(selectedXML);

                    NodeList orderNr = xFile.getNodeList(XMLHandler.NodeOption.ORDERNR);
                    NodeList date = xFile.getNodeList(XMLHandler.NodeOption.DATE);

                    Node orderNrNode = orderNr.item(0);
                    Node dateNote = date.item(0);

                    if(orderNrNode.getNodeType() == Node.ELEMENT_NODE && dateNote.getNodeType() == Node.ELEMENT_NODE){
                        Element orderNrElement = (Element) orderNrNode;
                        Element dateElement = (Element) dateNote;

                        order = new Order(new Integer(orderNrElement.getTextContent()), dateElement.getTextContent());
                    }


                    NodeList customer = xFile.getNodeList(XMLHandler.NodeOption.KLANT);
                    for(int i = 0; i < customer.getLength(); i++){
                        Node node = customer.item(i);
                        if(node.getNodeType() == Node.ELEMENT_NODE){
                            Element element = (Element) node;
                            String name = element.getElementsByTagName("voornaam").item(0).getTextContent();
                            String surname = element.getElementsByTagName("achternaam").item(0).getTextContent();
                            String address = element.getElementsByTagName("adres").item(0).getTextContent();
                            String zipcode = element.getElementsByTagName("postcode").item(0).getTextContent();
                            String city = element.getElementsByTagName("plaats").item(0).getTextContent();
                            new Customer(name, surname, address, zipcode, city);
                        }
                    }

                    NodeList list = xFile.getNodeList(XMLHandler.NodeOption.ARTIKELNR);
                    for(int i = 0; i < list.getLength(); i++){
                        Node nNode = list.item(i);
                        if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                            Element eElement = (Element) nNode;
                            //System.out.println(eElement.getTextContent());
                            ResultSet rs = sqlh.getData("*", "products", "id = " + eElement.getTextContent());
                            int count = 0;
                            try {
                                while(rs.next()){
                                    //System.out.println(rs.getString("name") + " , x="+rs.getString("x") + " | y="+rs.getString("y"));
                                    Product product = new Product(rs.getString("name"), new Integer(rs.getString("x")), new Integer(rs.getString("y")), new Integer(rs.getString("size")));
                                    order.addProduct(product);
                                    dtm.addRow(new Object[] {rs.getString("id"), rs.getString("name")});

                                    count++;
                                }
                                if(count == 0){
                                    System.out.println("[Error] Product met ID: "+ eElement.getTextContent() + " is niet gevonden in de database.");
                                    JOptionPane.showMessageDialog(this,
                                            "Product met ID: "+ eElement.getTextContent() + " is niet gevonden in de database, en is dus niet toegevoegd aan de order.",
                                            "Error",
                                            JOptionPane.ERROR_MESSAGE);
                                }
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }

                        }
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }


            }else{
                System.out.println("Cancel");
            }
        }else if(e.getSource() == jbOrderSpecs){
            System.out.println("Order specs");
            orderSpecsDialog = new OrderSpecsDialog(this, order, dtm);
            orderSpecsDialog.setVisible(true);
        }else if(e.getSource() == jbPrintOrder){
            System.out.println("Print order");
        }else if(e.getSource() == jbPickOrder){
            System.out.println("Start picking");
            gaTSP = new GA_TSP(order);
        }else if(e.getSource() == jbStopPick){
            System.out.println("Stop picking");
        }
    }
}
