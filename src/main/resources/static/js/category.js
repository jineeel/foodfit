$(document).ready(function () {
    $.ajax({
        url : "/item/findCategories",
        type : "post",
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
})


