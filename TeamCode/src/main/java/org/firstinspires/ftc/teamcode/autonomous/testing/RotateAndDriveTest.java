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

        auto.driveForDistance(5, 0.8);

        auto.rotate(90, 0.3);
        auto.rotate(90, 0.3);

        auto.driveForDistance(5, 0.8);

    }


}