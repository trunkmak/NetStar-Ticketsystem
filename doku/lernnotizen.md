# Lernnotizen zum Ticketsystem

![Die sechs Lernbausteine Tickets, Collections, Priorität, Rückgängig, Streams und Auswertung.](../assets/lernbausteine.png)

Diese Lernnotizen erklären die Konzepte des Ticketsystems anhand der vorhandenen fünf Java-Klassen und der Aufgabenblätter. Jede Erklärung verbindet den Begriff mit einem konkreten Verhalten im Programm. Für die noch offenen Bonusaufgaben dienen die Notizen als Vorbereitung.

## Von der Eingabe zur Suche

`String suche = suchtext.toLowerCase()` bereitet den Suchbegriff einmal vor. Im Stream wird auch jeder Titel kleingeschrieben; `contains(suche)` prüft anschließend den Teiltext. Die Variable bewirkt erst etwas, wenn sie tatsächlich im Filter verwendet wird.

Im Code: `TicketSystem.ticketsSuchen`. Beispiel: `SERVER` findet die Tickets #2 und #10. Die Suche betrifft alle Statuswerte. Die Benutzerführung liest die Eingabe in `Main`; `TicketSystem` führt die Suche aus. Für internationale Texte wäre später die Locale-Abhängigkeit von `toLowerCase()` zu prüfen.

**Selbst erklären:** Was passiert bei leerem Suchtext? Warum wird ein offenes Ticket dabei nicht automatisch bevorzugt?

## `findFirst` und `Optional`

Z4 filtert zuerst nach Bearbeiter, dann nach Priorität 1. `findFirst()` liefert `Optional<Ticket>`, weil es keinen Treffer geben kann. `isPresent()` prüft den Inhalt; erst im erfolgreichen Zweig ist `get()` abgesichert.

„Erstes“ bezieht sich auf die Reihenfolge der Gesamtliste. Es bedeutet weder „erstes offenes“ noch „nächstes in der PriorityQueue“. Für Tom Weber wird beim Start #2 gefunden, für Anna Berger kein kritisches Ticket. Auch ein bereits erledigtes kritisches Ticket kann später gefunden werden, weil Z4 keinen Statusfilter enthält.

**Selbst erklären:** Wie müsste sich die fachliche Frage ändern, wenn zusätzlich nur offene Tickets gesucht würden?

## Collections und gemeinsame Objekte

Liste, Queue, Stack, Map und Set haben verschiedene Aufgaben. Ein Ticket wird als dasselbe Objekt in mehreren Collections referenziert. Wird sein Status geändert, sehen andere Zugriffe auf dieses Objekt den neuen Wert.

Auch `Auswertung` bekommt dieselbe Liste über `system.getTickets()`. Deshalb bleibt eine bereits erzeugte Auswertung nach `anlegen` aktuell. Ein Stream wird dagegen für jeden Methodenaufruf neu erzeugt und nur einmal verwendet.

Die ursprüngliche Basisaufgabe verwendet eine FIFO-Queue. **Im aktuellen Code gilt wegen Z1 eine PriorityQueue:** kleinerer Prioritätswert zuerst, danach kleinere ID. `offer`/`add` ordnen ein Element nach dieser Regel ein. Die Formulierung „hinten anhängen“ im Undo-Javadoc ist daher veraltet. Der Stack bleibt LIFO: letzte Erledigung zuerst zurücknehmen.

**Selbst erklären:** Nach Erledigen von #2 und #5 wird einmal Undo ausgeführt. Welches Ticket ist wieder offen und welches bleibt erledigt?

## Lambdas und Streams lesen

Die Auswertung lässt sich in drei Fragen zerlegen: Welche Tickets bleiben? Welcher Wert wird daraus? Wie endet die Verarbeitung?

| Baustein | Bedeutung im Projekt |
| --- | --- |
| `Predicate<Ticket>` | Regel, die für ein Ticket `true` oder `false` liefert |
| `.filter(istOffen)` | Die gespeicherte Regel direkt an den Stream übergeben |
| `istOffen.test(ticket)` | Die Regel innerhalb eines eigenen Ausdrucks auf ein Ticket anwenden |
| `Function<Ticket, String>` | Ein Ticket in Titel mit Bearbeiter umwandeln |
| `Consumer<String>` | Einen Text ausgeben, ohne Rückgabewert |
| `map` | Ticket in einen anderen Wert umwandeln |
| `sorted` und `limit` | Erst globale Reihenfolge bestimmen, dann die ersten Treffer auswählen |
| `anyMatch` | Existiert mindestens ein passendes Ticket? Kann beim Treffer abbrechen |
| `collect`, `count`, `forEach` | Terminale Operationen, die die Verarbeitung auslösen |

Die beiden Bedingungen „kritisch“ und „offen“ sind verschieden: #10 hat Priorität 1, ist aber `IN_BEARBEITUNG`. Es zählt deshalb nicht zu den sechs offenen Tickets.

## Gruppieren und Zählen

`groupingBy` entscheidet, zu welchem Schlüssel ein Ticket gehört. `counting` zählt pro Schlüssel. Deshalb entsteht `Map<String, Long>` und keine Map mit Tickets als Werten.

`anzahlProBearbeiter()` zählt alle Tickets: Anna 3, Lena 3, Tom 4. `offeneTicketsProKunde()` filtert vorher nach `OFFEN`: Mueller Logistik 3, Schmidt & Soehne 2, Baeckerei Hoffmann 1. Autohaus Kraemer erscheint nicht als Schlüssel, weil es keine offenen Tickets hat.

**Selbst erklären:** Warum ist die Anzahl aller Tickets pro Bearbeiter noch keine Lösung für E4?

## Durchschnitt und leere Ergebnisse

`mapToInt` macht aus Tickets Prioritätszahlen, `average()` berechnet deren Durchschnitt. Das Ergebnis ist optional: Bei einer leeren Liste fehlt ein Mittelwert. `orElse(0.0)` legt das Verhalten der Methode für diesen Fall fest. Bei den zehn Beispieltickets ergibt sich 2,3.

Beim Testen von Nachkommastellen wird eine kleine Toleranz verwendet. `6` und `6L` sind in einem Vergleich über `Object` zudem unterschiedliche Typen; T1 muss das berücksichtigen (Bonusblatt S. 4).

## Javadoc als Verständniskontrolle

Pro Methode zuerst Eingabe, Verarbeitung und Ergebnis in Alltagssprache beschreiben. Daraus entsteht eine präzise Kurzbeschreibung, ein `@param` je Parameter und bei Rückgabewerten ein `@return`. Eine `void`-Methode bekommt keinen erfundenen Rückgabewert. Generische Typen im Fließtext mit `{@code List<Ticket>}` schreiben.

Die wichtige Lernarbeit ist das erneute Verstehen jeder Methode. Vor dem Formulieren hilft es, Eingabe, Verarbeitung und Ergebnis in eigenen Worten aufzuschreiben. So lässt sich prüfen, ob die Beschreibung zum tatsächlichen Verhalten passt.

**Übung:** Eine Methode auswählen, ohne Code vorlesen erklären und anschließend prüfen, ob ihr Javadoc genau dieses Verhalten beschreibt.

## Werkzeugfehler systematisch eingrenzen

Bei Problemen mit Javadoc Werkzeug und Eingabedateien getrennt prüfen: Ein nicht gefundenes `javadoc` betrifft die Werkzeugauflösung; bei `Input length = 1` die genaue Meldung, Zeichenkodierung und verwendete Argumentdatei untersuchen. Pfade und Arbeitsverzeichnis gehören ebenfalls zur Diagnose. Eine vermutete Ursache muss durch einen passenden Test bestätigt werden.

Für dieses Repository wurde der Aufruf mit explizitem JDK und UTF-8 tatsächlich ausgeführt. Er funktioniert auch im vorhandenen Benutzerpfad mit Umlaut; übrig bleibt eine konkrete Warnung zum Standardkonstruktor. Ein Umlaut im Pfad allein ist damit kein belegter Grund, das Projekt zu verschieben oder globale IntelliJ-Einstellungen zu verändern.

Vorgehen für die nächste Fehlermeldung: genaue Meldung lesen, verwendetes Werkzeug und Arbeitsverzeichnis prüfen, Quelldateien und Ausgabeziel getrennt betrachten, kleinsten passenden Aufruf ausführen und Ergebnis notieren.

## Weiterlernen

1. D1 abschließen: Kommentar und Konstruktorwarnung verstehen und beheben.
2. T1 als eigene, wiederholbare Prüfung schreiben.
3. E1 beginnen: Regeln mit `and`, `or`, `negate` kombinieren, ohne neue Schleifen.
4. Danach Sortierung, Statistik und Ranking in kleinen Schritten ergänzen.

Die [Roadmap](../ROADMAP.md) enthält die dazugehörigen Issues und Abschlusskriterien.

Für den Zusammenhang mit den Unterrichtsaufgaben: [Aufgabenleitfaden](aufgaben.md). Zum persönlichen Rückblick: [Mein Vorgehen und meine Lernerfahrungen](projektbericht.md). Praktische Hilfen stehen unter [Tipps und Tricks](tipps-und-tricks.md).
