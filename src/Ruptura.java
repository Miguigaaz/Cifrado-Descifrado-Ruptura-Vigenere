import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Ruptura {
    public static void iniciar(Scanner teclado) {
        int longitudMinima = 4;
        String textoCifrado = elegirOpcion(teclado);


        HashMap<String, List<Integer>> listaSubpartes = contarRepeticiones(textoCifrado,longitudMinima);
        List<Integer> listaDistancias = listaDeDistancias(listaSubpartes);
        int longitudClave = mcd(listaDistancias);
        StringBuilder[] subTextos = asignacionSubstrings(textoCifrado,longitudClave);

        int [][] frecuencias = contarLetrasEnSubtextos(subTextos);
        StringBuilder posibleClave = encontrarDesplazamientoAEO(frecuencias);
        System.out.printf("La clave más probable es: %s\n", posibleClave);
    }

    private static String elegirOpcion(Scanner teclado){
        while (true) {
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
            }
        }
    }

    public static int mcd(List<Integer> lista) {
        if (lista == null || lista.isEmpty()) {
            throw new IllegalArgumentException("La lista no puede estar vacía");
        }
        int resultado = Math.abs(lista.getFirst());
        for (int i = 1; i < lista.size(); i++) {
            resultado = mcdDosNumeros(resultado, lista.get(i));
        }
        return resultado;
    }

    private static int mcdDosNumeros(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    private static HashMap<String,List<Integer>> contarRepeticiones (String textoCifrado, int longitudMinima){
        HashMap<String, List<Integer>> listaSubpartes = new HashMap<>();
        for (int i = 0; i <= textoCifrado.length() - longitudMinima; i++) {
            for (int len = longitudMinima; len <= 5 && i + len <= textoCifrado.length(); len++) {
                String sub = textoCifrado.substring(i, i + len);

                listaSubpartes.putIfAbsent(sub, new ArrayList<>());
                listaSubpartes.get(sub).add(i);
            }
        }
        return listaSubpartes;
    }

    private static List<Integer> listaDeDistancias (HashMap<String, List<Integer>> listaSubpartes){
        List<Integer> listaDistancias = new ArrayList<>();

        for (Map.Entry<String, List<Integer>> entry : listaSubpartes.entrySet()) {
            List<Integer> posiciones = entry.getValue();
            if (posiciones.size() > 1) {
                for (int j = 1; j < posiciones.size(); j++) {
                    int distancia = posiciones.get(j) - posiciones.get(j - 1);
                    listaDistancias.add(distancia);
                }
            }
        }
        return listaDistancias;
    }

    private static StringBuilder[] asignacionSubstrings (String textoCifrado, int longitudClave){
        StringBuilder[] subTextos = new StringBuilder[longitudClave];
        for (int i= 0; i<subTextos.length; i++){
            subTextos[i]= new StringBuilder();
        }

        for (int i = 0; i<textoCifrado.length(); i++){
            subTextos[i%longitudClave].append(textoCifrado.charAt(i));
        }
        return subTextos;
    }

    private static int[][] contarLetrasEnSubtextos (StringBuilder[] subtextos){
            int[][] matriz = new int[subtextos.length][27];

            for (int i = 0; i < subtextos.length; i++) {
                for (int j = 0; j < subtextos[i].length(); j++) {
                    String letra = String.valueOf(subtextos[i].charAt(j));
                    int valorLetra = Utilidades.letraANumero(letra);
                    matriz[i][valorLetra]++;
                }
            }
            return matriz;
    }

    public static StringBuilder encontrarDesplazamientoAEO(int[][] frecuencias) {
        StringBuilder clave = new StringBuilder();
        int[] letrasAEONum = {0, 4, 15};

        for (int[] frecuencia : frecuencias) {
            int mejorDesplazamiento = 0;
            int maxSuma = -1;

            for (int i = 0; i < 26; i++) {
                int suma = 0;
                for (int letraAEONum : letrasAEONum) {
                    int indiceLetraRotada = (letraAEONum + i) % 26;
                    suma += frecuencia[indiceLetraRotada];
                }
                if (suma > maxSuma) {
                    maxSuma = suma;
                    mejorDesplazamiento = i;
                }
            }
            clave.append(Utilidades.numeroALetra(mejorDesplazamiento));
        }
        return clave;
    }

}
