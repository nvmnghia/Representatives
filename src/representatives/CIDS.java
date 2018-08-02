/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package representatives;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Vo Dinh Hieu
 */
public class CIDS {
    private String ids;
    private String excl;
    public CIDS(String i, String e){
        ids=i;
        excl=e;
    }
    public CIDS(Node n){
        NodeList nl=n.getChildNodes();
        for(int i=0;i<nl.getLength();i++){
            Node node=nl.item(i);
            String name=node.getNodeName();
            if(name.equals("ids"))
                ids=node.getTextContent();
            if(name.equals("excl"))
                excl=node.getTextContent();
                        
        }
    }
    public String getIDS(){
        return ids;
    }
    public String getEXCL(){
        return excl;
    }
}
