package model;

/**
 *
 * @author Lois Vernon Pua
 */
public class Division {
    private String divisionName;
    private int countryId;
   private int divisionId;


    /** Constructor for building an division.
     *
     * @param divisionId the id of the division
     * @param divisionName The name of the division.
     * @param countryId the id of the country
    
     */
    public Division (int divisionId, String divisionName, int countryId)
    {
        this.divisionId = divisionId;
        this.divisionName = divisionName;
        this.countryId = countryId;
    }

    /**
     * @return the divisionId
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * @return the divisionName
     */
    public String getName() {
        return divisionName;
    }

    /**
     * @return the countryId
     */
    public int getCountryId() {
        return countryId;
    }



    /**
     * @param divisionId the divisionId to set
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /**
     * @param divisionName the divisionName to set
     */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    /**
     * @param countryId the countryId to set
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }


    /**
     * @return returns division from the usage of combo-box.
     */
    @Override
    public String toString()
    {
        return (divisionName);
    }  
}
