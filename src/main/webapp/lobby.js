//let BASE_URL = "https://fhdicegame2.azurewebsites.net/";
let BASE_URL = "http://localhost:8079/";
let ownerLobbyIdPairs = [];

function setGameMode() {
    let lobbyid = sessionStorage.getItem("lobbyid");
    let gameMode = document.getElementById("gameModeSelection").value;
    fetch(BASE_URL + "Game-Config-servlet?mode=set-Game-Mode&username="+localStorage.getItem("username")+"&lobbyID="+lobbyid+"&game-mode="+gameMode)
}

function setGameRound() {
    let lobbyid = sessionStorage.getItem("lobbyid");
    let gameRound = document.getElementById("gameRound").value;
    fetch(BASE_URL + "Game-Config-servlet?mode=set-game-length&username="+localStorage.getItem("username")+"&lobbyID="+lobbyid+"&gameLength="+gameRound)
}

function setNumberOfPlayers() {
    let lobbyid = sessionStorage.getItem("lobbyid");
    let numberOfPlayers = document.getElementById("numberOfPlayers").value;
    fetch(BASE_URL + "Game-Config-servlet?mode=set_Number_of_Players&username="+localStorage.getItem("username")+"&lobbyID="+lobbyid+"&Number_of_Players="+numberOfPlayers)

}

function startLobby(){
    let lobbyid = sessionStorage.getItem("lobbyid");
    if(lobbyid !== null) {
        if(checkIfOwnerLobbyIdPairExists({'lobbyowner': localStorage.getItem("username"), 'lobbyid': lobbyid})){
            fetch(BASE_URL + "Game-servlet?mode=start-game&username=" + localStorage.getItem("username") + "&lobbyID=" + lobbyid)
            window.location.href = BASE_URL + "game.html"
        } else {
            alert("Sie sind nicht der Lobbyowner.");
        }
    } else {
        alert("Bitte erstellen Sie eine Lobby, um das Spiel zu starten.")
    }
}

async function createLobby(){
    await fetch(BASE_URL + "Lobby-servlet?mode=create&username="+localStorage.getItem("username"))
        // .then(response => {
        //     sessionStorage.setItem("lobbyid", response.headers.get("lobbyid"));
        // })
}

function leaveLobby(){
    sessionStorage.removeItem("lobbyid");
    fetch(BASE_URL + "Lobby-servlet?mode=leave&username="+localStorage.getItem("username"))
}

function joinLobby(id){
    let lobbyid = document.getElementById(id).getAttribute("name");
    sessionStorage.setItem("lobbyid", lobbyid);
    fetch(BASE_URL + "Lobby-servlet?mode=join&username="+localStorage.getItem("username")+"&lobbyID="+lobbyid)
}
let lobbies = [];
async function getLobbies(){
    const response = await fetch(BASE_URL + "Lobby-servlet?mode=get-Lobbies")
    for (let [key, value] of response.headers) {
        if(`${key}` === "lobbies"){
            saveLobbies(JSON.parse(`${value}`));
            return JSON.parse(`${value}`);
        }
    }
}

function checkIfOwnerLobbyIdPairExists(pair) {
    for(let i=0; i<ownerLobbyIdPairs.length; i++) {
        if(pair.lobbyid === ownerLobbyIdPairs[i].lobbyid && pair.lobbyowner === ownerLobbyIdPairs[i].lobbyowner) {
            return true;
        }
    }
    return false;
}

function insertList(value, index, array){
    if(index === 0) {
        document.getElementById("lobbies").innerHTML = "";
        ownerLobbyIdPairs = [];
    }
    let pair = {'lobbyowner': array[index].lobbyowner, 'lobbyid': array[index].lobbyid};
    if(!checkIfOwnerLobbyIdPairExists(pair)) {
        ownerLobbyIdPairs.push(pair);
    }
//    document.getElementById("lobbies").innerHTML = document.getElementById("lobbies").innerHTML + "<li>"+array[index].lobbyowner+" Lobby <input type='submit' id='"+index+"' onclick='joinLobby("+index+")' name="+array[index].lobbyid+" value='JOIN LOBBY'><ul><li>Players: "+array[index].players+"</li></ul></li>";
    document.getElementById("lobbies").innerHTML = document.getElementById("lobbies").innerHTML + "<table><tr><th>"+ "Lobbyowner: " + array[index].lobbyowner+ "</th></tr><tr><td>" + "Players:" + array[index].players+ "</td></tr><tr><td>" + "Game Mode:" + array[index].gamemode+ "</td></tr><tr><td>" + "Number of Players:" + array[index].numberofplayers+ "</td></tr><tr><td>" + "Rounds:" + array[index].gamelength+ "</td></tr><tr><td>" + "<input type='submit' id='"+index+"' onclick='joinLobby("+index+")' name="+array[index].lobbyid+" value='JOIN LOBBY'>" + "</td></tr><hr></table>";
    if(localStorage.getItem("username") === array[index].lobbyowner) {
        document.getElementById("gameConfig").style.display = "block";
    }
}

function saveLobbies(data) {
    lobbies = data;
}

function checkIfPlayerIsInLobby() {
    for(let i=0; i<lobbies.length; i++) {
        let playersOfLobby = lobbies[i].players.split(',');
        for(let j=0; j<playersOfLobby.length; j++) {
            if(localStorage.getItem("username") === playersOfLobby[j]) {
                return true;
            }
        }
    }
    return false;
}

function convert(obj){
    obj.then((result) => {
        result.forEach(insertList);
        return result;
    }).catch(err=>console.log(err))
}


function logout(){
    localStorage.removeItem("username");
}

function setUsername() {
    localStorage.setItem("username", document.getElementById("username").value);
    document.getElementById("createSession").style.display = "none";
}

async function isGameStarted(lobbyid){
    const response2 = await fetch(BASE_URL + "Game-servlet?mode=has-Game-started&username="+localStorage.getItem("username")+"&lobbyID="+lobbyid)
    for (let [key, value] of response2.headers) {
        if(`${key}` === "isstarted" && `${value}` === "true"){
            window.location.href = BASE_URL + "game.html"
        }
    }
}

async function isJoinedLobby(){
    if(checkIfPlayerIsInLobby()) {
        const response2 = await fetch(BASE_URL + "Lobby-servlet?mode=get-lobby-id&username=" + localStorage.getItem("username"))
        for (let [key, value] of response2.headers) {
            if (`${key}` === "lobbyid") {
                sessionStorage.setItem("lobbyid", `${value}`);
            }
        }
    }
}

//Für das single page verhalten wird die jeweilige form gezeigt.
if(localStorage.getItem("username") !== null) {
    document.getElementById("createSession").style.display = "none";
    convert(getLobbies());
}else{
    document.getElementById("loggedin").style.display = "none";
}
//Endlosschleife für das realtime laden
setInterval(() => {
    if(sessionStorage.getItem("lobbyid") != null) {
        isGameStarted(sessionStorage.getItem("lobbyid"));
    }else{
        isJoinedLobby();
    }
    convert(getLobbies());
}, 600);
