package llvm.factorial;

import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.PointerPointer;
import org.bytedeco.llvm.LLVM.*;

import static org.bytedeco.llvm.global.LLVM.*;

public class Factorial2 {
    public static void main(String[] args) {
        LLVMInitializeCore(LLVMGetGlobalPassRegistry());
        LLVMLinkInMCJIT();
        LLVMInitializeNativeAsmPrinter();
        LLVMInitializeNativeAsmParser();
        LLVMInitializeNativeTarget();

        LLVMModuleRef module = LLVMModuleCreateWithName("Factorial2");
        LLVMBuilderRef builder = LLVMCreateBuilder();
        LLVMTypeRef i32Type = LLVMInt32Type();
        LLVMTypeRef returnType = i32Type;

        LLVMValueRef one = LLVMConstInt(i32Type, 1, 0);
        LLVMValueRef two = LLVMConstInt(i32Type, 2, 0);
        LLVMValueRef seven = LLVMConstInt(i32Type, 7, 0);
        LLVMValueRef fortyTwo = LLVMConstInt(i32Type, 42, 0);

        // 创建factorial函数
        LLVMTypeRef factorial2Type = LLVMFunctionType(returnType, i32Type, 1, 0);
        LLVMValueRef factorial2 = LLVMAddFunction(module, "Factorial2", factorial2Type);
        // 构建函数体
        LLVMBasicBlockRef entry = LLVMAppendBasicBlock(factorial2, "entry");
        LLVMBasicBlockRef forBranch = LLVMAppendBasicBlock(factorial2, "forBranch");
        LLVMBasicBlockRef trueBranch = LLVMAppendBasicBlock(factorial2, "true");
        LLVMBasicBlockRef exit = LLVMAppendBasicBlock(factorial2, "exit");

        // 获取参数 val
        LLVMValueRef val = LLVMGetParam(factorial2, /* parameterIndex */0);

        // 设置在entry块后插入代码
        LLVMPositionBuilderAtEnd(builder, entry);
        // int temp = 1;
        LLVMValueRef temp = LLVMBuildAlloca(builder, i32Type, "temp");
        LLVMBuildStore(builder, one, temp);
        // int i = 2;
        LLVMValueRef i = LLVMBuildAlloca(builder, i32Type, "i");
        LLVMBuildStore(builder, two, temp);
        // 无条件跳转到 forBranch
        LLVMBuildBr(builder, forBranch);

        LLVMPositionBuilderAtEnd(builder, forBranch);
        // i <= val
        LLVMValueRef iLoad = LLVMBuildLoad(builder, i, "iLoad");
        LLVMValueRef condition = LLVMBuildICmp(builder, LLVMIntSLE, iLoad, val, "condition=i<=val");
        // 条件为真，到trueBranch，否则到exit
        LLVMBuildCondBr(builder, condition, trueBranch, exit);

        // 设置在trueBranch块后插入代码
        LLVMPositionBuilderAtEnd(builder, trueBranch);
        // temp *= i;
        iLoad = LLVMBuildLoad(builder, i, "iLoad");
        LLVMValueRef tempLoad = LLVMBuildLoad(builder, temp, "tempLoad");
        LLVMValueRef mulRes = LLVMBuildMul(builder, tempLoad, iLoad, "temp=temp*i");
        LLVMBuildStore(builder, mulRes, temp);
        // i++;
        iLoad = LLVMBuildLoad(builder, i, "iLoad");
        LLVMValueRef addRes = LLVMBuildAdd(builder, iLoad, one, "i=i+1");
        LLVMBuildStore(builder, addRes, i);
        // 无条件跳转到forBranch块，继续迭代
        LLVMBuildBr(builder, forBranch);

        // 设置在exit块后插入代码
        LLVMPositionBuilderAtEnd(builder, exit);
        tempLoad = LLVMBuildLoad(builder, temp, "tempLoad");
        LLVMBuildRet(builder, tempLoad);


        // 创建main函数
        // char -> int8; 指向char的指针
        LLVMTypeRef charPtr = LLVMPointerType(LLVMInt8Type(), 0);
        // 指向char指针的指针
        LLVMTypeRef argumentType = LLVMPointerType(charPtr, 0);
        // 创建参数类型
        PointerPointer<Pointer> argumentTypes = new PointerPointer<>(2)
                .put(0, i32Type)
                .put(1, argumentType);
        // 创建函数类型
        LLVMTypeRef mainType = LLVMFunctionType(returnType, argumentTypes, 2, 0);
        LLVMValueRef mainFunc = LLVMAddFunction(module, "main", mainType);
        // 构建函数体
        LLVMBasicBlockRef mainEntry = LLVMAppendBasicBlock(mainFunc, "mainEntry");
        // 设置在mainEntry块后插入代码
        LLVMPositionBuilderAtEnd(builder, mainEntry);
        // 构建实参
        PointerPointer<Pointer> arguments = new PointerPointer<>(1)
                .put(0, two);
        // factorial(2)
        LLVMValueRef callFactorial2 = LLVMBuildCall(builder, factorial2, arguments, 1, "factorial(2)");
        // factorial(2) * 7
        LLVMValueRef factorial2Mul7 = LLVMBuildMul(builder, callFactorial2, seven, "factorial(2)*7");
        // factorial(2) * 7 == 42
        LLVMValueRef ifEqual = LLVMBuildICmp(builder, LLVMIntEQ, factorial2Mul7, fortyTwo, "factorial(2)*7==42");
        // 尤其要注意的：ifEqual是i1类型，而main函数要求返回i32类型，所以这里要做类型转换
        LLVMValueRef result = LLVMBuildZExt(builder, ifEqual, i32Type, "result");
        LLVMBuildRet(builder, result);

        // 导出生成的LLVM IR
        LLVMDumpModule(module);

        //释放资源
        LLVMDisposeBuilder(builder);
    }
}
