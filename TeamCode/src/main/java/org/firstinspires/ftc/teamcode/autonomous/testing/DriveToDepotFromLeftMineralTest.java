package org.firstinspires.ftc.teamcode.autonomous.testing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.autonomous.Auto;
import org.firstinspires.ftc.teamcode.autonomous.Robot;

@Autonomous(name="[TEST] Drive to Depot from Left Mineral", group = "Tests")
public class DriveToDepotFromLeftMineralTest extends LinearOpMode
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
        auto.rotate(25, 0.3);
        auto.driveForDistance(35, 0.3);

        // Rotate toward depot
        auto.rotate(-25 + 45, 0.3);

        // Drive to depot and rotate 90 degrees
        auto.driveForDistance(25, 0.3);
        auto.rotate(-90, 0.7);

        // Drop icon
        auto.dropTeamMarker();

        // Drive to crater
        auto.driveForDistance(77, 0.8);

        while(opModeIsActive());

    }

}