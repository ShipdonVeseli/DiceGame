let canvas_statistic;
let ctx_statistic;
let timeinsystem = [];
let x2 = ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34"]
//let BASE_URL = "https://fhdicegame2.azurewebsites.net/";
let BASE_URL = "http://localhost:8079/";

function loadTimeInSystem() {
    canvas_statistic = document.getElementById('statistic_canvas');
    ctx_statistic = canvas_statistic.getContext('2d');
    getTimeInSystem().then(() => {
        showTimeInSystem();
    });
}

function showTimeInSystem(){
    let x_Axis = 'Order of Arrival';
    let y_Axis = 'Time in System';
    drawBarChart(timeinsystem, x_Axis, y_Axis, 1, x2, "Time in System of Token");
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

async function getTimeInSystem(){
    await fetch(BASE_URL + "Game-servlet?mode=get-Time-in-System&username=" + localStorage.getItem("username") + "&lobbyID=" + sessionStorage.getItem("lobbyid"))
        .then(response => {
            drawTimeInSystem(JSON.parse(response.headers.get("time-in-system")))
        })
}

function drawTimeInSystem(data){
    for(let i=0; i<data[0].resourceDataArrayList.length; i++) {
        timeinsystem.push(data[0].resourceDataArrayList[i]);
    }
}

