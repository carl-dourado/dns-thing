# dns-thing

Um lab pequeno em Java para consultar DNS pelo terminal.

A ideia aqui e treinar Java basico/intermediario com uma coisa real: passar um dominio, tentar resolver os IPs e tratar erro sem jogar tudo em framework.

## o que tem

- Java puro
- `main` simples
- argumentos pelo terminal
- `InetAddress` para resolver dominio
- `try/catch` para erro de DNS
- saida em texto ou JSON

## uso

Compilar:

```bash
javac src/main/java/DnsThing.java
```

Rodar:

```bash
java -cp src/main/java DnsThing github.com
```

Saida em JSON:

```bash
java -cp src/main/java DnsThing github.com --json
```

## por que Java aqui

Eu nao quis usar Spring nem biblioteca externa porque a ideia e entender o basico primeiro.

O foco foi:

- ler argumentos
- separar metodos pequenos
- usar classe simples para guardar resultado
- entender excecao quando o dominio nao resolve
- imprimir uma resposta que da para ler no terminal

## limites

- nao compara resolvedores ainda
- nao separa A/AAAA manualmente, so mostra os IPs retornados pelo sistema
- nao e clone do `dig`
- e mais estudo do que ferramenta pronta

## coisas para melhorar depois

- validar melhor dominio e argumentos
- separar o codigo em package quando o projeto crescer
- adicionar testes pequenos para JSON e erro de DNS
- comparar resultado entre resolvedor do sistema e um resolvedor publico

## anotacoes de aprendizado

Esse projeto ficou propositalmente sem Maven/Gradle para eu praticar Java direto pelo terminal. Se ele crescer, faz sentido colocar build tool e testes, mas por enquanto a simplicidade ajuda a enxergar o basico.
