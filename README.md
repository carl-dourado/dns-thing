# dns-thing

Coisa pequena para cutucar DNS.

Serve para consultar um dominio, ver A/AAAA/CNAME e comparar resolvedores quando `dig` estiver instalado. Quando nao tiver `dig`, ele cai para a resolucao normal do Python.

## uso

```bash
python dns_thing.py github.com
```

Comparando resolvedores:

```bash
python dns_thing.py github.com --resolver 1.1.1.1 --resolver 8.8.8.8
```

Mudando tipo:

```bash
python dns_thing.py github.com --type A --type AAAA
```

## notas

- sem dependencia externa
- `dig` e opcional
- ainda e mais lab do que ferramenta

