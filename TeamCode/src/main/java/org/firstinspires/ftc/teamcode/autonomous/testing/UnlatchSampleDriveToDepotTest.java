package org.firstinspires.ftc.teamcode.autonomous.testing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.autonomous.Auto;
import org.firstinspires.ftc.teamcode.autonomous.Robot;
import org.firstinspires.ftc.teamcode.utilities.GoldDetector;

@Autonomous(name="[TEST] >> Unlatch, Sample, Drive To Depot", group = "Tests")
public class UnlatchSampleDriveToDepotTest extends LinearOpMode
{
    Robot robot;
    Auto auto;

    @Override
    public void runOpMode() throws InterruptedException {

        // Initialize objects
        robot = new Robot(hardwareMap);
        auto = new Auto(this, robot);

        GoldDetector.Position goldPos = GoldDetector.Position.NONE;

        auto.initSampling();

        waitForStart();

        goldPos = auto.attemptSampleMinerals(6);

        telemetry.addData("Gold Mineral Position", goldPos.toString());
        telemetry.update();

        // Step 1: Land Robot
        auto.land();

        // Step 2: Drive forward a bit
        //auto.driveForDistance(15, 0.3);

        sleep(500);

        // Step 3: Unlatch
        auto.rotateRightPivot(7, 0.7, 3);

        // Step 5: Rotate back to initial orientation
        auto.rotateLeftPivot(-7, 0.7, 3);

        switch (goldPos){
            case LEFT:
                auto.rotateRightPivot(27, 0.8, 3);
                auto.driveForDistance(-50, 0.8);
                auto.rotateRightPivot(-27, 0.8,3);
                auto.rotateRightPivot(-45, 0.8,3);
                auto.driveForDistance(-40, 0.8);
                auto.dropTeamMarker();
                auto.driveForDistance(76, 0.8);
                break;
            case NONE:
            case CENTER:
                auto.driveForDistance(-45, 0.8);
                auto.dropTeamMarker();
                auto.driveForDistance(-15, 0.8);
                auto.rotateLeftPivot(-45, 0.8, 3);
                auto.driveForDistance(78, 0.8);
                break;
            case RIGHT:
                auto.rotateLeftPivot(-26, 0.8, 3);
                auto.driveForDistance(-50, 0.8);
                auto.rotateLeftPivot(26, 0.8,3);
                auto.rotateLeftPivot(45, 0.8,3);
                auto.driveForDistance(-30, 0.8);
                auto.dropTeamMarker();
                auto.driveForDistance(-10, 0.8);
                auto.rotateLeftPivot(-90, 0.8, 3);
                auto.driveForDistance(78, 0.8);
                break;

        }

        while(opModeIsActive());
    }

}