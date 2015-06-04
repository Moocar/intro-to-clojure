package com.walmartlabs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import sun.org.mozilla.javascript.internal.Function;

import java.awt.*;
import java.math.BigInteger;
import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World");
    }

    public void scalars() {
        int a = 1 + 2;
        a = 1 + 2 + 3 + 4 + 5 + 6;
        String s = "my string";
        s.getClass();
        BigInteger big = new BigInteger("123784598763498576239485762348956");
        double d = 22/7;
    }

    public void functions() {
        "foo".getClass();
        int i = 1 + 2;
        Math.min(4, 5);
    }

    public static int foo(int a, int b) {
        return 10 + a + b;
    }

    public void runFoo() {
        foo(42, 142);
    }

    public void datastructures() {
        Arrays.asList(1, 2, 3);

        Vector v = new Vector();
        v.add(5);
        v.add(6);
        v.add(7);

        HashSet s = new HashSet();
        s.add(1);
        s.add(1);

        HashMap m = new HashMap();
        m.put("foo", 1);
        m.put("bar", 2);
    }

    public void runSomething() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Hi There");
            }
        }).start();
    }

    public int add10(int x) {
        return x + 10;
    }

    public void mapAdd10() {
        List<Integer> a = Arrays.asList(1, 2, 3, 4);
        List<Integer> b = new ArrayList<Integer>();
        for (int i : a) {
            b.add(add10(i));
        }
    }

    public boolean even(int x) {
        return x % 2 == 0;
    }

    public void filterEven() {
        List<Integer> a = Arrays.asList(1, 2, 3, 4);
        List<Integer> b = new ArrayList<Integer>();
        for (int i : a) {
            if (even(i)) {
                b.add(i);
            }
        }
    }
}
