package by.training.hotel.entity.data_transfer_object;

import by.training.hotel.entity.Entity;
import org.joda.time.LocalDate;

import java.util.Objects;

public class SearchUnitDTO implements Entity {

    private static final long serialVersionUID = -7795690481375817579L;

    private LocalDate from;

    private LocalDate to;

    private Integer adultCount;

    private Integer childCount;

    private Integer comfortLevel;

    public SearchUnitDTO() {
    }

    public LocalDate getFrom() {
        return from;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getTo() {
        return to;
    }

    public void setTo(LocalDate to) {
        this.to = to;
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

    public Integer getComfortLevel() {
        return comfortLevel;
    }

    public void setComfortLevel(Integer comfortLevel) {
        this.comfortLevel = comfortLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SearchUnitDTO that = (SearchUnitDTO) o;
        return Objects.equals(getFrom(), that.getFrom()) &&
                Objects.equals(getTo(), that.getTo()) &&
                Objects.equals(getAdultCount(), that.getAdultCount()) &&
                Objects.equals(getChildCount(), that.getChildCount()) &&
                Objects.equals(getComfortLevel(), that.getComfortLevel());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getFrom(), getTo(), getAdultCount(), getChildCount(), getComfortLevel());
    }

    @Override
    public String toString() {
        return "SearchUnitDTO{" +
                "from=" + from +
                ", to=" + to +
                ", adultCount=" + adultCount +
                ", childCount=" + childCount +
                ", comfortLevel=" + comfortLevel +
                '}';
    }
}
