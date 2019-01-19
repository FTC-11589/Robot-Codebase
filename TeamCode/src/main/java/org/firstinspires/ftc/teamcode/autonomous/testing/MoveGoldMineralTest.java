package org.firstinspires.ftc.teamcode.autonomous.testing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.autonomous.Auto;
import org.firstinspires.ftc.teamcode.utilities.GoldDetector;

@Autonomous(name="[TEST] Move Gold Mineral Test", group = "Tests")
public class MoveGoldMineralTest extends LinearOpMode
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

        GoldDetector.Position goldPos = auto.attemptSampleMinerals();

        telemetry.addData("Gold Mineral Position", goldPos.toString());
        telemetry.update();

        switch (goldPos) {
            case LEFT:
                auto.rotate(10, 0.5);
                auto.driveForDistance(4, 0.8);
                break;
            case CENTER:
                auto.driveForDistance(4, 0.8);
                break;
            case RIGHT:
                auto.rotate(-10, 0.5);
                auto.driveForDistance(4, 0.8);
                break;
            case NONE:
                break;
        }
    }


}