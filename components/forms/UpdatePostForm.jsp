<form id="create_post_form" action="create" method="post">
    <h1>Update Post</h1>
    <div class="input_combo">
        <p id="previous" class="input_error"></p>
        <p>Enter Post Id</p>
        <input id="post_title" type="text" name="id" placeholder="123" required> </input>
    </div>
    <div class="input_combo">
        <p id="t_error" class="input_error"></p>
        <p>Enter New Title</p>
        <input id="post_title" type="text" name="postTitle" placeholder="spreading happiness is favour for humanity..." required> </input>
    </div>
    <div class="input_combo">
        <p id="s_error" class="input_error"></p>
        <p>Enter Updated Description</p>
        <textarea id="post_description" type="text" name="postDescription" placeholder="i.e khan" required> </textarea>
    </div>
    <div class="button_wrapper">
        <button type="submit" id="update_post_btn" name = "action" value = "update_post">Update Post</button>
    </div>
</form>