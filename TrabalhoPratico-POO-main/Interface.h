#ifndef TRABALHOPRATICO_POO_RELOADED_INTERFACE_H
#define TRABALHOPRATICO_POO_RELOADED_INTERFACE_H

#include "App.h"
#include <thread>
#include <chrono>
#include <windows.h>

using namespace std::chrono;

class Interface {
    App* game;
    static void SplashScreen();
public:
    Interface();
    ~Interface();

    void Start();

    [[noreturn]] void Loop();

};


#endif //TRABALHOPRATICO_POO_RELOADED_INTERFACE_H
