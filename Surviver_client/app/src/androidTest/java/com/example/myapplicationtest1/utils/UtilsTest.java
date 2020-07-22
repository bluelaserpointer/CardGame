package com.example.myapplicationtest1.utils;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class UtilsTest {

    @Test
    public void identifyUser() {
        Assert.assertTrue(Utils.identifyUser("1", "1"));
    }
}