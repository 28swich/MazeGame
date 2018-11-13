package com.swich.maze.utils;

public class Time {

    public static final long SECOND = 1000000000L;

    public static long get() {
        return System.nanoTime();
    }

}
