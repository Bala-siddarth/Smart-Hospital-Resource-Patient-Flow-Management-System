package entities;

import enums.WardType;

public class Ward {

    private String wardId;
    private WardType wardType;
    private int totalBeds;
    private int occupiedBeds;

    public Ward() {
    }

    public Ward(String wardId, WardType wardType, int totalBeds) {
        this.wardId = wardId;
        this.wardType = wardType;
        this.totalBeds = totalBeds;
        this.occupiedBeds = 0;
    }

    public String getWardId() {
        return wardId;
    }

    public void setWardId(String wardId) {
        this.wardId = wardId;
    }

    public WardType getWardType() {
        return wardType;
    }

    public void setWardType(WardType wardType) {
        this.wardType = wardType;
    }

    public int getTotalBeds() {
        return totalBeds;
    }

    public void setTotalBeds(int totalBeds) {
        this.totalBeds = totalBeds;
    }

    public int getOccupiedBeds() {
        return occupiedBeds;
    }

    public void setOccupiedBeds(int occupiedBeds) {
        this.occupiedBeds = occupiedBeds;
    }

    public boolean hasAvailableBed() {
        return occupiedBeds < totalBeds;
    }

    public void occupyBed() {
        if (hasAvailableBed()) {
            occupiedBeds++;
        }
    }

    public void releaseBed() {
        if (occupiedBeds > 0) {
            occupiedBeds--;
        }
    }

    public int getAvailableBeds() {
        return totalBeds - occupiedBeds;
    }

    @Override
    public String toString() {
        return "\nWard ID         : " + wardId +
                "\nWard Type       : " + wardType +
                "\nTotal Beds      : " + totalBeds +
                "\nOccupied Beds   : " + occupiedBeds +
                "\nAvailable Beds  : " + getAvailableBeds();
    }
}