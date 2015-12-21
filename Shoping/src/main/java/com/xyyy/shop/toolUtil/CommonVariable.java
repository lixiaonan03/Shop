package com.xyyy.shop.toolUtil;

/**
 * @Description:一些常量和访问服务器的一些路径配置
 */
public class CommonVariable {

	//爱华的
	//public static String IP1="http://192.168.10.27";
	//公网开发所用的
	public static String IP1="http://test.sun-yard.com";
	//正式服务器的嵌套页地址
	//public static String IP1="http://weixin.sun-yard.com";
	// 开发测试服务器地址
	//public static String IP = "http://101.200.228.203:8181";
	// 产品测试环境下服务器地址
	public static String IP = "http://101.200.228.203:81";
	//正式服务器地址
	//public static String IP = "http://weixin.sun-yard.com:51";
	/**
	 * 嵌套的首页的地址
	 */
	public static String HomeURL =IP1+ "/mall/new/home?device=android";
	/**
	 * 嵌套的订单商品评价地址
	 */
	public static String OrderEvaluateURL = IP1+"/mall/eval/evalList?device=android&orderId=";
	/**
	 * 商品详情url
	 */
	public static String GoodDeatilURL = IP1+"/mall/cat/goodInfo?id=";
	/**
	 * 商品详情 图文介绍 mall/goods/goodsDesc?id=%@&device=ios 商品详情
	 */
	public static String GoodDeatilIntroduceURL = IP1+"/mall/goods/goodsDesc?id=";
	/**
	 * 商品详情 评价列表的url mall/cat/getAllEval?goodsId=%@&fivePec=%@&device=ios
	 */
	public static String GoodDeatilEvaluateURL = IP1+"/mall/cat/getAllEval?goodsId=";
	/**
	 *  帮助中心url
	 */
	public static String HelpURL = IP1+"/mall/help/goHelp?device=android";
	/**
	 * 会员注册协议
	 */
	public static String MemberAgreementURL = IP1+"/mall/memb/goMembProtocol?device=android";
	
	
	/**
	 * 物流信息的url嵌套
	 */
	//public static String MemberAgreementURL = IP1+"/mall/memb/goMembProtocol?device=android";
	public static String OrderGetLogisticsURL = "http://192.168.10.27/mall/logistics/getLogistics?orderId=";
	/**
	 * 近日配送清单 菜谱的页面
	 */
	//public static String GetCookbookURL = IP1+"/mall/memb/goMembProtocol?device=android";
	public static String GetCookbookURL = "http://192.168.10.27/mall/detail/getCookBook?menuId=";
	
	
	/**
	 * 获取短信验证码的接口
	 */
	public static String GetCodeURL = IP
			+ "/mallService/api/EnnSmsCode/getVlidateCode/v1/";
	/**
	 * 登录的url
	 */
	public static String LoginURL = IP
			+ "/mallService/api/EnnUserLoginfo/getMemb/v2/";
	/**
	 * 注册的url
	 */
	public static String RegisterURL = IP
			+ "/mallService/api/EnnUserLoginfo/create/v2/";
	/**
	 * 获取商品分类的url 一级的带的参数为-100 2级的参数为 一级的id
	 */
	public static String GetCatURL = IP
			+ "/mallService/api/EnnGoodsCat/getCat/v1/";
	/**
	 * 获取商品列表的url 参数为2级分类的id
	 */
	public static String GetGoodURL = IP
			+ "/mallService/api/EnnGoods/getGoods/v1/";
	/*public static String GetGoodURL = IP
			+ "/mallService/api/EnnGoods/searchByName/v3";*/
	/**
	 * 搜索商品的url
	 */
	public static String SearchGoodURL = IP
			+ "/mallService/api/EnnGoods/searchByName/v1/";

	/**
	 * 通过商品id获取商品详情
	 */
	public static String GoodInfoURL = IP
			+ "/mallService/api/EnnGoods/goodItem/v1/";

	/**
	 * 加入商品到购物车的url
	 */
	public static String CartAddGoodURL = IP
			+ "/mallService/api/EnnCart/addPdt2Cart/v1";
	/**
	 * 根据人员id获取 购物车信息的url
	 */
	public static String CartGetGoodURL = IP
			+ "/mallService/api/EnnCart/getGoodCar/v1/";
	/**
	 * 根据人员id获取 当前购物车商品数量总数的url
	 */
	public static String CartGetGoodNumURL = IP
			+ "/mallService/api/EnnCart/getCarGoodsNum/v1/";
	/**
	 * 人员删除购物车中商品的url
	 */
	public static String CartDeleteGoodURL = IP
			+ "/mallService/api/EnnCart/getGoodCarDeletePdt/v1/";
	/**
	 * 人员更新购物车中商品的url 批量
	 */
	// public static String
	// CartUpdateAllGoodURL=IP+"/mallService/api/EnnCart/updateByUserIdAndGoodsIds/v2";
	/**
	 * 人员在登录之后同步购物车数据的接口
	 */
	public static String CartLoginUpdateAllGoodURL = IP
			+ "/mallService/api/EnnCart/updateByUserIdAndGoodsIds/v3";
	/**
	 * 人员更新购物车中商品的url
	 */
	public static String CartUpdateGoodURL = IP
			+ "/mallService/api/EnnCart/updateByUserIdAndGoodsIds/v1";
	/**
	 * 人员点击购物车去结算按钮需要的订单信息
	 */
	public static String CartOrderURL = IP
			+ "/mallService/api/EnnOrder/orderInfo/v1/";
	/**
	 * 人员点击购物车去结算按钮需要的会员卡配送信息 参数人员id
	 */
	public static String CartOrderMemberURL = IP
			+ "/mallService/api/EnnCard/getEnableCardByMemId/v1/";
	/**
	 * 判断购物车中的商品是否包含会员卡
	 */
	public static String CartOrderIshasMembercardURL = IP
			+ "/mallService/api/EnnGoods/isContainCard/v1";
	/**
	 * 购物车模块创建订单的url
	 */
	public static String CartCreatOrderURL = IP
			+ "/mallService/api/EnnOrder/createOrder/v2";

	/**
	 * 购物车模块 获取发票列表的url 参数 人员id
	 */
	public static String CartGetInvociesURL = IP
			+ "/mallService/api/EnnMemberInvoice/getInvoiceByMembId/v1/";
	/**
	 * 购物车模块删除发票的url 参数 发票id
	 */
	public static String CartDelInvocieURL = IP
			+ "/mallService/api/EnnMemberInvoice/delete/v1/";
	/**
	 * 获取确认订单之后支付详情信息的url
	 */
	public static String GetOrderPayDetailURL = IP
			+ "/mallService/api/EnnOrder/getPayMsg/v1/";
	/**
	 * 用户支付时判断用户是否有支付密码
	 */
	public static String GetUserMembercardPayPasswordURL = IP
			+ "/mallService/api/EnnMemberPaypwd/search/v1";
	/**
	 * 更新用户会员信息 (头像信息)
	 */
	public static String UpdateMemberinfoImgURL = IP
			+ "/mallService/api/EnnMember/update/v1";
	/**
	 * 更新用户会员信息
	 */
	public static String UpdateMemberinfoURL = IP
			+ "/mallService/api/EnnMember/update/v2";
	/**
	 * 更新用户密码信息的
	 */
	public static String UpdateMemberPasswordURL = IP
			+ "/mallService/api/EnnMember/updateMemberPass/v1/";
	/**
	 * 找回用户密码的url
	 * api/EnnUserLoginfo/getBackPass/v1/{phone}/{verifiCode}/{passwd}
	 */
	public static String FindMemberPasswordURL = IP
			+ "/mallService/api/EnnUserLoginfo/getBackPass/v1/";
	/**
	 * 完善用户资料
	 */
	public static String MemberCompleteURL = IP
			+ "/mallService/api/EnnMember/perfectData/v1";
	/**
	 * 修改用户手机号的 {phone}/{verifiCode} 返回member对象
	 */
	public static String ChangeUserphoneOneURL = IP
			+ "/mallService/api/EnnMember/validatePhone/v2/";
	/**
	 * 修改用户手机号第2步的
	 */
	public static String ChangeUserphoneTwoURL = IP
			+ "/mallService/api/EnnMember/changeUserPhone/v1/";

	/**
	 * 获取人员全部订单的信息url
	 */
	public static String GetAllOrderURL = IP
			+ "/mallService/api/EnnOrder/getOrderVOs/v3";
	/**
	 * 获取订单详情的url
	 */
	public static String GetOrderDetailURL = IP
			+ "/mallService/api/EnnOrder/myOrderInfo/v2/";
	/**
	 * 获取订单中支付详细列表的url
	 */
	public static String GetOrderPayListURL = IP
			+ "/mallService/api/EnnPayRecord/searchDetail/v1";
	

	/**
	 * 获取我的收货地址的url
	 */
	public static String GetMyAddressURL = IP
			+ "/mallService/api/EnnMemberReceipt/getByMembId/v1/";
	/**
	 * 修改我的收货地址的url
	 */
	public static String UpdateMyAddressURL = IP
			+ "/mallService/api/EnnMemberReceipt/update/v1";
	/**
	 * 批量修改我的收货地址的url
	 */
	public static String UpdateMyAddressListURL = IP
			+ "/mallService/api/EnnMemberReceipt/update/v2";
	/**
	 * 删除我的收货地址的url
	 */
	public static String DeleteMyAddressURL = IP
			+ "/mallService/api/EnnMemberReceipt/delete/v1/";
	/**
	 * 添加我的收货地址的url
	 */
	public static String AddMyAddressURL = IP
			+ "/mallService/api/EnnMemberReceipt/create/v1";

	/**
	 * 通过人员id获取我的会员卡的url
	 */
	public static String GetMyMemberURL = IP
			+ "/mallService/api/EnnCard/getCardByMemId/v1/";

	/**
	 * 获取用户礼品卡详情的url  卡id+ 
	 */
	public static String GetGiftDetailURL = IP
			+ "/mallService/api/EnnCard/giftCardDetail/v1/";
	/**
	 * 用户礼品卡开启是使用的接口  卡id+ 
	 */
	public static String UpdateGiftStateURL = IP
			+ "/mallService/api/EnnCard/exchangeGiftCard/v1/";
	/**
	 * 通过礼品卡获取订单详情的
	 */
	public static String GetOrderByCardcodeURL = IP
			+ "/mallService/api/EnnOrder/giftCardOrderInfo/v3/";
	/**
	 * 获取用户所绑定的会员卡详情的url
	 */
	public static String GetMembercardDetailURL = IP
			+ "/mallService/api/EnnCard/getCardDetail/v1/";
	/**
	 * 用户修改会员卡详情的url
	 */
	public static String UpdateMembercardDetailURL = IP
			+ "/mallService/api/EnnCard/updateCardDetail/v1";
	/**
	 * 根据会员卡类型 获取会员卡商品列表的url
	 */
	public static String GetMembercardBytypeURL = IP
			+ "/mallService/api/EnnCard/getCardByType/v1/";
	/**
	 * 会员绑定旧会员卡的url
	 * api/EnnCard/bindCard/v2/{cardCode}/{userPhone}/{randCode}/{membId}
	 */
	public static String BindMemberURL = IP
			+ "/mallService/api/EnnCard/bindCard/v2/";
	/**
	 * 会员绑定新会员卡的url api/EnnCard/bindCard/v1/{cardCode}/{activeCode}/{membId}
	 */
	public static String BindNewMemberURL = IP
			+ "/mallService/api/EnnCard/bindCard/v3/";
	/**
	 * 找回用户会员卡支付密码的
	 */
	public static String MembercardPayPasswordFindURL = IP
			+ "/mallService/api/EnnMember/getBackPayPass/v1/";
	/**
	 * 设置用户会员卡支付密码的
	 */
	public static String MembercardPayPasswordSetURL = IP
			+ "/mallService/api/EnnMemberPaypwd/setPayPwd/v1/";
	/**
	 * 获取用户会员卡密码的
	 */
	public static String MembercardPayPasswordGetURL = IP
			+ "/mallService/api/EnnMemberPaypwd/search/v1";
	/**
	 * 修改用户会员卡支付密码的
	 */
	public static String MembercardPayPasswordUpdateURL = IP
			+ "/mallService/api/EnnMemberPaypwd/setPayPwd/v1/";
	/**
	 * 获取会员卡消费记录的列表
	 */
	public static String GetMemberCardRecord = IP
			+ "/mallService/api/EnnCardRecord/search/v1";
	/*public static String GetMemberCardRecord = IP
			+ "/mallService/api/EnnCardRecord/searchByPage/v1";*/
	
	/**
	 * 会员卡充值规则接口
	 */
	public static String GetCardRulesURL = IP + "/mallService/api/EnnCardRule//list/v1";

	/**
	 * 获取所有菜品的url 包含会员口味
	 */
	public static String GetAllDishesURL = IP
			+ "/mallService/api/EnnDishes/getAllDishes/v4/";
	/**
	 * 获取会员口味的url
	 */
	public static String GetMemberTasteURL = IP
			+ "/mallService/api/EnnMemberFlavor/getMemFlavor/v1/";
	/**
	 * 获取会员全部口味的url
	 */
	public static String GetMemberAllTasteURL = IP
			+ "/mallService/api/EnnMemberFlavor/getMemFlavors/v1";
	/**
	 * 添加会员口味的url
	 */
	public static String AddMemberTasteURL = IP
			+ "/mallService/api/EnnMemberFlavor/create/v1";
	/**
	 * 删除会员口味的url
	 */
	public static String DeleteMemberTasteURL = IP
			+ "/mallService/api/EnnMemberFlavor/delete/v1";

	/**
	 * 判断当前用户是否能进行选菜操作
	 */
	public static String IsDelivenNoticeURL = IP
			+ "/mallService/api/EnnDeliveNotice/isDeliveNotice/v1/";
	/**
	 * 获取会员配送预告的url
	 */
	public static String GetDelivenNoticeURL = IP
			+ "/mallService/api/EnnDeliveNotice/getDelivenNotice/v2/";
	/**
	 * 修改会员配送预告的url
	 */
	public static String UpdateDelivenNoticeURL = IP
			+ "/mallService/api/EnnMembVege/saveMembVege/v1";

	/**
	 * 获取近日配送清单的url
	 */
	public static String GetDeliveRecodeURL = IP
			+ "/mallService/api/EnnOrderDelive/deliveRecode/v1/";
	/**
	 * 近日配送清单详情的url
	 */
	public static String GetDeliveDetailURL = IP
			+ "/mallService/api/EnnOrderDelive/deliveDetail/v2/";
    
	
	//收货地址的处理
	/**
	 * 根据上一级目录查出该目录下的内容
	 */
	public static String GetAreaListURL = IP
			+ "/mallService/api/EnnSysArea/searchAreaByParentId/v1/";
	/**
	 * 根据id查找出详细省市区地址
	 */
	public static String GetAreaDetailURL = IP
			+ "/mallService/api/EnnSysArea/get/v1/";
	/**
	 * 根据收货地址id和商品列表获取可购买的商品id
	 */
	public static String GetShowgoodByAddressidURL = IP
			+ "/mallService/api/EnnGoods/showGoods/v1";
	/**
	 * 通过商品id获取收货地址列表
	 */
	public static String GetAdressBygoodidURL = IP
			+ "/mallService/api/EnnGoods/getDistributions/v2";


	/**
	 * 微信获取用户信息
	 */
	public static String GetMemberInfoForWxURL = IP
			+ "/mallService/api/EnnMember/getweixinUserInfo/v1/";
	/**
	 * 微信下单的url api/EnnPay/payad/v1/
	 */
	public static String WXtopayURL = IP + "/mallService/api/EnnPay/pay/v1";
	/**
	 * 会员卡充值接口
	 */
	public static String MembercardRechargeURL = IP + "/mallService/api/EnnCard/memcardRecharge/v1";
	/**
	 * 订单第三方支付失败  回滚接口
	 */
	public static String PayErrorURL = IP + "/mallService/api/EnnPay/returnCardConsumption/v1/";

	/**
	 * 获取短信验证码接口code标识 04-验证手机号 05-更换手机号
	 */
	public static String SMSCodeForRegister = "01";
	public static String SMSCodeForFindpassword = "02";
	public static String SMSCodeForBindOldmember = "03";
	public static String SMSCodeForCheckPhone = "04";
	public static String SMSCodeForChangePhone = "05";
	public static String SMSCodeForPresentMember = "06";
}
