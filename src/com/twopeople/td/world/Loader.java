package com.twopeople.td.world;

import com.twopeople.td.entity.Camp;
import com.twopeople.td.entity.Entity;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import sun.plugin.dom.core.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * Created by podko_000
 * At 1:22 on 22.09.13
 */

public class Loader {
    static final int WAVE_SPAWNER = 2, WALL = 1, PYLON = 5, AREA = 4;

    public static class WorldData {

    }

    public static WorldData fromFile(String path, World world) throws ParserConfigurationException, IOException, SAXException {
        WorldData wd = new WorldData();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        org.w3c.dom.Document doc = builder.parse(path);

        NodeList map = doc.getElementsByTagName("map");
        Element element = (Element) map.item(0);
        Element entities = (Element) element.getElementsByTagName("entities").item(0);
        NodeList eList = entities.getElementsByTagName("entities");
        for (int i = 0; i < eList.getLength(); i++) {
            Element e = (Element) eList.item(i);
            getEntity(e, world);
        }

        return null;
    }

    private static Entity getEntity(Element e, World world) {
        int x = Integer.parseInt(e.getAttribute("x"));
        int y = Integer.parseInt(e.getAttribute("y"));
        int type = Integer.parseInt(e.getAttribute("type"));
        int id = Integer.parseInt(e.getAttribute("id"));

        switch (type) {
            case PYLON:
                return new Camp(world, x, y, id);
            /*case WAVE_SPAWNER:
                return new WaveSpawner(world, x, y, id);
            case WALL:
                return new Wall(world, x, y, id);
            case AREA:
                return new Area(x,y,id,e);*/
        }

        return null;
    }
}
