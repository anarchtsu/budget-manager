import React from 'react';
import {Bar} from "react-chartjs-2";


const StatisticsView = ({dailyStatistics, text}) => {
    const footer = (tooltipItems) => {
        let tooltipText = ''
        tooltipItems[0].raw.tooltipList
            .forEach(([key, value]) => tooltipText += key + ': ' + Math.abs(value) + '\n')
        return tooltipText;
    };
    const multiplier = dailyStatistics.length < 4 ? 400 : 200
    const styles = {
        width: (dailyStatistics.length * multiplier) + 'px',
        height: '450px'
    }
    return (
        <div className="chartWrapper">
            <div style={styles}>
                <Bar
                    data={{
                        labels: dailyStatistics.map(d => d.date),
                        datasets: [
                            {
                                label: 'Доходы',
                                data: dailyStatistics.map(e => ({
                                    date: e.date,
                                    value: e.incomesAmount,
                                    tooltipList: Object.entries(e.categoryStatistics).filter(([key, value]) => value > 0)
                                })),
                                borderColor: 'blue',
                                backgroundColor: 'blue',
                                parsing: {
                                    xAxisKey: 'date',
                                    yAxisKey: 'value'
                                }
                            },
                            {
                                label: 'Расходы',
                                data: dailyStatistics.map(e => ({
                                    date: e.date,
                                    value: e.expensesAmount,
                                    tooltipList: Object.entries(e.categoryStatistics).filter(([key, value]) => value < 0)
                                })),
                                borderColor: 'red',
                                backgroundColor: 'red',
                                parsing: {
                                    xAxisKey: 'date',
                                    yAxisKey: 'value'
                                }
                            }
                        ],
                    }}
                    height={400}
                    // width={600}
                    options={{
                        maintainAspectRatio: false,
                        responsive: true,
                        plugins: {
                            legend: {
                                position: 'left',
                            },
                            title: {
                                display: true,
                                position: 'left',
                                text: text
                            },
                            tooltip: {
                                callbacks: {
                                    footer: footer
                                }
                            }
                        }
                    }}
                />
            </div>
        </div>
    );
};

export default StatisticsView;
