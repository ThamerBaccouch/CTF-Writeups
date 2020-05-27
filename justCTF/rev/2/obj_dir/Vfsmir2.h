// Verilated -*- C++ -*-
// DESCRIPTION: Verilator output: Primary design header
//
// This header should be included by all source files instantiating the design.
// The class here is then constructed to instantiate the design.
// See the Verilator manual for examples.

#ifndef _Vfsmir2_H_
#define _Vfsmir2_H_

#include "verilated.h"

class Vfsmir2__Syms;

//----------

VL_MODULE(Vfsmir2) {
  public:
    
    // PORTS
    // The application code writes and reads these signals to
    // propagate new values into/out from the Verilated model.
    VL_IN8(clk,0,0);
    VL_IN8(di,7,0);
    VL_OUT8(solved,0,0);
    
    // LOCAL SIGNALS
    // Internals; generally not touched by application code
    VL_SIG16(fsmir2__DOT__c,8,0);
    
    // LOCAL VARIABLES
    // Internals; generally not touched by application code
    VL_SIG8(__Vclklast__TOP__clk,0,0);
    
    // INTERNAL VARIABLES
    // Internals; generally not touched by application code
    Vfsmir2__Syms* __VlSymsp;  // Symbol table
    
    // PARAMETERS
    // Parameters marked /*verilator public*/ for use by application code
    
    // CONSTRUCTORS
  private:
    Vfsmir2& operator= (const Vfsmir2&);  ///< Copying not allowed
    Vfsmir2(const Vfsmir2&);  ///< Copying not allowed
  public:
    /// Construct the model; called by application code
    /// The special name  may be used to make a wrapper with a
    /// single model invisible WRT DPI scope names.
    Vfsmir2(const char* name="TOP");
    /// Destroy the model; called (often implicitly) by application code
    ~Vfsmir2();
    
    // API METHODS
    /// Evaluate the model.  Application must call when inputs change.
    void eval();
    /// Simulation complete, run final blocks.  Application must call on completion.
    void final();
    
    // INTERNAL METHODS
  private:
    static void _eval_initial_loop(Vfsmir2__Syms* __restrict vlSymsp);
  public:
    void __Vconfigure(Vfsmir2__Syms* symsp, bool first);
  private:
    static QData _change_request(Vfsmir2__Syms* __restrict vlSymsp);
    void _ctor_var_reset();
  public:
    static void _eval(Vfsmir2__Syms* __restrict vlSymsp);
  private:
#ifdef VL_DEBUG
    void _eval_debug_assertions();
#endif // VL_DEBUG
  public:
    static void _eval_initial(Vfsmir2__Syms* __restrict vlSymsp);
    static void _eval_settle(Vfsmir2__Syms* __restrict vlSymsp);
    static void _initial__TOP__2(Vfsmir2__Syms* __restrict vlSymsp);
    static void _sequent__TOP__1(Vfsmir2__Syms* __restrict vlSymsp);
    static void _settle__TOP__3(Vfsmir2__Syms* __restrict vlSymsp);
} VL_ATTR_ALIGNED(128);

#endif // guard
