let canvas_statistic;
let ctx_statistic;
let numberinsystem = [];
let x1 = ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"];
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
    drawBarChart(numberinsystem, x_Axis, y_Axis, 5, x1, "Tokens in System");
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
