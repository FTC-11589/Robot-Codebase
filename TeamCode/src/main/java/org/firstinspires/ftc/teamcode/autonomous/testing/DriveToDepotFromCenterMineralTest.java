package org.firstinspires.ftc.teamcode.autonomous.testing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.autonomous.Auto;
import org.firstinspires.ftc.teamcode.autonomous.Robot;

@Autonomous(name="[TEST] Drive to Depot from Center Mineral", group = "Tests")
public class DriveToDepotFromCenterMineralTest extends LinearOpMode
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
        auto.driveForDistance(35, 0.3);

        // Drive to depot and rotate 180 degrees
        auto.driveForDistance(25, 0.3);
        auto.rotate(160, 0.7);

        // Drop icon
        auto.dropTeamMarker();

        // Rotate toward crater
        auto.rotate(30, 0.7);

        // Drive to crater
        auto.driveForDistance(77, 0.8);

        while(opModeIsActive());

    }

}