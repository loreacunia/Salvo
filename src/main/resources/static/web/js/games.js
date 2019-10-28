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
        swal('Failed: ' + textStatus);
      });

}
 function cargarUsuario(){
     $.get("/api/games")
           .done(function(data){
               app.games = data.games.reverse();
               app.currentUser = data.player;
               })
            .fail(function (jqXHR, textStatus) {
               swal('Failed: ' + textStatus);

                })
    }

function createGame(){
$.post("/api/games")
.done (function (data){
                 swal('Success game created: ',{
                 timer: 2500,
                 });
            window.setTimeout(function() {window.location.href = '/web/game.html?gp='+data.gpid;
            }, 2500)
    })
}

function joinGame (){
location.href = "/web/games.html?gp"+gpid;
}

function register(){
var form = document.getElementById("register-form")
 $.post("/api/players",{
 email : form ["username"].value,
 password : form ["password"].value
 })
  .done (function (jqXHR, textStatus) {
                     swal('Success: ' + textStatus);
                 })
 .done(function (){
  location.reload();
                 })
       .fail(function (jqXHR, textStatus) {
            swal('Failed: ' + textStatus);
        });
    }

     function login() {
     if(app.currentUser == "guest"){
    var form = document.getElementById('login-form')
 $.post("/api/login",
   { username: form["username"].value,
    password: form["password"].value })
    .done(setTimeout(function(){ cargarUsuario(); },1000))
      .fail(function (jqXHR, textStatus) {
        swal('Failed: ' + textStatus);
          });
                   }else{
                       console.log("Ya existe un usuario")
    }
    }
function logout() {
      $.post("/api/logout")
       .done(window.location.replace("games.html"))
       .fail(function (jqXHR, textStatus) {
            swal('Failed: ' + textStatus);
        });
    }


