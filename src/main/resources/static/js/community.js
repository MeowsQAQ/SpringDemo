//提交回复
function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    comment2target(questionId,1,content);
}
//弹出二级回复
function collapseComments(e) {
    var id =e.getAttribute("data-id");
    var comments=$("#comment-"+id);
    var collapse = e.getAttribute("data-collapse")
    if (collapse){
        comments.removeClass("in")
        e.removeAttribute("data-collapse")
        e.classList.remove("active")
    }else{
        $.getJSON( "/comment/"+id, function( data ) {
            console.log(data);
            comments.addClass("in");
            //标记二级评论状态
            e.setAttribute("data-collapse","in");
            e.classList.add("active")
        });
    }
}

function comment2target(targetId,type,content) {
    if (!content){
        alert("内容不能为空");
        return;
    }
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType:'application/json',
        data:JSON.stringify({
        "parentId":targetId,
        "commentContent":content,
        "commentType":type
    }),
        success:function(response){
        if (response.code==200){
            window.location.reload();
        }else {
            if (response.code==2003) {
                var isAccepted = confirm(response.message);
                if (isAccepted){
                    window.open("https://github.com/login/oauth/authorize?client_id=90b1a8b5753a396293b4&redirect_url=http://localhost:8080/callback&scope=user&state=1")
                    window.localStorage.setItem("closeable","1");
                }
            } else{
                alert(response.message)
            }
        }
        console.log(response)
    },
    dataType:"json"
});
}

function comment(e) {
    var commentId = e.getAttribute("data-id");
    var content =$("#sub-comment-"+commentId).val();
    comment2target(commentId,2,content)
}