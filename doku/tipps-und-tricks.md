# Tipps und Tricks aus dem Projekt

Diese Sammlung verbindet die Hinweise der [Aufgabenblätter](aufgaben.md) mit praktischen Erfahrungen aus dem Ticketsystem. Die Beispiele beziehen sich auf den vorhandenen Code; ausführliche Begriffserklärungen stehen in den [Lernnotizen](lernnotizen.md).

## 1. Eine kleine Frage nach der anderen beantworten

Vor einer Methode drei Fragen notieren: **Was kommt hinein? Was soll passieren? Was kommt heraus?** Bei `titelVonKunde` lautet das: Kundenname hinein, passende Tickets auswählen und Titel sortieren, eine Liste von Titeln zurückgeben. Erst danach die Stream-Schritte formulieren.

Eine Methode schreiben, kompilieren und einen passenden Prüfwert ausprobieren. So lässt sich ein Fehler der letzten Änderung zuordnen. Die komplette Aufgabe auf einmal zu ergänzen macht die Suche unnötig schwer.

## 2. Die Collection nach ihrer Aufgabe auswählen

| Frage | Passender Baustein im Projekt |
| --- | --- |
| Wo bleiben alle Tickets erhalten? | `List<Ticket>` |
| Welches offene Ticket wird als Nächstes bearbeitet? | `PriorityQueue<Ticket>` |
| Welche Erledigung nehme ich zuletzt zurück? | `Stack<Ticket>` |
| Welche Tickets gehören zu welchem Bearbeiter? | `Map<String, List<Ticket>>` |
| Welche Kundennamen gibt es ohne Duplikate? | `Set<String>` |

Die Queue enthält eine Arbeitsauswahl, die Liste den gesamten Bestand. Beim Erledigen wird der Status geändert, nicht das Ticket aus der Gesamtliste gelöscht. Auch erledigte Tickets sollen in Auswertungen und der Übersicht auffindbar bleiben.

## 3. FIFO, LIFO und Priorität auseinanderhalten

FIFO gehört zur ursprünglichen `LinkedList`-Queue: zuerst eingereiht, zuerst entnommen. Der Stack arbeitet LIFO: zuletzt abgelegt, zuerst zurückgenommen. Seit Z1 bestimmt die **PriorityQueue** dagegen anhand des Comparators, welches Ticket zuerst kommt.

Bei gleicher Priorität hilft die ID als zweite Regel. Im Projekt gilt: Priorität 1 vor 2, bei Gleichstand kleinere ID zuerst. Nach Undo wird das Ticket erneut nach dieser Regel eingeordnet. Eine Schleife über eine PriorityQueue verspricht dagegen keine vollständig sortierte Ausgabe.

**Merktest:** Zwei Erledigungen und einmal Undo ergeben im aktuellen Stand #2 ERLEDIGT und #5 OFFEN. Die FIFO-Erwartung aus dem ursprünglichen A4 wäre hier veraltet.

## 4. Leere Ergebnisse vor dem Zugriff behandeln

`poll()` kann bei leerer Queue `null` liefern. `pop()` darf im Projekt erst nach Prüfung des Stacks erfolgen. `findFirst()` liefert ein Optional, das leer sein kann. In der vorhandenen Z4-Methode steht `get()` deshalb im Zweig nach `isPresent()`.

Nicht nur den Trefferfall ausprobieren: Ein unbekannter Bearbeiter, eine vollständig geleerte Queue und Undo direkt nach Programmstart zeigen, ob die Randfälle behandelt werden.

## 5. Streams als Verarbeitungskette lesen

```text
Quelle → auswählen → umwandeln → gegebenenfalls sortieren → Ergebnis erzeugen
stream   filter      map          sorted / limit          collect / count / ...
```

`filter` behält passende Elemente. `map` erzeugt daraus andere Werte. Nach `.map(ticket -> ticket.getTitel())` liegen im Stream Strings statt Tickets vor. Danach muss man mit diesen Strings weiterarbeiten.

Ein Stream endet mit einer terminalen Operation wie `collect`, `count`, `forEach` oder `anyMatch`. Für jede neue Auswertung wieder bei `tickets.stream()` beginnen. Einen bereits verbrauchten Stream nicht erneut benutzen.

## 6. Gespeicherte Regeln und ihr Ergebnis unterscheiden

`istOffen` ist eine Regel vom Typ `Predicate<Ticket>`, kein fertiger Wahrheitswert. `filter(istOffen)` bekommt diese Regel direkt. Innerhalb einer anderen Bedingung wendet `istOffen.test(ticket)` sie auf ein konkretes Ticket an und liefert `true` oder `false`.

Für „richtiger Kunde **und** kritisch **und** offen“ ist `&&` passend. `||` würde schon eine einzige erfüllte Bedingung genügen lassen. Das wäre eine andere fachliche Frage.

Für E1 folgt daraus der nächste Lernschritt: Vorhandene Regeln mit `and`, `or` und `negate` zusammensetzen. Die Aufgabe ist noch offen; zuerst die gewünschten Treffermengen aufschreiben und dann die Kombination prüfen.

## 7. Erst sortieren, dann begrenzen

`sorted(...).limit(3)` sucht die ersten drei nach der gewünschten Reihenfolge aus allen passenden Tickets. `limit(3).sorted(...)` betrachtet nur die ersten drei der bisherigen Reihenfolge und kann dringendere Tickets übersehen.

Beim Comparator zunächst das wichtigste Kriterium wählen und mit `thenComparing` die Gleichstandsregel ergänzen. Wenn der Compiler den Typ nicht erkennt, das erste Lambda mit `(Ticket t) -> ...` schreiben. In E2 muss `reversed()` die komplette Reihenfolge einschließlich der ID-Regel umdrehen.

## 8. „Gibt es eines?“ und „Wie viele?“ getrennt formulieren

`anyMatch` beantwortet eine Existenzfrage und kann bei einem Treffer aufhören. `count` zählt die passende Menge. Für `hatKritischesOffenesTicket` ist deshalb `anyMatch` passend.

Ebenso „kritisch“ und „offen“ nicht verwechseln: Ticket #10 ist kritisch, aber `IN_BEARBEITUNG`. Bei einem Filter auf `OFFEN` darf es nicht mitgezählt werden.

## 9. Gruppenschlüssel und Gruppenwert erklären können

Bei `Map<String, List<Ticket>>` ist der Schlüssel der Bearbeitername, der Wert eine Ticketliste. Bei `Map<String, Long>` nach `groupingBy` und `counting` ist der Wert dagegen eine Anzahl.

Vor dem Schreiben sagen können: „Ich gruppiere nach … und ermittle pro Gruppe …“. Für offene Tickets je Kunde muss der Offen-Filter **vor** der Gruppierung stehen. Ein Kunde ohne Treffer entsteht dabei nicht automatisch als Gruppe mit Anzahl 0.

## 10. Suchbegriff einmal vorbereiten

In `ticketsSuchen` wird der Suchbegriff einmal kleingeschrieben und als `suche` gespeichert. Im Stream wird jeder Titel ebenfalls kleingeschrieben und mit `contains(suche)` geprüft. Die vorbereitete Variable muss im Filter tatsächlich verwendet werden.

Mit einer Eingabe wie `SERVER` prüfen, ob die Normalisierung auf beiden Seiten erfolgt. Die bestehende Suche berücksichtigt alle Statuswerte. Für eine spätere international ausgerichtete Anwendung wäre zusätzlich eine bewusste Locale-Regel sinnvoll.

## 11. Methoden auch im Menü sichtbar machen

Eine korrekte Methode erzeugt noch keine sichtbare Funktion, wenn sie nie aufgerufen wird. Ein Beispiel aus dem Projekt ist `offeneTicketsProKunde()`: Der Aufruf samt Ausgabe musste in Menü 7 ergänzt werden.

Als Muster eine bereits verwendete Auswertungsmethode in `Main` suchen. Bei einem neuen Menüfall auch Überschrift, Eingabe, Methodenaufruf und Rückkehr zur Menüschleife kontrollieren. Die bereits belegten Nummern 8 und 9 bei Teil 2 berücksichtigen.

Im klassischen `switch` können lokale Variablen verschiedener `case`-Zweige denselben Gültigkeitsbereich teilen. Ein sprechender anderer Name wie `gesuchterBearbeiter` löst die dokumentierte Namenskollision. Eigene Blöcke sind eine weitere Möglichkeit.

## 12. Tests mit dem richtigen Ausgangszustand starten

Für unabhängige TicketSystem-Tests jeweils frische Beispieldaten laden. Sonst beeinflusst ein zuvor erledigtes Ticket die nächste Prüfung. Bei einer bewusst zusammenhängenden Folge wie Anlegen → Erledigen → Undo bleibt dagegen gerade derselbe Zustand erhalten.

Erwartungswerte müssen zum aktuellen Ausbau passen: sechs offene Tickets beim Start; Queue-Reihenfolge seit Z1 #2, #5, #1, #7, #9, #3. Zahlen wie `6L` und `6` können bei einem Vergleich über `Object` trotz gleicher Größe unterschiedliche Typen haben. Double-Werte über eine kleine Toleranz vergleichen.

## 13. Javadoc als Kontrolle des eigenen Verständnisses nutzen

Zuerst einen Satz in Alltagssprache schreiben. Dann Parameter und Rückgabewert beschreiben. Eine `void`-Methode erhält keinen erfundenen `@return`-Tag. Typen mit spitzen Klammern in Beschreibungstexten als `{@code List<Ticket>}` notieren.

Nach einer Verhaltensänderung auch die Kommentare lesen. Der vorhandene Undo-Kommentar stammt noch vom FIFO-Gedanken und muss an die PriorityQueue angepasst werden. Dokumentation kann veralten, obwohl sie ohne Syntaxfehler erzeugt wird.

## 14. Werkzeugfehler mit konkreten Beobachtungen eingrenzen

Bei einer Javadoc-Fehlermeldung Werkzeugpfad, Arbeitsverzeichnis, Quellpfade, Ausgabeziel und Zeichenkodierung getrennt betrachten. „Befehl nicht gefunden“ deutet auf die Werkzeugauflösung; eine vorhandene Quelldatei wird dadurch nicht fehlerhaft.

Die Skripte verwenden `JAVA_HOME`, sofern gesetzt. Unter Windows kann für die aktuelle PowerShell-Sitzung beispielsweise Folgendes helfen, wenn das JDK dort installiert ist:

```powershell
$env:JAVA_HOME = 'C:\Program Files\Java\jdk-25'
./scripts/javadoc.ps1
```

Bei `Input length = 1` sind mögliche Ursachen zunächst zu prüfen. Beim tatsächlichen Repository-Test funktionierte Javadoc auch im Benutzerpfad mit Umlaut. Erst die konkrete Fehlerursache prüfen, bevor globale Einstellungen geändert werden.

## 15. Lernzeit realistisch betrachten

Die 40 Minuten für D1 sind ein Richtwert. Bei der Bearbeitung wurde dafür fast ein halber Schultag benötigt, weil jede Methode erneut verstanden und beschrieben werden musste. Dieses Wiederholen gehört zum Lernen.

Für den nächsten Durchlauf hilft eine feste Routine: Eingabe – Verarbeitung – Ergebnis notieren, kurz dokumentieren, kompilieren und ein Beispiel ausführen. Im [Projektbericht](projektbericht.md) steht, wie sich diese Erfahrung auf das weitere Vorgehen übertragen lässt.
