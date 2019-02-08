package org.firstinspires.ftc.teamcode.autonomous.testing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.autonomous.Robot;
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

        GoldDetector.Position goldPos = GoldDetector.Position.NONE;

        auto.initSampling();

        waitForStart();
        if(goldPos == GoldDetector.Position.NONE) {
            goldPos = auto.attemptSampleMinerals(5);
        }

        auto.rotate(180, 0.3);

        telemetry.addData("Gold Mineral Position", goldPos.toString());
        telemetry.update();

        switch (goldPos){
            case NONE:
            case CENTER:
                auto.driveForDistance(60, 0.8);
                break;
            case LEFT:
                auto.rotate(21, 0.3);
                auto.driveForDistance(40, 0.8);
                auto.rotate(-(21+37), 0.3);
                auto.driveForDistance(25.3, 0.8);
                break;
            case RIGHT:
                auto.rotate(-21, 0.3);
                auto.driveForDistance(40, 0.8);
                auto.rotate(21+37, 0.3);
                auto.driveForDistance(25.3, 0.8);
                break;
        }


    }


}