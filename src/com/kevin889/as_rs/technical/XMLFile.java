package com.kevin889.as_rs.technical;

import com.kevin889.as_rs.core.Customer;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by kevin889 on 17-04-15.
 */
public class XMLFile extends XMLHandler {

    private int orderNr;
    private String name, surname, address, zipcode, city;
    private Customer customer;
    private String date;
    private ArrayList<Integer> productNrs = new ArrayList<Integer>();

    public XMLFile(File file) throws IOException {
        super(file);
        init();
    }

    private void init(){
        initOrderNr();
        initCustomer();
        initDate();
        initProducts();
    }

    private void initOrderNr(){
        NodeList list = super.getNodeList(NodeOption.ORDERNR);
        System.out.println(list);
        Node node = list.item(0);
        if(node.getNodeType() == Node.ELEMENT_NODE){
            Element element = (Element) node;
            this.orderNr = new Integer(element.getTextContent());
        }
    }

    private void initCustomer(){
        NodeList list = super.getNodeList(NodeOption.KLANT);

        Node node = list.item(0);
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            name = element.getElementsByTagName("voornaam").item(0).getTextContent();
            surname = element.getElementsByTagName("achternaam").item(0).getTextContent();
            address = element.getElementsByTagName("adres").item(0).getTextContent();
            zipcode = element.getElementsByTagName("postcode").item(0).getTextContent();
            city = element.getElementsByTagName("plaats").item(0).getTextContent();

            customer = new Customer(name, surname, address, zipcode, city);
        }
    }

    private void initDate(){
        NodeList list = super.getNodeList(NodeOption.DATE);
        Node node = list.item(0);
        if(node.getNodeType() == Node.ELEMENT_NODE){
            Element element = (Element) node;
            this.date = element.getTextContent();
        }
    }

    private void initProducts(){
        NodeList list = super.getNodeList(NodeOption.ARTIKELNR);

        for(int i = 0; i < list.getLength(); i++){
            Node node = list.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE){
                Element element = (Element) node;
                productNrs.add(new Integer(element.getTextContent()));
            }
        }
    }

    public ArrayList<Integer> getProductNrs() {
        return productNrs;
    }

    public int getOrderNr() {
        return orderNr;
    }

    public String getDate() {
        return date;
    }

    public Customer getCustomer(){
        return customer;
    }
}
