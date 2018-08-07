package by.training.hotel.listener;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

public class SessionCleanerListener implements HttpSessionAttributeListener {

    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
//        System.out.println("Added: " + event.getName() + " " + event.getValue());
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
//        System.out.println("Removed: " + event.getName() + " " + event.getValue());
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {

    }
}
