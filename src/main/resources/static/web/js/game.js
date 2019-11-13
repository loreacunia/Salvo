
 function getParameterByName(name) {
   var match = RegExp('[?&]' + name + '=([^&]*)').exec(window.location.search);
   return match && decodeURIComponent(match[1].replace(/\+/g, ' '));
 }

 $.get('/api/game_view/' + getParameterByName('gp'))
    .done(function (data) {
      console.log(data);
      var playerInfo;
      if (data.gamePlayers[0].id == getParameterByName('gp')) {
        playerInfo = [data.gamePlayers[0].name, data.gamePlayers[1].name];
}
      else{
        playerInfo = [data.gamePlayers[1].name, data.gamePlayers[0].name];
        }

      $('#playerInfo').text(playerInfo[0]+ '(you) vs ' + playerInfo[1]);

      data.ships.forEach(function (shipPiece) {
        shipPiece.shipLocations.forEach(function (shipLocation) {
          let turnHitted = isHit(shipLocation,data.salvos,playerInfo[0].id)
          if(turnHitted >0){
            $('#B_' + shipLocation).addClass('ship-piece-hited');
            $('#B_' + shipLocation).text(turnHitted);
          }
          else
            $('#B_' + shipLocation).addClass('ship-piece');
        });
      });
      data.salvos.forEach(function (salvo) {
        console.log(salvo);
        if (playerInfo[0].id === salvo.player) {
          salvo.salvoLocations.forEach(function (location) {
            $('#S_' + location).addClass('salvo');
          });
        } else {
          salvo.salvoLocations.forEach(function (location) {
            $('#_' + location).addClass('salvo');
          });
        }
      });
    })
    .fail(function (jqXHR, textStatus) {
      alert('Failed: ' + textStatus);
    });


function isHit(shipLocation,salvos,playerId) {
  var hit = 0;
  salvos.forEach(function (salvo) {
    if(salvo.player != playerId)
      salvo.salvoLocations.forEach(function (location) {
        if(shipLocation === location)
          hit = salvo.turn;
      });
  });
  return hit
  };


