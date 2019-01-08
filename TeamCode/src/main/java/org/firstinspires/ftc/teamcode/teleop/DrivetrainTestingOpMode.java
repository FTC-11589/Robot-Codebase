package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@TeleOp(name = "Advanced Drivetrain")
public class DrivetrainTestingOpMode extends OpMode {
    DcMotorEx backLeftDriveMotor, backRightDriveMotor;

    @Override
    public void init() {
        backLeftDriveMotor = (DcMotorEx) hardwareMap.dcMotor.get("backLeftDriveMotor");
        backRightDriveMotor = (DcMotorEx) hardwareMap.dcMotor.get("backRightDriveMotor");

        backLeftDriveMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightDriveMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public void init_loop() {
        backLeftDriveMotor.setPower(1);
        backRightDriveMotor.setPower(1);
        super.init_loop();
    }

    @Override
    public void loop() {
        backLeftDriveMotor.setVelocity((-gamepad1.left_stick_y + gamepad1.left_stick_x) * 360, AngleUnit.DEGREES);
        backRightDriveMotor.setVelocity((-gamepad1.left_stick_y - gamepad1.left_stick_x) * 360, AngleUnit.DEGREES);
    }

    @Override
    public void stop() {
        backLeftDriveMotor.setPower(0);
        backRightDriveMotor.setPower(0);
        super.stop();
    }
}
