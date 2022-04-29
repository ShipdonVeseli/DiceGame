let canvas_statistic;
let ctx_statistic;
let numberinsystem = [];
//let BASE_URL = "https://fhdicegame2.azurewebsites.net/";
let BASE_URL = "http://localhost:8079/";

function loadNumberInSystem() {
    canvas_statistic = document.getElementById('statistic_canvas');
    ctx_statistic = canvas_statistic.getContext('2d');
    getNumberInSystem().then(() => {
        showNumberInSystem();
    });
}

function showNumberInSystem() {
    let x_Axis = 'Turn';
    let y_Axis = 'Number in System';
    let gameLength = sessionStorage.getItem("gameLength");
    let x1 = gameLengthToArray(gameLength);
    drawBarChart(numberinsystem, x_Axis, y_Axis, 5, x1, "Tokens in System");
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

async function getNumberInSystem(){
    await fetch(BASE_URL + "Game-servlet?mode=get-Number-in-System&username=" + localStorage.getItem("username") + "&lobbyID=" + sessionStorage.getItem("lobbyid"))
        .then(response => {
            drawNumberInSystem(JSON.parse(response.headers.get("number-in-system")));
        })
}

function drawNumberInSystem(data){
    for(let i=0; i<data[0].NumberInSystem.length; i++) {
        numberinsystem.push(data[0].NumberInSystem[i]);
    }
}
