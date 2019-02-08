package org.firstinspires.ftc.teamcode.autonomous.testing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.autonomous.Robot;
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

        GoldDetector.Position goldPos = GoldDetector.Position.NONE;

        auto.initSampling();

        waitForStart();
        if(goldPos == GoldDetector.Position.NONE) {
            goldPos = auto.attemptSampleMinerals(1000);
        }

        auto.rotate(160, 0.3);

        telemetry.addData("Gold Mineral Position", goldPos.toString());
        telemetry.update();

        switch (goldPos){
            case LEFT:
                auto.rotate(21, 0.3);
                break;
            case RIGHT:
                auto.rotate(-21, 0.3);
                break;
        }

        while (opModeIsActive());
    }


}