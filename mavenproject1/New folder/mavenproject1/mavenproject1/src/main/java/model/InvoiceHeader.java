
package model;

import java.util.ArrayList;
import java.util.Date;


public class InvoiceHeader {
    private int invNum;
    private String customer;
    private Date invDate;
    private ArrayList<LineInvoice> lines;   
 
public InvoiceHeader(){
    
}
public InvoiceHeader(int num, String customer ,Date invDate ){
    this.invNum = num;
    this.customer = customer;
    this.invDate = invDate;
}

    public int getNum() {
        return invNum;
    }

    public void setNum(int num) {
        this.invNum = num;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Date getInvDate() {
        return invDate;
    }

    public void setInvDate(Date invDate) {
        this.invDate = invDate;
    }

    public ArrayList<LineInvoice> getLines() {
        return lines;
    }

    public void setLines(ArrayList<LineInvoice> lines) {
        this.lines = lines;
    }

    public double getInvoiceTatol(){
        double total = 0.0;
        
        for ( int i = 0; i < lines.size(); i++ )
        {
            total += lines.get(i).getlineTatol();
        }
        return total;
    }
}