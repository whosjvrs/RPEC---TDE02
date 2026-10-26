import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Árvore binária do Código Morse.
 * Regra de inserção: ponto (.) vai para a esquerda, traço (-) vai para a direita.
 * A raiz é um nó vazio; cada letra fica no nó ao final do caminho do seu código.
 */
public class MorseBST {
    private static final char EMPTY = ' ';
    private static final char UNKNOWN = '?';

    private final Node root;

    public MorseBST() {
        root = new Node(EMPTY);
    }

    // ===================== INSERÇÃO =====================

    /**
     * Insere uma letra percorrendo o código Morse símbolo por símbolo.
     * Se um nó do caminho ainda não existir, ele é criado vazio.
     */
    public void insert(char letter, String morseCode) {
        Node current = root;

        for (char symbol : morseCode.toCharArray()) {
            if (symbol == '.') {
                if (current.left == null) {
                    current.left = new Node(EMPTY);
                }
                current = current.left;
            } else if (symbol == '-') {
                if (current.right == null) {
                    current.right = new Node(EMPTY);
                }
                current = current.right;
            } else {
                throw new IllegalArgumentException("Símbolo inválido no código Morse: " + symbol);
            }
        }

        current.letter = Character.toUpperCase(letter);
    }

    // ===================== DECODIFICAÇÃO (Morse -> texto) =====================

    /**
     * Decodifica um texto em Morse.
     * Letras separadas por espaço e palavras separadas por " / ".
     * Exemplo: "... --- ... / ..--- -----" -> "SOS 20"
     */
    public String decode(String morseText) {
        StringBuilder result = new StringBuilder();
        String[] words = morseText.trim().split("\\s*/\\s*");

        for (int i = 0; i < words.length; i++) {
            if (i > 0) {
                result.append(' ');
            }
            for (String code : words[i].trim().split("\\s+")) {
                if (!code.isEmpty()) {
                    result.append(decodeLetter(code));
                }
            }
        }

        return result.toString();
    }

    /**
     * Decodifica uma única letra: desce na árvore seguindo os pontos e traços.
     * Retorna '?' se o código não levar a nenhuma letra.
     */
    private char decodeLetter(String morseCode) {
        Node current = root;

        for (char symbol : morseCode.toCharArray()) {
            if (symbol == '.') {
                current = current.left;
            } else if (symbol == '-') {
                current = current.right;
            } else {
                return UNKNOWN;
            }

            if (current == null) {
                return UNKNOWN;
            }
        }

        return current.letter == EMPTY ? UNKNOWN : current.letter;
    }

    // ===================== CODIFICAÇÃO (texto -> Morse) =====================

    /**
     * Codifica um texto em Morse.
     * Letras separadas por espaço e palavras separadas por " / ".
     * Exemplo: "SOS" -> "... --- ..."
     */
    public String encode(String text) {
        StringBuilder result = new StringBuilder();
        String[] words = text.trim().toUpperCase().split("\\s+");

        for (int i = 0; i < words.length; i++) {
            if (i > 0) {
                result.append(" / ");
            }
            char[] letters = words[i].toCharArray();
            for (int j = 0; j < letters.length; j++) {
                if (j > 0) {
                    result.append(' ');
                }
                result.append(encodeLetter(letters[j]));
            }
        }

        return result.toString();
    }

    /**
     * Codifica uma única letra buscando seu caminho na árvore.
     * Retorna "?" se a letra não existir na árvore.
     */
    private String encodeLetter(char letter) {
        String code = findMorseCode(root, letter, "");
        return code == null ? String.valueOf(UNKNOWN) : code;
    }

    /**
     * Busca recursiva pela letra. O caminho percorrido até ela é o próprio
     * código Morse: cada passo à esquerda adiciona '.', à direita adiciona '-'.
     */
    private String findMorseCode(Node node, char letter, String path) {
        if (node == null) {
            return null;
        }
        if (node.letter == letter) {
            return path;
        }

        String leftResult = findMorseCode(node.left, letter, path + ".");
        if (leftResult != null) {
            return leftResult;
        }
        return findMorseCode(node.right, letter, path + "-");
    }

    // ===================== ALTURA E DESENHO (código base) =====================

    // Calcula a altura da árvore
    public int getHeight() {
        return getHeight(root);
    }

    private int getHeight(Node node) {
        if (node == null) {
            return 0;
        }
        return 1 + Math.max(getHeight(node.left), getHeight(node.right));
    }

    public void drawTree(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);

        // Começa o desenho da árvore na raiz
        drawNode(gc, root, canvas.getWidth() / 2, 40, canvas.getWidth() / 4, 1);
    }

    private void drawNode(GraphicsContext gc, Node node, double x, double y, double xOffset, int level) {
        if (node == null) {
            return;
        }

        // Desenha um círculo ao redor do nó
        gc.setStroke(Color.BLACK);
        gc.strokeOval(x - 15, y - 15, 30, 30); // Desenha o círculo com raio 15

        // Desenha a letra dentro do círculo
        gc.strokeText(String.valueOf(node.letter), x - 5, y + 5);

        // Desenho das linhas para os nós filhos
        if (node.left != null) {
            double newX = x - xOffset;
            double newY = y + 120;
            gc.strokeLine(x, y + 15, newX, newY - 15); // Linha entre o nó atual e o filho à esquerda
            drawNode(gc, node.left, newX, newY, xOffset / 2, level + 1);
        }

        if (node.right != null) {
            double newX = x + xOffset;
            double newY = y + 120;
            gc.strokeLine(x, y + 15, newX, newY - 15); // Linha entre o nó atual e o filho à direita
            drawNode(gc, node.right, newX, newY, xOffset / 2, level + 1);
        }
    }
}
