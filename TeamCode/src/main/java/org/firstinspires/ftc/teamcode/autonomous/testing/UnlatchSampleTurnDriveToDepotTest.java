package org.firstinspires.ftc.teamcode.autonomous.testing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.autonomous.Auto;
import org.firstinspires.ftc.teamcode.autonomous.Robot;
import org.firstinspires.ftc.teamcode.utilities.GoldDetector;

@Disabled
@Autonomous(name="[TEST] Unlatch, Sample, Turn and Drive to Depot", group = "Tests")
public class UnlatchSampleTurnDriveToDepotTest extends LinearOpMode
{
    Robot robot;
    Auto auto;
    Orientation lastOrientation;

    @Override
    public void runOpMode() throws InterruptedException {

        // Initialize objects
        robot = new Robot(hardwareMap);
        auto = new Auto(this, robot);

        GoldDetector.Position goldPos = GoldDetector.Position.NONE;

        auto.initSampling();

        waitForStart();

        goldPos = auto.attemptSampleMinerals(3);

        telemetry.addData("Gold Mineral Position", goldPos.toString());
        telemetry.update();

        // Step 1: Land Robot
        auto.land();

        // Step 2: Drive forward a bit
        auto.driveForDistance(1, 0.7);

        // Step 3: Unlatch
        auto.rotateRightPivot(10, 0.7, 3);

        // Step 4: Drive back a bit
        auto.driveForDistance(-5, 0.5);

        // Step 5: Rotate back to initial orientation
        auto.rotateRightPivot(-10, 0.7, 3);

        switch (goldPos){
            case LEFT:
                auto.rotate(-25, 0.3);
                auto.driveForDistance(-35, 0.3);
                break;
            case NONE:
            case CENTER:
                auto.driveForDistance(-45, 0.3);
                break;
            case RIGHT:
                auto.rotate(25, 0.3);
                auto.driveForDistance(-35, 0.3);
                break;

        }

        while(opModeIsActive());

    }

}