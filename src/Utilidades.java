import java.text.Normalizer;

public class Utilidades {
    public static String limpiarTexto(String texto) {
        texto = texto.replace("ñ", "~").replace("Ñ", "~");
        texto = Normalizer.normalize(texto, Normalizer.Form.NFD);
        texto = texto.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        texto = texto.replaceAll("[^A-Za-z~]", "");
        texto = texto.replace("~", "Ñ");
        return texto.toUpperCase();
    }
    public static String numeroALetra (int numero){
        return switch (numero) {
            case 0 -> "A";
            case 1 -> "B";
            case 2 -> "C";
            case 3 -> "D";
            case 4 -> "E";
            case 5 -> "F";
            case 6 -> "G";
            case 7 -> "H";
            case 8 -> "I";
            case 9 -> "J";
            case 10 -> "K";
            case 11 -> "L";
            case 12 -> "M";
            case 13 -> "N";
            case 14 -> "Ñ";
            case 15 -> "O";
            case 16 -> "P";
            case 17 -> "Q";
            case 18 -> "R";
            case 19 -> "S";
            case 20 -> "T";
            case 21 -> "U";
            case 22 -> "V";
            case 23 -> "W";
            case 24 -> "X";
            case 25 -> "Y";
            case 26 -> "Z";
            default -> null;
        };
    }

    public static int letraANumero(String letra) {
        return switch (letra.toUpperCase()) {
            case "A" -> 0;
            case "B" -> 1;
            case "C" -> 2;
            case "D" -> 3;
            case "E" -> 4;
            case "F" -> 5;
            case "G" -> 6;
            case "H" -> 7;
            case "I" -> 8;
            case "J" -> 9;
            case "K" -> 10;
            case "L" -> 11;
            case "M" -> 12;
            case "N" -> 13;
            case "Ñ" -> 14;
            case "O" -> 15;
            case "P" -> 16;
            case "Q" -> 17;
            case "R" -> 18;
            case "S" -> 19;
            case "T" -> 20;
            case "U" -> 21;
            case "V" -> 22;
            case "W" -> 23;
            case "X" -> 24;
            case "Y" -> 25;
            case "Z" -> 26;
            default -> -1;
        };
    }
}
