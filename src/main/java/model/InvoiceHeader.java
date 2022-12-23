/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author karee
 */
public class InvoiceHeader {

    private int id;
    private String datecreated;
    private String customerName;
    private int total;
    private ArrayList<InvoiceLine> items;

    public InvoiceHeader(int id, String datecreated, String customerName, int total, ArrayList<InvoiceLine> items) {
        this.id = id;
        this.datecreated = datecreated;
        this.customerName = customerName;
        this.total = total;
        this.items = items;
    }

    public InvoiceHeader() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDatecreated() {
        return datecreated;
    }

    public void setDatecreated(String datecreated) {
        this.datecreated = datecreated;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getTotal() {
        int totalPrice = 0;
        if (items == null) {
            return 0;
        }
        for (int i = 0; i < items.size(); i++) {
            totalPrice = totalPrice + items.get(i).getItemTotal();
        }
        return totalPrice;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ArrayList<InvoiceLine> getItems() {
        if (items == null) {
            items = new ArrayList<>();
        }
        return items;
    }

    public void setItems(ArrayList<InvoiceLine> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Invoice{" + "id=" + id + ", datecreated=" + datecreated + ", customerName=" + customerName + ", total=" + total + ", items=" + items + '}';
    }
}
