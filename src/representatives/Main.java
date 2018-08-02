/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package representatives;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author hieuvd
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try{
            ArrayList<Affiliation> affiliations=new Affiliations("Affiliations.xml").getAffiliations();
            
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = null;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/vci_scholar","root", "");
            

            // create the java statement
            Statement st = conn.createStatement();
            for(int i=0;i<affiliations.size()-1;i++)
                affiliations.get(i).process(conn);
            
            conn.close();
          
            //new Affiliations("Affiliations.xml");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
}
