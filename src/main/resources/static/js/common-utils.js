// function formRequest(method, url, body, success, fail){
//     fetch(url,{
//         method: method,
//         body : body,
//     }).then((response)=> response.text())
//     .then((result)=>success(result))
//         .catch(error => {
//         console.error("요청 실패 " + error);
//         return fail(error);
//     });
// }
/*
   error Alert
 */
function cartErrorConfirm(response){
    Swal.fire({
        text: '상품을 장바구니에 추가할 수 없습니다',
        icon: 'error',
        showCancelButton: false,
        confirmButtonColor: '#8a8a8a',
        confirmButtonText: '확인',
        width: 400,

    }).then((result) => {
        if (result.value) {
            location.replace(response)
        }
    })
}
/*
    cart success Alert
 */
function successConfirm(text,confirm,cancel){
    Swal.fire({
        text: text,
        icon: 'success',
        showCancelButton: true,
        confirmButtonColor: '#66bc39',
        cancelButtonColor: '#8a8a8a',
        confirmButtonText: confirm,
        cancelButtonText: cancel,
        width: 400,

    }).then((result) => {
        if (result.value) {
            location.replace(`/cart`)
        }
    })
}