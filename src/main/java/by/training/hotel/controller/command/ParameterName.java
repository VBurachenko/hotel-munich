package by.training.hotel.controller.command;

public final class ParameterName {

    private ParameterName() {
    }

    public static final String SET_LANG = "setLang";
    public static final String LOCAL_LANG = "localLang";
    public static final String CURRENT_PAGE = "currentPage";

    public static final String USER_ID = "user_id";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String PASSWORD_FIRST = "password1";
    public static final String PASSWORD_SECOND = "password2";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String TEL_NUMBER = "telephoneNumber";
    public static final String BIRTHDAY = "birthday";
    public static final String GENDER_MALE = "genderMale";
    public static final String DISCOUNT = "discount";
    public static final String ROLE = "role";

    public static final String CHECK_IN = "check_in";
    public static final String CHECK_OUT = "check_out";
    public static final String PAGE = "page";
    public static final String ADULTS = "adultCount";
    public static final String CHILDREN = "childCount";
    public static final String COMFORT_LEVEL = "comfortLevel";
    public static final String BOOKING_STATUS = "bookingStatus";

    public static final String INVOICE_ID = "invoiceId";

    public static final String SEARCH_UNIT = "searchUnit";
    public static final String ROOMS_FOR_DISPLAY = "roomsForDisplay";

    public static final String REGISTRATION_ERROR = "registerError";
    public static final String LOGIN_ERROR = "loginError";
    public static final String CHANGE_BOOKING_ERROR = "changeBookingError";

    public static final int WRONG_PASSWORD_OR_EMAIL_CODE = 1;
    public static final int REGISTERED_ALREADY_OR_ERROR_CODE = 2;
    public static final int CHANGE_OF_BOOKING_MESSAGE_CODE = 3;
    public static final int CHANGE_USER_BLOCK_ERROR_CODE = 4;
    public static final int ACCOUNT_BLOCKED_CODE = 5;

    public static final String ROOM_NUMBER = "roomNumber";
    public static final String ORDER_IN_CART = "orderInCart";
    public static final String SELECTED_ROOMS = "selectedRooms";

    public static final String USERS_BOOKING_SET = "usersBookingSet";
    public static final String USERS_INVOICE_SET = "usersInvoiceSet";

    public static final String INVOICE_FOR_BOOKING = "invoiceForBooking";
    public static final String BOOKING_IN_PROCESS = "bookingInProcess";

    public static final String PAYMENT = "payment";

    public static final String TOTAL_AMOUNT = "totalAmount";

    public static final String BOOKING_ID = "bookingId";
    public static final String BOOKING_FOR_CHANGE = "bookingForChange";

    public static final String USERS_FOR_VIEW = "usersForView";
    public static final String BLOCK_DOWN = "blockDown";
    public static final String BLOCKED_USER_ID = "blockedUserId";
    public static final String USER_OPERATION_MESSAGE = "userOperationMessage";

    public static final String BOOKINGS_FOR_VIEW = "bookingsForView";

    public static final String CANCEL_BOOKING_ID = "cancelBookingId";
    public static final String CANCEL_INVOICE_ID = "cancelInvoiceId";

    public static final String JUST_ADDED_BOOKING_ID = "justAddedBookingId";

    public static final String INVOICE_ID_FOR_PAYMENT = "invoiceIdForPayment";
    public static final String INVOICE_FOR_PAYMENT = "invoiceForPayment";

    public static final String ROOMS_FOR_VIEW = "roomsForView";
    public static final String INVOICES_FOR_VIEW = "invoicesForView";

    public static final String DISABLED_ROOM_NUMBER = "disabledRoomNumber";
    public static final String ROOM_OPERATION_MESSAGE = "roomOperationMessage";
    public static final String BOOKING_OPERATION_MESSAGE = "bookingOperationMessage";

    public static final int CHANGE_ROOM_STATUS_ERROR_CODE = 6;

    public static final String SEARCH_USER_ARTIFACT = "searchUserArtifact";

    public static final int NO_SUCH_USER_MESSAGE_CODE = 7;
    public static final int NO_SUCH_ROOM_MESSAGE_CODE = 8;
    public static final int ROOM_NOT_ADDED_MESSAGE_CODE = 9;
    public static final int NO_SUCH_BOOKING_MESSAGE_CODE = 10;
    public static final int IMPOSSIBLE_PROCESS_BOOKING_MESSAGE_CODE = 11;

    public static final String BERTH_COUNT = "berthCount";
    public static final String PRICE_PER_NIGHT = "pricePerNight";
    public static final String PICTURE_LINK = "pictureLink";

    public static final String IS_PAYED = "isPayed";

    public static final int BOOKING_NOT_PROCESSED_CODE = 12;

    public static final String INVOICE_OPERATION_MESSAGE = "invoiceOperationMessage";
    public static final int NO_SUCH_INVOICE_MESSAGE_CODE = 13;

    public static final int ADMIN_WAS_NOT_REGISTERED_MESSAGE_CODE = 14;
}
