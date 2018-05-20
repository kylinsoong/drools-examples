package org.sample.util;

public class LogisticsUtil {
    
    /**
     * 收发通信息导入客户端后，每行不能超过35个字符，超过则自动换行，
     *    注意：不能将一个单词分裂为两行
     * @param addr
     * @return
     */
    public static String maxLength35(String addr) {
        StringBuffer sb = new StringBuffer();
        
        String[] tokens = addr.split(" ");
        int length = 0;
        for(String token : tokens) {
            sb.append(token);
            length += token.length();
            if(length > 35) {
                sb.append("\n");
                length = 0;
            } else {
                sb.append(" ");
                length += 1;
            }
        }
        
        return sb.substring(0, sb.length() -1);
    }

    public static void main(String[] args) {
        
        String result = null;

        result = maxLength35("KUEHNE & NAGEL LIMITED\\r11-16F, BLOCK 1, LIFE HUB AT DANING\\rOFFICE TOWER, 1868, GONG HE XIN RD,\\rZHABEI DISTRICT, SHANGHAI, CHINA\\rAGENT OF BLUE ANCHOR LINE\\rKN REF. 4351-0457-804.020");
        
        System.out.println(result);
    }

}
