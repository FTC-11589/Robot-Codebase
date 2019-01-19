package org.firstinspires.ftc.teamcode.autonomous.testing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.autonomous.Auto;

@Autonomous(name="[TEST] Navigation Target Tracking", group = "Tests")
public class NavigationTargetTrackingTest extends LinearOpMode
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

        telemetry.addData("Mode", "running");
        telemetry.update();

        while(opModeIsActive()) {
            // Find location of robot on the field
            auto.attemptFindNavigationalTarget();

            telemetry.addData("Location (X)", auto.robotPos.getX());
            telemetry.addData("Location (Y)", auto.robotPos.getY());
            telemetry.addData("Heading", auto.robotAngle);

            telemetry.update();
        }

    }


}