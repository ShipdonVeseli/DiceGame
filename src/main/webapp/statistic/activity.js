let canvas_statistic;
let ctx_statistic;
let dicevalues = [];
let x1 = ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"];
let BASE_URL = "http://localhost:8079/";

function loadActivity() {
    canvas_statistic = document.getElementById('statistic_canvas');
    ctx_statistic = canvas_statistic.getContext('2d');
    getActivity().then(() => {
        showActivity();
    })
}

function showActivity() {
    ctx_statistic.clearRect(0,0,ctx_statistic.width, ctx_statistic.height);
    var getSelectedValue = document.querySelector( 'input[name="activity"]:checked').value;

    let x_Axis = 'Turn';
    let y_Axis = 'Number';
    drawBarChart(dicevalues[getSelectedValue-1], x_Axis, y_Axis, "", x1, "History of Rolls");
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