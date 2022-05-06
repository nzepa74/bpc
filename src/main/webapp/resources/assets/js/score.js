score = (function () {
        "use strict";

        function _baseURL() {
            return 'api/score/';
        }

        function getEvaluationScore(year, companyId) {
            $.ajax({
                url: 'api/common/evaluationScore',
                type: 'GET',
                data: {companyId: companyId, year: year},
                success: function (res) {
                    let data = res.dto;
                    let score = [];
                    let year = [];
                    for (let i = 0; i < data.length; i++) {
                        score.push(data[i].score);
                        year.push(data[i].year);
                    }
                    $('#evaluationGraph').empty();
                    displayScoreGraph(score, year);
                }
            });
        }

        function displayScoreGraph(score, year) {
            window.ApexCharts && (new ApexCharts(document.getElementById('evaluationGraph'), {
                chart: {
                    type: "area",
                    fontFamily: 'inherit',
                    height: 300,
                    parentHeightOffset: 0,
                    toolbar: {
                        show: false,
                    },
                    animations: {
                        enabled: true
                    },
                },
                dataLabels: {
                    enabled: true,
                },
                fill: {
                    opacity: .16,
                    type: 'solid'
                },
                stroke: {
                    width: 2,
                    lineCap: "round",
                    curve: "smooth",
                },
                series: [{
                    name: "% Scored",
                    data: score
                }],
                grid: {
                    padding: {
                        top: -20,
                        right: 0,
                        left: -4,
                        bottom: -4
                    },
                    strokeDashArray: 0,
                },
                xaxis: {
                    labels: {
                        padding: 0,
                    },
                    tooltip: {
                        enabled: true
                    },
                    axisBorder: {
                        show: false,
                    },
                },
                yaxis: {
                    labels: {
                        padding: 4
                    },
                },
                labels: year,
                colors: ["#47aef3"],
                legend: {
                    show: false,
                },
            })).render();
        }

        function progressBar(score) {
            let i = 0;
            if (i === 0) {
                i = 1;
                let elem;
                elem = document.getElementById("score");
                $('#score').addClass('custom-progress-bar');
                $('.scoreText').empty().text(score);
                let counter = $('.scoreText').text();
                $({numberValue: 0}).animate({numberValue: counter}, {
                    duration: 300,
                    easing: 'linear',
                    progress: function () {
                        $('.scoreText').html("<small>Scored " + Math.ceil(this.numberValue * 100) / 100 + "%</small>");
                    }
                });

                let width = -1;
                let id = setInterval(frame, 10);

                function frame() {
                    if (width >= score) {
                        clearInterval(id);
                        i = 0;
                    } else {
                        width++;
                        elem.style.width = width + "%";
                    }
                }
            }
        }

        return {
            getEvaluationScore: getEvaluationScore
            , progressBar: progressBar
        }
    }
)();

$(document).ready(function () {
    let year = $('#year').val();
    let companyId = $('#companyId').val();
    score.getEvaluationScore(null, null);
    score.progressBar(93.75, 1);
 });