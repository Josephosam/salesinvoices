
package controller;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import model.InvoiceHeader;
import view.InvoiceFrame;



public class Invoicelistener implements ActionListener {
   
    private SimpleDateFormat joo = new SimpleDateFormat("dd-MM-yyyy");
    private InvoiceFrame josframe;

    public Invoicelistener (InvoiceFrame frame){
        this.josframe = frame;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case "Create New Invoice":
               Createnewinvoice();
                      break;
                  case "Delete Invoice":
                Deleteinvoice();
                      break;
                  case "Save Line":
                      saveline();
                      break;
                  case "Delete Line":
                Deleteline();
                      break;
                  case "Save":
                Save();
                      break;
                   case "load":
                Load();
                       break;
                   case "Exit":
                   
                       break;
        }
        
    }

    private void Createnewinvoice() {
        
        }

    private void Deleteinvoice() {
        }

    private void saveline() {
      }

    private void Deleteline() {
        }

    private void Load() {
      
             JFileChooser fc = new JFileChooser();
              try {
                
            int result = fc.showOpenDialog( josframe);
            if (result == JFileChooser.APPROVE_OPTION) {
            
            String headerFile = fc.getSelectedFile().getPath();
           
             
            Path headerPath = Paths.get(headerFile.getAbsolutePath( ));
                
              
                
                java.util.List<String> headerLines = Files.readAllLines(headerPath);
                ArrayList<InvoiceHeader> invoiceHeaders = new ArrayList<>();
                for (String headerLine : headerLines) {
                    String[] arr = headerLine.split(",");
                    String str1 = arr[0];
                    String str2 = arr[1];
                    String str3 = arr[2];
                    int code = Integer.parseInt(str1);
                    Date invoiceDate =  joo.parse(str2);
                    InvoiceHeader header = new InvoiceHeader(code, str3, invoiceDate);
                    invoiceHeaders.add(header);
                }
            }
            
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(josframe, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(josframe, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void Save() {
        }

    private void Exit() {
        System.exit (0) ;
    }
    
}           