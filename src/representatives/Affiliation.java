/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package representatives;

import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author Vo Dinh Hieu
 */
public class Affiliation {
    private String representative;
    private ArrayList<IDS> idss = new ArrayList();
    private ArrayList<CIDS> cidss = new ArrayList();

    public String getRepresentative() {
        return representative;
    }

    public ArrayList getIDSs() {
        return idss;
    }

    public ArrayList getCIDSs() {
        return cidss;
    }

    public Affiliation(Node affiliationNode) {
        NodeList nl = affiliationNode.getChildNodes();
        for (int i = 0; i < nl.getLength(); i++) {
            Node node = nl.item(i);
            String name = node.getNodeName();
            switch (name) {
                case "ids":
                    idss.add(new IDS(node));
                    break;
                case "cids":
                    cidss.add(new CIDS(node));
                    break;
                case "representative":
                    representative = node.getTextContent();
                    break;
            }
        }
    }

    public void print() {
        System.out.println("Representative: " + representative);
        System.out.println("IDS: ");
        for (IDS ids : idss) {
            System.out.println(ids.getIDS());
        }
        System.out.println("CIDS: ");
        for (CIDS cids : cidss) {
            System.out.println("IDS: " + cids.getIDS());
            System.out.println("EXCL: " + cids.getEXCL());
        }
    }

    public void process(Connection conn) throws Exception {
        System.out.println("Representative: " + representative);

        String query = "select * from representatives where name = '" + representative + "'";
        String id = null;
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(query);
        if (rs.next()) {
            id = rs.getString("ID");
            System.out.println("Representative ID: " + id);
        } else {
            query = "insert into representatives (name) values ('" + representative + "')";
            st.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            rs = st.getGeneratedKeys();
            if (rs.next()) {
                id=rs.getString(1);
                System.out.println("New representative with ID: " + rs.getInt(1));
            }

        }

        //System.out.println("IDS: ");
        for (IDS ids : idss) {
            System.out.println(ids.getIDS());
            //String query="update organizes set representative="+representative+" where name like %"+ids+"%";
            query = "select id, name from organizes where name like '%" + ids.getIDS() + "%';";
            rs = st.executeQuery(query);
            String update;
            Statement st2 = conn.createStatement();
            // iterate through the java resultset
            while (rs.next()) {
                System.out.println(rs.getString("name"));
                query = "select * from organize_representative where (organize_id=" + rs.getInt(1) + ") AND (representative_id=" + id + ")";
                if (st2.executeQuery(query).next() == false) {
                    update = "insert into organize_representative (organize_id, representative_id) values (" + rs.getInt(1) + "," + id + ")";
                    st2.executeUpdate(update);
                }

                //System.out.println("test");
            }
            //st.close();
        }

        Statement stm = conn.createStatement();
        System.out.println("CIDS: ");
        for (CIDS cids : cidss) {

            System.out.println("IDS: " + cids.getIDS());
            System.out.println("EXCL: " + cids.getEXCL());
            query = "select id, name from organizes where (name like '%" + cids.getIDS() + "%') AND NOT (name like '%" + cids.getEXCL() + "%')";
            rs = st.executeQuery(query);

            // iterate through the java resultset
            while (rs.next()) {
                System.out.println(rs.getString("name"));
                //System.out.println("test");

                query = "select * from organize_representative where (organize_id=" + rs.getInt(1) + ") AND (representative_id=" + id + ")";
                if (stm.executeQuery(query).next() == false) {
                    String update = "insert into organize_representative (organize_id, representative_id) values (" + rs.getInt(1) + "," + id + ")";
                    stm.executeUpdate(update);
                }

            }
        }
    }

}
