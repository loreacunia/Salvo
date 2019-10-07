var  app = new Vue ({
    el: "#app",
    data:{
    games:[],
    players:[],
    currentUser: ""
    }
})
$(function () {
  loadData();
  cargarUsuario();
});

function getParameterByName(name) {
  var match = RegExp('[?&]' + name + '=([^&]*)').exec(window.location.search);
  return match && decodeURIComponent(match[1].replace(/\+/g, ' '));
}
function loadData() {
  $.get('/api/leaderboard')
  .done(function (data){
    app.players = data;
    })
  .fail(function (jqXHR, textStatus) {
        alert('Failed: ' + textStatus);
      });

}
 function cargarUsuario(){
     $.get("/api/games")
           .done(function(data){
               app.games = data.games;
               app.currentUser = data.player.email;
               })
            .fail(function (jqXHR, textStatus) {
               alert('Failed: ' + textStatus);

                })
    }


function register(){
var form = document.getElementById("register-form")
 $.post("/api/players",{
 email : form ["username"].value,
 password : form ["password"].value
 })
  .done (function (jqXHR, textStatus) {
                     alert('Success: ' + textStatus);
                 })
       .fail(function (jqXHR, textStatus) {
            alert('Failed: ' + textStatus);
        });
    }

     function login() {
     if(app.currentUser == "Guest"){
    var form = document.getElementById('login-form')
 $.post("/api/login",
   { username: form["username"].value,
    password: form["password"].value })
    .done(setTimeout(function(){ cargarUsuario(); },1000))
      .fail(function (jqXHR, textStatus) {
        alert('Failed: ' + textStatus);
          });
                   }else{
                       console.log("Ya existe un usuario")
    }
    }
function logout() {
      $.post("/api/logout")
       .done(window.location.replace("games.html"))
       .fail(function (jqXHR, textStatus) {
            alert('Failed: ' + textStatus);
        });
    }