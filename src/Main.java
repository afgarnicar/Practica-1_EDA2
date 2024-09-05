import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    //Metodo para convertir las palabras en la sumatoria de sus caracteres con codigo ascii
    public static List<Integer> conversorASCII(List<String> listaStrings) {
        List<Integer> listaAscii = new ArrayList<>();

        for (String string : listaStrings) {
            int sumaAscii = 0;
            for (char c : string.toCharArray()) {
                sumaAscii += (int) c;
            }
            listaAscii.add(sumaAscii);
        }

        return listaAscii;
    }

    public static void bubbleSort(String[] arr, List<Integer> asciiValues) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (asciiValues.get(j) > asciiValues.get(j + 1)) {
                    String tempStr = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tempStr;

                    int tempVal = asciiValues.get(j);
                    asciiValues.set(j, asciiValues.get(j + 1));
                    asciiValues.set(j + 1, tempVal);
                }
            }
        }
    }

    public static void bucketSort(String[] array, List<Integer> asciiValues) {
        int maxVal = asciiValues.get(0);
        for (int i = 1; i < asciiValues.size(); i++) {
            if (maxVal < asciiValues.get(i)) {
                maxVal = asciiValues.get(i);
            }
        }
        List<List<String>> bucketArray = new ArrayList<>(maxVal + 1);
        for (int i = 0; i <= maxVal; i++) {
            bucketArray.add(new ArrayList<>());
        }
        for (int i = 0; i < array.length; i++) {
            bucketArray.get(asciiValues.get(i)).add(array[i]);
        }
        int index = 0;
        for (List<String> bucket : bucketArray) {
            for (String str : bucket) {
                array[index++] = str;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);

        // Pedir al usuario el número de palabras a ordenar
        System.out.println("Ingrese el número de palabras que desea ordenar:");
        int numPalabras = sc.nextInt();
        sc.nextLine();  // Consumir el salto de línea restante

        // Leer palabras desde el archivo
        List<String> words = readWordsFromFile("C:/Users/User/Desktop/palabras/palabras.ES", numPalabras);

        // Convertir la lista de palabras a un array para usar en los métodos de ordenación
        String[] array = words.toArray(new String[0]);

        // Convertir las palabras a sus valores ASCII
        List<Integer> resultado = conversorASCII(words);

        // Medir el tiempo de ejecución de bubbleSort
        long startTime = System.nanoTime();
        bubbleSort(array, resultado);
        long endTime = System.nanoTime();
        long durationBubbleSort = endTime - startTime;
        double durationBubbleSortSeconds = durationBubbleSort / 1_000_000_000.0;
        System.out.println("Lista ordenada con bubbleSort: ");
        for (String str : array) {
            System.out.print(str + ", ");
        }
        System.out.println();
        System.out.println("Tiempo de ejecución de bubbleSort: " + durationBubbleSort + " nanosegundos (" + durationBubbleSortSeconds + " segundos)");

        // Hacer una copia del array para usar bucketSort
        String[] arrayCopy = Arrays.copyOf(array, array.length);

        // Medir el tiempo de ejecución de bucketSort
        startTime = System.nanoTime();
        bucketSort(arrayCopy, resultado);
        endTime = System.nanoTime();
        long durationBucketSort = endTime - startTime;
        double durationBucketSortSeconds = durationBucketSort / 1_000_000_000.0;
        System.out.println("Lista ordenada con bucketSort: ");
        for (String str : arrayCopy) {
            System.out.print(str + ", ");
        }
        System.out.println();
        System.out.println("Tiempo de ejecución de bucketSort: " + durationBucketSort + " nanosegundos (" + durationBucketSortSeconds + " segundos)");
    }

    private static List<String> readWordsFromFile(String fileName, int numPalabras) throws IOException {
        List<String> words = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(fileName));

        String line;
        int count = 0;
        while ((line = reader.readLine()) != null && count < numPalabras) {
            words.add(line);
            count++;
        }
        reader.close();

        return words;
    }
}
