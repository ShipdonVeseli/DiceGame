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

function drawCanvas(value, index, array) {
    // if (index === 0) {
    //     getRound(value, index, array)
    // } else {
    //     for (let i = 0; i < players.length; i++) {
    //         updateLineToAddToken(players[i]);
    //         drawToken(players[i]);
    //     }
    // }

}

function getRound(value, index, array) {
    ctx.fillText(array[index].round, 10, 50);
}

function convert(obj) {
    obj.then((result) => {
        result.forEach(drawCanvas)
        return result;
    }).catch(err => console.log(err))
}

function reloadField() {
    //ctx.clearRect(0, 0, 1000,1000);
    convert(getStatus());
}


setInterval(() => {
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
    ctx.font = "15px verdana";
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
            updateLineToAddToken(player);
            drawToken(player);
        }
    })
}

function createplayer() {
    var token;
    for (let i = 1; i < 11; i++) {
        let player = {
            tokensize: 4,
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

function updateLineToAddToken(player) {
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

