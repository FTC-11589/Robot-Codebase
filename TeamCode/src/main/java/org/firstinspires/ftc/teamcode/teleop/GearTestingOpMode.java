package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "Gear Test")
public class GearTestingOpMode extends OpMode {
    DcMotor leftGearMotor, rightGearMotor;

    @Override
    public void init() {
        leftGearMotor = hardwareMap.dcMotor.get("leftGearMotor");
        rightGearMotor = hardwareMap.dcMotor.get("rightGearMotor");

        leftGearMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void loop() {
        if (gamepad1.y)
        {
            leftGearMotor.setPower(leftGearMotor.getPower() + 0.01);
            rightGearMotor.setPower(rightGearMotor.getPower() + 0.01);
        }
        else if (gamepad1.a)
        {
            leftGearMotor.setPower(leftGearMotor.getPower() - 0.01);
            rightGearMotor.setPower(leftGearMotor.getPower() - 0.01);
        }
        else if (gamepad1.x)
        {
            leftGearMotor.setPower(0);
            rightGearMotor.setPower(0);
        }
    }
}
