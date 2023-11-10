package aed;

public class SistemaCNE {
    Partido[] partidos;
    Distrito[] distritos;
    int votos_totales;
    ColaDePrioridad<Partido> ballotage;

    public class Partido implements Comparable<Partido> {
        private int votos;
        private String nombre;
        private int id;

        public int compareTo(Partido otro) {
            return this.votos - otro.votos;
        }

        public Partido(int votos, String nombre, int id) {
            this.votos = votos;
            this.nombre = nombre;
            this.id = id;
        }

        public Partido() {
        }
    }

    public class Distrito {
        // Constantes
        private int cant_bancas;
        private int max;
        private int min;
        private String nombre;
        // Variables
        private int[] votos_partidos;
        private int votos_totales;
        private ColaDePrioridad<Partido> dHondt;
        private boolean dHondt_calculado;
        private int[] resultado_dHondt;
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
            int[] ultimasMesasDistritos) { // O(D*P)
        this.partidos = new Partido[nombresPartidos.length]; // O(P)
        this.distritos = new Distrito[nombresDistritos.length]; // O(D)
        // this.dHondt = new ColaDePrioridad[nombresDistritos.length]; // O(D)
        this.ballotage = new ColaDePrioridad<Partido>(nombresPartidos.length); // O(P)
        this.votos_totales = 0;
        // O(D*P) porque se ejecuta D veces un bloque de código O(P)
        for (int dist = 0; dist < distritos.length; dist++) { // O(D)
            Distrito distrito = new Distrito();
            distrito.cant_bancas = diputadosPorDistrito[dist];
            distrito.nombre = nombresDistritos[dist];
            distrito.max = ultimasMesasDistritos[dist];
            distrito.min = (dist == 0) ? 0 : (distritos[dist - 1].max + 1);
            distrito.votos_partidos = new int[nombresPartidos.length]; // O(P)
            // distrito.resultado_dHondt = new int[nombresPartidos.length]; // O(P)
            // for (int part = 0; part < nombresPartidos.length; part++) { // O(P).
            // distrito.votos_partidos[part] = 0; // En java no es necesario, se inicializan
            // en 0,
            // distrito.resultado_dHondt[part] = 0; // pero para hacerlo más declarativo
            // }
            distrito.votos_totales = 0;
            distritos[dist] = distrito;

            // dHondt[dist] = new ColaDePrioridad<Partido>(nombresPartidos.length); // O(P)
        }

        for (int part = 0; part < partidos.length; part++) { // O(P)
            Partido partido = new Partido();
            partido.nombre = nombresPartidos[part];
            partido.votos = 0;
            partido.id = part;
            partidos[part] = partido;
        }

    }

    public String nombrePartido(int idPartido) {
        throw new UnsupportedOperationException("No implementada aun");
    }

    public String nombreDistrito(int idDistrito) {
        throw new UnsupportedOperationException("No implementada aun");
    }

    public int diputadosEnDisputa(int idDistrito) { // O(1)
        return distritos[idDistrito].cant_bancas;
    }

    public String distritoDeMesa(int idMesa) {
        throw new UnsupportedOperationException("No implementada aun");
    }

    public void registrarMesa(int idMesa, VotosPartido[] actaMesa) {
        Distrito distrito;
        distrito = distritos[0]; // provisorio, sacar

        // específico para Ej9 - resultadosDiputados.
        distrito.dHondt_calculado = false;
        distrito.resultado_dHondt = new int[partidos.length - 1]; // O(P)
        Partido[] umbral = new Partido[partidos.length - 1]; // O(P)
        for (int i = 0; i < partidos.length - 1; i++) { // O(P)
            if (porcentaje(distrito.votos_partidos[i], distrito.votos_totales) > 3) {
                umbral[i] = new Partido(distrito.votos_partidos[i], partidos[i].nombre, i);
            } else {
                umbral[i] = new Partido(0, partidos[i].nombre, i);
            }
        }
        distrito.dHondt = new ColaDePrioridad<Partido>(umbral); // O(P)
    }

    public int votosPresidenciales(int idPartido) {
        return partidos[idPartido].votos;
    }

    public int votosDiputados(int idPartido, int idDistrito) {
        return distritos[idDistrito].votos_partidos[idPartido];
    }

    public int[] resultadosDiputados(int idDistrito) { // O(Dd*log(P))
        Distrito distrito = distritos[idDistrito];
        int[] res = distrito.resultado_dHondt;
        if (!distrito.dHondt_calculado) {
            ColaDePrioridad<Partido> votos = distrito.dHondt;
            // O(Dd*log(P)) porque se realiza Dd veces un bloque de código O(log(P))
            for (int i = 0; i < distrito.cant_bancas; i++) { // O(Dd)
                Partido banca = votos.desencolar(); // O(log(P))
                res[banca.id] += 1;
                banca.votos = distrito.votos_partidos[banca.id] / (res[banca.id] + 1);
                votos.encolar(banca); // O(log(P))
            }
            distrito.dHondt_calculado = true;
        }
        return res;
    }

    public int porcentaje(Partido partido, int votos_totales) {
        return (partido.votos * 100) / votos_totales;
    }

    public int porcentaje(int votos, int votos_totales) {
        return (votos * 100) / votos_totales;
    }

    public boolean hayBallotage() {
        boolean res = true;
        Partido primero = ballotage.proximo();
        if (porcentaje(primero, votos_totales) >= 45) {
            res = false;
        } else if (porcentaje(primero, votos_totales) >= 40
                && porcentaje(primero, votos_totales) - porcentaje(ballotage.segundo(), votos_totales) >= 10) {
            res = false;
        }
        return res;
    }
}
