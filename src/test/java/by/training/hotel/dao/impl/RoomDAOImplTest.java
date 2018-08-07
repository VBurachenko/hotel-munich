package by.training.hotel.dao.impl;

import by.training.hotel.dao.RoomDAO;
import by.training.hotel.dao.exception.DAOException;
import by.training.hotel.entity.Room;
import by.training.hotel.entity.data_transfer_object.SearchUnitDTO;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(DataProviderRunner.class)
public class RoomDAOImplTest extends BaseDAOTest{

    private final RoomDAO<Integer, Room> roomDAO = factory.getRoomDao();

    private Room testRoom;

    @Before
    public void initTestingObject(){
        testRoom = new Room();
    }

    @After
    public void cleanTestingObject(){
        testRoom = null;
    }

    @Test
    public void add() {
    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void getById() {
    }

    @Test
    public void getElementsList() {
    }

    @Test
    public void getElementsList1() {
    }

    @DataProvider
    public static Object[][] provideRooms(){
        Room room1002 = new Room();
        room1002.setRoomNumber(1002);
        room1002.setBerthCount(2);
        room1002.setComfortLevel(3);
        room1002.setPricePerNight(52.5);
        room1002.setPictureLink("fej7dwed");
        room1002.setAvailableStatus(true);

        Room room2001 = new Room();
        room2001.setRoomNumber(2001);
        room2001.setBerthCount(3);
        room2001.setComfortLevel(4);
        room2001.setPricePerNight(56.4);
        room2001.setPictureLink("fer4rdwfcr");
        room2001.setAvailableStatus(true);

        Room room2006 = new Room();
        room2006.setRoomNumber(2006);
        room2006.setBerthCount(3);
        room2006.setComfortLevel(5);
        room2006.setPricePerNight(95.8);
        room2006.setPictureLink("cr77gerefwe");
        room2006.setAvailableStatus(true);

        Room room2005 = new Room();
        room2005.setRoomNumber(2005);
        room2005.setBerthCount(5);
        room2005.setComfortLevel(4);
        room2005.setPricePerNight(55.9);
        room2005.setPictureLink("vf34rerf");
        room2005.setAvailableStatus(true);


        return new Object[][]{{room1002, false}, {room2005, true}, {room2006, false}, {room2001, false}};
    }

    @Test
    @UseDataProvider("provideRooms")
    public void findFreeRooms(Room room, boolean expectedThatRoomIsFree) throws DAOException {

        SearchUnitDTO searchUnit = new SearchUnitDTO();
        searchUnit.setFrom(new LocalDate(2018, 7, 4));
        searchUnit.setTo(new LocalDate(2018, 7, 21));
        searchUnit.setAdultCount(1);
        searchUnit.setChildCount(0);
        searchUnit.setComfortLevel(3);

        boolean roomIsFree = false;
        List<Room> freeRoomsList = roomDAO.findFreeRooms(searchUnit, 0,50);
        for (Room roomInList : freeRoomsList){
            System.out.println(roomInList);
        }
        if (freeRoomsList.contains(room)){
            roomIsFree = true;
        }

        assertEquals(expectedThatRoomIsFree, roomIsFree);

    }

    @Test
    public void getCountOfFreeRooms() {

    }

    @Test
    public void getTotalCountOfRooms() {
    }

}