#ifndef JOGOUI_H
#define JOGOUI_H

typedef struct{
    char caracter;
    char nome[100];
    int pid;
    int x;
    int y;
}Jogador;

typedef struct{
    char nome[100];
    char mensagem[100];
}Mensagem;

#define MAX_PLAYERS 10

#endif