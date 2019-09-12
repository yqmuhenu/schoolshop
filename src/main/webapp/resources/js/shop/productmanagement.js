$(function () {
    //获取此店铺下的商品列表的URL
    var listUrl = '/schoolshop/product/getproductlistbyshop?pageIndex=1&pageSize=9999';
    //商品上下架URL
    var statusUrl = '/schoolshop/product/modifyproduct';

    getList();

    /**
     * 获取当前店铺下的商品列表
     */
    function getList() {
        //从后台获取当前店铺下的商品列表
        $.getJSON(listUrl, function (data) {
            if (data.success) {
                var productList = data.productList;
                var tempHtml = '';
                /**
                 * 遍历每条商品信息，拼接成一行显示，列表信息包括：
                 * 商品名称，
                 * 优先级，
                 * 编辑按钮（含productId）
                 * 上架/下架（含productId），
                 * 删除（含productId），
                 * 预览（含productId）
                 *
                 */
                productList.map(function (item, index) {
                    var textOp = "下架";
                    var contraryStatus = 0;
                    if (item.enableStatus == 0) {
                        //若状态值为0，表明是已下架的商品，操作变为上架
                        textOp = "上架";
                        contraryStatus = 1;
                    } else {
                        contraryStatus = 0;
                    }

                    var textDel = '删除';
                    var delStatus = -1;

                    //判断商品类别是否为空
                    var itemProductCategoryName;
                    if(item.productCategory == null){
                        itemProductCategoryName =
                            '<div class="col-25" style="color: #cd0a0a">' +
                            '编辑商品类别' +
                            '</div>';
                    }else {
                        itemProductCategoryName =
                            '<div class="col-25">' +
                            item.productCategory.productCategoryName +
                            '</div>';
                    }
                    //拼接每件商品的行信息
                    tempHtml += '' + '<div class="row row-product">'
                        + '<div class="col-25">'
                        + item.productName
                        + '</div>'
                        + itemProductCategoryName
                        /*+ '<div class="col-20">'
                        + item.priority//商品优先级不再显示
                        + '</div>'*/
                        + '<div class="col-50">'
                        + '<a href="#" class="edit" data-id="'
                        + item.productId
                        + '" data-status="'
                        + item.enableStatus
                        + '">编辑</a>'
                        + '<a href="#" class="status" data-id="'
                        + item.productId
                        + '" data-status="'
                        + contraryStatus
                        + '">'
                        + textOp
                        + '</a>'
                        + '<a href="#" class="status" data-id="'
                        + item.productId
                        + '" data-status="'
                        + delStatus
                        + '">'
                        + textDel
                        + '</a>'
                        + '<a href="#" class="preview" data-id="'
                        + item.productId
                        + '" data-status="'
                        + item.enableStatus
                        + '">预览</a>'
                        + '</div>'
                        + '</div>';
                });
                //将拼接好的信息赋值进html控件中
                $('.product-wrap').html(tempHtml);
            }
        });
    }

    /**
     * 进行上架/下架商品
     * @param id
     * @param enableStatus
     */
    function changeItemStatus(id, enableStatus) {
        //定义product json对象并添加productId以及状态（上架/下架）
        var product = {};
        product.productId = id;
        product.enableStatus = enableStatus;
        $.confirm('确定么?', function () {
            //上下架相关产品
            $.ajax({
                url: statusUrl,
                type: 'POST',
                data: {
                    productStr: JSON.stringify(product),
                    statusChange: true
                },
                dataType: 'json',
                success: function (data) {
                    if (data.success) {
                        $.toast('操作成功！');
                        getList();
                    } else {
                        //$.toast('操作失败！');
                        $.toast(data.errMsg);
                    }
                }
            });
        });
    }

    /**
     * 将class为product-wrap里面的a标签绑定上点击的事件
     */
    $('.product-wrap').on('click', 'a', function (e) {
        var target = $(e.currentTarget);
        if (target.hasClass('edit')) {
            //如果有class edit 则点击就进入店铺信息编辑页面，并带有productId参数
            window.location.href = '/schoolshop/shopadmin/productoperation?productId='
                + e.currentTarget.dataset.id;
        } else if (target.hasClass('status')) {
            //如果有class status则调用后台功能上/下架相关商品，并带有productId参数
            changeItemStatus(e.currentTarget.dataset.id,
                e.currentTarget.dataset.status);
        } else if (target.hasClass('preview')) {
            //如果有class preview则去前台展示系统该商品详情页预览商品情况
            window.location.href = '/schoolshop/frontend/productdetail?productId='
                + e.currentTarget.dataset.id;
        }
    });

    $('#new').click(function () {
        window.location.href = '/schoolshop/shopadmin/productoperation';
    });
});