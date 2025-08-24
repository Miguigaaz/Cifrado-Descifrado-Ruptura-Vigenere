import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

public class Cifrar {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);

        System.out.println("Cifrar un texto nuevo (1) \n" +
                "Cifrar de un documento existente (2)");
        int opcion = teclado.nextInt();
        teclado.nextLine();

        String texto = elegirOpcion(opcion,teclado);

        System.out.println("Escribe la clave con la que cifrar:");
        String clave = Utilidades.limpiarTexto(teclado.nextLine());

        String mcifrado = cifrado(texto, clave);

        System.out.println("La clave cifrada es:");
        for (int i = 0; i < mcifrado.length(); i++) {
            System.out.print(mcifrado.charAt(i));
            if ((i + 1) % 5 == 0) System.out.print("  ");
            if ((i + 1) % 40 == 0) System.out.println();
        }
    }

    private static String elegirOpcion(int numero, Scanner teclado){
        while (true) {
            switch (numero) {
                case 1: {
                    System.out.println("Escribe el texto a cifrar:");
                    return Utilidades.limpiarTexto(teclado.nextLine());
                }
                case 2: {
                    System.out.println("Escribe la ruta del archivo a leer:");
                    String rutaArchivo = teclado.nextLine();

                    try {
                        File archivo = new File(rutaArchivo);
                        Scanner lectorArchivo = new Scanner(archivo, "UTF-8");
                        StringBuilder contenido = new StringBuilder();

                        while (lectorArchivo.hasNextLine()) {
                            contenido.append(lectorArchivo.nextLine()).append(" ");
                        }
                        lectorArchivo.close();

                        return Utilidades.limpiarTexto(contenido.toString());
                    } catch (FileNotFoundException e) {
                        System.out.println("Archivo no encontrado. Por favor, intenta de nuevo.");
                        System.out.println("Introduce 1 para texto nuevo o 2 para archivo:");
                        numero = teclado.nextInt();
                        teclado.nextLine();
                    }
                    break;
                }
                default:
                    System.out.println("Opción no válida. Por favor, introduce 1 o 2:");
                    numero = teclado.nextInt();
                    teclado.nextLine();
            }
        }
    }

    private static String cifrado(String texto, String clave) {
        StringBuilder mCifrado = new StringBuilder(texto.length());
        int indiceClave = 0;

        for (int i = 0; i < texto.length(); i++) {
            int numTexto = Utilidades.letraANumero(String.valueOf(texto.charAt(i)));
            int numClave = Utilidades.letraANumero(String.valueOf(clave.charAt(indiceClave)));

            if (numTexto == -1)
                throw new IllegalArgumentException("Carácter inválido en texto en posición " + i + ": '" + texto.charAt(i) + "'");
            if (numClave == -1)
                throw new IllegalArgumentException("Carácter inválido en clave en posición " + indiceClave + ": '" + clave.charAt(indiceClave) + "'");

            int suma = (numTexto + numClave) % 27;
            String letraCifrada = Utilidades.numeroALetra(suma);

            if (letraCifrada == null)
                throw new IllegalStateException("Conversión inválida de número a letra para valor: " + suma);

            mCifrado.append(letraCifrada);

            indiceClave = (indiceClave + 1) % clave.length();
        }

        return mCifrado.toString();
    }
}
