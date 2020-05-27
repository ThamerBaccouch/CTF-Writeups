// Verilated -*- C++ -*-
// DESCRIPTION: Verilator output: Symbol table internal header
//
// Internal details; most calling programs do not need this header

#ifndef _Vfsmir__Syms_H_
#define _Vfsmir__Syms_H_

#include "verilated.h"

// INCLUDE MODULE CLASSES
#include "Vfsmir.h"

// SYMS CLASS
class Vfsmir__Syms : public VerilatedSyms {
  public:
    
    // LOCAL STATE
    const char* __Vm_namep;
    bool __Vm_didInit;
    
    // SUBCELL STATE
    Vfsmir*                        TOPp;
    
    // CREATORS
    Vfsmir__Syms(Vfsmir* topp, const char* namep);
    ~Vfsmir__Syms() {}
    
    // METHODS
    inline const char* name() { return __Vm_namep; }
    
} VL_ATTR_ALIGNED(64);

#endif // guard
