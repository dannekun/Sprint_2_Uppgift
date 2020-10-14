import org.junit.jupiter.api.Test;

import java.io.*;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Daniel Bojic
 * Date: 2020-10-09
 * Time: 18:51
 * Project: Inlämningsuppgift 2
 * Copyright: MIT
 */
class BestGymEverTest {


    BestGymEver g = new BestGymEver();
    Medlem m = new Medlem("9405264814", "Daniel", "Bojic",
            "2019-10-03");


    @Test
    public final void compareDateTest() {
        String message = "";

        LocalDate compareInDate = LocalDate.of(2019, 05, 05);
        LocalDate todayInDate = LocalDate.of(2020, 10, 03);
        todayInDate = todayInDate.minusYears(1);

        boolean isItBefore = todayInDate.isBefore(compareInDate);

        assertTrue(isItBefore == false);
    }

    @Test
    public final void generateListFromFileTest() {
        List<Medlem> members = g.generateListFromFile(g.stringtoFilePathName("customers.txt"));
        assertTrue(members.size() == 15);
        assertFalse(members.size() == 13);
    }


    @Test
    public final void searchUserFromListTest() {
        List<Medlem> member = g.generateListFromFile(g.stringtoFilePathName("customers.txt"));
        String helaNamnet = "";
        String personNr = "";
        for (Medlem m : member) {
            if (m.getFörnamnEfternamn().equals("Alhambra Aromes") || m.getPersonNr().equals("7603021234")) {
                helaNamnet = m.getFörnamn() + " " + m.getEfternamn();
                personNr = m.getPersonNr();
            }
        }
        assertEquals(helaNamnet, "Alhambra Aromes");
        assertEquals(personNr, "7603021234");
    }


    @Test
    public final void registerMemberPrintTest() {
        final String usernamePath = m.getFörnamnEfternamn() + ".txt";
        String message = "";

        if (new File(usernamePath).isFile()) {
            try (PrintWriter ut = new PrintWriter(new BufferedWriter(new FileWriter(usernamePath, true)))) {
                message = LocalDate.now().toString();
            } catch (IOException e) {
                e.printStackTrace();
                message = g.errorMessage();
            }
        } else {
            try (PrintWriter ut = new PrintWriter(new BufferedWriter(new FileWriter(usernamePath)))) {
                message = m.getPersonNr() + ", " + m.getFörnamnEfternamn() + "\n" + LocalDate.now().toString();
            } catch (IOException e) {
                e.printStackTrace();
                message = g.errorMessage();
            }
        }
        assertEquals(message, LocalDate.now().toString());
    }


}