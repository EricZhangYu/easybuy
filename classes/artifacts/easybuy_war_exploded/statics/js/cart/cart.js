//商品详情页面添加到购物车
function addCart() {
    var entityId=$("input[name='entityId']").val();
    var quantity=$("input[name='quantity']").val();
    //添加到购物车
    addCartByParam(entityId,quantity);
}

//添加购物车
function addCartByParam(entityId,quantity) {
    $.ajax({
        url:contextPath+"/Cart",
        method:"post",
        data:{
            action:"add",
            entityId:entityId,
            quantity:quantity
        },
        success:function (jsonStr) {
            var result=eval("("+jsonStr+")");
            //判断状态
            if(result.status==1){
                showMessage("操作成功");
                refreshCart();
            }else{
                showMessage(result.message);
            }
        }
    })
}

//刷新购物车
function refreshCart() {
    $.ajax({
        url: contextPath + "/Cart",
        method: "post",
        data: {
            action: "refreshCart"
        },
        success: function (jsonStr) {
            $("#searchBar").html(jsonStr);
        }
    })
}
//刷新订单
function refreshOrder() {
    $.ajax({
        url: contextPath + "/Cart",
        method: "post",
        data: {
            action: "toSettlement"
        },
        success: function (jsonStr) {
            $("#searchBar").html(jsonStr);
        }
    })
}

//结算加载购物车列表
    function settlement1() {
        $.ajax({
            url: contextPath + "/Cart",
            method: "post",
            data: {
                action: "toSettlement1"
            },
            success: function (jsonStr) {
                $("#settlement").html(jsonStr);
                // refreshOrder();
            }
        })
    }

function settlement2() {
    $.ajax({
        url: contextPath + "/Cart",
        method: "post",
        data: {
            action: "settlement2"
        },
        success: function (jsonStr) {
            refreshCart();
            $("#settlement").html(jsonStr);
        }
    })
}

/**
 * 结算 订单支付提醒
 */
function settlement3() {
    //判断地址
    var addressId = $("input[name='selectAddress']:checked").val();
    var newAddress = $("input[name='address']").val();
    var newRemark = $("input[name='remark']").val();
    if (addressId == "" || addressId == null) {
        showMessage("请选择收货地址");
        return;
    } else if (addressId == "-1") {
        if (newAddress == "" || newAddress == null) {
            showMessage("请填写新的收货地址");
            return;
        } else if (newAddress.length <= 2 || newAddress.length >= 50) {
            showMessage("收货地址为2-50个字符");
            return;
        }
    }
    $.ajax({
        url: contextPath + "/Cart",
        method: "post",
        data: {
            action: "settlement3",
            addressId:addressId,
            newAddress:newAddress,
            newRemark:newRemark
        },
        success: function (jsonStr) {
            if(jsonStr.substr(0,1)=="{"){
                var result = eval("(" + jsonStr + ")");
                showMessage(result.message);
            }else{
                $("#settlement").html(jsonStr);
            }
        }
    });
}

    //商品详情页的数量 减
    function subQuantity(obj,entityId) {
        var quantity=Number(getPCount(jq(obj)))-1;
        if(quantity==0){
            quantity=1;
        }
        //进行数量的修改
        modifyCart(entityId,quantity,obj);
        refreshOrder();
       // location.reload(quantity);
    }

    //商品详情页的数量 加
    function addQuantity(obj,entityId,stock) {
        var quantity=Number(getPCount(jq(obj)))+1;
        if(stock < quantity){
            showMessage("商品数量不足");
            return;//如果库存不再就直接退出程序
        }
        //进行商品数量的修改
        modifyCart(entityId,quantity,obj);
        refreshOrder();
        // location.reload(quantity);
    }

    //修改购物车列表
    function modifyCart(entityId,quantity,obj) {
        $.ajax({
            url: contextPath + "/Cart",
            method: "post",
            data: {
                action: "modifyCart",
                entityId:entityId,
                quantity:quantity
            },
            dateType:"json",
            success: function (jsonStr) {
                if(jsonStr.status==1){
                    //重新显示商品的数量
                    obj.parent().find(".car_ipt").val(quantity);
                    settlement1();
                    refreshOrder();
                }else{
                    // showMessage(jsonStr.message);
                }
            }
        })
    }
    
    //删除方法
    function removeCart(entityId) {
        $.ajax({
            url: contextPath + "/Cart",
            method: "post",
            data: {
                action: "modifyCart",
                entityId:entityId,
                quantity:0
            },
            dateType:"json",
            success: function (jsonStr) {
                if(jsonStr.status==1){
                    settlement1();
                }else{
                    showMessage(jsonStr.message);
                    // refreshOrder();
                    window.location.reload(settlement1());
                }
            }
        })
    }

    function addFavorite(entityId) {
        $.ajax({
            url: contextPath + "/Favorite",
            method: "post",
            data: {
                action: "addFavorite",
                id:entityId,
            },
            dateType:"json",
            success: function (jsonStr) {
                favoriteList();
            }
        })
    }

    function favoriteList() {
        $.ajax({
            url: contextPath + "/Favorite",
            method: "post",
            data: {
                action: "favoriteList"
            },
            success: function (jsonStr) {
                $("#favoriteList").html(jsonStr);
            }
        })
    }