# Trabalho Prático de Programação Orientada a Objectos - 2021/2022 (ISEC - LEI)

Regras gerais do trabalho:
O trabalho prático de POO é constituído por um programa em C++. Este programa deve:
- Seguir os princípios e práticas de orientação a objetos;
- Ser feito em C++, usando corretamente a semântica desta linguagem;
- Usar as classes/bibliotecas usadas nas aulas, onde apropriado, ou outras da biblioteca standard C++.
Não devem ser usadas outras bibliotecas sem o consentimento prévio dos docentes;

Deve concretizar as funcionalidades do tema referidas no enunciado.
A interface com o utilizador é feita segundo o conceito de consola não-gráfica, e não requer nenhuma
manipulação nem de cores nem de posicionamento de elementos no ecrã.
São aceites os trabalhos que usem a framework Qt para construção de interface gráfica. Esta framework é
feita em C++ e é cross-platform, estando alinhada com os objetivos gerais da disciplina (C++). O uso desta
framework é completamente opcional, mas será recompensado com até 12.5% (mediante a quantidade de
uso desta framework que é feita), e sem ultrapassar os 100% do trabalho. Significa que pode esta valorização
ser usada para compensar outras partes em falta. Todo o trabalho de exploração desta framework (que é fácil)
fica a cargo dos interessados em a usar.

IMPORTANTE: Este trabalho não usa a framework Qt.


# O que nao funciona?
- Pequenos detalhes que nem eu me lembro. A base de jogo em si funciona e ha possibilidade de 'ganhar'.
- Formatacao da ilha na consola.

# Lista de comandos
Funcao CheckSyntax() no ficheiro App.cpp tem um if else com a lista dos comandos.

# Outras informacoes
Para mudar os recursos iniciais basta ir ao ficheiro Storage.cpp e na funcao Create(string type) trocar a variavel 'quantity' (deve estar a 10000) para o valor desejado.
