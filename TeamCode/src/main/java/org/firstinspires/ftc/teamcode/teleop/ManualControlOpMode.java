package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "Manual Control")
public class ManualControlOpMode extends OpMode
{
    DcMotorEx backLeftDriveMotor, backRightDriveMotor, elevatorMotor;
    DcMotor leftCollectorFeederMotor, rightCollectorFeederMotor;
    Servo armHingeRightServo, armHingeLeftServo;
    ElapsedTime timer = new ElapsedTime();

    @Override
    public void init()
    {
        backRightDriveMotor = (DcMotorEx) hardwareMap.dcMotor.get("drive_right");
        backLeftDriveMotor = (DcMotorEx) hardwareMap.dcMotor.get("drive_left");
        elevatorMotor = (DcMotorEx) hardwareMap.dcMotor.get("elevator");
        leftCollectorFeederMotor = hardwareMap.dcMotor.get("feeder_left");
        rightCollectorFeederMotor = hardwareMap.dcMotor.get("feeder_right");
        armHingeRightServo = hardwareMap.servo.get("arm_hinge_right");
        armHingeLeftServo = hardwareMap.servo.get("arm_hinge_left");


        backLeftDriveMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        leftCollectorFeederMotor.setDirection(DcMotorSimple.Direction.REVERSE);


        backRightDriveMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftDriveMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        elevatorMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        elevatorMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        backRightDriveMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftDriveMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        elevatorMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        telemetry.setAutoClear(true);
        telemetry.setCaptionValueSeparator("\r\n");
        telemetry.setItemSeparator(telemetry.getCaptionValueSeparator());

        telemetry.addData("Program Status", "Initialized");
    }

    @Override
    public void init_loop()
    {
        super.init_loop();
        timer.reset();
    }

    @Override
    public void start()
    {
        super.start();
        backRightDriveMotor.setMotorEnable();
        backLeftDriveMotor.setMotorEnable();
        elevatorMotor.setMotorEnable();

        armHingeLeftServo.setPosition(0);
        armHingeRightServo.setPosition(0);

    }

    @Override
    public void loop()
    {
        backLeftDriveMotor.setPower(Range.clip(-gamepad1.left_stick_y + gamepad1.left_stick_x, -1.0, 1.0));
        backRightDriveMotor.setPower(Range.clip(-gamepad1.left_stick_y - gamepad1.left_stick_x, -1.0, 1.0));

        if (gamepad1.y)
        {
            leftCollectorFeederMotor.setPower(1);
            rightCollectorFeederMotor.setPower(1);
        }
        else if (gamepad1.a)
        {
            leftCollectorFeederMotor.setPower(-1);
            rightCollectorFeederMotor.setPower(-1);
        }
        else if (gamepad1.x)
        {
            leftCollectorFeederMotor.setPower(0);
            rightCollectorFeederMotor.setPower(0);
        }

        elevatorMotor.setPower(Range.clip(gamepad2.left_trigger - gamepad2.right_trigger, -1.0, 1.0));

        armHingeRightServo.setPosition(Range.clip(armHingeRightServo.getPosition() - gamepad2.right_stick_y * 0.01, -1.0, 1.0));
        armHingeLeftServo.setPosition(armHingeRightServo.getPosition());

        telemetry.addData("Slide Position", elevatorMotor.getCurrentPosition());
        telemetry.addData("Arm Hinge Right Position", armHingeRightServo.getPosition());
        telemetry.addData("Arm Hinge Left Position", armHingeLeftServo.getPosition());
        telemetry.addData("Feeder State", "Spinning " + (leftCollectorFeederMotor.getPower() == 0 ? "Stopped" : leftCollectorFeederMotor.getPower() > 0 ? "Forward" : "Backward"));
    }

    @Override
    public void stop()
    {
        super.stop();
        telemetry.addData("Elapsed Time", timer.toString());
    }
}
