package org.example.persistence;

import org.example.business.point.Point;

import java.util.List;

public interface Persistence {
    List<Point> getLastInstance();
}
