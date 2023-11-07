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
        Partido[] partidos = new Partido[nombresPartidos.length]; // O(P)
        Distrito[] distritos = new Distrito[nombresDistritos.length]; // O(D)
        ColaDePrioridad<Partido>[] dHondt = new ColaDePrioridad[nombresDistritos.length]; // O(D)
        ColaDePrioridad<Partido> ballotage = new ColaDePrioridad<Partido>(nombresPartidos.length); // O(P)
        int votos_totales = 0;

        for (int dist = 0; dist < distritos.length; dist++) {
            Distrito distrito = new Distrito();
            distrito.cant_bancas = diputadosPorDistrito[dist];
            distrito.nombre = nombresDistritos[dist];
            distrito.max = ultimasMesasDistritos[dist];
            distrito.min = (dist == 0) ? 0 : (distritos[dist - 1].max + 1);
            distrito.votos_partidos = new int[nombresDistritos.length];
            for (int part = 0; part < nombresPartidos.length; part++) {
                distrito.votos_partidos[part] = 0;
            }
            distritos[dist] = distrito;

            dHondt[dist] = new ColaDePrioridad<Partido>(nombresPartidos.length);
        }

        for (int part = 0; part < partidos.length; part++) {
            Partido partido = new Partido();
            partido.nombre = nombresPartidos[part];
            partido.votos = 0;
            partidos[part] = partido;
        }

    }

    public String nombrePartido(int idPartido) {
        throw new UnsupportedOperationException("No implementada aun");
    }

    public String nombreDistrito(int idDistrito) {
        throw new UnsupportedOperationException("No implementada aun");
    }

    public int diputadosEnDisputa(int idDistrito) {
        throw new UnsupportedOperationException("No implementada aun");
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

    public int[] resultadosDiputados(int idDistrito) {
        ColaDePrioridad<Partido> votos = dHondt[idDistrito];
        int[] res = new int[distritos[idDistrito].votos_partidos.length];
        for (int i = 0; i < distritos[idDistrito].cant_bancas; i++) {
            Partido banca = votos.desencolar();
            res[banca.id] += 1;
            banca.votos = banca.votos / (res[banca.id] + 1);
            votos.encolar(banca);
        }

        return res;
    }

    public boolean hayBallotage() {
        throw new UnsupportedOperationException("No implementada aun");
    }
}
