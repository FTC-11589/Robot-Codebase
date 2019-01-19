package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpModeManager;
import com.qualcomm.robotcore.hardware.HardwareDevice;

import org.firstinspires.ftc.robotcore.external.Consumer;
import org.firstinspires.ftc.robotcore.external.Predicate;

import java.util.HashMap;

public final class RobotManager
{
    private static OpModeManager manager;

    public static void setManager(OpModeManager manager) {
        RobotManager.manager = manager;
    }
}
