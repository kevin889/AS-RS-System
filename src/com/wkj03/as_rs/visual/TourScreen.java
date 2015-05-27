package com.wkj03.as_rs.visual;

import com.wkj03.as_rs.Magazijn;
import com.wkj03.as_rs.algoritme.GA_TSP;
import com.wkj03.as_rs.core.Order;
import com.wkj03.as_rs.technical.*;
import gnu.io.CommPortIdentifier;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;

/**
 * Created by kevin889 on 16-04-15.
 */
public class TourScreen extends JFrame implements ActionListener{

    private GA_TSP gaTSP;

    //initialiseer objecten
    private JButton jbXmlSelect;
    private JButton jbOrderSpecs;
    private JButton jbPrintOrder;
    private JButton jbPickOrder;
    private JButton jbStopPick;
    private JTable jtProducts;
    private JLabel jlStatus;
    private JComboBox jcbSerialPort;

    private ProductsTableModel dtm;
    private TourPanel tpView;

    private JFileChooser fc;
    private File selectedXML;
    private XMLData xmlData;
    private SQLHandler sqlh;
    private SQLData sqlData;
    private String serialPort;

    private Order order;
    private OrderSpecsDialog orderSpecsDialog;

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

        jbXmlSelect = new com.wkj03.as_rs.visual.Button("Selecteer XML", com.wkj03.as_rs.visual.Button.ButtonType.SELECT_XML, new Rectangle(10, 10, 150, 50), this);

        add(jbXmlSelect);

        jbOrderSpecs = new com.wkj03.as_rs.visual.Button("Order gegevens", com.wkj03.as_rs.visual.Button.ButtonType.ORDER_SPECS, new Rectangle(10, 65, 150, 50), this);
        jbOrderSpecs.setEnabled(false);
        add(jbOrderSpecs);

        jbPrintOrder = new com.wkj03.as_rs.visual.Button("Print pakbon", com.wkj03.as_rs.visual.Button.ButtonType.PRINT_ORDER, new Rectangle(10, 125, 150, 50), this);
        jbPrintOrder.setEnabled(false);
        add(jbPrintOrder);

        dtm = new ProductsTableModel();

        jtProducts = new JTable(dtm);

        jtProducts.getColumnModel().getColumn(0).setPreferredWidth(10);
        JScrollPane jsSP = new JScrollPane(jtProducts);
        jsSP.setBounds(10, 180, 150, 200);
        add(jsSP);

        ArrayList<String> serialList = new ArrayList<String>();
        Enumeration portList = CommPortIdentifier.getPortIdentifiers();
        while(portList.hasMoreElements()){
            CommPortIdentifier portId = (CommPortIdentifier) portList.nextElement();
            serialList.add(portId.getName());
        }

        String[] arr = serialList.toArray(new String[serialList.size()]);

        jcbSerialPort = new JComboBox(arr);
        jcbSerialPort.setBounds(10, 390, 150, 30);
        jcbSerialPort.addActionListener(this);
        serialPort = (String) jcbSerialPort.getSelectedItem();
        add(jcbSerialPort);

        tpView = new TourPanel();
        tpView.setSerialPort(serialPort);
        tpView.setBounds(170, 10, TourPanel.PWIDTH, TourPanel.PHEIGHT);
        add(tpView);

        jbPickOrder = new com.wkj03.as_rs.visual.Button("Start", com.wkj03.as_rs.visual.Button.ButtonType.START_GA, new Rectangle(170, 520, 250, 50), this);
        jbPickOrder.setEnabled(false);
        add(jbPickOrder);

        jbStopPick = new com.wkj03.as_rs.visual.Button("Stop", com.wkj03.as_rs.visual.Button.ButtonType.STOP_GA, new Rectangle(540, 520, 250, 50), this);
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

    public String getSerialPort(){
        return serialPort;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == jcbSerialPort){
            System.out.println(jcbSerialPort.getSelectedItem());
            serialPort = (String) jcbSerialPort.getSelectedItem();
            tpView.setSerialPort(serialPort);
        }
    }
}
