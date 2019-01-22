package org.firstinspires.ftc.teamcode.opmodes;

import android.transition.Slide;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.utilities.TrackedValue;
import org.firstinspires.ftc.teamcode.utilities.ValueTools;

import static org.firstinspires.ftc.teamcode.utilities.OperationTools.apply;

@TeleOp
public final class MainControl extends OpMode {
    private DcMotorEx backLeftDriveMotor, backRightDriveMotor, baseSlideMotor, extensionSlideMotor;
    private DcMotor leftIntakeMotor, rightIntakeMotor;
    private CRServo leftArmHingeServo, rightArmHingeServo;
    private ElapsedTime timer = new ElapsedTime();
    private SlideSelectionState slideSelection = SlideSelectionState.BASE;
    private TrackedValue<Boolean> slideControlSwapButton = new TrackedValue<>(() -> gamepad2.b);

    enum SlideSelectionState {BASE, EXTENSION, BOTH}

    @Override
    public void init() {
        backRightDriveMotor = (DcMotorEx) hardwareMap.dcMotor.get("drive_right");
        backLeftDriveMotor = (DcMotorEx) hardwareMap.dcMotor.get("drive_left");
        baseSlideMotor = (DcMotorEx) hardwareMap.dcMotor.get("slide_base");
        extensionSlideMotor = (DcMotorEx) hardwareMap.dcMotor.get("slide_extension");
        leftIntakeMotor = hardwareMap.dcMotor.get("intake_left");
        rightIntakeMotor = hardwareMap.dcMotor.get("intake_right");
        rightArmHingeServo = hardwareMap.crservo.get("arm_hinge_right");
        leftArmHingeServo = hardwareMap.crservo.get("arm_hinge_left");

        apply(device -> device.setDirection(DcMotorSimple.Direction.REVERSE), backLeftDriveMotor, leftIntakeMotor, extensionSlideMotor, leftArmHingeServo);
        apply(device -> device.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER), baseSlideMotor, extensionSlideMotor);
        apply(device -> device.setMode(DcMotor.RunMode.RUN_USING_ENCODER), backRightDriveMotor, backLeftDriveMotor, baseSlideMotor, extensionSlideMotor);
        apply(device -> device.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE), backLeftDriveMotor, backRightDriveMotor, baseSlideMotor, extensionSlideMotor);

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

        if (slideControlSwapButton.currentValue && slideControlSwapButton.previousValue)
            switch (slideSelection)
            {
                case BASE:
                    slideSelection = SlideSelectionState.EXTENSION;
                    break;
                case EXTENSION:
                    slideSelection = SlideSelectionState.BOTH;
                    break;
                case BOTH:
                    slideSelection = SlideSelectionState.BASE;
                    break;
            }

        switch (slideSelection)
        {
            case BASE:
                baseSlideMotor.setPower(REFERENCE_TARGET_SLIDE_POWER);
                break;
            case EXTENSION:
                extensionSlideMotor.setPower(REFERENCE_TARGET_SLIDE_POWER * GEARED_POWER_FACTOR);
                break;
            case BOTH:
                baseSlideMotor.setPower(REFERENCE_TARGET_SLIDE_POWER);
                extensionSlideMotor.setPower(REFERENCE_TARGET_SLIDE_POWER * GEARED_POWER_FACTOR);
                break;
        }
        apply(device -> device.setPower(Range.clip(-gamepad2.right_stick_y, -1.0, 1.0)), leftArmHingeServo, rightArmHingeServo);

        telemetry.addData("Slide State [" + slideSelection + "]", "base " + ValueTools.getMotionTypeChar(baseSlideMotor.getPower()) + ": " + baseSlideMotor.getCurrentPosition() + ", extension "+ ValueTools.getMotionTypeChar(extensionSlideMotor.getPower()) + ": " + extensionSlideMotor.getCurrentPosition());
        telemetry.addData("Slide State", "base: " + baseSlideMotor.getCurrentPosition());
        telemetry.addData("Particle Containment Storage Arm Hinge Position/Inclination", "left " + ValueTools.getMotionTypeChar(leftArmHingeServo.getPower()) + ": " + leftArmHingeServo.getPower() + ", right " + ValueTools.getMotionTypeChar(rightArmHingeServo.getPower()) + ": " + rightArmHingeServo.getPower());
        telemetry.addData("Intake System State", "left: " + ValueTools.getMotionTypeChar(leftIntakeMotor.getPower()) + " " + ValueTools.getMotionType(leftIntakeMotor.getPower()) + ", right: " + ValueTools.getMotionTypeChar(rightIntakeMotor.getPower()) + " " + ValueTools.getMotionType(rightIntakeMotor.getPower()));
    }

    @Override
    public void stop() {
        super.stop();
        telemetry.addData("Time Elapsed", timer.toString());
    }
}
