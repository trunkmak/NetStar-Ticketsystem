import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Wertet Tickets mit Lambdas und Streams aus.
 *
 * @author Hermann Zimmermann
 * @version 1.0
 */
public class Auswertung {
    private Predicate<Ticket> istOffen = ticket -> ticket.getStatus().equals("OFFEN");
    private Predicate<Ticket> istKritisch = ticket -> ticket.getPrioritaet() == 1;
    private Function<Ticket, String> titelMitBearbeiter = ticket -> ticket.getTitel() + " (" + ticket.getBearbeiter() + ") ";
    private Consumer<String> zeileAusgeben = zeile -> System.out.println("- " + zeile);

    private List<Ticket> tickets;

    /**
     * Erstellt eine Auswertung fuer die uebergebene Ticketliste.
     *
     * @param tickets Liste aller auszuwertenden Tickets.
     */
    public Auswertung(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    /**
     * Ermittelt die Anzahl der offenen Tickets.
     *
     * @return Anzahl der Tickets mit dem Status OFFEN.
     */
    public long anzahlOffen() {
        return tickets.stream()
                .filter(istOffen)
                .count();
    }

    /**
     * Ermittelt die Titel aller kritischen offenen Tickets.
     *
     * @return {@code List<String>} mit den Titeln der kritischen offenen Tickets.
     */
    public List<String> kritischeOffeneTitel() {
        return tickets.stream()
                .filter(istOffen)
                .filter(istKritisch)
                .map(ticket -> ticket.getTitel())
                .collect(Collectors.toList());
    }

    /**
     * Gibt alle offenen Tickets mit Titel und Bearbeiter aus.
     */
    public void offeneTicketsAusgeben() {
        tickets.stream()
                .filter(istOffen) // Laesst nur offene Tickets durch.
                .map(titelMitBearbeiter) // Formt jedes Ticket in einen Text um.
                .forEach(zeileAusgeben); // Gibt jeden Text mit '-' davor aus.
    }

    /**
     * Ermittelt die dringendsten offenen Tickets in sortierter Reihenfolge.
     *
     * @param anzahl Maximale Anzahl der zurueckgegebenen Tickets.
     * @return {@code List<Ticket>} mit den dringendsten offenen Tickets.
     */
    public List<Ticket> dringendsteOffene(int anzahl) {
        return tickets.stream()
                .filter(istOffen)
                .sorted(Comparator.comparing((Ticket t) -> t.getPrioritaet())
                        .thenComparing(ticket -> ticket.getId()))
                .limit(anzahl)
                .collect(Collectors.toList());
    }

    /**
     * Ermittelt die Titel aller Tickets eines Kunden in alphabetischer Reihenfolge.
     *
     * @param kunde Name des Kunden, dessen Tickets gesucht werden.
     * @return Alphabetisch sortierte Titel der Tickets des Kunden.
     */
    public List<String> titelVonKunde(String kunde) {
        return tickets.stream()
                .filter(ticket -> ticket.getKunde().equals(kunde))
                .map(ticket -> ticket.getTitel())
                .sorted()
                .collect(Collectors.toList());
    }

    /**
     * Prueft, ob es fuer den angegebenen Kunden mindestens ein Ticket gibt,
     * das gleichzeitig kritisch und offen ist.
     *
     * @param kunde Name des Kunden, dessen Tickets gesucht werden.
     * @return true, wenn der Kunde mindestens ein kritisches offenes Ticket hat, sonst false.
     */
    public boolean hatKritischesOffenesTicket(String kunde) {
        return tickets.stream() // Sobald alle 3 Bedingungen erfuellt sind, gibt die Methode 'true' zurueck.
                .anyMatch(ticket -> ticket.getKunde().equals(kunde) // Wird geprueft, ob Kundenname dem String kunde entspricht.
                        && istKritisch.test(ticket) // Ob die Prioritaet kritisch ist.
                        && istOffen.test(ticket)); // Ob der Status OFFEN lautet.
    }

    /**
     * Ermittelt die Anzahl der Tickets pro Bearbeiter.
     *
     * @return Map mit Bearbeiternamen als Schluessel und der jeweiligen Ticketanzahl als Wert.
     */
    public Map<String, Long> anzahlProBearbeiter() {
        return tickets.stream()
                .collect(Collectors.groupingBy(ticket -> ticket.getBearbeiter(),
                        Collectors.counting()));
    }

    /**
     * Berechnet den Durchschnitt aller Prioritaetswerte.
     *
     * @return Durchschnitt aller Prioritaetswerte oder 0.0, wenn keine Tickets vorhanden sind.
     */
    public double durchschnittPrioritaet() {
        return tickets.stream()
                .mapToInt(tickets -> tickets.getPrioritaet()) // Holt jede Prioritaetszahl.
                .average() // Bildet daraus den Durchschnitt.
                .orElse(0.0); // Liefert 0.0, falls die Ticketliste leer waere und daher kein Durchschnitt existiert.
    }

    /**
     * Ermittelt die Anzahl der offenen Tickets pro Kunde.
     *
     * @return Map mit Kundennamen als Schluessel und der Anzahl offener Tickets als Wert.
     */
    public Map<String, Long> offeneTicketsProKunde() {
        return tickets.stream()
                .filter(istOffen)
                .collect(Collectors.groupingBy(ticket -> ticket.getKunde(), Collectors.counting()));
    }

}
