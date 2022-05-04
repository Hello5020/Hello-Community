/**
 * 提交回复
 */
function post(){
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
  target(questionId,1,content);
}
function comment(e) {
    var commentId = e.getAttribute("data-id");
    var content = $("#input-"+commentId).val();
        target(commentId,2,content);
}
function target(targetId,type,content) {
    if (!content){
        alert("不能回复空内容!");
        return;
    }

    $.ajax({
        type:"POST",
        url: "/comment",
        contentType:"application/json",
        data: JSON.stringify({
            "parentId": targetId,
            "content": content,
            "type": type
        }),
        success: function (response) {
            if (response.code == 5200){
                window.location.reload();
            }else {
                if(response.code == 5003){
                    var isAccepted = confirm(response.message);
                    if (isAccepted){
                        window.open("/login");
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
        $.getJSON("/comment/"+id,function (data) {
            console.log(data);
            commments.addClass("in");
            e.setAttribute("data-collapse","in");
            e.classList.add("active");
        })
    }
}

