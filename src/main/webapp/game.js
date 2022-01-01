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

}

function convert(obj){
    obj.then((result) => {
        result.forEach(drawCanvas)
        return result;
    }).catch(err=>console.log(err))
}

function reloadField(){
   convert(getStatus());
}


setInterval(() => {
    reloadField();
}, 5000);