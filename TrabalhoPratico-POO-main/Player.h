#ifndef TRABALHOPRATICO_POO_RELOADED_PLAYER_H
#define TRABALHOPRATICO_POO_RELOADED_PLAYER_H

#include <iostream>
#include <vector>
#include "Storage.h"

using namespace std;

class Player {
    string username;
    Storage* storage;
    float money;

public:
    Player();
    ~Player();

    float GetMoney();
    float GetIron();
    float GetSteel();
    float GetCoal();
    float GetWood();
    float GetWoodBeam();
    float GetElectricity();

    bool TakeMoney(float money);
    bool TakeIron(float iron);
    bool TakeSteel(float steel);
    bool TakeCoal(float coal);
    bool TakeWood(float wood);
    bool TakeWoodBeam(float wood_beam);
    bool TakeElectricity(float electricity);

    void GiveMoney(float money);
    void GiveIron(float iron);
    void GiveIronMont(float iron);
    void GiveSteel(float steel);
    void GiveCoal(float coal);
    void GiveWood(float wood);
    void GiveWoodBeam(float wood_beam);
    void GiveElectricity(float electricity);
    void GiveWoodFlr(float wood);

    string GetUsername();
    Storage* GetStorage();

    static Player* Create(string username);
};


#endif //TRABALHOPRATICO_POO_RELOADED_PLAYER_H
