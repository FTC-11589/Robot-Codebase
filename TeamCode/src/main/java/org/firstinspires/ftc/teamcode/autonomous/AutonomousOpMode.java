package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.utilities.Navigation;

@Autonomous(name="Drive Avoid Imu", group="Exercises")
public class AutonomousOpMode extends LinearOpMode
{
    Robot robot;
    Auto auto;

    @Override
    public void runOpMode() throws InterruptedException
    {

        // Initialize objects
        robot = new Robot(hardwareMap);
        auto = new Auto(this, robot);


        // Make sure the imu gyro is calibrated before continuing.
        telemetry.addData("Mode", "calibrating...");
        telemetry.update();
        while (!isStopRequested() && !robot.imu.isGyroCalibrated())
        {
            sleep(50);
            idle();
        }


        telemetry.addData("Mode", "waiting for start");
        telemetry.addData("imu calib status", robot.imu.getCalibrationStatus().toString());
        telemetry.update();

        waitForStart();

        telemetry.addData("Mode", "running");
        telemetry.update();

        // Face navigation target
        auto.rotate(80, 0.2);

        // Drive towards target
        auto.driveForDistance(4, 0.8);

        // Find location of robot on the field
        auto.attemptFindNavigationalTarget();

        telemetry.addData("Location (X)", auto.robotPos.getX());
        telemetry.addData("Location (Y)", auto.robotPos.getY());
        telemetry.addData("Heading", auto.robotAngle);

        telemetry.update();

        while(opModeIsActive()) {

        }
    }


}