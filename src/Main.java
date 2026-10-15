import java.util.List;
import java.util.Scanner;

/**
 * Konsolenmenue des NetStar-Ticketsystems (Geruest).
 * <p>
 * Ihr ergaenzt die Stellen mit TODO. Zuerst schreibt ihr die Klasse
 * TicketSystem (Teil A), spaeter die Klasse Auswertung (Teil B).
 *
 * @author Christopher Partridge
 * @author Hermann Zimmermann
 */
public class Main {

    public static void main(String[] args) {
        TicketSystem system = new TicketSystem();
        List<Ticket> beispiele = Beispieldaten.ladeTickets();
        Auswertung auswertung = new Auswertung(system.getTickets());

        for (Ticket t : beispiele) {
            system.hinzufuegen(t);
        }

        Scanner scanner = new Scanner(System.in);
        boolean laeuft = true;

        while (laeuft) {
            menueAusgeben();
            if (!scanner.hasNextLine()) {
                break;
            }
            String eingabe = scanner.nextLine().trim();

            switch (eingabe) {
                case "1":
                    for (Ticket t : system.getTickets()) {
                        System.out.println(t);
                    }
                    break;
                case "2":
                    System.out.print("Titel: ");
                    String titel = scanner.nextLine().trim();

                    System.out.print("Kunde: ");
                    String kunde = scanner.nextLine().trim();

                    System.out.print("Bearbeiter: ");
                    String bearbeiter = scanner.nextLine().trim();

                    System.out.print("Prioritaet: ");
                    String eingabePrioritaet = scanner.nextLine().trim();
                    try {
                        int prioritaet = Integer.parseInt(eingabePrioritaet);
                        if (prioritaet < 1 || prioritaet > 4) { // Erlaubten Bereich pruefen.
                            System.out.println("Prioritaet muss zwischen 1 und 4 liegen.");
                        } else {
                            system.anlegen(titel, kunde, bearbeiter, prioritaet);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Prioritaet muss eine Zahl sein.");
                    }
                    break;
                case "3":
                    system.naechstesErledigen();
                    break;
                case "4":
                    system.letzteErledigungRueckgaengig();
                    break;
                case "5":
                    system.ticketsProBearbeiterAusgeben();
                    break;
                case "6":
                    system.kundenAusgeben();
                    break;
                case "7":
                    System.out.println("=== AUSWERTUNGEN ===");
                    System.out.println("\nOffene Tickets: " + auswertung.anzahlOffen());

                    System.out.println("\nKritische offene Titel:");
                    System.out.println(auswertung.kritischeOffeneTitel());

                    System.out.println("\nAlle offenen Tickets:");
                    auswertung.offeneTicketsAusgeben();

                    System.out.println("\nDrei dringendste offene Tickets:");
                    for (Ticket ticket : auswertung.dringendsteOffene(3)) {
                        System.out.println(ticket);
                    }

                    System.out.println("\nAnzahl Tickets pro Bearbeiter:");
                    System.out.println(auswertung.anzahlProBearbeiter());

                    System.out.println("\nDurchschnittliche Prioritaet: " + auswertung.durchschnittPrioritaet());

                    System.out.println("Offene Tickets pro Kunde:");
                    System.out.println(auswertung.offeneTicketsProKunde());

                    // --- Testkunde ---
                    String testKunde = "Mueller Logistik";

                    System.out.println("\nTitel von " + testKunde + ":");
                    System.out.println(auswertung.titelVonKunde(testKunde));

                    System.out.println("\nHat " + testKunde + " ein kritisches offenes Ticket?: " + auswertung.hatKritischesOffenesTicket(testKunde));
                    break;
                case "8":
                    System.out.print("Suche: ");
                    String eingabeSuche = scanner.nextLine().trim();
                    system.ticketsSuchen(eingabeSuche);
                    break;
                case "9":
                    System.out.print("Bearbeiter: ");
                    String gesuchterBearbeiter  = scanner.nextLine().trim();
                    system.erstesKritischesTicket(gesuchterBearbeiter );
                    break;
                case "0":
                    laeuft = false;
                    break;
                default:
                    System.out.println("Ungueltige Eingabe.");
            }
        }
        System.out.println("Programm beendet.");
    }

    private static void menueAusgeben() {
        System.out.println();
        System.out.println("=== NetStar Ticketsystem ===");
        System.out.println("1  Alle Tickets anzeigen");
        System.out.println("2  Neues Ticket anlegen");
        System.out.println("3  Naechstes Ticket erledigen");
        System.out.println("4  Letzte Erledigung rueckgaengig");
        System.out.println("5  Tickets pro Bearbeiter");
        System.out.println("6  Alle Kunden");
        System.out.println("7  Auswertungen");
        System.out.println("8  Tickets suchen");
        System.out.println("9  Erstes kritisches Ticket eines Bearbeiters");
        System.out.println("0  Beenden");
        System.out.print("Auswahl: ");
    }
}
