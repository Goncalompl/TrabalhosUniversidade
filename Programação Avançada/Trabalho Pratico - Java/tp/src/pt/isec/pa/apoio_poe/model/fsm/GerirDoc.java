package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.Comando;
import pt.isec.pa.apoio_poe.model.data.Gestor;

public class GerirDoc extends EstadosAdapter {


    public GerirDoc(Gestor gestor)  {
        super(gestor);
    }
    @Override
    public IEstado gerir(Comando comando) {
        gestor.clearRegisto();
        switch (comando.getTipo()) {
            case INSERIR_DOC:
                gestor.inserirDoc(comando.getParams().get(0), comando.getParams().get(1));
                break;
            case LISTAR_DOCS:
                gestor.listaDocentes();
                break;
            case CONSULTAR_DOC:
                gestor.consultarDocente(comando.getParams().get(0));
                break;
            case LER_CSV_DOCS:
                gestor.obterInfoFichDoc(comando.getParams().get(0));
                break;
            case EDITAR_DOC:
                gestor.editarDoc(comando.getParams().get(0),comando.getParams().get(1),comando.getParams().get(2));
            case ELIMINA_DOC:
                gestor.Eliminar(comando.getParams().get(0));
                break;

        }
        return this;
    }

    @Override
    public IEstado voltar() {
        return new Configuracao(gestor);
    }

    @Override
    public EstadoGestor getEstado() {
        return EstadoGestor.GERIR_DOC;
    }

    @Override
    public IEstado fechaFase() {
        return new Configuracao(gestor);
    }
}