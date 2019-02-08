package org.firstinspires.ftc.teamcode.autonomous.testing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.autonomous.Auto;
import org.firstinspires.ftc.teamcode.autonomous.Robot;

@Autonomous(name="[TEST] Rotate and Drive Test", group = "Tests")
public class RotateAndDriveTest extends LinearOpMode
{
    Robot robot;
    Auto auto;

    @Override
    public void runOpMode() throws InterruptedException
    {

        // Initialize objects
        robot = new Robot(hardwareMap);
        auto = new Auto(this, robot);

        waitForStart();

        telemetry.addData("Step: ", "Driving for distance 1");
        telemetry.update();

        auto.driveForDistance(25, 0.8);

        telemetry.addData("Step: ", "Rotating");
        telemetry.update();


        auto.rotate(90, 0.3);
        auto.rotate(90, 0.3);

        telemetry.addData("Step: ", "Driving for distance 2");
        telemetry.update();


        auto.driveForDistance(25, 0.8);

        while (opModeIsActive());
    }


}