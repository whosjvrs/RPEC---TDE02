# TDE 2 – Código Morse com Árvore Binária

Trabalho de **Resolução de Problemas Estruturados em Computação** (PUCPR – Prof. Lisiane Reips).

O programa monta uma árvore binária com o alfabeto Morse (letras A–Z e números 0–9), mostra a árvore em uma janela JavaFX e permite **codificar** e **decodificar** mensagens pelo console.

## Como a árvore funciona

- A raiz é um nó vazio.
- **Ponto (`.`) → esquerda** e **traço (`-`) → direita**.
- Cada caractere fica no nó ao final do caminho do seu código. Exemplo: `A = .-` → esquerda, depois direita.
- Nós intermediários que não correspondem a nenhum caractere ficam vazios.

## Estrutura

```
TDE02/
├── src/
│   ├── Node.java            → nó da árvore (letra, filho esquerdo e filho direito)
│   ├── MorseBST.java        → inserção, codificação, decodificação, altura e desenho
│   └── TreeVisualizer.java  → carrega o alfabeto, abre a janela e roda o menu do console
├── README.md
└── .gitignore
```

## Como usar

Ao executar, abre uma janela com a árvore e aparece um menu no console:

```
===== CÓDIGO MORSE =====
1 - Decodificar (Morse -> texto)
2 - Codificar (texto -> Morse)
0 - Sair
```

Formato do Morse: letras separadas por **espaço** e palavras separadas por **` / `**.

```
Decodificar: ... --- ... / ..--- -----  ->  SOS 20
Codificar:   ola mundo                  ->  --- .-.. .- / -- ..- -. -.. ---
```

Caracteres desconhecidos aparecem como `?`.

## Como executar

Requisitos: **JDK 17+** e o **JavaFX SDK** ([download](https://gluonhq.com/products/javafx/)).

### Pelo terminal

```bash
# Substitua pelo caminho da pasta lib do JavaFX SDK
export PATH_TO_FX=/caminho/para/javafx-sdk/lib     # Windows: set PATH_TO_FX=C:\caminho\javafx-sdk\lib

# Rodar na pasta raiz do projeto (TDE02)
javac --module-path $PATH_TO_FX --add-modules javafx.controls -d out src/*.java
java  --module-path $PATH_TO_FX --add-modules javafx.controls -cp out TreeVisualizer
```

### Pelo IntelliJ

1. *File → Project Structure → Libraries → +* e adicione a pasta `lib` do JavaFX SDK.
2. Em *Run → Edit Configurations → VM options*, coloque:
   `--module-path "CAMINHO/javafx-sdk/lib" --add-modules javafx.controls`
3. Execute a classe `TreeVisualizer`.

