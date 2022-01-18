package model;

/**
 *Class that will handle the contact.
 * @author Lois Vernon Pua
 */
public class Contact {

    public int contactId;
    public String contactEmail;
    public String contactName;

    /** Constructor for building a contact.
     *
     * @param contactId The id of the contact.
     * @param contactName the name of the contact.
     * @param contactEmail The Email of the contact.
     */
    public Contact (int contactId, String contactName, String contactEmail)
    {
        this.contactId = contactId;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }


    /**
     * @return the contactId
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * @return the contactName
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * @return the contactEmail
     */
    public String getContactEmail() {
        return contactEmail;
    }


    /**
     * @param contactId the contactId to set
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * @param contactName the contactName to set
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * @param contactEmail the contactEmail to set
     */
    public void setEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }


    /**
     * @return returns from the usage of combo-box.
     */
    @Override
    public String toString()
    {
        return (contactName);
    }
}
