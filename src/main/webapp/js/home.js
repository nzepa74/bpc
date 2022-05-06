home = (function () {
        "use strict";

        function _baseURL() {
            return 'api/evaluationDashboard/';
        }

        function getAllCompanyScore(year) {
            $.ajax({
                url: 'api/common/getAllCompanyScore',
                type: 'GET',
                data: {year: year},
                success: function (res) {
                    let data = res.dto;
                    let figure = [];
                    let category = [];
                    for (let i = 0; i < data.length; i++) {
                        figure.push(data[i].score);
                        category.push(data[i].shortName);
                    }
                    $('#allCompanyScoreGraph').empty();
                    displayScoreChart(figure, category);
                }
            });
        }

        function displayScoreChart(figure, category) {
            window.ApexCharts && (new ApexCharts(document.getElementById('allCompanyScoreGraph'), {
                chart: {
                    events: {
                        click: (event, chartContext, config) => {
                            let shortName = config.config.xaxis.categories[config.dataPointIndex];
                            let year = $("#year").val();
                            if (typeof shortName !== "undefined") {
                                window.location.href = 'score?yId=' + year + '&&sName=' + shortName;
                            }
                        }
                    },
                    type: "bar",
                    fontFamily: 'inherit',
                    height: 320,
                    parentHeightOffset: 0,
                    toolbar: {
                        show: false,
                    },
                    animations: {
                        enabled: true
                    },
                },
                plotOptions: {
                    bar: {
                        columnWidth: '50%',
                    },
                },
                dataLabels: {
                    enabled: true,
                    formatter: function (val) {
                        return val + "%";
                    }
                },
                fill: {
                    opacity: 1,
                },
                series: [{
                    name: "% Scored",
                    data: figure
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
                        enabled: false
                    },
                    axisBorder: {
                        show: false,
                    },
                    categories: category,
                },
                yaxis: {
                    labels: {
                        padding: 4
                    },
                },
                // colors: ["#47aef3"],
                colors: [
                    function ({value, seriesIndex, w}) {
                        let color = '#ff0000';
                        if (value > 70 && value < 80) {
                            color = '#eeb05f'
                        } else if (value > 80 && value < 90) {
                            color = '#6fe39b'
                        } else if (value >= 90) {
                            color = '#5cb85c'
                        }
                        return color;
                    }
                ],
                legend: {
                    show: false,
                },
            })).render();
        }

        return {
            getAllCompanyScore: getAllCompanyScore
        }
    }
)();

$(document).ready(function () {
    let year = $('#year').val();
    let companyId = $('#companyId').val();
    home.getAllCompanyScore(2022);
});