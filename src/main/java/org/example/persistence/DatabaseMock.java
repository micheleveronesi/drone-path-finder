package org.example.persistence;

import org.example.business.point.Point;

import java.util.List;

public class DatabaseMock implements Persistence {
    public List<Point> getLastInstance() {
        return null;
    }
}
