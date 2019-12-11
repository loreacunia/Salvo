var gamesData
var actualPlayer
var opponent = {"name":"Waiting for opponent"}
//Para obtener el id del gamePlayer colocado como query en la url
var gpId = getParameterByName("gp")
console.log(gpId)
function getParameterByName(name) {
  var match = RegExp('[?&]' + name + '=([^&]*)').exec(window.location.search);
  return match && decodeURIComponent(match[1].replace(/\+/g, ' '));
}

fetch("/api/game_view/"+gpId)
.then(function(response){
	return response.json()
})
.then(function(json){
  gamesData = json
  //Determina el jugador actual y contra quien juega
  WhoIsWho()

  //Cargamos por medio de gridstackla los barcos y chequeamos si ya hay barcos o no para hacer una grilla statica o no
  //(En un futuro se trabajara sin la pagina intermedia de place-ship por lo que de momento esto comparacion esta demas)
  if(gamesData.ships.length > 0){
    //if true, the grid is initialized in static mode, that is, the ships can't be moved
    loadGrid(true)
  } else{
    //On the contrary, the grid is initialized in dynamic mode, allowing the user to move the ships
    loadGrid(false)
     //A futuro para cargar los salvos por medio de gridstack
    //loadGridSalvo()
  }


   createGrid(11, $(".grid-salvos"), 'salvos') //carga la matriz que contendra los salvoes pero sin gridstack.js
    setSalvos() //carga los salvoes ya guardados
      //Una vez cargado los salvoes con createGrid procedemos a establecer una funcion click por cada celda de la siguiente manera
      $('div[id^="salvos"].grid-cell') .click(function(){
          if(!$(this).hasClass("salvo") && !$(this).hasClass("targetCell") && $(".targetCell").length < 5)
            {
              $(this).addClass("targetCell");
            } else if($(this).hasClass("targetCell")){
              $(this).removeClass("targetCell");}
      })

    //Determino que celdas golpee y las 'pinto'
    gamesData.hits.opponent.forEach(function(playTurn) {
            playTurn.hitLocations.forEach(function (hitCell) {
  x = +(hitCell.substring(1)) - 1
  y =stringToInt(hitCell[0].toUpperCase())
                cellID = "#salvoes" + y + x;
                $(cellID).addClass("hitCell");
            });
        });
        makeGameRecordTable(gamesData.hits.opponent, "gameRecordOppTable");
         makeGameRecordTable(gamesData.hits.self, "gameRecordSelfTable");


})
.catch(function(error){
	console.log(error)
})


function WhoIsWho(){
  for(i = 0; i < gamesData.gamePlayers.length; i++){
    if(gamesData.gamePlayers[i].gpid == gpId){
      actualPlayer = gamesData.gamePlayers[i]
    } else{
      opponent = gamesData.gamePlayers[i]
    }
  }
  let logger = document.getElementById("logger")
  let wrapper = document.createElement('DIV')
  let p1 = document.createElement('P')
  p1.innerHTML = `Hi ${actualPlayer.name}!`
  let p2 = document.createElement('P')
  p2.innerHTML = `your opponent is: ${opponent.name}`
  wrapper.appendChild(p1)
  wrapper.appendChild(p2)
  logger.appendChild(wrapper)
}
//shots sera un modelo de salvo:
//shot{
//  turn: 1,
//  locations:["A1","C2","G4"]
//}

function getTurn (){
  var arr=[]
  var turn = 0;
  gamesData.salvos.map(function(salvo){
    if(salvo.player == actualPlayer.id){
      arr.push(salvo.turn);
    }
  })
  turn = Math.max.apply(Math, arr);

  if (turn == -Infinity){
    return 1;
  } else {
    return turn + 1;
  }

}

function shoot(){
    var turno = getTurn()
    var locationsToShoot=[];
    $(".targetCell").each(function(){
        let location = $(this).attr("id").substring(6);
        let locationConverted = String.fromCharCode(parseInt(location[0]) + 65) + (parseInt(location[1]) + 1)

        locationsToShoot.push(locationConverted)
    })
    console.log(locationsToShoot)
    var url = "/api/games/players/" + getParameterByName("gp") + "/salvoes"
    $.post({
        url: url,
        data: JSON.stringify({turn: turno, salvosLocation:locationsToShoot}),
        dataType: "text",
        contentType: "application/json"
    })
    .done(function (response, status, jqXHR) {
        alert( "Salvo added: " + response );
        location.reload();
    })
    .fail(function (jqXHR, status, httpError){
        alert("Failed to add salvo: " + status + " " + httpError);
    })

}
function makeGameRecordTable (hitsArray, gameRecordTableId) {

        var tableId = "#" + gameRecordTableId + " tbody";
        $(tableId).empty();
        var shipsAfloat = 5;
        var playerTag;
        if (gameRecordTableId == "gameRecordOppTable") {
            playerTag = "#opp";
        }
        if (gameRecordTableId == "gameRecordSelfTable") {
            playerTag = "#";
        }

        hitsArray.forEach(function (playTurn) {
            var hitsReport = "";
            if (playTurn.damages.carrierHits > 0){
                hitsReport += "Carrier " + addDamagesIcons(playTurn.damages.carrierHits, "hit") + " ";
                if (playTurn.damages.carrier === 5){
                    hitsReport += "SUNK! ";
                    shipsAfloat--;
                }
            }

            if (playTurn.damages.battleshipHits > 0){
                hitsReport += "Battleship " + addDamagesIcons(playTurn.damages.battleshipHits, "hit") + " ";
                if (playTurn.damages.battleship === 4){
                    hitsReport += "SUNK! ";
                    shipsAfloat--;
                }
            }
            if (playTurn.damages.submarineHits > 0){
                hitsReport += "Submarine " + addDamagesIcons(playTurn.damages.submarineHits, "hit") + " ";
                if (playTurn.damages.submarine === 3){
                    hitsReport += "SUNK! ";
                    shipsAfloat--;
                }
            }
            if (playTurn.damages.destroyerHits > 0){
                hitsReport += "Destroyer " + addDamagesIcons(playTurn.damages.destroyerHits, "hit") + " ";
                if (playTurn.damages.destroyer === 3){
                    hitsReport += "SUNK! ";
                    shipsAfloat--;
                }
            }
            if (playTurn.damages.patrolboatHits > 0){
                hitsReport += "Patrol Boat " + addDamagesIcons(playTurn.damages.patrolboatHits, "hit") + " ";
                if (playTurn.damages.patrolboat === 2){
                    hitsReport += "SUNK! ";
                    shipsAfloat--;
                }
            }

            if (playTurn.missed > 0){
                hitsReport +=  "Missed shots " + addDamagesIcons(playTurn.missed, "missed") + " ";
            }

            if (hitsReport === ""){
                hitsReport = "All salvos missed! No damages!"
            }

            $('<tr><td class="textCenter">' + playTurn.turn + '</td><td>' + hitsReport + '</td></tr>').prependTo(tableId);

        });
        $('#shipsLeftSelfCount').text(shipsAfloat);
    }

    function addDamagesIcons (numberOfHits, hitOrMissed) {
        var damagesIcons = "";
        if (hitOrMissed === "missed") {
            for (var i = 0; i < numberOfHits; i++) {
                damagesIcons += "<img class='hitblast' src='img/missed.png'>"
            }
        }
            if (hitOrMissed === "hit") {
                for (var i = 0; i < numberOfHits; i++) {
                    damagesIcons += "<img class='hitblast' src='img/redhit.png'>"
                }
        }
        return damagesIcons;
    }

function backToHomepage() {
  swal("Closing game...", {
    closeOnClickOutside: true,
    icon: "info",
    buttons: false,
    timer: 1500,
  });
  window.setTimeout(function () { window.location.href = "http://localhost:8080/web/games.html" }, 1500);
}






