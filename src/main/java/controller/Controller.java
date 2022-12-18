/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import model.InvoiceHeader;
import model.InvoiceLine;
import view.InvoiceDialogue;
import view.InvoiceGUI;

/**
 *
 * @author karee
 */
public class Controller implements ActionListener, ListSelectionListener {

    private InvoiceGUI inv;
    private InvoiceDialogue invDialogue;

    public Controller(InvoiceGUI inv) {

        this.inv = inv;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("LoadMenu")) {
            System.out.println("load menu clicked");
            var invoicelist = chooseFile();
            displayData(invoicelist);
        } else if (e.getActionCommand().equals("SaveMenu")) {
            System.out.println("save menu clicked");
        } else if (e.getActionCommand().equals("Create New Invoice")) {
            System.out.println("create invoice btn clicked");
            ShowNewInvoiceDialogue();
        } else if (e.getActionCommand().equals("Delete Invoice")) {
            System.out.println("delete invoice btn clicked");
            deleteInvoice();
        } else if (e.getActionCommand().equals("Save")) {
            System.out.println("save btn clicked");
        } else if (e.getActionCommand().equals("Cancel")) {
            inv.dispose();
        } else if (e.getActionCommand().equals("CancelDialogue")) {
            System.out.println("cancel invoice btn clicked");
            invDialogue.dispose();
        } else if (e.getActionCommand().equals("Add New Invoice")) {
            System.out.println("add new item buttin field");
            createNewInvoice();
            invDialogue.dispose();
        }

    }

    private void createNewInvoice() {
        InvoiceHeader newInvoice = new InvoiceHeader();

        String customerName = invDialogue.getNewCustomerNameTF().getText();
        String date = invDialogue.getNewDateTF().getText();

        newInvoice.setCustomerName(customerName);
        newInvoice.setDatecreated(date);

        var invoiceList = inv.getInvoiceslist();
        ArrayList<Integer> idList = new ArrayList<>();
        for (int i = 0; i < invoiceList.size(); i++) {
            idList.add(invoiceList.get(i).getId());
        }
        int maxID = Collections.max(idList);

        newInvoice.setCustomerName(customerName);
        newInvoice.setDatecreated(date);
        newInvoice.setId(maxID);

        invoiceList.add(newInvoice);
        DefaultTableModel model = (DefaultTableModel) inv.getInvoiceTable().getModel();
        Object data[] = new Object[]{newInvoice.getId() + 1, newInvoice.getDatecreated(), newInvoice.getCustomerName(), newInvoice.getItems()};
        model.addRow(data);
    }

    private ArrayList<InvoiceHeader> chooseFile() {
        JFileChooser f = new JFileChooser();
        ArrayList<InvoiceHeader> invoiceList = new ArrayList<>();
        ArrayList<InvoiceLine> invoiceItems = new ArrayList<>();
        InvoiceHeader newInvoice;
        InvoiceLine newInvoiceItem;

        if (f.showOpenDialog(this.inv) == JFileChooser.OPEN_DIALOG) {
            File file = f.getSelectedFile();
            Path path = Paths.get(file.getAbsolutePath());
            try {
                var lines = Files.readAllLines(path);
                for (int i = 0; i < lines.size(); i++) {
                    String[] line = lines.get(i).split(",");
                    newInvoice = new InvoiceHeader();
                    newInvoice.setId(Integer.parseInt(line[0]));
                    newInvoice.setDatecreated(line[1]);
                    newInvoice.setCustomerName(line[2]);

                    invoiceList.add(newInvoice);
                }
                System.err.println("hello");

                if (f.showOpenDialog(this.inv) == JFileChooser.OPEN_DIALOG) {
                    File file2 = f.getSelectedFile();
                    path = Paths.get(file2.getAbsolutePath());
                    var lines2 = Files.readAllLines(path);

                    for (int i = 0; i < lines2.size(); i++) {
                        String[] line = lines2.get(i).split(",");
                        newInvoiceItem = new InvoiceLine();
                        newInvoiceItem.setId(Integer.parseInt(line[0]));
                        newInvoiceItem.setItemName(line[1]);
                        newInvoiceItem.setItemPrice(Integer.parseInt(line[2]));
                        newInvoiceItem.setCount(Integer.parseInt(line[3]));
                        InvoiceHeader relatedInvoice = null;
                        for (InvoiceHeader invoice : invoiceList) {
                            if (invoice.getId() == Integer.parseInt(line[0])) {
                                relatedInvoice = new InvoiceHeader();
                                relatedInvoice = invoice;
                                break;
                            }
                        }
                        newInvoiceItem.setInvoice(relatedInvoice);
                        invoiceItems.add(newInvoiceItem);
                        relatedInvoice.getItems().add(newInvoiceItem);
                    }

                }

            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return invoiceList;
    }

    private void deleteInvoice() {
        int index = inv.getInvoiceTable().getSelectedRow();
//        var Id = inv.getInvoiceTable().getModel().getValueAt(index, 0);

        ((DefaultTableModel) inv.getInvoiceTable().getModel()).removeRow(index);

        DefaultTableModel model = (DefaultTableModel) inv.getInvoiceItemsTable().getModel();
        model.setRowCount(0);
        inv.getInvoiceNumberTF().setText(null);
        inv.getInvoiceDateTF().setText(null);
        inv.getCustomerNameTF().setText(null);
        inv.getInvoiceTotalTF().setText(null);
//        for (int i = 0; i < invoiceLinesTable.getRowCount(); i++) {
//            if (invoiceLinesTable.getValueAt(i, 0).equals(Id)) {
//                ((DefaultTableModel) inv.getInvoiceItemsTable().getModel()).removeRow(i);
//            }
//        }
//        var result = inv.getInvoiceItemsTable();

        JOptionPane.showMessageDialog(null, "Invoice deleted successfully");
    }

    private void displayData(ArrayList<InvoiceHeader> invoiceList) {

        String col[] = {"No.", "Date", "Customer", "Total"};
        DefaultTableModel tableModel = new DefaultTableModel(col, 0);

        for (int i = 0; i < invoiceList.size(); i++) {
            int id = invoiceList.get(i).getId();
            String date = invoiceList.get(i).getDatecreated();
            String name = invoiceList.get(i).getCustomerName();
            int total = invoiceList.get(i).getTotal();

            Object data[] = new Object[]{id, date, name, total};
            tableModel.addRow(data);
        }
        inv.getInvoiceTable().setModel(tableModel);
        inv.setInvoiceslist(invoiceList);

    }

    @Override
    public void valueChanged(ListSelectionEvent e) {

        //Object selectedRow = inv.getInvoiceTable().getValueAt(inv.getInvoiceTable().getSelectedRow(), inv.getInvoiceTable().getSelectedColumn());
        var index = inv.getInvoiceTable().getSelectedRow();
        if (index != -1) {

            var selectedInvoice = inv.getInvoiceslist().get(index);

            inv.getInvoiceNumberTF().setText(Integer.toString(selectedInvoice.getId()));
            inv.getInvoiceDateTF().setText(selectedInvoice.getDatecreated());
            inv.getCustomerNameTF().setText(selectedInvoice.getCustomerName());
            inv.getInvoiceTotalTF().setText(Integer.toString(selectedInvoice.getTotal()));

            System.out.println("selected");

            String col[] = {"No.", "Item Name", "Item Price", "Count", "Item Total"};
            DefaultTableModel invoiceItemModel = new DefaultTableModel(col, 0);
            ArrayList<InvoiceLine> invoiceItems = new ArrayList<>();
            invoiceItems = selectedInvoice.getItems();

            for (int i = 0; i < invoiceItems.size(); i++) {
                int id = invoiceItems.get(i).getId();
                String itemName = invoiceItems.get(i).getItemName();
                int itemPrice = invoiceItems.get(i).getItemPrice();
                int count = invoiceItems.get(i).getCount();
                int total = invoiceItems.get(i).getItemTotal();

                Object data[] = new Object[]{id, itemName, itemPrice, count, total};
                invoiceItemModel.addRow(data);
            }
//        inv.getInvoiceItemsTable().getColumnModel().getColumn(0).setPreferredWidth(100);
            inv.getInvoiceItemsTable().setModel(invoiceItemModel);
//           JTableHeader h = inv.getInvoiceItemsTable().getTableHeader();
//        TableColumn col1= inv.getInvoiceItemsTable().getColumn(0);
//        col1.setPreferredWidth(100);
//        TableColumn col2= inv.getInvoiceItemsTable().getColumn(1);
//        col2.setPreferredWidth(100);
//        TableColumn col3= inv.getInvoiceItemsTable().getColumn(2);
//        col3.setPreferredWidth(100);
//        TableColumn col4= inv.getInvoiceItemsTable().getColumn(3);
//        col4.setPreferredWidth(100);
//        TableColumn col5= inv.getInvoiceItemsTable().getColumn(4);
//        col5.setPreferredWidth(100);
            System.out.println("selected");
        }

    }

    private void ShowNewInvoiceDialogue() {
        invDialogue = new InvoiceDialogue(inv);
        invDialogue.setVisible(true);
    }

}
