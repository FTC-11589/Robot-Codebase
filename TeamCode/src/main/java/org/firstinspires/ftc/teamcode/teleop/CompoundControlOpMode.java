package org.firstinspires.ftc.teamcode.teleop;

import android.sax.TextElementListener;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.utilities.TrackedValue;
import org.firstinspires.ftc.teamcode.utilities.ValueTools;

@TeleOp(name = "Compound Systems Control")
public class CompoundControlOpMode extends OpMode
{
    DcMotorEx backLeftDriveMotor, backRightDriveMotor;
    DcMotor leftSlideMotor, rightSlideMotor;

    Servo hookServo;
    ElapsedTime timer = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);

    TrackedValue<Double> slidePower = new TrackedValue<>(() -> ValueTools.Coalesce(rightSlideMotor, DcMotor::getPower), value -> Range.clip(value, -1.0, 1.0));

    @Override
    public void init()
    {
        backLeftDriveMotor = (DcMotorEx) hardwareMap.dcMotor.get("backLeftDriveMotor");
        backRightDriveMotor = (DcMotorEx) hardwareMap.dcMotor.get("backRightDriveMotor");

        leftSlideMotor = hardwareMap.dcMotor.get("leftSlideMotor");
        rightSlideMotor = hardwareMap.dcMotor.get("rightSlideMotor");

        hookServo = hardwareMap.servo.get("hookServo");

        backRightDriveMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightSlideMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        backLeftDriveMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightDriveMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        rightSlideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftSlideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        telemetry.setAutoClear(true);
        telemetry.setCaptionValueSeparator("\r\n");
        telemetry.setItemSeparator(telemetry.getCaptionValueSeparator());

        telemetry.addData("Program Status", "Initialized");
    }

    @Override
    public void init_loop()
    {
        backLeftDriveMotor.setMotorEnable();
        backRightDriveMotor.setMotorEnable();
        slidePower.update();

        telemetry.addData("Drivetrain Status", () -> "left: " + backLeftDriveMotor.getVelocity(AngleUnit.DEGREES) + "°/s\r\nright: " + backRightDriveMotor.getVelocity(AngleUnit.DEGREES) + "°/s");
        telemetry.addData("Program Status", "Loop Initialized");
        super.init_loop();
    }

    @Override
    public void start()
    {
        timer.reset();
        telemetry.addData("Program Status", "Started");
        super.start();
    }

    @Override
    public void loop()
    {
        backLeftDriveMotor.setVelocity((-gamepad1.left_stick_y + gamepad1.left_stick_x) * 360, AngleUnit.DEGREES);
        backRightDriveMotor.setVelocity((-gamepad1.left_stick_y - gamepad1.left_stick_x) * 360, AngleUnit.DEGREES);

        // TODO: Implement system that sets motor power communally based on which gamepad button was pressed instead of individually in each if statement by tracking expected motor power value.
        // TODO: Make ManagedValue class that can be used to wrap the getting and setting of motor power.

        if (gamepad1.y)
        {
            slidePower.currentValue = slidePower.previousValue + 0.01;
            telemetry.addData("Slide Status", "Motor Powers Incremented to " + leftSlideMotor.getPower());
        }
        else if (gamepad1.a)
        {
            leftSlideMotor.setPower(Range.clip(leftSlideMotor.getPower() - 0.01, -1.0, 1.0));
            rightSlideMotor.setPower(Range.clip(rightSlideMotor.getPower() - 0.01, -1.0, 1.0));
            telemetry.addData("Slide Status", "Motor Powers Decremented to " + leftSlideMotor.getPower());
        }
        else if (gamepad1.x)
        {
            leftSlideMotor.setPower(0);
            rightSlideMotor.setPower(0);
            telemetry.addData("Slide Status", "Motors Stopped");
        }

        // TODO: Implement target position setting of slide motors in order to toggle between full extension and full retraction.
    }

    @Override
    public void stop()
    {
        telemetry.addData("Program Status", "Stopped");
        telemetry.addData("Elapsed Time", timer.toString());
        super.stop();
    }
}
