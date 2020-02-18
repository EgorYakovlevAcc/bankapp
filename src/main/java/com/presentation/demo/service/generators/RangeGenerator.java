package com.presentation.demo.service.generators;
import org.springframework.stereotype.Service;

@Service
public class RangeGenerator {
    public static String nextInt(int a, int b) {
        return "" + ((int)(Math.random() * b) + a);
    }
}
