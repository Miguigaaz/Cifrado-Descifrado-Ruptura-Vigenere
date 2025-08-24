import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        boolean salir = false;

        do {
            System.out.println("""
                Elige una opción:
                1. Cifrar
                2. Descifrar
                3. Romper cifrado
                4. Salir""");

            try {
                int eleccion = teclado.nextInt();
                teclado.nextLine();

                switch (eleccion){
                    case 1:
                        Cifrar.iniciar(teclado);
                        break;
                    case 2:
                        Descifrar.iniciar(teclado);
                        break;
                    case 3:
                        Ruptura.iniciar(teclado);
                        break;
                    case 4:
                        System.out.println("Saliendo");
                        salir = true;
                        break;
                    default:
                        System.out.println("Error, ingresa un numero valido");
                }

            } catch (InputMismatchException exception){
                System.out.println("Error, ingresa un numero valido");
                teclado.nextLine();
            }
            System.out.println("\n");
        } while (!salir);

        teclado.close();
    }
}
