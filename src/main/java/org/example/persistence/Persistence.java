package org.example.persistence;

import org.example.business.Point;

import java.util.List;

public interface Persistence {
    Instance getLastInstance();
    boolean saveInstance(Instance instance);
}
