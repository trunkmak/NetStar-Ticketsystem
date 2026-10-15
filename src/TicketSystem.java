import java.util.*;

/**
 * Verwaltet Tickets und deren Bearbeitung.
 *
 * @author Hermann Zimmermann
 * @version 1.0
 */
public class TicketSystem {
    private List<Ticket> tickets = new ArrayList<>();
    private Queue<Ticket> offeneTickets = new PriorityQueue<>(
            (ticket1, ticket2) -> {
                int vergleich = ticket1.getPrioritaet() - ticket2.getPrioritaet();
                if (vergleich != 0) {
                    return vergleich;
                }
                return ticket1.getId() - ticket2.getId(); // Bei gleicher Prioritaet wird die Id auch verglichen.
            });
    private Stack<Ticket> erledigteTickets = new Stack<>();
    private Map<String, List<Ticket>> ticketsProBearbeiter = new HashMap<>();
    private Set<String> kunden = new HashSet<>();

    // Status von Tickets
    private static final String OFFEN = "OFFEN";
    private static final String ERLEDIGT = "ERLEDIGT";
    private static final String IN_BEARBEITUNG = "IN_BEARBEITUNG";

    /**
     * Legt ein neues Ticket mit der naechsten freien Id an.
     *
     * @param titel      Titel des Tickets.
     * @param kunde      Der Kunde des Tickets.
     * @param bearbeiter Der zustaendige Bearbeiter.
     * @param prioritaet 1 (kritisch) bis 4 (niedrig).
     *
     */
    public void anlegen(String titel, String kunde, String bearbeiter, int prioritaet) {
        int groessteId = 0;
        for (Ticket t : tickets) {
            if (t.getId() > groessteId) { // Hat das aktuelle Ticket eine hoehere ID als unser bisheriger Hoechstwert?
                groessteId = t.getId();
            }
        }
        groessteId++;

        Ticket neuesTicket = new Ticket(groessteId, titel, kunde, bearbeiter, prioritaet, "OFFEN");
        hinzufuegen(neuesTicket);
    }

    /**
     * Fuegt ein Ticket hinzu und reiht offene Tickets ein.
     *
     * @param ticket Das hinzuzufuegende Ticket.
     */
    public void hinzufuegen(Ticket ticket) {
        String bearbeiter = ticket.getBearbeiter();

        if (ticket.getStatus().equals(OFFEN)) {
            offeneTickets.add(ticket);
        }
        tickets.add(ticket);

        if (!ticketsProBearbeiter.containsKey(bearbeiter)) {
            ticketsProBearbeiter.put(bearbeiter, new ArrayList<>());
        }
        ticketsProBearbeiter.get(bearbeiter).add(ticket);
        kunden.add(ticket.getKunde());
    }

    /**
     * Nimmt das naechste offene Ticket aus der Queue und setzt seinen Status auf ERLEDIGT.
     * Gibt eine Meldung aus, wenn die Queue leer ist.
     */
    public void naechstesErledigen() {
        Ticket ticket = offeneTickets.poll();

        if (ticket != null) {
            ticket.setStatus(ERLEDIGT);
            erledigteTickets.push(ticket);
        } else {
            System.out.println("Keine offenen Tickets vorhanden.");
        }
    }

    /**
     * Nimmt das zuletzt erledigte Ticket vom Stack, setzt es auf OFFEN und haengt es wieder hinten in die Warteschlange ein.
     * Gibt eine Meldung aus, wenn keine Erledigung rueckgaengig gemacht werden kann.
     */
    public void letzteErledigungRueckgaengig() {
        if (!erledigteTickets.isEmpty()) {
            Ticket ticket = erledigteTickets.pop();
            ticket.setStatus(OFFEN);
            offeneTickets.offer(ticket);
        } else {
            System.out.println("Keine Erledigung zum Rueckgaengigmachen vorhanden.");
        }
    }

    /**
     * Gibt alle Tickets gruppiert nach Bearbeiter aus.
     */
    public void ticketsProBearbeiterAusgeben() {
        for (String bearbeiter : ticketsProBearbeiter.keySet()) {
            System.out.println(bearbeiter + ":");

            for (Ticket ticket : ticketsProBearbeiter.get(bearbeiter)) {
                System.out.println(ticket);
            }
        }
    }

    /**
     * Gibt alle Tickets aus, deren Titel den Suchtext enthaelt.
     *
     * @param suchtext Text, nach dem in den Tickettiteln gesucht wird
     */
    public void ticketsSuchen(String suchtext) {
        String suche = suchtext.toLowerCase();
        tickets.stream()
                .filter(ticket -> ticket.getTitel()
                        .toLowerCase()
                        .contains(suche))
                .forEach(ticket -> System.out.println(ticket));
    }

    /**
     * Gibt das erste kritische Ticket eines Bearbeiters aus.
     *
     * @param bearbeiter Name des Bearbeiters
     */
    public void erstesKritischesTicket(String bearbeiter) {
        Optional<Ticket> erstesKritischesTicket = tickets.stream()
                .filter(ticket -> ticket.getBearbeiter().equals(bearbeiter))
                .filter(ticket -> ticket.getPrioritaet() == 1)
                .findFirst();
        if (erstesKritischesTicket.isPresent()) {
            System.out.println(erstesKritischesTicket.get());
        } else {
            System.out.println("Fuer " + bearbeiter + " wurden keine kritischen Tickets gefunden.");
        }
    }

    /**
     * Gibt alle Kunden aus.
     */
    public void kundenAusgeben() {
        for (String kunde : kunden) {
            System.out.println(kunde);
        }
    }

    /**
     * Gibt alle gespeicherten Tickets als {@code List<Ticket>} zurueck.
     *
     * @return Die Liste aller Tickets.
     */
    public List<Ticket> getTickets() {
        return tickets;
    }

}
