package org.firstinspires.ftc.teamcode.autonomous.testing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.autonomous.Auto;
import org.firstinspires.ftc.teamcode.autonomous.Robot;
import org.firstinspires.ftc.teamcode.utilities.GoldDetector;

@Autonomous(name="[TEST] Drive to Depot from Right Mineral", group = "Tests")
public class DriveToDepotFromRightMineralTest extends LinearOpMode
{
    Robot robot;
    Auto auto;

    @Override
    public void runOpMode() throws InterruptedException {

        // Initialize objects
        robot = new Robot(hardwareMap);
        auto = new Auto(this, robot);

        waitForStart();

        // Assume start after unlatching

        // Push mineral
        auto.rotate(-25, 0.3);
        auto.driveForDistance(35, 0.3);

        // Rotate toward depot
        auto.rotate(25 + 45, 0.3);

        // Drive to depot and rotate 180 degrees
        auto.driveForDistance(25, 0.3);
        auto.rotate(160, 0.7);

        // Drop icon
        auto.dropTeamMarker();

        // Drive to crater
        auto.driveForDistance(77, 0.8);

        while(opModeIsActive());

    }

}