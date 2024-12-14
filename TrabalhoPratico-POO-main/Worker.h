#ifndef TP_POO_RELOADED_WORKER_H
#define TP_POO_RELOADED_WORKER_H

#include <iostream>
#include <sstream>
#include <vector>
#include "Codes.h"

using namespace std;

class Worker {
protected:
    string id;
    vector <string> buildings;
    string type;
    int days;
    float quitProb;
    bool has_moved;

    void AssignID(int* day, int* worker_nr);

public:
    Worker();
    ~Worker();

    string GetType();
    string GetID();
    static Worker* Create(const string& type, int* days, int* worker_nr);
    void UpdateQuitProb(int &days);
    float GetQuitProb() const;
    bool HasMoved();
    void SetHasMoved(bool has_moved);

};

class Operario : public Worker {
public:
    Operario();
};

class Lenhador : public Worker {
public:
    Lenhador();
};

class Mineiro : public Worker{
public:
    Mineiro();
};
#endif //TP_POO_RELOADED_WORKER_H
