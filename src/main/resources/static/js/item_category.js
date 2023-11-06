document.addEventListener("DOMContentLoaded", ()=>{
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
    const selectValue = category1.value;

    function success(result){
        const category2 = document.getElementById('category2');;
        category2.innerHTML = '';

        result.forEach(function (category) {
            const option = document.createElement('option');
            option.value = category.categoryCode;
            option.text = category.categoryName;
            category2.appendChild(option);
        });

        if(categoryValue2){
            category2.value = categoryValue2;
        }
    }
    function fail(error){
        console.error("요청실패 : "+error);
    }
    categoryRequest('POST', '/api/item/findDetailCategory', selectValue, success, fail);
}
/*
    fetch
 */
function categoryRequest(method, url, body, success, fail){
    fetch(url,{
        method: method,
        body: body,
    })
        .then(response=>response.json())
        .then(result=>success(result))
        .catch(error => {
            return fail(error);
    });
}