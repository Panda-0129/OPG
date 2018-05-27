import java.util.ArrayList;

class FirstVT {

    Set getFirstVt(String str) {
        Set set = new Set(str);
//        已经求过的VN
        ArrayList<String> First = new ArrayList<>();

        First.add(str);

        for (Production production : Config.productions) {
            if (production.head.equals(str)) {
                for (String body : production.body) {
                    for (String vt : Config.VT) {
//                        通过当前产生式中终结符的位置来判断该产生式属于算法中的哪种类型
                        if (body.contains(vt)) {
                            int index = body.indexOf(vt);
//                            A->a...和A->Ba...两种情况，直接加入集合
                            if (index == 0 || index == 1) {
                                set.addSet(vt);
                            }
                        }
                    }
                    for (String vn : Config.VN) {
                        if (body.contains(vn)) {
                            int index = body.indexOf(vn);
//                            A->B...这种情况时，若当前FITSTVT(vn)未被加入当前FIRSTVT集，则加入
                            if (index == 0 && !First.contains(vn)) {
                                set.addSet(getFirstVt(vn));
                            }
                        }
                    }
                }
            }
        }

        return set;
    }
}
