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