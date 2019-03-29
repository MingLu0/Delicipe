package com.luo.ming.delicipe.Helpers;

public class IngredientClassHelper {

    public boolean isDouble(String str){
        try{
            Double.parseDouble(str);
            return true;

        }catch(NumberFormatException exception){
            return false;
        }
    }

    public double parseRatio(String ratio){

        if(ratio.contains("/")){
            String[] rat = ratio.split("/");
            double value = Double.parseDouble(rat[0])/Double.parseDouble(rat[1]);

            return (double)Math.round(value * 100d) / 100d;
        }
        else
            return Double.parseDouble(ratio);
    }

    public String convertArrayOfStringIntoString(String []arr){
        StringBuilder builder = new StringBuilder();
        for(String s : arr) {
            builder.append(s);
            builder.append(" ");
        }
        String str = builder.toString();

        return  str;

    }
}
