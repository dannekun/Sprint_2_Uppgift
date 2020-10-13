import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Daniel Bojic
 * Date: 2020-10-08
 * Time: 16:32
 * Project: Inlämningsuppgift 2
 * Copyright: MIT
 */
public class BestGymEver {

    private final String pathFromString = "customers.txt";

    private final String radbrytning = "------------------------------";

    public String error() {
        return getRadbrytning() + "Det blev något fel!" + getRadbrytning();
    }

    public String notFound() {
        return "Kunden är inte medlem här.";
    }

    public String byeMessage() {
        return "Vi ses en annan gång!";
    }

    public String payed() {
        return "Kunden är en nuvarande medlem. Årsavgiften betalades inom ett år sedan.";
    }

    public String notPayed() {
        return "Kunden är en före detta medlem. Årsavgiften betalades för över ett år sedan.";
    }


    public String getRadbrytning() {
        return radbrytning;
    }

    public String getPathFromString() {
        return pathFromString;
    }

    public Path stringtoFileName(String pathName) {
        Path pathFromFile = Paths.get(pathName);

        return pathFromFile;

    }

    public List<Medlem> generateList(Path path) {
        List<Medlem> medlemmar = new ArrayList<>();
        try (Scanner in = new Scanner(new File(path.getFileName().toString()))) {
            while (in.hasNext()) {
                String importPersonNr = in.next();
                importPersonNr = importPersonNr.substring(0, importPersonNr.length() - 1);
                String importFörnamn = in.next();
                String importEfternamn = in.next();
                String importMedlemskap = in.next();
                medlemmar.add(new Medlem(importPersonNr, importFörnamn, importEfternamn, importMedlemskap));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(error());
            System.exit(0);
        }
        return medlemmar;
    }


    public void printHistory(Medlem m) {
        final String usernamePath = m.getEntireName() + ".txt";

        if (new File(usernamePath).isFile()) {
            try (PrintWriter ut = new PrintWriter(new BufferedWriter(new FileWriter(usernamePath, true)))) {
                ut.println(LocalDate.now().toString());
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(error());
            }
        } else {
            try (PrintWriter ut = new PrintWriter(new BufferedWriter(new FileWriter(usernamePath)))) {
                ut.println(m.getPersonNr() + ", " + m.getEntireName() + "\n" + LocalDate.now().toString());
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(error());
            }
        }
    }

    public void searchUser(String förnam, String efternamn, String personNr) {
        List<Medlem> members = generateList(stringtoFileName(getPathFromString()));
        boolean didYouFind = false;
        String message = "";
        String helaNamnet = förnam.trim() + " " + efternamn.trim();
        for (Medlem m : members) {
            if (m.getEntireName().toLowerCase().equals(helaNamnet.toLowerCase()) || m.getPersonNr().equals(personNr)) {
                message = compareDate(m.getMedlemskap(), m);
                didYouFind = true;
            }
        }
        if (didYouFind == false) {
            message = notFound();
        }

        System.out.println(message);

    }

    public String compareDate(String compare, Medlem m) {
        String message = "";

        LocalDate compareInDate = LocalDate.parse(compare);
        LocalDate todayInDate = LocalDate.now();
        todayInDate = todayInDate.minusYears(1);
        boolean isItBefore = todayInDate.isBefore(compareInDate);

        if (isItBefore == true) {
            printHistory(m);
            message = payed();
        } else {
            message = notPayed();
        }
        return message;
    }

    public void typeUser() {
        Scanner in = new Scanner(System.in);
        String personnr = "";
        String first = "";
        String last = "";
        String entireName = "";

        while (true) {
            System.out.print("Skriv ett namn eller personnr: ");
            try {
                if (in.hasNextDouble()) {
                    personnr = in.next();
                    break;
                } else if (in.hasNextLine()) {
                    entireName = in.nextLine();
                    String[] words = entireName.split(" ");
                    first = words[0];
                    if (!(words.length >= 2)) {
                        System.out.println(error());
                    } else {
                        last = words[1];
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(error());
            }
        }
        searchUser(first, last, personnr);
        while (anotherTime() == true) {
            anotherTime();
        }
    }

    public boolean anotherTime() {
        Scanner in = new Scanner(System.in);

        System.out.print("Kolla en annan kund? (ja/nej) ");
        String answer = "";
        boolean masterAnswer = true;
        if (in.hasNextLine()) {
            answer = in.nextLine();

            if (answer.equals("nej")) {
                System.out.println(byeMessage());
                System.exit(0);
            } else if (!answer.equals("ja")) {
                System.out.println(error());
            } else {
                masterAnswer = false;
            }
        }
        return masterAnswer;
    }


    public void mainProgram() {
        while (true) {
            typeUser();
        }
    }


}
