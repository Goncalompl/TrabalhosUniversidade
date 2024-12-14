    #include "Building.h"


Building::Building() {
    type = "             ";
    level = 0;
    max_level = 0;
    destroy_prob = 0;
    is_online = true;
    needs_worker = true;
    output = 0;
    days = 0;
}

Building::~Building() {

}

void Building::NextDay() {
    days++;
}

int Building::GetDay() {
    return days;
}

bool Building::LevelUp() {
    if(level >= max_level)
        return false;

    level++;
    output++;
    return true;
}

bool Building::IsOnline() const {
    return is_online;
}

bool Building::NeedsWorker() const {
    return needs_worker;
}

void Building::SetOffline() {
    is_online = false;
}

void Building::SetOnline() {
    is_online = true;
}

void Building::SetLevel(int lvl) {
    level = lvl;
}

int Building::GetLevel() {
    return level;
}

float Building::GetDestroyProb() const {
    return destroy_prob;
}

float Building::GetOutput() const {
    return output;
}

string Building::GetType() const {
    return type;
}

Building* Building::Create(const string& type) {
    Building* building;
    if (type == "minaf" || type == minaf) {
        building = new Minaf;
    } else if (type == "minac" || type == minac) {
        building = new Minac;
    } else if (type == "central" || type == central) {
        building = new Central;
    } else if (type == "bat" || type == bat) {
        building = new Bat;
    } else if (type == "fund" || type == fund) {
        building = new Fund;
    } else if (type == "filt" || type == filt) {
        building = new Edx;
    } else if (type == undef) {
        building = new Building;
    }else if (type == "serr" || type == serr) {
        building = new Serr;
    } else building = new Building;
    return building;
}

float Building::GiveMoneyDestroy() {

}

Minaf::Minaf() {

    type = minaf;
    level = 1;
    max_level = 5;
    destroy_prob = 0.15;
    is_online = false;
    needs_worker = true;
    output = 2;
}


Minac::Minac() {
    type = minac;
    level = 1;
    max_level = 5;
    destroy_prob = 0.1;
    is_online = true;
    needs_worker = true;
    output = 2;
}

Central::Central() {
    type = central;
    level = 1;
    max_level = 1;
    destroy_prob = 0;
    is_online = true;
    needs_worker = true;
    output = 1;
}

Bat::Bat() {
    type = bat;
    level = 1;
    max_level = 5;
    destroy_prob = 0;
    is_online = false;
}

Fund::Fund() {
    type = fund;
    level = 1;
    max_level = 1;
    destroy_prob = 0;
    is_online = false;
}

Serr::Serr() {
    type = serr;
    level = 1;
    max_level = 1;
    destroy_prob = 0;
    is_online = false;
}

Edx::Edx() {
    type = filt;
    level = 1;
    max_level = 2;
    destroy_prob = 0;
    is_online = false;
}
