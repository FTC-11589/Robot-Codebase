package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.utilities.IMU;

public class Robot {

    public DcMotorEx backLeftDriveMotor, backRightDriveMotor, elevatorMotor;
    public DcMotor leftCollectorFeederMotor, rightCollectorFeederMotor;
    public Servo armHingeRightServo, armHingeLeftServo;

    public BNO055IMU imu;

    public final static String VUFORIA_KEY = "AT/or93/////AAABmS/xDnxbNE/IkA0tX4Ff/D0/EXYxHCIM8hCQqWKqTCN4u4j+AaznN6iFrdjOLMEomg8qOf/sUo77rQX/bk20wF73EqQeRWpU0SWlVMPIwfmUwb9Qev0k/eVSLLFTLvYmWceWkQnrxD7mX79iScVDsuIKWRaEFAOWZb1IB8mXvrCpYOOZ+k3HW8A/IiQ7/ZzDKRhIhC2lMV4csQXh7vDaZ+3hflMhEyM+Ivhq6TB+ep2Xt8wzzil3npGUqaku9drUKrghK0hyM3AW1D011gsKZulE6tDQEByKqLFENInG+jOPSz29QW4gmdRdAfa8LUEqpwrC1jSloMKYq7Gg/p4zH2sF4Zj3mXOgLW1LMO0vokvo";

    public final double WHEEL_DIAMETER = 4; // Inches
    public final int TICKS_PER_REVOLUTION = 1440; // Inches
    public final double CAMERA_FORWARD_DISPLACEMENT = 1.0; // Inches
    public final double CAMERA_LEFT_DISPLACEMENT = 1.0; // Inches


    public Robot(HardwareMap hardwareMap) {
        backRightDriveMotor = (DcMotorEx) hardwareMap.dcMotor.get("drive_right");
        backLeftDriveMotor = (DcMotorEx) hardwareMap.dcMotor.get("drive_left");
//        elevatorMotor = (DcMotorEx) hardwareMap.dcMotor.get("elevator");
//        leftCollectorFeederMotor = hardwareMap.dcMotor.get("feeder_left");
//        rightCollectorFeederMotor = hardwareMap.dcMotor.get("feeder_right");
//        armHingeRightServo = hardwareMap.servo.get("arm_hinge_right");
//        armHingeLeftServo = hardwareMap.servo.get("arm_hinge_left");

        imu = hardwareMap.get(BNO055IMU.class, "imu");

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();

        parameters.mode                = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled      = false;

        imu.initialize(parameters);

        backLeftDriveMotor.setDirection(DcMotorSimple.Direction.REVERSE);
//        leftCollectorFeederMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        backRightDriveMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftDriveMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        elevatorMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        elevatorMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        backRightDriveMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftDriveMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        elevatorMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }



}
