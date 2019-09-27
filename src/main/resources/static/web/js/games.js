var  app = new Vue ({
    el: "#app",
    data:{
    games:[],
    players:[]
    }
})
loadData();
function loadData() {
    $.get("/api/leaderboard")
    .done(function(data) {
      app.players = data;
    })
    .fail(function( jqXHR, textStatus ) {
      console.log( "Failed: " + textStatus );
    });
  }
  function login(evt) {
    evt.preventDefault();
    var form = evt.target.form;
    $.post("/api/login",
           { name: form["username"].value,
             pwd: form["password"].value })
     .done(console.log("loggeado"))
     .fail(function( jqXHR, textStatus ) {
           console.log( "Failed: " + textStatus );
         });
  }

  function logout(evt) {
    evt.preventDefault();
    $.post("/api/logout")
     .done(console.log("desbloqueado"))
     .fail(function( jqXHR, textStatus ) {
           console.log( "Failed: " + textStatus );
         });
  }