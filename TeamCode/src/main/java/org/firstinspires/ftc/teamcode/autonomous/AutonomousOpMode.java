package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;

@Autonomous(name="Autonomous Mode")
public class AutonomousOpMode extends LinearOpMode {

    final double WHEEL_CIRCUMFERENCE = 12.56;

    DcMotorEx backLeftDriveMotor, backRightDriveMotor, elevatorMotor;
    DcMotor leftCollectorFeederMotor, rightCollectorFeederMotor;
    Servo collectorServo, armHingeServo;
    ElapsedTime timer = new ElapsedTime();

    BNO055IMU imu;

    // State used for updating telemetry
    Orientation angle;
    Acceleration gravity;

    @Override
    public void runOpMode() throws InterruptedException {
        backRightDriveMotor = (DcMotorEx) hardwareMap.dcMotor.get("drive_right");
        backLeftDriveMotor = (DcMotorEx) hardwareMap.dcMotor.get("drive_left");
        elevatorMotor = (DcMotorEx) hardwareMap.dcMotor.get("elevator");
        leftCollectorFeederMotor = hardwareMap.dcMotor.get("feeder_left");
        rightCollectorFeederMotor = hardwareMap.dcMotor.get("feeder_right");
        collectorServo = hardwareMap.servo.get("collector_hinge");
        armHingeServo = hardwareMap.servo.get("arm_hinge");

        backLeftDriveMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        leftCollectorFeederMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        backRightDriveMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftDriveMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        elevatorMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        elevatorMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        backRightDriveMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftDriveMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        elevatorMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        telemetry.setAutoClear(true);
        telemetry.setCaptionValueSeparator("\r\n");
        telemetry.setItemSeparator(telemetry.getCaptionValueSeparator());

        telemetry.addData("Program Status", "Initialized");

        backRightDriveMotor.setMotorEnable();
        backLeftDriveMotor.setMotorEnable();
        elevatorMotor.setMotorEnable();

        waitForStart();



        // Step 1: Land Robot
        elevatorMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        elevatorMotor.setTargetPosition(-8435);
        elevatorMotor.setPower(0.25);
        while (opModeIsActive() && elevatorMotor.isBusy())
        {
            telemetry.addData("encoder-fwd", elevatorMotor.getCurrentPosition() + "  busy=" + elevatorMotor.isBusy());
            telemetry.update();
        }
        elevatorMotor.setPower(0.0);

        // Step 2: Move

        resetStartTime();
        while(opModeIsActive() && getRuntime() < 1.75) {
            backLeftDriveMotor.setPower(0.5);
            backRightDriveMotor.setPower(-0.5);
        }


        // Drive to depot
        backLeftDriveMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRightDriveMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        backLeftDriveMotor.setTargetPosition((int)(1440 * (91.4/WHEEL_CIRCUMFERENCE)));
        backRightDriveMotor.setTargetPosition((int)(1440 * (91.4/WHEEL_CIRCUMFERENCE)));

        while (opModeIsActive() && backRightDriveMotor.isBusy() && backLeftDriveMotor.isBusy()) {
            backLeftDriveMotor.setPower(1);
            backRightDriveMotor.setPower(1);
        }

        backLeftDriveMotor.setPower(0);
        backRightDriveMotor.setPower(0);

    }
}
