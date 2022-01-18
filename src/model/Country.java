package model;

/** The class that will handle the country.
 *
 * @author Lois Vernon Pua
 */
public class Country {
    private final String countryName;
      private final int countryId;

    /** Constructor for building a country.
     *
    
     * @param countryName the name of the country.
     * @param countryId The id of the country.
     */
    public Country(int countryId, String countryName)
    {
        this.countryId = countryId;
        this.countryName = countryName;
    }


    /**
     * @return the countryId
     */
    public int getCountryId()
    {
        return countryId;
    }


    /**
     * @return the countryName
     */
    public String getCountryName()
    {
        return countryName;
    }


    /**
     * @return returns a country name from the usage of combo-box.
     */
    @Override
    public String toString()
    {
        return (countryName);
    }
    
}
