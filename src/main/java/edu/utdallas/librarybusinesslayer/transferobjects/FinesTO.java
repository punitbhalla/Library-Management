package edu.utdallas.librarybusinesslayer.transferobjects;

public class FinesTO {


    private String borrowerName;
    private int cardID;
    private double outStandingFines;

    public String getBorrowerName() {
        return borrowerName;
    }

    public void setBorrowerName(String borrowerName) {
        this.borrowerName = borrowerName;
    }

    public int getCardID() {
        return cardID;
    }

    public void setCardID(int cardID) {
        this.cardID = cardID;
    }

    public double getOutStandingFines() {
        return outStandingFines;
    }

    public void setOutStandingFines(double outStandingFines) {
        this.outStandingFines = outStandingFines;
    }
}
