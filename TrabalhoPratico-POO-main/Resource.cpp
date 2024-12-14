#include "Resource.h"

Resource::Resource() {
    value = 0;
    quantity = 0;
    max = 0;
}

float Resource::GetMax() {
    return max;
}

float Resource::GetQuant() {
    return quantity;
}

string Resource::GetType() {
    return type;
}

float Resource::GetValue() {
    return value;
}

void Resource::SetMax(int& max) {
    this->max = max;
}

void Resource::SetQuant(float& quant) {
    this->quantity = quant;
}


Resource* Resource::Create(string type) {

    Resource* resource;

    if (type == "iron") {
        resource = new Iron;
        resource->value = 1;

    } else if (type == "steel") {
        resource = new Steel;
        resource->value = 2;

    } else if (type == "coal") {
        resource = new Coal;
        resource->value = 1;

    } else if (type == "wood") {
        resource = new Wood;
        resource->value = 1;

    } else if (type == "wood_beam") {
        resource = new WoodBeam;
        resource->value = 2;

    } else if (type == "electricity") {
        resource = new Eletricity;
        resource->value = 1.5;

    } else
        return nullptr;

    resource->quantity = 10000;
    resource->max = 0;
    resource->type = type;
    return resource;

}
