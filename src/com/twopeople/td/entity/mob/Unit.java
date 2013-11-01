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

public class Unit extends Mob implements Cloneable {
    static HashMap<Integer, Unit> units = new HashMap<Integer, Unit>();
    private String name;

    public Unit(World world, float x, float z) {
        super(world, x, z, 0, 0);
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
            Element e;
            try {
                e = (Element) mobs.item(i);
            } catch (Exception ex) {
                continue;
            }
            NodeList p = e.getChildNodes();
            Unit u = new Unit(null, 0, 0);
            for (int j = 0; j < p.getLength(); j++) {
                Element property;
                try {
                    property = (Element) p.item(j);
                } catch (Exception ex) {
                    continue;
                }
                String name = property.getTagName();
                System.out.println(name);
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
                } else if(name.equals("Width")) {
                    u.setWidth(Integer.parseInt(property.getTextContent()));
                } else if(name.equals("Height")) {
                    u.setHeight(Integer.parseInt(property.getTextContent()));
                }
            }

            units.put(u.getId(), u);
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Unit fromType(World world, float x, float z, int unitId) {
        try {
            Unit unit = (Unit) units.get(unitId).clone();
            unit.setX(x);
            unit.setZ(z);
            unit.setWorld(world);

            return unit;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Unit clone(Unit u) {
        Unit n = new Unit(u.world, u.getX(), u.getZ());
        n.setWidth(u.getWidth());
        n.setHeight(u.getHeight());
        return n;
    }
}