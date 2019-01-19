package org.firstinspires.ftc.teamcode.utilities.field;

public class Vector {
    private double x;
    private double y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setX(double newX) {
        x = newX;
    }
    public void setY(double newY) {
        y = newY;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    // Returns the vector's distance from another vector (thank you, pythagoras)
    public double distanceFrom(Vector other) {
        return Math.sqrt(Math.pow(x - other.getX(), 2) + Math.pow(y - other.getY(), 2));
    }
    public double distanceFromOrigin() {
        return distanceFrom(new Vector(0, 0));
    }

    // Returns the vector sum/difference of this vector and another vector
    public Vector sum(Vector other) {
        return new Vector(x + other.getX(), y + other.getY());
    }
    public Vector changeInPosition(Vector other) {
        return new Vector(x - other.getX(), y - other.getY());
    }

    public int quadrant() {
        if (x > 0 && y > 0)
            return 1;
        else if (x < 0 && y > 0)
            return 2;
        else if (x < 0 && y < 0)
            return 3;
        else if (x > 0 && y < 0)
            return 4;

        return 0;
    }

    public String toString() {
        return "(" + Math.round(x) + ", " + Math.round(y) + ")";
    }
}