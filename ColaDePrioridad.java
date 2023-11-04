package aed;

public class ColaDePrioridad { // max heap
    int[] elems;

    public static void main(String[] args) {
        int[] hola = { 5, 2, 3, 1, 8, 2, 4, 10, 2, 7, 42, 73, 71, 13, 23, 53 };
        ColaDePrioridad pepe = new ColaDePrioridad(hola);
        pepe.print();
        pepe.desencolar();
        pepe.print();
    }

    public ColaDePrioridad(int longitud) {
        elems = new int[longitud];
    }

    public ColaDePrioridad(int[] arreglo) {
        // elems = new int[arreglo.length];
        for (int i = arreglo.length - 1; i >= 0; i--) {
            heapify(arreglo, i);
        }
        elems = arreglo;
    }

    public int desencolar() {
        int maximo = elems[0];
        int ultimo = elems[elems.length - 1];
        elems[0] = ultimo;
        elems[elems.length - 1] = 0;
        heapify(elems, 0);

        return maximo;
    }

    public void print() {
        String res = "[";
        for (int i = 0; i < elems.length - 1; i++) {
            res += elems[i] + ", ";
        }
        res += elems[elems.length - 1] + "]";
        System.out.println(res);

        int max_long = 1;
        int altura = 1;
        while (2 * max_long < elems.length) {
            max_long = 2 * max_long;
            altura++;
        }
        String linea = "";
        for (int i = 0; i < altura; i++) {
            for (int j = 0; j < Math.pow(2, i); j++) {
                for (int espacio = 0; espacio < max_long / (i + 1.2); espacio++) {
                    linea += "  ";
                }
                linea += elems[(int) Math.pow(2, i) + j - 1];
            }
            System.out.println(linea);
            linea = "";
        }

    }

    private void heapify(int[] arr, int i) {
        int hijoIzq = i * 2 + 1;
        int hijoDer = i * 2 + 2;
        boolean tieneHijoIzq = hijoIzq < arr.length;
        boolean tieneHijoDer = hijoDer < arr.length;
        boolean tieneHijos = tieneHijoDer || tieneHijoIzq;

        if (tieneHijos) {
            if ((tieneHijoIzq && arr[i] < arr[hijoIzq]) || (tieneHijoDer && arr[i] < arr[hijoDer])) {
                int mayorHijo;
                if (tieneHijoDer && arr[hijoDer] > arr[hijoIzq]) {
                    mayorHijo = hijoDer;
                } else {
                    mayorHijo = hijoIzq;
                }
                int mayorHijoValor = arr[mayorHijo];
                arr[mayorHijo] = arr[i];
                arr[i] = mayorHijoValor;
                heapify(arr, mayorHijo);
            }
        }
    }
}
