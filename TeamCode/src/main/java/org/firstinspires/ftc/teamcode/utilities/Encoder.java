package org.firstinspires.ftc.teamcode.utilities;

import com.qualcomm.robotcore.hardware.DcMotorEx;

public class Encoder {

    private DcMotorEx motor;
    private double wheelDiameter; // wheel diameter in inches

    private final static int TETRIX_TICKS_PER_REV = 1440;

    public Encoder(DcMotorEx motor, double diameter) {
        this.motor = motor;
        wheelDiameter = diameter;
    }

    public int ticksPerRev() {
        return TETRIX_TICKS_PER_REV;
    }

    public int getEncoderCount() {
        return motor.getCurrentPosition();
    }

    public double motorRotations() {
        return (double) getEncoderCount() / ticksPerRev();
    }

    public double linDistance() {
        return motorRotations() * getWheelCircumference();
    }


    // Shortcut methods for resetting, run without, run to position, and setting target position
    public void reset() {
        motor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
    }
    public void runWith() {
        motor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
    }
    public void setup() {
        reset();
        runWith();
    }
    public void runWithout() {
        motor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
    }
    public void runToPosition() {
        motor.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
    }
    public void setTarget(double linDistance) {
        int encoderPos = (int) (linDistance * ticksPerRev() / getWheelCircumference());
        motor.setTargetPosition(encoderPos);
    }
    public void setEncoderTarget(int encoderTicks) {
        motor.setTargetPosition(encoderTicks);
    }

    public DcMotorEx getMotor() {
        return motor;
    }

    public double getWheelDiameter() {
        return wheelDiameter;
    }
    public double getWheelCircumference() {
        return Math.PI * wheelDiameter;
    }

}