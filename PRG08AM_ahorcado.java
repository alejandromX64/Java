import java.util.Random;
import java.util.Scanner;
public class PRG08AM_ahorcado {
    private static final String[] PALABRAS = {
        "programacion", "java", "ordenador", "teclado", "pantalla",
        "algoritmo", "variable", "funcion", "compilador", "servidor"
    };
    private static final int MAX_INTENTOS = 6;
    public static void main(String[] args) {
        //Declaraciones
        Scanner sc = new Scanner(System.in);
        boolean jugarOtra;
        //Inicio
        do {
            jugar(sc);
            System.out.print("\n¿Quieres jugar otra vez? (s/n): ");
            jugarOtra = sc.nextLine().trim().equalsIgnoreCase("s");
        } while (jugarOtra);

        System.out.println("¡Gracias por jugar!");
        sc.close();
    }
    // Controla una partida completa
    private static void jugar(Scanner sc) {
        String palabra = elegirPalabra();
        char[] progreso = new char[palabra.length()];
        java.util.Arrays.fill(progreso, '_');
        StringBuilder usadas = new StringBuilder();
        int fallos = 0;
        System.out.println("\n=== JUEGO DEL AHORCADO ===");
        while (fallos < MAX_INTENTOS && !haGanado(progreso)) {
            dibujarAhorcado(fallos);
            mostrarEstado(progreso, usadas.toString(), MAX_INTENTOS - fallos);

            char letra = pedirLetra(sc, usadas.toString());
            usadas.append(letra).append(' ');

            if (palabra.indexOf(letra) >= 0) {
                revelarLetra(palabra, progreso, letra);
                System.out.println("¡Bien! La letra '" + letra + "' está en la palabra.");
            } else {
                fallos++;
                System.out.println("Lo siento, la letra '" + letra + "' no está.");
            }
        }
        dibujarAhorcado(fallos);
        if (haGanado(progreso)) {
            System.out.println("¡Ganaste! La palabra era: " + palabra);
        } else {
            System.out.println("Has perdido. La palabra era: " + palabra);
        }
    }
    // Elige una palabra al azar
    private static String elegirPalabra() {
        return PALABRAS[new Random().nextInt(PALABRAS.length)];
    }
    // Pide una letra válida que no se haya usado antes
    private static char pedirLetra(Scanner sc, String usadas) {
        while (true) {
            System.out.print("Introduce una letra: ");
            String entrada = sc.nextLine().trim().toLowerCase();

            if (entrada.length() != 1 || !Character.isLetter(entrada.charAt(0))) {
                System.out.println("Escribe solo una letra.");
            } else if (usadas.indexOf(entrada.charAt(0)) >= 0) {
                System.out.println("Ya usaste esa letra.");
            } else {
                return entrada.charAt(0);
            }
        }
    }
    // Descubre todas las apariciones de la letra en el progreso
    private static void revelarLetra(String palabra, char[] progreso, char letra) {
        for (int i = 0; i < palabra.length(); i++) {
            if (palabra.charAt(i) == letra) {
                progreso[i] = letra;
            }
        }
    }
    // Comprueba si ya no quedan huecos
    private static boolean haGanado(char[] progreso) {
        for (char c : progreso) {
            if (c == '_') return false;
        }
        return true;
    }
    // Muestra palabra, letras usadas e intentos restantes
    private static void mostrarEstado(char[] progreso, String usadas, int intentos) {
        StringBuilder sb = new StringBuilder();
        for (char c : progreso) sb.append(c).append(' ');
        System.out.println("\nPalabra: " + sb);
        System.out.println("Letras usadas: " + usadas);
        System.out.println("Intentos restantes: " + intentos);
    }
    // Dibuja el muñeco según el número de fallos
    private static void dibujarAhorcado(int fallos) {
        String[] etapas = {
            "  +---+\n  |   |\n      |\n      |\n      |\n      |\n=========",
            "  +---+\n  |   |\n  O   |\n      |\n      |\n      |\n=========",
            "  +---+\n  |   |\n  O   |\n  |   |\n      |\n      |\n=========",
            "  +---+\n  |   |\n  O   |\n /|   |\n      |\n      |\n=========",
            "  +---+\n  |   |\n  O   |\n /|\\  |\n      |\n      |\n=========",
            "  +---+\n  |   |\n  O   |\n /|\\  |\n /    |\n      |\n=========",
            "  +---+\n  |   |\n  O   |\n /|\\  |\n / \\  |\n      |\n========="
        };
        System.out.println(etapas[fallos]);
    }//main
}//class
