package by.training.hotel.entity;

import org.joda.time.LocalDate;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Booking implements Entity{

    private static final long serialVersionUID = -2548492215132320189L;

    private Long bookingId;

    private LocalDate checkInDate;

    private LocalDate checkOutDate;

    private Integer adultCount;

    private Integer childCount;

    private Integer userId;

    private Long invoiceId;

    private BookingStatus bookingStatus;

    private Set<Room> roomsSet = new HashSet<>();

    public Booking() {}

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public Integer getAdultCount() {
        return adultCount;
    }

    public void setAdultCount(Integer adultCount) {
        this.adultCount = adultCount;
    }

    public Integer getChildCount() {
        return childCount;
    }

    public void setChildCount(Integer childCount) {
        this.childCount = childCount;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public Set<Room> getRoomsSet() {
        return roomsSet;
    }

    public void setRoomsSet(Set<Room> roomsSet) {
        this.roomsSet = roomsSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Booking booking = (Booking) o;
        return Objects.equals(getBookingId(), booking.getBookingId()) &&
                Objects.equals(getCheckInDate(), booking.getCheckInDate()) &&
                Objects.equals(getCheckOutDate(), booking.getCheckOutDate()) &&
                Objects.equals(getAdultCount(), booking.getAdultCount()) &&
                Objects.equals(getChildCount(), booking.getChildCount()) &&
                Objects.equals(getUserId(), booking.getUserId()) &&
                Objects.equals(getInvoiceId(), booking.getInvoiceId()) &&
                getBookingStatus() == booking.getBookingStatus() &&
                Objects.equals(getRoomsSet(), booking.getRoomsSet());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getBookingId(), getCheckInDate(), getCheckOutDate(), getAdultCount(), getChildCount(), getUserId(), getInvoiceId(), getBookingStatus(), getRoomsSet());
    }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingId=" + bookingId +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                ", adultCount=" + adultCount +
                ", childCount=" + childCount +
                ", userId=" + userId +
                ", invoiceId=" + invoiceId +
                ", bookingStatus=" + bookingStatus +
                ", roomsSet=" + roomsSet +
                '}';
    }
}
