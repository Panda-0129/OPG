import java.util.ArrayList;

class Table {

//    存储关系表的二维数组
    String table[][] = new String[Config.VT.size() + 2][Config.VT.size() + 2];
    private FirstVT firstVT = new FirstVT();
    private LastVT lastVT = new LastVT();

    void generateTable() {

//        初始化当前表
        for (int i = 0; i < Config.VT.size() + 2; i++) {
            for (int j = 0 ; j < Config.VT.size() + 2; j++) {
                table[i][j] = "";
            }
        }

//        设置表的第一列与第一行
        table[0][Config.VT.size() + 1] = "#";
        table[Config.VT.size() + 1][0] = "#";

        for (int i = 1; i < Config.VT.size() + 1; i++) {
            table[0][i] = Config.VT.get(i - 1);
            table[i][0] = Config.VT.get(i - 1);
        }

        for (Production production : Config.productions) {
            for (String body : production.body) {
                for (int i = 0; i < body.length() - 1; i++) {
//                    产生式中有终结符相邻情况，则Xi=Xi+1
                    if (Config.VT.contains(String.valueOf(body.charAt(i))) && Config.VT.contains(String.valueOf(body.charAt(i + 1)))) {
                        ArrayList<Integer> tmpPos = getPos(String.valueOf(body.charAt(i)), String.valueOf(body.charAt(i + 1)));
                        table[tmpPos.get(0)][tmpPos.get(1)] = "=";
                    }
//                    产生式中有终结符非终结符终结符情况，则Xi=Xi+2
                    if (i <= body.length() - 3 && Config.VT.contains(String.valueOf(body.charAt(i)))
                            && Config.VT.contains(String.valueOf(body.charAt(i + 2)))
                            && Config.VN.contains(String.valueOf(body.charAt(i + 1)))) {
                        ArrayList<Integer> tmpPos = getPos(String.valueOf(body.charAt(i)), String.valueOf(body.charAt(i + 2)));
                        if (tmpPos.size() == 2) {
                            table[tmpPos.get(0)][tmpPos.get(1)] = "=";
                        }
                    }
//                    产生式中存在终结符后紧接着非终结符的情况
                    if (Config.VT.contains(String.valueOf(body.charAt(i))) && Config.VN.contains(String.valueOf(body.charAt(i + 1)))) {
//                        当前非终结符的FIRSTVT集中的每个b，都置当前终结符<b
                        for (String b : firstVT.getFirstVt(String.valueOf(body.charAt(i + 1))).body) {
                            ArrayList<Integer> tmpPos = getPos(String.valueOf(body.charAt(i)), b);
                            table[tmpPos.get(0)][tmpPos.get(1)] = "<";
                        }
                    }
//                    产生式中存在非终结符后紧接着终结符的情况
                    if (Config.VN.contains(String.valueOf(body.charAt(i))) && Config.VT.contains(String.valueOf(body.charAt(i + 1)))) {
//                        当前非终结符符号LASTVT集中的每个a，置当前a>当前终结符
                        for (String a : lastVT.getLastVt(String.valueOf(body.charAt(i))).body) {
                            ArrayList<Integer> tmpPos = getPos(a, String.valueOf(body.charAt(i + 1)));
                            table[tmpPos.get(0)][tmpPos.get(1)] = ">";
                        }
                    }
                }
            }
        }

//        对#的处理
//        #<FIRSTVT(起始符号)
        for (int i = 0; i < firstVT.getFirstVt(Config.productions.get(0).head).body.size(); i++) {
            for (String a : firstVT.getFirstVt(Config.productions.get(0).head).body) {
                int tmp = getPosX(a);
                if (tmp >= 0)
                    table[Config.VT.size() + 1][tmp] = "<";
            }
        }
//        #>LASTVT(起始符号)
        for (int i = 0; i < lastVT.getLastVt(Config.productions.get(0).head).body.size(); i++) {
            for (String a : lastVT.getLastVt(Config.productions.get(0).head).body) {
                int tmp = getPosY(a);
                if (tmp >= 0)
                    table[tmp][Config.VT.size() + 1] = ">";
            }
        }

    }

    private int getPosX(String str) {
        int tmp = -1;
        for (int i = 0; i < Config.VT.size() + 2; i++) {
            if (table[0][i].equals(str)) {
                tmp = i;
            }
        }
        return tmp;
    }

    private int getPosY(String str) {
        int tmp = -1;
        for (int i = 0; i < Config.VT.size() + 2; i++) {
            if (table[i][0].equals(str)) {
                tmp = i;
            }
        }
        return tmp;
    }

    ArrayList<Integer> getPos(String y, String x) {
        ArrayList<Integer> pos = new ArrayList<>();
        for (int i = 0; i < Config.VT.size() + 2; i++) {
            if (table[i][0].equals(y)) {
                pos.add(i);
            }
        }
        for (int i = 0; i < Config.VT.size() + 2; i++) {
            if (table[0][i].equals(x)) {
                pos.add(i);
            }
        }
        return pos;
    }

    void outputTable () {
        System.out.println("OPG table:");
        for (int i = 0; i < Config.VT.size() + 2; i++) {
            for (int j = 0; j < Config.VT.size() + 2; j++) {
                if (table[i][j].equals(""))
                    System.out.print(" ");
                System.out.print(" " + table[i][j] + " ");
            }
            System.out.println();
        }
    }

}
