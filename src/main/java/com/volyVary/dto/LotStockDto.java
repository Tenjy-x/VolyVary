package com.volyVary.dto;

public class LotStockDto {
    private int id;
    private int idLot;
    private double quantiteReel;
    public LotStockDto(int idLot,double quantiteReel) {
        this.idLot = idLot;
        this.quantiteReel = quantiteReel;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getIdLot() {
        return idLot;
    }
    public void setIdLot(int idLot) {
        this.idLot = idLot;
    }
    public double getQuantiteReel() {
        return quantiteReel;
    }
    public void setQuantiteReel(double quantiteReel) {
        this.quantiteReel = quantiteReel;
    }
   
}
