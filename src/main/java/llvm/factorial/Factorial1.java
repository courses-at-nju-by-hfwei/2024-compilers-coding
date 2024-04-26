package llvm.factorial;

import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.PointerPointer;
import org.bytedeco.llvm.LLVM.*;
import static org.bytedeco.llvm.global.LLVM.*;

public class Factorial1 {
    static void factorial1opt0() {
        LLVMInitializeCore(LLVMGetGlobalPassRegistry());
        LLVMLinkInMCJIT();
        LLVMInitializeNativeAsmPrinter();
        LLVMInitializeNativeAsmParser();
        LLVMInitializeNativeTarget();

        LLVMContextRef context = LLVMContextCreate();
        LLVMModuleRef module = LLVMModuleCreateWithNameInContext("Factorial1", context);
        LLVMBuilderRef builder = LLVMCreateBuilderInContext(context);

        LLVMTypeRef i32Type = LLVMInt32TypeInContext(context);
        LLVMTypeRef i8Type = LLVMInt8TypeInContext(context);
        LLVMTypeRef i8ArrayType = LLVMPointerType(i8Type, 0);
        LLVMTypeRef i8ArrayArrayType = LLVMPointerType(i8ArrayType, 0);
        LLVMValueRef zero = LLVMConstInt(i32Type, 0, 0);
        LLVMValueRef one = LLVMConstInt(i32Type, 1, 0);

        PointerPointer<Pointer> mainParamTypes = new PointerPointer<>(2)
                .put(0, i32Type)
                .put(1, i8ArrayArrayType);

        PointerPointer<Pointer> factorialParamTypes = new PointerPointer<>(1)
                .put(0, i32Type);

        // i32 main(i32, i8**)
        LLVMTypeRef mainRetType = LLVMFunctionType(i32Type, mainParamTypes, 2, 0);
        // i32 factorial(i32)
        LLVMTypeRef factorialRetType = LLVMFunctionType(i32Type, factorialParamTypes, 1, 0);

        // define function main and factorial
        LLVMValueRef mainFunction = LLVMAddFunction(module, "main", mainRetType);
        LLVMValueRef factorialFunction = LLVMAddFunction(module, "factorial", factorialRetType);

        // append code at main function
        LLVMBasicBlockRef mainEntry = LLVMAppendBasicBlock(mainFunction, "");
        LLVMPositionBuilderAtEnd(builder, mainEntry);

        // get function param
        LLVMValueRef arg1 = LLVMGetParam(mainFunction, 0);
        LLVMValueRef arg2 = LLVMGetParam(mainFunction, 1);

        // alloca and store param
        LLVMValueRef alloca1 = LLVMBuildAlloca(builder, i32Type, "");
        LLVMValueRef alloca2 = LLVMBuildAlloca(builder, i32Type, "");
        LLVMValueRef alloca3 = LLVMBuildAlloca(builder, i8ArrayArrayType, "");
        LLVMBuildStore(builder, zero, alloca1);
        LLVMBuildStore(builder, arg1, alloca2);
        LLVMBuildStore(builder, arg2, alloca3);

        // call factorial(2)
        PointerPointer<Pointer> argsv1 = new PointerPointer<>(1)
                .put(0, LLVMConstInt(i32Type, 2, 0));
        LLVMValueRef call1 = LLVMBuildCall(builder, factorialFunction, argsv1, 1, "");
        // factorial(2) * 7
        LLVMValueRef mul1 = LLVMBuildMul(builder, call1, LLVMConstInt(i32Type, 7, 0), "");
        // factorial(2) * 7 == 42
        LLVMValueRef icmp1 = LLVMBuildICmp(builder, LLVMIntEQ, mul1, LLVMConstInt(i32Type, 42, 0), "");
        // i1 -> i32
        LLVMValueRef zext1 = LLVMBuildZExt(builder, icmp1, i32Type, "");
        // return
        LLVMBuildRet(builder, zext1);

        // append code at factorial function
        LLVMBasicBlockRef factorialEntry = LLVMAppendBasicBlock(factorialFunction, "");
        LLVMPositionBuilderAtEnd(builder, factorialEntry);
        // alloca i32 and store arg
        LLVMValueRef arg3 = LLVMGetParam(factorialFunction, 0);
        LLVMValueRef alloca4 = LLVMBuildAlloca(builder, i32Type, "");
        LLVMValueRef alloca5 = LLVMBuildAlloca(builder, i32Type, "");
        LLVMBuildStore(builder, arg3, alloca5);
        LLVMValueRef load1 = LLVMBuildLoad(builder, alloca5, "");
        // val == 0
        LLVMValueRef icmp2 = LLVMBuildICmp(builder, LLVMIntEQ, load1, zero, "");

        // append 3 basic block
        LLVMBasicBlockRef entry1 = LLVMAppendBasicBlock(factorialFunction, "");
        LLVMBasicBlockRef entry2 = LLVMAppendBasicBlock(factorialFunction, "");
        LLVMBasicBlockRef entry3 = LLVMAppendBasicBlock(factorialFunction, "");

        // if val == 0 goto entry1 else goto entry2
        LLVMBuildCondBr(builder, icmp2, entry1, entry2);

        // append code at entry1
        LLVMPositionBuilderAtEnd(builder, entry1);
        // store 1 to alloca4
        LLVMBuildStore(builder, one, alloca4);
        // goto entry3
        LLVMBuildBr(builder, entry3);

        // append code at entry2
        LLVMPositionBuilderAtEnd(builder, entry2);
        // load2, load3 = val
        LLVMValueRef load2 = LLVMBuildLoad(builder, alloca5, "");
        LLVMValueRef load3 = LLVMBuildLoad(builder, alloca5, "");
        // val - 1
        LLVMValueRef sub1 = LLVMBuildSub(builder, load3, one, "");
        // call factorial(val - 1)
        PointerPointer<Pointer> argsv2 = new PointerPointer<>(1)
                .put(0, sub1);
        LLVMValueRef call2 = LLVMBuildCall(builder, factorialFunction, argsv2, 1, "");
        // val * factorial(val - 1)
        LLVMValueRef mul2 = LLVMBuildMul(builder, load2, call2, "");
        // store result to alloca4
        LLVMBuildStore(builder, mul2, alloca4);
        // goto entry3
        LLVMBuildBr(builder, entry3);

        // append code at entry3
        LLVMPositionBuilderAtEnd(builder, entry3);
        // return value at alloca4
        LLVMValueRef load4 = LLVMBuildLoad(builder, alloca4, "");
        LLVMBuildRet(builder, load4);

        LLVMDumpModule(module);
    }

    static void factorial1opt1() {
        LLVMInitializeCore(LLVMGetGlobalPassRegistry());
        LLVMLinkInMCJIT();
        LLVMInitializeNativeAsmPrinter();
        LLVMInitializeNativeAsmParser();
        LLVMInitializeNativeTarget();

        LLVMContextRef context = LLVMContextCreate();
        LLVMModuleRef module = LLVMModuleCreateWithNameInContext("Factorial1", context);
        LLVMBuilderRef builder = LLVMCreateBuilderInContext(context);

        LLVMTypeRef i32Type = LLVMInt32TypeInContext(context);
        LLVMTypeRef i8Type = LLVMInt8TypeInContext(context);
        LLVMTypeRef i8ArrayType = LLVMPointerType(i8Type, 0);
        LLVMTypeRef i8ArrayArrayType = LLVMPointerType(i8ArrayType, 0);
        LLVMValueRef zero = LLVMConstInt(i32Type, 0, 0);
        LLVMValueRef one = LLVMConstInt(i32Type, 1, 0);

        PointerPointer<Pointer> mainParamTypes = new PointerPointer<>(2)
                .put(0, i32Type)
                .put(1, i8ArrayArrayType);

        PointerPointer<Pointer> factorialParamTypes = new PointerPointer<>(1)
                .put(0, i32Type);

        LLVMTypeRef mainRetType = LLVMFunctionType(i32Type, mainParamTypes, 2, 0);
        LLVMTypeRef factorialRetType = LLVMFunctionType(i32Type, factorialParamTypes, 1, 0);

        LLVMValueRef mainFunction = LLVMAddFunction(module, "main", mainRetType);
        LLVMValueRef factorialFunction = LLVMAddFunction(module, "factorial", factorialRetType);

        LLVMBasicBlockRef mainEntry = LLVMAppendBasicBlock(mainFunction, "");
        LLVMPositionBuilderAtEnd(builder, mainEntry);

        // 相比于opt0, opt1并没有store main函数参数的中间代码，因为main函数的参数并没有用到

        // call factorial(2)
        PointerPointer<Pointer> argsv1 = new PointerPointer<>(1)
                .put(0, LLVMConstInt(i32Type, 2, 0));
        LLVMValueRef call1 = LLVMBuildCall(builder, factorialFunction, argsv1, 1, "");
        // factorial(2) * 7
        LLVMValueRef mul1 = LLVMBuildMul(builder, call1, LLVMConstInt(i32Type, 7, 0), "");
        // factorial(2) * 7 == 42
        LLVMValueRef icmp1 = LLVMBuildICmp(builder, LLVMIntEQ, mul1, LLVMConstInt(i32Type, 42, 0), "");
        // i1 -> i32
        LLVMValueRef zext1 = LLVMBuildZExt(builder, icmp1, i32Type, "");
        // return
        LLVMBuildRet(builder, zext1);

        // append code at factorial
        LLVMBasicBlockRef factorialEntry = LLVMAppendBasicBlock(factorialFunction, "");
        LLVMPositionBuilderAtEnd(builder, factorialEntry);
        // get arg(val)
        LLVMValueRef arg3 = LLVMGetParam(factorialFunction, 0);
        // val == 0
        LLVMValueRef icmp2 = LLVMBuildICmp(builder, LLVMIntEQ, arg3, zero, "");

        LLVMBasicBlockRef entry1 = LLVMAppendBasicBlock(factorialFunction, "");
        LLVMBasicBlockRef entry2 = LLVMAppendBasicBlock(factorialFunction, "");

        // if val == 0 goto entry2 else goto entry1
        LLVMBuildCondBr(builder, icmp2, entry2, entry1);

        // append code at entry1
        LLVMPositionBuilderAtEnd(builder, entry1);
        // val - 1
        LLVMValueRef add1 = LLVMBuildAdd(builder, arg3, LLVMConstInt(i32Type, -1, 0), "");
        // call factorial(val - 1)
        PointerPointer<Pointer> argsv2 = new PointerPointer<>(1)
                .put(0, add1);
        LLVMValueRef call2 = LLVMBuildCall(builder, factorialFunction, argsv2, 1, "");
        // val * factorial(val - 1)
        LLVMValueRef mul2 = LLVMBuildMul(builder, call2, arg3, "");
        // goto entry2
        LLVMBuildBr(builder, entry2);

        // append code at entry2
        LLVMPositionBuilderAtEnd(builder, entry2);
        // build phi: if prev block is entry1, phi1 = mul2; if prev block is factorialEntry, phi1 = 1
        // more about phi: https://llvm.org/docs/LangRef.html#phi-instruction
        LLVMValueRef phi1 = LLVMBuildPhi(builder, i32Type, "");
        PointerPointer<Pointer> phiValues = new PointerPointer<>(2)
                .put(0, mul2)
                .put(1, one);
        PointerPointer<Pointer> phiBlocks = new PointerPointer<>(2)
                .put(0, entry1)
                .put(1, factorialEntry);
        LLVMAddIncoming(phi1, phiValues, phiBlocks, 2);

        // return result
        LLVMBuildRet(builder, phi1);

        LLVMDumpModule(module);
    }

    public static void main(String[] args) {
        factorial1opt0();
        factorial1opt1();
    }
}
