package com.dimar.frontend;

public class ShapeFactory {
    private ShapePool shapePool;
    public ShapeFactory() {
        shapePool = new ShapePool();
    }

    public Shape createShape(String type) {
        return shapePool.obtain(type);
    }

    public void release(Shape shape) {
        shapePool.release(shape);
    }
}
