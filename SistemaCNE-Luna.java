package aed;
public class SistemaCNE {
    Partido[] partidos;
    Distrito[] distritos;
    int votos_totales;
    ColaDePrioridad[] dHondt;
    ColaDePrioridad ballotage;

    public class Partido{
        private int votos_totales;
        private String nombre;
    }

    public class Distrito{
        private int cant_bancas;
        private int max;
        private int min;
        private String nombre;
        private int[] votos_partidos;
    }

    public class VotosPartido{
        private int presidente;
        private int diputados;

        VotosPartido(int presidente, int diputados){this.presidente = presidente; this.diputados = diputados;}{
            this.presidente = presidente;
            this.diputados = diputados;
        }

        public int votosPresidente(){return presidente;}
        public int votosDiputados(){return diputados;}
    }

    public SistemaCNE(String[] nombresDistritos, int[] diputadosPorDistrito, String[] nombresPartidos, int[] ultimasMesasDistritos) {
        partidos = new Partido[nombresPartidos.length];
        distritos = new Distrito[nombresDistritos.length];
        dHondt = new ColaDePrioridad[nombresDistritos.length];
        ballotage = new ColaDePrioridad(nombresPartidos.length);
        votos_totales = 0;

        for (int dist = 0; dist < distritos.length; dist++){
            Distrito distrito = new Distrito();
            distrito.cant_bancas = diputadosPorDistrito[dist];
            distrito.nombre = nombresDistritos[dist];
            distrito.max = ultimasMesasDistritos[dist];
            distrito.min = (dist == 0) ? 0 : (distritos[dist - 1].max + 1);
            distrito.votos_partidos = new int[nombresDistritos.length];
            for (int part = 0; part < nombresPartidos.length; part++){
                distrito.votos_partidos[part] = 0;
            }
            distritos[dist] = distrito;

            dHondt[dist] = new ColaDePrioridad(nombresPartidos.length);
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
        return distritos.votos_partidos[idPartido][idDistrito];
    }

    public int[] resultadosDiputados(int idDistrito){
        throw new UnsupportedOperationException("No implementada aun");
    }

    public int porcentajes(int votos_partidos){
        return (ballotage.elems[votos_partidos] * 100)/votos_totales;
    }

    public boolean hayBallotage(){
        boolean res = false;
        if (porcentajes(ballotage.elems[0]) > 45){
            res = true;
        } else if (porcentajes(ballotage.elems[0]) > 40 && porcentajes(ballotage.elems[0]) - porcentajes(ballotage.elems[1]) >= 10 && porcentajes(ballotage.elems[0]) - porcentajes(ballotage.elems[2]) >= 10){
            res = true;
        } 
        return res;
    }
}
}
