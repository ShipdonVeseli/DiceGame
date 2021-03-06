let canvas_statistic;
let ctx_statistic;
let throughput = [];
//let BASE_URL = "https://fhdicegame2.azurewebsites.net/";
let BASE_URL = "http://localhost:8079/";


function loadThroughput() {
    canvas_statistic = document.getElementById('statistic_canvas');
    ctx_statistic = canvas_statistic.getContext('2d');
    getThroughput().then(() => {
        showThroughput();
    });
}

function showThroughput() {
    let x_Axis = 'Turn';
    let y_Axis = 'Throughput';
    let gameLength = sessionStorage.getItem("gameLength");
    let x1 = gameLengthToArray(gameLength);
    drawBarChart(throughput, x_Axis, y_Axis, 4, x1, "Throughput of Tokens");
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

async function getThroughput(){
    await fetch(BASE_URL + "Game-servlet?mode=get-Throughput&username=" + localStorage.getItem("username") + "&lobbyID=" + sessionStorage.getItem("lobbyid"))
        .then(response => {
            drawThroughput(JSON.parse(response.headers.get("throughput")));
        })
}

function drawThroughput(data){
    for(let i=0; i<data[0].Throughput.length; i++) {
        throughput.push(data[0].Throughput[i]);
    }
}
