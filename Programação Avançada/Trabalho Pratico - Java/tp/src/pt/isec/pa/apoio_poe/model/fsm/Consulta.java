package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.Comando;
import pt.isec.pa.apoio_poe.model.data.Gestor;

public class Consulta extends EstadosAdapter{
    private final boolean fase4Aberta;
    public Consulta(Gestor gestor, boolean fase4Aberta) {
        super(gestor);
        this.fase4Aberta = fase4Aberta;
    }
    @Override
    public IEstado gerir(Comando comando) {
        gestor.clearRegisto();
        switch (comando.getTipo()) {
            case ALUNOS_ATR :
                gestor.listarAlunosAtribuidos();
                break;
            case ALUNOS_NATR :
                gestor.listarAlunosNAtribuidos();
                break;
            case PROPOSTAS_DIS :
                gestor.listarPropostasDis();
                break;
            case PROPOSTAS_ATR :
                gestor.listarPropostasAtribuidas();
                break;
            case LISTAR_PROPC :
                gestor.listarAlunosProDocAtr();
                gestor.listarAlunosProDocNAtr();
                gestor.listarPropostasAtribuidas();
                break;
            case GUARDAR:
                gestor.guardarEstagios(comando.getParams().get(0));
                break;


        }
        return this;
    }



    @Override
    public IEstado recomecar() {
        return new Configuracao(new Gestor());
    }

    @Override
    public EstadoGestor getEstado() {
        return EstadoGestor.CONSULTA;
    }

}
