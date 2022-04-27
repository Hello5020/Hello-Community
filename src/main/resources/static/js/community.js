/**
 * 提交回复
 */
function post(){
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    if (!content){
     alert("不能回复空内容!");
     return;
    }

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
                window.location.reload();
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

/**
 * 二级评论
 */
function collapseComments(e) {
    var id = e.getAttribute("data-id");
    var commments = $("#comment-"+id);

    var collapse = e.getAttribute("data-collapse");
    if (collapse){
        commments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active");
    }else {
        commments.addClass("in");
        e.setAttribute("data-collapse","in");
        e.classList.add("active");
    }
}

