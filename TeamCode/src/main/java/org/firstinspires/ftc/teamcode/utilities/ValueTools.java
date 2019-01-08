package org.firstinspires.ftc.teamcode.utilities;

import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.external.Function;

public final class ValueTools
{
    private ValueTools() { }

    public static <T, R> R Coalesce(T value, Function<T, R> operator)
    {
        return operator != null && value != null ? operator.apply(value) : null;
    }
}
