/**
 * Created by Daniel Bojic
 * Date: 2020-10-08
 * Time: 16:40
 * Project: Inlämningsuppgift 2
 * Copyright: MIT
 */
public class Medlem extends Person{
    private String medlemskap;

    public String getMedlemskap() {
        return medlemskap;
    }

    public void setMedlemskap(String medlemskap) {
        this.medlemskap = medlemskap;
    }

    Medlem(){

    }

    Medlem(String personNr, String förnamn, String efternamn, String medlemskap){
        setPersonNr(personNr);
        setFörnamn(förnamn);
        setEfternamn(efternamn);
        setMedlemskap(medlemskap);
        setFörnamnEfternamn(förnamn, efternamn);
    }
}
