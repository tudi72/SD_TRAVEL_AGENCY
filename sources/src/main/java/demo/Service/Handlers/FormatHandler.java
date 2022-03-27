package demo.Service.Handlers;

public class FormatHandler {

    public static boolean checkIntFormat(String number){
        try{
            int n = Integer.parseInt(number);
            return true;
        }catch(Exception e){
            return false;
        }
    }
}
