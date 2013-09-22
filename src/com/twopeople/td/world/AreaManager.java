package com.twopeople.td.world;

import com.twopeople.td.entity.WaveSpawner;

import java.util.List;

/**
 * Created by podko_000
 * At 12:46 on 22.09.13
 */

public class AreaManager {
    private List<Area> areas;
    private List<WaveSpawner> spawners;

    public AreaManager(List<Area> areas, List<WaveSpawner> spawners)
    {
        this.areas = areas;
        this.spawners = spawners;
        for(Area a:areas) {
            if (a.getWave() < 0) {
                openArea(a);
            }
        }

        for(WaveSpawner s:spawners)
        {
            if(s.getArea()<0)
                s.activate(this);
        }
    }

    public void startWave(int id)
    {

    }

    public void finishWave(int id)
    {
        for(Area a:areas)
        {
            if(a.getWave()==id)
            {
                openArea(a);
            }
        }
    }

    public void update()
    {
        for(WaveSpawner ws:spawners)
            ws.update();
    }

    private void openArea(Area a) {
        a.setOpened(true);
        for(WaveSpawner ws:spawners)
        {
            if(ws.getArea()==a.getId())
            ws.activate(this);
        }
    }
}
