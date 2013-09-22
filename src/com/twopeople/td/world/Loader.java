package com.twopeople.td.world;

import com.twopeople.td.entity.Camp;
import com.twopeople.td.entity.WaveSpawner;
import com.twopeople.td.entity.interior.Wall;
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
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        org.w3c.dom.Document doc = builder.parse(path);

        NodeList map = doc.getElementsByTagName("map");
        Element element = (Element) map.item(0);
        NodeList eList = element.getElementsByTagName("entities");
        for (int i = 0; i < eList.getLength(); i++) {
            Element e = (Element) eList.item(i);
            getEntity(e, world);
        }

        eList = element.getElementsByTagName("areas");
        for(int i=0; i < eList.getLength(); i++)
        {
            Element e = (Element) eList.item(i);
            getArea(e,world);
        }
    }

    private static void getEntity(Element e, World world) {
        int x = Integer.parseInt(e.getAttribute("x"));
        int y = Integer.parseInt(e.getAttribute("y"));
        int type = Integer.parseInt(e.getAttribute("type"));
        int id = Integer.parseInt(e.getAttribute("id"));

        switch (type) {
            case PYLON:
                world.addEntity(new Camp(world, x, y, id));
                break;
            case WAVE_SPAWNER:
                WaveSpawner ws = new WaveSpawner(world, x, y, id);
                NodeList waveElements = e.getElementsByTagName("wave");
                for(int i=0;i<waveElements.getLength();i++)
                {
                    Element wave = (Element)waveElements.item(i);
                    int time = Integer.parseInt(wave.getAttribute("time"));
                    int pylon = Integer.parseInt(wave.getAttribute("pylon"));
                    Wave w = new Wave(time, pylon);
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
                world.addEntity(new Wall(world, x,y,id));
                break;
        }
    }

    private static void getArea(Element e, World world)
    {
        int x = Integer.parseInt(e.getAttribute("x"));
        int y = Integer.parseInt(e.getAttribute("y"));
        int id = Integer.parseInt(e.getAttribute("id"));
        int width = Integer.parseInt(e.getAttribute("width"));
        int height = Integer.parseInt(e.getAttribute("height"));
        int wave = Integer.parseInt(e.getAttribute("after"));
        world.addArea(new Area(world,x,y,width,height, wave,id));
    }
}
