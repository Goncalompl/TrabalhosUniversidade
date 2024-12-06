package pt.isec.pa.apoio_poe.model.data.propostas;

import pt.isec.pa.apoio_poe.model.data.alunos.Aluno;
import pt.isec.pa.apoio_poe.model.data.docente.Docente;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class GestorPropostas {
    private final ArrayList<Proposta> propostas;

    public GestorPropostas() {
        propostas = new ArrayList<>();
    }

    public boolean inserir(Proposta x) {
        if (x == null)
            return false;
        for (int i = 0; i < propostas.size(); i++) {
            if (propostas.get(i).getCodI() == x.getCodI()) {
                return false;
            }
        }

        propostas.add(x);
        return true;
    }

    public ArrayList<Proposta> getPropostas() {
        return propostas;
    }

    public Proposta consulta(String codI) {
        for (Proposta proposta : propostas) {
            if (proposta.getCodI().equals(codI))
                return proposta;
        }
        return null;
    }

    public Estagio consultaE(String codI) {

        for (Proposta proposta : propostas) {
            if (proposta instanceof Estagio)
                return (Estagio) proposta;
        }
        return null;
    }

    public boolean eliminarProp(String codI) {
        for (int i = 0; i < propostas.size(); i++) {
            if (Objects.equals(codI, propostas.get(i).getCodI())) {
                propostas.remove(i);
                return true;
            }
        }
        return false;
    }




    public boolean obterInfoFichEstagio(String nomeFich) {
        File ficheiro = new File(nomeFich);
        Scanner scanner;
        String line;
        String[] splitted;
        String codI, titulo, entidade, area;
        boolean registada;
        long nrAluno;

        try {
            scanner = new Scanner(ficheiro);
        } catch (FileNotFoundException e) {
            return false;
        }

        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            splitted = line.split(",");
            if (splitted.length != 5) {
                scanner.close();
                return false;
            }
            codI = splitted[0];
            area = splitted[1];
            titulo = splitted[2];
            entidade = splitted[3];
            nrAluno = Long.parseLong(splitted[4]);
            Estagio estagio = new Estagio(codI, titulo, area, entidade, nrAluno);
        }
        scanner.close();
        return true;
    }

    public boolean obterInfoFichProjeto(String nomeFich) {
        File ficheiro = new File(nomeFich);
        Scanner scanner;
        String line;
        String[] splitted;
        String codI, titulo, emailDocente, ramo;
        boolean registada;
        long nrAluno;

        try {
            scanner = new Scanner(ficheiro);
        } catch (FileNotFoundException e) {
            return false;
        }

        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            splitted = line.split(",");
            if (splitted.length != 5) {
                scanner.close();
                return false;
            }
            codI = splitted[0];
            ramo = splitted[1];
            titulo = splitted[2];
            emailDocente = splitted[3];
            nrAluno = Long.parseLong(splitted[4]);

            inserir(new Projeto(codI, titulo, ramo, emailDocente, nrAluno));
        }
        scanner.close();
        return true;
    }

    public boolean obterInfoFichAutoproposto(String nomeFich) {
        File ficheiro = new File(nomeFich);
        Scanner scanner;
        String line;
        String[] splitted;
        String codI, titulo, emailDocente, ramo;
        boolean registada;
        long nrAluno;

        try {
            scanner = new Scanner(ficheiro);
        } catch (FileNotFoundException e) {
            return false;
        }

        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            splitted = line.split(",");
            if (splitted.length != 3) {
                scanner.close();
                return false;
            }
            codI = splitted[0];
            titulo = splitted[1];
            nrAluno = Long.parseLong(splitted[2]);
            inserir(new Autoproposto(codI, titulo, nrAluno));
        }
        scanner.close();
        return true;
    }


    public ArrayList<Proposta> getPropostas(boolean autoproposta, boolean propostaD) {
        ArrayList<Proposta> resultado = new ArrayList<>();
        if (autoproposta) {
            for (int i = 0; i < propostas.size(); i++) {
                if (propostas.get(i) instanceof Autoproposto)
                    resultado.add(propostas.get(i));
            }
        }
        if (propostaD) {
            for (int i = 0; i < propostas.size(); i++) {
                if (!(propostas.get(i) instanceof Autoproposto))
                    resultado.add(propostas.get(i));
            }
        }

        if (!autoproposta && !propostaD)
            resultado.addAll(propostas);
        return resultado;
    }

    public int size() {
        return propostas.size();
    }

    public Proposta get(int index) {
        return propostas.get(index);
    }

    public ArrayList<Projeto> getProjetos() {
        ArrayList<Projeto> resultado = new ArrayList<>();
        for (Proposta proposta : propostas) {
            if (proposta instanceof Projeto)
                resultado.add((Projeto) proposta);
        }
        return resultado;
    }

    public ArrayList<Proposta> getProDocenteAtribuido() {
        ArrayList<Proposta> resultado = new ArrayList<>();
        for (Proposta proposta : propostas) {
            if (proposta.isDocenteAtribuido())
                resultado.add(proposta);

        }
        return resultado;
    }

    public ArrayList<Proposta> getProDocenteNAtribuido() {
        ArrayList<Proposta> resultado = new ArrayList<>();
        for (Proposta proposta : propostas) {
            if (!proposta.isDocenteAtribuido())
                resultado.add(proposta);

        }
        return resultado;
    }

    public int contaProDocente(Docente docente) {
        int contador = 0;
        for (Proposta proposta : propostas) {
            if (proposta.getEmailDoc().equals(docente.getEmail()))
                ++contador;
        }
        return contador;
    }

    public ArrayList<Proposta> getPropostasDis() {
        ArrayList<Proposta> resultado = new ArrayList<>();
        for (Proposta proposta : propostas) {
            if (!proposta.isAlunoAtribuido())
                resultado.add(proposta);
        }
        return resultado;
    }

    public boolean guardarEstagios(String nomeFich) {
        try {
            FileWriter fileWriter = new FileWriter(nomeFich);
            for (Proposta proposta : propostas) {
                if (proposta instanceof Estagio)
                    fileWriter.write(proposta.getCodI() + "," + ((Estagio) proposta).getArea() + "," +
                            proposta.getTitulo() + "," + ((Estagio) proposta).getEntidade() + "," + proposta.getNrAluno() + "\n");

            }
            fileWriter.close();
        } catch (IOException e) {
            return false;
        }


        return true;
    }

    public boolean guardarProjetos(String nomeFich) {
        try {
            FileWriter fileWriter = new FileWriter(nomeFich);
            for (Proposta proposta : propostas) {
                if (proposta instanceof Projeto)
                    fileWriter.write(proposta.getCodI() + "," + ((Projeto) proposta).getRamo() + "," +
                            proposta.getTitulo() + "," + ((Projeto) proposta).getEmailPro() + "," + proposta.getNrAluno() + "\n");

            }
            fileWriter.close();
        } catch (IOException e) {
            return false;
        }


        return true;
    }

}
