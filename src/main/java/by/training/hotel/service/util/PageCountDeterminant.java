package by.training.hotel.service.util;

public class PageCountDeterminant {

    public static int definePageCount(int itemsTotalCount, int itemsPerPage){
        return (int) Math.ceil(itemsTotalCount * 1.0 / itemsPerPage);
    }
}
