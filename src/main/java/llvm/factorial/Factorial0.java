package llvm.factorial;

/**
 * @author: hfwei
 * @date: 12/18/22
 * @note:
 */

import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.PointerPointer;
import org.bytedeco.llvm.LLVM.*;
import static org.bytedeco.llvm.global.LLVM.*;

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
