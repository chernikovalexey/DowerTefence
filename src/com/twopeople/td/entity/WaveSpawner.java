package com.twopeople.td.entity;

import com.twopeople.td.world.AreaManager;
import com.twopeople.td.world.Wave;
import com.twopeople.td.world.World;

import java.util.ArrayList;

/**
 * Created by podko_000
 * At 3:37 on 22.09.13
 */

public class WaveSpawner extends Entity {
    private ArrayList<Wave> waves = new ArrayList<Wave>();
    private AreaManager areaManager;
    private int area;
    private boolean activated;
    private long activatedTime;

    public WaveSpawner(World world, float x, float y, int area, int id) {
        super(world, x, 0, y, 48, 48, id);
        this.area = area;
    }

    public void addWave(Wave w) {
        waves.add(w);
    }

    public void activate(AreaManager areaManager) {
        this.areaManager = areaManager;
        activated = true;
        activatedTime = System.currentTimeMillis();
    }

    public void waveFinished(int wave)
    {
        areaManager.finishWave(wave);
    }

    public void update()
    {
        if(!activated) return;
        long current = System.currentTimeMillis() - activatedTime;
        for(Wave w:waves)
        {

            if(w.isStarted() && !w.isFinished())
            {
                w.next(world, this);
            }
            else if(!w.isStarted() && current/1000>w.getTime())
            {
                w.setStarted(true);
            }
        }
    }

    public int getArea() {
        return area;
    }
}
