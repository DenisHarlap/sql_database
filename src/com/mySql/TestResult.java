package com.mySql;

/**
 * Created by Denis on 23.02.15.
 */
public class TestResult {

    private int testId;
    private String date;
    private int mark;

    public TestResult(){}

    public TestResult(int testId, String date, int mark) {
        this.testId = testId;
        this.date = date;
        this.mark = mark;
    }

    public int getTestId() {
        return testId;
    }

    public String getDate() {
        return date;
    }

    public int getMark() {
        return mark;
    }
}
