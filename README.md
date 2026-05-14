# dns-thing

Esse repo nasceu para treinar Java com uma coisa pequena e real: resolver DNS pelo terminal.

Eu nao quis colocar Spring, Maven ou biblioteca externa aqui. A ideia era fazer no basico mesmo: receber um dominio, tentar resolver, tratar erro e imprimir uma resposta que de para usar.

## o que tem aqui

- Java puro
- `main` simples
- leitura de argumentos pelo terminal
- resolucao de DNS com `InetAddress`
- tratamento quando o dominio nao resolve
- saida em texto
- saida em JSON com `--json`

## compilando

```bash
javac src/main/java/DnsThing.java
```

## rodando

```bash
java -cp src/main/java DnsThing github.com
```

Saida em JSON:

```bash
java -cp src/main/java DnsThing github.com --json
```

Ajuda:

```bash
java -cp src/main/java DnsThing --help
```

## o que eu treinei

- estrutura basica de um programa Java
- separacao em metodos pequenos
- classe simples para guardar resultado
- uso de `InetAddress.getAllByName`
- `try/catch` para erro de DNS
- geracao manual de JSON pequeno

## o que falta

- validar melhor dominio e argumentos
- separar o codigo em package quando o projeto crescer
- adicionar testes pequenos para JSON e erro de DNS
- comparar resultado entre resolvedor do sistema e um resolvedor publico
- separar consulta A e AAAA manualmente
- adicionar timeout configuravel
- salvar resultado em arquivo

## nota

Nao e um clone do `dig` ou `nslookup`. Ficou propositalmente sem Maven/Gradle para eu praticar Java direto pelo terminal. Se ele crescer, faz sentido colocar build tool e testes.
