function post(){
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    $.ajax({
        type:"POST",
        url: "/comment",
        contentType:"application/json",
        data: JSON.stringify({
            "parentId": questionId,
            "content": content,
            "type": 1
        }),
        success: function (response) {
            if (response.code == 5200){
                $("#comment_section").hide();
            }else {
                if(response.code == 5003){
                    var isAccepted = confirm(response.message);
                    if (isAccepted){
                        window.open("https://github.com/login/oauth/authorize?client_id=fd6e83c078c4d597be9a&redirect_uri=http://localhost:8889/callback&scope=user&state=1");
                        window.localStorage.setItem("closeable",true);
                    }
                }else {
                    alert(response.message);
                }
            }
            console.log(response);
        },
        dataType: "json"
    });
}