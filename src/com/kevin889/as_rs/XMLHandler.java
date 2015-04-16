package com.kevin889.as_rs;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;

/**
 * Created by kevin889 on 16-04-15.
 */
public class XMLHandler {

    private File file;
    private Document doc = null;

    public XMLHandler(File file) throws IOException{
        this.file = file;
    }

    public File getFile(){
        return file;
    }

    public Document getDocument(){
        if(doc == null){
            initDoc();
        }

        return doc;

    }

    public void initDoc(){
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            doc = db.parse(file);
            doc.getDocumentElement().normalize();
        }catch(Exception e){
            throw new IllegalArgumentException("Onjuiste XML structuur.");
        }
    }

    public NodeList getNodeList(NodeOption option){
        return getDocument().getElementsByTagName(option.toString());
    }

    public enum NodeOption{
        ORDERNR("ordernummer"),
        KLANT("klant"),
        DATE("datum"),
        ARTIKELNR("artikelnr"); //week 6 presentatie


        private String tagName;
        private NodeOption(String tagName){
            this.tagName = tagName;
        }

        public String toString(){
            return tagName;
        }
    }



}
