package com.anyi.safe.console.controller.platform.regulations;

public class Test
{
    
    public static void main(String[] args)
    {
        System.out.println(hex2Decimal("d08ade19"));
    }
    
    public static String hex2Decimal(String hex)
    {
        StringBuilder builder = new StringBuilder();
        if (hex.length() == 8)
        {
            for (int i = 0; i < 4; i++)
            {
                String str = hex.substring(hex.length() - 2 * (i + 1), hex.length() - 2 * i);
                builder.append(str);
            }
        }
        String decimal = String.valueOf(Long.parseLong(builder.toString(), 16));
        while (decimal.length() < 10)
        {
            decimal = "0" + decimal;
        }
        
        return decimal;
    }
}
