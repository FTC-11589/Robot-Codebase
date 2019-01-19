package org.firstinspires.ftc.teamcode.utilities.field;

import java.util.HashMap;
import java.util.Map;

public class FieldMap {
    // Creates a map: each Vector position is mapped to a string identifier
    private Map<FieldElement, Vector> map = new HashMap<>();

    public final double FULL_FIELD_LENGTH = 144;
    public final double QUADRANT_LENGTH = FULL_FIELD_LENGTH/2;
    public final double SQUARE_LENGTH = FULL_FIELD_LENGTH/6;
    public final double HALF_SQUARE_LENGTH = SQUARE_LENGTH/2;
    public final double MID_QUADRANT = QUADRANT_LENGTH/2;

    // Constructor generates the map
    public FieldMap() {
        generate();
    }

    private void generate() {
        // Quadrant 2
        // Minerals
        add(FieldElement.BLUE_DEPOT_LEFT_MINERAL, new Vector(-2*SQUARE_LENGTH, SQUARE_LENGTH));
        add(FieldElement.BLUE_DEPOT_MIDDLE_MINERAL, new Vector(-MID_QUADRANT, MID_QUADRANT));
        add(FieldElement.BLUE_DEPOT_RIGHT_MINERAL, new Vector(-SQUARE_LENGTH, 2*SQUARE_LENGTH));
        // Blue Depot
        add(FieldElement.BLUE_DEPOT, new Vector(-5*HALF_SQUARE_LENGTH,5*HALF_SQUARE_LENGTH));

        // Quadrant 3
        // Minerals
        add(FieldElement.RED_CRATER_LEFT_MINERAL, new Vector(-SQUARE_LENGTH, -5*HALF_SQUARE_LENGTH));
        add(FieldElement.RED_CRATER_MIDDLE_MINERAL, new Vector(-MID_QUADRANT, -MID_QUADRANT));
        add(FieldElement.RED_CRATER_RIGHT_MINERAL, new Vector(-5*HALF_SQUARE_LENGTH, -SQUARE_LENGTH));
        // Crater Position
        add(FieldElement.RED_CRATER_LEFT_EDGE, new Vector(-SQUARE_LENGTH,-11/2*HALF_SQUARE_LENGTH));
        add(FieldElement.RED_CRATER_CENTER_EDGE, new Vector(-5*HALF_SQUARE_LENGTH,-SQUARE_LENGTH));
        add(FieldElement.RED_CRATER_RIGHT_EDGE, new Vector(-5*HALF_SQUARE_LENGTH,-5*HALF_SQUARE_LENGTH));

        // Quadrant 4
        // Minerals
        add(FieldElement.RED_DEPOT_LEFT_MINERAL, new Vector(2*SQUARE_LENGTH, -SQUARE_LENGTH));
        add(FieldElement.RED_DEPOT_MIDDLE_MINERAL, new Vector(MID_QUADRANT, -MID_QUADRANT));
        add(FieldElement.RED_DEPOT_RIGHT_MINERAL, new Vector(SQUARE_LENGTH, -2*SQUARE_LENGTH));
        // Red Depot
        add(FieldElement.RED_DEPOT, new Vector(5*HALF_SQUARE_LENGTH,-5*HALF_SQUARE_LENGTH));

        // Quadrant 1
        // Minerals
        add(FieldElement.BLUE_CRATER_LEFT_MINERAL, new Vector(SQUARE_LENGTH, 2*SQUARE_LENGTH));
        add(FieldElement.BLUE_CRATER_MIDDLE_MINERAL, new Vector(MID_QUADRANT, MID_QUADRANT));
        add(FieldElement.BLUE_CRATER_RIGHT_MINERAL, new Vector(2*SQUARE_LENGTH, SQUARE_LENGTH));
        // Crater Positions
        add(FieldElement.BLUE_CRATER_LEFT_EDGE, new Vector(SQUARE_LENGTH,11/2*HALF_SQUARE_LENGTH));
        add(FieldElement.BLUE_CRATER_CENTER_EDGE, new Vector(5*HALF_SQUARE_LENGTH,SQUARE_LENGTH));
        add(FieldElement.BLUE_CRATER_RIGHT_EDGE, new Vector(5*HALF_SQUARE_LENGTH, SQUARE_LENGTH));

        // Nav Targets
        add(FieldElement.BLUE_ROVER, new Vector(0,QUADRANT_LENGTH));
        add(FieldElement.FRONT_OF_BLUE_ROVER, new Vector(0, 5*HALF_SQUARE_LENGTH));

        add(FieldElement.FRONT_CRATERS, new Vector(QUADRANT_LENGTH,0));
        add(FieldElement.FRONT_OF_FRONT_CRATERS, new Vector(5*HALF_SQUARE_LENGTH, 0));

        add(FieldElement.BACK_SPACE, new Vector(-QUADRANT_LENGTH,0));
        add(FieldElement.FRONT_OF_BACK_SPACE, new Vector(-5*HALF_SQUARE_LENGTH, 0));

        add(FieldElement.RED_FOOTPRINT, new Vector(0,-QUADRANT_LENGTH));
        add(FieldElement.FRONT_OF_RED_FOOTPRINT, new Vector(0, -5*HALF_SQUARE_LENGTH));
    }

    // Accesses the position of a specific element based on quadrant and name
    public Vector get(FieldElement element) {
        return map.get(element);
    }

    // Adds an element to the field map with quadrant, name, and vector position
    private void add(FieldElement element, Vector position) {
        map.put(element, position);
    }
}