package com.twopeople.td.world;

import java.util.ArrayList;

/**
 * Created by podko_000
 * At 3:49 on 22.09.13
 */

public class Wave {
    private int time, pylon;
    private ArrayList<WaveInfo> waveInfo = new ArrayList<WaveInfo>();

    public Wave(int time, int pylon) {
        this.time = time;
        this.pylon = pylon;
    }

    public void addUnit(WaveInfo wave) {
        waveInfo.add(wave);
    }
}
