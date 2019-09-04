$(function() {
    loadData();
});

function getParameterByName(name) {
    var match = RegExp('[?&]' + name + '=([^&]*)').exec(window.location.search);
    return match && decodeURIComponent(match[1].replace(/\+/g, ' '));
};

function loadData(){
    $.get('/api/game_view/'+getParameterByName('gamePlayer'))
        .done(function(data) {
            console.log(data)
            let playerInfo;
            if(data.gamePlayers[0].id == getParameterByName('gamePlayer'))
                playerInfo = [data.gamePlayers[0].player.userName,data.gamePlayers[1].player.userName];
            else
                playerInfo = [data.gamePlayers[1].player.userName,data.gamePlayers[0].player.userName];

            $('#playerInfo').text(playerInfo[0] + '(you) vs ' + playerInfo[1]);

            data.ships.forEach(function(shipPiece){
                shipPiece.locations.forEach(function(shipLocation){
                    $('#'+shipLocation).addClass('ship-piece');
                })
            });
        })
        .fail(function( jqXHR, textStatus ) {
          alert( "Failed: " + textStatus );
        });
};