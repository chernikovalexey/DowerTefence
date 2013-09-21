package com.twopeople.td.entity.tower;

import com.twopeople.td.world.World;

/**
 * Created by Alexey
 * At 4:10 PM on 9/21/13
 */

public class Factory {
    public static Tower create(String tower, World w, float x, float z) {
        try {
            return (Tower) Class.forName(tower).getConstructor(World.class, float.class, float.class).newInstance(new Object[]{w, x, z});
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}