let commentsContainer = document.getElementById("comments_section")
let postsContainer = document.getElementById("all_posts");
let loginError = document.getElementById("login_error");
let searchPostInput = document.getElementsByClassName("search_post")[0];

commentsContainer.style.display = "none";
let resp = null;

async function loadPosts(title = "", author  = "") {
  let link;
  title = title.trim()
  if(!title && !author) link = "posts"
  else if (!title && author) link = `posts?author=${author}`
  else if (title && !author) link = `posts?title=${title}`
  else link = `posts?title=${title}&author=${author}`
  fetch(link, {
    method: "GET",
  })
    .then(response => {
      resp = response;
      return response.text()
    })
    .then(data => {
      if (resp?.status === 200) {
        postsContainer.innerHTML = data;
        commentsContainer.style.display = "block";
        loginError.style.display = "none";
      }
      else {
        console.log(data)
        commentsContainer.style.display = "none";
        postsContainer.style.display = "none";
        searchPostInput.style.display = "none";
        loginError.innerText = "Login First To Access"
      }
    });
}

window.onload = function (){
  loadPosts()
}

function doLike(id) {
  let resp = null;
  const params = new URLSearchParams();
  params.append("id", id);
  params.append("action_name", "like_post")
  fetch("posts/like", {
    method: "POST",
    body: params,
  })
    .then(response => {
      resp = response;
      return response.text()
    })
    .then(async data => {
      console.log(data)
      if(resp?.status === 403){
        window.alert("ALREADY LIKED");
      }else{
        await loadPosts()
      }
      //console.log(data)
    })
}

function doComment(id) {
  const params = new URLSearchParams();
  params.append("id", id);
  params.append("action_name", "comment_post");
  params.append("comment", "Nice Looking");
  fetch("posts/comment", {
    method: "POST",
    body: params,
  })
    .then(response => {
      resp = response;
      //console.log(response)
      return response.text()
    })
    .then(data => {
      console.log(data)
      getComments(id);
    })
}

function getComments(id){
  fetch(`post/comments?postId=${id}`, {
    method: "GET",
  })
    .then(response => {
      resp = response;
      return response.text()
    })
    .then(data => {
      commentsContainer.innerHTML = data;
    })
}

function showComments(id){
  getComments(id);
}

async function searchPosts(){
  let searchTerm = document.getElementById("search_post").value;
  await loadPosts(searchTerm);
}

async function loadMyPosts(){
  let searchTerm = document.getElementById("search_post").value;
  await loadPosts(searchTerm, "me");
}