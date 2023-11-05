package aed;

public class ColaDePrioridad { // Max Heap.
    int[] elems; // Para complejidades n = longitud. Se toma el peor caso.
    int longitud; // Si no se aclara se asume que es O(1).

    public static void main(String[] args) {
        int[] arr = { 5, 2, 3, 1, 8, 2, 4, 10, 2, 7, 42, 73, 71, 13, 23, 53, 92, 10, -20 };
        ColaDePrioridad cola = new ColaDePrioridad(arr);
        cola.print();
        int max = cola.desencolar();
        cola.print();
        cola.encolar(max);
        cola.print();
    }

    public ColaDePrioridad(int longitud) { // O(1)
        elems = new int[longitud];
        this.longitud = longitud;
    }

    public ColaDePrioridad(int[] arreglo) { // O(n)
        longitud = arreglo.length;
        // elems = new int[arreglo.length];
        for (int i = arreglo.length - 1; i >= 0; i--) { // O(n) por lo visto en clase del algoritmo de Floyd
            heapify(arreglo, i, longitud); // O(log(n) - altura(i))
        }
        elems = arreglo;
    }

    // requiere que longitud > 0
    public int desencolar() { // O(log n)
        int maximo = elems[0];
        int ultimo = elems[longitud - 1];
        elems[0] = ultimo;
        elems[longitud - 1] = -1;
        longitud--;
        heapify(elems, 0, longitud); // O(log n)

        return maximo;
    }

    public int proximo() { // O(1)
        return elems[0];
    }

    // requiere longitud > 1
    public int segundo() { // O(1)
        if (longitud > 2) {
            if (elems[1] > elems[2]) {
                return elems[1];
            } else {
                return elems[2];
            }
        } else {
            return elems[1];
        }
    }

    // requiere que longitud < elems.length
    public void encolar(int e) { // O(log n)
        int i = longitud;
        elems[longitud] = e;
        longitud++;
        while (elems[i] > elems[(i - 1) / 2]) { // O(log n), cada vez se reduce a la mitad
            elems[i] = elems[(i - 1) / 2];
            elems[(i - 1) / 2] = e;
            i = (i - 1) / 2;
        }
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
        while (2 * max_long < longitud) {
            max_long = 2 * max_long;
            altura++;
        }
        String linea = "";
        for (int i = 0; i < altura; i++) {
            for (int j = 0; j < Math.pow(2, i); j++) {
                for (int espacio = 0; espacio < max_long / (i + 1.2); espacio++) {
                    linea += "  ";
                }
                if ((int) Math.pow(2, i) + j - 1 < longitud) {
                    linea += elems[(int) Math.pow(2, i) + j - 1];
                }
            }
            System.out.println(linea);
            linea = "";
        }

    }

    // No sabemos calcular formalmente la complejidad de funciones recursivas, sin
    // embargo
    // va a haber a lo sumo (la altura del subárbol correspondiente a i)
    // recursiones, y
    // como solo incluye operaciones O(1) y su llamado recursivo, entonces podemos
    // decir
    // su cumplejidad es O(la altura del subárbol correspondiente a i) =
    // O(log(max_long) - nivel(i))
    private void heapify(int[] arr, int i, int max_long) { // O(log(max_long) - nivel(i))
        int hijoIzq = i * 2 + 1;
        int hijoDer = i * 2 + 2;
        boolean tieneHijoIzq = hijoIzq < max_long;
        boolean tieneHijoDer = hijoDer < max_long;
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
                heapify(arr, mayorHijo, max_long); // O(log(max_long) - nivel(i) - 1). Cada vez se va reduciendo u
            }
        }
    }
}
