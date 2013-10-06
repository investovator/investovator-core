package org.investovator.core.excelimporter;

/**
 * Created with IntelliJ IDEA.
 * User: hasala
 * Date: 8/14/13
 * Time: 1:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class HistoryData {

    private double highPrice;
    private double lowPrice;
    private double closingPrice;
    private double noOfTrades;
    private double noOfShares;
    private double turnover;
    private String date;

    public HistoryData(){

    }

    public double getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(double highPrice) {
        this.highPrice = highPrice;
    }

    public double getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(double lowPrice) {
        this.lowPrice = lowPrice;
    }

    public double getClosingPrice() {
        return closingPrice;
    }

    public void setClosingPrice(double closingPrice) {
        this.closingPrice = closingPrice;
    }

    public double getNoOfTrades() {
        return noOfTrades;
    }

    public void setNoOfTrades(double noOfTrades) {
        this.noOfTrades = noOfTrades;
    }

    public double getNoOfShares() {
        return noOfShares;
    }

    public void setNoOfShares(double noOfShares) {
        this.noOfShares = noOfShares;
    }

    public double getTurnover() {
        return turnover;
    }

    public void setTurnover(double turnover) {
        this.turnover = turnover;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}