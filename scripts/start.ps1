$ErrorActionPreference = 'Stop'
$projectRoot = Split-Path $PSScriptRoot -Parent
Push-Location -LiteralPath $projectRoot
try {
    $compiler = if ($env:JAVA_HOME) { Join-Path $env:JAVA_HOME 'bin/javac.exe' } else { 'javac' }
    $runtime = if ($env:JAVA_HOME) { Join-Path $env:JAVA_HOME 'bin/java.exe' } else { 'java' }
    $sources = @(Get-ChildItem -LiteralPath 'src' -Filter '*.java' | ForEach-Object { $_.FullName })
    & $compiler -encoding UTF-8 -d build/classes @sources
    if ($LASTEXITCODE -ne 0) { throw 'Kompilieren fehlgeschlagen.' }
    & $runtime -cp build/classes Main
    if ($LASTEXITCODE -ne 0) { throw 'Programm wurde mit einem Fehler beendet.' }
} finally {
    Pop-Location
}
