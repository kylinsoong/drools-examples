package com.sample.client;

import java.io.IOException;
import java.util.Arrays;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.kie.api.command.Command;
import org.kie.internal.command.CommandFactory;

import com.sample.models.CountryCode;

public class Main {
    
    static String SENDER = "{\"lookup\":\"default-stateless-ksession\",\"commands\":[{\"insert\":{\"object\":{\"com.sample.models.Booking\":{\"values\":{\"data\":[{\"Summary\":{\"wfobc_transport_term\":\"CY/CY\",\"wfobc_etd\":\"02/05/2018\",\"wfobc_payment_mode\":\"FREIGHT PREPAID\",\"wfobc_total_volumn\":\"45.000\",\"wfobc_bok_contract_no\":\"KONE\",\"wfobc_consign_department\":\"\",\"wfobc_load_port\":\"SHANGHAI\",\"wfobc_notify\":\"KUEHNE + NAGEL SDN. BHD.\\r1 JALAN BUMBUNG U8/90 SEKSYEN U8\\rPERINDUSTRIAN BUKIT JELUTONG\\r40150 SHAN ALAM, SELANGOR, MALAYSIA\",\"wfobc_booking_remark\":\"CONTRACT NUMBER: KONE\\rOB/L NUMBER:\",\"wfobc_voyage\":\"TBA\",\"wfobc_booking_party\":\"SCOTT CHE\",\"wfobc_discharge_port\":\"PORT KLANG\",\"org_id\":\"\",\"wfobc_vessel_name\":\"TBN\",\"wfobc_shipper\":\"KUEHNE & NAGEL LIMITED\\r11-16F, BLOCK 1, LIFE HUB AT DANING\\rOFFICE TOWER, 1868, GONG HE XIN RD,\\rZHABEI DISTRICT, SHANGHAI, CHINA\\rAGENT OF BLUE ANCHOR LINE\\rKN REF. 4351-0139-804.040\",\"wfobc_consignee\":\"KUEHNE + NAGEL SDN. BHD.\\r1 JALAN BUMBUNG U8/90 SEKSYEN U8\\rPERINDUSTRIAN BUKIT JELUTONG\\r40150 SHAN ALAM, SELANGOR, MALAYSIA\\rAGENT OF BLUE ANCHOR LINE\",\"order_id\":\"a\",\"wfobc_custom_no\":\"4351-0139-804.040\",\"wfobc_total_gross_weight\":\"45000.00\",\"ext_company\":\"KUEHNE & NAGEL LIMITED\"},\"Container\":[{\"wfobcc_container_type\":\"40HC\",\"wfobcc_container_num\":\"1\"},{\"wfobcc_container_type\":\"40HC\",\"wfobcc_container_num\":\"1\"},{\"wfobcc_container_type\":\"40HC\",\"wfobcc_container_num\":\"1\"}],\"pages\":{\"endpage\":2,\"startpage\":1,\"fileName\":\"MAPEMLCN0005560197-4351-0139-804.040+.PDF\"},\"Detail\":[{\"wfobcc_description\":\"TWO PIECES OF MAP\\rESCALATOR\\rBKG NO:\\rREL NO:\",\"wfobcc_volumn_unit\":\"M3\",\"wfobcc_quantity\":\"1\",\"wfobcc_marks\":\"KONE\\rES20180261\",\"wfobcc_volumn\":\"15.000\",\"wfobcc_pcs\":\"40' HC\",\"wfobcc_gross_weight\":\"15000.00\",\"wfobcc_gross_weight_unit\":\"KG\"},{\"wfobcc_description\":\"TWO PIECES OF MAP00\\rESCALATOR\\rBKG NO:\\rREL NO:\",\"wfobcc_volumn_unit\":\"M3\",\"wfobcc_quantity\":\"1\",\"wfobcc_marks\":\"KONE\\rES20180261\",\"wfobcc_volumn\":\"15.000\",\"wfobcc_pcs\":\"40' HC\",\"wfobcc_gross_weight\":\"15000.00\",\"wfobcc_gross_weight_unit\":\"KG\"},{\"wfobcc_description\":\"TWO PIECES OF MAP\\rESCALATOR\\rBKG NO:\",\"wfobcc_volumn_unit\":\"M3\",\"wfobcc_quantity\":\"1\",\"wfobcc_marks\":\"KONE\\rES20180261\",\"wfobcc_volumn\":\"15.000\",\"wfobcc_pcs\":\"40' HC\",\"wfobcc_gross_weight\":\"15000.00\",\"wfobcc_gross_weight_unit\":\"KG\"}]}],\"params\":{\"docType\":\"CONSIGN\",\"appId\":\"FFQ6ht26\",\"appKey\":\"uQ3FE10G\",\"appSecret\":\"d0cef53a5ccef0d564d9c391432c484b\",\"echoStr\":null,\"reqUuid\":\"77d395ed575648059131026175c1a4d9\",\"ifNeedOcr\":\"0\",\"ocrType\":\"\",\"billStyle\":\"\",\"ifNeedCallback\":\"0\",\"callbackUrl\":null,\"pathCode\":null,\"fieldStyle\":\"UNDERLINE\"}},\"resultCode\":\"success\",\"resultMessage\":\"识别成功\"}},\"out-identifier\":\"booking\"}},{\"fire-all-rules\":{}},{\"get-objects\":{\"out-identifier\":\"objects\"}},{\"dispose\":{}}]}";
    
    static ObjectMapper mapperObj = new ObjectMapper();

    public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException {

        Command<?>[] commands = {
                CommandFactory.newInsert(new CountryCode("SHANGHAI"), "contrycode"),
                CommandFactory.newFireAllRules(),
                CommandFactory.newGetObjects("objects"),
                CommandFactory.newDispose()
        }; 
        
        Command<?> batchExecution = CommandFactory.newBatchExecution(Arrays.asList(commands), "default-stateless-ksession");
        
        System.out.println(mapperObj.writeValueAsString(batchExecution));
    }

}
