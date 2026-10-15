/**
 * Ein Ticket (Stoermeldung) der NetStar GmbH.
 * <p>
 * Prioritaet: 1 = kritisch, 2 = hoch, 3 = normal, 4 = niedrig
 * <p>
 * Status: "OFFEN", "IN_BEARBEITUNG" oder "ERLEDIGT"
 * <p>
 * Diese Klasse ist fertig und wird nicht veraendert.
 *
 * @author Christopher Partridge
 */
public class Ticket {

    private final int id;
    private final String titel;
    private final String kunde;
    private final String bearbeiter;
    private final int prioritaet;
    private String status;

    public Ticket(int id, String titel, String kunde, String bearbeiter, int prioritaet, String status) {
        this.id = id;
        this.titel = titel;
        this.kunde = kunde;
        this.bearbeiter = bearbeiter;
        this.prioritaet = prioritaet;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getTitel() {
        return titel;
    }

    public String getKunde() {
        return kunde;
    }

    public String getBearbeiter() {
        return bearbeiter;
    }

    public int getPrioritaet() {
        return prioritaet;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("#%-2d  P%d  %-34s %-20s %-13s %s",
                id, prioritaet, titel, kunde, bearbeiter, status);
    }
}
