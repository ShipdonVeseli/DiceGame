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

async function getStatus(){
    const response = await fetch("http://localhost:8079/Game-servlet?mode=status&username="+localStorage.getItem("username")+"&lobbyID="+sessionStorage.getItem("lobbyid"))
    for (let [key, value] of response.headers) {
        if(`${key}` === "gamestatus"){
            return JSON.parse(`${value}`);
        }
    }
}

function drawCanvas(value, index, array){
    if(index === 0){
        getRound(value, index, array)
    }else{
        for(let i=0; i < players.length; i++) {
            updateLineToAddToken(players[i]);
            ctx.beginPath();
            ctx.arc(players[i].token_x, players[i].token_y, 2, 0, 2 * Math.PI);
            ctx.stroke();
        }
    }

}

function getRound(value, index, array){
    ctx.fillText(array[index].round, 10, 50);

}

function convert(obj){
    obj.then((result) => {
        result.forEach(drawCanvas)
        return result;
    }).catch(err=>console.log(err))
}

function reloadField(){
    //ctx.clearRect(0, 0, 1000,1000);
    convert(getStatus());
}


setInterval(() => {
    reloadField();
}, 5000);

let canvas;
let ctx;

let PLAYER_COORDINATE_X = 15;
let PLAYER_COORDINATE_Y = 15;
let token = {
    width: 20,
    height: 20
};
let players = [];

function startGame() {
    canvas = document.getElementById('responsive-canvas');
    ctx = canvas.getContext('2d');
    ctx.font = "15px verdana";
    var canvas = document.getElementById('responsive-canvas');
    var heightRatio = 0.5;
    canvas.height = canvas.width * heightRatio;

    loadImages();
    createplayer();
    drawImages();
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
            width: 20,
            height: 20,
            src: 'images/player' + i + '.png',
            img: new Image()
        };

        player.img.src = player.src;
        players.push(player);

        if(i < 5) {
            PLAYER_COORDINATE_X += 60;
        } else if(i===5) {
            PLAYER_COORDINATE_X += 0;
        } else {
            PLAYER_COORDINATE_X -= 60;
        }

        if(i===5) {
            PLAYER_COORDINATE_Y += 100;
        }
    }

}

function updateLineToAddToken(player) {
    if (player.col < player.width) {
        player.token_x = player.x + player.col;
        player.token_y = player.y + player.height + 10 + player.row;
    } else {
        player.col = 0;
        player.row += 5;
        player.token_x = player.x + player.col;
        player.token_y = player.y + player.height + 10 + player.row;
    }
    player.col += 5;
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

