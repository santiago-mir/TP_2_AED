package aed;

public class SistemaCNE {
    Partido[] partidos;
    Distrito[] distritos;
    int votos_totales;
    ColaDePrioridad<Partido>[] dHondt;
    ColaDePrioridad<Partido> ballotage;

    public class Partido implements Comparable<Partido> {
        private int votos;
        // private int[] votos_distritos;
        private String nombre;
        private int id;

        public int compareTo(Partido otro) {

            // boolean oen = (otro == null); // otro es null

            // boolean cd = otro.getClass() != this.getClass(); // clase es distinta
            // if (oen || cd) {
            // return -1;
            // }
            // Partido otroPartido = (Partido) otro;
            return this.votos - otro.votos;
        }
    }

    public class Distrito {
        private int cant_bancas;
        private int max;
        private int min;
        private String nombre;
        private int[] votos_partidos;
        private int votos_totales;
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
        this.dHondt = new ColaDePrioridad[nombresDistritos.length]; // O(D)
        this.ballotage = new ColaDePrioridad<Partido>(nombresPartidos.length); // O(P)
        this.votos_totales = 0;

        for (int dist = 0; dist < distritos.length; dist++) { // O(D*P) porque se ejecuta D veces un bloque de código
                                                              // O(P)
            Distrito distrito = new Distrito();
            distrito.cant_bancas = diputadosPorDistrito[dist];
            distrito.nombre = nombresDistritos[dist];
            distrito.max = ultimasMesasDistritos[dist];
            distrito.min = (dist == 0) ? 0 : (distritos[dist - 1].max + 1);
            distrito.votos_partidos = new int[nombresDistritos.length];
            for (int part = 0; part < nombresPartidos.length; part++) { // O(P)
                distrito.votos_partidos[part] = 0;
            }
            distrito.votos_totales = 0;
            distritos[dist] = distrito;

            dHondt[dist] = new ColaDePrioridad<Partido>(nombresPartidos.length); // O(P)
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
        throw new UnsupportedOperationException("No implementada aun");
    }

    public int votosPresidenciales(int idPartido) {
        throw new UnsupportedOperationException("No implementada aun");
    }

    public int votosDiputados(int idPartido, int idDistrito) {
        throw new UnsupportedOperationException("No implementada aun");
    }

    public int[] resultadosDiputados(int idDistrito) { // O(Dd*log(P)) ???
        ColaDePrioridad<Partido> votos = dHondt[idDistrito];
        int[] res = new int[distritos[idDistrito].votos_partidos.length]; // O(P) ???
        for (int i = 0; i < distritos[idDistrito].cant_bancas; i++) { // O(Dd*log(P)) porque se realiza Dd veces un
                                                                      // bloque de código O(log(P))
            Partido banca = votos.desencolar(); // O(log(P))
            res[banca.id] += 1;
            banca.votos = banca.votos / (res[banca.id] + 1);
            votos.encolar(banca); // O(log(P))
        }

        return res;
    }

    public boolean hayBallotage() {
        throw new UnsupportedOperationException("No implementada aun");
    }
}
