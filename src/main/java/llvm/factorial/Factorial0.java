package llvm.factorial;

/**
 * @author: hfwei
 * @date: 12/18/22
 * @note: Unfinished!!! See Factorial1 and Factorial2 for full examples.
 */

import static org.bytedeco.llvm.global.LLVM.LLVMAddGlobal;
import static org.bytedeco.llvm.global.LLVM.LLVMConstInt;
import static org.bytedeco.llvm.global.LLVM.LLVMCreateBuilder;
import static org.bytedeco.llvm.global.LLVM.LLVMGetGlobalPassRegistry;
import static org.bytedeco.llvm.global.LLVM.LLVMInitializeCore;
import static org.bytedeco.llvm.global.LLVM.LLVMInitializeNativeAsmParser;
import static org.bytedeco.llvm.global.LLVM.LLVMInitializeNativeAsmPrinter;
import static org.bytedeco.llvm.global.LLVM.LLVMInitializeNativeTarget;
import static org.bytedeco.llvm.global.LLVM.LLVMInt32Type;
import static org.bytedeco.llvm.global.LLVM.LLVMLinkInMCJIT;
import static org.bytedeco.llvm.global.LLVM.LLVMModuleCreateWithName;
import static org.bytedeco.llvm.global.LLVM.LLVMSetInitializer;

import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.PointerPointer;
import org.bytedeco.llvm.LLVM.LLVMBuilderRef;
import org.bytedeco.llvm.LLVM.LLVMModuleRef;
import org.bytedeco.llvm.LLVM.LLVMTypeRef;
import org.bytedeco.llvm.LLVM.LLVMValueRef;

public class Factorial0 {
  public static void main(String[] args) {
    LLVMInitializeCore(LLVMGetGlobalPassRegistry());
    LLVMLinkInMCJIT();
    LLVMInitializeNativeAsmPrinter();
    LLVMInitializeNativeAsmParser();
    LLVMInitializeNativeTarget();

    LLVMModuleRef module = LLVMModuleCreateWithName("Factorial0");
    LLVMBuilderRef builder = LLVMCreateBuilder();
    LLVMTypeRef i32Type = LLVMInt32Type();

    LLVMValueRef zero = LLVMConstInt(i32Type, 0, 0);
    LLVMValueRef globalVar = LLVMAddGlobal(module, i32Type, "globalVar");
    LLVMSetInitializer(globalVar, zero);

    LLVMTypeRef returnType = i32Type;
    PointerPointer<Pointer> argumentTypes = new PointerPointer<>(2)
        .put(0, i32Type)
        .put(1, i32Type);
  }
}
