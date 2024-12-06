package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.Comando;
import pt.isec.pa.apoio_poe.model.data.Gestor;

public class GerirProps extends EstadosAdapter {


    public GerirProps(Gestor gestor)  {
        super(gestor);
    }
    @Override
    public IEstado gerir(Comando comando) {
        gestor.clearRegisto();
        switch (comando.getTipo()) {
            case INSERIR_PROP_E:
                gestor.inserirPropE(comando.getParams().get(0),comando.getParams().get(1),comando.getParams().get(2),comando.getParams().get(3), Long.parseLong(comando.getParams().get(4)));
                break;
            case INSERIR_PROP_P:
                gestor.inserirPropP(comando.getParams().get(0),comando.getParams().get(1),comando.getParams().get(2),comando.getParams().get(3), Long.parseLong(comando.getParams().get(4)));
                break;
            case LISTAR_PROPS:
                gestor.listaPropostas(true, true, true, true);
                break;
            case EDITAR_ESTAGIO:
                 break;
            case EDITAR_PROJETO:
                break;
            case CONSULTAR_PROP:
                gestor.listaPropostas(comando.getParams().get(0));
                break;
            case CONSULTAR_PROP_P:
                gestor.listaPropostasProjeto(comando.getParams().get(0));
                break;
            case LER_CSV_ESTAGIO:
                gestor.obterInfoFichEstagio(comando.getParams().get(0));
                break;
            case LER_CSV_PROJETO:
                gestor.obterInfoFichProjeto(comando.getParams().get(0));
                break;
            case ELIMINA_PROP:
                gestor.eliminarProp(comando.getParams().get(0));


        }
        return this;
    }

    @Override
    public EstadoGestor getEstado() {
        return EstadoGestor.GERIR_PROP;
    }

    @Override
    public IEstado voltar() {
        return new Configuracao(gestor);
    }

    @Override
    public IEstado fechaFase() {
        return new Configuracao(gestor);
    }
}