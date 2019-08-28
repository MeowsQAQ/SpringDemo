function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType:'application/json',
        data:JSON.stringify({
            "parentId":questionId,
            "commentContent":content,
            "commentType":1
        }),
        success:function(response){
            if (response.code==200){
                $("#comment_section").hide();
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