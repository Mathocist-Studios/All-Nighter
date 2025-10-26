package com.mathochist.mazegame.World.Objects;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class Utils {

    public static <T> T instantiate(String className, Class<T> type, Object... constructorArgs) {
        try {
            // return type.cast(Class.forName(className).newInstance());
            Class<?>[] constructorArgTypes = new Class<?>[constructorArgs.length];
            System.out.println(Arrays.toString(constructorArgs));
            for (int i = 0; i < constructorArgs.length; i++) {
                constructorArgTypes[i] = constructorArgs[i].getClass();
            }
            return type.cast(Class.forName(className)
                    .getConstructor(constructorArgTypes)
                    .newInstance(constructorArgs));
        } catch (InstantiationException |
                 IllegalAccessException |
                 ClassNotFoundException |
                 InvocationTargetException |
                 NoSuchMethodException e) {
            throw new IllegalStateException(e);
        }
    }

}
