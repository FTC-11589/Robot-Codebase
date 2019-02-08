package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

@Autonomous(name="[BACKUP] Autonomous Mode")
public class AutonomousOpModeBackup extends LinearOpMode {

    final double WHEEL_CIRCUMFERENCE = 12.56;

    Auto auto;
    Robot robot;

    @Override
    public void runOpMode() throws InterruptedException {
        robot = new Robot(hardwareMap);
        auto = new Auto(this, robot);

        telemetry.addData("Program Status", "Initialized");

        waitForStart();

        // Step 1: Land Robot
        robot.baseSlideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.baseSlideMotor.setTargetPosition(9000);
        robot.baseSlideMotor.setPower(0.8);
        while (opModeIsActive() && robot.baseSlideMotor.isBusy());
        robot.baseSlideMotor.setPower(0.0);

        auto.rotate(180, 0.7);

        // Drive to depot
        robot.backLeftDriveMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.backRightDriveMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        robot.backLeftDriveMotor.setTargetPosition((int)(1440 * (91.4/WHEEL_CIRCUMFERENCE)));
        robot.backRightDriveMotor.setTargetPosition((int)(1440 * (91.4/WHEEL_CIRCUMFERENCE)));

        robot.backLeftDriveMotor.setPower(1);
        robot.backRightDriveMotor.setPower(1);

        while (opModeIsActive() && robot.backRightDriveMotor.isBusy() && robot.backLeftDriveMotor.isBusy());

        robot.backLeftDriveMotor.setPower(0);
        robot.backRightDriveMotor.setPower(0);

    }
}
