package com.xu.mortgageme;

/**
 * Created by Omistaja on 11/03/2017.
 */

public class Calculations {

    private int credit_score = 0;
    private double with_interest;
    private int loan;
    private int deposit;

    //private double interest = 0.018;

   public Calculations(int annual_income, int num_of_jobs, int duration, int loan, int deposit) {

        double ann_inc_prop = (double) annual_income / ((double) loan / (double) duration);
        this.loan = loan;
        this.deposit = deposit;

        if (ann_inc_prop > 3) { // 100000/20 >= 500000/3
            this.credit_score += 80;
        } else if (ann_inc_prop > 2) { //70000 >= 200000/10
            this.credit_score += 50;
        } else if (ann_inc_prop > 1.5) { //70000 >= 200000/10
            this.credit_score += 30;
        } else if (ann_inc_prop > 1.2) { //70000 >= 200000/10
            this.credit_score += 10;
        } else {
            this.credit_score += 0;
        }

        if (ann_inc_prop < 2) {
            if (num_of_jobs == 1) {
                this.credit_score += 20;
            } else if (num_of_jobs <= 3) {
                this.credit_score += 10;
            } else {
                this.credit_score += 0;
            }

        }

    }

    public int getScore() {

        return this.credit_score;

    }

    public int getLoan() {

        return this.loan;

    }

    public double getWith_interest() {

        return loan + loan * (getInterest() / 100);

    }

    public double getDeposited() {
        //return (1 - ((double) deposit / (double) loan) ) * 0.1;
        return (loan - deposit) * (4.0 / (double) loan);
    }

    public double getInterest() {

        double inter = 3.5 + (100.0 - (double) credit_score) * 0.07;
        return inter + getDeposited();
    }
}