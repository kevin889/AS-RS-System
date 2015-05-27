package com.wkj03.as_rs.technical;

import javax.swing.table.DefaultTableModel;

/**
 * Created by kevin889 on 17-04-15.
 */
public class ProductsTableModel extends DefaultTableModel {

    public ProductsTableModel() {
        addColumn("Id");
        addColumn("Naam");
    }

    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
