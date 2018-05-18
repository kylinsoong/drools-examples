package org.sample.sinotrans.models;

import java.util.ArrayList;
import java.util.List;

public class FactFactory {

    /**
     * 2. 收发通信息导入客户端后，每行不能超过35个字符，超过则自动换行，
     *    注意：不能将一个单词分裂为两行
     */
    public static Booking WHL_43510457804020() {
        
        Summary summary = new Summary();
        summary.setWfobcTransportTerm("CY/CY");
        summary.setWfobcEtd("28/04/2018");
        summary.setWfobcPaymentMode("FREIGHT COLLECT");
        summary.setWfobcTotalVolumn("8.950");
        summary.setWfobcBokContractNo("WCSA_FAK005N");
        summary.setWfobcConsignDepartment("");
        summary.setWfobcLoadPort("SHANGHAI");
        summary.setWfobcNotify("KUEHNE + NAGEL S.A.S.\\rREG.DIAN NO.011/RES.08930 28-10-03\\rCRA 48 NO.10-45, OFICINA 905, CC.\\rMONTERREY COLOMBIA NIT:800.039.9961");
        summary.setWfobcBookingRemark("CONTRACT NUMBER:WCSA_FAK005N\\rOB/L NUMBER:");
        summary.setWfobcVoyage("0363-020E");
        summary.setWfobcBookingParty("YIYI HUANG");
        summary.setWfobcDischargePort("BUENAVENTURA");
        summary.setOrgId("");
        summary.setWfobcVesselName("VALENCE");
        summary.setWfobcShipper("KUEHNE & NAGEL LIMITED\\r11-16F, BLOCK 1, LIFE HUB AT DANING\\rOFFICE TOWER, 1868, GONG HE XIN RD,\\rZHABEI DISTRICT, SHANGHAI, CHINA\\rAGENT OF BLUE ANCHOR LINE\\rKN REF. 4351-0457-804.020");
        summary.setWfobcConsignee("KUEHNE + NAGEL S.A.S.\\rREG.DIAN NO.011/RES.08930 28-10-03\\rCRA 48 NO.10-45, OFICINA 905, CC.\\rMONTERREY COLOMBIA NIT:800.039.9961\\rAGENT OF BLUE ANCHOR LINE");
        summary.setOrderId("a");
        summary.setWfobcCustomNo("4351-0457-804.020");
        summary.setWfobcTotalGrossWeight("22150.00");
        summary.setExtCompany("KUEHNE & NAGEL LIMITED");
        
        Container container = new Container();
        container.setWfobccContainerNum("1");
        container.setWfobccContainerType("20GE");
        List<Container> containers = new ArrayList<>();
        containers.add(container);
        
        Pages pages = new Pages();
        pages.setEndpage(1);
        pages.setFileName("MAPEMLCN0005553408-4351-0457-804.020+.PDF");
        pages.setStartpage(1);
        
        Detail detail = new Detail();
        detail.setWfobccDescription("48 PKGS\\rSELF DRILLING ANCHOR BOLT\\rBKG NO: 177SVKLDS81290\\rREL NO:");
        detail.setWfobccVolumnUnit("M3");
        detail.setWfobccQuantity("48");
        detail.setWfobccMarks("N/M");
        detail.setWfobccVolumn("8.950");
        detail.setWfobccPcs("PKGS");
        detail.setWfobccGrossWeight("22150.00");
        detail.setWfobccGrossWeightUnit("KG");
        List<Detail> details = new ArrayList<>();
        details.add(detail);
        
        Datum datum = new Datum();
        datum.setSummary(summary);
        datum.setContainer(containers);
        datum.setPages(pages);
        datum.setDetail(details);
        
        List<Datum> data = new ArrayList<>();
        data.add(datum);
        
        Params params = new Params();
        params.setDocType("CONSIGN");
        params.setAppId("FFQ6ht26");
        params.setAppKey("uQ3FE10G");
        params.setAppSecret("d0cef53a5ccef0d564d9c391432c484b");
        params.setReqUuid("3adf8aab54734b188772276e38144bb9");
        params.setIfNeedOcr("0");
        params.setOcrType("");
        params.setBillStyle("");
        params.setIfNeedCallback("0");
        params.setFieldStyle("UNDERLINE");
        
        Values values = new Values();
        values.setParams(params);
        values.setData(data);
        
        Booking booking = new Booking();
        booking.setResultCode("success");
        booking.setResultMessage("识别成功");
        booking.setValues(values);
        
        return booking;
    }
}
