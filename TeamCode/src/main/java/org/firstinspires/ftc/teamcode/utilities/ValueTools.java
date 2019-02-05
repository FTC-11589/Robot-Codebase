package org.firstinspires.ftc.teamcode.utilities;

public class ValueTools {
    public static int getInt(boolean value) {
        return value ? 1 : 0;
    }

    public enum MotionType {NONE, FORWARD, BACKWARD}

    public static MotionType getMotionType(double power) {
        return MotionType.values()[(int)Math.ceil(Math.signum(power) >= 0 ? power : -power + 1)];
    }

    public static char getMotionTypeChar(double power) {
        switch (getMotionType(power))
        {
            case BACKWARD:
                return '▼';
            case FORWARD:
                return '▲';
            default:
            case NONE:
                return '■';
        }
    }
}
