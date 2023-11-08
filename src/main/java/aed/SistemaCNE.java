package aed;

public class SistemaCNE {
    private Partido[] partidos;
    private Distrito[] distritos;
    private ColaDePrioridad[] dHondt;
    private ColaDePrioridad ballotage;

    private class Partido {
        private int votos__totales_presidente;
        private String nombre;

        Partido() {
            votos__totales_presidente = 0;
            nombre = "";
        }
    }

    private class Distrito {
        private int cant_bancas;
        private int max;
        private int min;
        private String nombre;
        private int[] votos_partido_dip;

        Distrito() {
            cant_bancas = 0;
            max = 0;
            min = 0;
            nombre = "";
            votos_partido_dip = null;
        }
    }

    public class VotosPartido {
        private int presidente;
        private int diputados;

        VotosPartido(int presidente, int diputados) {
            this.presidente = presidente;
            this.diputados = diputados;
        }

        public int votosPresidente() {
            return presidente;
        }

        public int votosDiputados() {
            return diputados;
        }
    }

    public SistemaCNE(String[] nombresDistritos, int[] diputadosPorDistrito, String[] nombresPartidos,
            int[] ultimasMesasDistritos) {
        // generamos el array de distritos, complejidad O(d)
        int i = 0;
        distritos = new Distrito[nombresDistritos.length];
        while (i < nombresDistritos.length) {
            distritos[i] = new Distrito(); // generamos el distrito con valores predeterminados
            // asignamos valores
            distritos[i].nombre = nombresDistritos[i];
            distritos[i].max = ultimasMesasDistritos[i] - 1;
            if (i == 0) {
                distritos[i].min = 0;
            } else {
                distritos[i].min = distritos[i - 1].max + 1;
            }
            distritos[i].cant_bancas = diputadosPorDistrito[i];
            distritos[i].votos_partido_dip = new int[nombresPartidos.length]; // tomo en cuenta los votos en blanco
            i++;
        }
        // generamos el array de distritos, complejidad O(p)
        i = 0; // reiniciamos i
        partidos = new Partido[nombresPartidos.length];
        while (i < nombresPartidos.length) {
            partidos[i] = new Partido(); // generamos el partido con nombres predeterminados
            // asignamos valores
            partidos[i].nombre = nombresPartidos[i];
            i++;
        }
    }

    public String nombrePartido(int idPartido) {
        return partidos[idPartido].nombre; // O(1)
    }

    public String nombreDistrito(int idDistrito) {
        return distritos[idDistrito].nombre; // O(1)
    }

    public int diputadosEnDisputa(int idDistrito) {
        return distritos[idDistrito].cant_bancas; // O(1)
    }

    public String distritoDeMesa(int idMesa) {
        // usamos una busqueda binaria sobre el array de distritos, podemos hacer esto
        // ya que el rango de bancas esta ordenado
        String res = busquedaBinariaDistrito(idMesa).nombre;
        return res;
    }

    public void registrarMesa(int idMesa, VotosPartido[] actaMesa) {
        // complejidad O(log(d))
        Distrito distrito = busquedaBinariaDistrito(idMesa);
        int i = 0;
        // complejidad O(p)
        while (i < partidos.length) {
            distrito.votos_partido_dip[i] += actaMesa[i].diputados;
            // vamos actualizando los votos totales para cada partido
            partidos[i].votos__totales_presidente += actaMesa[i].presidente;
            i++;
        }
        // total complejidad O(P + log(d)) ? revisar
    }

    public int votosPresidenciales(int idPartido) {
        return partidos[idPartido].votos__totales_presidente;
    }

    public int votosDiputados(int idPartido, int idDistrito) {
        return distritos[idDistrito].votos_partido_dip[idPartido];
    }

    public int[] resultadosDiputados(int idDistrito) {
        throw new UnsupportedOperationException("No implementada aun");
    }

    public boolean hayBallotage() {
        throw new UnsupportedOperationException("No implementada aun");
    }

    private Distrito busquedaBinariaDistrito(int idMesa) {
        int low = 0;
        int high = distritos.length - 1;
        // casos triviales
        if (distritos[low].min <= idMesa && distritos[low].max >= idMesa) {
            return distritos[low]; // caso mesa perteneciente al primer distrito del arreglo
        }
        if (distritos[high].min <= idMesa && distritos[high].max >= idMesa) {
            return distritos[high]; // caso mesa perteneciente al ultimo distrito del arreglo
        } else {
            // casos no triviales
            while (low + 1 < high && (distritos[low].min > idMesa || distritos[low].max < idMesa)) {
                int mid = low + (high - low) / 2;
                if (distritos[mid].max < idMesa || (distritos[mid].min <= idMesa && distritos[mid].max >= idMesa)) {
                    low = mid;
                } else {
                    high = mid;
                }
            }
            return distritos[low];
        }
    }
}
