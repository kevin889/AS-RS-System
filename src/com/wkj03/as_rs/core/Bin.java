package com.wkj03.as_rs.core;

import java.util.ArrayList;

/**
 * Created by kevin889 on 26-05-15.
 */
public class Bin {

        private int inhoud;
        private int maxInhoud;
        private ArrayList<Product> pakketten;
        private int binNummer;
        private Boolean vol;

        public Bin(int maxInhoud, int binNummer) {
            this.maxInhoud = maxInhoud;
            this.binNummer = binNummer;
            pakketten = new ArrayList<Product>();
            vol = false;
        }


        public void addPakket(Product p) {
            pakketten.add(p);
            inhoud = inhoud + p.getSize();
        }

        public Boolean isVol() {
            return vol;
        }

        public void setVol(Boolean vol) {
            this.vol = vol;

        }

        public int getRestant() {
            return (maxInhoud - inhoud);

        }

        public int getInhoud() {
            return this.inhoud;
        }

        public ArrayList getPakketten() {
            return pakketten;
        }

        public Product getProduct(int index) { return pakketten.get(index); }

        public int getBinNummer() {
            return binNummer;
        }

        public int getMaxInhoud() {
            return maxInhoud;
        }



}
