package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;
import org.firstinspires.ftc.teamcode.autonomous.Robot;
import org.firstinspires.ftc.teamcode.utilities.GoldDetector;
import org.firstinspires.ftc.teamcode.utilities.Navigation;

@Autonomous(name="Drive Avoid Imu", group="Exercises")
public class AutonomousOpMode extends LinearOpMode
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

        GoldDetector.Position goldPos = auto.attemptSampleMinerals(5);

        telemetry.addData("Gold Mineral Position", goldPos.toString());
        telemetry.update();

        auto.rotate(180, 0.7);

        switch (goldPos){
            case NONE:
                auto.driveForDistance(15, 0.8);
                break;
            case LEFT:
                auto.rotate(30, 0.5);
                auto.driveForDistance(20, 0.8);
                break;
            case CENTER:
                auto.driveForDistance(15, 0.8);
                break;
            case RIGHT:
                auto.rotate(-30, 0.5);
                auto.driveForDistance(20, 0.8);
                break;
        }


    }


}