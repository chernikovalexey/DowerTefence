package com.twopeople.td.entity.mob;

import com.twopeople.td.world.World;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.util.HashMap;

/**
 * Created by Alexey
 * At 3:19 AM on 9/22/13
 */

public class Unit extends Mob {
    static HashMap<Integer, Unit> units = new HashMap<Integer, Unit>();
    private String name;

    public Unit(World world, float x, float z, int id) {
        super(world, x, z, 0, 0, id);
    }

    static {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        org.w3c.dom.Document doc = null;
        try {
            doc = builder.parse("res/units/data.utwopeople");
        } catch (Exception e) {
            e.printStackTrace();
        }

        NodeList map = doc.getElementsByTagName("ArrayOfMob");
        Element element = (Element) map.item(0);
        NodeList mobs = element.getElementsByTagName("Mob");
        for (int i = 0; i < mobs.getLength(); i++) {
            Element e = (Element) mobs.item(i);
            NodeList p = e.getChildNodes();
            Unit u = new Unit(null, 0, 0, 0);
            for (int j = 0; j < p.getLength(); j++) {
                Element property = (Element) p.item(i);
                String name = property.getTagName();
                if (name.equals("Speed")) {
                    u.setSpeed(Integer.parseInt(property.getTextContent()));
                } else if (name.equals("Health")) {
                    u.setSpeed(Integer.parseInt(property.getTextContent()));
                } else if (name.equals("IsShooting")) {
                    u.setShooting(property.getTextContent().equals("true"));
                } else if (name.equals("Demage")) {
                    u.setDamage(Integer.parseInt(property.getTextContent()));
                } else if (name.equals("Reward")) {
                    u.setReward(Integer.parseInt(property.getTextContent()));
                } else if (name.equals("IsFlying")) {
                    u.setFlying(property.getTextContent().equals("true"));
                } else if (name.equals("Id")) {
                    u.setId(Integer.parseInt(property.getTextContent()));
                } else if (name.equals("Name")) {
                    u.setName(property.getTextContent());
                }
            }

            units.put(u.getId(), u);
        }
    }

    public void setName(String name) {
        this.name = name;
    }
}