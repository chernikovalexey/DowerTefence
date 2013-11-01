package com.twopeople.td.world;

import com.twopeople.td.entity.Camp;
import com.twopeople.td.entity.WaveSpawner;
import com.twopeople.td.entity.mob.Unit;

import java.util.ArrayList;

/**
 * Created by podko_000
 * At 3:49 on 22.09.13
 */

public class Wave {
    private int time, pylon;
    private boolean started;
    private ArrayList<WaveInfo> waveInfo = new ArrayList<WaveInfo>();
    private boolean finished;
    private int current, id;

    public Wave(int time, int pylon, int id) {
        this.time = time;
        this.pylon = pylon;
        this.id = id;
    }

    public void addUnit(WaveInfo wave) {
        waveInfo.add(wave);
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public boolean isFinished() {
        return finished;
    }

    public void next(World world, WaveSpawner parent) {
        if (waveInfo.size() == 0) {
            finished = true;
            return;
        }
        Unit u = Unit.fromType(world, parent.getX(), parent.getZ(), waveInfo.get(0).unitId);
        if (world.getEntities().nothingColliding(u)) {
            waveInfo.get(0).count--;
            if (waveInfo.get(0).count == 0) { waveInfo.remove(0); }
            world.addEntity(u);

            Camp camp = world.getCamp(this.pylon);
            if (camp != null) {
                u.setGoal(world.getCamp(this.pylon));
            }
        }

        if (waveInfo.size() == 0) { parent.waveFinished(this.id); }
    }

    public int getTime() {
        return time;
    }
}
