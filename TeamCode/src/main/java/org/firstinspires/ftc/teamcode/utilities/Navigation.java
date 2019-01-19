package org.firstinspires.ftc.teamcode.utilities;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.teamcode.autonomous.Robot;
import org.firstinspires.ftc.teamcode.utilities.field.Vector;

import java.util.ArrayList;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.YZX;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.FRONT;

public class Navigation {

    private static final String VUFORIA_KEY = Robot.VUFORIA_KEY;

    // Since ImageTarget trackables use mm to specify their dimensions, we must use mm for all the physical dimension.
    private static final float mmPerInch = 25.4f;
    private static final float mmFTCFieldWidth = (12*6) * mmPerInch;
    private static final int targetHeight = 6;
    private static final float mmTargetHeight = (targetHeight) * mmPerInch;

    // The camera on the RC that we are using (FRONT or BACK)
    private static final VuforiaLocalizer.CameraDirection CAMERA_CHOICE = BACK;

    // Setup tracking data
    private VuforiaLocalizer vuforia;
    private ArrayList<VuforiaTrackable> navigationTargets;

    // Instance data: hardwareMap and displacements that indicate phone camera location on robot
    private HardwareMap hwMap;
    private double camForwardDisplacement; // eg: Camera is ___ inches in front of robot center
    private double camLeftDisplacement; // eg: Camera is ___ inches to left of robot center

    // For returning to telemetry
    private boolean targetVisible;
    private String whichTargetVisible;
    private static final String[] targetNames = {"Blue-Rover", "Red-Footprint", "Front-Craters", "Back-Space"};
    private OpenGLMatrix lastLocation = null; // the last location of a nav target we've seen
    private VectorF camPos;
    public Orientation robotRotation;

    public Navigation(HardwareMap hwMap, double camForwardDisplacement, double camLeftDisplacement) {
        // Hardware
        this.hwMap = hwMap;
        this.camForwardDisplacement = camForwardDisplacement;
        this.camLeftDisplacement = camLeftDisplacement;

        // Tracker results
        targetVisible = false; // by default, we assume we don't see a target
        whichTargetVisible = null; // by default, we assume we don't see a target

        // Data for tracker
        navigationTargets = new ArrayList<VuforiaTrackable>();
        camPos = null;
        robotRotation = null;
    }

    public void setupTracker() {
        int cameraMonitorViewId = hwMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hwMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        parameters.vuforiaLicenseKey = Robot.VUFORIA_KEY;
        parameters.cameraName = hwMap.get(WebcamName.class, "Webcam 1");

        parameters.vuforiaLicenseKey = VUFORIA_KEY;

        // Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Load the data sets for the trackable navigation targets
        VuforiaTrackables targetsRoverRuckus = vuforia.loadTrackablesFromAsset("RoverRuckus");
        VuforiaTrackable blueRover = targetsRoverRuckus.get(0);
        blueRover.setName(targetNames[0]);
        VuforiaTrackable redFootprint = targetsRoverRuckus.get(1);
        redFootprint.setName(targetNames[1]);
        VuforiaTrackable frontCraters = targetsRoverRuckus.get(2);
        frontCraters.setName(targetNames[2]);
        VuforiaTrackable backSpace = targetsRoverRuckus.get(3);
        backSpace.setName(targetNames[3]);

        /** Blue Rover Target, Middle of Blue Perimeter Wall **/
        OpenGLMatrix blueRoverLocationOnField = OpenGLMatrix
                .translation(0, mmFTCFieldWidth, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 0));
        blueRover.setLocation(blueRoverLocationOnField);

        /** Red Footprint Target, Middle of Red Perimeter Wall **/
        OpenGLMatrix redFootprintLocationOnField = OpenGLMatrix
                .translation(0, mmFTCFieldWidth, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 0));
        redFootprint.setLocation(redFootprintLocationOnField);

        /** Front Craters Target, Middle of Front Perimeter Wall **/
        OpenGLMatrix frontCratersLocationOnField = OpenGLMatrix
                .translation(0, mmFTCFieldWidth, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 0));
        frontCraters.setLocation(frontCratersLocationOnField);

        /** Back Space Target, Middle of Back Perimeter Wall **/
        OpenGLMatrix backSpaceLocationOnField = OpenGLMatrix
                .translation(0, mmFTCFieldWidth, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 0));
        backSpace.setLocation(backSpaceLocationOnField);

        // Store the navigation targets in the navigationTargets ArrayList
        navigationTargets.addAll(targetsRoverRuckus);

        OpenGLMatrix phoneLocationOnRobot = OpenGLMatrix
                .translation(0, 0, 0)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, YZX, DEGREES,
                        CAMERA_CHOICE == FRONT ? 90 : -90, 90, 0));

        /**  Let all the trackable listeners know where the phone is.  */
        for (VuforiaTrackable trackable : navigationTargets) {
            ((VuforiaTrackableDefaultListener) trackable.getListener()).setPhoneInformation(phoneLocationOnRobot, parameters.cameraDirection);
        }
        targetsRoverRuckus.activate();
    }

    // Look for navigation targets and update targetVisible if found
    public void lookForTargets() {
        for (VuforiaTrackable trackable : navigationTargets) {
            if (((VuforiaTrackableDefaultListener) trackable.getListener()).isVisible()) {
                targetVisible = true;
                whichTargetVisible = trackable.getName();

                OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener)trackable.getListener()).getUpdatedRobotLocation();
                if (robotLocationTransform != null) {
                    lastLocation = robotLocationTransform;
                }
                break; // Exit the for loop if we've found one of the nav targets
            } else {
                targetVisible = false;
            }
        }

        // If we've seen a target and know where the robot is
        if (targetVisible && lastLocation != null) {
            // express position (translation) of robot in inches.
            camPos = lastLocation.getTranslation();

            // Express the rotation of the robot in degrees. Extrinsic = Roll, XYZ = Pitch, DEGREES = Yaw
            // More info about Roll, Pitch, Yaw: https://www.novatel.com/assets/Web-Phase-2-2012/Solution-Pages/AttitudePlane.png
            robotRotation = Orientation.getOrientation(lastLocation, EXTRINSIC, XYZ, DEGREES);
        }
    }

    // The following methods check for the visibility of a specific target by inputting its index in navigationTargets or target name
    public boolean specificTargetVisible(int targetIndex) {
        return ((VuforiaTrackableDefaultListener) navigationTargets.get(targetIndex).getListener()).isVisible();
    }

    // Return true if the robot sees any target at all
    public boolean isTargetVisible() {
        return targetVisible;
    }

    // Return which target the robot sees
    public String visibleTarget() {
        return whichTargetVisible;
    }

    // Returns vector of camera's position in inches
    public Vector getCamPosition() {
        double x = camPos.get(0) / mmPerInch, y = camPos.get(1) / mmPerInch;

        // Craters
        if (specificTargetVisible(2)) {
            return new Vector(y, -x);
        }

        // Footprint
        else if (specificTargetVisible(1)) {
            return new Vector(-x, -y);
        }

        // Space
        else if (specificTargetVisible(3)) {
            return new Vector(-y, x);
        }

        // Rover
        return new Vector(x, y);
    }

    // Converts camera position into robot center's position using camForwardDisplacement
    // THIS WILL WORK IF ROBOT IS IN CENTER OF FRONT SIDE (camLeftDisplacement = 0)
    public Vector getRobotPosition() {
        double yawR = -(robotRotation.thirdAngle) * Math.PI / 180; // draw line from nav target to robot, this is the angle (in radians) the line makes with nearest axis

        double x = getCamPosition().getX(); // Camera Position X
        double y = getCamPosition().getY(); // Camera Position Y
        double disp = camForwardDisplacement; // How far forward from robot center is the camera?

        // Craters
        if (specificTargetVisible(2)) {
            return new Vector(x - disp * Math.cos(yawR), y + disp * Math.sin(yawR));
        }

        // Rover
        else if (specificTargetVisible(0)) {
            return new Vector(x - disp * Math.sin(yawR), y - disp * Math.cos(yawR));
        }

        // Space
        else if (specificTargetVisible(3)) {
            return new Vector(x + disp * Math.cos(yawR), y - disp * Math.sin(yawR));
        }

        // Footprint
        else if (specificTargetVisible(1)) {
            return new Vector(x + disp * Math.sin(yawR), y + disp * Math.cos(yawR));
        }

        // If everything fails, just return original phone position
        return getCamPosition();
    }

    // Returns robot's rotation in degrees, see field map for reference
    public double getRobotRotation() {
        double yaw = robotRotation.thirdAngle;

        // Craters
        if (specificTargetVisible(2)) {
            return (360 + yaw) % 360;
        }

        // Rover
        else if (specificTargetVisible(0)) {
            return yaw + 90;
        }

        // Space
        else if (specificTargetVisible(3)) {
            return yaw + 180;
        }

        // Footprint
        return yaw + 270;
    }
}