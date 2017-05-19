package ru.spbau.mit;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;

import ru.spbau.mit.testClasses.*;


public class TestInjector {

    @Test
    public void injectorShouldInitializeClassWithoutDependencies()
            throws Exception {
        Object object = Injector.initialize(
                "ru.spbau.mit.testClasses.ClassWithoutDependencies",
                Collections.<String>emptyList());
        assertTrue(object instanceof ClassWithoutDependencies);
    }

    @Test
    public void injectorShouldInitializeClassWithOneClassDependency()
            throws Exception {
        Object object = Injector.initialize(
                "ru.spbau.mit.testClasses.ClassWithOneClassDependency",
                Collections.singletonList("ru.spbau.mit.testClasses.ClassWithoutDependencies"));
        assertTrue(object instanceof ClassWithOneClassDependency);
        ClassWithOneClassDependency instance = (ClassWithOneClassDependency) object;
        assertTrue(instance.dependency != null);
    }

    @Test
    public void injectorShouldInitializeClassWithOneInterfaceDependency()
            throws Exception {
        Object object = Injector.initialize(
                "ru.spbau.mit.testClasses.ClassWithOneInterfaceDependency",
                Collections.singletonList("ru.spbau.mit.testClasses.InterfaceImpl"));
        assertTrue(object instanceof ClassWithOneInterfaceDependency);
        ClassWithOneInterfaceDependency instance = (ClassWithOneInterfaceDependency) object;
        assertTrue(instance.dependency instanceof InterfaceImpl);
    }

    @Test(expected = InjectionCycleException.class)
    public void checkCycle() throws Exception {
        Injector.initialize(
                "ru.spbau.mit.testClasses.A",
                Arrays.asList("ru.spbau.mit.testClasses.B",
                        "ru.spbau.mit.testClasses.C",
                        "ru.spbau.mit.testClasses.D")
        );
    }

    @Test(expected = ImplementationNotFoundException.class)
    public void checkNotFound() throws Exception {
        Injector.initialize(
                "ru.spbau.mit.testClasses.B",
                Collections.singletonList("ru.spbau.mit.testClasses.C")
        );
    }
}
