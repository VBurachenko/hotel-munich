package by.training.hotel.entity;

import java.util.Objects;

public class Room implements Entity, Comparable<Room>{

    private static final long serialVersionUID = 6830391423828569602L;

    private Integer roomNumber;

    private Integer berthCount;

    private Integer comfortLevel;

    private Double pricePerNight;

    private String pictureLink;

    private Boolean availableStatus;

    public Room() { }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Integer getBerthCount() {
        return berthCount;
    }

    public void setBerthCount(Integer berthCount) {
        this.berthCount = berthCount;
    }

    public Integer getComfortLevel() {
        return comfortLevel;
    }

    public void setComfortLevel(Integer comfortLevel) {
        this.comfortLevel = comfortLevel;
    }

    public Double getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(Double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public String getPictureLink() {
        return pictureLink;
    }

    public void setPictureLink(String pictureLink) {
        this.pictureLink = pictureLink;
    }

    public Boolean getAvailableStatus() {
        return availableStatus;
    }

    public void setAvailableStatus(Boolean availableStatus) {
        this.availableStatus = availableStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Room room = (Room) o;
        return Objects.equals(getRoomNumber(), room.getRoomNumber()) &&
                Objects.equals(getBerthCount(), room.getBerthCount()) &&
                Objects.equals(getComfortLevel(), room.getComfortLevel()) &&
                Objects.equals(getPricePerNight(), room.getPricePerNight()) &&
                Objects.equals(getPictureLink(), room.getPictureLink()) &&
                Objects.equals(getAvailableStatus(), room.getAvailableStatus());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getRoomNumber(), getBerthCount(), getComfortLevel(), getPricePerNight(), getPictureLink(), getAvailableStatus());
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomNumber=" + roomNumber +
                ", berthCount=" + berthCount +
                ", comfortLevel=" + comfortLevel +
                ", pricePerNight=" + pricePerNight +
                ", pictureLink='" + pictureLink + '\'' +
                ", availableStatus=" + availableStatus +
                '}';
    }

    @Override
    public int compareTo(Room o) {
        return 0;
    }
}
