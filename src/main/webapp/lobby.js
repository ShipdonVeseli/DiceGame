function startLobby(){
    let lobbyid = sessionStorage.getItem("lobbyid");
    let gameMode = document.getElementById("gameModeSelection").value;
    fetch("http://localhost:8079/Game-servlet?mode=start-game&username="+localStorage.getItem("username")+"&lobbyID="+lobbyid)
    fetch("http://localhost:8079/Game-Config-servlet?mode=set-Game-Mode&username="+localStorage.getItem("username")+"&lobbyID="+lobbyid+"&game-mode="+gameMode);
    window.location.href = "http://localhost:8079/game.html"
}

async function createLobby(){
    const createresponse = await fetch("http://localhost:8079/Lobby-servlet?mode=create&username="+localStorage.getItem("username"))
    for (let [key, value] of createresponse.headers) {
        console.log(`${key}`)
        console.log(`${value}`)
        if(`${key}` === "lobbyid"){
            sessionStorage.setItem("lobbyid", `${value}`)
        }
    }

}

function leaveLobby(){
    sessionStorage.removeItem("lobbyid");
    fetch("http://localhost:8079/Lobby-servlet?mode=leave&username="+localStorage.getItem("username"))
}

function joinLobby(id){
    let lobbyid = document.getElementById(id).getAttribute("name");
    sessionStorage.setItem("lobbyid", lobbyid);
    fetch("http://localhost:8079/Lobby-servlet?mode=join&username="+localStorage.getItem("username")+"&lobbyID="+lobbyid)
}

async function getLobbies(){
    const response = await fetch("http://localhost:8079/Lobby-servlet?mode=get-Lobbies")
    for (let [key, value] of response.headers) {
        if(`${key}` === "lobbies"){
            return JSON.parse(`${value}`);
        }
    }
}

function insertList(value, index, array){
    if(index === 0) document.getElementById("lobbies").innerHTML = "";
//    document.getElementById("lobbies").innerHTML = document.getElementById("lobbies").innerHTML + "<li>"+array[index].lobbyowner+" Lobby <input type='submit' id='"+index+"' onclick='joinLobby("+index+")' name="+array[index].lobbyid+" value='JOIN LOBBY'><ul><li>Players: "+array[index].players+"</li></ul></li>";
    document.getElementById("lobbies").innerHTML = document.getElementById("lobbies").innerHTML + "<table><tr><th>"+ "Lobbyowner: " + array[index].lobbyowner+ "</th></tr><tr><td>" + "Players:" + array[index].players+ "</td></tr><tr><td>" + "<input type='submit' id='"+index+"' onclick='joinLobby("+index+")' name="+array[index].lobbyid+" value='JOIN LOBBY'>" + "</td></tr><hr></table>";
    document.getElementById("gameMode").style.display = "block";
}

function convert(obj){
    obj.then((result) => {
        result.forEach(insertList)
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
    const response2 = await fetch("http://localhost:8079/Game-servlet?mode=has-Game-started&username="+localStorage.getItem("username")+"&lobbyID="+lobbyid)
    for (let [key, value] of response2.headers) {
        if(`${key}` === "isstarted" && `${value}` === "true"){
            window.location.href = "http://localhost:8079/game.html"
        }
    }
}

async function isJoinedLobby(){
    const response2 = await fetch("http://localhost:8079/Lobby-servlet?mode=get-lobby-id&username="+localStorage.getItem("username"))
    for (let [key, value] of response2.headers) {
        if(`${key}` === "lobbyid"){
            sessionStorage.setItem("lobbyid", `${value}`);
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
        isGameStarted(sessionStorage.getItem("lobbyid"))
    }else{
        isJoinedLobby();
    }
    convert(getLobbies());
}, 600);