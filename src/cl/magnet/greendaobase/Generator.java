package cl.magnet.greendaobase;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;

/**
 * Generates entities and DAOs for an example project.
 * 
 * Run it as a Java application (not Android).
 * 
 * @author Ignacio Parada
 */
public class Generator {
	
    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1, "cl.magnet.EXAMPLE"); //THIS SHOULD BE CHANGED

        addReceivedMessage(schema);
        addContact(schema);
        addMessageToSend(schema);
        addMessageContacts(schema);
        addCustomerOrder(schema);

        new DaoGenerator().generateAll(schema, "../EXAMPLE/src"); //THIS SHOULD BE CHANGED
    }

    private static void addReceivedMessage(Schema schema) {
        Entity receivedMessage = schema.addEntity("ReceivedMessage");
        receivedMessage.addIdProperty();
        receivedMessage.addStringProperty("username").notNull();
        receivedMessage.addStringProperty("readableName");
        receivedMessage.addStringProperty("pictureLocation").notNull();
        receivedMessage.addLongProperty("serverId").notNull();
    }
    
    private static void addContact(Schema schema) {
        Entity contact = schema.addEntity("Contact");
        contact.addIdProperty();
        contact.addStringProperty("phoneNumber").notNull().unique();
        contact.addStringProperty("name").notNull();
    }
    
    private static void addMessageToSend(Schema schema) {
        Entity messageToSend = schema.addEntity("MessageToSend");
        messageToSend.addIdProperty();
        messageToSend.addStringProperty("pictureLocation").notNull();
    }
    
    private static void addMessageContacts(Schema schema){
    	Entity messageContacts = schema.addEntity("MessageContacts");
    	messageContacts.addIdProperty();
    	messageContacts.addStringProperty("contactId");
    	messageContacts.addLongProperty("messageToSendId");
    }

	private static void addCustomerOrder(Schema schema) {
        Entity customer = schema.addEntity("Customer");
        customer.addIdProperty();
        customer.addStringProperty("name").notNull();

        Entity order = schema.addEntity("Order");
        order.setTableName("ORDERS"); // "ORDER" is a reserved keyword
        order.addIdProperty();
        Property orderDate = order.addDateProperty("date").getProperty();
        Property customerId = order.addLongProperty("customerId").notNull().getProperty();
        order.addToOne(customer, customerId);

        ToMany customerToOrders = customer.addToMany(order, customerId);
        customerToOrders.setName("orders");
        customerToOrders.orderAsc(orderDate);
    }

}