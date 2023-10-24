$(document).ready(function () {
    $.ajax({
        url : "/item/findCategories",
        type : "POST",
        success: function (result){
            console.log(result)
            const categoryMenu = $("#categoryMenu");
            $.each(result, function (index, category){
                const listCategory = $("<li>");
                const link = $("<a>").attr("href", "/item/list/" + category.categoryCode).text(category.categoryName);
                listCategory.append(link);
                categoryMenu.append(listCategory);

                // let categories = $('<li>').attr("href", "/item?category=" + category.categoryCode).text(category.categoryName);
                // categoryMenu.append(categories);
            });
        },error: function (error){
            console.error("카테고리 요청 실패"+error);
        }
    });
    const checkoutItems = document.getElementById('checkout_items');
    if(checkoutItems){
        $.ajax({
            url : "/cart/itemCount",
            type : "POST",
            success: function (result){
                if(result.cartItemCount>0){
                    checkoutItems.textContent = result.cartItemCount;
                    checkoutItems.style.display="flex"
                }else{
                    checkoutItems.textContent="";
                    checkoutItems.style.display="none"
                }
            },error: function (error){
                console.error("카테고리 요청 실패"+error);
            }
        });
    }

})


