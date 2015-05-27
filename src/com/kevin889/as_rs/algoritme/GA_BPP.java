package com.kevin889.as_rs.algoritme;

import com.kevin889.as_rs.core.Bin;
import com.kevin889.as_rs.core.Order;
import com.kevin889.as_rs.core.Product;

import java.util.ArrayList;

/**
 * Created by kevin889 on 16-04-15.
 */
public class GA_BPP {

    private Order tm;

    private ArrayList<Bin> bins = new ArrayList<Bin>();
    private ArrayList<Product> wachtrij;
    private ArrayList<Integer> position = new ArrayList<Integer>();
    private Boolean status;

    public GA_BPP(Order tm, ArrayList<Product> route) {
        this.tm = tm;
        this.wachtrij = new ArrayList<Product>();
        for(int i = 0; i < route.size(); i++){
            wachtrij.add(route.get(i));
        }

        bins.add(new Bin(10, 0));
        bins.add(new Bin(10, 1));

        status = false;
        while(wachtrij.size() != 0) {
            verwerkPakket();
        }
        tm.setBins(bins);

    }

    /**
     * Hier wordt per pakket gekeken in welke bin hij geplaatst moet worden.
     * Eerst wordt de eerste van de producten array geladen, en wordt er bepaald in welke bin deze moet.
     * Hierna wordt deze verwijderd uit de arraylist.
     */
    public void verwerkPakket() {
        Product pakket = wachtrij.get(0);
        int restantBin1NaPakket = getBin(0).getRestant() - pakket.getSize();
        int restantBin2NaPakket = getBin(1).getRestant() - pakket.getSize();

        //leegt bins als er geen enkel pakketje meer bijkan.
        Integer volleBin = null; //Integer kan null hebben, int niet geeft dit errors?
        if (getBin(0).getRestant() == 0) {
            volleBin = bins.indexOf(getBin(0));
        } else if (getBin(1).getRestant() == 0) {
            volleBin = bins.indexOf(getBin(0));
        } else if (getBin(0).getRestant() == 0 && getBin(1).getRestant() == 0) {
            volleBin = 9999;
        }
        if (volleBin != null) {
            System.out.println(volleBin);
            if (volleBin == 9999) {
                getBin(0).setVol(true);
                getBin(1).setVol(false);
                bins.add(new Bin(10, bins.size() + 1));
                bins.add(new Bin(10, bins.size() + 2));
            } else {
                bins.get(volleBin).setVol(true);
                bins.add(new Bin(10, bins.size() + 1));
            }
        }

        //als pakket in géén van beide bin's kan.
        if (restantBin1NaPakket < 0 && restantBin2NaPakket < 0) {

            Bin volsteBin = getBin(0).getRestant() < getBin(1).getRestant() ? getBin(0) : getBin(1);

            volsteBin.setVol(true);
            bins.add(new Bin(10, bins.size() + 1));

            //als het pakket in minimaal één bin kan.
        } else {
            if(restantBin1NaPakket < restantBin2NaPakket){
                position.add(1);
                //System.out.println("links");
            }else{
               position.add(0);
                //System.out.println("rechts");
            }
            getBin(restantBin1NaPakket < restantBin2NaPakket ? 1 : 0).addPakket(pakket);
            wachtrij.remove(pakket);
        }
        // kijkt welke bin het mindst vol is en voegt hem toe aand die bin
    }

    public Boolean getStatus() {
        return status;
    }

    public Bin getBin(int i) {
        ArrayList<Bin> bruikbaar = new ArrayList<Bin>();
        for (Bin b : bins) {
            if (!b.isVol()) {
                bruikbaar.add(b);
            }
        }
        return bruikbaar.get(i);
        // geeft de 2 bins die niet als vol zijn aangegeven voor het algoritme om te vullen en de
        // tabel om weer te geven
    }

    public String toString(){
        return position.toString();
    }

    /**
     * Geeft de positie
     * @return
     */
    public ArrayList<Integer> getPosition(){
        return position;
    }

}
