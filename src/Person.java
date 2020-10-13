/**
 * Created by Daniel Bojic
 * Date: 2020-10-08
 * Time: 16:38
 * Project: Inlämningsuppgift 2
 * Copyright: MIT
 */
public class Person {
    private String personNr;
    private String förnamn;
    private String efternamn;
    private String entireName;

    public String getEntireName() {
        return entireName;
    }

    public void setEntireName(String firstname, String lastname) {
        this.entireName = firstname + " " + lastname;
    }

    public String getPersonNr() {
        return personNr;
    }

    public void setPersonNr(String personNr) {
        this.personNr = personNr;
    }

    public String getFörnamn() {
        return förnamn;
    }

    public void setFörnamn(String förnamn) {
        this.förnamn = förnamn;
    }

    public String getEfternamn() {
        return efternamn;
    }

    public void setEfternamn(String efternamn) {
        this.efternamn = efternamn;
    }


    @Override
    public String toString(){
        return getEntireName();
    }
}
