// Verilated -*- C++ -*-
// DESCRIPTION: Verilator output: Symbol table internal header
//
// Internal details; most calling programs do not need this header

#ifndef _Vfsmir2__Syms_H_
#define _Vfsmir2__Syms_H_

#include "verilated.h"

// INCLUDE MODULE CLASSES
#include "Vfsmir2.h"

// SYMS CLASS
class Vfsmir2__Syms : public VerilatedSyms {
  public:
    
    // LOCAL STATE
    const char* __Vm_namep;
    bool __Vm_didInit;
    
    // SUBCELL STATE
    Vfsmir2*                       TOPp;
    
    // CREATORS
    Vfsmir2__Syms(Vfsmir2* topp, const char* namep);
    ~Vfsmir2__Syms() {}
    
    // METHODS
    inline const char* name() { return __Vm_namep; }
    
} VL_ATTR_ALIGNED(64);

#endif // guard
