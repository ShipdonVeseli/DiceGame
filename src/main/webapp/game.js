let BASE_URL = "https://fhdicegame2.azurewebsites.net/";

function fix_dpi() {
    if (window.screen.availWidth > 1900) {
        canvas.setAttribute('width', window.screen.availWidth/1.8);
        canvas.setAttribute('height', window.screen.availHeight/2.3);
    } else if (window.screen.availWidth > 1000) {
        canvas.setAttribute('width', window.screen.availWidth/1.3);
        canvas.setAttribute('height', window.screen.availHeight/1.7);
    }
}

function setCanvasHeight(numberOfPlayers) {
    if(players.length > 5 ) {
        canvas.height = players[numberOfPlayers-1].y + players[numberOfPlayers-1].height + 50;
    }
}

async function getStatus() {
    const response = await fetch(BASE_URL + "Game-servlet?mode=status&username=" + localStorage.getItem("username") + "&lobbyID=" + sessionStorage.getItem("lobbyid"))
    if(response.status === 400) {
        isGameTerminated = true;
        // sessionStorage.removeItem("lobbyid");
        // window.location.href = 'index.html';
    } else if(response.status === 200) {
        for (let [key, value] of response.headers) {
            if (`${key}` === "gamestatus") {
                return JSON.parse(`${value}`);
            }
        }
    }
}

let gameMode;
let numberOfPlayers;
let gameLength;
async function gameModeRequest() {
    await fetch(BASE_URL + "Game-Config-servlet?mode=get-Game-mode&username=" + localStorage.getItem("username") + "&lobbyID=" + sessionStorage.getItem("lobbyid"))
        .then(response => getGameMode(Number(response.headers.get("gameMode"))))
}

function getGameMode(data) {
    gameMode = data;
}

async function numberOfPlayersRequest() {
    await fetch(BASE_URL + "Game-Config-servlet?mode=get_Number_of_Players&lobbyID=" + sessionStorage.getItem("lobbyid"))
        .then(response => getNumberOfPlayers(Number(response.headers.get("Number_of_Players"))));
}

function getNumberOfPlayers(data) {
    numberOfPlayers = data;
}

async function gameLengthRequest() {
    await fetch(BASE_URL + "Game-Config-servlet?mode=get-game-length&lobbyID=" + sessionStorage.getItem("lobbyid"))
        .then(response => getGameRound(Number(response.headers.get("get-game-length"))));
}

function getGameRound(data) {
    gameLength = data;
}

function rollDice() {
    let button = document.getElementById('roll');
    fetch(BASE_URL + "Game-servlet?mode=roll-me&username=" + localStorage.getItem("username") + "&lobbyID=" + sessionStorage.getItem("lobbyid"))

    if(button.value === 'Roll Dices' && localStorage.getItem("username") === activePlayerName) {
        button.value = 'Move';
    } else if(button.value === 'Move' && localStorage.getItem("username") === activePlayerName) {
        fetch(BASE_URL + "Game-servlet?mode=make-move&username=" + localStorage.getItem("username") + "&lobbyID=" + sessionStorage.getItem("lobbyid"))
        button.value = 'Roll Dices';
    }
}

function reset() {
    let text = "Wollen Sie das Spiel wirklich restarten?"
    if(confirm(text) === true) {
        fetch(BASE_URL + "Game-servlet?mode=reset&username=" + localStorage.getItem("username") + "&lobbyID=" + sessionStorage.getItem("lobbyid"))
        alert("Das Spiel wird neu gestartet.")
        isGameEnded = false;
    }
}

function leave() {
    let text = "Wollen Sie das Spiel wirklich verlassen?"
    if(confirm(text) === true) {
        fetch(BASE_URL + "Game-servlet?mode=leave-game&username=" + localStorage.getItem("username") + "&lobbyID=" + sessionStorage.getItem("lobbyid"))
        alert("Das Spiel wird verlassen.")
        sessionStorage.removeItem("lobbyid");
        window.location.href = 'index.html';
    }
}

let activePlayerName;
function getActivePlayer(value, index, array) {
    fetch(BASE_URL + "Game-servlet?mode=get-Active-Player&username=" + localStorage.getItem("username") + "&lobbyID=" + sessionStorage.getItem("lobbyid"))

    let indexOfActivePlayer = array[index].activePlayerIndex + 1;
    activePlayerName = array[indexOfActivePlayer].playername;
    let roll_button = document.getElementById("roll");
    let moveBtnGameFour = document.getElementById("movegamefour")

    if(localStorage.getItem("username") === activePlayerName) {
    //     roll_button.disabled = false;
    //     roll_button.classList.remove("red");
    //     roll_button.removeAttribute("title");
        moveBtnGameFour.disabled = false;
        moveBtnGameFour.classList.remove("red");
        moveBtnGameFour.removeAttribute("title");
    } else {
    //     roll_button.disabled = true;
    //     roll_button.classList.add("red");
    //     roll_button.setAttribute("title", "It's not your turn now");
        moveBtnGameFour.disabled = true;
        moveBtnGameFour.classList.add("red")
        moveBtnGameFour.setAttribute("title", "It's not your turn now");
    }

    return activePlayerName;
}

let dices = [];
for (let i = 1; i <= 6; i++) {
    let dice = new Image();
    dice.src = "/images/Dice" + i + ".png";
    dices.push(dice);
}

function loadDiceImage(dicevalue, playerindex) {
    yupper = players[playerindex].y - 30;
    ylower = players[playerindex].y + players[playerindex].height + 30;
    for(indi = 0; indi < dicevalue.length; indi++){
        if(playerindex < 5) {
            yupper += 35;
            ctx.drawImage(dices[dicevalue[indi] - 1], players[playerindex].x + players[playerindex].width + 15, yupper, 30, 30);
        } else {
            ylower -= 35;
            ctx.drawImage(dices[dicevalue[indi] - 1], players[playerindex].x + players[playerindex].width + 15, ylower - 30, 30, 30);
        }
    }
    // requestAnimationFrame(loadDiceImage);
}

let round;
function drawCanvas(value, index, array) {
    if (index === 0) {
        ctx.clearRect(0, 0, canvas.width, canvas.height);
        round = array[index].round;
        if (round === 0) {
            oldRound = round;
        }
        if(round === gameLength) {
            document.getElementById("info").innerText = "Game is ended" + " - Round: " + round;
            isGameEnded = true;
        } else {
            document.getElementById("info").innerText = "Active Player: " + getActivePlayer(value, index, array) + " - Round: " + round;
        }
    } else {
        if(localStorage.getItem("username") !== array[index].playername){
            addPlayernameToDropDownList(array, index);
        }
        drawPlayerNames(value, index, array);
        if(gameMode !== 4) {
            loadDiceImage(array[index].dicevalue, index - 1);
        }

        players[index - 1].tokensize = array[index].blueresources + array[index].normalresources;
        players[index - 1].normalResources = array[index].normalresources;
        players[index - 1].blueResources = array[index].blueresources;

        if(array[index].playername !== "") {
            loadImageForPlayer(players[index-1]);
            drawImageForPlayer(players[index-1]);
        }
        if(array[index].playername === activePlayerName) {
            drawGreenRectangleForActivePlayer(players[index-1]);
        }

        let lastDigit = index%10;
        if(lastDigit === 0) {
            lastDigit = 10;
        }
        for (let i = 0; i < players[index - 1].blueResources; i++) {
            if(index <= 5) {
                updateLineToAddTokenForUpperRow(players[index - 1]);
                drawBlueResources(players[index - 1]);
            } else if(index > 5 && lastDigit > 5 && lastDigit <=10) {
                updateLineToAddTokenForSecondRow(players[index - 1]);
                drawBlueResources(players[index - 1]);
            } else {
                updateLineToAddTokenForEverySecondRow(players[index - 1]);
                drawBlueResources(players[index - 1]);
            }
        }

        for (let i = 0; i < players[index - 1].normalResources; i++) {
            if(index <= 5) {
                updateLineToAddTokenForUpperRow(players[index - 1]);
                drawNormalResources(players[index - 1]);
            } else if(index > 5 && lastDigit > 5 && lastDigit <=10) {
                updateLineToAddTokenForSecondRow(players[index - 1]);
                drawNormalResources(players[index - 1]);
            } else {
                updateLineToAddTokenForEverySecondRow(players[index - 1]);
                drawNormalResources(players[index - 1]);
            }
        }
    }

    switch (gameMode) {
        case 2:
            gameTwoSettings(array, index, value);
            break;
        case 3:
            gameThreeSettings();
            break;
        case 4:
            gameFourSettings(array, index, value);
            break;
    }
}

function gameTwoSettings(array, index, value) {
    if((localStorage.getItem("username") === array[index].playername && array[index].dicevalue.length === 0) || isGameEnded) {
        document.getElementById("gameModeTwo").style.display = "none";
    } else if(localStorage.getItem("username") === array[index].playername && array[index].dicevalue.length !== 0) {
        document.getElementById("gameModeTwo").style.display = "block";
    }
}

function gameThreeSettings() {
    if(isGameEnded || round > 0) {
        document.getElementById("gameModeThree").style.display = "none";
    }
}

function gameFourSettings(array, index, value) {
    if (oldRound !== round && round > 0) {
        sendChosenPlayerRequest(round - 1);
        oldRound = round;
    }
    if(localStorage.getItem("username") === array[index].playername && chosenPlayer !== undefined) {
        for (let i=0; i<players.length; i++) {
            if (players[i].name === chosenPlayer) {
                drawFlagToChosenPlayer(players[i], i);
            }
        }
    }
}

function addPlayernameToDropDownList(array, index) {
    if(document.querySelector('select').length < numberOfPlayers-1) {
        const select = document.querySelector('select');
        select.options.add(new Option(array[index].playername, array[index].playername));
    }
}

function drawPlayerNames(value, index, array) {
    ctx.font = '15px calibri';
    ctx.fillStyle = 'black';
    players[index - 1].name = array[index].playername;
    if (index < 6) {
        ctx.fillText(players[index - 1].name, players[index - 1].x, players[index - 1].y - 10, players[index - 1].width);
    } else {
        ctx.fillText(players[index - 1].name, players[index - 1].x, players[index - 1].y + players[index - 1].height + 20, players[index - 1].width);
    }
}

function convert(obj) {
    obj.then((result) => {
        result.forEach(drawCanvas)
        return result;
    }).catch(err => console.log(err))
}

function reloadField() {
    convert(getStatus());
}

let isGameTerminated = false;
let isGameEnded = false;
setInterval(() => {
    for (let i = 0; i < players.length; i++) {
        players[i].tokensize = 0;
        players[i].row = 0;
        players[i].col = 0;
    }
    if(!isGameTerminated) {
        if(round === undefined || round <= gameLength) {
            reloadField();
        }
    }
}, 600);

let canvas;
let ctx;

let PLAYER_COORDINATE_X = 100;
let PLAYER_COORDINATE_Y = 20;

let players = [];

function startGame() {
    canvas = document.getElementById('responsive-canvas');
    ctx = canvas.getContext('2d');
    canvas_statistic = document.getElementById('statistic_canvas');
    ctx_statistic = canvas_statistic.getContext('2d');
    var heightRatio = 0.5;
    canvas_statistic.height = canvas.width * heightRatio;
    canvas.height = canvas.width * heightRatio;
    fix_dpi();
    gameLengthRequest().then();
    numberOfPlayersRequest().then(() => {
        createplayer();
        setCanvasHeight(numberOfPlayers);
    })
    gameModeRequest().then(() => {
        document.getElementById("title").innerHTML += " - Game Mode " + gameMode;
        switch (gameMode) {
            case 1:
                document.getElementById("responsive-canvas").style.marginBottom = "4rem";
                break;
            case 2:
                document.getElementById("gameModeTwo").style.display = "block";
                break;
            case 3:
                document.getElementById("gameModeThree").style.display = "block";
                break;
            case 4:
                document.getElementById("gameModeFour").style.display = "block";
                document.getElementById("roll").style.display = "none";
                loadFlagForChosenPlayer();
                eventListenerForChosenWeakestLink(canvas);
                eventListenerForMouseMove(canvas);
                yourPerformance();
                break;
        }}
    )
}

function drawNormalResources(player) {
    ctx.beginPath();
    ctx.arc(player.token_x, player.token_y, 7, 0, 2 * Math.PI);
    ctx.stroke();
    ctx.closePath();
}

function drawBlueResources(player) {
    ctx.beginPath();
    ctx.arc(player.token_x, player.token_y, 7, 0, 2 * Math.PI);
    ctx.fillStyle = 'blue';
    ctx.fill();
    ctx.stroke();
    ctx.closePath();
}

function drawGreenRectangleForActivePlayer(player) {
    ctx.strokeStyle = "green";
    ctx.strokeRect(player.x - 5, player.y - 5, player.width + 10, player.height + 10);
    ctx.strokeStyle = "#000000";
}

function gameModeTwo() {
    let selection = document.getElementById("gameTwoSelection");
    let user = selection.options[selection.selectedIndex].text;
    fetch(BASE_URL + "Game-Config-servlet?mode=set-Game-Mode&username=" + localStorage.getItem("username") + "&lobbyID=" + sessionStorage.getItem("lobbyid") + "&game-mode=2");
    fetch(BASE_URL + "Game-servlet?mode=give-dice&username=" + localStorage.getItem("username") + "&lobbyID=" + sessionStorage.getItem("lobbyid") + "&playerNameReceiver=" + user);

}

function gameModeThree() {
    var getSelectedValue = document.querySelector( 'input[name="diceValue"]:checked').value;
    var min;
    var max;
    if(getSelectedValue === "1-6") {
        min = 1;
        max = 6;
    } else if(getSelectedValue === "3-4") {
        min = 3;
        max = 4;
    }
    alert("min: " + min + " max: " + max);
    fetch(BASE_URL + "Game-Config-servlet?mode=set-Game-Mode&username=" + localStorage.getItem("username") + "&lobbyID=" + sessionStorage.getItem("lobbyid") + "&game-mode=3");
    fetch(BASE_URL + "Game-Config-servlet?mode=setDice&username=" + localStorage.getItem("username") + "&lobbyID=" + sessionStorage.getItem("lobbyid") + "&min=" + min + "&max=" + max);
}

function createplayer() {
    for (let i = 1; i<=numberOfPlayers; i++) {
        let playerImageNumber = i % 10;
        if(playerImageNumber === 0) {
            playerImageNumber = 10;
        }
        let player = {
            tokensize: 0,
            normalResources: 0,
            blueResources: 0,
            row: 0,
            col: 0,
            name: ' ',
            x: PLAYER_COORDINATE_X,
            y: PLAYER_COORDINATE_Y,
            token_x: 0,
            token_y: 0,
            width: 80,
            height: 80,
            src: 'images/player' + playerImageNumber + '.png',
            img: new Image()
        };

        player.img.src = player.src;
        players.push(player);

        let lastDigit = i%10;

        if(lastDigit === 0){
            PLAYER_COORDINATE_X += 0;
        } else if (lastDigit < 5) {
            PLAYER_COORDINATE_X += 180;
        } else if (lastDigit === 5) {
            PLAYER_COORDINATE_X += 0;
        } else {
            PLAYER_COORDINATE_X -= 180;
        }

        if (lastDigit === 5 || lastDigit === 0) {
            PLAYER_COORDINATE_Y += 300;
        }
    }
}

function updateLineToAddTokenForUpperRow(player) {
    if (player.col >= player.width) {
        player.col = 0;
        player.row += 15;
    }
    player.token_x = player.x + 5 + player.col + player.width;
    player.token_y = player.y + player.height + 15 + player.row;
    player.col += 15;
}

function updateLineToAddTokenForSecondRow(player) {
    if (player.col >= player.width) {
        player.col = 0;
        player.row -= 15;
    }
    player.token_x = player.x + 5 + player.col - player.width;
    player.token_y = player.y - 15 + player.row;
    player.col += 15;
}

function updateLineToAddTokenForEverySecondRow(player) {
    if (player.col >= player.width) {
        player.col = 0;
        player.row -= 15;
    }
    player.token_x = player.x + 5 + player.col + player.width;
    player.token_y = player.y - 15 + player.row;
    player.col += 15;
}

function loadImageForPlayer(player) {
    player.img = new Image();
    player.img.src = player.src;
}

function drawImageForPlayer(player) {
    ctx.drawImage(player.img, player.x, player.y, player.width, player.height);
    // requestAnimationFrame(drawImageForPlayer);
}
//Die Funktionen bis zum nächsten Kommentar sind für Game 4
let chosenPlayer;
let oldRound;
let flag;

function getMousePos(e) {
    var mouseX = e.offsetX * canvas.width / canvas.clientWidth | 0;
    var mouseY = e.offsetY * canvas.height / canvas.clientHeight | 0;
    return {x: mouseX, y: mouseY};
}

function eventListenerForMouseMove(canvas) {
    canvas.addEventListener('mousemove', function (e) {
        if(!isGameEnded) {
            let mousePos = getMousePos(e);
            for (let i = 0; i < players.length; i++) {
                if (mousePos.x >= players[i].x && mousePos.x <= players[i].x + players[i].width) {
                    if (mousePos.y >= players[i].y && mousePos.y <= players[i].y + players[i].height) {
                        drawRedRectangleForPlayer(players[i]);
                    }
                }
            }
        }
    });
}

function eventListenerForChosenWeakestLink(canvas) {
    canvas.addEventListener('mousedown', function(e) {
        if(!isGameEnded) {
            let mousePos = getMousePos(e);
            for (let i = 0; i < players.length; i++) {
                if (mousePos.x >= players[i].x && mousePos.x <= players[i].x + players[i].width) {
                    if (mousePos.y >= players[i].y && mousePos.y <= players[i].y + players[i].height) {
                        alert(players[i].name);
                        chosenPlayer = players[i].name;
                    }
                }
            }
        }
    });
}

function loadFlagForChosenPlayer() {
    flag = new Image();
    flag.src = "/images/flag.png";
}

function drawFlagToChosenPlayer(player, index) {
    if(index < 5) {
        ctx.drawImage(flag, player.x + player.width/2.5, player.y + player.height + 15, 30, 30);
    } else {
        ctx.drawImage(flag, player.x + player.width/2.5, player.y - 50, 30, 30);
    }
}

function drawRedRectangleForPlayer(player) {
    ctx.strokeStyle = "red";
    ctx.strokeRect(player.x - 5, player.y - 5, player.width + 10, player.height + 10);
    ctx.strokeStyle = "#000000";
}

function rollAndMoveDice(){
    fetch(BASE_URL + "Game-servlet?mode=roll-all&username=" + localStorage.getItem("username") + "&lobbyID=" + sessionStorage.getItem("lobbyid"))
    fetch(BASE_URL + "Game-servlet?mode=make-move&username=" + localStorage.getItem("username") + "&lobbyID=" + sessionStorage.getItem("lobbyid"))
}

function sendChosenPlayerRequest(round) {
    if(chosenPlayer !== undefined) {
        fetch(BASE_URL + "Game-servlet?mode=vote&username=" + localStorage.getItem("username") + "&lobbyID=" + sessionStorage.getItem("lobbyid") + "&PlayerNameWeakestLink=" + chosenPlayer + "&round=" + round)
    }
}

async function performanceRequests() {
    var votingHistory = await fetch(BASE_URL + "Game-servlet?mode=get-Voting-History&username=" + localStorage.getItem("username") + "&lobbyID=" + sessionStorage.getItem("lobbyid"));
    var weakestLink = await fetch(BASE_URL + "Game-servlet?mode=get-weakest-Link&username=" + localStorage.getItem("username") + "&lobbyID=" + sessionStorage.getItem("lobbyid"));
    Promise.all([votingHistory, weakestLink])
        .then(response => {
            insertPlayerToPerformanceList(JSON.parse(response[0].headers.get("getVotingHistory")));
            insertWeakestLinkToPerformance(response[1].headers.get("weakest-Link"));
        })
}

function insertWeakestLinkToPerformance(weakestLinkName) {
    if(round === gameLength) {
        document.getElementById("weakest-link").style.display = "block";
        let img = document.getElementById("weakest-link-image");
        let name = document.getElementById("weakest-link-name");
        name.innerHTML = weakestLinkName;
        img.style.width = '3rem';
        for (let i = 0; i < players.length; i++) {
            if (players[i].name === weakestLinkName) {
                img.src = players[i].img.src;
            }
        }
    }
}

function insertPlayerToPerformanceList(json) {
    let table = document.getElementById("seeYourPerformance");
    let rowImage = null;
    let rowRound = null;
    let rowName = null;
    let lastDigit;
    let cellNumber = 0;
    for (let i = 0; i < json.players.length; i++) {
        if (localStorage.getItem("username") === json.players[i][0].playerName) {
            if(json.players[i][0].votingHistory[0].votes.length === 0) {
                insertNothingToTable(round);
            } else {
                for (let j = 0; j < json.players[i][0].votingHistory[0].votes.length; j++) {
                    let indexOfChosenPlayer = json.players[i][0].votingHistory[0].votes[j].indexOfWeakestPlayer;
                    let chosenGameRound = json.players[i][0].votingHistory[0].votes[j].gameRound;

                    if(j === 0 && chosenGameRound !== 0) {
                        insertNothingToTable(chosenGameRound);
                    }

                    lastDigit = chosenGameRound % 10;
                    addAllInfoToYourPerformance(players[indexOfChosenPlayer].name, chosenGameRound);
                }
            }
        }
    }

    function insertNothingToTable(loopSize) {
        for (let k=0; k<loopSize; k++) {
            lastDigit = k%10;
            addAllInfoToYourPerformance("nothing", k);
        }
    }

    function addAllInfoToYourPerformance(playerName, chosenGameRound) {
        if (chosenGameRound === 0 || chosenGameRound%7 === 0) {
            rowRound = table.insertRow(-1);
            rowImage = table.insertRow(-1);
            rowName = table.insertRow(-1);
        }
        let cellName = rowRound.insertCell(cellNumber);
        cellName.innerHTML = playerName;
        cellName.style.fontSize = "0.8rem";
        let cellRound = rowName.insertCell(cellNumber);
        cellRound.innerHTML = chosenGameRound;
        cellRound.style.fontSize = "0.8rem"
        addImageToYourPerformance(playerName, rowImage, cellNumber);
        cellNumber++;
        if (cellNumber === 7) {
            cellNumber = 0;
        }
    }
}

function addImageToYourPerformance(playerName, row, cellNumber) {
    let img = document.createElement('img');
    if(playerName === "nothing") {
        img.src = "images/no_choosed_player.png";
    } else {
        for (let i=0; i<players.length; i++) {
            if(players[i].name === playerName) {
                img.src = players[i].img.src;
            }
        }
    }
    img.style.width = '3rem';
    row.insertCell(cellNumber).appendChild(img);
}

function yourPerformance() {
    const openModalButtons = document.querySelectorAll('[data-modal-target]')
    const closeModalButtons = document.querySelectorAll('[data-close-button]')
    const overlay = document.getElementById('overlay')

    openModalButtons.forEach(button => {
        button.addEventListener('click', () => {
            performanceRequests().then(() => {
                const modal = document.querySelector(button.dataset.modalTarget)
                openModal(modal)
            });
        })
    })

    overlay.addEventListener('click', () => {
        document.getElementById("seeYourPerformance").innerHTML = "";
        const modals = document.querySelectorAll('.modal.active')
        modals.forEach(modal => {
            closeModal(modal)
        })
    })

    closeModalButtons.forEach(button => {
        button.addEventListener('click', () => {
            document.getElementById("seeYourPerformance").innerHTML = "";
            const modal = button.closest('.modal')
            closeModal(modal)
        })
    })

    function openModal(modal) {
        if (modal == null) return
        modal.classList.add('active')
        overlay.classList.add('active')
    }

    function closeModal(modal) {
        if (modal == null) return
        modal.classList.remove('active')
        overlay.classList.remove('active')
    }
}

// bis hier für Game 4
