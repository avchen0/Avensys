var ready = (callback) => {
    if (document.readyState != "loading") callback();
    else document.addEventListener("DOMContentLoaded", callback);
}
ready(() => {
    document.querySelector(".header").style.height = window.innerHeight + "px";
})
$(document).ready(function () {

   $("#button1").click(function(){

        $('#demo-modal').modal('show');
   });

   $("#sub").click(function(){
       $("#demo-modal").modal('hide');
    });
})