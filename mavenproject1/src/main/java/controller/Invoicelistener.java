package controller;


    import java.awt.event.ActionListener;
    import java.awt.event.ActionEvent;
    import java.io.File;
    import java.io.FileNotFoundException;
    import java.io.FileOutputStream;
    import java.io.IOException;
    import java.nio.file.Files;
    import java.nio.file.Path;
    import java.nio.file.Paths;
    import java.text.ParseException;
    import java.util.ArrayList;
    import java.util.Date;
import java.util.List;
    import javax.swing.JFileChooser;
    import javax.swing.JOptionPane;
    import model.InvoiceHeader;
    import model.InvoiceHeaderTable;
    import model.InvoiceLineTable;
    import model.LineInvoice;
    import view.InvHeaderDia;
    import view.InvLineDia;
    import view.InvoiceFrame;



    public class Invoicelistener implements ActionListener {

    private InvoiceFrame josframe;
    private InvHeaderDia headDiajo;
    private InvLineDia lineDialjo;

    

    public Invoicelistener (InvoiceFrame josframe){
        this.josframe = josframe;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            
            
            case "load" -> Load();
            case "Create New Invoice" -> Createnewinvoice();
                  case "Delete Invoice" -> Deleteinvoice();
                  case "Save Line" -> saveline();
                  case "Delete Line" -> Deleteline();
                  case "Save" -> 
                      Save();
            case "newInvoiceOK" -> newInvoiceDialogOK();

            case "newInvoiceCancel" -> newInvoiceDialogCancel();

            case "newLineCancel" -> newLineDialogCancel();

            case "newLineOK" -> newLineDialogOK();


                   
        }

    }

    private void Createnewinvoice() {
        headDiajo = new InvHeaderDia(josframe);
        headDiajo.setVisible(true);
        }

    private void Deleteinvoice() {
        int selectedInvoiceIndex = josframe.getTH().getSelectedRow();
        if (selectedInvoiceIndex != -1) {
            josframe.getInvoicesArray().remove(selectedInvoiceIndex);
            josframe.getHeaderTableModel().fireTableDataChanged();

            josframe.getTH().setModel(new InvoiceLineTable(null));
            josframe.setLinesArray(null);
            josframe.getCutinv().setText("");
            josframe.getNoinv().setText("");
            josframe.getInvitems().setText("");
            josframe.getDatinv().setText("");
        }
    }
    private void saveline() {
        lineDialjo = new InvLineDia(josframe);
        lineDialjo.setVisible(true);
      }

    private void Deleteline() {
        
            int selectedLineIndex = josframe.getTL().getSelectedRow();
        int selectedInvoiceIndex = josframe.getTH().getSelectedRow();
        if (selectedLineIndex != -1) {
            josframe.getLinesArray().remove(selectedLineIndex);
            InvoiceLineTable lineTableModel = (InvoiceLineTable) josframe.getTL().getModel();
            lineTableModel.fireTableDataChanged();
            josframe.getInvitems().setText(""+josframe.getLinesArray().get(selectedInvoiceIndex).getlineTatol());
            josframe.getHeaderTableModel().fireTableDataChanged();
            josframe.getTH().setRowSelectionInterval(selectedLineIndex, selectedInvoiceIndex);
        
        
        }
    }
    private void Load() {

             JFileChooser fc = new JFileChooser();
              try {

            int result = fc.showOpenDialog( josframe);
            if (result == JFileChooser.APPROVE_OPTION) {
            File jokoFile = fc.getSelectedFile();
            Path jooPath = Paths.get(jokoFile.getAbsolutePath()); 
                List<String> headerLines = Files.readAllLines(jooPath);
                ArrayList<InvoiceHeader> invoiceHeaders = new ArrayList<>();
                for (String headerLine : headerLines) {
                    String[] arr = headerLine.split(",");
                    String str1 = arr[0];
                    String str2 = arr[1]; 
                    String str3 = arr[2];
                    int code = Integer.parseInt(str1);
                    Date invoiceDate =  InvoiceFrame.joo22.parse(str2);
                    InvoiceHeader header = new InvoiceHeader(code, str3, invoiceDate);
                    invoiceHeaders.add(header);
                 System.out.println(" invoce num "+"\t"+ str1 +" invoice date "+"\t" +str2+ " customer name "+ str3);
                }
               
                josframe.setInvoicesArray(invoiceHeaders);
                
               
            result = fc.showOpenDialog( josframe);
            if(result == JFileChooser.APPROVE_OPTION) {
              File joFile = fc.getSelectedFile();
              Path jPath = Paths.get(joFile.getAbsolutePath()); 
              List<String>lineLines = Files.readAllLines(jPath);
              ArrayList  <LineInvoice> invoiceLines = new ArrayList<>();
              
               for (String lineLine : lineLines) {
                        String[] arr = lineLine.split(",");
                        String s1 = arr[0];
                        String s2 = arr[1];
                        String s3 = arr[2];
                        String s4 = arr[3];
                        int invCode = Integer.parseInt(s1);
                        double price = Double.parseDouble(s3);
                        int count = Integer.parseInt(s4);
                        InvoiceHeader inv = josframe.getInvObj(invCode);
                        LineInvoice linej = new LineInvoice(s2, price, count, inv);
                        inv.getLines().add(linej);
                        System.out.println(" invcode "+ s1  +"\t" + " count "+ s4+"\t"+" price "+ s2+"\t"+" total "+ s3);
                }
               
            }
            InvoiceHeaderTable htj = new InvoiceHeaderTable(invoiceHeaders);
                josframe.setHeaderTableModel(htj);
                josframe.getTH().setModel(htj);
                System.out.println("files read");
               
                
             
            }
        } catch (IOException | ParseException ex) {
            JOptionPane.showMessageDialog(josframe, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void Save() {
        JFileChooser FS =   new JFileChooser();
        int result =FS.showSaveDialog(josframe);
        
        
        if 
                (result   != JFileChooser.APPROVE_OPTION)
                {
                    String path = FS.getSelectedFile().getPath();
                    FileOutputStream jojo = null;
                    try {
                    jojo=new  FileOutputStream(path); 
                    
                    
                    }
                    catch (FileNotFoundException e){
                    }finally{
                        try {
                    jojo.close();
                    
                        }
                    catch(IOException e){
                    }
                    }
        }}

    private void newLineDialogOK() {
        
    lineDialjo.setVisible(false);
        
        String name = lineDialjo.getItemName().getText();
        String str1 = lineDialjo.getItemCount().getText();
        String str2 = lineDialjo.getItemPrice().getText();
        int count = 1;
        double price = 1;
        try {
            count = Integer.parseInt(str1);
        } catch(NumberFormatException ex) {
            JOptionPane.showMessageDialog(josframe, "Cannot convert number", "Invalid number format", JOptionPane.ERROR_MESSAGE);
        }
        
        try {
            price = Double.parseDouble(str2);
        } catch(NumberFormatException ex) {
            JOptionPane.showMessageDialog(josframe, "Cannot convert price", "Invalid number format", JOptionPane.ERROR_MESSAGE);
        }
        int selectedInvHeader = josframe.getTH().getSelectedRow();
        if (selectedInvHeader != -1) {
            InvoiceHeader invHeader = josframe.getInvoicesArray().get(selectedInvHeader);
            LineInvoice line = new LineInvoice(name, price, count, invHeader);
            invHeader.getLines().add(line);
            josframe.getLinesArray().add(line);
            InvoiceLineTable lineTableModel = (InvoiceLineTable) josframe.getTL().getModel();
            lineTableModel.fireTableDataChanged();
            josframe.getHeaderTableModel().fireTableDataChanged();
        }
        josframe.getTH().setRowSelectionInterval(selectedInvHeader, selectedInvHeader);
        lineDialjo.dispose();
        lineDialjo = null;
    
    
    
    }

    private void newLineDialogCancel() {
        lineDialjo.setVisible(false);
        lineDialjo.dispose();
        lineDialjo = null;
        }

    private void newInvoiceDialogCancel() {
        headDiajo.setVisible(false);
        headDiajo.dispose();
        headDiajo = null;
    }

    private void newInvoiceDialogOK() {
    
        headDiajo.setVisible(false);

        String custName = headDiajo.getCustName().getText();
        String str = headDiajo.getInvDate().getText();
        Date dj = new Date();
        try {
            dj = InvoiceFrame.joo22.parse(str);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(josframe, "Cannot parse date, resetting to today.", " date format", JOptionPane.ERROR_MESSAGE);
        }

        int invNum = 0;
        for (InvoiceHeader num : josframe.getInvoicesArray()) {
            if (num.getNum() > invNum) {
                invNum = num.getNum();
            }
        }
        invNum++;
        InvoiceHeader newInv = new InvoiceHeader(invNum, custName, dj);
        josframe.getInvoicesArray().add(newInv);
        josframe.getHeaderTableModel().fireTableDataChanged();
        headDiajo.dispose();
        headDiajo = null;
        
    }
    }
