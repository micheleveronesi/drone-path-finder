package org.example.persistence;

public interface Persistence {
    Instance getLastInstance();
    boolean saveInstance(Instance instance);
}
