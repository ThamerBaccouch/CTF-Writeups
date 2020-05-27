// Verilated -*- C++ -*-
// DESCRIPTION: Verilator output: Design implementation internals
// See Vfsmir.h for the primary calling header

#include "Vfsmir.h"            // For This
#include "Vfsmir__Syms.h"


//--------------------
// STATIC VARIABLES


//--------------------

VL_CTOR_IMP(Vfsmir) {
    Vfsmir__Syms* __restrict vlSymsp = __VlSymsp = new Vfsmir__Syms(this, name());
    Vfsmir* __restrict vlTOPp VL_ATTR_UNUSED = vlSymsp->TOPp;
    // Reset internal values
    
    // Reset structure values
    _ctor_var_reset();
}

void Vfsmir::__Vconfigure(Vfsmir__Syms* vlSymsp, bool first) {
    if (0 && first) {}  // Prevent unused
    this->__VlSymsp = vlSymsp;
}

Vfsmir::~Vfsmir() {
    delete __VlSymsp; __VlSymsp=NULL;
}

//--------------------


void Vfsmir::eval() {
    VL_DEBUG_IF(VL_DBG_MSGF("+++++TOP Evaluate Vfsmir::eval\n"); );
    Vfsmir__Syms* __restrict vlSymsp = this->__VlSymsp;  // Setup global symbol table
    Vfsmir* __restrict vlTOPp VL_ATTR_UNUSED = vlSymsp->TOPp;
#ifdef VL_DEBUG
    // Debug assertions
    _eval_debug_assertions();
#endif // VL_DEBUG
    // Initialize
    if (VL_UNLIKELY(!vlSymsp->__Vm_didInit)) _eval_initial_loop(vlSymsp);
    // Evaluate till stable
    int __VclockLoop = 0;
    QData __Vchange = 1;
    while (VL_LIKELY(__Vchange)) {
	VL_DEBUG_IF(VL_DBG_MSGF("+ Clock loop\n"););
	_eval(vlSymsp);
	__Vchange = _change_request(vlSymsp);
	if (VL_UNLIKELY(++__VclockLoop > 100)) VL_FATAL_MT(__FILE__,__LINE__,__FILE__,"Verilated model didn't converge");
    }
}

void Vfsmir::_eval_initial_loop(Vfsmir__Syms* __restrict vlSymsp) {
    vlSymsp->__Vm_didInit = true;
    _eval_initial(vlSymsp);
    int __VclockLoop = 0;
    QData __Vchange = 1;
    while (VL_LIKELY(__Vchange)) {
	_eval_settle(vlSymsp);
	_eval(vlSymsp);
	__Vchange = _change_request(vlSymsp);
	if (VL_UNLIKELY(++__VclockLoop > 100)) VL_FATAL_MT(__FILE__,__LINE__,__FILE__,"Verilated model didn't DC converge");
    }
}

//--------------------
// Internal Methods

VL_INLINE_OPT void Vfsmir::_sequent__TOP__1(Vfsmir__Syms* __restrict vlSymsp) {
    VL_DEBUG_IF(VL_DBG_MSGF("+    Vfsmir::_sequent__TOP__1\n"); );
    Vfsmir* __restrict vlTOPp VL_ATTR_UNUSED = vlSymsp->TOPp;
    // Variables
    VL_SIG8(__Vdly__c,7,0);
    // Body
    __Vdly__c = vlTOPp->c;
    // ALWAYS at fsmir.sv:13
    __Vdly__c = 0U;
    if ((0x80U & (IData)(vlTOPp->c))) {
	__Vdly__c = 0U;
    } else {
	if ((0x40U & (IData)(vlTOPp->c))) {
	    __Vdly__c = 0U;
	} else {
	    if ((0x20U & (IData)(vlTOPp->c))) {
		if ((0x10U & (IData)(vlTOPp->c))) {
		    if ((8U & (IData)(vlTOPp->c))) {
			if ((4U & (IData)(vlTOPp->c))) {
			    __Vdly__c = 0U;
			} else {
			    if ((2U & (IData)(vlTOPp->c))) {
				if ((1U & (IData)(vlTOPp->c))) {
				    __Vdly__c = 0U;
				} else {
				    if ((0x47U == ((IData)(vlTOPp->di) 
						   ^ (IData)(vlTOPp->c)))) {
					__Vdly__c = 0x3bU;
				    }
				}
			    } else {
				if ((1U & (IData)(vlTOPp->c))) {
				    if ((6U == ((IData)(vlTOPp->di) 
						^ (IData)(vlTOPp->c)))) {
					__Vdly__c = 0x3aU;
				    }
				} else {
				    if ((0x4cU == ((IData)(vlTOPp->di) 
						   ^ (IData)(vlTOPp->c)))) {
					__Vdly__c = 0x39U;
				    }
				}
			    }
			}
		    } else {
			if ((4U & (IData)(vlTOPp->c))) {
			    if ((2U & (IData)(vlTOPp->c))) {
				if ((1U & (IData)(vlTOPp->c))) {
				    if ((0x5fU == ((IData)(vlTOPp->di) 
						   ^ (IData)(vlTOPp->c)))) {
					__Vdly__c = 0x38U;
				    }
				} else {
				    if ((0x51U == ((IData)(vlTOPp->di) 
						   ^ (IData)(vlTOPp->c)))) {
					__Vdly__c = 0x37U;
				    }
				}
			    } else {
				if ((1U & (IData)(vlTOPp->c))) {
				    if ((0x5cU == ((IData)(vlTOPp->di) 
						   ^ (IData)(vlTOPp->c)))) {
					__Vdly__c = 0x36U;
				    }
				} else {
				    if ((0x46U == ((IData)(vlTOPp->di) 
						   ^ (IData)(vlTOPp->c)))) {
					__Vdly__c = 0x35U;
				    }
				}
			    }
			} else {
			    if ((2U & (IData)(vlTOPp->c))) {
				if ((1U & (IData)(vlTOPp->c))) {
				    if ((0x6cU == ((IData)(vlTOPp->di) 
						   ^ (IData)(vlTOPp->c)))) {
					__Vdly__c = 0x34U;
				    }
				} else {
				    if ((0x5cU == ((IData)(vlTOPp->di) 
						   ^ (IData)(vlTOPp->c)))) {
					__Vdly__c = 0x33U;
				    }
				}
			    } else {
				if ((1U & (IData)(vlTOPp->c))) {
				    if ((0x5eU == ((IData)(vlTOPp->di) 
						   ^ (IData)(vlTOPp->c)))) {
					__Vdly__c = 0x32U;
				    }
				} else {
				    if ((0x59U == ((IData)(vlTOPp->di) 
						   ^ (IData)(vlTOPp->c)))) {
					__Vdly__c = 0x31U;
				    }
				}
			    }
			}
		    }
		} else {
		    if ((8U & (IData)(vlTOPp->c))) {
			if ((4U & (IData)(vlTOPp->c))) {
			    if ((2U & (IData)(vlTOPp->c))) {
				if ((1U & (IData)(vlTOPp->c))) {
				    if ((0x5bU == ((IData)(vlTOPp->di) 
						   ^ (IData)(vlTOPp->c)))) {
					__Vdly__c = 0x30U;
				    }
				} else {
				    if ((0x4fU == ((IData)(vlTOPp->di) 
						   ^ (IData)(vlTOPp->c)))) {
					__Vdly__c = 0x2fU;
				    }
				}
			    } else {
				if ((1U & (IData)(vlTOPp->c))) {
				    if ((0x59U == ((IData)(vlTOPp->di) 
						   ^ (IData)(vlTOPp->c)))) {
					__Vdly__c = 0x2eU;
				    }
				} else {
				    if ((0x43U == ((IData)(vlTOPp->di) 
						   ^ (IData)(vlTOPp->c)))) {
					__Vdly__c = 0x2dU;
				    }
				}
			    }
			} else {
			    if ((2U & (IData)(vlTOPp->c))) {
				if ((1U & (IData)(vlTOPp->c))) {
				    if ((0x45U == ((IData)(vlTOPp->di) 
						   ^ (IData)(vlTOPp->c)))) {
					__Vdly__c = 0x2cU;
				    }
				} else {
				    if ((0x75U == ((IData)(vlTOPp->di) 
						   ^ (IData)(vlTOPp->c)))) {
					__Vdly__c = 0x2bU;
				    }
				}
			    } else {
				if ((1U & (IData)(vlTOPp->c))) {
				    if ((0x50U == ((IData)(vlTOPp->di) 
						   ^ (IData)(vlTOPp->c)))) {
					__Vdly__c = 0x2aU;
				    }
				} else {
				    if ((0x4bU == ((IData)(vlTOPp->di) 
						   ^ (IData)(vlTOPp->c)))) {
					__Vdly__c = 0x29U;
				    }
				}
			    }
			}
		    } else {
			if ((4U & (IData)(vlTOPp->c))) {
			    if ((2U & (IData)(vlTOPp->c))) {
				if ((1U & (IData)(vlTOPp->c))) {
				    if ((0x49U == ((IData)(vlTOPp->di) 
						   ^ (IData)(vlTOPp->c)))) {
					__Vdly__c = 0x28U;
				    }
				} else {
				    if ((0x47U == ((IData)(vlTOPp->di) 
						   ^ (IData)(vlTOPp->c)))) {
					__Vdly__c = 0x27U;
				    }
				}
			    } else {
				if ((1U & (IData)(vlTOPp->c))) {
				    if ((0x43U == ((IData)(vlTOPp->di) 
						   ^ (IData)(vlTOPp->c)))) {
					__Vdly__c = 0x26U;
				    }
				} else {
				    if ((0x7bU == ((IData)(vlTOPp->di) 
						   ^ (IData)(vlTOPp->c)))) {
					__Vdly__c = 0x25U;
				    }
				}
			    }
			} else {
			    if ((2U & (IData)(vlTOPp->c))) {
				if ((1U & (IData)(vlTOPp->c))) {
				    if ((0x4bU == ((IData)(vlTOPp->di) 
						   ^ (IData)(vlTOPp->c)))) {
					__Vdly__c = 0x24U;
				    }
				} else {
				    if ((0x56U == ((IData)(vlTOPp->di) 
						   ^ (IData)(vlTOPp->c)))) {
					__Vdly__c = 0x23U;
				    }
				}
			    } else {
				if ((1U & (IData)(vlTOPp->c))) {
				    if ((0x48U == ((IData)(vlTOPp->di) 
						   ^ (IData)(vlTOPp->c)))) {
					__Vdly__c = 0x22U;
				    }
				} else {
				    if ((0x57U == ((IData)(vlTOPp->di) 
						   ^ (IData)(vlTOPp->c)))) {
					__Vdly__c = 0x21U;
				    }
				}
			    }
			}
		    }
		}
	    } else {
		if ((0x10U & (IData)(vlTOPp->c))) {
		    if ((8U & (IData)(vlTOPp->c))) {
			if ((4U & (IData)(vlTOPp->c))) {
			    if ((2U & (IData)(vlTOPp->c))) {
				if ((1U & (IData)(vlTOPp->c))) {
				    if ((0x40U == ((IData)(vlTOPp->di) 
						   ^ (IData)(vlTOPp->c)))) {
					__Vdly__c = 0x20U;
				    }
				} else {
				    if ((0x5dU == ((IData)(vlTOPp->di) 
						   ^ (IData)(vlTOPp->c)))) {
					__Vdly__c = 0x1fU;
				    }
				}
			    } else {
				if ((1U & (IData)(vlTOPp->c))) {
				    if ((0x42U == ((IData)(vlTOPp->di) 
						   ^ (IData)(vlTOPp->c)))) {
					__Vdly__c = 0x1eU;
				    }
				} else {
				    if ((0x68U == ((IData)(vlTOPp->di) 
						   ^ (IData)(vlTOPp->c)))) {
					__Vdly__c = 0x1dU;
				    }
				}
			    }
			} else {
			    if ((2U & (IData)(vlTOPp->c))) {
				if ((1U & (IData)(vlTOPp->c))) {
				    if ((0x68U == ((IData)(vlTOPp->di) 
						   ^ (IData)(vlTOPp->c)))) {
					__Vdly__c = 0x1cU;
				    }
				} else {
				    if ((0x6fU == ((IData)(vlTOPp->di) 
						   ^ (IData)(vlTOPp->c)))) {
					__Vdly__c = 0x1bU;
				    }
				}
			    } else {
				if ((1U & (IData)(vlTOPp->c))) {
				    if ((0x73U == ((IData)(vlTOPp->di) 
						   ^ (IData)(vlTOPp->c)))) {
					__Vdly__c = 0x1aU;
				    }
				} else {
				    if ((0x47U == ((IData)(vlTOPp->di) 
						   ^ (IData)(vlTOPp->c)))) {
					__Vdly__c = 0x19U;
				    }
				}
			    }
			}
		    } else {
			if ((4U & (IData)(vlTOPp->c))) {
			    if ((2U & (IData)(vlTOPp->c))) {
				if ((1U & (IData)(vlTOPp->c))) {
				    if ((0x64U == ((IData)(vlTOPp->di) 
						   ^ (IData)(vlTOPp->c)))) {
					__Vdly__c = 0x18U;
				    }
				} else {
				    if ((0x7fU == ((IData)(vlTOPp->di) 
						   ^ (IData)(vlTOPp->c)))) {
					__Vdly__c = 0x17U;
				    }
				}
			    } else {
				if ((1U & (IData)(vlTOPp->c))) {
				    if ((0x4aU == ((IData)(vlTOPp->di) 
						   ^ (IData)(vlTOPp->c)))) {
					__Vdly__c = 0x16U;
				    }
				} else {
				    if ((0x73U == ((IData)(vlTOPp->di) 
						   ^ (IData)(vlTOPp->c)))) {
					__Vdly__c = 0x15U;
				    }
				}
			    }
			} else {
			    if ((2U & (IData)(vlTOPp->c))) {
				if ((1U & (IData)(vlTOPp->c))) {
				    if ((0x7cU == ((IData)(vlTOPp->di) 
						   ^ (IData)(vlTOPp->c)))) {
					__Vdly__c = 0x14U;
				    }
				} else {
				    if ((0x7eU == ((IData)(vlTOPp->di) 
						   ^ (IData)(vlTOPp->c)))) {
					__Vdly__c = 0x13U;
				    }
				}
			    } else {
				if ((1U & (IData)(vlTOPp->c))) {
				    if ((0x78U == ((IData)(vlTOPp->di) 
						   ^ (IData)(vlTOPp->c)))) {
					__Vdly__c = 0x12U;
				    }
				} else {
				    if ((0x62U == ((IData)(vlTOPp->di) 
						   ^ (IData)(vlTOPp->c)))) {
					__Vdly__c = 0x11U;
				    }
				}
			    }
			}
		    }
		} else {
		    if ((8U & (IData)(vlTOPp->c))) {
			if ((4U & (IData)(vlTOPp->c))) {
			    if ((2U & (IData)(vlTOPp->c))) {
				if ((1U & (IData)(vlTOPp->c))) {
				    if ((0x6aU == ((IData)(vlTOPp->di) 
						   ^ (IData)(vlTOPp->c)))) {
					__Vdly__c = 0x10U;
				    }
				} else {
				    if ((0x58U == ((IData)(vlTOPp->di) 
						   ^ (IData)(vlTOPp->c)))) {
					__Vdly__c = 0xfU;
				    }
				}
			    } else {
				if ((1U & (IData)(vlTOPp->c))) {
				    if ((0x60U == ((IData)(vlTOPp->di) 
						   ^ (IData)(vlTOPp->c)))) {
					__Vdly__c = 0xeU;
				    }
				} else {
				    if ((0x69U == ((IData)(vlTOPp->di) 
						   ^ (IData)(vlTOPp->c)))) {
					__Vdly__c = 0xdU;
				    }
				}
			    }
			} else {
			    if ((2U & (IData)(vlTOPp->c))) {
				if ((1U & (IData)(vlTOPp->c))) {
				    if ((0x7fU == ((IData)(vlTOPp->di) 
						   ^ (IData)(vlTOPp->c)))) {
					__Vdly__c = 0xcU;
				    }
				} else {
				    if ((0x79U == ((IData)(vlTOPp->di) 
						   ^ (IData)(vlTOPp->c)))) {
					__Vdly__c = 0xbU;
				    }
				}
			    } else {
				if ((1U & (IData)(vlTOPp->c))) {
				    if ((0x70U == ((IData)(vlTOPp->di) 
						   ^ (IData)(vlTOPp->c)))) {
					__Vdly__c = 0xaU;
				    }
				} else {
				    if ((0x5bU == ((IData)(vlTOPp->di) 
						   ^ (IData)(vlTOPp->c)))) {
					__Vdly__c = 9U;
				    }
				}
			    }
			}
		    } else {
			if ((4U & (IData)(vlTOPp->c))) {
			    if ((2U & (IData)(vlTOPp->c))) {
				if ((1U & (IData)(vlTOPp->c))) {
				    if ((0x7cU == ((IData)(vlTOPp->di) 
						   ^ (IData)(vlTOPp->c)))) {
					__Vdly__c = 8U;
				    }
				} else {
				    if ((0x40U == ((IData)(vlTOPp->di) 
						   ^ (IData)(vlTOPp->c)))) {
					__Vdly__c = 7U;
				    }
				}
			    } else {
				if ((1U & (IData)(vlTOPp->c))) {
				    if ((0x51U == ((IData)(vlTOPp->di) 
						   ^ (IData)(vlTOPp->c)))) {
					__Vdly__c = 6U;
				    }
				} else {
				    if ((0x47U == ((IData)(vlTOPp->di) 
						   ^ (IData)(vlTOPp->c)))) {
					__Vdly__c = 5U;
				    }
				}
			    }
			} else {
			    if ((2U & (IData)(vlTOPp->c))) {
				if ((1U & (IData)(vlTOPp->c))) {
				    if ((0x77U == ((IData)(vlTOPp->di) 
						   ^ (IData)(vlTOPp->c)))) {
					__Vdly__c = 4U;
				    }
				} else {
				    if ((0x71U == ((IData)(vlTOPp->di) 
						   ^ (IData)(vlTOPp->c)))) {
					__Vdly__c = 3U;
				    }
				}
			    } else {
				if ((1U & (IData)(vlTOPp->c))) {
				    if ((0x74U == ((IData)(vlTOPp->di) 
						   ^ (IData)(vlTOPp->c)))) {
					__Vdly__c = 2U;
				    }
				} else {
				    if ((0x6aU == ((IData)(vlTOPp->di) 
						   ^ (IData)(vlTOPp->c)))) {
					__Vdly__c = 1U;
				    }
				}
			    }
			}
		    }
		}
	    }
	}
    }
    vlTOPp->c = __Vdly__c;
    vlTOPp->solved = (0x3bU == (IData)(vlTOPp->c));
}

void Vfsmir::_initial__TOP__2(Vfsmir__Syms* __restrict vlSymsp) {
    VL_DEBUG_IF(VL_DBG_MSGF("+    Vfsmir::_initial__TOP__2\n"); );
    Vfsmir* __restrict vlTOPp VL_ATTR_UNUSED = vlSymsp->TOPp;
    // Body
    // INITIAL at fsmir.sv:9
    vlTOPp->c = 0U;
}

void Vfsmir::_settle__TOP__3(Vfsmir__Syms* __restrict vlSymsp) {
    VL_DEBUG_IF(VL_DBG_MSGF("+    Vfsmir::_settle__TOP__3\n"); );
    Vfsmir* __restrict vlTOPp VL_ATTR_UNUSED = vlSymsp->TOPp;
    // Body
    vlTOPp->solved = (0x3bU == (IData)(vlTOPp->c));
}

void Vfsmir::_eval(Vfsmir__Syms* __restrict vlSymsp) {
    VL_DEBUG_IF(VL_DBG_MSGF("+    Vfsmir::_eval\n"); );
    Vfsmir* __restrict vlTOPp VL_ATTR_UNUSED = vlSymsp->TOPp;
    // Body
    if (((IData)(vlTOPp->clk) & (~ (IData)(vlTOPp->__Vclklast__TOP__clk)))) {
	vlTOPp->_sequent__TOP__1(vlSymsp);
    }
    // Final
    vlTOPp->__Vclklast__TOP__clk = vlTOPp->clk;
}

void Vfsmir::_eval_initial(Vfsmir__Syms* __restrict vlSymsp) {
    VL_DEBUG_IF(VL_DBG_MSGF("+    Vfsmir::_eval_initial\n"); );
    Vfsmir* __restrict vlTOPp VL_ATTR_UNUSED = vlSymsp->TOPp;
    // Body
    vlTOPp->_initial__TOP__2(vlSymsp);
}

void Vfsmir::final() {
    VL_DEBUG_IF(VL_DBG_MSGF("+    Vfsmir::final\n"); );
    // Variables
    Vfsmir__Syms* __restrict vlSymsp = this->__VlSymsp;
    Vfsmir* __restrict vlTOPp VL_ATTR_UNUSED = vlSymsp->TOPp;
}

void Vfsmir::_eval_settle(Vfsmir__Syms* __restrict vlSymsp) {
    VL_DEBUG_IF(VL_DBG_MSGF("+    Vfsmir::_eval_settle\n"); );
    Vfsmir* __restrict vlTOPp VL_ATTR_UNUSED = vlSymsp->TOPp;
    // Body
    vlTOPp->_settle__TOP__3(vlSymsp);
}

VL_INLINE_OPT QData Vfsmir::_change_request(Vfsmir__Syms* __restrict vlSymsp) {
    VL_DEBUG_IF(VL_DBG_MSGF("+    Vfsmir::_change_request\n"); );
    Vfsmir* __restrict vlTOPp VL_ATTR_UNUSED = vlSymsp->TOPp;
    // Body
    // Change detection
    QData __req = false;  // Logically a bool
    return __req;
}

#ifdef VL_DEBUG
void Vfsmir::_eval_debug_assertions() {
    VL_DEBUG_IF(VL_DBG_MSGF("+    Vfsmir::_eval_debug_assertions\n"); );
    // Body
    if (VL_UNLIKELY((clk & 0xfeU))) {
	Verilated::overWidthError("clk");}
}
#endif // VL_DEBUG

void Vfsmir::_ctor_var_reset() {
    VL_DEBUG_IF(VL_DBG_MSGF("+    Vfsmir::_ctor_var_reset\n"); );
    // Body
    clk = VL_RAND_RESET_I(1);
    di = VL_RAND_RESET_I(8);
    c = VL_RAND_RESET_I(8);
    solved = VL_RAND_RESET_I(1);
    __Vclklast__TOP__clk = VL_RAND_RESET_I(1);
}
