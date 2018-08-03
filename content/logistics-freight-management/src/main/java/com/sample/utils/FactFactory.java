package com.sample.utils;

import java.util.ArrayList;
import java.util.List;

import com.sample.models.Booking;
import com.sample.models.Container;
import com.sample.models.Datum;
import com.sample.models.Detail;
import com.sample.models.Pages;
import com.sample.models.Params;
import com.sample.models.Summary;
import com.sample.models.Values;

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
    
    /**
     * 1.英文品名导入客户端后，不能超过（512字符*4行），如果客户品名行数多余4行，请自动将每行品名后加空格，将下一行的品名接上来显示，
     *   注意：不能超过4行
     * 2.备注栏位内容导入客户端后，不能超过（70字符*3行），如果客户备注行数多余3行，请自动将每行备注后加空格，将下一行的备注接上来显示，
     *   注意：不能超过3行
     * 
     * @return
     */
    public static Booking SMLINE_43510370803261() {
        
        Summary summary = new Summary();
        summary.setWfobcTransportTerm("CY/CY");
        summary.setWfobcEtd("24/04/2018");
        summary.setWfobcPaymentMode("FREIGHT COLLECT");
        summary.setWfobcTotalVolumn("91.572");
        summary.setWfobcBokContractNo("FAK-AEF171000-WC");
        summary.setWfobcConsignDepartment("");
        summary.setWfobcLoadPort("SHANGHAI");
        summary.setWfobcNotify("KUEHNE + NAGEL, INC.\\r4100 NORTH COMMERCE DRIVE\\rEAST POINT, GA 30344\\rUNITED STATES\\rREF:1019-0707-440.000");
        summary.setWfobcBookingRemark("CONTRACT NUMBER: FAK-AEF171000-WC\\rOB/L NUMBER: SHSB8B485500\\rFAK\\r4/24 SML CPX");
        summary.setWfobcVoyage("1803E");
        summary.setWfobcBookingParty("RYNA WU");
        summary.setWfobcDischargePort("LONG BEACH,CA");
        summary.setOrgId("");
        summary.setWfobcVesselName("SM LONG BEACH");
        summary.setWfobcShipper("KUEHNE & NAGEL LIMITED\\r11-16F, BLOCK 1, LIFE HUB AT DANING\\rOFFICE TOWER, 1868, GONG HE XIN RD,\\rZHABEI DISTRICT, SHANGHAI, CHINA\\rAGENT OF BLUE ANCHOR AMERICA LINE\\rKN REF. 4351-0370-803.261");
        summary.setWfobcConsignee("KUEHNE + NAGEL, INC.\\r4100 NORTH COMMERCE DRIVE\\rEAST POINT, GA 30344\\rUNITED STATES\\rAGENT OF BLUE ANCHOR AMERICA LINE");
        summary.setOrderId("a");
        summary.setWfobcCustomNo("4351-0370-803.261");
        summary.setWfobcTotalGrossWeight("23744.00");
        summary.setExtCompany("KUEHNE & NAGEL LIMITED");
        
        Container c1 = new Container();
        c1.setWfobccContainerNum("1");
        c1.setWfobccContainerType("40GE");
        Container c2 = new Container();
        c2.setWfobccContainerNum("1");
        c2.setWfobccContainerType("40GE");
        Container c3 = new Container();
        c3.setWfobccContainerNum("1");
        c3.setWfobccContainerType("40GE");
        
        List<Container> containers = new ArrayList<>();
        containers.add(c1);
        containers.add(c2);
        containers.add(c3);
        
        Pages pages = new Pages();
        pages.setEndpage(2);
        pages.setFileName("MAPEMLCN0005554020-4351-0370-803.261+.PDF");
        pages.setStartpage(1);
        
        Detail d1 = new Detail();
        d1.setWfobccDescription("18 PKGS\\rBKG NO:\\rREL NO:");
        d1.setWfobccVolumnUnit("M3");
        d1.setWfobccQuantity("18");
        d1.setWfobccMarks("CCLU4685466\\rSEAL 310120");
        d1.setWfobccVolumn("30.132");
        d1.setWfobccPcs("PKGS");
        d1.setWfobccGrossWeight("7344.00");
        d1.setWfobccGrossWeightUnit("KG");
        
        Detail d2 = new Detail();
        d2.setWfobccDescription("20 PKGS\\rBKG NO:\\rREL NO:");
        d2.setWfobccVolumnUnit("M3");
        d2.setWfobccQuantity("20");
        d2.setWfobccMarks("SMCU4504715\\rSEAL 310119");
        d2.setWfobccVolumn("30.720");
        d2.setWfobccPcs("PKGS");
        d2.setWfobccGrossWeight("8200.00");
        d2.setWfobccGrossWeightUnit("KG");
        
        Detail d3 = new Detail();
        d3.setWfobccDescription("20 PKGS\\rHOT TOWEL DELTA BOX,\\rGREY TONGS\\rLINER, TRAY 1/3 ATLAS\\rBE BAMBOO TEXTURE\\rTABLECLOTH, WHITE\\rOUTBOUND PO#:\\r506107; 506358; 506360\\rN.W.P.M\\rHS-CODE:6307900000/\\r4823909000/6302539090\\r58 PKGS=58 PLTS\\rTTL:58 PKGS\\rSAY TOTAL FIFTY EIGHT\\rPACKAGES ONLY\\rBKG NO:\\rREL NO:");
        d3.setWfobccVolumnUnit("M3");
        d3.setWfobccHscode("6307900000/");
        d3.setWfobccQuantity("58");
        d3.setWfobccMarks("FSCU4701775\\rSEAL 310118\\rN/M");
        d3.setWfobccVolumn("30.720");
        d3.setWfobccPcs("PKGS");
        d3.setWfobccGrossWeight("8200.00");
        d3.setWfobccGrossWeightUnit("KG");
        
        List<Detail> details = new ArrayList<>();
        details.add(d1);
        details.add(d2);
        details.add(d3);
        
        Datum datum = new Datum();
        datum.setSummary(summary);
        datum.setContainer(containers);
        datum.setPages(pages);
        datum.setDetail(details);
        
        List<Datum> items = new ArrayList<>();
        items.add(datum);
        
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
        values.setData(items);
        
        Booking booking = new Booking();
        booking.setResultCode("success");
        booking.setResultMessage("识别成功");
        booking.setValues(values);
        
        return booking;
    }
    
    /**
     * 1.所有SHIPPER开头是“KUEHNE”的，在【发货人代码】栏位内填入：N:SMTC-NV00299
     * 4.当订舱托书中的付款方式是“FREIGT COLLECT”，请将【交货地】内容复制后填写在客户端【付款地点】栏位，并在【运费条款说明】里输入“FREIGT COLLECT AT 交货地的值”
     * 
     * @return
     */
    public static Booking ONE_43510238804160 () {
        
        Summary summary = new Summary();
        summary.setWfobcTransportTerm("CY/CY");
        summary.setWfobcEtd("06/05/2018");
        summary.setWfobcPaymentMode("FREIGHT COLLECT");
        summary.setWfobcTotalVolumn("60.740");
        summary.setWfobcBokContractNo("SINN00769A");
        summary.setWfobcConsignDepartment("");
        summary.setWfobcLoadPort("SHANGHAI");
        summary.setWfobcNotify("KUEHNE & NAGEL N.V.\\rLLOYDSTRAAT 35\\rNL-3024 EA ROTTERDAM HAVEN NR 230\\rNETHERLANDS");
        summary.setWfobcBookingRemark("ONE FE4\\rCONTRACT NUMBER: SINN00769A\\rOB/L NUMBER:");
        summary.setWfobcVoyage("005W");
        summary.setWfobcBookingParty("JENNY.J.LI");
        summary.setWfobcDischargePort("ROTTERDAM");
        summary.setOrgId("");
        summary.setWfobcVesselName("LINAH");
        summary.setWfobcShipper("KUEHNE & NAGEL LIMITED\\r11-16F, BLOCK 1, LIFE HUB AT DANING\\rOFFICE TOWER, 1868, GONG HE XIN RD,\\rZHABEI DISTRICT, SHANGHAI, CHINA\\rAGENT OF BLUE ANCHOR LINE\\rKN REF. 4351-0238-804.160");
        summary.setWfobcConsignee("KUEHNE & NAGEL N.V.\\rLLOYDSTRAAT 35\\rNL-3024 EA ROTTERDAM HAVEN NR 230\\rNETHERLANDS\\rAGENT OF BLUE ANCHOR LINE");
        summary.setOrderId("a");
        summary.setWfobcCustomNo("4351-0238-804.160");
        summary.setWfobcTotalGrossWeight("5280.00");
        summary.setExtCompany("KUEHNE & NAGEL LIMITED");
        
        Container container = new Container();
        container.setWfobccContainerNum("1");
        container.setWfobccContainerType("40GE");
        List<Container> containers = new ArrayList<>();
        containers.add(container);
        
        Pages pages = new Pages();
        pages.setEndpage(1);
        pages.setStartpage(1);
        pages.setFileName("MAPEMLCN0005565401-4351-0238-804.160+.PDF");
        
        Detail detail = new Detail();
        detail.setWfobccDescription("100% POLY WOMEN WOVEN\\rJACKET\\rKIDS JACKET\\rDUFFLE COAT\\rTTL:627 CTN\\rHS-CODE(S):620213\\rBKG NO:\\rREL NO:");
        detail.setWfobccVolumnUnit("M3");
        detail.setWfobccHscode("620213");
        detail.setWfobccQuantity("627");
        detail.setWfobccMarks("N/M");
        detail.setWfobccVolumn("60.740");
        detail.setWfobccPcs("CTN");
        detail.setWfobccGrossWeight("5280.00");
        detail.setWfobccGrossWeightUnit("KG");
        List<Detail> details = new ArrayList<>();
        details.add(detail);
        
        Datum datum = new Datum();
        datum.setSummary(summary);
        datum.setContainer(containers);
        datum.setPages(pages);
        datum.setDetail(details);
        
        List<Datum> items = new ArrayList<>();
        items.add(datum);
        
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
        values.setData(items);
        
        Booking booking = new Booking();
        booking.setResultCode("success");
        booking.setResultMessage("识别成功");
        booking.setValues(values);
        
        return booking;
    }
    
    /**
     * 2.所有SHIPPER开头不是“KUEHNE”的，在【发货人代码】栏位内填入：B
     * 8.如果订舱的托书中，其卸货港或目的港是美国线、加拿大港口，但收发通开头都不是“KUEHNE”，请在导入托书的同时，在备注里中添加：
     *    CARRIER FILLING
     *  注意：以上内容不要和其他备注内容连接在一起，另起一行显示
     * 
     * @return
     */
    public static Booking ONE_43519188804541() {
        
        Summary summary = new Summary();
        summary.setWfobcTransportTerm("CY/CY");
        summary.setWfobcEtd("27/04/2018");
        summary.setWfobcPaymentMode("FREIGHT COLLECT");
        summary.setWfobcTotalVolumn("159.149");
        summary.setWfobcBokContractNo("SC0119919");
        summary.setWfobcConsignDepartment("");
        summary.setWfobcLoadPort("SHANGHAI");
        summary.setWfobcBookingRemark("CONTRACT NUMBER: SC0119919\\rOB/L NUMBER:\\r4-27 ONE PS5");
        summary.setWfobcVoyage("092E");
        summary.setWfobcBookingParty("ACE ZHUANG");
        summary.setWfobcDischargePort("LOS ANGELES,CA");
        summary.setOrgId("");
        summary.setWfobcVesselName("NYK AQUARIUS");
        summary.setWfobcShipper("HEALTH & LIFE (SUZHOU) CO., LTD\\rNO 1428 XIANGILANG ROAD\\rSUZHOU 215129 CHINA\\rKN REF. 4351-9188-804.541");
        summary.setWfobcConsignee("HOMEDICS USA,LLC\\r3000 PONTIAC TRAIL\\rCOMMERCE TOWNSHIP MI 48390\\rUNITED STATES");
        summary.setOrderId("a");
        summary.setWfobcCustomNo("4351-9188-804.541");
        summary.setWfobcTotalGrossWeight("25801.51");
        summary.setExtCompany("KUEHNE & NAGEL LIMITED");
        
        Container c1 = new Container();
        c1.setWfobccContainerNum("1");
        c1.setWfobccContainerType("40GE");
        Container c2 = new Container();
        c2.setWfobccContainerNum("1");
        c2.setWfobccContainerType("40GE");
        Container c3 = new Container();
        c3.setWfobccContainerNum("1");
        c3.setWfobccContainerType("40GE");
        
        List<Container> containers = new ArrayList<>();
        containers.add(c1);
        containers.add(c2);
        containers.add(c3);
        
        Pages pages = new Pages();
        pages.setStartpage(1);
        pages.setEndpage(2);
        pages.setFileName("MAPEMLCN0005546416-4351-9188-804.541+.pdf");
        
        Detail d1 = new Detail();
        d1.setWfobccDescription("WALG AUTO WRIST 2017\\rWALG AUTO ARM 2017\\r3417 PACKAGES\\rHS-CODE(S):9018.19\\rBKG NO:\\rREL NO:");
        d1.setWfobccVolumnUnit("M3");
        d1.setWfobccHscode("9018.19");
        d1.setWfobccQuantity("3417");
        d1.setWfobccMarks("");
        d1.setWfobccVolumn("54.538");
        d1.setWfobccPcs("PACKAGES");
        d1.setWfobccGrossWeight("9204.60");
        d1.setWfobccGrossWeightUnit("KG");
        
        Detail d2 = new Detail();
        d2.setWfobccDescription("WALG AUTO WRIST 2017\\rWALG AUTO ARM 2017\\r2501 PACKAGES\\rHS-CODE(S):9018.19\\rBKG NO:\\rREL NO:");
        d2.setWfobccVolumnUnit("M3");
        d2.setWfobccHscode("9018.19");
        d2.setWfobccQuantity("2501");
        d2.setWfobccMarks("");
        d2.setWfobccVolumn("54.773");
        d2.setWfobccPcs("PACKAGES");
        d2.setWfobccGrossWeight("7927.54");
        d2.setWfobccGrossWeightUnit("KG");
        
        Detail d3 = new Detail();
        d3.setWfobccDescription("WALGREENS DELUXE ARM 2016\\rWALG AUTO ARM 2017\\rSTANDARD SIZE CUFF\\rLARGE SIZE CUFF\\rNEW UNIVERSAL CUFF\\rSTANDARD SIZE CUFF FOR BPA\\r-040\\rLARGE SIZE CUFF FOR BPA-04\\r0\\r2969 PACKAGES\\rHS-CODE(S):9018.19\\rBKG NO:\\rREL NO:");
        d3.setWfobccVolumnUnit("M3");
        d3.setWfobccHscode("9018.19");
        d3.setWfobccQuantity("2969");
        d3.setWfobccMarks("");
        d3.setWfobccVolumn("49.838");
        d3.setWfobccPcs("PACKAGES");
        d3.setWfobccGrossWeight("8669.37");
        d3.setWfobccGrossWeightUnit("KG");
        
        List<Detail> details = new ArrayList<>();
        details.add(d1);
        details.add(d2);
        details.add(d3);
        
        Datum datum = new Datum();
        datum.setSummary(summary);
        datum.setContainer(containers);
        datum.setPages(pages);
        datum.setDetail(details);
        
        List<Datum> items = new ArrayList<>();
        items.add(datum);
        
        Params params = new Params();
        params.setDocType("CONSIGN");
        params.setAppId("FFQ6ht26");
        params.setAppKey("uQ3FE10G");
        params.setAppSecret("d0cef53a5ccef0d564d9c391432c484b");
        params.setReqUuid("dfd1a8308a9242b791fc50d6d659f2db");
        params.setIfNeedOcr("0");
        params.setOcrType("");
        params.setBillStyle("");
        params.setIfNeedCallback("0");
        params.setFieldStyle("UNDERLINE");
        
        Values values = new Values();
        values.setParams(params);
        values.setData(items);
        
        Booking booking = new Booking();
        booking.setResultCode("success");
        booking.setResultMessage("识别成功");
        booking.setValues(values);
        
        return booking;
    }
    
    /**
     * 5.当订舱托书中的付款方式是“FREIGT PREPAID”，请将【装货港】内容复制后填写在【付款地点】栏位，并在【运费条款说明】里输入“FREIGT PREPAID AT 装货港的值”。
     * @return
     */
    public static Booking ONE_43510158804118() {
        
        Summary summary = new Summary();
        summary.setWfobcTransportTerm("CY/CY");
        summary.setWfobcEtd("30/04/2018");
        summary.setWfobcPaymentMode("FREIGHT PREPAID");
        summary.setWfobcTotalVolumn("60.000");
        summary.setWfobcBokContractNo("SINN00769A");
        summary.setWfobcConsignDepartment("");
        summary.setWfobcLoadPort("SHANGHAI");
        summary.setWfobcNotify("KUEHNE & NAGEL SPOL S R O\\rPEKARSKA 7\\r15500 PRAHA 5\\rCZECH REPUBLIC");
        summary.setWfobcBookingRemark("CONTRACT NUMBER: SINN00769A\\rOB/L NUMBER:");
        summary.setWfobcVoyage("004W");
        summary.setWfobcBookingParty("JANE XU");
        summary.setWfobcDischargePort("HAMBURG");
        summary.setOrgId("");
        summary.setWfobcVesselName("MOL TRIBUTE");
        summary.setWfobcShipper("KUEHNE & NAGEL LIMITED\\r11-16F, BLOCK 1, LIFE HUB AT DANING\\rOFFICE TOWER, 1868, GONG HE XIN RD,\\rZHABEI DISTRICT, SHANGHAI, CHINA\\rAGENT OF BLUE ANCHOR LINE\\rKN REF. 4351-0158-804.118");
        summary.setWfobcConsignee("KUEHNE & NAGEL SPOL S R O\\rPEKARSKA 7\\r15500 PRAHA 5\\rCZECH REPUBLIC\\rAGENT OF BLUE ANCHOR LINE");
        summary.setOrderId("a");
        summary.setWfobcCustomNo("4351-0158-804.118");
        summary.setWfobcTotalGrossWeight("15835.00");
        summary.setExtCompany("KUEHNE & NAGEL LIMITED");
        
        Container container = new Container();
        container.setWfobccContainerNum("1");
        container.setWfobccContainerType("40GE");
        List<Container> containers = new ArrayList<>();
        containers.add(container);
        
        Pages pages = new Pages();
        pages.setStartpage(1);
        pages.setEndpage(1);
        pages.setFileName("MAPEMLCN0005565383-4351-0158-804.118+.PDF");
        
        Detail detail = new Detail();
        detail.setWfobccDescription("PLASTIC\\rNAC:\\rBESTWAY INFLATABLES CORP\\rBKG NO:\\rREL NO:");
        detail.setWfobccVolumnUnit("M3");
        detail.setWfobccQuantity("1");
        detail.setWfobccMarks("N/M");
        detail.setWfobccVolumn("60.000");
        detail.setWfobccPcs("40' HC");
        detail.setWfobccGrossWeight("15835.00");
        detail.setWfobccGrossWeightUnit("KG");
        List<Detail> details = new ArrayList<>();
        details.add(detail);
        
        Datum datum = new Datum();
        datum.setSummary(summary);
        datum.setContainer(containers);
        datum.setPages(pages);
        datum.setDetail(details);
        
        List<Datum> items = new ArrayList<>();
        items.add(datum);
        
        Params params = new Params();
        params.setDocType("CONSIGN");
        params.setAppId("FFQ6ht26");
        params.setAppKey("uQ3FE10G");
        params.setAppSecret("d0cef53a5ccef0d564d9c391432c484b");
        params.setReqUuid("bcf204b4e96245a7a19d8868582a7cf1");
        params.setIfNeedOcr("0");
        params.setOcrType("");
        params.setBillStyle("");
        params.setIfNeedCallback("0");
        params.setFieldStyle("UNDERLINE");
        
        Values values = new Values();
        values.setParams(params);
        values.setData(items);
        
        Booking booking = new Booking();
        booking.setResultCode("success");
        booking.setResultMessage("识别成功");
        booking.setValues(values);
        
        return booking;
    }
    
    /**
     * 9.收发通信息导入客户端后，每行不能超过35个字符，超过则自动换行，注意：不能将一个单词分裂为两行
     * 3.在导入订舱托书的时候，请在品名里截取HS CODE填写到客户端的【HS CODE】栏位中，要求：
     *    A.如果卸货港或目的港是美国、加拿大港口的，HS CODE必须是8位数字。若订舱托书中品名内的HS CODE超过8位，请系统自动截取前8位填写到指定栏位，若不满8未，则末尾自动以“0”补齐后填写到指定栏位；
     *    B.如果卸货港或目的港是非美国、加拿大港口的，HS CODE必须是6位数字。若订舱托书中品名内的HS CODE超过6位，请系统自动截取前6位填写到指定栏位，若不满6未，则末尾自动以“0”补齐后填写到指定栏位；
     * 6.所有订舱托书中的包装单位都取单数，例如托书上显示PACKAGES，导入客户端就要显示为PACKAGE。
     * 7.如果订舱的托书中，其卸货港或目的港是美国线、加拿大港口，且收发通开头都是“KUEHNE”，请在导入托书的同时，在备注里中添加：
     *   SCAC CODE:BANQ
     *   ACI CODE:8041
     *   注意：以上内容分两行显示在备注中，也不要连接在其他备注内容后   
     * @return
     */
    public static Booking ONE_43510375803063() {
        
        Summary summary = new Summary();
        summary.setWfobcTransportTerm("CY/CY");
        summary.setWfobcEtd("03/05/2018");
        summary.setWfobcPaymentMode("FREIGHT PREPAID");
        summary.setWfobcTotalVolumn("66.936");
        summary.setWfobcBokContractNo("FIX-RIC5076704_EC");
        summary.setWfobcConsignDepartment("");
        summary.setWfobcLoadPort("SHANGHAI");
        summary.setWfobcNotify("KUEHNE + NAGEL, INC.\\r1001 BUSSE ROAD\\rELK GROVE VILLAGE, IL 60007\\rUSA\\rREF:1018-6692-540.000");
        summary.setWfobcBookingRemark("CONTRACT NUMBER: FIX-RIC5076704_EC\\rOB/L NUMBER:");
        summary.setWfobcVoyage("001E");
        summary.setWfobcBookingParty("PEILI JI");
        summary.setWfobcDischargePort("WILMINGTON, NC");
        summary.setOrgId("");
        summary.setWfobcVesselName("NORTHERN JUSTICE");
        summary.setWfobcShipper("KUEHNE & NAGEL LIMITED\\r11-16F, BLOCK 1, LIFE HUB AT DANING\\rOFFICE TOWER, 1868, GONG HE XIN RD,\\rZHABEI DISTRICT, SHANGHAI, CHINA\\rAGENT OF BLUE ANCHOR AMERICA LINE\\rKN REF. 4351-0375-803.063");
        summary.setWfobcConsignee("KUEHNE + NAGEL, INC.\\r1001 BUSSE ROAD\\rELK GROVE VILLAGE, IL 60007\\rUSA\\rAGENT OF BLUE ANCHOR AMERICA LINE");
        summary.setOrderId("a");
        summary.setWfobcCustomNo("4351-0375-803.063");
        summary.setWfobcTotalGrossWeight("7777.60");
        summary.setExtCompany("KUEHNE & NAGEL LIMITED");
        
        Container container = new Container();
        container.setWfobccContainerNum("1");
        container.setWfobccContainerType("40HC");
        List<Container> containers = new ArrayList<>();
        containers.add(container);
        
        Pages pages = new Pages();
        pages.setStartpage(1);
        pages.setEndpage(1);
        pages.setFileName("MAPEMLCN0005553909-4351-0375-803.063+.PDF");
        
        Detail detail = new Detail();
        detail.setWfobccDescription("OFFICE CHAIR\\rNO S.W.P.M.\\rS/C:FIX-RIC5076704_EC\\rNAC:OFM\\rA1 \\rHS-CODE(S):940130\\rBKG NO:\\rREL NO:");
        detail.setWfobccVolumnUnit("M3");
        detail.setWfobccHscode("940130");
        detail.setWfobccQuantity("1");
        detail.setWfobccMarks("OFM\\r1503810");
        detail.setWfobccVolumn("66.936");
        detail.setWfobccPcs("40' HC");
        detail.setWfobccGrossWeight("7777.60");
        detail.setWfobccGrossWeightUnit("KG");
        List<Detail> details = new ArrayList<>();
        details.add(detail);
        
        Datum datum = new Datum();
        datum.setSummary(summary);
        datum.setContainer(containers);
        datum.setPages(pages);
        datum.setDetail(details);
        
        List<Datum> items = new ArrayList<>();
        items.add(datum);
        
        Params params = new Params();
        params.setDocType("CONSIGN");
        params.setAppId("FFQ6ht26");
        params.setAppKey("uQ3FE10G");
        params.setAppSecret("d0cef53a5ccef0d564d9c391432c484b");
        params.setReqUuid("8f862069ab2e4895b4e07cd80f84c06f");
        params.setIfNeedOcr("0");
        params.setOcrType("");
        params.setBillStyle("");
        params.setIfNeedCallback("0");
        params.setFieldStyle("UNDERLINE");
        
        Values values = new Values();
        values.setParams(params);
        values.setData(items);
        
        Booking booking = new Booking();
        booking.setResultCode("success");
        booking.setResultMessage("识别成功");
        booking.setValues(values);
        
        return booking;
    }
    
    /**
     * 3.美加线如果【卸货港】≠【交货地】，【运输条款】栏位请根据附件表格中“转运方式及条款”中的内容，填写在【品名】栏位和【补充信息】栏位内（细节可后续讨论）
     * @return
     */
    public static Booking MSC_43519128804023() {
        
        Summary summary = new Summary();
        summary.setWfobcTransportTerm("CY/CY");
        summary.setWfobcEtd("20/04/2018");
        summary.setWfobcPaymentMode("FREIGHT PREPAID");
        summary.setWfobcTotalVolumn("17.500");
        summary.setWfobcBokContractNo("FAK-17-107TPC_IPI");
        summary.setWfobcConsignDepartment("");
        summary.setWfobcLoadPort("SHANGHAI");
        summary.setWfobcNotify("KUEHNE + NAGEL, INC.\\r1001 BUSSE ROAD\\rELK GROVE VILLAGE, IL 60007\\rUSA");
        summary.setWfobcBookingRemark("MSCU JAGUAR\\r1X20GP 4/20\\rCONTRACT NUMBER: FAK-17-107TPC_IPI\\rOB/L NUMBER:");
        summary.setWfobcVoyage("815N");
        summary.setWfobcBookingParty("ENIX ZHANG");
        summary.setWfobcDischargePort("LONG BEACH,CA");
        summary.setOrgId("");
        summary.setWfobcVesselName("MAERSK EMDEN");
        summary.setWfobcShipper("KUEHNE & NAGEL LIMITED\\r11-16F, BLOCK 1, LIFE HUB AT DANING\\rOFFICE TOWER, 1868, GONG HE XIN RD,\\rZHABEI DISTRICT, SHANGHAI, CHINA\\rAGENT OF BLUE ANCHOR AMERICA LINE\\rKN REF. 4351-9128-804.023");
        summary.setWfobcConsignee("KUEHNE + NAGEL, INC.\\r1001 BUSSE ROAD\\rELK GROVE VILLAGE, IL 60007\\rUSA\\rAGENT OF BLUE ANCHOR AMERICA LINE");
        summary.setOrderId("a");
        summary.setWfobcCustomNo("4351-9128-804.023");
        summary.setWfobcTotalGrossWeight("16651.00");
        summary.setExtCompany("KUEHNE & NAGEL LIMITED");
        
        Container container = new Container();
        container.setWfobccContainerNum("1");
        container.setWfobccContainerType("20GE");
        List<Container> containers = new ArrayList<>();
        containers.add(container);
        
        Pages pages = new Pages();
        pages.setStartpage(1);
        pages.setEndpage(1);
        pages.setFileName("MAPEMLCN0005526435-4351-9128-804.023+.PDF");
        
        Detail detail = new Detail();
        detail.setWfobccDescription("LAMINATE FLOORING\\rBKG NO:\\rREL NO:");
        detail.setWfobccVolumnUnit("M3");
        detail.setWfobccQuantity("1");
        detail.setWfobccMarks("USBO-LAM-182");
        detail.setWfobccVolumn("17.500");
        detail.setWfobccPcs("20' GE");
        detail.setWfobccGrossWeight("16651.00");
        detail.setWfobccGrossWeightUnit("KG");
        List<Detail> details = new ArrayList<>();
        details.add(detail);
        
        Datum datum = new Datum();
        datum.setSummary(summary);
        datum.setContainer(containers);
        datum.setPages(pages);
        datum.setDetail(details);
        
        List<Datum> items = new ArrayList<>();
        items.add(datum);
        
        Params params = new Params();
        params.setDocType("CONSIGN");
        params.setAppId("FFQ6ht26");
        params.setAppKey("uQ3FE10G");
        params.setAppSecret("d0cef53a5ccef0d564d9c391432c484b");
        params.setReqUuid("5e2d0bc0a44d419b88a4b5d9063c97e3");
        params.setIfNeedOcr("0");
        params.setOcrType("");
        params.setBillStyle("");
        params.setIfNeedCallback("0");
        params.setFieldStyle("UNDERLINE");
        
        Values values = new Values();
        values.setParams(params);
        values.setData(items);
        
        Booking booking = new Booking();
        booking.setResultCode("success");
        booking.setResultMessage("识别成功");
        booking.setValues(values);
        
        return booking;
    }
    
    /**
     * 3.美加线如果【卸货港】≠【交货地】，【运输条款】栏位请根据附件表格中“转运方式及条款”中的内容，填写在【品名】栏位和【补充信息】栏位内（细节可后续讨论）
     * @return
     */
    public static Booking MSC_43510382804029() {
        
        Summary summary = new Summary();
        summary.setWfobcTransportTerm("CY/CY");
        summary.setWfobcEtd("27/04/2018");
        summary.setWfobcPaymentMode("FREIGHT PREPAID");
        summary.setWfobcTotalVolumn("68.000");
        summary.setWfobcBokContractNo("17-108TPC-126");
        summary.setWfobcConsignDepartment("");
        summary.setWfobcLoadPort("SHANGHAI");
        summary.setWfobcNotify("KUEHNE + NAGEL, INC.\\r11501 METRO AIRPORT CENTER DRIVE\\rSUITE 100\\rROMULUS, MI 48174,  U.S.A.");
        summary.setWfobcBookingRemark("CONTRACT NUMBER: 17-108TPC-126\\rOB/L NUMBER:\\r4/27 MSC JAGUAR FM SHA TO DET VIA LGB\\r1*40HQ\\rA/C YANFENG\\rMSC CODE: US003858");
        summary.setWfobcVoyage("FX816N");
        summary.setWfobcBookingParty("CHERRY LI");
        summary.setWfobcDischargePort("LONG BEACH,CA");
        summary.setOrgId("");
        summary.setWfobcVesselName("MSC LAUREN");
        summary.setWfobcShipper("KUEHNE & NAGEL LIMITED\\r11-16F, BLOCK 1, LIFE HUB AT DANING\\rOFFICE TOWER, 1868, GONG HE XIN RD,\\rZHABEI DISTRICT, SHANGHAI, CHINA\\rAGENT OF BLUE ANCHOR AMERICA LINE\\rKN REF. 4351-0382-804.029");
        summary.setWfobcConsignee("KUEHNE + NAGEL, INC.\\r11501 METRO AIRPORT CENTER DRIVE\\rSUITE 100\\rROMULUS, MI 48174,  U.S.A.\\rAGENT OF BLUE ANCHOR AMERICA LINE");
        summary.setOrderId("a");
        summary.setWfobcCustomNo("4351-0382-804.029");
        summary.setWfobcTotalGrossWeight("3960.00");
        summary.setExtCompany("KUEHNE & NAGEL LIMITED");
        
        Container container = new Container();
        container.setWfobccContainerNum("1");
        container.setWfobccContainerType("40HC");
        List<Container> containers = new ArrayList<>();
        containers.add(container);
        
        Pages pages = new Pages();
        pages.setStartpage(1);
        pages.setEndpage(2);
        pages.setFileName("MAPEMLCN0005521975-4351-0382-804.029+.PDF");
        
        Detail detail = new Detail();
        detail.setWfobccDescription("AUTO PARTS\\r(DOOR'S TRIM)\\rNO S.W.P.M.\\rA/C YANFENG\\rNO MSC CHASSIS REQUIRED\\rDESTINATION\\rHS-CODE(S):870829\\rBKG NO:\\rREL NO:");
        detail.setWfobccVolumnUnit("M3");
        detail.setWfobccHscode("870829");
        detail.setWfobccQuantity("1");
        detail.setWfobccMarks("N/M");
        detail.setWfobccVolumn("68.000");
        detail.setWfobccPcs("40' HC");
        detail.setWfobccGrossWeight("3960.00");
        List<Detail> details = new ArrayList<>();
        details.add(detail);
        
        Datum datum = new Datum();
        datum.setSummary(summary);
        datum.setContainer(containers);
        datum.setPages(pages);
        datum.setDetail(details);
        
        List<Datum> items = new ArrayList<>();
        items.add(datum);
        
        Params params = new Params();
        params.setDocType("CONSIGN");
        params.setAppId("FFQ6ht26");
        params.setAppKey("uQ3FE10G");
        params.setAppSecret("d0cef53a5ccef0d564d9c391432c484b");
        params.setReqUuid("c243f942ea714e24ba3a643afcfab207");
        params.setIfNeedOcr("0");
        params.setOcrType("");
        params.setBillStyle("");
        params.setIfNeedCallback("0");
        params.setFieldStyle("UNDERLINE");
        
        Values values = new Values();
        values.setParams(params);
        values.setData(items);
        
        Booking booking = new Booking();
        booking.setResultCode("success");
        booking.setResultMessage("识别成功");
        booking.setValues(values);
        
        return booking;
    }
    
    /**
     * 1.收发通信息导入客户端后，每行不能超过35个字符，超过则自动换行，注意：不能将一个单词分裂为两行
     * @return
     */
    public static Booking IAL_43510139804040() {
        
        Summary summary = new Summary();
        summary.setWfobcTransportTerm("CY/CY");
        summary.setWfobcEtd("02/05/2018");
        summary.setWfobcPaymentMode("FREIGHT PREPAID");
        summary.setWfobcTotalVolumn("45.000");
        summary.setWfobcBokContractNo("KONE");
        summary.setWfobcConsignDepartment("");
        summary.setWfobcLoadPort("SHANGHAI");
        summary.setWfobcNotify("KUEHNE + NAGEL SDN. BHD.\\r1 JALAN BUMBUNG U8/90 SEKSYEN U8\\rPERINDUSTRIAN BUKIT JELUTONG\\r40150 SHAN ALAM, SELANGOR, MALAYSIA");
        summary.setWfobcBookingRemark("CONTRACT NUMBER: KONE\\rOB/L NUMBER:");
        summary.setWfobcVoyage("TBA");
        summary.setWfobcBookingParty("SCOTT CHE");
        summary.setWfobcDischargePort("PORT KLANG");
        summary.setOrgId("");
        summary.setWfobcVesselName("TBN");
        summary.setWfobcShipper("KUEHNE & NAGEL LIMITED\\r11-16F, BLOCK 1, LIFE HUB AT DANING\\rOFFICE TOWER, 1868, GONG HE XIN RD,\\rZHABEI DISTRICT, SHANGHAI, CHINA\\rAGENT OF BLUE ANCHOR LINE\\rKN REF. 4351-0139-804.040");
        summary.setWfobcConsignee("KUEHNE + NAGEL SDN. BHD.\\r1 JALAN BUMBUNG U8/90 SEKSYEN U8\\rPERINDUSTRIAN BUKIT JELUTONG\\r40150 SHAN ALAM, SELANGOR, MALAYSIA\\rAGENT OF BLUE ANCHOR LINE");
        summary.setOrderId("a");
        summary.setWfobcCustomNo("4351-0139-804.040");
        summary.setWfobcTotalGrossWeight("45000.00");
        summary.setExtCompany("KUEHNE & NAGEL LIMITED");
        
        Container c1 = new Container();
        c1.setWfobccContainerNum("1");
        c1.setWfobccContainerType("40HC");
        Container c2 = new Container();
        c2.setWfobccContainerNum("1");
        c2.setWfobccContainerType("40HC");
        Container c3 = new Container();
        c3.setWfobccContainerNum("1");
        c3.setWfobccContainerType("40HC");
        
        List<Container> containers = new ArrayList<>();
        containers.add(c1);
        containers.add(c2);
        containers.add(c3);
        
        Pages pages = new Pages();
        pages.setStartpage(1);
        pages.setEndpage(2);
        pages.setFileName("MAPEMLCN0005560197-4351-0139-804.040+.PDF");
        
        Detail d1 = new Detail();
        d1.setWfobccDescription("TWO PIECES OF MAP\\rESCALATOR\\rBKG NO:\\rREL NO:");
        d1.setWfobccVolumnUnit("M3");
        d1.setWfobccQuantity("1");
        d1.setWfobccMarks("KONE\\rES20180261");
        d1.setWfobccVolumn("15.000");
        d1.setWfobccPcs("40' HC");
        d1.setWfobccGrossWeight("15000.00");
        d1.setWfobccGrossWeightUnit("KG");
        
        Detail d2 = new Detail();
        d2.setWfobccDescription("TWO PIECES OF MAP00\\rESCALATOR\\rBKG NO:\\rREL NO:");
        d2.setWfobccVolumnUnit("M3");
        d2.setWfobccQuantity("1");
        d2.setWfobccMarks("KONE\\rES20180261");
        d2.setWfobccVolumn("15.000");
        d2.setWfobccPcs("40' HC");
        d2.setWfobccGrossWeight("15000.00");
        d2.setWfobccGrossWeightUnit("KG");
        
        Detail d3 = new Detail();
        d3.setWfobccDescription("TWO PIECES OF MAP\\rESCALATOR\\rBKG NO:");
        d3.setWfobccVolumnUnit("M3");
        d3.setWfobccQuantity("1");
        d3.setWfobccMarks("KONE\\rES20180261");
        d3.setWfobccVolumn("15.000");
        d3.setWfobccPcs("40' HC");
        d3.setWfobccGrossWeight("15000.00");
        d3.setWfobccGrossWeightUnit("KG");
        
        List<Detail> details = new ArrayList<>();
        details.add(d1);
        details.add(d2);
        details.add(d3);
        
        Datum datum = new Datum();
        datum.setSummary(summary);
        datum.setContainer(containers);
        datum.setPages(pages);
        datum.setDetail(details);
        
        List<Datum> items = new ArrayList<>();
        items.add(datum);
        
        Params params = new Params();
        params.setDocType("CONSIGN");
        params.setAppId("FFQ6ht26");
        params.setAppKey("uQ3FE10G");
        params.setAppSecret("d0cef53a5ccef0d564d9c391432c484b");
        params.setReqUuid("77d395ed575648059131026175c1a4d9");
        params.setIfNeedOcr("0");
        params.setOcrType("");
        params.setBillStyle("");
        params.setIfNeedCallback("0");
        params.setFieldStyle("UNDERLINE");
        
        Values values = new Values();
        values.setParams(params);
        values.setData(items);
        
        Booking booking = new Booking();
        booking.setResultCode("success");
        booking.setResultMessage("识别成功");
        booking.setValues(values);
        
        return booking;
    }
}
