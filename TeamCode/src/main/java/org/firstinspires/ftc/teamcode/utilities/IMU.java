package org.firstinspires.ftc.teamcode.utilities;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class IMU {

    private BNO055IMU imu;

    public IMU(BNO055IMU imu, boolean calibrate) {
        this.imu = imu;
        if (calibrate)
            calibrate();
    }

    public void calibrate() {
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        imu.initialize(parameters);
    }

    public double[] getAngles() {
        Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        double[] anglesArray = {angles.thirdAngle, angles.secondAngle, angles.firstAngle};

        return anglesArray;
    }

    public double getHeading() {
        return -getAngles()[2];
    }

    public static int convertToDegrees(double radians) {
        return (int) (radians * 180 / Math.PI);
    }

    public static double convertToRadians(int degrees) { return degrees * Math.PI / 180; }
}