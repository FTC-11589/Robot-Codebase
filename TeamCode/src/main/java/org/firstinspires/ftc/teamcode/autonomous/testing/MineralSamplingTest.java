package org.firstinspires.ftc.teamcode.autonomous.testing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.autonomous.Auto;
import org.firstinspires.ftc.teamcode.utilities.GoldDetector;

@Autonomous(name="[TEST] Mineral Sampling Test", group = "Tests")
public class MineralSamplingTest extends LinearOpMode
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

        while (opModeIsActive()) {
            GoldDetector.Position goldPos = auto.attemptSampleMinerals();

            telemetry.addData("Gold Mineral Position", goldPos.toString());
            telemetry.update();
        }
    }


}