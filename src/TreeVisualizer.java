import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

import java.util.Scanner;

/**
 * Aplicação principal: monta a árvore Morse, abre a janela com o desenho
 * da árvore e roda o menu de codificar/decodificar no console.
 */
public class TreeVisualizer extends Application {

    // Alfabeto Morse: letras A-Z e números 0-9
    private static final String[][] MORSE_ALPHABET = {
            {"A", ".-"},   {"B", "-..."}, {"C", "-.-."}, {"D", "-.."},
            {"E", "."},    {"F", "..-."}, {"G", "--."},  {"H", "...."},
            {"I", ".."},   {"J", ".---"}, {"K", "-.-"},  {"L", ".-.."},
            {"M", "--"},   {"N", "-."},   {"O", "---"},  {"P", ".--."},
            {"Q", "--.-"}, {"R", ".-."},  {"S", "..."},  {"T", "-"},
            {"U", "..-"},  {"V", "...-"}, {"W", ".--"},  {"X", "-..-"},
            {"Y", "-.--"}, {"Z", "--.."},
            {"0", "-----"}, {"1", ".----"}, {"2", "..---"}, {"3", "...--"},
            {"4", "....-"}, {"5", "....."}, {"6", "-...."}, {"7", "--..."},
            {"8", "---.."}, {"9", "----."}
    };

    private static final int MAX_WINDOW_WIDTH = 1280;
    private static final int MAX_WINDOW_HEIGHT = 720;

    private final MorseBST bst = new MorseBST();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Visualizador de Árvore Binária - Código Morse");

        loadAlphabet();

        Canvas canvas = createTreeCanvas();
        ScrollPane scrollPane = new ScrollPane(canvas);

        double windowWidth = Math.min(canvas.getWidth(), MAX_WINDOW_WIDTH);
        double windowHeight = Math.min(canvas.getHeight(), MAX_WINDOW_HEIGHT);

        primaryStage.setScene(new Scene(scrollPane, windowWidth, windowHeight));
        primaryStage.show();

        startConsoleMenu();
    }

    // Insere todo o alfabeto Morse na árvore
    private void loadAlphabet() {
        for (String[] entry : MORSE_ALPHABET) {
            bst.insert(entry[0].charAt(0), entry[1]);
        }
    }

    // Cria o canvas com tamanho proporcional à altura da árvore e desenha a árvore
    private Canvas createTreeCanvas() {
        int height = bst.getHeight();
        int canvasHeight = 100 + height * 100;
        int canvasWidth = (int) Math.pow(2, height) * 20;

        Canvas canvas = new Canvas(canvasWidth, canvasHeight);
        bst.drawTree(canvas);
        return canvas;
    }

    // ===================== MENU DO CONSOLE =====================

    /**
     * O menu roda em uma thread separada para não travar a janela do JavaFX
     * enquanto o programa espera o usuário digitar no console.
     */
    private void startConsoleMenu() {
        Thread consoleThread = new Thread(this::runConsoleMenu);
        consoleThread.setDaemon(true);
        consoleThread.start();
    }

    private void runConsoleMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            printMenu();
            if (!scanner.hasNextLine()) {
                break;
            }

            String option = scanner.nextLine().trim();
            switch (option) {
                case "1" -> decodeFromInput(scanner);
                case "2" -> encodeFromInput(scanner);
                case "0" -> running = false;
                default -> System.out.println("Opção inválida! Tente novamente.");
            }
        }

        System.out.println("Encerrando o programa...");
        Platform.exit();
    }

    private void printMenu() {
        System.out.println();
        System.out.println("===== CÓDIGO MORSE =====");
        System.out.println("1 - Decodificar (Morse -> texto)");
        System.out.println("2 - Codificar (texto -> Morse)");
        System.out.println("0 - Sair");
        System.out.print("Escolha uma opção: ");
    }

    private void decodeFromInput(Scanner scanner) {
        System.out.println("Digite o código Morse (letras separadas por espaço, palavras por ' / '):");
        String morseText = scanner.nextLine();
        System.out.println("Texto decodificado: " + bst.decode(morseText));
    }

    private void encodeFromInput(Scanner scanner) {
        System.out.println("Digite o texto (letras A-Z e números 0-9):");
        String text = scanner.nextLine();
        System.out.println("Código Morse: " + bst.encode(text));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
