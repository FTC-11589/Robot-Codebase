package org.firstinspires.ftc.teamcode.autonomous.testing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.autonomous.Auto;
import org.firstinspires.ftc.teamcode.autonomous.Robot;

@Disabled
@Autonomous(name="[TEST] Unlatch, Turn and Drive to Crater", group = "Tests")
public class UnlatchTurnDriveTest extends LinearOpMode
{
    Robot robot;
    Auto auto;
    Orientation lastOrientation;

    @Override
    public void runOpMode() throws InterruptedException {

        // Initialize objects
        robot = new Robot(hardwareMap);
        auto = new Auto(this, robot);

        waitForStart();

        // Step 1: Land Robot
        robot.baseSlideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.baseSlideMotor.setTargetPosition(9000);
        robot.baseSlideMotor.setPower(0.8);
        while (opModeIsActive() && robot.baseSlideMotor.isBusy()) {
            telemetry.addData("encoder-fwd", robot.baseSlideMotor.getCurrentPosition() + "  busy=" + robot.baseSlideMotor.isBusy());
            telemetry.update();
        }
        robot.baseSlideMotor.setPower(0.0);

        auto.rotate(160, 0.7);

        auto.driveForDistance(35, 0.3);

        while(opModeIsActive());

    }

}