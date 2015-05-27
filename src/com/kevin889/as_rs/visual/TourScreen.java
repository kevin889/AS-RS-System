package com.kevin889.as_rs.visual;

import com.kevin889.as_rs.Magazijn;
import com.kevin889.as_rs.core.Bin;
import com.kevin889.as_rs.technical.*;
import com.kevin889.as_rs.algoritme.GA_TSP;
import com.kevin889.as_rs.core.Order;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by kevin889 on 16-04-15.
 */
public class TourScreen extends JFrame{

    private GA_TSP gaTSP;

    //initialiseer objecten
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
    private XMLData xmlData;
    private SQLHandler sqlh;
    private SQLData sqlData;

    private Order order;
    private OrderSpecsDialog orderSpecsDialog;
    private ArrayList<Bin> bins;

    /**
     *
     * Maakt het frame aan en plaatst de interface objecten in het frame.
     *
     * @param sqlh
     */
    public TourScreen(SQLHandler sqlh) {

        //Maak frame aan en plaats alle objecten

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

        jbPrintOrder = new Button("Print pakbon", Button.ButtonType.PRINT_ORDER, new Rectangle(10, 125, 150, 50), this);
        jbPrintOrder.setEnabled(false);
        add(jbPrintOrder);

        dtm = new ProductsTableModel();

        jtProducts = new JTable(dtm);

        jtProducts.getColumnModel().getColumn(0).setPreferredWidth(10);
        JScrollPane jsSP = new JScrollPane(jtProducts);
        jsSP.setBounds(10, 180, 150, 200);
        add(jsSP);

        tpView = new TourPanel();
        tpView.setBounds(170, 10, TourPanel.PWIDTH, TourPanel.PHEIGHT);
        add(tpView);

        jbPickOrder = new Button("Start", Button.ButtonType.START_GA, new Rectangle(170, 520, 250, 50), this);
        jbPickOrder.setEnabled(false);
        add(jbPickOrder);

        jbStopPick = new Button("Stop", Button.ButtonType.STOP_GA, new Rectangle(540, 520, 250, 50), this);
        jbStopPick.setEnabled(false);
        //add(jbStopPick);

        setVisible(true);

        if(Magazijn.DEV_MODE) {
            setFile(new File("/Users/kevin889/Desktop/testOrder.xml"));
        }

    }

    /**
     * Geeft de huidige order
     * @return
     */
    public Order getOrder(){
        return order;
    }

    /**
     * Geeft de "Order gegevens" class
     * @return
     */
    public OrderSpecsDialog getOrderSpecsDialog(){
        return orderSpecsDialog;
    }

    /**
     * Technische informatie over de tabel met producten
     * @return
     */
    public ProductsTableModel getProductsTableModel(){
        return dtm;
    }

    /**
     * Laad het xml bestand in de applicatie en verwerkt de inhoud
     * @param f
     */
    public void setFile(File f) {
        selectedXML = f;
        try {
            xmlData = new XMLData(f);

            order = new Order(xmlData.getOrderNr(), xmlData.getDate(), xmlData.getCustomer());
            orderSpecsDialog = new OrderSpecsDialog(this, getOrder(), getProductsTableModel());

            sqlData = new SQLData(this);
            sqlData.createProducts();

            tpView.init(getOrder());

            //Zet de buttons in het frame op enabled.
            jbOrderSpecs.setEnabled(true);
            jbPrintOrder.setEnabled(true);
            jbPickOrder.setEnabled(true);
            jbStopPick.setEnabled(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Krijg de xml data
     * @return
     */
    public XMLData getXmlData(){
        return xmlData;
    }

    /**
     * Krijg het bestand kies venster
     * @return
     */
    public JFileChooser getFileChooser(){
        return fc;
    }

    /**
     * Krijg de sql handler
     * @return
     */
    public SQLHandler getSQLHandler(){
        return sqlh;
    }

    /**
     * Technische informatie over de tabel
     * @return
     */
    public ProductsTableModel getDtm(){
        return dtm;
    }

    /**
     * Krijg het tourpanel
     * @return
     */
    public TourPanel getTourPanel(){
        return tpView;
    }

    /**
     * Genereer en print de pakbon
     */
    public void printOrder(){
        System.out.println(order.getBins());
        for(int i = 0; i < order.getBins().size(); i++){
            System.out.println("####################### PAKLIJST " + (i + 1) + "/"+ order.getBins().size() +" ##################");
            System.out.println("Aan: "+order.getCustomer().getName()+" " + order.getCustomer().getSurname() + ", " + order.getCustomer().getAddress() + ", " + order.getCustomer().getZipcode() + " " + order.getCustomer().getCity());
            System.out.println("\nInhoud doos:");
            for(int b = 0; b < order.getBins().get(i).getPakketten().size(); b++){
                System.out.println((b + 1) + ". " + order.getBins().get(i).getProduct(b).getName());
            }
            System.out.println("\n");
        }
    }

}
