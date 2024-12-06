package pt.isec.pa.apoio_poe.model.data.propostas;

public class Estagio extends Proposta {
    private String area;
    private String entidade;

    public String getArea() {
        return area;
    }

    public Estagio(String codI, String titulo, String area, String entidade, long aluno) {
        super(codI, titulo, aluno);
        this.area = area;
        this.entidade = entidade;

    }

    Estagio(String codI, String titulo, String entidade,long aluno) {
        super(codI, titulo, aluno);
        this.entidade = entidade;

    }

    public String toString() {
        return "Codigo de Identificação: " + getCodI() + "; Titulo: " + getTitulo() + "; Entidade: " + entidade;
    }

    public String getEntidade() {
        return this.entidade;
    }

}
