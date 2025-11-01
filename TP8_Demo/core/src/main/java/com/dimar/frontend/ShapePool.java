package com.dimar.frontend;
import java.util.ArrayList;

public class ShapePool {
    private ArrayList<Shape> circlePool;
    private ArrayList<Shape> squarePool;

    public ShapePool() {
        circlePool = new ArrayList<>();
        squarePool = new ArrayList<>();
        for (int x = 0; x < 3; x++) {
            circlePool.add(new Circle());
            squarePool.add(new Square());
        }
    }

    public Shape obtain(String type) {
        if (type.equals("Circle") && !circlePool.isEmpty()) {
            return circlePool.remove(circlePool.size() - 1);
        }else if (type.equals("Square") && !squarePool.isEmpty()) {
            return squarePool.remove(squarePool.size() - 1);
        }

        return null;
    }

    public void release(Shape shape) {
        if (shape.getTipe().equals("Circle")) {
            circlePool.add(shape);
        }else if (shape.getTipe().equals("Square")) {
            squarePool.add(shape);
        }
    }

    public ArrayList<Shape> getPool(String type) {
        if (type.equals("Circle")) {
            return circlePool;
        }else if (type.equals("Square")) {
            return squarePool;
        }

        return new ArrayList<>();
    }
}
