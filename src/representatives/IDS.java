/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package representatives;

import org.w3c.dom.Node;

/**
 *
 * @author Vo Dinh Hieu
 */
public class IDS {
    private String ids;
    public IDS(String s){
        ids=s;
    }
    public IDS(Node n){
        ids=n.getTextContent();
    }
    public String getIDS(){
        return ids;
    }
}
