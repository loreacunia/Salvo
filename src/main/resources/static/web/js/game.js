var gamesData
var actualPlayer
var opponent = "Waiting for opponent"
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
  var contador = 0
    //Una vez cargado los salvoes con createGrid procedemos a establecer una funcion click por cada celda de la siguiente manera
    $('div[id^="salvos"].grid-cell').click(function(){
        //seguir para codear el disparar una celda, tener en cuenta el tamaño maximo de disparos y que no pueda disparar a una celda ya pintada
        //la diferencia entre una celda pintada y otra que no esta en la clase "salvo" que se le agrega
//        console.log(evt.target)
        console.log("d")
//        si (laCeldaPintada){
//            alert("no puedes")
//        }else{
//            if (!celdaAdisparar && contador < 5){
//                addClass(celdaAdisparar)
//                contador++
//            }
//        }
    });
})
.catch(function(error){
	console.log(error)
})


function WhoIsWho(){
  for(i = 0; i < gamesData.gamePlayers.length; i++){
    if(gamesData.gamePlayers[i].gpid == gpId){
      actualPlayer = gamesData.gamePlayers[i].name
    } else{
      opponent = gamesData.gamePlayers[i].name
    }
  }
  let logger = document.getElementById("logger")
  let wrapper = document.createElement('DIV')
  let p1 = document.createElement('P')
  p1.innerHTML = `Hi ${actualPlayer}!`
  let p2 = document.createElement('P')
  p2.innerHTML = `your opponent is: ${opponent}`
  wrapper.appendChild(p1)
  wrapper.appendChild(p2)
  logger.appendChild(wrapper)
}
//shots sera un modelo de salvo:
//shot{
//  turn: 1,
//  locations:["A1","C2","G4"]
//}
//Disparar los salvos
function shoot(turno,locations){
    var url = "/api/games/players/" + getParameterByName("gp") + "/salvos"
    $.post({
        url: url,
        data: JSON.stringify({turn: turno, salvoLocations:locations}),
        dataType: "text",
        contentType: "application/json"
    })
    .done(function (response, status, jqXHR) {
        alert( "Salvo added: " + response );
        //location.reload
    })
    .fail(function (jqXHR, status, httpError){
        alert("Failed to add salvos: " + status + " " + httpError);
    })
}
