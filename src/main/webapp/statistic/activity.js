let canvas_statistic;
let ctx_statistic;
let dicevalues = [];
//let BASE_URL = "https://fhdicegame2.azurewebsites.net/";
let BASE_URL = "http://localhost:8079/";

function loadActivity() {
    canvas_statistic = document.getElementById('statistic_canvas');
    ctx_statistic = canvas_statistic.getContext('2d');
    createActivityButtons();
    getActivity().then(() => {
        showActivity();
    })
}

function showActivity() {
    ctx_statistic.clearRect(0,0,ctx_statistic.width, ctx_statistic.height);
    var getSelectedValue = document.querySelector( 'input[name="activity"]:checked').value;
    let gameLength = sessionStorage.getItem("gameLength");
    let x1 = gameLengthToArray(gameLength);
    let x_Axis = 'Turn';
    let y_Axis = 'Number';
    drawBarChart(dicevalues[getSelectedValue-1], x_Axis, y_Axis, "", x1, "History of Rolls");
}

function createActivityButtons() {
    let numberOfPlayers = Number(sessionStorage.getItem("numberOfPlayers"));
    for(let i=1; i<=numberOfPlayers; i++) {
        let radiobox = createRadioButton('activity_player' + i, i.toString(), i.toString());
        if(i===1) {
            radiobox.checked = true;
        }
    }
    createRadioButton('activity_all', numberOfPlayers+1, 'all');
}

function createRadioButton(id, value, innerHTML) {
    let radiobox = document.createElement('input');
    radiobox.type = 'radio';
    radiobox.id = id;
    radiobox.value = value;
    radiobox.name = 'activity';
    radiobox.onclick = function () {
        showActivity();
    }

    let label = document.createElement('label');
    label.htmlFor = id;
    label.innerHTML = innerHTML;

    let container = document.getElementById("activity_buttons");
    container.appendChild(radiobox);
    container.appendChild(label);
    return radiobox;
}

function gameLengthToArray(gameLength) {
    let result = [];
    for(let i=0; i<=gameLength; i++) {
        result.push(i.toString());
    }
    return result;
}

function drawBarChart(data, x_Axis, y_Axis, stepSize, x_Size, title) {
    new Chart(document.getElementById("statistic_canvas"), {
        type: 'bar',
        data: {
            labels: x_Size,
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
                text: title
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

async function getActivity(){
    await fetch(BASE_URL + "Game-servlet?mode=get-Activity&username=" + localStorage.getItem("username") + "&lobbyID=" + sessionStorage.getItem("lobbyid"))
        .then(response => {
        drawActivity(JSON.parse(response.headers.get("activity")))
    })
}

function drawActivity(data){
    for(let i=0; i<data.length; i++) {
        dicevalues.push(data[i].dicevalues);
    }
}