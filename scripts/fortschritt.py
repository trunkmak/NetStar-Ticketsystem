#!/usr/bin/env python3
"""Render the README progress card from the task table and optional GitHub issues."""

import argparse
import json
import math
import re
from collections import Counter
from html import escape
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
EXPECTED = {f'{prefix}{n}' for prefix, count in [('A', 6), ('B', 7), ('Z', 4), ('D', 4), ('E', 4), ('T', 2)] for n in range(1, count + 1)}
COLORS = {'done': '#29d7ce', 'partial': '#ffbd66', 'open': '#809ab0', 'blocked': '#f18baa'}


def load_tasks(path):
    tasks = {}
    for line in path.read_text(encoding='utf-8').splitlines():
        cells = [cell.strip() for cell in line.split('|')]
        if len(cells) < 5 or not re.fullmatch(r'[ABZDET]\d+', cells[1]):
            continue
        task, status = cells[1], cells[3]
        if status.startswith(('Erledigt', 'Durch Z1 erweitert')):
            state = 'done'
        elif status.startswith('Teilweise'):
            state = 'partial'
        elif status.startswith('Blockiert'):
            state = 'blocked'
        elif status == 'Offen':
            state = 'open'
        else:
            raise ValueError(f'Unknown task status: {task}: {status}')
        if task in tasks:
            raise ValueError(f'Duplicate task: {task}')
        tasks[task] = state
    if tasks.keys() != EXPECTED:
        raise ValueError('The status table must contain the 27 documented tasks exactly once.')
    return tasks


def merge_issues(tasks, data):
    """Issue state overrides completion; open partial/blocked tasks keep their detail."""
    if not isinstance(data, list):
        raise ValueError('Expected a JSON list of issues or paginated issue lists.')
    issues = [issue for page in data for issue in (page if isinstance(page, list) else [page])]
    seen = set()
    for issue in issues:
        if 'pull_request' in issue:
            continue
        match = re.match(r'^([ABZDET]\d+)\s*:', issue.get('title', ''))
        if not match or match[1] not in tasks:
            continue
        task = match[1]
        if task in seen:
            raise ValueError(f'Multiple issues refer to {task}; clarify the task assignment.')
        seen.add(task)
        labels = {label['name'] if isinstance(label, dict) else label for label in issue.get('labels', [])}
        if issue['state'] == 'closed':
            tasks[task] = 'done'
        elif 'blocked' in labels:
            tasks[task] = 'blocked'
        elif tasks[task] not in {'partial', 'blocked'}:
            tasks[task] = 'open'
    return tasks


def label(x, y, value, size=20, color='#f0f7fc', weight=400, extra=''):
    return f'<text x="{x}" y="{y}" font-size="{size}" fill="{color}" font-weight="{weight}" {extra}>{escape(str(value))}</text>'


def render(tasks, live=False):
    counts = Counter(tasks.values())
    total, done = len(tasks), counts['done']
    percent = round(100 * done / total)
    summary = f'{done} von {total} Aufgaben erledigt ({percent} Prozent), {counts["partial"]} teilweise, {counts["open"]} offen, {counts["blocked"]} blockiert.'
    circumference = 2 * math.pi * 88
    parts = [
        '<svg xmlns="http://www.w3.org/2000/svg" width="1440" height="420" viewBox="0 0 1440 420" role="img" aria-labelledby="title desc">',
        '<title id="title">NetStar – Projektfortschritt</title>',
        f'<desc id="desc">{escape(summary)} Nur vollständig erledigte Aufgaben zählen zum Prozentwert.</desc>',
        '<rect width="1440" height="420" rx="24" fill="#10273d"/>',
        '<g font-family="Segoe UI, Arial, sans-serif">',
        label(40, 51, 'Projektfortschritt', 32, weight=700),
        label(40, 83, 'Der aktuelle Stand der 27 Lernaufgaben', 18, '#a5bdce'),
        '<rect x="1205" y="32" width="193" height="38" rx="19" fill="#29d7ce" fill-opacity=".1" stroke="#29d7ce" stroke-opacity=".35"/>',
        label(1301, 57, 'GITHUB SYNC' if live else 'AUFGABENSTATUS', 13, COLORS['done'], 700, 'text-anchor="middle" letter-spacing="1.5"'),
        '<circle cx="144" cy="231" r="88" fill="none" stroke="#28445c" stroke-width="15"/>',
        f'<circle cx="144" cy="231" r="88" fill="none" stroke="{COLORS["done"]}" stroke-width="15" stroke-linecap="round" stroke-dasharray="{circumference * done / total:.3f} {circumference:.3f}" transform="rotate(-90 144 231)"/>',
        label(144, 236, f'{percent} %', 48, '#fff', 750, 'text-anchor="middle"'),
        label(144, 269, f'{done} / {total} fertig', 18, '#a5bdce', extra='text-anchor="middle"'),
    ]
    for index, (state, name) in enumerate([('done', 'erledigt'), ('partial', 'teilweise'), ('open', 'offen'), ('blocked', 'blockiert')]):
        x = 320 + index * 270
        parts.append(f'<circle cx="{x + 7}" cy="136" r="6" fill="{COLORS[state]}"/>')
        parts.append(label(x + 24, 144, f'{counts[state]} {name}', 21, '#dce9f2', 600))
    parts.append('<rect x="320" y="171" width="1078" height="10" rx="5" fill="#28445c"/>')
    offset = 320
    for state in ['done', 'partial', 'open', 'blocked']:
        width = 1078 * counts[state] / total
        if width:
            parts.append(f'<rect x="{offset:.3f}" y="171" width="{width:.3f}" height="10" fill="{COLORS[state]}"/>')
        offset += width
    for index, (name, prefixes, detail) in enumerate([
        ('Basis + Zusatz', 'ABZ', 'A1–B7 und Z1–Z4'),
        ('Dokumentation', 'D', 'D1–D4 · Meilenstein 1'),
        ('Erweiterungen', 'E', 'E1–E4 · Meilenstein 2'),
        ('Testen', 'T', 'T1–T2 · Meilenstein 3'),
    ]):
        x = 320 + index * 274
        group = [state for task, state in tasks.items() if task[0] in prefixes]
        completed, number = group.count('done'), len(group)
        parts.extend([
            f'<rect x="{x}" y="210" width="256" height="124" rx="15" fill="#17354c" stroke="#31516a"/>',
            label(x + 18, 240, name, 20, '#eff7fc', 600),
            label(x + 18, 276, f'{completed} / {number}', 28, COLORS['done'], 700),
            label(x + 18, 309, detail, 14, '#a5bdce'),
        ])
    source = 'Basis: Aufgabenstatus · Aufgaben mit Issue: GitHub-Status' if live else 'Quelle: doku/aufgabenstatus.md'
    parts.extend([
        label(40, 372, source, 17, '#b6cbda'),
        label(40, 400, 'Teilweise erledigte Aufgaben zählen noch nicht als fertig. Alle 27 Aufgaben sind gleich gewichtet.', 16, '#91aec3'),
        '</g></svg>',
    ])
    return '\n'.join(parts) + '\n'


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--status', type=Path, default=ROOT / 'doku/aufgabenstatus.md')
    parser.add_argument('--issues-json', type=Path)
    parser.add_argument('--output', type=Path, default=ROOT / 'assets/fortschritt.svg')
    args = parser.parse_args()
    tasks = load_tasks(args.status)
    if args.issues_json:
        tasks = merge_issues(tasks, json.loads(args.issues_json.read_text(encoding='utf-8-sig')))
    args.output.parent.mkdir(parents=True, exist_ok=True)
    args.output.write_text(render(tasks, live=args.issues_json is not None), encoding='utf-8')
    counts = Counter(tasks.values())
    print(f'Fortschritt: {counts["done"]}/{len(tasks)} erledigt; {counts["partial"]} teilweise; {counts["open"]} offen; {counts["blocked"]} blockiert')


if __name__ == '__main__':
    main()
