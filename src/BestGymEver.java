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

    public String errorMessage() {
        return radbrytning() + "Det blev något fel!" + radbrytning();
    }

    public String memberNotFound() {
        return "Kunden är inte medlem här.";
    }

    public String byeMessage() {
        return "Vi ses en annan gång!";
    }

    public String customerPayed() {
        return "Kunden är en nuvarande medlem. Årsavgiften betalades inom ett år sedan.";
    }

    public String customerDidNotPay() {
        return "Kunden är en före detta medlem. Årsavgiften betalades för över ett år sedan.";
    }

    public String radbrytning() {
        return "------------------------------";
    }

    public String getPathFromString() {
        return pathFromString;
    }

    public Path stringtoFilePathName(String pathName) {
        Path pathFromFile = Paths.get(pathName);

        return pathFromFile;

    }

    public List<Medlem> generateListFromFile(Path path) {
        List<Medlem> medlemmar = new ArrayList<>();
        try (Scanner in = new Scanner(new File(path.getFileName().toString()))) {
            while (in.hasNext()) {
                String tempPersonNr = in.next();
                tempPersonNr = tempPersonNr.substring(0, tempPersonNr.length() - 1);
                String tempFörnamn = in.next();
                String tempEfternamn = in.next();
                String tempMedlemskap = in.next();
                medlemmar.add(new Medlem(tempPersonNr, tempFörnamn, tempEfternamn, tempMedlemskap));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(errorMessage());
            System.exit(0);
        }
        return medlemmar;
    }


    public void registerMemberPrint(Medlem m) {
        final String usernamePath = m.getFörnamnEfternamn() + ".txt";

        if (new File(usernamePath).isFile()) {
            try (PrintWriter ut = new PrintWriter(new BufferedWriter(new FileWriter(usernamePath, true)))) {
                ut.println(LocalDate.now().toString());
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(errorMessage());
            }
        } else {
            try (PrintWriter ut = new PrintWriter(new BufferedWriter(new FileWriter(usernamePath)))) {
                ut.println(m.getPersonNr() + ", " + m.getFörnamnEfternamn() + "\n" + LocalDate.now().toString());
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(errorMessage());
            }
        }
    }

    public void searchUserFromList(String förnamn, String efternamn, String personNr) {
        List<Medlem> members = generateListFromFile(stringtoFilePathName(getPathFromString()));
        boolean didYouFindMember = false;
        String message = "";
        String förnamnEfternamn = förnamn.trim() + " " + efternamn.trim();
        for (Medlem m : members) {
            if (m.getFörnamnEfternamn().toLowerCase().equals(förnamnEfternamn.toLowerCase()) || m.getPersonNr().equals(personNr)) {
                message = compareDate(m.getMedlemskap(), m);
                didYouFindMember = true;
            }
        }
        if (didYouFindMember == false) {
            message = memberNotFound();
        }

        System.out.println(message);

    }

    public String compareDate(String dateToCompare, Medlem m) {
        String message = "";

        LocalDate compareInLocalDate = LocalDate.parse(dateToCompare);
        LocalDate todayInLocalDate = LocalDate.now();
        todayInLocalDate = todayInLocalDate.minusYears(1);
        boolean isItBefore = todayInLocalDate.isBefore(compareInLocalDate);

        if (isItBefore == true) {
            registerMemberPrint(m);
            message = customerPayed();
        } else {
            message = customerDidNotPay();
        }
        return message;
    }

    public void checkMember() {
        Scanner in = new Scanner(System.in);
        String tempPersonnr = "";
        String tempFörnamn = "";
        String tempEfternamn = "";
        String tempFörnamnEfternamn = "";

        while (true) {
            System.out.print("Skriv ett namn eller personnr: ");
            try {
                if (in.hasNextDouble()) {
                    tempPersonnr = in.next();
                    break;
                } else if (in.hasNextLine()) {
                    tempFörnamnEfternamn = in.nextLine();
                    String[] words = tempFörnamnEfternamn.split(" ");
                    tempFörnamn = words[0];
                    if (!(words.length >= 2)) {
                        System.out.println(errorMessage());
                    } else {
                        tempEfternamn = words[1];
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(errorMessage());
            }
        }
        searchUserFromList(tempFörnamn, tempEfternamn, tempPersonnr);
        while (checkForAnotherMember() == true) {
            checkForAnotherMember();
        }
    }

    public boolean checkForAnotherMember() {
        Scanner in = new Scanner(System.in);

        System.out.print("Kolla en annan kund? (ja/nej) ");
        String answer = "";
        boolean masterAnswer = true;
        if (in.hasNextLine()) {
            answer = in.nextLine();
            answer = answer.toLowerCase();

            if (answer.equals("nej")) {
                System.out.println(byeMessage());
                System.exit(0);
            } else if (!answer.equals("ja")) {
                System.out.println(errorMessage());
            } else {
                masterAnswer = false;
            }
        }
        return masterAnswer;
    }


    public void mainProgram() {
        while (true) {
            checkMember();
        }
    }


}
