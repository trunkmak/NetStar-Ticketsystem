# Architektur und Datenfluss

![Datenfluss: Anlegen, PriorityQueue, Erledigen und Rückgängig mit erneutem Einreihen.](../assets/ticket-ablauf.png)

Das Programm hat fünf Klassen im Standardpackage. `Main` liest Eingaben und ruft Fachmethoden auf. `TicketSystem` verwaltet den Bestand, `Auswertung` liest dieselbe Bestandsliste, `Beispieldaten` stellt zehn Starttickets bereit, `Ticket` trägt die Daten.

```mermaid
classDiagram
    class Main {
        +main(String[] args)$ void
    }
    class Beispieldaten {
        +ladeTickets()$ List~Ticket~
    }
    class Ticket {
        -int id
        -String titel
        -String kunde
        -String bearbeiter
        -int prioritaet
        -String status
        +getId() int
        +getTitel() String
        +getKunde() String
        +getBearbeiter() String
        +getPrioritaet() int
        +getStatus() String
        +setStatus(String status) void
        +toString() String
    }
    class TicketSystem {
        -List~Ticket~ tickets
        -Queue~Ticket~ offeneTickets
        -Stack~Ticket~ erledigteTickets
        -Map~String,List~ ticketsProBearbeiter
        -Set~String~ kunden
        +anlegen(String titel, String kunde, String bearbeiter, int prioritaet) void
        +hinzufuegen(Ticket ticket) void
        +naechstesErledigen() void
        +letzteErledigungRueckgaengig() void
        +ticketsProBearbeiterAusgeben() void
        +ticketsSuchen(String suchtext) void
        +erstesKritischesTicket(String bearbeiter) void
        +kundenAusgeben() void
        +getTickets() List~Ticket~
    }
    class Auswertung {
        -List~Ticket~ tickets
        -Predicate~Ticket~ istOffen
        -Predicate~Ticket~ istKritisch
        -Function~Ticket,String~ titelMitBearbeiter
        -Consumer~String~ zeileAusgeben
        +anzahlOffen() long
        +kritischeOffeneTitel() List~String~
        +offeneTicketsAusgeben() void
        +dringendsteOffene(int anzahl) List~Ticket~
        +titelVonKunde(String kunde) List~String~
        +hatKritischesOffenesTicket(String kunde) boolean
        +anzahlProBearbeiter() Map~String,Long~
        +durchschnittPrioritaet() double
        +offeneTicketsProKunde() Map~String,Long~
    }
    Main ..> Beispieldaten : Startdaten
    Main ..> TicketSystem : Bedienung
    Main ..> Auswertung : Ausgaben
    Main ..> Ticket : Anzeige
    Beispieldaten ..> Ticket : erzeugt
    TicketSystem "1" --> "0..*" Ticket : verwaltet Referenzen
    Auswertung "1" --> "0..*" Ticket : wertet aus
```

Das Diagramm ist eine lesbare Architekturübersicht. Für D2 fehlen noch das vollständige UML-Inventar einschließlich Konstruktoren, Statuskonstanten, ausgeschriebener verschachtelter Map-Typen sowie ein geprüfter Bild-/PDF-Export. D2 bleibt deshalb teilweise offen.

## Collections im Zusammenspiel

`hinzufuegen` aktualisiert Liste, Bearbeiter-Map und Kunden-Set. Nur bei Status `OFFEN` kommt die Referenz auch in die PriorityQueue. `naechstesErledigen` entnimmt die dringendste Referenz, ändert den Status und legt sie auf den Stack. Undo nimmt die letzte Referenz vom Stack, setzt sie auf `OFFEN` und ordnet sie erneut in die Queue ein.

Für den Bestand gelten die offenen IDs **1, 2, 3, 5, 7, 9**. Die Reihenfolge beim Erledigen lautet **2, 5, 1, 7, 9, 3**. Die bei Programmstart bereits erledigten Tickets #4/#8 befinden sich nicht im Undo-Stack; rückgängig gemacht werden nur Erledigungen aus der laufenden Sitzung.

`Auswertung` besitzt keinen eigenen Snapshot. Ihr Konstruktor speichert die übergebene Listenreferenz. Änderungen am Bestand werden deshalb im nächsten neu erzeugten Stream berücksichtigt. Die zurückgegebene Liste ist öffentlich veränderbar; eine spätere Kapselungsverbesserung muss diesen Datenfluss bewusst erhalten.
