#!/usr/bin/env python3
import argparse
import json
import shutil
import socket
import subprocess
import time


DEFAULT_TYPES = ["A", "AAAA", "CNAME"]


def query_with_dig(domain, record_type, resolver=None, timeout=3):
    command = ["dig"]
    if resolver:
        command.append(f"@{resolver}")
    command.extend([domain, record_type, "+time=2", "+tries=1", "+short"])

    started = time.perf_counter()
    result = subprocess.run(command, capture_output=True, text=True, check=False, timeout=timeout)
    answers = [line.strip() for line in result.stdout.splitlines() if line.strip()]

    return {
        "resolver": resolver or "sistema",
        "type": record_type,
        "ok": result.returncode == 0 and bool(answers),
        "answers": answers,
        "ms": round((time.perf_counter() - started) * 1000, 2),
        "error": result.stderr.strip() or None,
    }


def query_with_socket(domain):
    started = time.perf_counter()
    try:
        records = socket.getaddrinfo(domain, None)
        answers = sorted({item[4][0] for item in records})
        ok = bool(answers)
        error = None
    except OSError as exc:
        answers = []
        ok = False
        error = str(exc)

    return {
        "resolver": "sistema",
        "type": "A/AAAA",
        "ok": ok,
        "answers": answers,
        "ms": round((time.perf_counter() - started) * 1000, 2),
        "error": error,
    }


def main():
    parser = argparse.ArgumentParser(description="consulta DNS pequena")
    parser.add_argument("domain")
    parser.add_argument("--resolver", action="append", help="resolvedor para comparar")
    parser.add_argument("--type", action="append", choices=DEFAULT_TYPES, default=None)
    parser.add_argument("--json", action="store_true")
    args = parser.parse_args()

    record_types = args.type or DEFAULT_TYPES
    resolvers = args.resolver or [None]

    if shutil.which("dig"):
        rows = [
            query_with_dig(args.domain, record_type, resolver)
            for resolver in resolvers
            for record_type in record_types
        ]
    else:
        rows = [query_with_socket(args.domain)]

    if args.json:
        print(json.dumps(rows, indent=2, ensure_ascii=False))
        return

    for row in rows:
        print(f"{row['resolver']} {row['type']} {row['ms']}ms")
        if row["answers"]:
            for answer in row["answers"]:
                print(f"  {answer}")
        else:
            print(f"  sem resposta ({row['error'] or 'vazio'})")


if __name__ == "__main__":
    main()

