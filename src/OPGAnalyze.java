import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

class OPGAnalyze {

    private String inputStr;
    private Table table = new Table();
//    分析栈
    private Stack<String> analyzeStack = new Stack<>();
//    输入符号栈，用动态数组实现
    private ArrayList<String> inputStack = new ArrayList<>();
//    输入符号下标
    private int cursorOfInput = 0;

    void analyze () {
//        初始化符号栈、获取输入串
        init();
//        记录最近出栈符号
        String recentPop;

        while (true) {
//            #在栈顶，且输入串下标指向#
            if (analyzeStack.peek().equals("#") && inputStack.get(cursorOfInput).equals("#")) {
                System.out.println("Success");
                return;
            } else {
//                a<b 或 a=b
                if (isLess(analyzeStack.peek(), inputStack.get(cursorOfInput))
                        || isEqual(analyzeStack.peek(), inputStack.get(cursorOfInput))) {
                    analyzeStack.push(inputStack.get(cursorOfInput));
                    cursorOfInput++;
                    outputStack();
                }
//                a>b
                else if (isLarger(analyzeStack.peek(), inputStack.get(cursorOfInput))) {
                    recentPop = analyzeStack.peek();
                    while ( !isLess(analyzeStack.peek(), recentPop) ) {
                        recentPop = analyzeStack.pop();
                    }
                    outputStack();
                } else {
                    System.out.println("Error");
                    return;
                }
            }
        }

    }

    private void init() {
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNext()) {
            inputStr = scanner.next();
            System.out.println(inputStr);
        }
        scanner.close();

        table.generateTable();
        analyzeStack.push("#");
        int index = 0;
        while ( index  < inputStr.length()) {
            inputStack.add(String.valueOf(inputStr.charAt(index)));
            index++;
        }
    }

    private boolean isLess (String a, String b) {
        ArrayList<Integer> tmp = table.getPos(a, b);
        return tmp.size() == 2 && table.table[tmp.get(0)][tmp.get(1)].equals("<");
    }

    private boolean isEqual (String a, String b) {
        ArrayList<Integer> tmp = table.getPos(a, b);
        return tmp.size() == 2 && table.table[tmp.get(0)][tmp.get(1)].equals("=");
    }

    private boolean isLarger (String a, String b) {
        ArrayList<Integer> tmp = table.getPos(a, b);
        return tmp.size() == 2 && table.table[tmp.get(0)][tmp.get(1)].equals(">");
    }

    private void outputStack() {
        for (String aStProduction : analyzeStack) {
            System.out.print(aStProduction);
        }

        if (analyzeStack.size() < 4)
            System.out.print("\t");

//        System.out.print("\t\t");

//        for (int i = stInputStr.size(); i > 0; i-- ) {
//            System.out.print(stInputStr.get(i - 1));
//        }
//
//        if (stInputStr.size() < 4)
//            System.out.print("\t");
//
//        System.out.print("\t\t" + currentPro);

        System.out.println();
    }

//    private String getTopVT () {
//        String vt;
//
//        if (Config.VT.contains(analyzeStack.peek()) || analyzeStack.peek().equals("#")) {
//            vt = analyzeStack.peek();
//        } else {
//            ArrayList<String> tmp = new ArrayList<>();
//            while ( analyzeStack.size() > 0 && !Config.VT.contains(analyzeStack.peek())) {
//                tmp.add(analyzeStack.pop());
//            }
//            vt = analyzeStack.peek();
//            for (int i = tmp.size() - 1; i >= 0; i++) {
//                analyzeStack.push(tmp.get(i));
//            }
//        }
//
//        return vt;
//    }

}
