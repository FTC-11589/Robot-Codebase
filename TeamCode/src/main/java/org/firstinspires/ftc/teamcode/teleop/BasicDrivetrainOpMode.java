package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "Basic Drivetrain")
public class BasicDrivetrainOpMode extends OpMode {
    DcMotor leftDriveMotor, rightDriveMotor;

    @Override
    public void init() {
        leftDriveMotor = hardwareMap.dcMotor.get("leftDriveMotor");
        rightDriveMotor = hardwareMap.dcMotor.get("rightDriveMotor");

        leftDriveMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void init_loop() {
        super.init_loop();
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void loop() {
        leftDriveMotor.setPower(-gamepad1.left_stick_y + gamepad1.left_stick_x);
        rightDriveMotor.setPower(-gamepad1.left_stick_y - gamepad1.left_stick_x);
    }

    @Override
    public void stop() {
        super.stop();
    }
}
