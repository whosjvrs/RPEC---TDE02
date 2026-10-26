/**
 * Nó da árvore binária do Código Morse.
 * Cada nó guarda uma letra (ou ' ' quando é um nó intermediário vazio)
 * e referências para os filhos: esquerda = ponto (.), direita = traço (-).
 */
public class Node {
    char letter;
    Node left;
    Node right;

    public Node(char letter) {
        this.letter = letter;
        this.left = null;
        this.right = null;
    }
}
