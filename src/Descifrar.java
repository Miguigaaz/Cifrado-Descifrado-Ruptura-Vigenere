import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

public class Descifrar {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);

        System.out.println("Descifrar un texto nuevo (1) \n" +
                "Descifrar de un documento existente (2)");
        int opcion = teclado.nextInt();
        teclado.nextLine();

        String textoCifrado = elegirOpcion(opcion,teclado);

        System.out.println("Escribe la clave con la que descifrar:");
        String clave = Utilidades.limpiarTexto(teclado.nextLine());

        String mOriginal = descifrado(textoCifrado,clave);

        System.out.println("La clave descifrada es:");
        for (int i = 0; i < mOriginal.length(); i++) {
            System.out.print(mOriginal.charAt(i));
            if ((i + 1) % 40 == 0) System.out.println();
        }
    }

    private static String elegirOpcion(int numero, Scanner teclado){
        while (true) {
            switch (numero) {
                case 1: {
                    System.out.println("Escribe el texto a desifrar:");
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

    private static String descifrado(String textoCifrado, String clave) {
        StringBuilder mOriginal = new StringBuilder(textoCifrado.length());
        int indiceClave = 0;

        for (int i = 0; i < textoCifrado.length(); i++) {
            int numCifrado = Utilidades.letraANumero(String.valueOf(textoCifrado.charAt(i)));
            int numClave = Utilidades.letraANumero(String.valueOf(clave.charAt(indiceClave)));

            if (numCifrado == -1)
                throw new IllegalArgumentException("Carácter inválido en texto cifrado en posición " + i + ": '" + textoCifrado.charAt(i) + "'");
            if (numClave == -1)
                throw new IllegalArgumentException("Carácter inválido en clave en posición " + indiceClave + ": '" + clave.charAt(indiceClave) + "'");

            int resta = numCifrado - numClave;
            if (resta < 0) resta += 27;

            String letraOriginal = Utilidades.numeroALetra(resta);

            if (letraOriginal == null || letraOriginal.isEmpty())
                throw new IllegalStateException("Conversión inválida de número a letra para valor: " + resta);

            mOriginal.append(letraOriginal);

            indiceClave = (indiceClave + 1) % clave.length();
        }

        return mOriginal.toString();
    }

}
