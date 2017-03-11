/**
 * Created by jevge on 10/03/2017.
 */
public class Credit_Score {

    public static void main(String[] args){

        Calculations mortgage = new Calculations(15000,3,10,200000);
        System.out.println(mortgage.getScore());
        System.out.println(mortgage.getLoan());
        System.out.println(mortgage.getWith_interest());

    }

}
