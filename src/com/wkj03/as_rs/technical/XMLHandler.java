package com.wkj03.as_rs.technical;

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

    /**
     * Laad de content die in een xml bestand staat
     * @param file
     * @throws IOException
     */
    public XMLHandler(File file) throws IOException{
        this.file = file;
    }

    /**
     * Geeft het huidig geladen bestand
     * @return
     */
    public File getFile(){
        return file;
    }

    /**
     * Geeft het huidig geladen document
     * @return
     */
    public Document getDocument(){
        if(doc == null){
            initDoc();
        }

        return doc;

    }

    /**
     * XML Structuur wordt omgezet naar te lezen objecten.
     */
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

    /**
     * Geeft een lijst met de hele inhoud van het xml bestand
     * @param option
     * @return
     */
    public NodeList getNodeList(NodeOption option){
        return getDocument().getElementsByTagName(option.toString());
    }

    /**
     * Keuzes uit de verschillende xml tags
     */
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
