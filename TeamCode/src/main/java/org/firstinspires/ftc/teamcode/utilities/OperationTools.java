package org.firstinspires.ftc.teamcode.utilities;

import org.firstinspires.ftc.robotcore.external.Consumer;

public class OperationTools {
    @SafeVarargs
    public static <T> void apply(Consumer<T> operation, T... operands) {
        for (T operand : operands)
            operation.accept(operand);
    }
}
