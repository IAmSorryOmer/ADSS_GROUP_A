package com.company;

import com.company.LogicLayer.Discount;

import java.util.LinkedList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Discount discount1 = new Discount(80, null, null);
        Discount discount2 = new Discount(95.5, null, null);
        Discount discount3 = new Discount(90, null, null);
        Discount discount4 = new Discount(70, null, null);
        List<Discount> discounts = new LinkedList<>();
        discounts.add(discount1);
        discounts.add(discount2);
        discounts.add(discount3);
        discounts.add(discount4);
        double max = discounts.stream()
                .reduce(0.0, (accumulatedDouble, discount) -> Math.max(accumulatedDouble, discount.getPercentage()), Math::max);
        System.out.println(max);
    }
}
