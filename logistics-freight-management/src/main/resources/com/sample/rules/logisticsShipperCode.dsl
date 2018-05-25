[condition][]装货地址是以 "{value}" 开头的=summary : Summary(wfobcShipper.startsWith("{value}"))
[condition][]订单是特殊订单=order : Order(isSpecial == true)
[condition][]订单的级别是 "{value}"=order : Order(level == "{value}")
[consequence][]发货人代码栏位内填入 "{value}"=summary.setWfobcShipperCode("{value}");
[consequence][]订单的费用是 {value}=order.setFee({value});
