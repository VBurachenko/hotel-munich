package by.training.hotel.controller.command.mapping;

public enum PageEnum {

    HOME("/WEB-INF/jsp/home.jsp"),
    FREE_ROOM_LIST("/WEB-INF/jsp/freeRoomList.jsp"),
    ERROR_PAGE("/WEB-INF/jsp/error.jsp"),
    LOGIN("/WEB-INF/jsp/login.jsp"),
    REGISTRATION("/WEB-INF/jsp/registration.jsp"),
    SEARCH_ROOM("/WEB-INF/jsp/searchRoom.jsp"),
    CUSTOMER_OFFICE("/WEB-INF/jsp/customer/customerOffice.jsp"),
    ADMIN_OFFICE("/WEB-INF/jsp/admin/adminOffice.jsp"),
    SUCCESS_REGISTRATION("/WEB-INF/jsp/successRegistration.jsp"),
    CART_CONTENT("/WEB-INF/jsp/customer/cartContent.jsp"),
    SELECT_PAYMENT_TYPE("/WEB-INF/jsp/customer/selectPaymentType.jsp"),
    INVOICE_PAYMENT("/WEB-INF/jsp/customer/invoicePayment.jsp"),
    BOOKING_IMPOSSIBLE("/WEB-INF/jsp/bookingImpossible.jsp"),
    SUCCESS_PAYMENT("/WEB-INF/jsp/customer/successInvoicePayment.jsp"),
    CHANGE_BOOKING("/WEB-INF/jsp/customer/changeBooking.jsp"),
    USER_LIST("/WEB-INF/jsp/admin/userList.jsp"),
    BOOKING_LIST("/WEB-INF/jsp/admin/bookingList.jsp"),
    ROOM_LIST("/WEB-INF/jsp/admin/roomList.jsp"),
    ADDING_NEW_ROOM_FORM("/WEB-INF/jsp/admin/addingNewRoomForm.jsp"),
    ROOM_ADDING_SUCCESS("/WEB-INF/jsp/admin/roomAddingSuccess.jsp"),
    BOOKING_PROCESSING_FORM("/WEB-INF/jsp/admin/bookingProcessingForm.jsp"),
    SUCCESS_BOOKING_PROCESSING("/WEB-INF/jsp/admin/successBookingProcessing.jsp");


    private String path;

    PageEnum(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
