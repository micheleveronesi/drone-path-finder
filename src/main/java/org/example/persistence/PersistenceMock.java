package org.example.persistence;

import java.util.ArrayList;

public class PersistenceMock implements Persistence{

    public Instance getLastInstance() {
        return new Instance(new ArrayList<>());
    }

    public boolean saveInstance(Instance instance) {
        return true;
    }
}
