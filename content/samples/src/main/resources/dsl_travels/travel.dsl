[condition][Reservation]客户想升舱=r : Reservation(classUpgrade == true)
[condition][Customer]客户是 {value} 会员=Customer(loyaltyLevel == "{value}")
[consequence][][r : Reservation]收取 {value} 的升舱费用=r.setClassUpgradeFees(new BigDecimal("{value}"));
