//Patron class where the patron object is described
public class Patron {
    //patrons attributes ID, Name, Address, and FineAmount
    private int patronID;
    private String patronName;
    private String patronAddress;
    private Double patronFineAmount;

    //Patron Constructor to pass attributes, as well as clamp fine amount to 0-250 range
    public Patron(int patronID, String patronName, String patronAddress, double patronFineAmount){
        this.patronID = patronID;
        this.patronName = patronName;
        this.patronAddress = patronAddress;
        if (patronFineAmount < 0){
            this.patronFineAmount = 0.0;
        } else if (patronFineAmount > 250){
            this.patronFineAmount = 250.0;
        } else {
            this.patronFineAmount = patronFineAmount;
        }
    }

    //patron ID getter
    public int getPatronID() {
        return patronID;
    }

    //patron ID setter
    public void setPatronID(int patronID){
        this.patronID = patronID;
    }

    //patron name getter
    public String getPatronName() {
        return patronName;
    }

    //patron name setter
    public void setPatronName(String patronName){
        this.patronName = patronName;
    }

    //patron address getter
    public String getPatronAddress(){
        return patronAddress;
    }

    //patron address setter
    public void setPatronAddress(String patronAddress){
        this.patronAddress = patronAddress;
    }

    //patron fine amount getter
    public double getPatronFineAmount() {
        return patronFineAmount;
    }

    //patron fine amount setter
    public void setPatronFineAmount(double patronFineAmount){
        this.patronFineAmount = patronFineAmount;
    }

    //overridden toString to print patron objects neatly
    @Override
    public String toString() {
        return "ID : " + patronID + " | Name: " + patronName + " | Address: " + patronAddress + " | Fine Amount: $" + String.format("%.2f", patronFineAmount);
    }
}

