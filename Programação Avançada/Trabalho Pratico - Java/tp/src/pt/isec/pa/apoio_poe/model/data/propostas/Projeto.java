package pt.isec.pa.apoio_poe.model.data.propostas;


import pt.isec.pa.apoio_poe.model.data.alunos.Aluno;
import pt.isec.pa.apoio_poe.model.data.docente.Docente;

public class Projeto extends Proposta {
    private String ramo;
    private String emailPro;
    private long nrAluno;

    Projeto(String codI, String titulo, String ramo, String emailPro) {
        super(codI, titulo);
        this.ramo = ramo;
        this.emailPro = emailPro;
    }
    public Projeto(String codI, String titulo, String ramo, String emailPro, long nrAluno) {
        super(codI, titulo);
        this.ramo = ramo;
        this.emailPro = emailPro;
        this.nrAluno = nrAluno;

    }

    @Override
    public String toString() {
        return "Codigo de Identificação: " + getCodI() + "\nTitulo: " + getTitulo() + "\nRamo: " + ramo + "\n Email proponente: " + emailPro + "\nNumero do aluno" + nrAluno;
    }

    public String getRamo() {
        return this.ramo;
    }
    public String getEmailPro() {
        return this.emailPro;
    }
    public long getNrAluno() {
        return this.nrAluno;
    }

}
