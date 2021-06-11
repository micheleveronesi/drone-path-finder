package org.example.business;

import java.util.HashMap;
import java.util.Map;

public enum Material {
    ARTIFICIAL_MATERIAL,
    COATING,
    LIQUID,
    MINERAL_ROCK,
    ORGANIC_COMPOUND,
    SOIL,
    VEGETATION,
    UNDEFINED;

    private static final Map<Integer, Material> PREDICTION_MAP = new HashMap<>();
    static {
        PREDICTION_MAP.put(0, ARTIFICIAL_MATERIAL);
        PREDICTION_MAP.put(1, COATING);
        PREDICTION_MAP.put(2, LIQUID);
        PREDICTION_MAP.put(3, MINERAL_ROCK);
        PREDICTION_MAP.put(4, ORGANIC_COMPOUND);
        PREDICTION_MAP.put(5, SOIL);
        PREDICTION_MAP.put(6, VEGETATION);
    }

    public static Material intToMaterial(int prediction) {
        Material m = PREDICTION_MAP.get(prediction);
        if(m == null)
            return UNDEFINED;
        return m;
    }
}
