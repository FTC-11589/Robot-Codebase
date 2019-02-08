package org.firstinspires.ftc.teamcode.autonomous.testing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.autonomous.Auto;
import org.firstinspires.ftc.teamcode.autonomous.Robot;

@Autonomous(name="[TEST] Rotate Test", group = "Tests")
public class RotateTest extends LinearOpMode
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

        telemetry.addData("Step: ", "Rotating 1");
        telemetry.update();

        auto.rotate(90, 0.3);

        telemetry.addData("Step: ", "Rotating 2");
        telemetry.update();

        auto.rotate(90, 0.3);

        telemetry.addData("Step: ", "Rotating 3");
        telemetry.update();

        auto.rotate(-90, 0.3);

        telemetry.addData("Step: ", "Rotating 4");
        telemetry.update();

        auto.rotate(-90, 0.3);

        telemetry.addData("Step: ", "Done");
        telemetry.update();


        while (opModeIsActive());

    }


}