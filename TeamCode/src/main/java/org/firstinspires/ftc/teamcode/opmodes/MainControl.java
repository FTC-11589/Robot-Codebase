package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.utilities.OperationTools;
import org.firstinspires.ftc.teamcode.utilities.ValueTools;

@TeleOp
public final class MainControl extends OpMode {
    private DcMotorEx backLeftDriveMotor, backRightDriveMotor, baseSlideMotor, extensionSlideMotor;
    private DcMotor leftIntakeMotor, rightIntakeMotor;

    private CRServo leftArmHingeServo, rightArmHingeServo;

    ElapsedTime timer = new ElapsedTime();

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

        OperationTools.apply(device -> device.setDirection(DcMotorSimple.Direction.REVERSE), backLeftDriveMotor, leftIntakeMotor, extensionSlideMotor, leftArmHingeServo);
        OperationTools.apply(device -> device.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER), baseSlideMotor, extensionSlideMotor);
        OperationTools.apply(device -> device.setMode(DcMotor.RunMode.RUN_USING_ENCODER), backRightDriveMotor, backLeftDriveMotor, baseSlideMotor, extensionSlideMotor);
        OperationTools.apply(device -> device.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE), backLeftDriveMotor, backRightDriveMotor, baseSlideMotor, extensionSlideMotor);

        telemetry.setAutoClear(true);
        telemetry.setCaptionValueSeparator("\r\n");
        telemetry.setItemSeparator(telemetry.getCaptionValueSeparator());

        telemetry.addData("Program Status", "Initialized");
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void loop() {
        backLeftDriveMotor.setPower(Range.clip(-gamepad1.left_stick_y + gamepad1.left_stick_x, -1.0, 1.0));
        backRightDriveMotor.setPower(Range.clip(-gamepad1.left_stick_y - gamepad1.left_stick_x, -1.0, 1.0));

        if (gamepad1.y)
            OperationTools.apply(device -> device.setPower(1), leftIntakeMotor, rightIntakeMotor);
        else if (gamepad1.a)
            OperationTools.apply(device -> device.setPower(-1), leftIntakeMotor, rightIntakeMotor);
        else if (gamepad1.x)
            OperationTools.apply(device -> device.setPower(0), leftIntakeMotor, rightIntakeMotor);

        OperationTools.apply(device -> device.setPower(Range.clip(gamepad2.left_trigger - gamepad2.right_trigger, -1.0, 1.0)), baseSlideMotor, extensionSlideMotor);
        OperationTools.apply(device -> device.setPower(Range.clip(-gamepad2.right_stick_y, -1.0, 1.0)), leftArmHingeServo, rightArmHingeServo);

        telemetry.addData("Slide State", "base " + ValueTools.getMotionTypeChar(baseSlideMotor.getPower()) + ": " + baseSlideMotor.getCurrentPosition() + ", extension " + ValueTools.getMotionTypeChar(extensionSlideMotor.getPower()) + ": " + extensionSlideMotor.getCurrentPosition());
        telemetry.addData("Particle Containment Storage Arm Hinge Position/Inclination", "left " + ValueTools.getMotionTypeChar(leftArmHingeServo.getPower()) + ": " + leftArmHingeServo.getPower() + ", right " + ValueTools.getMotionTypeChar(rightArmHingeServo.getPower()) + ": " + rightArmHingeServo.getPower());
        telemetry.addData("Intake System State", "left: " + ValueTools.getMotionTypeChar(leftIntakeMotor.getPower()) + " " + ValueTools.getMotionType(leftIntakeMotor.getPower()) + ", right: " + ValueTools.getMotionTypeChar(rightIntakeMotor.getPower()) + " " + ValueTools.getMotionType(rightIntakeMotor.getPower()));
    }

    @Override
    public void stop() {
        super.stop();
        telemetry.addData("Time Elapsed", timer.toString());
    }
}
