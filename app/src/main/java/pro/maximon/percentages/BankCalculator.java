package pro.maximon.percentages;


public class BankCalculator {

    double sumDeposit;
    double sumProfit;


    public BankCalculator() {


    }

    public void calculateAnnualProfit(double deposit, double percentage) {

        double profit = deposit * percentage * 0.01;
        sumDeposit += deposit;
        sumProfit += profit;

    }

    public double getFullProfit() {
        return sumProfit;
    }

    public double getPercent() {
        return sumProfit / sumDeposit * 100;
    }

    public double getFullDeposit() {
        return sumDeposit;
    }
}
