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
    private String förnamnEfternamn;

    public String getFörnamnEfternamn() {
        return förnamnEfternamn;
    }

    public void setFörnamnEfternamn(String firstname, String lastname) {
        this.förnamnEfternamn = firstname + " " + lastname;
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
    public String toString() {
        return getFörnamnEfternamn();
    }
}
