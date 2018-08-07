package by.training.hotel.controller.command.mapping;

import by.training.hotel.controller.command.Command;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class CommandManager {

    private final static CommandManager INSTANCE = new CommandManager();

    private final static String XML_FILE_COMMANDS_PATH = "controller_command.xml";

    private final static String COMMAND = "command";

    private final static String ACTION = "action";

    private final static String CLASS = "class";

    private CommandManager() {

    }

    public Map<String, Command> getAllCommands() throws MappingException {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        ClassLoader loader = getClass().getClassLoader();
        Map<String, Command> actionsAndCommandsMap = new HashMap<>();
        try (InputStream stream = loader.getResourceAsStream(XML_FILE_COMMANDS_PATH)){

            DocumentBuilder docBuilder = builderFactory.newDocumentBuilder();
            Document usedDocWithCommands = docBuilder.parse(new InputSource(stream));
            Element root = usedDocWithCommands.getDocumentElement();
            NodeList commandsList = root.getElementsByTagName(COMMAND);

            for (int i = 0; i < commandsList.getLength(); i++){

                Element parsedCommand = (Element) commandsList.item(i);

                String actionName = getElementTextContent(parsedCommand, ACTION);
                String commandClassName = getElementTextContent(parsedCommand, CLASS);

                Class currentCommandClass = Class.forName(commandClassName);
                Command command = (Command) currentCommandClass.getDeclaredConstructor().newInstance();

                actionsAndCommandsMap.put(actionName, command);
            }
        } catch (SAXException | IOException | ClassNotFoundException e) {
            throw new MappingException(e);
        } catch (ParserConfigurationException e) {
            throw new MappingException(e);
        } catch (NoSuchMethodException e) {
            throw new MappingException(e);
        } catch (IllegalAccessException e) {
            throw new MappingException(e);
        } catch (InstantiationException e) {
            throw new MappingException(e);
        } catch (InvocationTargetException e) {
            throw new MappingException(e);
        }
        return actionsAndCommandsMap;
    }

    private String getElementTextContent(Element element, String elementName){
        NodeList nodeList = element.getElementsByTagName(elementName);
        Node node = nodeList.item(0);
        return node.getTextContent();
    }

    public static CommandManager getInstance() {
        return INSTANCE;
    }
}
