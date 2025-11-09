package com.mathochist.mazegame.World.Objects;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Utils {

    /**
     * Instantiate an object of the given class name with the provided constructor arguments.
     * Ensures the instantiated object is of the expected type.
     * <br>
     * <b>Note:</b> The constructor argument types are inferred from the runtime classes of the provided arguments.
     * This means that you should not use primitive types (e.g., int, boolean) directly in the constructor arguments,
     * as they will be boxed into their corresponding wrapper classes (e.g., Integer, Boolean).
     * <br>
     * <b>Defo not a safe function and is susceptible to injection code but don't tell anyone shhhhhh.</b>
     * <br>
     * <br>
     * <b>Example Usage:</b>
     * <pre>
     *     MyClass obj = Utils.instantiate("com.example.MyClass", MyClass.class, "arg1", 42, true);
     * </pre>
     * This will attempt to find a constructor in `MyClass` that matches the argument types
     * `(String, Integer, Boolean)`.
     * <br>
     * Further examples can be found in GameWorld where this method is used to instantiate world objects given by JSON data.
     * <br>
     * <br>
     * <b>CREDITS: <a href="https://stackoverflow.com/questions/9886266/is-there-a-way-to-instantiate-a-class-by-name-in-java">StackOverflow</a></b>
     *
     * @param className       The fully qualified name of the class to instantiate.
     * @param type            The expected type of the instantiated object.
     * @param constructorArgs The arguments to pass to the constructor.
     * @param <T>             The type parameter.
     * @return An instance of the specified class cast to the expected type.
     * @throws IllegalStateException If instantiation fails due to reflection issues.
     */
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


    /**
     * Returns a new array containing the elements of the input array that are not present in the subArray.
     *
     * @param array    The original array.
     * @param subArray The array of elements to exclude from the original array.
     * @param <T>      The type of the array elements.
     * @return A new array containing the complement of the subArray in the original array.
     * @throws IllegalArgumentException if the input array is null.
     */
    public static <T> T[] complementOfSubArray(T[] array, T[] subArray) {
        if (array == null) throw new IllegalArgumentException("complementOfSubArray: Array must not be null");
        if (subArray == null || subArray.length == 0) return Arrays.copyOf(array, array.length);

        Set<T> exclude = new HashSet<>(Arrays.asList(subArray));
        List<T> filtered = Arrays.stream(array)
            .filter(e -> !exclude.contains(e))
            .toList();
        return filtered.toArray(Arrays.copyOf(array, 0));
    }


}
