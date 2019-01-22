package org.firstinspires.ftc.teamcode.utilities;

import android.support.annotation.NonNull;

import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.external.Function;

import java.util.ArrayList;
import java.util.List;

public final class TrackedValue<T>
{
    private static List<TrackedValue> instances = new ArrayList<>();

    public TrackedValue(@NonNull Func<T> valueGenerator)
    {
        generator = valueGenerator;
        instances.add(this);
        update();
    }

    public TrackedValue(@NonNull Func<T> valueGenerator, @NonNull Function<T, T> valueCoercer)
    {
        this(valueGenerator);
        coercer = valueCoercer;
    }

    public T previousValue, currentValue;

    private Func<T> generator;
    private Function<T, T> coercer = value -> value;

    public T update()
    {
        previousValue = currentValue;
        return currentValue = coercer.apply(generator.value());
    }

    public static void updateAll()
    {
        for (TrackedValue instance : instances)
            instance.update();
    }
}
