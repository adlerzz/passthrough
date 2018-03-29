package by.passthrough.research.engine.ai;

/**
 * Created by alst0816 on 27.03.2018
 */
public class AnswerSolver {

    private static AnswerSolver INSTANCE = null;

    private AnswerSolver(){

    }

    public static AnswerSolver getInstance() {
        if(INSTANCE == null){
            INSTANCE = new AnswerSolver();
        }
        return INSTANCE;
    }

    public String reverse(String str){
        StringBuffer sb = new StringBuffer();
        for( int i=str.length() - 1; i>=0; i--) {
            sb.append(str.charAt(i));
        }
        return sb.toString();
    }
}
