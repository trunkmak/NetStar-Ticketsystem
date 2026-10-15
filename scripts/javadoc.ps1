$ErrorActionPreference = 'Stop'
$projectRoot = Split-Path $PSScriptRoot -Parent
Push-Location -LiteralPath $projectRoot
try {
    $generator = if ($env:JAVA_HOME) { Join-Path $env:JAVA_HOME 'bin/javadoc.exe' } else { 'javadoc' }
    & $generator -encoding UTF-8 -docencoding UTF-8 -charset UTF-8 -d doku/api -sourcepath src src/TicketSystem.java src/Auswertung.java
    if ($LASTEXITCODE -ne 0) { throw 'Javadoc-Erzeugung fehlgeschlagen.' }
} finally {
    Pop-Location
}
