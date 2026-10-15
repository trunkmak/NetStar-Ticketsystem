import java.util.ArrayList;
import java.util.List;

/**
 * Liefert 10 Beispiel-Tickets fuer die NetStar GmbH.
 * Diese Klasse ist fertig und wird nicht veraendert.
 *
 * @author Christopher Partridge
 */
public class Beispieldaten {

    /**
     * Laedt Beispiel Daten mit Id, Titel, Kunde, Bearbeiter, Prioritaet und Status.
     * @return Beispiel Tickets
     */
    public static List<Ticket> ladeTickets() {
        List<Ticket> tickets = new ArrayList<>();

        tickets.add(new Ticket(1, "VPN-Verbindung bricht ab", "Mueller Logistik", "Anna Berger", 2, "OFFEN"));
        tickets.add(new Ticket(2, "Mailserver nicht erreichbar", "Schmidt & Soehne", "Tom Weber", 1, "OFFEN"));
        tickets.add(new Ticket(3, "Neuen Drucker einrichten", "Mueller Logistik", "Anna Berger", 4, "OFFEN"));
        tickets.add(new Ticket(4, "Passwort zuruecksetzen", "Baeckerei Hoffmann", "Tom Weber", 3, "ERLEDIGT"));
        tickets.add(new Ticket(5, "Backup schlaegt fehl", "Schmidt & Soehne", "Lena Fischer", 1, "OFFEN"));
        tickets.add(new Ticket(6, "WLAN im Besprechungsraum langsam", "Autohaus Kraemer", "Lena Fischer", 3, "IN_BEARBEITUNG"));
        tickets.add(new Ticket(7, "Software-Lizenz abgelaufen", "Baeckerei Hoffmann", "Anna Berger", 2, "OFFEN"));
        tickets.add(new Ticket(8, "Monitor flackert", "Autohaus Kraemer", "Tom Weber", 4, "ERLEDIGT"));
        tickets.add(new Ticket(9, "Firewall-Regel anpassen", "Mueller Logistik", "Lena Fischer", 2, "OFFEN"));
        tickets.add(new Ticket(10, "Server-Festplatte fast voll", "Autohaus Kraemer", "Tom Weber", 1, "IN_BEARBEITUNG"));

        return tickets;
    }
}
