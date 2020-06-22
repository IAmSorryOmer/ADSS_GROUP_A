package Entities;

import java.time.LocalDate;

public class Discount {
    private String id;
    private double percentage;
    private LocalDate fromDate;
    private LocalDate toDate;

    public Discount(String id, double percentage, LocalDate fromDate, LocalDate toDate) {
        this.id = id;
        this.percentage = percentage;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public String toString() {
        return "percentage id: " + id + ", percentage of discount: " + percentage + ", from date: " + fromDate.toString() + ", to date: " + toDate.toString();
    }
}
