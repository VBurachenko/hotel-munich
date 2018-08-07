package by.training.hotel.entity;

import org.joda.time.LocalDate;

import java.util.Objects;

public class Invoice implements Entity{

    private static final long serialVersionUID = -1867487374113051198L;

    private Long invoiceId;

    private Integer userId;

    private LocalDate invoiceDate;

    private Integer nightsCount;

    private Double totalPayment;

    private InvoiceStatus invoiceStatus;

    private Boolean isPayed;

    public Invoice() { }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Integer getNightsCount() {
        return nightsCount;
    }

    public void setNightsCount(Integer nightsCount) {
        this.nightsCount = nightsCount;
    }

    public Double getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(Double totalPayment) {
        this.totalPayment = totalPayment;
    }

    public InvoiceStatus getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(InvoiceStatus invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public Boolean getIsPayed() {
        return isPayed;
    }

    public void setIsPayed(Boolean isPayed) {
        this.isPayed = isPayed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Invoice invoice = (Invoice) o;
        return Objects.equals(getInvoiceId(), invoice.getInvoiceId()) &&
                Objects.equals(getUserId(), invoice.getUserId()) &&
                Objects.equals(getInvoiceDate(), invoice.getInvoiceDate()) &&
                Objects.equals(getNightsCount(), invoice.getNightsCount()) &&
                Objects.equals(getTotalPayment(), invoice.getTotalPayment()) &&
                getInvoiceStatus() == invoice.getInvoiceStatus() &&
                Objects.equals(isPayed, invoice.isPayed);
    }

    @Override
    public int hashCode() {

        return Objects.hash(getInvoiceId(), getUserId(), getInvoiceDate(), getNightsCount(), getTotalPayment(), getInvoiceStatus(), isPayed);
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "invoiceId=" + invoiceId +
                ", userId=" + userId +
                ", invoiceDate=" + invoiceDate +
                ", nightsCount=" + nightsCount +
                ", totalPayment=" + totalPayment +
                ", invoiceStatus=" + invoiceStatus +
                ", isPayed=" + isPayed +
                '}';
    }
}
