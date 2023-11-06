/*
    화면이 로드될 때 실행
 */
document.addEventListener("DOMContentLoaded", ()=>{
    mainCategory();
    mainCartCount();
})
/*
   header 카테고리 리스트 조회
 */
function mainCategory(){
    function success(result){
        const categoryMenu = document.getElementById('categoryMenu');
        result.forEach(function(category, index){
            const listCategory = document.createElement("li");
            const link = document.createElement("a");
            link.setAttribute("href", "/item/category/" + category.categoryCode);
            link.textContent = category.categoryName;
            listCategory.appendChild(link);
            categoryMenu.appendChild(listCategory);
        });
    }
    function fail(error){
        console.error("카테고리 요청 실패 : "+error);
    }

    mainRequest('POST', `/api/item/findCategories`, null, success, fail);
}
/*
    header 장바구니 수량 조회
 */
function mainCartCount(){
    const checkoutItems = document.getElementById('checkout_items');
    if(checkoutItems){
        function success(result){
            if(result.cartItemCount>0){
                checkoutItems.textContent = result.cartItemCount;
                checkoutItems.style.display="flex"
            }else{
                checkoutItems.textContent="";
                checkoutItems.style.display="none"
            }
        }
        function fail(error){
            checkoutItems.textContent="";
            checkoutItems.style.display="none"
        }
        mainRequest('POST', `/api/cart/itemCount`, null, success, fail);
    }
}
/*
    fetch
 */
function mainRequest(method, url, body, success, fail){
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



