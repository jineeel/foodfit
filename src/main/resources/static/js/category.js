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

    // const checkout_items = document.getElementById('checkout_items');
    // $.ajax({
    //     url : "/cart/itemCount",
    //     type : "POST",o
    //     success: function (result){
    //         console.log(result)
    //         if(result!=0){
    //             checkout_items.textContent = result
    //         }else{
    //             checkout_items.textContent="";
    //         }
    //
    //     },error: function (error){
    //         console.error("카테고리 요청 실패"+error);
    //     }
    // });
})


