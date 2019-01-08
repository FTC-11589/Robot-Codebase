package org.firstinspires.ftc.teamcode.utilities;

import android.support.annotation.NonNull;

import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.external.Function;

public final class TrackedValue<T>
{
    public TrackedValue(@NonNull Func<T> valueGenerator)
    {
        generator = valueGenerator;
        update();
    }

    public TrackedValue(@NonNull Func<T> valueGenerator, @NonNull Function<T, T> valueCoercer)
    {
        this(valueGenerator);
        coercer = valueCoercer;
    }

    public T previousValue, currentValue;

    Func<T> generator;
    Function<T, T> coercer = value -> value;

    public T update()
    {
        previousValue = currentValue;
        return currentValue = coercer.apply(generator.value());
    }
}
