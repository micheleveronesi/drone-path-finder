package org.example.persistence;

import org.example.business.Point;

import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

public class Instance implements Serializable {
    private final List<Point> points;
    private final Instant timestamp;
    private static final long MAX_DURATION = 10000; // max duration in seconds

    public Instance(List<Point> points) {
        this.points = points;
        timestamp = Instant.now();
    }

    public boolean isTooOld() {
        Duration res = Duration.between(timestamp, Instant.now());
        return res.getSeconds() > MAX_DURATION;
    }

    public List<Point> getPoints() { return points; }

    public boolean isEmpty() { return points.isEmpty(); }

}
