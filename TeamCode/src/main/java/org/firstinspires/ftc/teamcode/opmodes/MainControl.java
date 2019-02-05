package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.utilities.TrackedValue;

import static org.firstinspires.ftc.teamcode.utilities.OperationTools.apply;
import static org.firstinspires.ftc.teamcode.utilities.ValueTools.*;

@TeleOp
public final class MainControl extends OpMode {
    private DcMotorEx backLeftDriveMotor, backRightDriveMotor, baseSlideMotor, extensionSlideMotor;
    private DcMotor leftIntakeMotor, rightIntakeMotor;
    private Servo leftArmHingeServo, rightArmHingeServo;
    private ElapsedTime timer = new ElapsedTime();
    private TrackedValue<Boolean> slideControlSwapButton = new TrackedValue<>(() -> gamepad2.b);

    @Override
    public void init() {
        backRightDriveMotor = (DcMotorEx) hardwareMap.dcMotor.get("drive_right");
        backLeftDriveMotor = (DcMotorEx) hardwareMap.dcMotor.get("drive_left");
        baseSlideMotor = (DcMotorEx) hardwareMap.dcMotor.get("slide_base");
        extensionSlideMotor = (DcMotorEx) hardwareMap.dcMotor.get("slide_extension");
        leftIntakeMotor = hardwareMap.dcMotor.get("intake_left");
        rightIntakeMotor = hardwareMap.dcMotor.get("intake_right");
        rightArmHingeServo = hardwareMap.servo.get("arm_hinge_right");
        leftArmHingeServo = hardwareMap.servo.get("arm_hinge_left");

        leftArmHingeServo.setDirection(Servo.Direction.REVERSE);

        apply(device -> device.setDirection(DcMotorSimple.Direction.REVERSE), backLeftDriveMotor, leftIntakeMotor, extensionSlideMotor);
        apply(device -> device.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER), baseSlideMotor, extensionSlideMotor);
        apply(device -> device.setMode(DcMotor.RunMode.RUN_USING_ENCODER), backRightDriveMotor, backLeftDriveMotor, baseSlideMotor, extensionSlideMotor);
        apply(device -> device.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE), backLeftDriveMotor, backRightDriveMotor, baseSlideMotor, extensionSlideMotor);
        apply(device -> device.setPosition(0), leftArmHingeServo, rightArmHingeServo);

        telemetry.setAutoClear(true);
        telemetry.setCaptionValueSeparator("\r\n\t");
        telemetry.setItemSeparator(telemetry.getCaptionValueSeparator());

        telemetry.addData("Program Status", "Initialized");
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void loop() {
        slideControlSwapButton.update();
        final double GEARED_POWER_FACTOR = 80.0 / 120, REFERENCE_TARGET_SLIDE_POWER = Range.clip(gamepad2.left_trigger - gamepad2.right_trigger, -1.0, 1.0);

        backLeftDriveMotor.setPower(Range.clip(-gamepad1.left_stick_y + gamepad1.left_stick_x, -1.0, 1.0));
        backRightDriveMotor.setPower(Range.clip(-gamepad1.left_stick_y - gamepad1.left_stick_x, -1.0, 1.0));

        if (gamepad1.y)
            apply(device -> device.setPower(1), leftIntakeMotor, rightIntakeMotor);
        else if (gamepad1.a)
            apply(device -> device.setPower(-1), leftIntakeMotor, rightIntakeMotor);
        else if (gamepad1.x)
            apply(device -> device.setPower(0), leftIntakeMotor, rightIntakeMotor);

        if (REFERENCE_TARGET_SLIDE_POWER == 0) {
            baseSlideMotor.setPower(-gamepad2.left_stick_y);
            extensionSlideMotor.setPower(-gamepad2.right_stick_y * GEARED_POWER_FACTOR);
        } else {
            baseSlideMotor.setPower(REFERENCE_TARGET_SLIDE_POWER);
            extensionSlideMotor.setPower(REFERENCE_TARGET_SLIDE_POWER * GEARED_POWER_FACTOR);
        }

        apply(device -> device.setPosition(Range.clip(device.getPosition() + 0.01 * (getInt(gamepad2.right_bumper) - getInt(gamepad2.left_bumper)), 0, 0.07)), leftArmHingeServo, rightArmHingeServo);

        telemetry.addData("Slide State", "base " + getMotionTypeChar(baseSlideMotor.getPower()) + ": " + baseSlideMotor.getCurrentPosition() + ", extension " + getMotionTypeChar(extensionSlideMotor.getPower()) + ": " + extensionSlideMotor.getCurrentPosition());
        telemetry.addData("Particle Containment System Arm Hinge Position", "left: " + leftArmHingeServo.getPosition() + ", right: " + rightArmHingeServo.getPosition());
        telemetry.addData("Intake Assembly State", "left: " + getMotionTypeChar(leftIntakeMotor.getPower()) + " " + getMotionType(leftIntakeMotor.getPower()) + ", right: " + getMotionTypeChar(rightIntakeMotor.getPower()) + " " + getMotionType(rightIntakeMotor.getPower()));
    }

    @Override
    public void stop() {
        super.stop();
        telemetry.addData("Time Elapsed", timer.toString());
    }
}
