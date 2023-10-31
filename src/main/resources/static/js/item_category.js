$(document).ready(function () {
    const categoryValue1 = document.getElementById('categoryValue1').value;
    const categoryValue2 = document.getElementById('categoryValue2').value;
    if (categoryValue1) {
        document.getElementById('category1').value = categoryValue1
        categoryDetail(categoryValue2);
    }
});
/*
    2차 카테고리 조회
 */
const resultFail = document.getElementById('resultFail');
const category1 = document.getElementById('category1');

function categoryDetail(categoryValue2){
    const selectValue= category1.value;
    $.ajax({
        url: "/item/findDetailCategory",
        type: "POST",
        data : { selectValue : selectValue },
        success: function (response){
            const category2 = document.getElementById('category2');
            console.log(response);
            category2.innerHTML = '';

            response.forEach(function (category) {
                const option = document.createElement('option');
                option.value = category.categoryCode;
                option.text = category.categoryName;
                category2.appendChild(option);
            });

            if(categoryValue2){
                category2.value = categoryValue2;
            }
        },
        error: function (error){
            console.error("요청실패 "+error);
        }
    });
}
