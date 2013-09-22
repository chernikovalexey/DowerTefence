package com.twopeople.td.world;

import com.twopeople.td.entity.Camp;
import com.twopeople.td.entity.WaveSpawner;
import com.twopeople.td.entity.interior.Wall;
import org.newdawn.slick.geom.Vector2f;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * Created by podko_000
 * At 1:22 on 22.09.13
 */

public class Loader {
    static final int WAVE_SPAWNER = 2, WALL = 1, PYLON = 5, AREA = 4;


    public static void fromFile(String path, World world) throws ParserConfigurationException, IOException, SAXException {

        Element element = load(path);
        NodeList eList = element.getElementsByTagName("entities").item(0).getChildNodes();
        for (int i = 0; i < eList.getLength(); i++) {
            org.w3c.dom.Node e = (org.w3c.dom.Node)eList.item(i);
            if(e.getNodeName().equals("entity"))
            {
                getEntity((Element)e,world);
            }
        }

        loadAreas(element, world);
    }

    private static void loadAreas(Element root, World world)
    {
        NodeList eList = root.getElementsByTagName("areas").item(0).getChildNodes();
        for(int i=0; i < eList.getLength(); i++)
        {
            org.w3c.dom.Node e = (org.w3c.dom.Node) eList.item(i);
            if(e.getNodeName().equals("entity"))
            {
                getArea((Element)e,world);
            }
        }
    }

    private static Element load(String fileName) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        org.w3c.dom.Document doc = builder.parse(fileName);

        NodeList map = doc.getElementsByTagName("map");
        return (Element) map.item(0);
    }

    private static void getEntity(Element e, World world) {
        int x = Integer.parseInt(e.getAttribute("x")) * 48;
        int y = Integer.parseInt(e.getAttribute("y")) * 48;
        int type = Integer.parseInt(e.getAttribute("type"));
        int id = Integer.parseInt(e.getAttribute("id"));

        switch (type) {
            case PYLON:
                world.addEntity(new Camp(world, x, y, id));
                break;
            case WAVE_SPAWNER:
                int area = -1;
                if(!e.getAttribute("area").isEmpty())
                     area = Integer.parseInt(e.getAttribute("area"));
                WaveSpawner ws = new WaveSpawner(world, x, y, area, id);
                NodeList waveElements = e.getElementsByTagName("wave");
                for(int i=0;i<waveElements.getLength();i++)
                {
                    Element wave = (Element)waveElements.item(i);
                    int time = Integer.parseInt(wave.getAttribute("time"));
                    int pylon = Integer.parseInt(wave.getAttribute("pylon"));
                    Wave w = new Wave(time, pylon, id);
                    NodeList units = wave.getElementsByTagName("unit");
                    for(int j=0;j<units.getLength();j++)
                    {
                        Element unit = (Element) units.item(j);
                        int uid = Integer.parseInt(unit.getAttribute("unitId"));
                        int count = Integer.parseInt(unit.getAttribute("count"));
                        WaveInfo wi = new WaveInfo(uid, count);
                        w.addUnit(wi);
                    }
                    ws.addWave(w);
                }
                world.addSpawner(ws);
                break;
            case WALL:
                world.addEntity(new Wall(world, x,y));
                break;
        }
    }

    

    private static void getArea(Element e, World world)
    {
        int x = Integer.parseInt(e.getAttribute("x")) * 48;
        int y = Integer.parseInt(e.getAttribute("y")) * 48;

        int id = Integer.parseInt(e.getAttribute("id"));
        int width = Integer.parseInt(e.getAttribute("width")) * 48;
        int height = Integer.parseInt(e.getAttribute("height")) * 48;

        world.setWidth(Math.max(x + width, (int)world.getWidth()));
        world.setHeight(Math.max(y + height, (int)world.getHeight()));

        int wave = -1;
        System.out.println(e.getAttribute("after"));
        if(!e.getAttribute("after").isEmpty())
            wave = Integer.parseInt(e.getAttribute("after"));

        world.addArea(new Area(world,x,y,width,height, wave,id));
    }

    public static Vector2f preLoad(String fileName, World world) throws ParserConfigurationException, IOException, SAXException {
        Element element = load(fileName);
        loadAreas(element, world);
        return new Vector2f(world.getWidth(), world.getHeight());
    }
}
