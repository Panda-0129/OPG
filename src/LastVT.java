import java.util.ArrayList;

class LastVT {

    Set getLastVt(String str) {
        Set set = new Set(str);
        ArrayList<String> Last = new ArrayList<>();
        Last.add(str);

        for (Production production : Config.productions) {
            if (production.head.equals(str)) {
                for (String body : production.body) {
//                    A->...a，即以终结符结尾，该终结符入Lastvt
                    if (Config.VT.contains(String.valueOf(body.charAt(body.length() - 1)))) {
                        set.addSet(String.valueOf(body.charAt(body.length() - 1)));
                    }
//                    A->...B，即以非终结符结尾，该非终结符的Lastvt入A的Lastvt
                    if (Config.VN.contains(String.valueOf(body.charAt(body.length() - 1)))
                            && !Last.contains(String.valueOf(body.charAt(body.length() - 1)))) {
                        set.addSet(getLastVt(String.valueOf(body.charAt(body.length() - 1))));
                    }
//                    A->...aB，即先以非终结符结尾，前面是终结符，则终结符入Lastvt
                    if (body.length() > 1
                            && Config.VN.contains(String.valueOf(body.charAt(body.length() - 1)))
                            && Config.VT.contains(String.valueOf(body.charAt(body.length() - 2)))) {
                        set.addSet(String.valueOf(body.charAt(body.length() - 2)));
                    }

                }
            }
        }
        return set;
    }

}
