let dpi = window.devicePixelRatio;

function fix_dpi() {
//get CSS height
//the + prefix casts it to an integer
//the slice method gets rid of "px"
    let style_height = +getComputedStyle(canvas).getPropertyValue("height").slice(0, -2);
//get CSS width
    let style_width = +getComputedStyle(canvas).getPropertyValue("width").slice(0, -2);
//scale the canvas

    if (window.screen.availWidth > 1900) {
        canvas.setAttribute('width', window.screen.availWidth/1.8);
        canvas.setAttribute('height', window.screen.availHeight/2.3);
    } else if (window.screen.availWidth > 1000) {
        canvas.setAttribute('width', window.screen.availWidth/1.3);
        canvas.setAttribute('height', window.screen.availHeight/1.7);
    }
}

async function getStatus() {
    const response = await fetch("http://localhost:8079/Game-servlet?mode=status&username=" + localStorage.getItem("username") + "&lobbyID=" + sessionStorage.getItem("lobbyid"))
    for (let [key, value] of response.headers) {
        if (`${key}` === "gamestatus") {
            return JSON.parse(`${value}`);
        }
    }
}

async function getActivity(){
    const response = await fetch("http://localhost:8079/Game-servlet?mode=get-Activity&username=" + localStorage.getItem("username") + "&lobbyID=" + sessionStorage.getItem("lobbyid"));
    for (let [key, value] of response.headers) {
        if (`${key}` === "activity") {
            return JSON.parse(`${value}`);
        }
    }
}

async function getThroughput(){
    const response = await fetch("http://localhost:8079/Game-servlet?mode=get-Throughput&username=" + localStorage.getItem("username") + "&lobbyID=" + sessionStorage.getItem("lobbyid"));
    for (let [key, value] of response.headers) {
        if (`${key}` === "throughput") {
            return JSON.parse(`${value}`);
        }
    }
}

async function getNumberInSystem(){
    const response = await fetch("http://localhost:8079/Game-servlet?mode=get-Number-in-System&username=" + localStorage.getItem("username") + "&lobbyID=" + sessionStorage.getItem("lobbyid"));
    for(let [key, value] of response.headers) {
        if (`${key}` === "number-in-system") {
            return JSON.parse(`${value}`);
        }
    }
}

function rollDice() {
    let button = document.getElementById('roll');
    if(button.value === 'Roll Dices') {
        fetch("http://localhost:8079/Game-servlet?mode=roll-all&username=" + localStorage.getItem("username") + "&lobbyID=" + sessionStorage.getItem("lobbyid"))
        button.value = 'Move';
    } else {
        fetch("http://localhost:8079/Game-servlet?mode=make-move&username=" + localStorage.getItem("username") + "&lobbyID=" + sessionStorage.getItem("lobbyid"))
        button.value = 'Roll Dices';
    }
}

function getActivePlayer(value, index, array) {
    fetch("http://localhost:8079/Game-servlet?mode=get-Active-Player&username=" + localStorage.getItem("username") + "&lobbyID=" + sessionStorage.getItem("lobbyid"))

    let indexOfActivePlayer = array[index].activePlayerIndex + 1;
    let activePlayerName = array[indexOfActivePlayer].playername;

    if(localStorage.getItem("username") === activePlayerName) {
        document.getElementById('roll').disabled = false;
    } else {
        document.getElementById('roll').disabled = true;
    }

    return activePlayerName;
}

let dices = [];
let throughput = [];
let numberinsystem = [];
for (let i = 1; i <= 6; i++) {
    let dice = new Image();
    dice.src = "/images/Dice" + i + ".png";
    dices.push(dice);
}

function loadDiceImage(dicevalue, playerindex) {
    ctx.drawImage(dices[dicevalue - 1], players[playerindex].x + players[playerindex].width + 15, players[playerindex].y, 50, 50);
    requestAnimationFrame(loadDiceImage);
}

function drawCanvas(value, index, array) {
    if (index === 0) {
        ctx.clearRect(0, 0, 1500, 1000);
        document.getElementById("info").innerText = "Active Player: " + getActivePlayer(value, index, array) + " - Round: " + array[index].round;
    } else {
        drawPlayerNames(value, index, array);
        loadDiceImage(array[index].dicevalue, index - 1);

        players[index - 1].tokensize = array[index].blueresources + array[index].normalresources;
        players[index - 1].normalResources = array[index].normalresources;
        players[index - 1].blueResources = array[index].blueresources;

        for (let i = 0; i < players[index - 1].blueResources; i++) {
            if (index > 5) {
                updateLineToAddTokenForBottomRow(players[index - 1]);
                drawBlueResources(players[index - 1]);
            } else {
                updateLineToAddTokenForUpperRow(players[index - 1]);
                drawBlueResources(players[index - 1]);
            }
        }

        for (let i = 0; i < players[index - 1].normalResources; i++) {
            if (index > 5) {
                updateLineToAddTokenForBottomRow(players[index - 1]);
                drawNormalResources(players[index - 1]);
            } else {
                updateLineToAddTokenForUpperRow(players[index - 1]);
                drawNormalResources(players[index - 1]);
            }
        }
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

function drawThroughput(value,index, array){
    if(index === 0) throughput = [];
    throughput.push(array[index].Throughput)
}
function drawNumberInSystem(value,index, array){
    if(index === 0) numberinsystem = [];
    numberinsystem.push(array[index].NumberInSystem)
}

let dicevalues = []
function drawActivity(value, index, array){
    if(index === 0) dicevalues = [];
    dicevalues.push(array[index].dicevalues)
}

function convert(obj) {
    obj.then((result) => {
        result.forEach(drawCanvas)
        return result;
    }).catch(err => console.log(err))
}

function convertActivity(obj) {
    obj.then((result) => {
        result.forEach(drawActivity)
        return result;
    }).catch(err => console.log(err))
}

function convertThroughput(obj) {
    obj.then((result) => {
        result.forEach(drawThroughput)
        return result;
    }).catch(err => console.log(err))
}

function convertNumberInSystem(obj) {
    obj.then((result) => {
        result.forEach(drawNumberInSystem);
        return result;
    }).catch(err => console.log(err))
}

function reloadField() {
    convert(getStatus());
}


setInterval(() => {
    for (let i = 0; i < players.length; i++) {
        players[i].tokensize = 0;
        players[i].row = 0;
        players[i].col = 0;
    }
    convertNumberInSystem(getNumberInSystem());
    convertThroughput(getThroughput())
    convertActivity(getActivity());
    reloadField();
}, 5000);

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
    loadImages();
    createplayer();
    drawImages();

    document.getElementById("statistic").style.display = "none";
    document.getElementById("activity_buttons").style.display = "none";
    document.getElementById("back").style.display = "none";
}

function setButtonsForStatistics(button_to_display, value, not_display_btn1, not_display_btn2, not_display_btn3) {
    document.getElementById('buttons').classList.remove('statisticButtons');
    var game_id = document.getElementById("game");
    var statistic = document.getElementById("statistic");
    var activePlayer = document.getElementById("info");
    var roll_btn = document.getElementById("roll");

    if (game_id.style.display === "none") {
        button_to_display.value = value;
        roll_btn.style.display = "inline";
        activePlayer.style.display = "block";
        game_id.style.display = "block";
        statistic.style.display = "none";
        not_display_btn1.style.display = "inline";
        not_display_btn2.style.display = "inline";
        not_display_btn3.style.display = "inline";
    } else {
        button_to_display.value = "Back";
        roll_btn.style.display = "none";
        activePlayer.style.display = "none";
        game_id.style.display = "none";
        statistic.style.display = "block";
        not_display_btn1.style.display = "none";
        not_display_btn2.style.display = "none";
        not_display_btn3.style.display = "none";
        document.getElementById('buttons').classList.add('statisticButtons');
    }
}

function showNumberInSystem() {
    let showActivity_btn = document.getElementById('showActivity');
    let showThroughput_btn = document.getElementById('showThroughput');
    let showNumberInSystem_btn = document.getElementById('showNumberInSystem');
    let showTimeInSystem_btn = document.getElementById('showTimeInSystem');
    setButtonsForStatistics(showNumberInSystem_btn, "Show Number in System", showActivity_btn, showThroughput_btn, showTimeInSystem_btn);
    let x_Axis = 'Turn';
    let y_Axis = 'Number in System';
    drawBarChart(numberinsystem[0], x_Axis, y_Axis, 1);
}

function showTimeInSystem(){
    let showActivity_btn = document.getElementById('showActivity');
    let showThroughput_btn = document.getElementById('showThroughput');
    let showNumberInSystem_btn = document.getElementById('showNumberInSystem');
    let showTimeInSystem_btn = document.getElementById('showTimeInSystem');
    setButtonsForStatistics(showNumberInSystem_btn, "Show Number in System", showActivity_btn, showThroughput_btn, showTimeInSystem_btn);
    let x_Axis = 'Turn';
    let y_Axis = 'Number in System';
    drawBarChart('', x_Axis, y_Axis, 1);
}


function showThroughput() {
    let showActivity_btn = document.getElementById('showActivity');
    let showThroughput_btn = document.getElementById('showThroughput');
    let showNumberInSystem_btn = document.getElementById('showNumberInSystem');
    let showTimeInSystem_btn = document.getElementById('showTimeInSystem');
    setButtonsForStatistics(showThroughput_btn, "Show Throughput", showActivity_btn, showNumberInSystem_btn, showTimeInSystem_btn);
    let x_Axis = 'Turn';
    let y_Axis = 'Throughput';
    drawBarChart(throughput[0], x_Axis, y_Axis, 1);
}

function backToGame() {
    document.getElementById('buttons').classList.remove('statisticButtons');
    document.getElementById('game').style.display = "block";
    document.getElementById('statistic').style.display = "none";
    document.getElementById('roll').style.display = "inline";
    document.getElementById('showActivity').style.display = "inline";
    document.getElementById('showNumberInSystem').style.display = "inline";
    document.getElementById('showThroughput').style.display = "inline";
    document.getElementById('showTimeInSystem').style.display = "inline";
    document.getElementById('activity_buttons').style.display = "none";
    document.getElementById('back').style.display = "none";
}

function drawBarChart(data, x_Axis, y_Axis, stepSize) {
    new Chart(document.getElementById("statistic_canvas"), {
        type: 'bar',
        data: {
            labels: ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"],
            datasets: [
                {
                    backgroundColor: "#3e95cd",
                    data: data
                }
            ]
        },
        options: {
            legend: {display: false},
            responsive: true,
            maintainAspectRatio: false,
            title: {
                display: true,
                text: 'Activity'
            },
            scales: {
                yAxes: [{
                    scaleLabel: {
                        display: true,
                        labelString: y_Axis
                    },
                    ticks: {
                        beginAtZero: true,
                        stepSize: stepSize
                    }
                }],
                xAxes: [{
                    scaleLabel: {
                        display: true,
                        labelString: x_Axis
                    }
                }]
            }
        }
    });
}

function showActivity() {
    ctx_statistic.clearRect(0,0,ctx_statistic.width, ctx_statistic.height);
    var getSelectedValue = document.querySelector( 'input[name="activity"]:checked').value;

    document.getElementById('buttons').classList.add('statisticButtons');
    document.getElementById('game').style.display = "none";
    document.getElementById('statistic').style.display = "block";
    document.getElementById('roll').style.display = "none";
    document.getElementById('showActivity').style.display = "none";
    document.getElementById('showNumberInSystem').style.display = "none";
    document.getElementById('showThroughput').style.display = "none";
    document.getElementById('showTimeInSystem').style.display = "none";
    document.getElementById('activity_buttons').style.display = "block";
    document.getElementById('back').style.display = "inline-block";

    let x_Axis = 'Turn';
    let y_Axis = 'Number';
    drawBarChart(dicevalues[getSelectedValue-1], x_Axis, y_Axis, 1);
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

function createplayer() {
    for (let i = 1; i < 11; i++) {
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
            src: 'images/player' + i + '.png',
            img: new Image()
        };

        player.img.src = player.src;
        players.push(player);

        if (i < 5) {
            PLAYER_COORDINATE_X += 180;
        } else if (i === 5) {
            PLAYER_COORDINATE_X += 0;
        } else {
            PLAYER_COORDINATE_X -= 180;
        }

        if (i === 5) {
            PLAYER_COORDINATE_Y += 300;
        }
    }

}

function updateLineToAddTokenForUpperRow(player) {
    if (player.col < player.width) {
        player.token_x = player.x + 5 + player.col + player.width;
        player.token_y = player.y + player.height + 15 + player.row;
    } else {
        player.col = 0;
        player.row += 15;
        player.token_x = player.x + 5 + player.col + player.width;
        player.token_y = player.y + player.height + 15 + player.row;
    }
    player.col += 15;
}

function updateLineToAddTokenForBottomRow(player) {
    if (player.col < player.width) {
        player.token_x = player.x + 5 + player.col;
        player.token_y = player.y - 15 + player.row;
    } else {
        player.col = 0;
        player.row -= 15;
        player.token_x = player.x + 5 + player.col;
        player.token_y = player.y - 15 + player.row;
    }
    player.col += 15;
}

function loadImages() {
    players.forEach(function (player) {
        player.img = new Image();
        player.img.src = player.src;
    });
}

function drawImages() {
    players.forEach(function (player) {
        ctx.drawImage(player.img, player.x, player.y, player.width, player.height);
    });
    requestAnimationFrame(drawImages);
}
