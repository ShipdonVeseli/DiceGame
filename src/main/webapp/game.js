/*window.onload=function ()
{
    c=document.getElementById('dcg');
    var cc= c.getContext('2d');

    var background = new Image();
    background.src = "images\\gamebackground.jpg";

    background.onload = function ()
    {
        cc.drawImage(background,0,0);
    }
}*/
let dpi = window.devicePixelRatio;

function fix_dpi() {
//get CSS height
//the + prefix casts it to an integer
//the slice method gets rid of "px"
    let style_height = +getComputedStyle(canvas).getPropertyValue("height").slice(0, -2);
//get CSS width
    let style_width = +getComputedStyle(canvas).getPropertyValue("width").slice(0, -2);
//scale the canvas
    canvas.setAttribute('height', style_height * dpi);
    canvas.setAttribute('width', style_width * dpi);
}

async function getStatus() {
    const response = await fetch("http://localhost:8079/Game-servlet?mode=status&username=" + localStorage.getItem("username") + "&lobbyID=" + sessionStorage.getItem("lobbyid"))
    for (let [key, value] of response.headers) {
        if (`${key}` === "gamestatus") {
            return JSON.parse(`${value}`);
        }
    }
}

function rollDice(){
    fetch("http://localhost:8079/Game-servlet?mode=roll-all&username="+localStorage.getItem("username")+"&lobbyID=" + sessionStorage.getItem("lobbyid"))
    fetch("http://localhost:8079/Game-servlet?mode=make-move&username="+localStorage.getItem("username")+"&lobbyID=" + sessionStorage.getItem("lobbyid"))
}
function loadDiceImage(dicevalue, playerindex){
    let dice = new Image();
    dice.src = "/images/Dice"+dicevalue+".png";
    ctx.drawImage(dice, players[playerindex].x+players[playerindex].width+15, players[playerindex].y, 50, 50);
}
function drawCanvas(value, index, array) {
     if (index === 0) {
         ctx.clearRect(0, 0, 1500,1000);
         getRound(value, index, array)
     } else {
         loadDiceImage(array[index].dicevalue, index-1);
         players[index-1].tokensize =  array[index].blueresources + array[index].normalresources;
         for (let i=0; i<players[index-1].tokensize; i++) {
             if(index > 6){
                 updateLineToAddTokenForBottomRow(players[index-1]);
                 drawToken(players[index-1]);
             }else{
                 updateLineToAddTokenForUpperRow(players[index-1]);
                 drawToken(players[index-1]);
             }
         }
    }

}

function getRound(value, index, array) {
    ctx.fillText(array[index].round, 1250, 400);
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


setInterval(() => {
    for(let i = 0; i < players.length; i++){
        players[i].tokensize = 0;
        players[i].row = 0;
        players[i].col = 0;
    }
    reloadField();
}, 5000);

let canvas;
let ctx;

let PLAYER_COORDINATE_X = 50;
let PLAYER_COORDINATE_Y = 30;
let token = {
    width: 20,
    height: 20
};
let players = [];

function startGame() {
    canvas = document.getElementById('responsive-canvas');
    ctx = canvas.getContext('2d');
    ctx.font = "35px Arial";
    var heightRatio = 0.5;
    canvas.height = canvas.width * heightRatio;
    fix_dpi();
    loadImages();
    createplayer();
    drawImages();
    generateStartTokens();
}

function drawToken(player) {
    ctx.beginPath();
    ctx.arc(player.token_x, player.token_y, 7, 0, 2 * Math.PI);
    ctx.stroke();
}

function generateStartTokens() {
    players.forEach(function(player) {
        for (let i=0; i<player.tokensize; i++) {
            updateLineToAddTokenForUpperRow(player);
            drawToken(player);
        }
    })
}

function createplayer() {
    var token;
    for (let i = 1; i < 11; i++) {
        let player = {
            tokensize: 0,
            token: token = [],
            row: 0,
            col: 0,
            name: 'Player ' + i,
            x: PLAYER_COORDINATE_X,
            y: PLAYER_COORDINATE_Y,
            token_x: 0,
            token_y: 0,
            width: 100,
            height: 100,
            src: 'images/player' + i + '.png',
            img: new Image()
        };

        player.img.src = player.src;
        players.push(player);

        if (i < 5) {
            PLAYER_COORDINATE_X += 210;
        } else if (i === 5) {
            PLAYER_COORDINATE_X += 0;
        } else {
            PLAYER_COORDINATE_X -= 210;
        }

        if (i === 5) {
            PLAYER_COORDINATE_Y += 320;
        }
    }

}

function updateLineToAddTokenForUpperRow(player) {
    if (player.col < player.width) {
        player.token_x = player.x + 5 + player.col;
        player.token_y = player.y + player.height + 15 + player.row;
    } else {
        player.col = 0;
        player.row += 15;
        player.token_x = player.x + 5 + player.col;
        player.token_y = player.y + player.height + 15 + player.row;
    }
    player.col += 15;
}

function updateLineToAddTokenForBottomRow(player) {
    if(player.col < player.width) {
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
