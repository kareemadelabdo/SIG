/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author karee
 */
public class InvoiceLine {

    private int id;
    private int itemPrice;
    private int count;
    private int itemTotal;
    private String itemName;
    private InvoiceHeader invoice;

    public InvoiceLine() {
    }

    public InvoiceLine(int id, String itemName, int itemPrice, int count, int itemTotal, InvoiceHeader invoice) {
        this.id = id;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.count = count;
        this.itemTotal = itemTotal;
        this.invoice = invoice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(int itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getItemTotal() {
        return count * itemPrice;
    }

    public void setItemTotal(int itemTotal) {
        this.itemTotal = itemTotal;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public InvoiceHeader getInvoice() {
        return invoice;
    }

    public void setInvoice(InvoiceHeader invoice) {
        this.invoice = invoice;
    }

    @Override
    public String toString() {
        return "InvoiceItem{" + "id=" + id + ", itemPrice=" + itemPrice + ", count=" + count + ", itemTotal=" + itemTotal + ", itemName=" + itemName + ", invoice=" + invoice + '}';
    }

}
