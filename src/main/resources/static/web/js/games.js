var  app = new Vue ({
    el: "#app",
    data:{
        games:[],
        players:[],
        currentUser: "",
        username: "",
        password: ""
    },
    methods: {
        returnToGame(id, hasShips) {
        if(hasShips=="YES"){
        window.location.href = "http://localhost:8080/web/game.html?gp=" +id;
        } else {
        window.location.href = "http://localhost:8080/web/grid.html?gp=" +id;
        }
        },


   joinGame(gameid) {
      $.post("/api/games/" + gameid + "/players")
        .done(function (data) {
          swal("Joining game...", {
            closeOnClickOutside: false,
            icon: "info",
            buttons: false,
            timer: 1500,
          });
          window.setTimeout(function () {
            window.location.href = "http://localhost:8080/web/grid.html?gp=" + data.gpId;}, 1500);
        });
            }
            },
            }
 );

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
               app.currentUser = data.player == "guest" ? data.player : data.player.email;
               })
            .fail(function () {
               swal('Failed: ' + textStatus);

                })
    }

function createGame(){
$.post("/api/games")
.done (function (data){
                 swal('Success game created: ',{
                 timer: 2500,
                 });
            window.setTimeout(function() {window.location.href = '/web/grid.html?gp='+data.gpid;
            }, 2500)
    })
}

function register(){
 if(app.username == "" || app.password == "")
         swal("Please complete the fields.",{
                         closeOnClickOutside: false,
                         icon: "error",
                         buttons: false,
                         timer: 2500,
                         })


 else{
  $.post("/api/players", {
     username: app.username,
     password: app.password
     })
     .done(function(){
      swal("Welcome. Let's play!", {
             closeOnClickOutside: false,
             icon: "success",
             buttons: false,
             timer: 2500,
           });

                login();
         })
       .fail(function() {
        swal("Sorry, we couldn't create your account. Try again", {
                closeOnClickOutside: true,
                icon: "error",
                buttons: false,
                timer: 2500,
              });
     });
 }
 }

 function login() {
   if(app.username == "" || app.password == "")
        swal("Please complete the fields.",{
                        closeOnClickOutside: false,
                        icon: "error",
                        buttons: false,
                        timer: 2500,
                        });


else{
 $.post("/api/login", {
    username: app.username,
    password: app.password
    })
    .done(function(){
     swal("Welcome. Let's play!", {
            closeOnClickOutside: false,
            icon: "success",
            buttons: false,
            timer: 2500,
          });
          window.setTimeout(function () { window.location.reload() }, 2500);
        })
      .fail(function() {
       swal("Sorry, we couldn't access your account. Try again", {
               closeOnClickOutside: true,
               icon: "error",
               buttons: false,
               timer: 2500,
             });
    });
}
}


function logout() {
      $.post("/api/logout")
       .done( function(){
            window.location.replace("games.html")
       })
       .fail(function (jqXHR, textStatus) {
            swal('Failed: ' + textStatus);
        });
    }
