/*
    request
 */
// function request(method, url, body, success, fail){
//     fetch(url,{
//         method: method,
//         body: JSON.stringify(body),
//         headers: {
//             'Content-Type': 'application/json'
//         }
//     }).then(response=>{
//         if (response.status === 200 || response.status === 201) {
//             if(response.redirected){
//                 return fail(response);
//             }else {
//                 return success();
//             }
//         }
//         if(!response.ok){
//             throw new Error('네트워크 응답이 실패했습니다');
//         }
//         return response.json();
//     }).catch(error => {
//         console.error("요청 실패 " + error);
//         return fail();
//     });
// }

/*
   error Alert
 */
function errorAlert(text,url){
    Swal.fire({
        text: text,
        icon: 'error',
        showCancelButton: false,
        confirmButtonColor: '#8a8a8a',
        confirmButtonText: '확인',
        width: 400,

    }).then((result) => {
        if (result.value) {
            location.replace(url)
        }
    })
}
/*
    success Confirm
 */
function successConfirm(text,confirm,cancel, url){
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
            location.replace(url)
        }
    })
}

/*
    success Alert
 */
function successAlert(text,confirm,url){
    Swal.fire({
        text: text,
        icon: 'success',
        showCancelButton: true,
        confirmButtonColor: '#66bc39',
        confirmButtonText: confirm,
        width: 400,

    }).then((result) => {
        if (result.value) {
            location.replace(url)
        }
    })
}