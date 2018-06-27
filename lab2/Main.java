import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Array;
import java.util.*;

class transFunc {
    private char sym;
    private int state;
    private int res;

    public transFunc(char s, int st, int r) {
        this.res = r;
        this.state = st;
        this.sym = s;
    }

    public int getResult() {
        return res;
    }

    public char getSymbol() {
        return sym;
    }

    public int getState() {
        return state;
    }


}

class Machine {
    private Set<Character> A;
    private Set<Integer> S;
    private int s0;
    private Set<Integer> F;
    public Set<transFunc> f;
    private int curState;

    public Machine(Set<Character> Al, Set<Integer> St, int st0, Set<Integer> Fin, Set<transFunc> func) {
        this.A = Al;
        this.S = St;
        this.s0 = st0;
        this.curState = s0;
        this.F = Fin;
        this.f = func;
    }

    public Machine(String name) throws FileNotFoundException {
        Scanner in = new Scanner(new File(name));
        int a = in.nextInt(), s = in.nextInt(), st0 = in.nextInt();
        Set<Integer> _S = new HashSet<>(), _F = new HashSet<>();
        Set<Character> _A = new HashSet<>();
        for (int i = 0; i < s; i++){
            _S.add(i);
        }
        char c = 'a';
        for (int i = 0; i < a; i++){
            _A.add(c);
            c++;
        }
        this.S = _S;
        this.A = _A;
        this.s0 = st0;
        curState = s0;
        String l = in.nextLine();
        l = in.nextLine();
        String[] _Fst = l.split(" ");
        for (String tst:_Fst){
            //System.out.println(tst);
            _F.add(Integer.parseInt(tst));
        }
        this.F = _F;
        Set<transFunc> _f = new HashSet<>();
        while(in.hasNextLine()){
            String str = in.nextLine();
            String[] __f = str.split(" ");
            _f.add(new transFunc(__f[1].charAt(0),Integer.parseInt(__f[0]),Integer.parseInt(__f[2])));
        }
        this.f = _f;
    }

    public void reset() {
        curState = s0;
    }

    public boolean go(char sym) {
        for (transFunc i:f) {
            if (i.getSymbol() == sym && i.getState() == curState) {
                curState = i.getResult();
                return true;
            }
        }
        return false;
    }

    //checks whether it could run word "word"
    public boolean isAccepted(char[] word) {
        int temp = curState;
        reset();
        for(char s:word) {
            if(!go(s)) {curState = temp; return false;}
        }
        for(int st:F) {
            if(st == curState) {curState = temp; return true;}
        }
        curState = temp;
        return false;
    }

    public boolean recursiveIntIsAcceptable(int curLength, char[] word) {
        boolean result = true;
        if (curLength > 0) {
            for(Character elem : A) {
                word[curLength-1] = elem;
                result = result & recursiveIntIsAcceptable(curLength - 1, word);
            }
        }
        else return isAccepted(word);
        return result;
    }

    public boolean intIsAcceptable(int length) {
        char[] emptyWord = new char[length];
        return  recursiveIntIsAcceptable(length, emptyWord);
    }
}

public class Main {
    public static boolean task(String fname, int Num) throws FileNotFoundException {
        Machine au = new Machine(fname);
        return au.intIsAcceptable(Num);
    }
    public static void main(String[] args)throws Exception {
        int Num = 3;
        System.out.println(task("input.txt", Num));
    }
}