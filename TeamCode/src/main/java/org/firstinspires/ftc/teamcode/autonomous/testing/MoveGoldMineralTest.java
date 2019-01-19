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

        auto.initSampling();

        waitForStart();

        telemetry.addData("Mode", "Looking for gold mineral");
        telemetry.update();

        GoldDetector.Position goldPos = auto.attemptSampleMinerals(3);

        telemetry.addData("Gold Mineral Position", goldPos.toString());
        telemetry.update();

        // Step 1: Land Robot
        robot.baseSlideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.baseSlideMotor.setTargetPosition(-8435);
        robot.baseSlideMotor.setPower(0.25);
        while (opModeIsActive() && robot.baseSlideMotor.isBusy())
        {
            telemetry.addData("encoder-fwd", robot.baseSlideMotor.getCurrentPosition() + "  busy=" + robot.baseSlideMotor.isBusy());
            telemetry.update();
        }
        robot.baseSlideMotor.setPower(0.0);

        // Rotate
        auto.rotate(180, 0.7);

        // Push
        switch (goldPos){
            case NONE:
                auto.driveForDistance(5, 0.8);
                break;
            case LEFT:
                auto.rotate(30, 0.5);
                auto.driveForDistance(7, 0.8);
                break;
            case CENTER:
                auto.driveForDistance(5, 0.8);
                break;
            case RIGHT:
                auto.rotate(-30, 0.5);
                auto.driveForDistance(7, 0.8);
                break;
        }


    }


}