import React, {useState} from "react";
import axios from "../api/Axios";
import StatisticsView from "./StatisticsView";
import {
    Chart as ChartJS,
    CategoryScale,
    LinearScale,
    BarElement,
    Title,
    Tooltip,
    Legend,
} from 'chart.js';

ChartJS.register(
    CategoryScale,
    LinearScale,
    BarElement,
    Title,
    Tooltip,
    Legend
);

const url = '/api/v1/statistics';

const Statistics = () => {
    const [dateFrom, setDateFrom] = useState();
    const [dateTo, setDateTo] = useState();
    const [currencyId, setCurrencyId] = useState(1);

    const [text, setText] = useState();
    const [dailyStatistics, setDailyStatistics] = useState();

    const getStatistics = () => {
        axios.get(url, {
            params: {
                dateFrom,
                dateTo,
                currencyId
            }
        }).then(r => {
            console.log(r.data.dailyStatistics)
            setDailyStatistics(r.data.dailyStatistics)
            setText('Статистика за период (' + dateFrom + ' - ' + dateTo + ')')
        }).catch(err => {
            // todo
        })
    }


    const statisticsBar = dailyStatistics && text &&
        <StatisticsView dailyStatistics={dailyStatistics} text={text}/>

    return (
        <div className="statistics">
            <div className="statistics-filters">
                <form onSubmit={e => {
                    e.preventDefault()
                    getStatistics()
                }}>
                    <div>
                        <label htmlFor="date-from">Дата с:</label>
                        <input
                            type="date"
                            name="date-from"
                            onChange={e => {
                                e.preventDefault()
                                setDateFrom(e.target.value)
                            }}
                            value={dateFrom}
                        />
                    </div>
                    <div>
                        <label htmlFor="date-to">Дата по:</label>
                        <input
                            type="date"
                            name="date-to"
                            onChange={e => {
                                e.preventDefault()
                                setDateTo(e.target.value)
                            }}
                            value={dateTo}
                        />
                    </div>
                    <button type="submit">Рассчитать</button>
                </form>
            </div>
            <div className="statistics-bar">
                {statisticsBar}
            </div>
        </div>
    );
}

export default Statistics;
