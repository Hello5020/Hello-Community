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
        var subCommentContainer = $("#comment-"+id);
        if (subCommentContainer.children().length != 1){
            commments.addClass("in");
            e.setAttribute("data-collapse","in");
            e.classList.add("active");
        }else {
            $.getJSON("/comment/"+id,function (data) {
                $.each(data.data.reverse(),function (index,comment) {
                    console.log(comment);
                    var avatarElement = $("<img/>",{
                        "class": "media-object img-thumbnail",
                        "src":comment.user.avatarUrl
                    });
                    var mediaLeftElement = $("<div/>",{
                        "class": "media-left"
                    });
                    var $h5 = $("<h5/>", {
                        "class": "media-heading media-body",
                        "html": comment.user.name
                    });
                    var  mediaBodyElement = $("<div/>",{
                        "class": "media-body"
                    });
                    var menu = $("<div/>",{
                        "class": "comment-div menu"
                    });
                    menu.append($("<span/>",{
                        "class": "pull-right",
                        "html": moment(comment.gmtCreate).format("YYYY-MM-DD")
                    }));
                    mediaBodyElement.append($h5).append($("<div/>",{
                        "class": "huifu",
                        "html":comment.content
                    })).append(menu);
                    mediaLeftElement.append(avatarElement);
                    var mediaElement = $("<div/>",{
                        "class": "media"
                    })
                    mediaElement.append(mediaLeftElement).append(mediaBodyElement);
                    var contentElement = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments"
                    });

                    contentElement.append(mediaElement);
                    subCommentContainer.prepend(contentElement);
                });
                commments.addClass("in");
                e.setAttribute("data-collapse","in");
                e.classList.add("active");
            });
        }
    }
}
function selectTag(e) {
    var value = e.getAttribute("data-tag")
    var previous = $("#tag").val();
    if (previous.search(value) == -1){
        if (previous){
            $("#tag").val(previous+','+value);
        }else {
            $("#tag").val(value);
        }
    }
}
function showSelectTag() {
    $("#selectTag").show()
}
function hideSelectTag() {
    $("#selectTag").hide()
}
function AddLikeCount(e){
    $.ajax({
        type:"POST",
        url: "/comment",
        contentType:"application/json",
        data: JSON.stringify({
            "parentId": targetId,
            "content": content,
            "type": 3
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