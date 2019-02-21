package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.autonomous.Robot;
import org.firstinspires.ftc.teamcode.utilities.GoldDetector;
import org.firstinspires.ftc.teamcode.utilities.Navigation;
import org.firstinspires.ftc.teamcode.utilities.field.FieldMap;
import org.firstinspires.ftc.teamcode.utilities.field.Vector;

public class Auto {

    private LinearOpMode opMode;
    private Robot robot;
    public FieldMap fieldMap;

    Navigation navigation;
    GoldDetector goldDetector;

    private Orientation lastAngles = new Orientation();
    private double globalAngle;

    // Navigation/Positional Components
    public Vector robotPos; // Position on field
    public double robotAngle; // Angle relative to (0, 0) on field
    private double initialIMUHeading; // IMU when the robot first hits the floor


    public Auto(LinearOpMode opMode, Robot robot) {
        this.opMode = opMode;
        this.robot = robot;
        fieldMap = new FieldMap();

    }

    public void initNavigation() {
        navigation = new Navigation(opMode.hardwareMap, robot.CAMERA_FORWARD_DISPLACEMENT, robot.CAMERA_LEFT_DISPLACEMENT);
        navigation.setupTracker();
    }

    public void initSampling() {
        goldDetector = new GoldDetector(opMode.hardwareMap);
        goldDetector.initVuforia();
        goldDetector.initTfod();
    }

    public void resetAllEncoders() {
        resetDriveEncoders();
        resetSlideEncoders();
    }

    public void resetDriveEncoders() {
        robot.backRightDriveMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backLeftDriveMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    }

    public void resetSlideEncoders() {
        robot.baseSlideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.extensionSlideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    }



    /**
     * Resets the cumulative angle tracking to zero.
     */
    public void resetAngle()
    {
        lastAngles = robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        globalAngle = 0;
    }

    /**
     * Get current cumulative angle rotation from last reset.
     * @return Angle in degrees. + = left, - = right.
     */
    public double getAngle()
    {
        // We experimentally determined the Z axis is the axis we want to use for heading angle.
        // We have to process the angle because the imu works in euler angles so the Z axis is
        // returned as 0 to +180 or 0 to -180 rolling back to -179 or +179 when rotation passes
        // 180 degrees. We detect this transition and track the total cumulative angle of rotation.

        Orientation angles = robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        double deltaAngle = angles.firstAngle - lastAngles.firstAngle;

        if (deltaAngle < -180)
            deltaAngle += 360;
        else if (deltaAngle > 180)
            deltaAngle -= 360;

        globalAngle += deltaAngle;

        lastAngles = angles;

        return globalAngle;
    }

    /**
     * See if we are moving in a straight line and if not return a power correction value.
     * @return Power adjustment, + is adjust left - is adjust right.
     */
    public double checkDirection()
    {
        // The gain value determines how sensitive the correction is to direction changes.
        // You will have to experiment with your robot to get small smooth direction changes
        // to stay on a straight line.
        double correction, angle, gain = .10;

        angle = getAngle();

        if (angle == 0)
            correction = 0;             // no adjustment.
        else
            correction = -angle;        // reverse sign of angle for correction.

        correction = correction * gain;

        return correction;
    }

    /**
     * Rotate left or right the number of degrees. Does not support turning more than 180 degrees.
     * @param degrees Degrees to turn, + is left - is right
     */
    public void rotate(int degrees, double power) {

        robot.backRightDriveMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backLeftDriveMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        double leftPower, rightPower;

        // restart imu movement tracking.
        resetAngle();

        // getAngle() returns + when rotating counter clockwise (left) and - when rotating
        // clockwise (right).

        if (degrees < 0) {   // turn right.
            leftPower = power;
            rightPower = -power;
        } else if (degrees > 0) {   // turn left.
            leftPower = -power;
            rightPower = power;
        } else return;

        // set power to rotate.
        robot.backLeftDriveMotor.setPower(leftPower);
        robot.backRightDriveMotor.setPower(rightPower);

        // rotate until turn is completed.
        if (degrees < 0) {
            // On right turn we have to get off zero first.
            while (opMode.opModeIsActive() && getAngle() > 0) {}

            while (opMode.opModeIsActive() && getAngle() > degrees) {}
        } else {    // left turn.
            while (opMode.opModeIsActive() && getAngle() < degrees) {}
        }

        // turn the motors off.
        robot.backLeftDriveMotor.setPower(0);
        robot.backRightDriveMotor.setPower(0);

        // wait for rotation to stop.
        opMode.sleep(1000);

        // reset angle tracking on new heading.
        resetAngle();
    }

    /**
     * Converts a radian measure angle to degree
     * @param radians Angle is radians to convert
     */
    public static int convertToDegrees(double radians) {
        return (int) (radians * 180 / Math.PI);
    }

    /**
     * Attempts to find a navigational target and update the robot's location
     */
    public void attemptFindNavigationalTarget() {
        while(opMode.opModeIsActive() && !navigation.isTargetVisible()) {
            navigation.lookForTargets();
        }
        robotPos = navigation.getRobotPosition();
    }

    /**
     * Drives a specific distance
     */
    public void driveForDistance(double distance, double power) {
        resetDriveEncoders();

        robot.backRightDriveMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.backLeftDriveMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        int position = (int)(distance / (Math.PI * robot.WHEEL_DIAMETER)) * robot.TICKS_PER_REVOLUTION;

        robot.backRightDriveMotor.setTargetPosition(position);
        robot.backLeftDriveMotor.setTargetPosition(position);

        robot.backRightDriveMotor.setPower(power);
        robot.backLeftDriveMotor.setPower(power);

        while (opMode.opModeIsActive() && robot.backLeftDriveMotor.isBusy() && robot.backRightDriveMotor.isBusy());

        robot.backRightDriveMotor.setPower(0);
        robot.backLeftDriveMotor.setPower(0);
    }

    /**
     * Attempts to sample the field for a gold mineral
     */
    public GoldDetector.Position attemptSampleMinerals(int timeout) {
        GoldDetector.Position goldPos = GoldDetector.Position.NONE;

        goldDetector.activateTfod();

        opMode.resetStartTime();
        while(opMode.opModeIsActive() && opMode.getRuntime() <= timeout) {
            goldPos = goldDetector.find();
            if(goldPos != GoldDetector.Position.NONE) {
                break;
            }
        }

        goldDetector.shutdownTfod();

        return goldPos;
    }

    public void dropTeamMarker() {
        opMode.resetStartTime();
        while(opMode.opModeIsActive() && opMode.getRuntime() <= 2) {
            robot.rightArmHingeServo.setPosition(robot.ARM_HINGE_SERVO_MAX_POS);
            robot.leftArmHingeServo.setPosition(robot.ARM_HINGE_SERVO_MAX_POS);
        }
        opMode.resetStartTime();
        while(opMode.opModeIsActive() && opMode.getRuntime() <= 2) {
            robot.rightArmHingeServo.setPosition(0);
            robot.leftArmHingeServo.setPosition(0);
        }
    }


}
