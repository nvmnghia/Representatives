/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package representatives;

import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Vo Dinh Hieu
 */
public class Affiliations {
    private int numberAffiliation=0;
    private ArrayList<Affiliation> affiliations=new ArrayList();
    public ArrayList<Affiliation> getAffiliations(){
        return affiliations;        
    }
    public Affiliations(String fileName) throws Exception{
        File file = new File(fileName);
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(file);       
        NodeList affs=document.getElementsByTagName("affiliation");
        numberAffiliation=affs.getLength();
        for(int i=0;i<numberAffiliation;i++){
            affiliations.add(new Affiliation(affs.item(i)));
        }
        /*
        for(Affiliation a:affiliations){
            a.print();
        }
*/
        //String usr = document.getElementsByTagName("affiliation").item(0).getElementByTagName("ids").item(0).getTextContent();
        //String pwd = document.getElementsByTagName("password").item(0).getTextContent();
        //System.out.println(usr);
    }
}
