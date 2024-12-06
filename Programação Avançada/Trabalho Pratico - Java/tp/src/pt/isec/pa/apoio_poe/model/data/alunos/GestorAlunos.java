package pt.isec.pa.apoio_poe.model.data.alunos;

import pt.isec.pa.apoio_poe.model.data.propostas.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;


public class GestorAlunos {
    private final ArrayList<Aluno> alunosM = new ArrayList<>();

    public boolean inserir(Aluno x) {
        for (int i = 0; i < alunosM.size(); i++) {
            if (alunosM.get(i).getNrEstudante() == x.getNrEstudante()) {
                return false;
            } else if (alunosM.get(i).getEmail().equals(x.getEmail())) {
                return false;
            }
        }
        alunosM.add(x);
        return true;
    }

    public ArrayList<Aluno> getAlunos() {
        return alunosM;
    }

    public Aluno consultar(long nrE) {
        for (Aluno aluno : alunosM)
            if (aluno.getNrEstudante() == nrE)
                return aluno;
        return null;
    }

    public boolean eliminar(long nrE) {
        for (int i = 0; i < alunosM.size(); i++) {
            if (nrE == alunosM.get(i).getNrEstudante()) {
                alunosM.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean verifica(Aluno x) {
        return alunosM.contains(x);
    }

    public void editar(long nrAluno) {
        Aluno x = consultar(nrAluno);
        if (x == null)
            return;

        Scanner scanner = new Scanner(System.in);
        int num;
        if (!verifica(x)) {
            num = 8;
        } else {
            System.out.println("Insira os dados que pertende alterar:\n 1- Numero de Estudante\n 2-Nome\n 3-email\n 4-Sigla do Curso\n 5-Sigla do Ramo\n 6-Classificaçao\n 7-Acede a estagio\n 0-Sair");
            num = scanner.nextInt();
        }
        Scanner scanner1 = new Scanner(System.in);
        switch (num) {
            case 1:
                System.out.println("Introduza um novo Numero de estudante: ");
                long nrEst = scanner1.nextLong();
                for (Aluno aluno : alunosM) {
                    if (aluno.getNrEstudante() == x.getNrEstudante())
                        System.out.println("O numero ja existe!!");
                    break;
                }
                x.setNrEstudante(nrEst);
                break;
            case 2:
                System.out.println("Introduza um novo Nome: ");
                String nome = scanner1.nextLine();
                x.setNome(nome);
                break;
            case 3:
                System.out.println("Introduza um novo Email: ");
                String email = scanner1.nextLine();
                for (Aluno aluno : alunosM) {
                    if (aluno.getEmail().equals(x.getEmail())) {
                        System.out.println("O email ja existe!!");
                        break;
                    }
                }
                x.setEmail(email);
                break;
            case 4:
                System.out.println("Introduza a nova sigla de curso: ");
                String siglaC = scanner1.nextLine();
                x.setSiglaC(siglaC);
                break;
            case 5:
                System.out.println("Introduza a nova sigla de ramo: ");
                String siglaR = scanner1.nextLine();
                x.setSiglaR(siglaR);
                break;
            case 6:
                System.out.println("Introduza uma nova classificação ");
                double classificacao = scanner1.nextInt();
                x.setClassificacao(classificacao);
                break;
            case 7:
                if (x.getAcederEstagio()) {
                    x.setAcederEstagio(false);
                } else x.setAcederEstagio(true);
                break;
            case 8:
                System.out.println("O Aluno nao esta inserido nos dados");
        }
    }

    public ArrayList<Aluno> getAlunosAutopropostos(GestorPropostas propostas) {
        ArrayList<Aluno> resultado = new ArrayList<>();
        Aluno aluno;
        for (Proposta proposta : propostas.getPropostas()) {
            if (proposta instanceof Autoproposto) {
                aluno = consultar(proposta.getNrAluno());
                if (aluno != null)
                    resultado.add(aluno);
            }

        }
        return resultado;
    }

    public ArrayList<Aluno> getAlunosCandidatura(GestorPropostas candidaturas) {
        ArrayList<Aluno> resultado = new ArrayList<>();
        Aluno aluno;
        for (Proposta candidatura : candidaturas.getPropostas()) {
            aluno = consultar(candidatura.getNrAluno());
            if (aluno != null)
                resultado.add(aluno);

        }
        return resultado;
    }

    public ArrayList<Aluno> getAlunosNCandidatura(GestorPropostas candidaturas) {
        ArrayList<Aluno> resultado = new ArrayList<>();
        boolean flag;
        for (Aluno aluno : alunosM) {
            flag = false;
            for (int j = 0; j < candidaturas.getPropostas().size(); j++) {
                if (candidaturas.getPropostas().get(j).getNrAluno() == aluno.getNrEstudante()) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                resultado.add(aluno);
            }

        }
        return resultado;
    }

    public boolean obterInfoFich(String nomeFich) {
        File ficheiro = new File(nomeFich);
        Scanner scanner;
        String line;
        String[] splitted;
        long nrAluno;
        String nome, email, siglaC, siglaR;
        double classificacao;
        boolean acederEstagio;

        try {
            scanner = new Scanner(ficheiro);
        } catch (FileNotFoundException e) {
            return false;
        }

        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            splitted = line.split(",");
            if (splitted.length != 7) {
                scanner.close();
                return false;
            }

            nrAluno = Long.parseLong(splitted[0]);
            nome = splitted[1];
            email = splitted[2];
            siglaC = splitted[3];
            siglaR = splitted[4];
            classificacao = Double.parseDouble(splitted[5]);
            acederEstagio = Boolean.parseBoolean(splitted[6]);
            inserir(new Aluno(nrAluno,nome,email,siglaC,siglaR,classificacao,acederEstagio));
        }
        scanner.close();
        return true;
    }

    public void sort() {
        alunosM.sort(new Comparator<Aluno>() {
            @Override
            public int compare(Aluno o1, Aluno o2) {
                if (!o1.getAcederEstagio() && o2.getAcederEstagio())
                   return 1;
                if (o1.getAcederEstagio() && !o2.getAcederEstagio())
                    return -1;

                return Double.compare(o2.getClassificacao(), o1.getClassificacao());
            }
        });
    }

    public int size() {
        return alunosM.size();
    }

    public Aluno get(int index) {
        return alunosM.get(index);
    }


    public ArrayList<Aluno> getCandidaturasRegistada (ArrayList<Proposta> candidaturaR){
        ArrayList<Aluno> resultado = new ArrayList<>();
        Aluno aluno = null;
        for (Proposta proposta: candidaturaR) {
            aluno = consultar(proposta.getNrAluno());
                if(aluno == null)
                    continue;
                resultado.add(aluno);


        }

        return resultado;
    }
}
